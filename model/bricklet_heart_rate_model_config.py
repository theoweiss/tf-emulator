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

mod['getHeartRate'] = {
            'field': 'heartRate',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['isBeatStateChangedCallbackEnabled'] = {
            'field': 'BeatStateChangedCallback',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getHeartRateCallbackThreshold'] = {
            'field': 'heartRateCallbackThreshold',
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

mod['getHeartRateCallbackPeriod'] = {
            'field': 'heartRateCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['setHeartRateCallbackPeriod'] = {
            'field': 'heartRateCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setHeartRateCallbackThreshold'] = {
            'field': 'heartRateCallbackThreshold',
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

mod['enableBeatStateChangedCallback'] = {
            'field': 'BeatStateChangedCallback',
            'subdevice_type': 'actor',
            'function_type': 'enabler',
            'skip': False
            }

mod['disableBeatStateChangedCallback'] = {
            'field': 'BeatStateChangedCallback',
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

callbacks['heartRate'] = {
            'field': 'heartRate',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['heartRateReached'] = {
            'field': 'heartRateReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['beatStateChanged'] = {
            'field': 'beatStateChanged',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getHeartRate'] = {
            'value_type': 'number',
            'field': 'heartRate',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
enabled_fields['isBeatStateChangedCallbackEnabled'] = {
            'value_type': 'number',
            'field': 'BeatStateChangedCallback',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
threshold_fields['getHeartRateCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'heartRateCallbackThreshold',
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
