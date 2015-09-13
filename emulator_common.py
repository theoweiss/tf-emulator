#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Emulator Generator
Copyright (C) 2015 Thomas Weiss <theo@m1theo.org>

emulator_common.py: Common Library for generation of Emulator bindings

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public
License along with this program; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.
"""

import sys
import os
import copy


sys.path.append(os.path.split(os.getcwd())[0])
import common

class JavaDevice(common.Device):
    def __init__(self, raw_data, generator):
        common.Device.__init__(self, raw_data, generator)
        self.sensor_packets = []
        self.actuator_packets = []
        self.other_packets = []
        self.sensor_fields = {}
        self.actor_fields = {}
        self.other_actor_fields = {}
        self.other_sensors = {}
        self.special_fields = {}
        self.other_fields = {}
        self.callback_data = {}

    def get_java_class_name(self):
        return self.get_camel_case_category() + self.get_camel_case_name()

    def get_underscore_device_name(self):
        return self.get_underscore_category() + '_' + self.get_underscore_name()

    def load_model_data(self, model_path):
        if model_path not in sys.path:
            sys.path.append(model_path)
        self.sensor_fields = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').sensor_fields)
        self.actor_fields = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').actor_fields)
        self.other_fields = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').other_fields)
        self.other_actor_fields = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').other_actor_fields)
        self.other_sensors = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').other_sensors)
        self.special_fields = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').special_fields)
        callback_data = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').callbacks)
        self.callback_data = callback_data
        model_data = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').mod)
        self.model_data = model_data

        for packet in self.all_function_packets:
            if packet.get_headless_camel_case_name() in ('getAPIVersion', 'getResponseExpected', 'setResponseExpected',
                                                         'setResponseExpectedAll', 'getProtocol1BrickletName'):
                continue
            packet_data = model_data[packet.get_headless_camel_case_name()]
            self.subdevice_type = packet_data['subdevice_type']
            packet.field = packet_data['field']
            packet.function_type = packet_data['function_type']
            if packet_data['subdevice_type'] == 'sensor':
                self.sensor_packets.append(packet)
            elif packet_data['subdevice_type'] == 'actuator':
                self.actuator_packets.append(packet)
            else:
                self.other_packets.append(packet)
        for packet in self.callback_packets:
            packet_data = callback_data[packet.get_headless_camel_case_name()]
            packet.field = packet_data['field']

class JavaPacket(common.Packet):
    def __init__(self, raw_data, device, generator):
        common.Packet.__init__(self, raw_data, device, generator)
        self.subdevice_type = None
        self.field = None
        self.function_type = None
        
    def get_java_object_name(self):
        name = self.get_camel_case_name()
        if name.startswith('Get'):
            name = name[3:]

        return name

    def get_java_return_type(self, for_doc=False):
        elements = self.get_elements('out')

        if len(elements) == 0:
            return 'void'

        if len(elements) > 1:
            if for_doc:
                return self.get_device().get_java_class_name() + '.' + self.get_java_object_name()
            else:
                return self.get_java_object_name()

        return_type = elements[0].get_java_type()
        if hasattr(self.get_device().get_generator(), 'is_octave') and \
           self.get_device().get_generator().is_octave() and return_type == 'char':
            return_type = 'String'

        if elements[0].get_cardinality() > 1 and elements[0].get_type() != 'string':
            return_type += '[]'

        return return_type

    def get_java_parameter_list(self, just_types=False):
        param = []

        for element in self.get_elements():
            if element.get_direction() == 'out' and self.get_type() == 'function':
                continue
            java_type = element.get_java_type()
            if hasattr(self.get_device().get_generator(), 'is_octave') and \
               self.get_device().get_generator().is_octave() and java_type == 'char':
                java_type = 'String'
            name = element.get_headless_camel_case_name()
            arr = ''
            if element.get_cardinality() > 1 and element.get_type() != 'string':
                arr = '[]'

            if just_types:
                param.append('{0}{1}'.format(java_type, arr))
            else:
                param.append('{0}{1} {2}'.format(java_type, arr, name))

        return ', '.join(param)

class UidGenerator:
    uids = []

    def uid_is_unique(self, uid):
        if uid in self.uids:
            return False
        return True
    
    def get_uid_from_name(self, name):
        uidtmp = name
        if len(name) > 3:
            uidtmp = name[:3]
        uid = uidtmp
        count = 2
        while (not self.uid_is_unique(uid)):
            uid = uidtmp + str(count)
            count += 1            
        self.uids.append(uid)
        return uid

java_type = {
    'int8':   'byte',
    'uint8':  'short',
    'int16':  'short',
    'uint16': 'int',
    'int32':  'int',
    'uint32': 'long',
    'int64':  'long',
    'uint64': 'long',
    'float':  'float',
    'bool':   'boolean',
    'char':   'char',
    'string': 'String'
}

def get_java_type(type):
    return java_type[type]

random_value_function = {
    'int8':   'Utils.getRandomByte()',
    'uint8':  'Utils.getRandomShort()',
    'int16':  'Utils.getRandomShort()',
    'uint16': 'Utils.getRandomInt()',
    'int32':  'Utils.getRandomInt()',
    'uint32': 'Utils.getRandomLong()',
    'int64':  'Utils.getRandomLong()',
    'uint64': 'Utils.getRandomLong()',
    'float':  'Utils.getRandomFloat()',
    'bool':   'Utils.getRandomBoolean()',
    'char':   'Utils.getRandomChar()',
    'string': 'Utils.getRandomString()'
}

random_value_function_4test = {
    'int8':   'TestTools.getRandomByte()',
    'uint8':  'TestTools.getRandomShort()',
    'int16':  'TestTools.getRandomShort()',
    'uint16': 'TestTools.getRandomInt()',
    'int32':  'TestTools.getRandomInt()',
    'uint32': 'TestTools.getRandomLong()',
    'int64':  'TestTools.getRandomLong()',
    'uint64': 'TestTools.getRandomLong()',
    'float':  'TestTools.getRandomFloat()',
    'bool':   'TestTools.getRandomBoolean()',
    'char':   'TestTools.getRandomChar()',
    'string': 'TestTools.getRandomString()'
}


def get_random_value_function(datatype):
    return random_value_function[datatype]

def get_random_value_function_4test(datatype):
    return random_value_function_4test[datatype]

class JavaElement(common.Element):
    java_byte_buffer_method_suffix = {
        'int8':   '',
        'uint8':  '',
        'int16':  'Short',
        'uint16': 'Short',
        'int32':  'Int',
        'uint32': 'Int',
        'int64':  'Long',
        'uint64': 'Long',
        'float':  'Float',
        'bool':   '',
        'char':   '',
        'string': ''
    }

    java_byte_buffer_storage_type = {
        'int8':   'byte',
        'uint8':  'byte',
        'int16':  'short',
        'uint16': 'short',
        'int32':  'int',
        'uint32': 'int',
        'int64':  'long',
        'uint64': 'long',
        'float':  'float',
        'bool':   'byte',
        'char':   'byte',
        'string': 'byte'
    }

    def get_random_value_function(self):
        return get_random_value_function(self.get_type())
    
    def get_java_type(self):
        return get_java_type(self.get_type())

    def get_java_byte_buffer_method_suffix(self):
        return JavaElement.java_byte_buffer_method_suffix[self.get_type()]

    def get_java_byte_buffer_storage_type(self):
        return JavaElement.java_byte_buffer_storage_type[self.get_type()]
