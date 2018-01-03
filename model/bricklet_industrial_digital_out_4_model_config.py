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

mod['getMonoflop'] = {
            'field': 'monoflop',
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

mod['getValue'] = {
            'field': 'value',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setGroup'] = {
            'field': 'group',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setValue'] = {
            'field': 'value',
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

callbacks['monoflopDone'] = {
            'field': 'monoflopDone',
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
        
actor_fields['getMonoflop'] = {
            'value_type': 'number',
            'field': 'monoflop',
            'field_type': ['uint16', 'uint32', 'uint32'],
            'field_type_cardinality': [1, 1, 1],
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
        
actor_fields['getValue'] = {
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
        