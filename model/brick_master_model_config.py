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

mod['getUSBVoltage'] = {
            'field': 'uSBVoltage',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiStatus'] = {
            'field': 'wifiStatus',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChibiErrorLog'] = {
            'field': 'chibiErrorLog',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiBufferInfo'] = {
            'field': 'wifiBufferInfo',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getStackVoltage'] = {
            'field': 'stackVoltage',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChibiSignalStrength'] = {
            'field': 'chibiSignalStrength',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getRS485ErrorLog'] = {
            'field': 'rS485ErrorLog',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEthernetStatus'] = {
            'field': 'ethernetStatus',
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

mod['getStackCurrent'] = {
            'field': 'stackCurrent',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChibiFrequency'] = {
            'field': 'chibiFrequency',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getExtensionType'] = {
            'field': 'extensionType',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEthernetAuthenticationSecret'] = {
            'field': 'ethernetAuthenticationSecret',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiHostname'] = {
            'field': 'wifiHostname',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChibiMasterAddress'] = {
            'field': 'chibiMasterAddress',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChibiChannel'] = {
            'field': 'chibiChannel',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChibiSlaveAddress'] = {
            'field': 'chibiSlaveAddress',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiPowerMode'] = {
            'field': 'wifiPowerMode',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getChibiAddress'] = {
            'field': 'chibiAddress',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getRS485Address'] = {
            'field': 'rS485Address',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiAuthenticationSecret'] = {
            'field': 'wifiAuthenticationSecret',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiConfiguration'] = {
            'field': 'wifiConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getRS485SlaveAddress'] = {
            'field': 'rS485SlaveAddress',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiEncryption'] = {
            'field': 'wifiEncryption',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getRS485Configuration'] = {
            'field': 'rS485Configuration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiRegulatoryDomain'] = {
            'field': 'wifiRegulatoryDomain',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getLongWifiKey'] = {
            'field': 'longWifiKey',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEthernetConfiguration'] = {
            'field': 'ethernetConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getWifiCertificate'] = {
            'field': 'wifiCertificate',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getEthernetWebsocketConfiguration'] = {
            'field': 'ethernetWebsocketConfiguration',
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

mod['getStackVoltageCallbackThreshold'] = {
            'field': 'stackVoltageCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_getter',
            'skip': False
            }

mod['getUSBVoltageCallbackThreshold'] = {
            'field': 'uSBVoltageCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_getter',
            'skip': False
            }

mod['getStackCurrentCallbackThreshold'] = {
            'field': 'stackCurrentCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_getter',
            'skip': False
            }

mod['getDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_debounce_period_getter',
            'skip': False
            }

mod['getStackVoltageCallbackPeriod'] = {
            'field': 'stackVoltageCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getStackCurrentCallbackPeriod'] = {
            'field': 'stackCurrentCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['getUSBVoltageCallbackPeriod'] = {
            'field': 'uSBVoltageCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_getter',
            'skip': False
            }

mod['setStackCurrentCallbackPeriod'] = {
            'field': 'stackCurrentCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setStackVoltageCallbackPeriod'] = {
            'field': 'stackVoltageCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setUSBVoltageCallbackPeriod'] = {
            'field': 'uSBVoltageCallbackPeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_period_setter',
            'skip': False
            }

mod['setUSBVoltageCallbackThreshold'] = {
            'field': 'uSBVoltageCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_setter',
            'skip': False
            }

mod['setStackCurrentCallbackThreshold'] = {
            'field': 'stackCurrentCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_setter',
            'skip': False
            }

mod['setStackVoltageCallbackThreshold'] = {
            'field': 'stackVoltageCallbackThreshold',
            'subdevice_type': 'actor',
            'function_type': 'callback_threshold_setter',
            'skip': False
            }

mod['setDebouncePeriod'] = {
            'field': 'debouncePeriod',
            'subdevice_type': 'actor',
            'function_type': 'callback_debounce_period_setter',
            'skip': False
            }

mod['setLongWifiKey'] = {
            'field': 'longWifiKey',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setEthernetConfiguration'] = {
            'field': 'ethernetConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setWifiHostname'] = {
            'field': 'wifiHostname',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setWifiAuthenticationSecret'] = {
            'field': 'wifiAuthenticationSecret',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setChibiSlaveAddress'] = {
            'field': 'chibiSlaveAddress',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setExtensionType'] = {
            'field': 'extensionType',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setRS485Address'] = {
            'field': 'rS485Address',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setWifiCertificate'] = {
            'field': 'wifiCertificate',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setRS485Configuration'] = {
            'field': 'rS485Configuration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setWifiConfiguration'] = {
            'field': 'wifiConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setRS485SlaveAddress'] = {
            'field': 'rS485SlaveAddress',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setWifiRegulatoryDomain'] = {
            'field': 'wifiRegulatoryDomain',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setChibiFrequency'] = {
            'field': 'chibiFrequency',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setWifiPowerMode'] = {
            'field': 'wifiPowerMode',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setChibiAddress'] = {
            'field': 'chibiAddress',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setEthernetWebsocketConfiguration'] = {
            'field': 'ethernetWebsocketConfiguration',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setEthernetAuthenticationSecret'] = {
            'field': 'ethernetAuthenticationSecret',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setChibiMasterAddress'] = {
            'field': 'chibiMasterAddress',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setChibiChannel'] = {
            'field': 'chibiChannel',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setWifiEncryption'] = {
            'field': 'wifiEncryption',
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

mod['isChibiPresent'] = {
            'field': 'isChibiPresent',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['isEthernetPresent'] = {
            'field': 'isEthernetPresent',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['setEthernetHostname'] = {
            'field': 'setEthernetHostname',
            'subdevice_type': 'actuator',
            'function_type': 'setter',
            'skip': False
            }

mod['setEthernetMACAddress'] = {
            'field': 'setEthernetMACAddress',
            'subdevice_type': 'actuator',
            'function_type': 'setter',
            'skip': False
            }

mod['isRS485Present'] = {
            'field': 'isRS485Present',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['refreshWifiStatus'] = {
            'field': 'refreshWifiStatus',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['isWifiPresent'] = {
            'field': 'isWifiPresent',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['stackCurrent'] = {
            'field': 'stackCurrent',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['usbVoltage'] = {
            'field': 'usbVoltage',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['usbVoltageReached'] = {
            'field': 'usbVoltageReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['stackVoltage'] = {
            'field': 'stackVoltage',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['stackVoltageReached'] = {
            'field': 'stackVoltageReached',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['stackCurrentReached'] = {
            'field': 'stackCurrentReached',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getUSBVoltage'] = {
            'value_type': 'number',
            'field': 'uSBVoltage',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getWifiStatus'] = {
            'value_type': 'number',
            'field': 'wifiStatus',
            'field_type': ['uint8', 'uint8', 'uint8', 'int16', 'uint8', 'uint8', 'uint8', 'uint32', 'uint32', 'uint8'],
            'field_type_cardinality': [6, 6, 1, 1, 4, 4, 4, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getChibiErrorLog'] = {
            'value_type': 'number',
            'field': 'chibiErrorLog',
            'field_type': ['uint16', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getWifiBufferInfo'] = {
            'value_type': 'number',
            'field': 'wifiBufferInfo',
            'field_type': ['uint32', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getStackVoltage'] = {
            'value_type': 'number',
            'field': 'stackVoltage',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getChibiSignalStrength'] = {
            'value_type': 'number',
            'field': 'chibiSignalStrength',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getRS485ErrorLog'] = {
            'value_type': 'number',
            'field': 'rS485ErrorLog',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getEthernetStatus'] = {
            'value_type': 'number',
            'field': 'ethernetStatus',
            'field_type': ['uint8', 'uint8', 'uint8', 'uint8', 'uint32', 'uint32', 'string'],
            'field_type_cardinality': [6, 4, 4, 4, 1, 1, 32],
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
        
sensor_fields['getStackCurrent'] = {
            'value_type': 'number',
            'field': 'stackCurrent',
            'field_type': ['uint16'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getChibiFrequency'] = {
            'value_type': 'number',
            'field': 'chibiFrequency',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getExtensionType'] = {
            'value_type': 'number',
            'field': 'extensionType',
            'field_type': ['uint32'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getEthernetAuthenticationSecret'] = {
            'value_type': 'number',
            'field': 'ethernetAuthenticationSecret',
            'field_type': ['string'],
            'field_type_cardinality': [64],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWifiHostname'] = {
            'value_type': 'number',
            'field': 'wifiHostname',
            'field_type': ['string'],
            'field_type_cardinality': [16],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getChibiMasterAddress'] = {
            'value_type': 'number',
            'field': 'chibiMasterAddress',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getChibiChannel'] = {
            'value_type': 'number',
            'field': 'chibiChannel',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getChibiSlaveAddress'] = {
            'value_type': 'number',
            'field': 'chibiSlaveAddress',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWifiPowerMode'] = {
            'value_type': 'number',
            'field': 'wifiPowerMode',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getChibiAddress'] = {
            'value_type': 'number',
            'field': 'chibiAddress',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getRS485Address'] = {
            'value_type': 'number',
            'field': 'rS485Address',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWifiAuthenticationSecret'] = {
            'value_type': 'number',
            'field': 'wifiAuthenticationSecret',
            'field_type': ['string'],
            'field_type_cardinality': [64],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWifiConfiguration'] = {
            'value_type': 'number',
            'field': 'wifiConfiguration',
            'field_type': ['string', 'uint8', 'uint8', 'uint8', 'uint8', 'uint16'],
            'field_type_cardinality': [32, 1, 4, 4, 4, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getRS485SlaveAddress'] = {
            'value_type': 'number',
            'field': 'rS485SlaveAddress',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWifiEncryption'] = {
            'value_type': 'number',
            'field': 'wifiEncryption',
            'field_type': ['uint8', 'string', 'uint8', 'uint8', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 50, 1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getRS485Configuration'] = {
            'value_type': 'number',
            'field': 'rS485Configuration',
            'field_type': ['uint32', 'char', 'uint8'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWifiRegulatoryDomain'] = {
            'value_type': 'number',
            'field': 'wifiRegulatoryDomain',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getLongWifiKey'] = {
            'value_type': 'number',
            'field': 'longWifiKey',
            'field_type': ['string'],
            'field_type_cardinality': [64],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getEthernetConfiguration'] = {
            'value_type': 'number',
            'field': 'ethernetConfiguration',
            'field_type': ['uint8', 'uint8', 'uint8', 'uint8', 'uint16'],
            'field_type_cardinality': [1, 4, 4, 4, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getWifiCertificate'] = {
            'value_type': 'number',
            'field': 'wifiCertificate',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [32, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getEthernetWebsocketConfiguration'] = {
            'value_type': 'number',
            'field': 'ethernetWebsocketConfiguration',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
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
        
threshold_fields['getStackVoltageCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'stackVoltageCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 'x00',
            'max_value': 1000,
            'min_value': 0,
            'skip': False
        }
        
threshold_fields['getUSBVoltageCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'uSBVoltageCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 'x00',
            'max_value': 1000,
            'min_value': 0,
            'skip': False
        }
        
threshold_fields['getStackCurrentCallbackThreshold'] = {
            'value_type': 'threshold_buffer',
            'field': 'stackCurrentCallbackThreshold',
            'field_type': ['char', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 'x00',
            'max_value': 1000,
            'min_value': 0,
            'skip': False
        }
        
debounce_period_fields['getDebouncePeriod'] = {
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
        
other_sensors['isChibiPresent'] = {
            'value_type': 'number',
            'field': 'isChibiPresent',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_sensors['isEthernetPresent'] = {
            'value_type': 'number',
            'field': 'isEthernetPresent',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
special_fields['setEthernetHostname'] = {
            'value_type': 'number',
            'field': 'setEthernetHostname',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
special_fields['setEthernetMACAddress'] = {
            'value_type': 'number',
            'field': 'setEthernetMACAddress',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['isRS485Present'] = {
            'value_type': 'number',
            'field': 'isRS485Present',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['refreshWifiStatus'] = {
            'value_type': 'number',
            'field': 'refreshWifiStatus',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['isWifiPresent'] = {
            'value_type': 'number',
            'field': 'isWifiPresent',
            'field_type': ['bool'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        