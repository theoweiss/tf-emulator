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

mod['getConfiguration'] = {
            'field': 'configuration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isReadCallbackEnabled'] = {
            'field': 'ReadCallback',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setConfiguration'] = {
            'field': 'configuration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['enableReadCallback'] = {
            'field': 'ReadCallback',
            'subdevice_type': 'actor',
            'function_type': 'enabler',
            'skip': False
            }

mod['disableReadCallback'] = {
            'field': 'ReadCallback',
            'subdevice_type': 'actor',
            'function_type': 'disablers',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['write'] = {
            'field': 'write',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['read'] = {
            'field': 'read',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['readCallback'] = {
            'field': 'readCallback',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['errorCallback'] = {
            'field': 'errorCallback',
            'function_type': 'callback',
            'skip': False
            }
        
actor_fields['getConfiguration'] = {
            'value_type': 'number',
            'field': 'configuration',
            'field_type': ['uint8', 'uint8', 'uint8', 'uint8', 'uint8', 'uint8'],
            'field_type_cardinality': [1, 1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
enabled_fields['isReadCallbackEnabled'] = {
            'value_type': 'number',
            'field': 'ReadCallback',
            'field_type': ['bool'],
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

other_fields['write'] = {
            'value_type': 'number',
            'field': 'write',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['read'] = {
            'value_type': 'number',
            'field': 'read',
            'field_type': ['char', 'uint8'],
            'field_type_cardinality': [60, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        