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


mod['getTemperature'] = {
            'field': 'temperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getTemperatureCallbackPeriod'] = {
            'field': 'temperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getTemperatureCallbackThreshold'] = {
            'field': 'temperatureCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getI2CMode'] = {
            'field': 'i2CMode',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setTemperatureCallbackPeriod'] = {
            'field': 'temperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setI2CMode'] = {
            'field': 'i2CMode',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setTemperatureCallbackThreshold'] = {
            'field': 'temperatureCallbackThreshold',
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

callbacks['temperatureReached'] = {
            'field': 'temperatureReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['temperature'] = {
            'field': 'temperature',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getTemperature'] = {
            'value_type': 'number',
            'field': 'temperature',
            'field_type': ['int16'],
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
        
actor_fields['getTemperatureCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'temperatureCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getTemperatureCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'temperatureCallbackThreshold',
            'field_type': ['char', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getI2CMode'] = {
            'value_type': 'number',
            'field': 'i2CMode',
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
