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

mod['getRemainingSteps'] = {
            'field': 'remainingSteps',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCurrentConsumption'] = {
            'field': 'currentConsumption',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAllData'] = {
            'field': 'allData',
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

mod['getTimeBase'] = {
            'field': 'timeBase',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMaxVelocity'] = {
            'field': 'maxVelocity',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDecay'] = {
            'field': 'decay',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCurrentPosition'] = {
            'field': 'currentPosition',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMotorCurrent'] = {
            'field': 'motorCurrent',
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

mod['getSpeedRamping'] = {
            'field': 'speedRamping',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAllDataPeriod'] = {
            'field': 'allDataPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getTargetPosition'] = {
            'field': 'targetPosition',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getStepMode'] = {
            'field': 'stepMode',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getSteps'] = {
            'field': 'steps',
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

mod['isStatusLEDEnabled'] = {
            'field': 'StatusLED',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setSpeedRamping'] = {
            'field': 'speedRamping',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setMaxVelocity'] = {
            'field': 'maxVelocity',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setAllDataPeriod'] = {
            'field': 'allDataPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setTimeBase'] = {
            'field': 'timeBase',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setStepMode'] = {
            'field': 'stepMode',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setDecay'] = {
            'field': 'decay',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setTargetPosition'] = {
            'field': 'targetPosition',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setCurrentPosition'] = {
            'field': 'currentPosition',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setMotorCurrent'] = {
            'field': 'motorCurrent',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setMinimumVoltage'] = {
            'field': 'minimumVoltage',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setSteps'] = {
            'field': 'steps',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['enable'] = {
            'field': 'enabled',
            'subdevice_type': 'actor',
            'function_type': 'enabler',
            'skip': False
            }

mod['enableStatusLED'] = {
            'field': 'StatusLED',
            'subdevice_type': 'actor',
            'function_type': 'enabler',
            'skip': False
            }

mod['disable'] = {
            'field': 'enabled',
            'subdevice_type': 'actor',
            'function_type': 'disablers',
            'skip': False
            }

mod['disableStatusLED'] = {
            'field': 'StatusLED',
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

mod['reset'] = {
            'field': 'reset',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['fullBrake'] = {
            'field': 'fullBrake',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['driveForward'] = {
            'field': 'driveForward',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['stop'] = {
            'field': 'stop',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['driveBackward'] = {
            'field': 'driveBackward',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

mod['setSyncRect'] = {
            'field': 'setSyncRect',
            'subdevice_type': 'actuator',
            'function_type': 'setter',
            'skip': False
            }

mod['isSyncRect'] = {
            'field': 'isSyncRect',
            'subdevice_type': 'actuator',
            'function_type': 'setter',
            'skip': False
            }

callbacks['underVoltage'] = {
            'field': 'underVoltage',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['newState'] = {
            'field': 'newState',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['allData'] = {
            'field': 'allData',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['positionReached'] = {
            'field': 'positionReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getRemainingSteps'] = {
            'value_type': 'number',
            'field': 'remainingSteps',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getCurrentConsumption'] = {
            'value_type': 'number',
            'field': 'currentConsumption',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getAllData'] = {
            'value_type': 'number',
            'field': 'allData',
            'field_type': ['uint16', 'int32', 'int32', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1, 1, 1],
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
        
actor_fields['getTimeBase'] = {
            'value_type': 'number',
            'field': 'timeBase',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMaxVelocity'] = {
            'value_type': 'number',
            'field': 'maxVelocity',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getDecay'] = {
            'value_type': 'number',
            'field': 'decay',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getCurrentPosition'] = {
            'value_type': 'number',
            'field': 'currentPosition',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMotorCurrent'] = {
            'value_type': 'number',
            'field': 'motorCurrent',
            'field_type': ['uint16'],
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
        
actor_fields['getSpeedRamping'] = {
            'value_type': 'number',
            'field': 'speedRamping',
            'field_type': ['uint16', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAllDataPeriod'] = {
            'value_type': 'number',
            'field': 'allDataPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getTargetPosition'] = {
            'value_type': 'number',
            'field': 'targetPosition',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getStepMode'] = {
            'value_type': 'number',
            'field': 'stepMode',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getSteps'] = {
            'value_type': 'number',
            'field': 'steps',
            'field_type': ['int32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
enabled_fields['isEnabled'] = {
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
        
enabled_fields['isStatusLEDEnabled'] = {
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
        
other_actor_fields['fullBrake'] = {
            'value_type': 'number',
            'field': 'fullBrake',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_actor_fields['driveForward'] = {
            'value_type': 'number',
            'field': 'driveForward',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_actor_fields['stop'] = {
            'value_type': 'number',
            'field': 'stop',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_actor_fields['driveBackward'] = {
            'value_type': 'number',
            'field': 'driveBackward',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
special_fields['setSyncRect'] = {
            'value_type': 'number',
            'field': 'setSyncRect',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
special_fields['isSyncRect'] = {
            'value_type': 'number',
            'field': 'isSyncRect',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        