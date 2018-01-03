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

mod['getAvailableForGroup'] = {
            'field': 'availableForGroup',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEdgeCount'] = {
            'field': 'edgeCount',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getValue'] = {
            'field': 'value',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEdgeCountConfig'] = {
            'field': 'edgeCountConfig',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getInterrupt'] = {
            'field': 'interrupt',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getGroup'] = {
            'field': 'group',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_debounce_period_getter',
            'skip': False
            }

mod['setDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_debounce_period_setter',
            'skip': False
            }

mod['setGroup'] = {
            'field': 'group',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setEdgeCountConfig'] = {
            'field': 'edgeCountConfig',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setInterrupt'] = {
            'field': 'interrupt',
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

callbacks['interrupt'] = {
            'field': 'interrupt',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getAvailableForGroup'] = {
            'value_type': 'number',
            'field': 'availableForGroup',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getEdgeCount'] = {
            'value_type': 'number',
            'field': 'edgeCount',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
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
        
actor_fields['getEdgeCountConfig'] = {
            'value_type': 'number',
            'field': 'edgeCountConfig',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getInterrupt'] = {
            'value_type': 'number',
            'field': 'interrupt',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getGroup'] = {
            'value_type': 'number',
            'field': 'group',
            'field_type': ['char'],
            'field_type_cardinality': [4],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
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
