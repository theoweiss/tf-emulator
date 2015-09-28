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
        self.callbacks = {}

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
        callbacks = copy.deepcopy(__import__(self.get_underscore_device_name() + '_model_config').callbacks)
        self.callbacks = callbacks
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
            packet_data = callbacks[packet.get_headless_camel_case_name()]
            packet.field = packet_data['field']
    def get_sensors(self):
        return self.get_method_categories()[0]

    def get_actors(self):
        return  self.get_method_categories()[1]

    def get_actor_getters(self):
        return  self.get_method_categories()[2]

    def get_callback_setters(self):
        return self.get_method_categories()[3]

    def get_callback_getters(self):
        return self.get_method_categories()[4]

    def get_enablers(self):
        return self.get_method_categories()[5]

    def get_disablers(self):
        return self.get_method_categories()[6]

    def get_isEnableds(self):
        return self.get_method_categories()[7]

    def get_getIdentity(self):
        return self.get_method_categories()[8]

    def get_otherActorMethods(self):
        return self.get_method_categories()[9]

    def get_otherSensorMethods(self):
        return self.get_method_categories()[10]

    def get_special_setterMethods(self):
        return self.get_method_categories()[11]

    def get_otherMethods(self):
        return self.get_method_categories()[12]

    def get_booleans(self):
        return self.get_method_categories()[13]

    def get_callback_period_setters(self):
        return self.get_method_categories()[14]

    def get_callback_period_getters(self):
        return self.get_method_categories()[15]

    def get_method_categories(self):
        def down_case_first(name):
            return name[0].lower() + name[1:]
    
        # some heuristics on method names
        cls = self.get_camel_case_name()
        setter = {}
        getter = {}
        sensors = {}
        actors = {}
        actor_getters = {}
        callback_period_setters = {}
        callback_period_getters = {}
        callback_setters = {}
        callback_getters = {}
        enablers = {}
        disablers = {}
        isEnableds = {}
        booleans = {} # key is the name, value is a tuple (packet, enabler name, disabler name, isEnabled name
        getIdentity = {}
        other_actors = {}
        other_sensors = {}
        special_setters = {}
        others = {}
        special_setter_methods = {'setSelectedState': 'state',
                                  'setSelectedLEDState':'lEDState',
                                  'setEthernetHostname': 'ethernetConfiguration',
                                  'setEthernetMACAddress': 'ethernetConfiguration',
                                  'setEthernetHostname': 'ethernetConfiguration',
                                  'isSyncRect': 'syncRect',
                                  'setSyncRect': 'syncRect',
                                  'setSelectedValues': 'values'}
        other_boolean_methods = {('ledOn', 'ledOff', 'isLEDOn'): 'led', 
                                 ('ledsOn', 'ledsOff', 'areLedsOn'): 'leds', 
                                 ('lightOn', 'lightOff', 'isLightOn'): 'light', 
                                 ('backlightOn', 'backlightOff', 'isBacklightOn'): 'backlight'
                                 }
        other_actor_methods = ('fullBrake', 'reset', 'calibrate', 'tare', 'recalibrate', 'startCounter', 'fullBrake',
                               'driveForward', 'driveBackward', 'stop', 'morseCode', 'beep', 'saveCalibration',
                               'clearDisplay', 'writeLine', 'setSyncRect')
        other_sensor_methods = ('isOverCurrent', 'isSensorConnected', 'isChibiPresent', 'isButtonPressed', 
                                'isEthernetPresent')
        for packet in self.get_packets('function'):
            name_lower = packet.get_headless_camel_case_name()
            #print "name_lower " + name_lower
            if name_lower in special_setter_methods.keys():
                print "special setter method : " + name_lower
                name = name_lower
                special_setters[name_lower] = (packet, name)
            elif name_lower.startswith("set"):
                setter[name_lower[3:]] = (name_lower, packet)
            elif name_lower.startswith("get"):
                getter[name_lower[3:]] = (name_lower, packet)
            elif name_lower.startswith("enable"):
                if name_lower == "enable":
                    name = "enabled"
                else:
                    name = name_lower[6:]
                enablers[name_lower] = (packet, name)
                booleans[name] = []
                booleans[name].insert(1, name_lower)
            elif name_lower.startswith("disable"):
                if name_lower == "disable":
                    name = "enabled"
                else:
                    name = name_lower[7:]
                disablers[name_lower] = (packet, name)
                booleans[name].insert(2, name_lower)
            elif name_lower.startswith("is") and name_lower.endswith("Enabled"):
                if name_lower == "isEnabled":
                    name = "enabled"
                else:
                    name = name_lower[2:-7]
                isEnableds[name_lower] = (packet, name)
                booleans[name].insert(0, packet)
                booleans[name].insert(3, name_lower)
            else:
                if name_lower in other_actor_methods:
                    print "other actor method: " + name_lower
                    name = name_lower
                    other_actors[name_lower] = (packet, name)
                    continue
                elif name_lower in other_sensor_methods:
                    print "other actor sensor: " + name_lower
                    name = name_lower
                    other_sensors[name_lower] = (packet, name)
                    continue
                else:
                    found = False
                    for keys, name in other_boolean_methods.items(): 
                        if name_lower in keys:
                            if keys.index(name_lower) == 0:
                                enablers[name_lower] = (packet, name)
                                booleans[name] = []
                                booleans[name].insert(1, name_lower)
                                found = True
                                continue
                            elif keys.index(name_lower) == 1:
                                disablers[name_lower] = (packet, name)
                                booleans[name].insert(2, name_lower)
                                found = True
                                continue
                            else:
                                isEnableds[name_lower] = (packet, name)
                                booleans[name].insert(0, packet)
                                booleans[name].insert(3, name_lower)
                                found = True
                                continue
                
                    # we should not reach this point
                    if not found:
                        print "other unknown method: " + name_lower
                        name = name_lower
                        others[name_lower] = (packet, name)

        for name in getter.keys():
            if name not in setter:
                if name == "Identity":
                    getIdentity[getter.get(name)[0]] = getter.get(name)[1]
                elif name != "Protocol1BrickletName":
                    print "sensor " + name
                    # sensors is a list of function names
                    # TODO: hier muss auch noch das packet dazu das brauch ich später noch
                    sensors[getter.get(name)[0]] = (getter.get(name)[1], down_case_first(name))
            else:
                if "CallbackPeriod" in name:
                    setter_packet = setter.get(name)[1]
                    getter_packet = getter.get(name)[1]
                    callback_period_setters[setter.get(name)[0]] = (setter_packet, down_case_first(name))
                    callback_period_getters[getter.get(name)[0]] = (getter_packet, down_case_first(name))
                elif "CallbackThreshold" in name or "DebouncePeriod" in name:
                    callback_setters[setter.get(name)[0]] = (setter.get(name)[1], down_case_first(name))
                    callback_getters[getter.get(name)[0]] = (getter.get(name)[1], down_case_first(name))
                else:
                    print "actor: " + name
                    # function names as keys, property name as value
                    # e.g. {"setProperty": "Property",
                    #       "getProperty": "Property"} 
                    actors[setter.get(name)[0]] = (setter.get(name)[1], down_case_first(name))
                    actor_getters[getter.get(name)[0]] = (getter.get(name)[1], down_case_first(name))
        return (sensors, actors, actor_getters, callback_setters, callback_getters, enablers, disablers, 
                isEnableds, getIdentity, other_actors, other_sensors, special_setters, others, booleans,
                callback_period_setters, callback_period_getters)


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

extract_value_function = {
                          'uint8': 'Utils.unsignedByte({0})',
                          'uint16': 'Utils.unsignedShort({0})',
                          'uint32': 'Utils.unsignedInt({0})',
                          'int32': 'Utils.int({0})',
                          'int64': 'Utils.long({0})',
                          'uint64': 'Utils.long({0})',
                          'float': 'Utils.float({0})',
                          'bool': 'Utils.bool({0})',
                          'char': 'Utils.char({0})',
                          'string': 'Utils.string({0})'
                          }
def get_extract_value_function(value_type):
    return extract_value_function[value_type]

def get_random_value_function(datatype):
    return random_value_function[datatype]

def get_random_value_function_4test(datatype):
    return random_value_function_4test[datatype]

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

def get_java_byte_buffer_storage_type(type_name):
    return java_byte_buffer_storage_type[type_name]

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


    def get_random_value_function(self):
        return get_random_value_function(self.get_type())
    
    def get_java_type(self):
        return get_java_type(self.get_type())

    def get_java_byte_buffer_method_suffix(self):
        return JavaElement.java_byte_buffer_method_suffix[self.get_type()]

    def get_java_byte_buffer_storage_type(self):
        return java_byte_buffer_storage_type[self.get_type()]
