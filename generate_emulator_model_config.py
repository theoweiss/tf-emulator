#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Java Emulator Model Generator
Copyright (C) 2015 Theo Weiß <theo@m1theo.org>

generate_emulator_bindings.py: Generator for Emulator bindings

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

sys.path.append(os.path.split(os.getcwd())[0])
import common
import emulator_common

class JavaBindingsDevice(emulator_common.JavaDevice):

    def get_model_header(self):
        header = """# -*- coding: utf-8 -*-

# Redistribution and use in source and binary forms of this file,
# with or without modification, are permitted. See the Creative
# Commons Zero (CC0 1.0) License for more details.

mod = {}
sensor_fields = {}
actor_fields = {}
other_actor_fields = {}
other_sensors = {}
special_fields = {}
other_fields = {}
callbacks = {}
enabled_fields = {}
debounce_period_fields = {}
threshold_fields = {}
"""
        return header

    def get_field_template(self, data_type):
        # field_category is sensor_fields or actor_fields or other_fields
        number_field = """
{field_category}['{name}'] = {{
            'value_type': 'number',
            'field': '{field}',
            'field_type': {field_type},
            'field_type_cardinality': {field_type_cardinality},
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }}
        """
        char_field = """
{field_category}['{name}'] = {{
            'field': '{field}',
            'field_type': {field_type},
            'field_type_cardinality': {field_type_cardinality},
            'default_value': 'a',
            'allowed_values': 'a-Z',
            'skip': False
            }}
"""
        bool_field = """
{field_category}['{name}'] = {{
            'field': '{field}',
            'field_type': {field_type},
            'field_type_cardinality': {field_type_cardinality},
            'default_value': False,
            'skip': False
            }}
"""
        identity_field = """
{field_category}['{name}'] = {{
            'field': '{field}',
            'skip': False
            }}
"""
        threshold_field = """
{field_category}['{name}'] = {{
            'value_type': 'threshold_buffer',
            'field': '{field}',
            'field_type': {field_type},
            'field_type_cardinality': {field_type_cardinality},
            'default_value': 'x00',
            'max_value': 1000,
            'min_value': 0,
            'skip': False
        }}
        """

        debounce_period_field = """
{field_category}['{name}'] = {{
            'value_type': 'number',
            'field': '{field}',
            'field_type': {field_type},
            'field_type_cardinality': {field_type_cardinality},
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }}
        """

        if data_type in ('char', 'string'):
            return char_field
        elif data_type == 'bool':
            return bool_field
        elif data_type == 'identity':
            return identity_field
        elif data_type == 'threshold':
            return threshold_field
        elif data_type == 'debounce_period':
            return debounce_period_field
        else:
            return number_field

    def get_model(self):
        """
        for numbers:
        mod[<func_name>] = {
                            'value_type': 'number|char|boolean',
                            'field': '<fieldname>',
                            'field_type': 'uint8|uint16|char|...',
                            'subdevice_type': 'sensor|actuator|((identity|other?)',
                            'function_type': 'getter|setter|callback_setter',
                            'default_value': <defaultvalue>',
                            'max_value': <maxvalue>,
                            'min_value': <minvalue>,
                            'step_value': <step>
                            }
        for strings or char:
        mod[<func_name>] = {
                            'field': '<fieldname>',
                            'field_type': 'uint8|uint16|char|...',
                            'type': 'sensor|actor|identity',
                            'function_type': 'getter|setter',
                            'defaultvalue': <defaultvalue>',
                            'allowedvalues': [<allowed>,...],
                            }

        for bool:
        mod[<func_name>] = {
                            'field': '<fieldname>',
                            'field_type': 'uint8|uint16|char|...',
                            'function_type': 'getter|setter',
                            'type': 'sensor|actor|identity',
                            'defaultvalue: <defaultvalue>',
                            }

        """
        fields = ''
        functions = ''
        function = """
mod['{name}'] = {{
            'field': '{field}',
            'subdevice_type': '{subdevice_type}',
            'function_type': '{function_type}',
            'skip': False
            }}
"""
        callbacks = ''
        callback = """
callbacks['{0}'] = {{
            'field': '{1}',
            'function_type': 'callback',
            'skip': False
            }}
        """

        cls = self.get_camel_case_name()
        sensors = self.get_sensors()
    # get sensors
        for key in sensors.keys():
            field_category = 'sensor_fields'
            packet = sensors[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template(data_type)
            functions += function.format(**{
                                          'name': name_lower,
                                          'field': sensors[key][1],
                                          'subdevice_type': 'sensor',
                                          'function_type': 'getter'
                                          })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': sensors[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        actor_getters = self.get_actor_getters()
        for key in actor_getters.keys():
            field_category = 'actor_fields'
            packet = actor_getters[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template(data_type)
            functions += function.format(**{
                                          'name': name_lower,
                                          'field': actor_getters[key][1],
                                          'subdevice_type': 'actor',
                                          'function_type': 'getter'
                                          })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': actor_getters[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        isEnableds = self.get_isEnableds()
        for key in isEnableds.keys():
            field_category = 'enabled_fields'
            packet = isEnableds[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template(data_type)
            functions += function.format(**{
                                          'name': name_lower,
                                          'field': isEnableds[key][1],
                                          'subdevice_type': 'actor',
                                          'function_type': 'getter'
                                          })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': isEnableds[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        callback_threshold_getters = self.get_callback_threshold_getters()
        for key in callback_threshold_getters.keys():
            field_category = 'threshold_fields'
            packet = callback_threshold_getters[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template('threshold')
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': callback_threshold_getters[key][1],
                                     'function_type': 'callback_threshold_getter',
                                     'subdevice_type': 'actor'
                                     })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': callback_threshold_getters[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        callback_debounce_period_getters = self.get_callback_debounce_period_getters()
        for key in callback_debounce_period_getters.keys():
            field_category = 'debounce_period_fields'
            packet = callback_debounce_period_getters[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template('debounce_period')
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': callback_debounce_period_getters[key][1],
                                     'function_type': 'callback_debounce_period_getter',
                                     'subdevice_type': 'actor'
                                     })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': callback_debounce_period_getters[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        callback_period_getters = self.get_callback_period_getters()
        for key in callback_period_getters.keys():
            packet = callback_period_getters[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': callback_period_getters[key][1],
                                     'function_type': 'callback_period_getter',
                                     'subdevice_type': 'actor'
                                     })
        
        callback_period_setters = self.get_callback_period_setters()
        for key in callback_period_setters.keys():
            packet = callback_period_setters[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': callback_period_setters[key][1],
                                     'function_type': 'callback_period_setter',
                                     'subdevice_type': 'actor'
                                     })
        
        callback_threshold_setters = self.get_callback_threshold_setters()
        for key in callback_threshold_setters.keys():
            packet = callback_threshold_setters[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': callback_threshold_setters[key][1],
                                     'function_type': 'callback_threshold_setter',
                                     'subdevice_type': 'actor'
                                     })
        
        callback_debounce_period_setters = self.get_callback_debounce_period_setters()
        for key in callback_debounce_period_setters.keys():
            packet = callback_debounce_period_setters[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': callback_debounce_period_setters[key][1],
                                     'function_type': 'callback_debounce_period_setter',
                                     'subdevice_type': 'actor'
                                     })
        
        
        actuator_setters = self.get_actors()
        for key in actuator_setters.keys():
            packet = actuator_setters[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': actuator_setters[key][1],
                                     'function_type': 'actuator_setter',
                                     'subdevice_type': 'actor'
                                     })

        enablers = self.get_enablers()
        for key in enablers.keys():
            packet = enablers[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': enablers[key][1],
                                     'function_type': 'enabler',
                                     'subdevice_type': 'actor'
                                     })

        disablers = self.get_disablers()
        for key in disablers.keys():
            packet = disablers[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': disablers[key][1],
                                     'function_type': 'disablers',
                                     'subdevice_type': 'actor'
                                     })

        identity = self.get_getIdentity()
        for key in identity.keys():
            field_category = 'other_fields'
            packet = identity[key]
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template('identity')
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': 'identity',
                                     'function_type': 'getter',
                                     'subdevice_type': 'identity'
                                     })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': 'identity'
                                             })
        
        other_actors = self.get_otherActorMethods()
        for key in other_actors:
            field_category = 'other_actor_fields'
            packet = other_actors[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template(data_type)
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': other_actors[key][1],
                                     'subdevice_type': 'actuator',
                                     'function_type': 'getter'
                                     })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': other_actors[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        other_sensors = self.get_otherSensorMethods()
        for key in other_sensors:
            field_category = 'other_sensors'
            packet = other_sensors[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template(data_type)
            functions += function.format(**{
                                     'name': name_lower,
                                     'field': other_sensors[key][1],
                                     'subdevice_type': 'sensor',
                                     'function_type': 'getter'
                                     })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': other_sensors[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        special_methods = self.get_special_setterMethods()
        for key in special_methods:
            field_category = 'special_fields'
            packet = special_methods[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template(data_type)
            functions += function.format(**{
                                          'name': name_lower,
                                          'field': special_methods[key][1],
                                          'subdevice_type': 'actuator',
                                          'function_type': 'setter'
                                          })
            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': special_methods[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        others = self.get_otherMethods()
        for key in others:
            field_category = 'other_fields'
            packet = others[key][0]
            data_type, cardinality = packet.get_getter_data_type()
            name_lower = packet.get_headless_camel_case_name()
            field_template = self.get_field_template(data_type)
            functions += function.format(**{
                                          'name': name_lower,
                                          'field': others[key][1],
                                          'subdevice_type': 'other',
                                          'function_type': 'setter'
                                          })

            fields += field_template.format(**{
                                             'field_category': field_category,
                                             'name': name_lower,
                                             'field': others[key][1],
                                             'field_type': data_type,
                                             'field_type_cardinality': cardinality
                                             })

        callback_methods = self.get_callbacks()
        for key in callback_methods:
            packet = callback_methods[key][0]
            name_lower = packet.get_headless_camel_case_name()
            callbacks += callback.format(name_lower, callback_methods[key][1])

        return functions, fields, callbacks


    def get_callbacks(self):
        callbacks = {}
        for packet in self.get_packets('callback'):
            name_lower = packet.get_headless_camel_case_name()
            callbacks[name_lower] = (packet, name_lower)
        return callbacks

    def get_modelconfig(self):
        functions, fields, callbacks = self.get_model()
        modelconfig = ''
        modelconfig += self.get_model_header()
        modelconfig += functions
        modelconfig += callbacks
        modelconfig += fields
        return modelconfig

class JavaBindingsPacket(emulator_common.JavaPacket):

    def get_getter_data_type(self):
        types = []
        cardinality = []
        for element in self.get_elements('out'):
            types.append(element.get_type())
            cardinality.append(element.get_cardinality())
        return types, cardinality

    def get_setter_data_type(self):
        types = []
        cardinality = []
        for element in self.get_elements('in'):
            types.append(element.get_type())
            cardinality.append(element.get_cardinality())
        return types, cardinality


class JavaBindingsGenerator(common.BindingsGenerator):
    released_files_name_prefix = 'generator_model_config'
    modelsubdir = 'model'

    def prepare(self):
        common.recreate_directory(os.path.join(self.get_bindings_root_directory(), self.modelsubdir))

    def get_bindings_name(self):
        return 'emulator'

    def get_device_class(self):
        return JavaBindingsDevice

    def get_packet_class(self):
        return JavaBindingsPacket

    def get_element_class(self):
        return emulator_common.JavaElement

    def generate(self, device):
        filename = '{0}_model_config.py'.format(device.get_underscore_device_name())

        modelconfig = open(os.path.join(self.get_bindings_root_directory(), self.modelsubdir, filename), 'wb')
        modelconfig.write(device.get_modelconfig())
        modelconfig.close()

def generate(bindings_root_directory):
    common.generate(bindings_root_directory, 'en', JavaBindingsGenerator)

if __name__ == "__main__":
    generate(os.getcwd())
