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


mod['getTouchState'] = {
            'field': 'touchState',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getElectrodeConfig'] = {
            'field': 'electrodeConfig',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getElectrodeSensitivity'] = {
            'field': 'electrodeSensitivity',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setElectrodeSensitivity'] = {
            'field': 'electrodeSensitivity',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setElectrodeConfig'] = {
            'field': 'electrodeConfig',
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

mod['recalibrate'] = {
            'field': 'recalibrate',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

callbacks['touchState'] = {
            'field': 'touchState',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getTouchState'] = {
            'value_type': 'number',
            'field': 'touchState',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getElectrodeConfig'] = {
            'value_type': 'number',
            'field': 'electrodeConfig',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getElectrodeSensitivity'] = {
            'value_type': 'number',
            'field': 'electrodeSensitivity',
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

other_actor_fields['recalibrate'] = {
            'value_type': 'number',
            'field': 'recalibrate',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        