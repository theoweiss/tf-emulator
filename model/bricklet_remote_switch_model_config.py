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

mod['getSwitchingState'] = {
            'field': 'switchingState',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getRepeats'] = {
            'field': 'repeats',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setRepeats'] = {
            'field': 'repeats',
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

mod['switchSocketA'] = {
            'field': 'switchSocketA',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['switchSocketB'] = {
            'field': 'switchSocketB',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['switchSocket'] = {
            'field': 'switchSocket',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['switchSocketC'] = {
            'field': 'switchSocketC',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['dimSocketB'] = {
            'field': 'dimSocketB',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['switchingDone'] = {
            'field': 'switchingDone',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getSwitchingState'] = {
            'value_type': 'number',
            'field': 'switchingState',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getRepeats'] = {
            'value_type': 'number',
            'field': 'repeats',
            'field_type': ['uint8'],
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

other_fields['switchSocketA'] = {
            'value_type': 'number',
            'field': 'switchSocketA',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['switchSocketB'] = {
            'value_type': 'number',
            'field': 'switchSocketB',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['switchSocket'] = {
            'value_type': 'number',
            'field': 'switchSocket',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['switchSocketC'] = {
            'value_type': 'number',
            'field': 'switchSocketC',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['dimSocketB'] = {
            'value_type': 'number',
            'field': 'dimSocketB',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        