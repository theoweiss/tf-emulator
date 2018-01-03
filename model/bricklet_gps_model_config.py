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

mod['getDateTime'] = {
            'field': 'dateTime',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getStatus'] = {
            'field': 'status',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getMotion'] = {
            'field': 'motion',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getAltitude'] = {
            'field': 'altitude',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCoordinates'] = {
            'field': 'coordinates',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDateTimeCallbackPeriod'] = {
            'field': 'dateTimeCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getMotionCallbackPeriod'] = {
            'field': 'motionCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getCoordinatesCallbackPeriod'] = {
            'field': 'coordinatesCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getAltitudeCallbackPeriod'] = {
            'field': 'altitudeCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getStatusCallbackPeriod'] = {
            'field': 'statusCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['setAltitudeCallbackPeriod'] = {
            'field': 'altitudeCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setStatusCallbackPeriod'] = {
            'field': 'statusCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setDateTimeCallbackPeriod'] = {
            'field': 'dateTimeCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setMotionCallbackPeriod'] = {
            'field': 'motionCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setCoordinatesCallbackPeriod'] = {
            'field': 'coordinatesCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['restart'] = {
            'field': 'restart',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['status'] = {
            'field': 'status',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['motion'] = {
            'field': 'motion',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['altitude'] = {
            'field': 'altitude',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['coordinates'] = {
            'field': 'coordinates',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['dateTime'] = {
            'field': 'dateTime',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getDateTime'] = {
            'value_type': 'number',
            'field': 'dateTime',
            'field_type': ['uint32', 'uint32'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getStatus'] = {
            'value_type': 'number',
            'field': 'status',
            'field_type': ['uint8', 'uint8', 'uint8'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getMotion'] = {
            'value_type': 'number',
            'field': 'motion',
            'field_type': ['uint32', 'uint32'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getAltitude'] = {
            'value_type': 'number',
            'field': 'altitude',
            'field_type': ['uint32', 'uint32'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getCoordinates'] = {
            'value_type': 'number',
            'field': 'coordinates',
            'field_type': ['uint32', 'char', 'uint32', 'char', 'uint16', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1, 1, 1, 1, 1],
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

other_fields['restart'] = {
            'value_type': 'number',
            'field': 'restart',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        