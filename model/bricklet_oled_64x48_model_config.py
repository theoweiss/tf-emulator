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


mod['getDisplayConfiguration'] = {
            'field': 'displayConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setDisplayConfiguration'] = {
            'field': 'displayConfiguration',
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

mod['clearDisplay'] = {
            'field': 'clearDisplay',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['writeLine'] = {
            'field': 'writeLine',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['write'] = {
            'field': 'write',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['newWindow'] = {
            'field': 'newWindow',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

actor_fields['getDisplayConfiguration'] = {
            'value_type': 'number',
            'field': 'displayConfiguration',
            'field_type': ['uint8', 'bool'],
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

other_actor_fields['clearDisplay'] = {
            'value_type': 'number',
            'field': 'clearDisplay',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_actor_fields['writeLine'] = {
            'value_type': 'number',
            'field': 'writeLine',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['write'] = {
            'value_type': 'number',
            'field': 'write',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['newWindow'] = {
            'value_type': 'number',
            'field': 'newWindow',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        