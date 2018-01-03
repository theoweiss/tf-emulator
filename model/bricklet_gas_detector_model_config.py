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
enabled_fields = {}
debounce_period_fields = {}
threshold_fields = {}

mod['getValue'] = {
            'field': 'value',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDetectorType'] = {
            'field': 'detectorType',
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

mod['getValueCallbackThreshold'] = {
            'field': 'valueCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_getter',
            'skip': False
            }

mod['getDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_debounce_period_getter',
            'skip': False
            }

mod['getValueCallbackPeriod'] = {
            'field': 'valueCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['setValueCallbackPeriod'] = {
            'field': 'valueCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setValueCallbackThreshold'] = {
            'field': 'valueCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_setter',
            'skip': False
            }

mod['setDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_debounce_period_setter',
            'skip': False
            }

mod['setDetectorType'] = {
            'field': 'detectorType',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setMovingAverage'] = {
            'field': 'movingAverage',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['heaterOn'] = {
            'field': 'heaterOn',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['isHeaterOn'] = {
            'field': 'isHeaterOn',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['heaterOff'] = {
            'field': 'heaterOff',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['valueReached'] = {
            'field': 'valueReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['value'] = {
            'field': 'value',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getValue'] = {
            'value_type': 'number',
            'field': 'value',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDetectorType'] = {
            'value_type': 'number',
            'field': 'detectorType',
            'field_type': ['uint8'],
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
        
threshold_fields['getValueCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'valueCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 'x00',
            'max_value': 1000,
            'min_value': 0,
            'skip': False
        }
        
debounce_period_fields['getDebouncePeriod'] = {
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

other_fields['heaterOn'] = {
            'value_type': 'number',
            'field': 'heaterOn',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['isHeaterOn'] = {
            'value_type': 'number',
            'field': 'isHeaterOn',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['heaterOff'] = {
            'value_type': 'number',
            'field': 'heaterOff',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        