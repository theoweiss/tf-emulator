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

mod['getState'] = {
            'field': 'state',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getTagID'] = {
            'field': 'tagID',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPage'] = {
            'field': 'page',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['requestTagID'] = {
            'field': 'requestTagID',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['authenticateMifareClassicPage'] = {
            'field': 'authenticateMifareClassicPage',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['writePage'] = {
            'field': 'writePage',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['requestPage'] = {
            'field': 'requestPage',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['stateChanged'] = {
            'field': 'stateChanged',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getState'] = {
            'value_type': 'number',
            'field': 'state',
            'field_type': ['uint8', 'bool'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getTagID'] = {
            'value_type': 'number',
            'field': 'tagID',
            'field_type': ['uint8', 'uint8', 'uint8'],
            'field_type_cardinality': [1, 1, 7],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getPage'] = {
            'value_type': 'number',
            'field': 'page',
            'field_type': ['uint8'],
            'field_type_cardinality': [16],
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

other_fields['requestTagID'] = {
            'value_type': 'number',
            'field': 'requestTagID',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['authenticateMifareClassicPage'] = {
            'value_type': 'number',
            'field': 'authenticateMifareClassicPage',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['writePage'] = {
            'value_type': 'number',
            'field': 'writePage',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['requestPage'] = {
            'value_type': 'number',
            'field': 'requestPage',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        