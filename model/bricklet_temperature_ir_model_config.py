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


mod['getObjectTemperature'] = {
            'field': 'objectTemperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAmbientTemperature'] = {
            'field': 'ambientTemperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAmbientTemperatureCallbackPeriod'] = {
            'field': 'ambientTemperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getObjectTemperatureCallbackThreshold'] = {
            'field': 'objectTemperatureCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEmissivity'] = {
            'field': 'emissivity',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getObjectTemperatureCallbackPeriod'] = {
            'field': 'objectTemperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAmbientTemperatureCallbackThreshold'] = {
            'field': 'ambientTemperatureCallbackThreshold',
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

mod['setObjectTemperatureCallbackPeriod'] = {
            'field': 'objectTemperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setAmbientTemperatureCallbackPeriod'] = {
            'field': 'ambientTemperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setObjectTemperatureCallbackThreshold'] = {
            'field': 'objectTemperatureCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setEmissivity'] = {
            'field': 'emissivity',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setAmbientTemperatureCallbackThreshold'] = {
            'field': 'ambientTemperatureCallbackThreshold',
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

callbacks['objectTemperature'] = {
            'field': 'objectTemperature',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['ambientTemperatureReached'] = {
            'field': 'ambientTemperatureReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['ambientTemperature'] = {
            'field': 'ambientTemperature',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['objectTemperatureReached'] = {
            'field': 'objectTemperatureReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getObjectTemperature'] = {
            'value_type': 'number',
            'field': 'objectTemperature',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getAmbientTemperature'] = {
            'value_type': 'number',
            'field': 'ambientTemperature',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAmbientTemperatureCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'ambientTemperatureCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getObjectTemperatureCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'objectTemperatureCallbackThreshold',
            'field_type': ['char', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getEmissivity'] = {
            'value_type': 'number',
            'field': 'emissivity',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getObjectTemperatureCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'objectTemperatureCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAmbientTemperatureCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'ambientTemperatureCallbackThreshold',
            'field_type': ['char', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
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
        
other_fields['getIdentity'] = {
            'field': 'identity',
            'skip': False
            }
