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


mod['getTiltState'] = {
            'field': 'tiltState',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['isTiltStateCallbackEnabled'] = {
            'field': 'TiltStateCallback',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['enableTiltStateCallback'] = {
            'field': 'TiltStateCallback',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['disableTiltStateCallback'] = {
            'field': 'TiltStateCallback',
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

callbacks['tiltState'] = {
            'field': 'tiltState',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getTiltState'] = {
            'value_type': 'number',
            'field': 'tiltState',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isTiltStateCallbackEnabled'] = {
            'value_type': 'number',
            'field': 'TiltStateCallback',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
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
