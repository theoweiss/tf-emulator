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

mod['getPortInterrupt'] = {
            'field': 'portInterrupt',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPortMonoflop'] = {
            'field': 'portMonoflop',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPortConfiguration'] = {
            'field': 'portConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPort'] = {
            'field': 'port',
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

mod['setPortInterrupt'] = {
            'field': 'portInterrupt',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setPortMonoflop'] = {
            'field': 'portMonoflop',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setPortConfiguration'] = {
            'field': 'portConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setEdgeCountConfig'] = {
            'field': 'edgeCountConfig',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setPort'] = {
            'field': 'port',
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
        
actor_fields['getPortInterrupt'] = {
            'value_type': 'number',
            'field': 'portInterrupt',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getPortMonoflop'] = {
            'value_type': 'number',
            'field': 'portMonoflop',
            'field_type': ['uint8', 'uint32', 'uint32'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getPortConfiguration'] = {
            'value_type': 'number',
            'field': 'portConfiguration',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getPort'] = {
            'value_type': 'number',
            'field': 'port',
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
        