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


mod['getIlluminance'] = {
            'field': 'illuminance',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getIlluminanceCallbackPeriod'] = {
            'field': 'illuminanceCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getConfiguration'] = {
            'field': 'configuration',
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

mod['getIlluminanceCallbackThreshold'] = {
            'field': 'illuminanceCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setIlluminanceCallbackPeriod'] = {
            'field': 'illuminanceCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setIlluminanceCallbackThreshold'] = {
            'field': 'illuminanceCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setConfiguration'] = {
            'field': 'configuration',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setDebouncePeriod'] = {
            'field': 'debouncePeriod',
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

callbacks['illuminance'] = {
            'field': 'illuminance',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['illuminanceReached'] = {
            'field': 'illuminanceReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getIlluminance'] = {
            'value_type': 'number',
            'field': 'illuminance',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getIlluminanceCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'illuminanceCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getConfiguration'] = {
            'value_type': 'number',
            'field': 'configuration',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
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
        
actor_fields['getIlluminanceCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'illuminanceCallbackThreshold',
            'field_type': ['char', 'uint32', 'uint32'],
            'field_type_cardinality': [1, 1, 1],
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
