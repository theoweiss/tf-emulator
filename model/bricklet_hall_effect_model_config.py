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


mod['getEdgeCount'] = {
            'field': 'edgeCount',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getValue'] = {
            'field': 'value',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEdgeCountConfig'] = {
            'field': 'edgeCountConfig',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEdgeCountCallbackPeriod'] = {
            'field': 'edgeCountCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEdgeInterrupt'] = {
            'field': 'edgeInterrupt',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setEdgeCountCallbackPeriod'] = {
            'field': 'edgeCountCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setEdgeInterrupt'] = {
            'field': 'edgeInterrupt',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setEdgeCountConfig'] = {
            'field': 'edgeCountConfig',
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

mod['edgeInterrupt'] = {
            'field': 'edgeInterrupt',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['edgeCount'] = {
            'field': 'edgeCount',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getEdgeCount'] = {
            'value_type': 'number',
            'field': 'edgeCount',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getValue'] = {
            'value_type': 'number',
            'field': 'value',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getEdgeCountConfig'] = {
            'value_type': 'number',
            'field': 'edgeCountConfig',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getEdgeCountCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'edgeCountCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getEdgeInterrupt'] = {
            'value_type': 'number',
            'field': 'edgeInterrupt',
            'field_type': ['uint32'],
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

other_fields['edgeInterrupt'] = {
            'value_type': 'number',
            'field': 'edgeInterrupt',
            'field_type': ['uint32', 'bool'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        