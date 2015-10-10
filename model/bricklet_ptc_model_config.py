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

mod['getResistance'] = {
            'field': 'resistance',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWireMode'] = {
            'field': 'wireMode',
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

mod['getResistanceCallbackThreshold'] = {
            'field': 'resistanceCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getNoiseRejectionFilter'] = {
            'field': 'noiseRejectionFilter',
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

mod['getTemperatureCallbackPeriod'] = {
            'field': 'temperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getResistanceCallbackPeriod'] = {
            'field': 'resistanceCallbackPeriod',
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

mod['setResistanceCallbackPeriod'] = {
            'field': 'resistanceCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setResistanceCallbackThreshold'] = {
            'field': 'resistanceCallbackThreshold',
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

mod['setWireMode'] = {
            'field': 'wireMode',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setNoiseRejectionFilter'] = {
            'field': 'noiseRejectionFilter',
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

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['isSensorConnected'] = {
            'field': 'isSensorConnected',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

callbacks['resistanceReached'] = {
            'field': 'resistanceReached',
            'function_type': 'callback',
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
        
callbacks['resistance'] = {
            'field': 'resistance',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getTemperature'] = {
            'value_type': 'number',
            'field': 'temperature',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getResistance'] = {
            'value_type': 'number',
            'field': 'resistance',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWireMode'] = {
            'value_type': 'number',
            'field': 'wireMode',
            'field_type': ['uint8'],
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
            'field_type': ['char', 'int32', 'int32'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getResistanceCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'resistanceCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getNoiseRejectionFilter'] = {
            'value_type': 'number',
            'field': 'noiseRejectionFilter',
            'field_type': ['uint8'],
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
        
actor_fields['getResistanceCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'resistanceCallbackPeriod',
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

other_sensors['isSensorConnected'] = {
            'value_type': 'number',
            'field': 'isSensorConnected',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        