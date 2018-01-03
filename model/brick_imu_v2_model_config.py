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

mod['getLinearAcceleration'] = {
            'field': 'linearAcceleration',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAngularVelocity'] = {
            'field': 'angularVelocity',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getOrientation'] = {
            'field': 'orientation',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getTemperature'] = {
            'field': 'temperature',
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

mod['getQuaternion'] = {
            'field': 'quaternion',
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

mod['getMagneticField'] = {
            'field': 'magneticField',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getGravityVector'] = {
            'field': 'gravityVector',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAcceleration'] = {
            'field': 'acceleration',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getTemperaturePeriod'] = {
            'field': 'temperaturePeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getOrientationPeriod'] = {
            'field': 'orientationPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMagneticFieldPeriod'] = {
            'field': 'magneticFieldPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAccelerationPeriod'] = {
            'field': 'accelerationPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getLinearAccelerationPeriod'] = {
            'field': 'linearAccelerationPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAngularVelocityPeriod'] = {
            'field': 'angularVelocityPeriod',
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

mod['getQuaternionPeriod'] = {
            'field': 'quaternionPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getGravityVectorPeriod'] = {
            'field': 'gravityVectorPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['areLedsOn'] = {
            'field': 'leds',
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

mod['setAllDataPeriod'] = {
            'field': 'allDataPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setMagneticFieldPeriod'] = {
            'field': 'magneticFieldPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setAccelerationPeriod'] = {
            'field': 'accelerationPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setAngularVelocityPeriod'] = {
            'field': 'angularVelocityPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setTemperaturePeriod'] = {
            'field': 'temperaturePeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setOrientationPeriod'] = {
            'field': 'orientationPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setQuaternionPeriod'] = {
            'field': 'quaternionPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setLinearAccelerationPeriod'] = {
            'field': 'linearAccelerationPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setGravityVectorPeriod'] = {
            'field': 'gravityVectorPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['enableStatusLED'] = {
            'field': 'StatusLED',
            'subdevice_type': 'actor',
            'function_type': 'enabler',
            'skip': False
            }

mod['ledsOn'] = {
            'field': 'leds',
            'subdevice_type': 'actor',
            'function_type': 'enabler',
            'skip': False
            }

mod['ledsOff'] = {
            'field': 'leds',
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

mod['saveCalibration'] = {
            'field': 'saveCalibration',
            'subdevice_type': 'actuator',
            'function_type': 'getter',
            'skip': False
            }

callbacks['acceleration'] = {
            'field': 'acceleration',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['temperature'] = {
            'field': 'temperature',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['magneticField'] = {
            'field': 'magneticField',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['angularVelocity'] = {
            'field': 'angularVelocity',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['quaternion'] = {
            'field': 'quaternion',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['linearAcceleration'] = {
            'field': 'linearAcceleration',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['gravityVector'] = {
            'field': 'gravityVector',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['allData'] = {
            'field': 'allData',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['orientation'] = {
            'field': 'orientation',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getLinearAcceleration'] = {
            'value_type': 'number',
            'field': 'linearAcceleration',
            'field_type': ['int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getAngularVelocity'] = {
            'value_type': 'number',
            'field': 'angularVelocity',
            'field_type': ['int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getOrientation'] = {
            'value_type': 'number',
            'field': 'orientation',
            'field_type': ['int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getTemperature'] = {
            'value_type': 'number',
            'field': 'temperature',
            'field_type': ['int8'],
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
            'field_type': ['int16', 'int16', 'int16', 'int16', 'int16', 'int16', 'int16', 'int8', 'uint8'],
            'field_type_cardinality': [3, 3, 3, 3, 4, 3, 3, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getQuaternion'] = {
            'value_type': 'number',
            'field': 'quaternion',
            'field_type': ['int16', 'int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1, 1],
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
        
sensor_fields['getMagneticField'] = {
            'value_type': 'number',
            'field': 'magneticField',
            'field_type': ['int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getGravityVector'] = {
            'value_type': 'number',
            'field': 'gravityVector',
            'field_type': ['int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getAcceleration'] = {
            'value_type': 'number',
            'field': 'acceleration',
            'field_type': ['int16', 'int16', 'int16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getTemperaturePeriod'] = {
            'value_type': 'number',
            'field': 'temperaturePeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getOrientationPeriod'] = {
            'value_type': 'number',
            'field': 'orientationPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getMagneticFieldPeriod'] = {
            'value_type': 'number',
            'field': 'magneticFieldPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAccelerationPeriod'] = {
            'value_type': 'number',
            'field': 'accelerationPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getLinearAccelerationPeriod'] = {
            'value_type': 'number',
            'field': 'linearAccelerationPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getAngularVelocityPeriod'] = {
            'value_type': 'number',
            'field': 'angularVelocityPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
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
        
actor_fields['getQuaternionPeriod'] = {
            'value_type': 'number',
            'field': 'quaternionPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getGravityVectorPeriod'] = {
            'value_type': 'number',
            'field': 'gravityVectorPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
enabled_fields['areLedsOn'] = {
            'value_type': 'number',
            'field': 'leds',
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
        
other_actor_fields['saveCalibration'] = {
            'value_type': 'number',
            'field': 'saveCalibration',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        