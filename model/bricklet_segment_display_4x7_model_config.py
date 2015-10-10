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


mod['getCounterValue'] = {
            'field': 'counterValue',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getSegments'] = {
            'field': 'segments',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setSegments'] = {
            'field': 'segments',
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

mod['startCounter'] = {
            'field': 'startCounter',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

callbacks['counterFinished'] = {
            'field': 'counterFinished',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getCounterValue'] = {
            'value_type': 'number',
            'field': 'counterValue',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getSegments'] = {
            'value_type': 'number',
            'field': 'segments',
            'field_type': ['uint8', 'uint8', 'bool'],
            'field_type_cardinality': [4, 1, 1],
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

other_actor_fields['startCounter'] = {
            'value_type': 'number',
            'field': 'startCounter',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        