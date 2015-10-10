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


mod['getIlluminance'] = {
            'field': 'illuminance',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getColorTemperature'] = {
            'field': 'colorTemperature',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getColor'] = {
            'field': 'color',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getColorCallbackPeriod'] = {
            'field': 'colorCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getColorTemperatureCallbackPeriod'] = {
            'field': 'colorTemperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getConfig'] = {
            'field': 'config',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getIlluminanceCallbackPeriod'] = {
            'field': 'illuminanceCallbackPeriod',
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

mod['getColorCallbackThreshold'] = {
            'field': 'colorCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['isLightOn'] = {
            'field': 'light',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setColorCallbackPeriod'] = {
            'field': 'colorCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setColorTemperatureCallbackPeriod'] = {
            'field': 'colorTemperatureCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setIlluminanceCallbackPeriod'] = {
            'field': 'illuminanceCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setColorCallbackThreshold'] = {
            'field': 'colorCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['lightOn'] = {
            'field': 'light',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['setConfig'] = {
            'field': 'config',
            'subdevice_type': 'actor',
            'function_type': 'setter',
            'skip': False
            }

mod['lightOff'] = {
            'field': 'light',
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

callbacks['color'] = {
            'field': 'color',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['colorReached'] = {
            'field': 'colorReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['colorTemperature'] = {
            'field': 'colorTemperature',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['illuminance'] = {
            'field': 'illuminance',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getIlluminance'] = {
            'value_type': 'number',
            'field': 'illuminance',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getColorTemperature'] = {
            'value_type': 'number',
            'field': 'colorTemperature',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getColor'] = {
            'value_type': 'number',
            'field': 'color',
            'field_type': ['uint16', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getColorCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'colorCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getColorTemperatureCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'colorTemperatureCallbackPeriod',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getConfig'] = {
            'value_type': 'number',
            'field': 'config',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getIlluminanceCallbackPeriod'] = {
            'value_type': 'number',
            'field': 'illuminanceCallbackPeriod',
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
        
actor_fields['getColorCallbackThreshold'] = {
            'value_type': 'number',
            'field': 'colorCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16', 'uint16', 'uint16', 'uint16', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1, 1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['isLightOn'] = {
            'value_type': 'number',
            'field': 'light',
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
