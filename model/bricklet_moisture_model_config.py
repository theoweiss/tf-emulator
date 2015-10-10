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


mod['getMoistureValue'] = {
            'field': 'moistureValue',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMoistureCallbackThreshold'] = {
            'field': 'moistureCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMoistureCallbackPeriod'] = {
            'field': 'moistureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMovingAverage'] = {
            'field': 'movingAverage',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setMoistureCallbackPeriod'] = {
            'field': 'moistureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setMoistureCallbackThreshold'] = {
            'field': 'moistureCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setMovingAverage'] = {
            'field': 'movingAverage',
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

callbacks['moisture'] = {
            'field': 'moisture',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['moistureReached'] = {
            'field': 'moistureReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getMoistureValue'] = {
            'value_type': 'number',
            'field': 'moistureValue',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMoistureCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'moistureCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMoistureCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'moistureCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDebouncePeriod'] = {
            'value_type': 'number',
            'field': 'debouncePeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMovingAverage'] = {
            'value_type': 'number',
            'field': 'movingAverage',
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
