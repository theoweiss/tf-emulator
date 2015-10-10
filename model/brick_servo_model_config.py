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


mod['getCurrentPosition'] = {
            'field': 'currentPosition',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getServoCurrent'] = {
            'field': 'servoCurrent',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChipTemperature'] = {
            'field': 'chipTemperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getOverallCurrent'] = {
            'field': 'overallCurrent',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCurrentVelocity'] = {
            'field': 'currentVelocity',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getExternalInputVoltage'] = {
            'field': 'externalInputVoltage',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getStackInputVoltage'] = {
            'field': 'stackInputVoltage',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPulseWidth'] = {
            'field': 'pulseWidth',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDegree'] = {
            'field': 'degree',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getVelocity'] = {
            'field': 'velocity',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isVelocityReachedCallbackEnabled'] = {
            'field': 'VelocityReachedCallback',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isEnabled'] = {
            'field': 'enabled',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMinimumVoltage'] = {
            'field': 'minimumVoltage',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getOutputVoltage'] = {
            'field': 'outputVoltage',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPeriod'] = {
            'field': 'period',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isPositionReachedCallbackEnabled'] = {
            'field': 'PositionReachedCallback',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isStatusLEDEnabled'] = {
            'field': 'StatusLED',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPosition'] = {
            'field': 'position',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAcceleration'] = {
            'field': 'acceleration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setPeriod'] = {
            'field': 'period',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setAcceleration'] = {
            'field': 'acceleration',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['disable'] = {
            'field': 'enabled',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['disablePositionReachedCallback'] = {
            'field': 'PositionReachedCallback',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setDegree'] = {
            'field': 'degree',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['enableVelocityReachedCallback'] = {
            'field': 'VelocityReachedCallback',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['enablePositionReachedCallback'] = {
            'field': 'PositionReachedCallback',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setOutputVoltage'] = {
            'field': 'outputVoltage',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['enable'] = {
            'field': 'enabled',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setPulseWidth'] = {
            'field': 'pulseWidth',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['enableStatusLED'] = {
            'field': 'StatusLED',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setVelocity'] = {
            'field': 'velocity',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setMinimumVoltage'] = {
            'field': 'minimumVoltage',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setPosition'] = {
            'field': 'position',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['disableVelocityReachedCallback'] = {
            'field': 'VelocityReachedCallback',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['disableStatusLED'] = {
            'field': 'StatusLED',
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

mod['reset'] = {
            'field': 'reset',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

callbacks['underVoltage'] = {
            'field': 'underVoltage',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['velocityReached'] = {
            'field': 'velocityReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['positionReached'] = {
            'field': 'positionReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getCurrentPosition'] = {
            'value_type': 'number',
            'field': 'currentPosition',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getServoCurrent'] = {
            'value_type': 'number',
            'field': 'servoCurrent',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getChipTemperature'] = {
            'value_type': 'number',
            'field': 'chipTemperature',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getOverallCurrent'] = {
            'value_type': 'number',
            'field': 'overallCurrent',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getCurrentVelocity'] = {
            'value_type': 'number',
            'field': 'currentVelocity',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getExternalInputVoltage'] = {
            'value_type': 'number',
            'field': 'externalInputVoltage',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getStackInputVoltage'] = {
            'value_type': 'number',
            'field': 'stackInputVoltage',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getPulseWidth'] = {
            'value_type': 'number',
            'field': 'pulseWidth',
            'field_type': ['uint16', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDegree'] = {
            'value_type': 'number',
            'field': 'degree',
            'field_type': ['int16', 'int16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getVelocity'] = {
            'value_type': 'number',
            'field': 'velocity',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isVelocityReachedCallbackEnabled'] = {
            'value_type': 'number',
            'field': 'VelocityReachedCallback',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isEnabled'] = {
            'value_type': 'number',
            'field': 'enabled',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMinimumVoltage'] = {
            'value_type': 'number',
            'field': 'minimumVoltage',
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
        
actor_fields['getPeriod'] = {
            'value_type': 'number',
            'field': 'period',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isPositionReachedCallbackEnabled'] = {
            'value_type': 'number',
            'field': 'PositionReachedCallback',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isStatusLEDEnabled'] = {
            'value_type': 'number',
            'field': 'StatusLED',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getPosition'] = {
            'value_type': 'number',
            'field': 'position',
            'field_type': ['int16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAcceleration'] = {
            'value_type': 'number',
            'field': 'acceleration',
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

other_actor_fields['reset'] = {
            'value_type': 'number',
            'field': 'reset',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        