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


mod['getVelocity'] = {
            'field': 'velocity',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDistance'] = {
            'field': 'distance',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDistanceCallbackPeriod'] = {
            'field': 'distanceCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMode'] = {
            'field': 'mode',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMovingAverage'] = {
            'field': 'movingAverage',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getVelocityCallbackPeriod'] = {
            'field': 'velocityCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getVelocityCallbackThreshold'] = {
            'field': 'velocityCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isLaserEnabled'] = {
            'field': 'Laser',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDistanceCallbackThreshold'] = {
            'field': 'distanceCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setDistanceCallbackPeriod'] = {
            'field': 'distanceCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setVelocityCallbackPeriod'] = {
            'field': 'velocityCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setDistanceCallbackThreshold'] = {
            'field': 'distanceCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['enableLaser'] = {
            'field': 'Laser',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setMode'] = {
            'field': 'mode',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['disableLaser'] = {
            'field': 'Laser',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setVelocityCallbackThreshold'] = {
            'field': 'velocityCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setMovingAverage'] = {
            'field': 'movingAverage',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setDebouncePeriod'] = {
            'field': 'debouncePeriod',
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

callbacks['distance'] = {
            'field': 'distance',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['velocityReached'] = {
            'field': 'velocityReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['distanceReached'] = {
            'field': 'distanceReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['velocity'] = {
            'field': 'velocity',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getVelocity'] = {
            'value_type': 'number',
            'field': 'velocity',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getDistance'] = {
            'value_type': 'number',
            'field': 'distance',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDistanceCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'distanceCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMode'] = {
            'value_type': 'number',
            'field': 'mode',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMovingAverage'] = {
            'value_type': 'number',
            'field': 'movingAverage',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getVelocityCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'velocityCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDebouncePeriod'] = {
            'value_type': 'number',
            'field': 'debouncePeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getVelocityCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'velocityCallbackThreshold',
            'field_type': ['char', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isLaserEnabled'] = {
            'value_type': 'number',
            'field': 'Laser',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDistanceCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'distanceCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
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
