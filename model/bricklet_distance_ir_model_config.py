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


mod['getAnalogValue'] = {
            'field': 'analogValue',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDistance'] = {
            'field': 'distance',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAnalogValueCallbackPeriod'] = {
            'field': 'analogValueCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDistanceCallbackPeriod'] = {
            'field': 'distanceCallbackPeriod',
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

mod['getAnalogValueCallbackThreshold'] = {
            'field': 'analogValueCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDistanceCallbackThreshold'] = {
            'field': 'distanceCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getSamplingPoint'] = {
            'field': 'samplingPoint',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setAnalogValueCallbackPeriod'] = {
            'field': 'analogValueCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setDistanceCallbackPeriod'] = {
            'field': 'distanceCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setAnalogValueCallbackThreshold'] = {
            'field': 'analogValueCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setDistanceCallbackThreshold'] = {
            'field': 'distanceCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setSamplingPoint'] = {
            'field': 'samplingPoint',
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

callbacks['analogValue'] = {
            'field': 'analogValue',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['distance'] = {
            'field': 'distance',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['analogValueReached'] = {
            'field': 'analogValueReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['distanceReached'] = {
            'field': 'distanceReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getAnalogValue'] = {
            'value_type': 'number',
            'field': 'analogValue',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getDistance'] = {
            'value_type': 'number',
            'field': 'distance',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAnalogValueCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'analogValueCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDistanceCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'distanceCallbackPeriod',
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
        
actor_fields['getAnalogValueCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'analogValueCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDistanceCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'distanceCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getSamplingPoint'] = {
            'value_type': 'number',
            'field': 'samplingPoint',
            'field_type': ['uint16'],
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
