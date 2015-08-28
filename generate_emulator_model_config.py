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

# DC Brick communication config

mod = {}
"""
        return header

    def get_model(self):
        """
        for numbers:
        mod[<func_name>] = {
                            "field": "<fieldname>",
                            "field_type": "uint8|uint16|char|...",
                            "type": "sensor|actor|identity",
                            "function_type": "getter|setter|callback",
                            "defaultvalue": <defaultvalue>",
                            "maxvalue": <maxvalue>,
                            "minvalue": <minvalue>,
                            "step": <step>
                            }
        for strings or char:
        mod[<func_name>] = {
                            "field": "<fieldname>",
                            "field_type": "uint8|uint16|char|...",
                            "type": "sensor|actor|identity",
                            "function_type": "getter|setter",
                            "defaultvalue": <defaultvalue>",
                            "allowedvalues": [<allowed>,...],
                            }

        for boolean:
        mod[<func_name>] = {
                            "field": "<fieldname>",
                            "field_type": "uint8|uint16|char|...",
                            "function_type": "getter|setter",
                            "type": "sensor|actor|identity",
                            "defaultvalue: <defaultvalue>",
                            }

        """
        functions = ''
        numberfunction = """
mod['{0}'] = {{
            "field": "{1}",
            "field_type": "{2}",
            "type": "{3}",
            "function_type": "{4}",
            "defaultvalue": 100,
            "maxvalue": 0,
            "minvalue": 1000,
            "step": 1
            }}
"""

        charfunction = """
mod['{0}'] = {
            "field": "<fieldname>",
            "field_type": "uint8|uint16|char|...",
            "type": "sensor|actor|identity",
            "function_type": "getter|setter",
            "defaultvalue": <defaultvalue>",
            "allowedvalues": [<allowed>,...],
            }
"""
        boolfunction = """
mod['{0}'] = {
            "field": "<fieldname>",
            "field_type": "uint8|uint16|char|...",
            "type": "sensor|actor|identity",
            "function_type": "getter|setter",
            "defaultvalue": <defaultvalue>",
            }
"""

        cls = self.get_camel_case_name()
        sensors = self.get_sensors()
    # get sensors
        for key in sensors.keys():
            packet = sensors[key][0]
            name_lower = packet.get_headless_camel_case_name()
            print "sensors key1: " + sensors[key][1]
            functions += numberfunction.format(
                                     name_lower,
                                     sensors[key][1],
                                     packet.get_getter_data_type(),
                                     'sensor',
                                     'getter')

        actor_getters = {}
        actor_getters.update(self.get_actor_getters())
        actor_getters.update(self.get_callback_getters())
        actor_getters.update(self.get_isEnableds())
        for key in actor_getters.keys():
            packet = actor_getters[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += numberfunction.format(
                                     name_lower,
                                     actor_getters[key][1],
                                     packet.get_getter_data_type(),
                                     "getter",
                                     "actor")

        setter = {}
        setter.update(self.get_actors())
        setter.update(self.get_callback_setters())
        setter.update(self.get_enablers())
        setter.update(self.get_disablers())
        for key in setter.keys():
            packet = setter[key][0]
            name_lower = packet.get_headless_camel_case_name()
            functions += numberfunction.format(
                                     name_lower,
                                     setter[key][1],
                                     packet.get_setter_data_type(),
                                     "setter",
                                     "actor")

        identity = self.get_getIdentity()
        for key in identity.keys():
            packet = identity[key]
            name_lower = packet.get_headless_camel_case_name()
            functions += numberfunction.format(
                                     name_lower,
                                     "identity",
                                     "identity type",
                                     "getter",
                                     "identity")

        return functions

    def get_model_others(self):
        methods = ''
        method = """
  /**
   * 
   */
  private Buffer {0}(Packet packet) {{
    //TODO dummy method
    return null;
  }}
"""
        other_actors = self.get_otherActorMethods()
        for key in other_actors:
            packet = other_actors[key][0]
            name_lower = packet.get_headless_camel_case_name()
            methods += method.format(name_lower)

        other_sensors = self.get_otherSensorMethods()
        for key in other_sensors:
            packet = other_sensors[key][0]
            name_lower = packet.get_headless_camel_case_name()
            methods += method.format(name_lower)

        special_methods = self.get_special_setterMethods()
        for key in special_methods:
            packet = special_methods[key][0]
            name_lower = packet.get_headless_camel_case_name()
            methods += method.format(name_lower)

        others = self.get_otherMethods()
        for key in others:
            packet = others[key][0]
            name_lower = packet.get_headless_camel_case_name()
            methods += method.format(name_lower)

        return methods

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
                if "CallbackPeriod" in name or "CallbackThreshold" in name or "DebouncePeriod" in name:
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
                isEnableds, getIdentity, other_actors, other_sensors, special_setters, others, booleans)

    def get_modelconfig(self):
        modelconfig = ''
        modelconfig += self.get_model_header()
        modelconfig += self.get_model()
        return modelconfig

class JavaBindingsPacket(emulator_common.JavaPacket):


    def get_getter_data_type(self):
        types = ""
        for element in self.get_elements('out'):
            types += "{0} * {1}; ".format(element.get_type(), element.get_cardinality())
        return types

    def get_setter_data_type(self):
        types = ""
        for element in self.get_elements('in'):
            types += "{0} * {1}; ".format(element.get_type(), element.get_cardinality())
        return types


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
        filename = '{0}_config.py'.format(device.get_underscore_device_name())

        modelconfig = open(os.path.join(self.get_bindings_root_directory(), self.modelsubdir, filename), 'wb')
        modelconfig.write(device.get_modelconfig())
        modelconfig.close()

def generate(bindings_root_directory):
    common.generate(bindings_root_directory, 'en', JavaBindingsGenerator)

if __name__ == "__main__":
    generate(os.getcwd())
