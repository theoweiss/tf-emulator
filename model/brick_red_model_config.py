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

mod['getStringLength'] = {
            'field': 'stringLength',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getNextDirectoryEntry'] = {
            'field': 'nextDirectoryEntry',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProcesses'] = {
            'field': 'processes',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProcessIdentity'] = {
            'field': 'processIdentity',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getLastSpawnedProgramProcess'] = {
            'field': 'lastSpawnedProgramProcess',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getFileInfo'] = {
            'field': 'fileInfo',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProgramIdentifier'] = {
            'field': 'programIdentifier',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getPrograms'] = {
            'field': 'programs',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getListItem'] = {
            'field': 'listItem',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCustomProgramOptionNames'] = {
            'field': 'customProgramOptionNames',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getListLength'] = {
            'field': 'listLength',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProgramRootDirectory'] = {
            'field': 'programRootDirectory',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProcessState'] = {
            'field': 'processState',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProgramSchedulerState'] = {
            'field': 'programSchedulerState',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProcessCommand'] = {
            'field': 'processCommand',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getDirectoryName'] = {
            'field': 'directoryName',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProcessStdio'] = {
            'field': 'processStdio',
            'subdevice_type': 'sensor',
            'function_type': 'getter',
            'skip': False
            }

mod['getStringChunk'] = {
            'field': 'stringChunk',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getFileEvents'] = {
            'field': 'fileEvents',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProgramCommand'] = {
            'field': 'programCommand',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProgramStdioRedirection'] = {
            'field': 'programStdioRedirection',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getProgramSchedule'] = {
            'field': 'programSchedule',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getFilePosition'] = {
            'field': 'filePosition',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['getCustomProgramOptionValue'] = {
            'field': 'customProgramOptionValue',
            'subdevice_type': 'actor',
            'function_type': 'getter',
            'skip': False
            }

mod['setStringChunk'] = {
            'field': 'stringChunk',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setProgramCommand'] = {
            'field': 'programCommand',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setCustomProgramOptionValue'] = {
            'field': 'customProgramOptionValue',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setFilePosition'] = {
            'field': 'filePosition',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setProgramStdioRedirection'] = {
            'field': 'programStdioRedirection',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setProgramSchedule'] = {
            'field': 'programSchedule',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['setFileEvents'] = {
            'field': 'fileEvents',
            'subdevice_type': 'actor',
            'function_type': 'actuator_setter',
            'skip': False
            }

mod['getIdentity'] = {
            'field': 'identity',
            'subdevice_type': 'identity',
            'function_type': 'getter',
            'skip': False
            }

mod['releaseObjectUnchecked'] = {
            'field': 'releaseObjectUnchecked',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['openFile'] = {
            'field': 'openFile',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['allocateList'] = {
            'field': 'allocateList',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['createPipe'] = {
            'field': 'createPipe',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['expireSession'] = {
            'field': 'expireSession',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['spawnProcess'] = {
            'field': 'spawnProcess',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['killProcess'] = {
            'field': 'killProcess',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['writeFileUnchecked'] = {
            'field': 'writeFileUnchecked',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['removeFromList'] = {
            'field': 'removeFromList',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['readFileAsync'] = {
            'field': 'readFileAsync',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['startProgram'] = {
            'field': 'startProgram',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['rewindDirectory'] = {
            'field': 'rewindDirectory',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['readFile'] = {
            'field': 'readFile',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['truncateString'] = {
            'field': 'truncateString',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['writeFile'] = {
            'field': 'writeFile',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['expireSessionUnchecked'] = {
            'field': 'expireSessionUnchecked',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['createSession'] = {
            'field': 'createSession',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['abortAsyncFileRead'] = {
            'field': 'abortAsyncFileRead',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['appendToList'] = {
            'field': 'appendToList',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['openDirectory'] = {
            'field': 'openDirectory',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['continueProgramSchedule'] = {
            'field': 'continueProgramSchedule',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['allocateString'] = {
            'field': 'allocateString',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['releaseObject'] = {
            'field': 'releaseObject',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['removeCustomProgramOption'] = {
            'field': 'removeCustomProgramOption',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['purgeProgram'] = {
            'field': 'purgeProgram',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['keepSessionAlive'] = {
            'field': 'keepSessionAlive',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['defineProgram'] = {
            'field': 'defineProgram',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['writeFileAsync'] = {
            'field': 'writeFileAsync',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

mod['createDirectory'] = {
            'field': 'createDirectory',
            'subdevice_type': 'other',
            'function_type': 'setter',
            'skip': False
            }

callbacks['fileEventsOccurred'] = {
            'field': 'fileEventsOccurred',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['programSchedulerStateChanged'] = {
            'field': 'programSchedulerStateChanged',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['programProcessSpawned'] = {
            'field': 'programProcessSpawned',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['asyncFileWrite'] = {
            'field': 'asyncFileWrite',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['asyncFileRead'] = {
            'field': 'asyncFileRead',
            'function_type': 'callback',
            'skip': False
            }
        
callbacks['processStateChanged'] = {
            'field': 'processStateChanged',
            'function_type': 'callback',
            'skip': False
            }
        
sensor_fields['getStringLength'] = {
            'value_type': 'number',
            'field': 'stringLength',
            'field_type': ['uint8', 'uint32'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getNextDirectoryEntry'] = {
            'value_type': 'number',
            'field': 'nextDirectoryEntry',
            'field_type': ['uint8', 'uint16', 'uint8'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProcesses'] = {
            'value_type': 'number',
            'field': 'processes',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProcessIdentity'] = {
            'value_type': 'number',
            'field': 'processIdentity',
            'field_type': ['uint8', 'uint32', 'uint32', 'uint32'],
            'field_type_cardinality': [1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getLastSpawnedProgramProcess'] = {
            'value_type': 'number',
            'field': 'lastSpawnedProgramProcess',
            'field_type': ['uint8', 'uint16', 'uint64'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getFileInfo'] = {
            'value_type': 'number',
            'field': 'fileInfo',
            'field_type': ['uint8', 'uint8', 'uint16', 'uint32', 'uint16', 'uint32', 'uint32', 'uint64', 'uint64', 'uint64', 'uint64'],
            'field_type_cardinality': [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProgramIdentifier'] = {
            'value_type': 'number',
            'field': 'programIdentifier',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getPrograms'] = {
            'value_type': 'number',
            'field': 'programs',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getListItem'] = {
            'value_type': 'number',
            'field': 'listItem',
            'field_type': ['uint8', 'uint16', 'uint8'],
            'field_type_cardinality': [1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getCustomProgramOptionNames'] = {
            'value_type': 'number',
            'field': 'customProgramOptionNames',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getListLength'] = {
            'value_type': 'number',
            'field': 'listLength',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProgramRootDirectory'] = {
            'value_type': 'number',
            'field': 'programRootDirectory',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProcessState'] = {
            'value_type': 'number',
            'field': 'processState',
            'field_type': ['uint8', 'uint8', 'uint64', 'uint8'],
            'field_type_cardinality': [1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProgramSchedulerState'] = {
            'value_type': 'number',
            'field': 'programSchedulerState',
            'field_type': ['uint8', 'uint8', 'uint64', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProcessCommand'] = {
            'value_type': 'number',
            'field': 'processCommand',
            'field_type': ['uint8', 'uint16', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getDirectoryName'] = {
            'value_type': 'number',
            'field': 'directoryName',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
sensor_fields['getProcessStdio'] = {
            'value_type': 'number',
            'field': 'processStdio',
            'field_type': ['uint8', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getStringChunk'] = {
            'value_type': 'number',
            'field': 'stringChunk',
            'field_type': ['uint8', 'string'],
            'field_type_cardinality': [1, 63],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getFileEvents'] = {
            'value_type': 'number',
            'field': 'fileEvents',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getProgramCommand'] = {
            'value_type': 'number',
            'field': 'programCommand',
            'field_type': ['uint8', 'uint16', 'uint16', 'uint16', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getProgramStdioRedirection'] = {
            'value_type': 'number',
            'field': 'programStdioRedirection',
            'field_type': ['uint8', 'uint8', 'uint16', 'uint8', 'uint16', 'uint8', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getProgramSchedule'] = {
            'value_type': 'number',
            'field': 'programSchedule',
            'field_type': ['uint8', 'uint8', 'bool', 'uint32', 'uint16'],
            'field_type_cardinality': [1, 1, 1, 1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getFilePosition'] = {
            'value_type': 'number',
            'field': 'filePosition',
            'field_type': ['uint8', 'uint64'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
actor_fields['getCustomProgramOptionValue'] = {
            'value_type': 'number',
            'field': 'customProgramOptionValue',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
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

other_fields['releaseObjectUnchecked'] = {
            'value_type': 'number',
            'field': 'releaseObjectUnchecked',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['openFile'] = {
            'value_type': 'number',
            'field': 'openFile',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['allocateList'] = {
            'value_type': 'number',
            'field': 'allocateList',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['createPipe'] = {
            'value_type': 'number',
            'field': 'createPipe',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['expireSession'] = {
            'value_type': 'number',
            'field': 'expireSession',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['spawnProcess'] = {
            'value_type': 'number',
            'field': 'spawnProcess',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['killProcess'] = {
            'value_type': 'number',
            'field': 'killProcess',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['writeFileUnchecked'] = {
            'value_type': 'number',
            'field': 'writeFileUnchecked',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['removeFromList'] = {
            'value_type': 'number',
            'field': 'removeFromList',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['readFileAsync'] = {
            'value_type': 'number',
            'field': 'readFileAsync',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['startProgram'] = {
            'value_type': 'number',
            'field': 'startProgram',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['rewindDirectory'] = {
            'value_type': 'number',
            'field': 'rewindDirectory',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['readFile'] = {
            'value_type': 'number',
            'field': 'readFile',
            'field_type': ['uint8', 'uint8', 'uint8'],
            'field_type_cardinality': [1, 62, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['truncateString'] = {
            'value_type': 'number',
            'field': 'truncateString',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['writeFile'] = {
            'value_type': 'number',
            'field': 'writeFile',
            'field_type': ['uint8', 'uint8'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['expireSessionUnchecked'] = {
            'value_type': 'number',
            'field': 'expireSessionUnchecked',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['createSession'] = {
            'value_type': 'number',
            'field': 'createSession',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['abortAsyncFileRead'] = {
            'value_type': 'number',
            'field': 'abortAsyncFileRead',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['appendToList'] = {
            'value_type': 'number',
            'field': 'appendToList',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['openDirectory'] = {
            'value_type': 'number',
            'field': 'openDirectory',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['continueProgramSchedule'] = {
            'value_type': 'number',
            'field': 'continueProgramSchedule',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['allocateString'] = {
            'value_type': 'number',
            'field': 'allocateString',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['releaseObject'] = {
            'value_type': 'number',
            'field': 'releaseObject',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['removeCustomProgramOption'] = {
            'value_type': 'number',
            'field': 'removeCustomProgramOption',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['purgeProgram'] = {
            'value_type': 'number',
            'field': 'purgeProgram',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['keepSessionAlive'] = {
            'value_type': 'number',
            'field': 'keepSessionAlive',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['defineProgram'] = {
            'value_type': 'number',
            'field': 'defineProgram',
            'field_type': ['uint8', 'uint16'],
            'field_type_cardinality': [1, 1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['writeFileAsync'] = {
            'value_type': 'number',
            'field': 'writeFileAsync',
            'field_type': [],
            'field_type_cardinality': [],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        
other_fields['createDirectory'] = {
            'value_type': 'number',
            'field': 'createDirectory',
            'field_type': ['uint8'],
            'field_type_cardinality': [1],
            'default_value': 100,
            'max_value': 1000,
            'min_value': 0,
            'step_value': 1,
            'skip': False
        }
        