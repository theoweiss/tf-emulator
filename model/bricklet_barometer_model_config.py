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


mod['getAltitude'] = {
            'field': 'altitude',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChipTemperature'] = {
            'field': 'chipTemperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAirPressure'] = {
            'field': 'airPressure',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAirPressureCallbackPeriod'] = {
            'field': 'airPressureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAltitudeCallbackThreshold'] = {
            'field': 'altitudeCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAltitudeCallbackPeriod'] = {
            'field': 'altitudeCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getReferenceAirPressure'] = {
            'field': 'referenceAirPressure',
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

mod['getAirPressureCallbackThreshold'] = {
            'field': 'airPressureCallbackThreshold',
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

mod['setAltitudeCallbackPeriod'] = {
            'field': 'altitudeCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setAirPressureCallbackPeriod'] = {
            'field': 'airPressureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setAltitudeCallbackThreshold'] = {
            'field': 'altitudeCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setReferenceAirPressure'] = {
            'field': 'referenceAirPressure',
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

mod['setAirPressureCallbackThreshold'] = {
            'field': 'airPressureCallbackThreshold',
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

callbacks['altitude'] = {
            'field': 'altitude',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['altitudeReached'] = {
            'field': 'altitudeReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['airPressure'] = {
            'field': 'airPressure',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['airPressureReached'] = {
            'field': 'airPressureReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getAltitude'] = {
            'value_type': 'number',
            'field': 'altitude',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getChipTemperature'] = {
            'value_type': 'number',
            'field': 'chipTemperature',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getAirPressure'] = {
            'value_type': 'number',
            'field': 'airPressure',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAirPressureCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'airPressureCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAltitudeCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'altitudeCallbackThreshold',
            'field_type': ['char', 'int32', 'int32'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAltitudeCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'altitudeCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getReferenceAirPressure'] = {
            'value_type': 'number',
            'field': 'referenceAirPressure',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAveraging'] = {
            'value_type': 'number',
            'field': 'averaging',
            'field_type': ['uint8', 'uint8', 'uint8'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAirPressureCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'airPressureCallbackThreshold',
            'field_type': ['char', 'int32', 'int32'],
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
