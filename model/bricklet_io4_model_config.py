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

mod['getEdgeCount'] = {
            'field': 'edgeCount',
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

mod['getConfiguration'] = {
            'field': 'configuration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMonoflop'] = {
            'field': 'monoflop',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getValue'] = {
            'field': 'value',
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

mod['setValue'] = {
            'field': 'value',
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

mod['setConfiguration'] = {
            'field': 'configuration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setMonoflop'] = {
            'field': 'monoflop',
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

mod['setSelectedValues'] = {
            'field': 'setSelectedValues',
            'subdevice_type': 'actuator',
            'function_type': 'setter',
            'skip': False
            }

callbacks['interrupt'] = {
            'field': 'interrupt',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['monoflopDone'] = {
            'field': 'monoflopDone',
            'function_type': 'callback',
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
            'field_type': ['uint8'],
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
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMonoflop'] = {
            'value_type': 'number',
            'field': 'monoflop',
            'field_type': ['uint8', 'uint32', 'uint32'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getValue'] = {
            'value_type': 'number',
            'field': 'value',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
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

special_fields['setSelectedValues'] = {
            'value_type': 'number',
            'field': 'setSelectedValues',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        