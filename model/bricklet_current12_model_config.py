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

mod['getCurrent'] = {
            'field': 'current',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCurrentCallbackThreshold'] = {
            'field': 'currentCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCurrentCallbackPeriod'] = {
            'field': 'currentCallbackPeriod',
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

mod['getAnalogValueCallbackPeriod'] = {
            'field': 'analogValueCallbackPeriod',
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

mod['setCurrentCallbackPeriod'] = {
            'field': 'currentCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setCurrentCallbackThreshold'] = {
            'field': 'currentCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setAnalogValueCallbackThreshold'] = {
            'field': 'analogValueCallbackThreshold',
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

mod['calibrate'] = {
            'field': 'calibrate',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['isOverCurrent'] = {
            'field': 'isOverCurrent',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

callbacks['current'] = {
            'field': 'current',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['analogValue'] = {
            'field': 'analogValue',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['overCurrent'] = {
            'field': 'overCurrent',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['currentReached'] = {
            'field': 'currentReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['analogValueReached'] = {
            'field': 'analogValueReached',
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
        
sensor_fields['getCurrent'] = {
            'value_type': 'number',
            'field': 'current',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getCurrentCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'currentCallbackThreshold',
            'field_type': ['char', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getCurrentCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'currentCallbackPeriod',
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
        
other_fields['getIdentity'] = {
            'field': 'identity',
            'skip': False
            }

other_actor_fields['calibrate'] = {
            'value_type': 'number',
            'field': 'calibrate',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_sensors['isOverCurrent'] = {
            'value_type': 'number',
            'field': 'isOverCurrent',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        