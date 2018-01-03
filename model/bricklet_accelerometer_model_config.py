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

mod['getTemperature'] = {
            'field': 'temperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAcceleration'] = {
            'field': 'acceleration',
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

mod['isLEDOn'] = {
            'field': 'led',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAccelerationCallbackThreshold'] = {
            'field': 'accelerationCallbackThreshold',
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

mod['getAccelerationCallbackPeriod'] = {
            'field': 'accelerationCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['setAccelerationCallbackPeriod'] = {
            'field': 'accelerationCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setAccelerationCallbackThreshold'] = {
            'field': 'accelerationCallbackThreshold',
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

mod['setConfiguration'] = {
            'field': 'configuration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['ledOn'] = {
            'field': 'led',
            'subdevice_type': 'actor',
            'function_type': 'enabler',
            'skip': False
            }

mod['ledOff'] = {
            'field': 'led',
            'subdevice_type': 'actor',
            'function_type': 'disablers',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

callbacks['acceleration'] = {
            'field': 'acceleration',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['accelerationReached'] = {
            'field': 'accelerationReached',
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
        
sensor_fields['getAcceleration'] = {
            'value_type': 'number',
            'field': 'acceleration',
            'field_type': ['int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
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
        
enabled_fields['isLEDOn'] = {
            'value_type': 'number',
            'field': 'led',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
threshold_fields['getAccelerationCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'accelerationCallbackThreshold',
            'field_type': ['char', 'int16', 'int16', 'int16', 'int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1, 1, 1, 1, 1],
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
