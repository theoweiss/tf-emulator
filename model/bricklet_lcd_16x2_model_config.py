# -*- coding: utf-8 -*-

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


mod['getCustomCharacter'] = {
            'field': 'customCharacter',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isBacklightOn'] = {
            'field': 'backlight',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getConfig'] = {
            'field': 'config',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['backlightOn'] = {
            'field': 'backlight',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['backlightOff'] = {
            'field': 'backlight',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setConfig'] = {
            'field': 'config',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setCustomCharacter'] = {
            'field': 'customCharacter',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['clearDisplay'] = {
            'field': 'clearDisplay',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['writeLine'] = {
            'field': 'writeLine',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['isButtonPressed'] = {
            'field': 'isButtonPressed',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

callbacks['buttonReleased'] = {
            'field': 'buttonReleased',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['buttonPressed'] = {
            'field': 'buttonPressed',
            'function_type': 'callback',
            'skip': False
            }
        
actor_fields['getCustomCharacter'] = {
            'value_type': 'number',
            'field': 'customCharacter',
            'field_type': ['uint8'],
            'field_type_cardinality': [8],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isBacklightOn'] = {
            'value_type': 'number',
            'field': 'backlight',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getConfig'] = {
            'value_type': 'number',
            'field': 'config',
            'field_type': ['bool', 'bool'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['getIdentity'] = {
            'field': 'identity',
            'skip': False
            }

other_actor_fields['clearDisplay'] = {
            'value_type': 'number',
            'field': 'clearDisplay',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_actor_fields['writeLine'] = {
            'value_type': 'number',
            'field': 'writeLine',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_sensors['isButtonPressed'] = {
            'value_type': 'number',
            'field': 'isButtonPressed',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        