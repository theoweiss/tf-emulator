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

mod['getCO2Concentration'] = {
            'field': 'cO2Concentration',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCO2ConcentrationCallbackThreshold'] = {
            'field': 'cO2ConcentrationCallbackThreshold',
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

mod['getCO2ConcentrationCallbackPeriod'] = {
            'field': 'cO2ConcentrationCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['setCO2ConcentrationCallbackPeriod'] = {
            'field': 'cO2ConcentrationCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setCO2ConcentrationCallbackThreshold'] = {
            'field': 'cO2ConcentrationCallbackThreshold',
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

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

callbacks['co2ConcentrationReached'] = {
            'field': 'co2ConcentrationReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['co2Concentration'] = {
            'field': 'co2Concentration',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getCO2Concentration'] = {
            'value_type': 'number',
            'field': 'cO2Concentration',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
threshold_fields['getCO2ConcentrationCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'cO2ConcentrationCallbackThreshold',
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
