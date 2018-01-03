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

mod['getInputVoltage'] = {
            'field': 'inputVoltage',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getOutputVoltage'] = {
            'field': 'outputVoltage',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setOutputVoltage'] = {
            'field': 'outputVoltage',
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

sensor_fields['getInputVoltage'] = {
            'value_type': 'number',
            'field': 'inputVoltage',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getOutputVoltage'] = {
            'value_type': 'number',
            'field': 'outputVoltage',
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
