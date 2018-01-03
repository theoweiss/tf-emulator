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

mod['getMonoflop'] = {
            'field': 'monoflop',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getState'] = {
            'field': 'state',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setMonoflop'] = {
            'field': 'monoflop',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setState'] = {
            'field': 'state',
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

mod['setSelectedState'] = {
            'field': 'setSelectedState',
            'subdevice_type': 'actuator',
            'function_type': 'setter',
            'skip': False
            }

callbacks['monoflopDone'] = {
            'field': 'monoflopDone',
            'function_type': 'callback',
            'skip': False
            }
        
actor_fields['getMonoflop'] = {
            'value_type': 'number',
            'field': 'monoflop',
            'field_type': ['bool', 'uint32', 'uint32'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getState'] = {
            'value_type': 'number',
            'field': 'state',
            'field_type': ['bool', 'bool'],
            'field_type_cardinality': [1, 1],
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

special_fields['setSelectedState'] = {
            'value_type': 'number',
            'field': 'setSelectedState',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        