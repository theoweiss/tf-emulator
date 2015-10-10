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


mod['getButtonState'] = {
            'field': 'buttonState',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getLEDState'] = {
            'field': 'lEDState',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setLEDState'] = {
            'field': 'lEDState',
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

mod['setSelectedLEDState'] = {
            'field': 'setSelectedLEDState',
            'subdevice_type': 'actuator',
            'function_type': 'setter',
            'skip': False
            }

callbacks['stateChanged'] = {
            'field': 'stateChanged',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getButtonState'] = {
            'value_type': 'number',
            'field': 'buttonState',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getLEDState'] = {
            'value_type': 'number',
            'field': 'lEDState',
            'field_type': ['uint8', 'uint8'],
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

special_fields['setSelectedLEDState'] = {
            'value_type': 'number',
            'field': 'setSelectedLEDState',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        