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


mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['beep'] = {
            'field': 'beep',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['morseCode'] = {
            'field': 'morseCode',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['calibrate'] = {
            'field': 'calibrate',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

callbacks['beepFinished'] = {
            'field': 'beepFinished',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['morseCodeFinished'] = {
            'field': 'morseCodeFinished',
            'function_type': 'callback',
            'skip': False
            }
        
other_fields['getIdentity'] = {
            'field': 'identity',
            'skip': False
            }

other_actor_fields['beep'] = {
            'value_type': 'number',
            'field': 'beep',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_actor_fields['morseCode'] = {
            'value_type': 'number',
            'field': 'morseCode',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_actor_fields['calibrate'] = {
            'value_type': 'number',
            'field': 'calibrate',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        