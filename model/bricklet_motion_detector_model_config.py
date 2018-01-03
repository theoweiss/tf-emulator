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
enabled_fields = {}
debounce_period_fields = {}
threshold_fields = {}

mod['getMotionDetected'] = {
            'field': 'motionDetected',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

callbacks['detectionCycleEnded'] = {
            'field': 'detectionCycleEnded',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['motionDetected'] = {
            'field': 'motionDetected',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getMotionDetected'] = {
            'value_type': 'number',
            'field': 'motionDetected',
            'field_type': ['uint8'],
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
