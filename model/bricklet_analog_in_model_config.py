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

mod['getVoltage'] = {
            'field': 'voltage',
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

mod['getRange'] = {
            'field': 'range',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getVoltageCallbackThreshold'] = {
            'field': 'voltageCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAveraging'] = {
            'field': 'averaging',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getVoltageCallbackPeriod'] = {
            'field': 'voltageCallbackPeriod',
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

mod['setVoltageCallbackPeriod'] = {
            'field': 'voltageCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setAnalogValueCallbackPeriod'] = {
            'field': 'analogValueCallbackPeriod',
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

mod['setVoltageCallbackThreshold'] = {
            'field': 'voltageCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setAveraging'] = {
            'field': 'averaging',
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

mod['setRange'] = {
            'field': 'range',
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
        
callbacks['voltageReached'] = {
            'field': 'voltageReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['voltage'] = {
            'field': 'voltage',
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
        
sensor_fields['getVoltage'] = {
            'value_type': 'number',
            'field': 'voltage',
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
        
actor_fields['getRange'] = {
            'value_type': 'number',
            'field': 'range',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getVoltageCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'voltageCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAveraging'] = {
            'value_type': 'number',
            'field': 'averaging',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getVoltageCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'voltageCallbackPeriod',
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
        
other_fields['getIdentity'] = {
            'field': 'identity',
            'skip': False
            }
