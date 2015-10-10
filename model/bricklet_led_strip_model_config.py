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


mod['getSupplyVoltage'] = {
            'field': 'supplyVoltage',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChipType'] = {
            'field': 'chipType',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getRGBValues'] = {
            'field': 'rGBValues',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getFrameDuration'] = {
            'field': 'frameDuration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getClockFrequency'] = {
            'field': 'clockFrequency',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setClockFrequency'] = {
            'field': 'clockFrequency',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setRGBValues'] = {
            'field': 'rGBValues',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setChipType'] = {
            'field': 'chipType',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setFrameDuration'] = {
            'field': 'frameDuration',
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

callbacks['frameRendered'] = {
            'field': 'frameRendered',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getSupplyVoltage'] = {
            'value_type': 'number',
            'field': 'supplyVoltage',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getChipType'] = {
            'value_type': 'number',
            'field': 'chipType',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getRGBValues'] = {
            'value_type': 'number',
            'field': 'rGBValues',
            'field_type': ['uint8', 'uint8', 'uint8'],
            'field_type_cardinality': [16, 16, 16],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getFrameDuration'] = {
            'value_type': 'number',
            'field': 'frameDuration',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getClockFrequency'] = {
            'value_type': 'number',
            'field': 'clockFrequency',
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
