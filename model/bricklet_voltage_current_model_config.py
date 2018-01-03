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

mod['getCurrent'] = {
            'field': 'current',
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

mod['getPower'] = {
            'field': 'power',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getConfiguration'] = {
            'field': 'configuration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCalibration'] = {
            'field': 'calibration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPowerCallbackThreshold'] = {
            'field': 'powerCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_getter',
            'skip': False
            }

mod['getVoltageCallbackThreshold'] = {
            'field': 'voltageCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_getter',
            'skip': False
            }

mod['getCurrentCallbackThreshold'] = {
            'field': 'currentCallbackThreshold',
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

mod['getCurrentCallbackPeriod'] = {
            'field': 'currentCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getPowerCallbackPeriod'] = {
            'field': 'powerCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getVoltageCallbackPeriod'] = {
            'field': 'voltageCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['setVoltageCallbackPeriod'] = {
            'field': 'voltageCallbackPeriod',
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

mod['setPowerCallbackPeriod'] = {
            'field': 'powerCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setCurrentCallbackThreshold'] = {
            'field': 'currentCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_setter',
            'skip': False
            }

mod['setPowerCallbackThreshold'] = {
            'field': 'powerCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_setter',
            'skip': False
            }

mod['setVoltageCallbackThreshold'] = {
            'field': 'voltageCallbackThreshold',
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

mod['setCalibration'] = {
            'field': 'calibration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setConfiguration'] = {
            'field': 'configuration',
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

callbacks['powerReached'] = {
            'field': 'powerReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['power'] = {
            'field': 'power',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['current'] = {
            'field': 'current',
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
        
callbacks['currentReached'] = {
            'field': 'currentReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getCurrent'] = {
            'value_type': 'number',
            'field': 'current',
            'field_type': ['int32'],
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
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getPower'] = {
            'value_type': 'number',
            'field': 'power',
            'field_type': ['int32'],
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
            'field_type': ['uint8', 'uint8', 'uint8'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getCalibration'] = {
            'value_type': 'number',
            'field': 'calibration',
            'field_type': ['uint16', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
threshold_fields['getPowerCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'powerCallbackThreshold',
            'field_type': ['char', 'int32', 'int32'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 'x00',
            'max_value': 1000,
            'min_value': 0,
            'skip': False
        }
        
threshold_fields['getVoltageCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'voltageCallbackThreshold',
            'field_type': ['char', 'int32', 'int32'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 'x00',
            'max_value': 1000,
            'min_value': 0,
            'skip': False
        }
        
threshold_fields['getCurrentCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'currentCallbackThreshold',
            'field_type': ['char', 'int32', 'int32'],
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
