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

mod['getCurrentVelocity'] = {
            'field': 'currentVelocity',
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

mod['getExternalInputVoltage'] = {
            'field': 'externalInputVoltage',
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

mod['getChipTemperature'] = {
            'field': 'chipTemperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPWMFrequency'] = {
            'field': 'pWMFrequency',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCurrentVelocityPeriod'] = {
            'field': 'currentVelocityPeriod',
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

mod['getMinimumVoltage'] = {
            'field': 'minimumVoltage',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDriveMode'] = {
            'field': 'driveMode',
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

mod['setAcceleration'] = {
            'field': 'acceleration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setCurrentVelocityPeriod'] = {
            'field': 'currentVelocityPeriod',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setPWMFrequency'] = {
            'field': 'pWMFrequency',
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

mod['setDriveMode'] = {
            'field': 'driveMode',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setVelocity'] = {
            'field': 'velocity',
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

callbacks['underVoltage'] = {
            'field': 'underVoltage',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['emergencyShutdown'] = {
            'field': 'emergencyShutdown',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['velocityReached'] = {
            'field': 'velocityReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['currentVelocity'] = {
            'field': 'currentVelocity',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getCurrentVelocity'] = {
            'value_type': 'number',
            'field': 'currentVelocity',
            'field_type': ['int16'],
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
        
actor_fields['getPWMFrequency'] = {
            'value_type': 'number',
            'field': 'pWMFrequency',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getCurrentVelocityPeriod'] = {
            'value_type': 'number',
            'field': 'currentVelocityPeriod',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getVelocity'] = {
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
        
actor_fields['getDriveMode'] = {
            'value_type': 'number',
            'field': 'driveMode',
            'field_type': ['uint8'],
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
        