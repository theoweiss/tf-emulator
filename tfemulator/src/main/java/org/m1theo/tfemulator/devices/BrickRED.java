/*
 *  Copyright (c) 2015 Thomas Weiss <theo@m1theo.org>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
/* ***********************************************************
 * This file was automatically generated.      *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the emulator generator.             *
 *************************************************************/

package org.m1theo.tfemulator.devices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import org.m1theo.tfemulator.Brickd;
import org.m1theo.tfemulator.CommonServices;
import org.m1theo.tfemulator.Utils;
import org.m1theo.tfemulator.Utils.Step;
import org.m1theo.tfemulator.protocol.Packet;

/**
 * Executes user programs and controls other Bricks/Bricklets standalone
 */
public class BrickRED extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 17;
public final static String DEVICE_DISPLAY_NAME = "RED Brick";

  public final static byte FUNCTION_CREATE_SESSION = (byte)1;
  public final static byte FUNCTION_EXPIRE_SESSION = (byte)2;
  public final static byte FUNCTION_EXPIRE_SESSION_UNCHECKED = (byte)3;
  public final static byte FUNCTION_KEEP_SESSION_ALIVE = (byte)4;
  public final static byte FUNCTION_RELEASE_OBJECT = (byte)5;
  public final static byte FUNCTION_RELEASE_OBJECT_UNCHECKED = (byte)6;
  public final static byte FUNCTION_ALLOCATE_STRING = (byte)7;
  public final static byte FUNCTION_TRUNCATE_STRING = (byte)8;
  public final static byte FUNCTION_GET_STRING_LENGTH = (byte)9;
  public final static byte FUNCTION_SET_STRING_CHUNK = (byte)10;
  public final static byte FUNCTION_GET_STRING_CHUNK = (byte)11;
  public final static byte FUNCTION_ALLOCATE_LIST = (byte)12;
  public final static byte FUNCTION_GET_LIST_LENGTH = (byte)13;
  public final static byte FUNCTION_GET_LIST_ITEM = (byte)14;
  public final static byte FUNCTION_APPEND_TO_LIST = (byte)15;
  public final static byte FUNCTION_REMOVE_FROM_LIST = (byte)16;
  public final static byte FUNCTION_OPEN_FILE = (byte)17;
  public final static byte FUNCTION_CREATE_PIPE = (byte)18;
  public final static byte FUNCTION_GET_FILE_INFO = (byte)19;
  public final static byte FUNCTION_READ_FILE = (byte)20;
  public final static byte FUNCTION_READ_FILE_ASYNC = (byte)21;
  public final static byte FUNCTION_ABORT_ASYNC_FILE_READ = (byte)22;
  public final static byte FUNCTION_WRITE_FILE = (byte)23;
  public final static byte FUNCTION_WRITE_FILE_UNCHECKED = (byte)24;
  public final static byte FUNCTION_WRITE_FILE_ASYNC = (byte)25;
  public final static byte FUNCTION_SET_FILE_POSITION = (byte)26;
  public final static byte FUNCTION_GET_FILE_POSITION = (byte)27;
  public final static byte FUNCTION_SET_FILE_EVENTS = (byte)28;
  public final static byte FUNCTION_GET_FILE_EVENTS = (byte)29;
  public final static byte CALLBACK_ASYNC_FILE_READ = (byte)30;
  public final static byte CALLBACK_ASYNC_FILE_WRITE = (byte)31;
  public final static byte CALLBACK_FILE_EVENTS_OCCURRED = (byte)32;
  public final static byte FUNCTION_OPEN_DIRECTORY = (byte)33;
  public final static byte FUNCTION_GET_DIRECTORY_NAME = (byte)34;
  public final static byte FUNCTION_GET_NEXT_DIRECTORY_ENTRY = (byte)35;
  public final static byte FUNCTION_REWIND_DIRECTORY = (byte)36;
  public final static byte FUNCTION_CREATE_DIRECTORY = (byte)37;
  public final static byte FUNCTION_GET_PROCESSES = (byte)38;
  public final static byte FUNCTION_SPAWN_PROCESS = (byte)39;
  public final static byte FUNCTION_KILL_PROCESS = (byte)40;
  public final static byte FUNCTION_GET_PROCESS_COMMAND = (byte)41;
  public final static byte FUNCTION_GET_PROCESS_IDENTITY = (byte)42;
  public final static byte FUNCTION_GET_PROCESS_STDIO = (byte)43;
  public final static byte FUNCTION_GET_PROCESS_STATE = (byte)44;
  public final static byte CALLBACK_PROCESS_STATE_CHANGED = (byte)45;
  public final static byte FUNCTION_GET_PROGRAMS = (byte)46;
  public final static byte FUNCTION_DEFINE_PROGRAM = (byte)47;
  public final static byte FUNCTION_PURGE_PROGRAM = (byte)48;
  public final static byte FUNCTION_GET_PROGRAM_IDENTIFIER = (byte)49;
  public final static byte FUNCTION_GET_PROGRAM_ROOT_DIRECTORY = (byte)50;
  public final static byte FUNCTION_SET_PROGRAM_COMMAND = (byte)51;
  public final static byte FUNCTION_GET_PROGRAM_COMMAND = (byte)52;
  public final static byte FUNCTION_SET_PROGRAM_STDIO_REDIRECTION = (byte)53;
  public final static byte FUNCTION_GET_PROGRAM_STDIO_REDIRECTION = (byte)54;
  public final static byte FUNCTION_SET_PROGRAM_SCHEDULE = (byte)55;
  public final static byte FUNCTION_GET_PROGRAM_SCHEDULE = (byte)56;
  public final static byte FUNCTION_GET_PROGRAM_SCHEDULER_STATE = (byte)57;
  public final static byte FUNCTION_CONTINUE_PROGRAM_SCHEDULE = (byte)58;
  public final static byte FUNCTION_START_PROGRAM = (byte)59;
  public final static byte FUNCTION_GET_LAST_SPAWNED_PROGRAM_PROCESS = (byte)60;
  public final static byte FUNCTION_GET_CUSTOM_PROGRAM_OPTION_NAMES = (byte)61;
  public final static byte FUNCTION_SET_CUSTOM_PROGRAM_OPTION_VALUE = (byte)62;
  public final static byte FUNCTION_GET_CUSTOM_PROGRAM_OPTION_VALUE = (byte)63;
  public final static byte FUNCTION_REMOVE_CUSTOM_PROGRAM_OPTION = (byte)64;
  public final static byte CALLBACK_PROGRAM_SCHEDULER_STATE_CHANGED = (byte)65;
  public final static byte CALLBACK_PROGRAM_PROCESS_SPAWNED = (byte)66;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short ERROR_CODE_SUCCESS = (short)0;
  public final static short ERROR_CODE_UNKNOWN_ERROR = (short)1;
  public final static short ERROR_CODE_INVALID_OPERATION = (short)2;
  public final static short ERROR_CODE_OPERATION_ABORTED = (short)3;
  public final static short ERROR_CODE_INTERNAL_ERROR = (short)4;
  public final static short ERROR_CODE_UNKNOWN_SESSION_ID = (short)5;
  public final static short ERROR_CODE_NO_FREE_SESSION_ID = (short)6;
  public final static short ERROR_CODE_UNKNOWN_OBJECT_ID = (short)7;
  public final static short ERROR_CODE_NO_FREE_OBJECT_ID = (short)8;
  public final static short ERROR_CODE_OBJECT_IS_LOCKED = (short)9;
  public final static short ERROR_CODE_NO_MORE_DATA = (short)10;
  public final static short ERROR_CODE_WRONG_LIST_ITEM_TYPE = (short)11;
  public final static short ERROR_CODE_PROGRAM_IS_PURGED = (short)12;
  public final static short ERROR_CODE_INVALID_PARAMETER = (short)128;
  public final static short ERROR_CODE_NO_FREE_MEMORY = (short)129;
  public final static short ERROR_CODE_NO_FREE_SPACE = (short)130;
  public final static short ERROR_CODE_ACCESS_DENIED = (short)121;
  public final static short ERROR_CODE_ALREADY_EXISTS = (short)132;
  public final static short ERROR_CODE_DOES_NOT_EXIST = (short)133;
  public final static short ERROR_CODE_INTERRUPTED = (short)134;
  public final static short ERROR_CODE_IS_DIRECTORY = (short)135;
  public final static short ERROR_CODE_NOT_A_DIRECTORY = (short)136;
  public final static short ERROR_CODE_WOULD_BLOCK = (short)137;
  public final static short ERROR_CODE_OVERFLOW = (short)138;
  public final static short ERROR_CODE_BAD_FILE_DESCRIPTOR = (short)139;
  public final static short ERROR_CODE_OUT_OF_RANGE = (short)140;
  public final static short ERROR_CODE_NAME_TOO_LONG = (short)141;
  public final static short ERROR_CODE_INVALID_SEEK = (short)142;
  public final static short ERROR_CODE_NOT_SUPPORTED = (short)143;
  public final static short ERROR_CODE_TOO_MANY_OPEN_FILES = (short)144;
  public final static short OBJECT_TYPE_STRING = (short)0;
  public final static short OBJECT_TYPE_LIST = (short)1;
  public final static short OBJECT_TYPE_FILE = (short)2;
  public final static short OBJECT_TYPE_DIRECTORY = (short)3;
  public final static short OBJECT_TYPE_PROCESS = (short)4;
  public final static short OBJECT_TYPE_PROGRAM = (short)5;
  public final static long FILE_FLAG_READ_ONLY = 1L;
  public final static long FILE_FLAG_WRITE_ONLY = 2L;
  public final static long FILE_FLAG_READ_WRITE = 4L;
  public final static long FILE_FLAG_APPEND = 8L;
  public final static long FILE_FLAG_CREATE = 16L;
  public final static long FILE_FLAG_EXCLUSIVE = 32L;
  public final static long FILE_FLAG_NON_BLOCKING = 64L;
  public final static long FILE_FLAG_TRUNCATE = 128L;
  public final static long FILE_FLAG_TEMPORARY = 256L;
  public final static long FILE_FLAG_REPLACE = 512L;
  public final static int FILE_PERMISSION_USER_ALL = 448;
  public final static int FILE_PERMISSION_USER_READ = 256;
  public final static int FILE_PERMISSION_USER_WRITE = 128;
  public final static int FILE_PERMISSION_USER_EXECUTE = 64;
  public final static int FILE_PERMISSION_GROUP_ALL = 56;
  public final static int FILE_PERMISSION_GROUP_READ = 32;
  public final static int FILE_PERMISSION_GROUP_WRITE = 16;
  public final static int FILE_PERMISSION_GROUP_EXECUTE = 8;
  public final static int FILE_PERMISSION_OTHERS_ALL = 7;
  public final static int FILE_PERMISSION_OTHERS_READ = 4;
  public final static int FILE_PERMISSION_OTHERS_WRITE = 2;
  public final static int FILE_PERMISSION_OTHERS_EXECUTE = 1;
  public final static long PIPE_FLAG_NON_BLOCKING_READ = 1L;
  public final static long PIPE_FLAG_NON_BLOCKING_WRITE = 2L;
  public final static short FILE_TYPE_UNKNOWN = (short)0;
  public final static short FILE_TYPE_REGULAR = (short)1;
  public final static short FILE_TYPE_DIRECTORY = (short)2;
  public final static short FILE_TYPE_CHARACTER = (short)3;
  public final static short FILE_TYPE_BLOCK = (short)4;
  public final static short FILE_TYPE_FIFO = (short)5;
  public final static short FILE_TYPE_SYMLINK = (short)6;
  public final static short FILE_TYPE_SOCKET = (short)7;
  public final static short FILE_TYPE_PIPE = (short)8;
  public final static short FILE_ORIGIN_BEGINNING = (short)0;
  public final static short FILE_ORIGIN_CURRENT = (short)1;
  public final static short FILE_ORIGIN_END = (short)2;
  public final static int FILE_EVENT_READABLE = 1;
  public final static int FILE_EVENT_WRITABLE = 2;
  public final static short DIRECTORY_ENTRY_TYPE_UNKNOWN = (short)0;
  public final static short DIRECTORY_ENTRY_TYPE_REGULAR = (short)1;
  public final static short DIRECTORY_ENTRY_TYPE_DIRECTORY = (short)2;
  public final static short DIRECTORY_ENTRY_TYPE_CHARACTER = (short)3;
  public final static short DIRECTORY_ENTRY_TYPE_BLOCK = (short)4;
  public final static short DIRECTORY_ENTRY_TYPE_FIFO = (short)5;
  public final static short DIRECTORY_ENTRY_TYPE_SYMLINK = (short)6;
  public final static short DIRECTORY_ENTRY_TYPE_SOCKET = (short)7;
  public final static long DIRECTORY_FLAG_RECURSIVE = 1L;
  public final static long DIRECTORY_FLAG_EXCLUSIVE = 2L;
  public final static short PROCESS_SIGNAL_INTERRUPT = (short)2;
  public final static short PROCESS_SIGNAL_QUIT = (short)3;
  public final static short PROCESS_SIGNAL_ABORT = (short)6;
  public final static short PROCESS_SIGNAL_KILL = (short)9;
  public final static short PROCESS_SIGNAL_USER1 = (short)10;
  public final static short PROCESS_SIGNAL_USER2 = (short)12;
  public final static short PROCESS_SIGNAL_TERMINATE = (short)15;
  public final static short PROCESS_SIGNAL_CONTINUE = (short)18;
  public final static short PROCESS_SIGNAL_STOP = (short)19;
  public final static short PROCESS_STATE_UNKNOWN = (short)0;
  public final static short PROCESS_STATE_RUNNING = (short)1;
  public final static short PROCESS_STATE_ERROR = (short)2;
  public final static short PROCESS_STATE_EXITED = (short)3;
  public final static short PROCESS_STATE_KILLED = (short)4;
  public final static short PROCESS_STATE_STOPPED = (short)5;
  public final static short PROGRAM_STDIO_REDIRECTION_DEV_NULL = (short)0;
  public final static short PROGRAM_STDIO_REDIRECTION_PIPE = (short)1;
  public final static short PROGRAM_STDIO_REDIRECTION_FILE = (short)2;
  public final static short PROGRAM_STDIO_REDIRECTION_INDIVIDUAL_LOG = (short)3;
  public final static short PROGRAM_STDIO_REDIRECTION_CONTINUOUS_LOG = (short)4;
  public final static short PROGRAM_STDIO_REDIRECTION_STDOUT = (short)5;
  public final static short PROGRAM_START_MODE_NEVER = (short)0;
  public final static short PROGRAM_START_MODE_ALWAYS = (short)1;
  public final static short PROGRAM_START_MODE_INTERVAL = (short)2;
  public final static short PROGRAM_START_MODE_CRON = (short)3;
  public final static short PROGRAM_SCHEDULER_STATE_STOPPED = (short)0;
  public final static short PROGRAM_SCHEDULER_STATE_RUNNING = (short)1;
  String uidString;

  private Buffer stringChunk = getStringChunkDefault();
        
  private Buffer fileEvents = getFileEventsDefault();
        
  private Buffer programCommand = getProgramCommandDefault();
        
  private Buffer programStdioRedirection = getProgramStdioRedirectionDefault();
        
  private Buffer programSchedule = getProgramScheduleDefault();
        
  private Buffer filePosition = getFilePositionDefault();
        
  private Buffer customProgramOptionValue = getCustomProgramOptionValueDefault();
        
  private byte stringLength = 100;
  private byte stringLength_max = 1000;
  private byte stringLength_min = 0;
  private byte stringLength_step = 1;
  private long stringLength_generator_period = 100;
  private Step stringLength_direction = Step.UP;
  private long stringLengthCallbackPeriod;
  private long stringLength_callback_id;
  private byte stringLength_last_value_called_back;

  private byte nextDirectoryEntry = 100;
  private byte nextDirectoryEntry_max = 1000;
  private byte nextDirectoryEntry_min = 0;
  private byte nextDirectoryEntry_step = 1;
  private long nextDirectoryEntry_generator_period = 100;
  private Step nextDirectoryEntry_direction = Step.UP;
  private long nextDirectoryEntryCallbackPeriod;
  private long nextDirectoryEntry_callback_id;
  private byte nextDirectoryEntry_last_value_called_back;

  private byte processes = 100;
  private byte processes_max = 1000;
  private byte processes_min = 0;
  private byte processes_step = 1;
  private long processes_generator_period = 100;
  private Step processes_direction = Step.UP;
  private long processesCallbackPeriod;
  private long processes_callback_id;
  private byte processes_last_value_called_back;

  private byte processIdentity = 100;
  private byte processIdentity_max = 1000;
  private byte processIdentity_min = 0;
  private byte processIdentity_step = 1;
  private long processIdentity_generator_period = 100;
  private Step processIdentity_direction = Step.UP;
  private long processIdentityCallbackPeriod;
  private long processIdentity_callback_id;
  private byte processIdentity_last_value_called_back;

  private byte lastSpawnedProgramProcess = 100;
  private byte lastSpawnedProgramProcess_max = 1000;
  private byte lastSpawnedProgramProcess_min = 0;
  private byte lastSpawnedProgramProcess_step = 1;
  private long lastSpawnedProgramProcess_generator_period = 100;
  private Step lastSpawnedProgramProcess_direction = Step.UP;
  private long lastSpawnedProgramProcessCallbackPeriod;
  private long lastSpawnedProgramProcess_callback_id;
  private byte lastSpawnedProgramProcess_last_value_called_back;

  private byte fileInfo = 100;
  private byte fileInfo_max = 1000;
  private byte fileInfo_min = 0;
  private byte fileInfo_step = 1;
  private long fileInfo_generator_period = 100;
  private Step fileInfo_direction = Step.UP;
  private long fileInfoCallbackPeriod;
  private long fileInfo_callback_id;
  private byte fileInfo_last_value_called_back;

  private byte programIdentifier = 100;
  private byte programIdentifier_max = 1000;
  private byte programIdentifier_min = 0;
  private byte programIdentifier_step = 1;
  private long programIdentifier_generator_period = 100;
  private Step programIdentifier_direction = Step.UP;
  private long programIdentifierCallbackPeriod;
  private long programIdentifier_callback_id;
  private byte programIdentifier_last_value_called_back;

  private byte programs = 100;
  private byte programs_max = 1000;
  private byte programs_min = 0;
  private byte programs_step = 1;
  private long programs_generator_period = 100;
  private Step programs_direction = Step.UP;
  private long programsCallbackPeriod;
  private long programs_callback_id;
  private byte programs_last_value_called_back;

  private byte listItem = 100;
  private byte listItem_max = 1000;
  private byte listItem_min = 0;
  private byte listItem_step = 1;
  private long listItem_generator_period = 100;
  private Step listItem_direction = Step.UP;
  private long listItemCallbackPeriod;
  private long listItem_callback_id;
  private byte listItem_last_value_called_back;

  private byte customProgramOptionNames = 100;
  private byte customProgramOptionNames_max = 1000;
  private byte customProgramOptionNames_min = 0;
  private byte customProgramOptionNames_step = 1;
  private long customProgramOptionNames_generator_period = 100;
  private Step customProgramOptionNames_direction = Step.UP;
  private long customProgramOptionNamesCallbackPeriod;
  private long customProgramOptionNames_callback_id;
  private byte customProgramOptionNames_last_value_called_back;

  private byte listLength = 100;
  private byte listLength_max = 1000;
  private byte listLength_min = 0;
  private byte listLength_step = 1;
  private long listLength_generator_period = 100;
  private Step listLength_direction = Step.UP;
  private long listLengthCallbackPeriod;
  private long listLength_callback_id;
  private byte listLength_last_value_called_back;

  private byte programRootDirectory = 100;
  private byte programRootDirectory_max = 1000;
  private byte programRootDirectory_min = 0;
  private byte programRootDirectory_step = 1;
  private long programRootDirectory_generator_period = 100;
  private Step programRootDirectory_direction = Step.UP;
  private long programRootDirectoryCallbackPeriod;
  private long programRootDirectory_callback_id;
  private byte programRootDirectory_last_value_called_back;

  private byte processState = 100;
  private byte processState_max = 1000;
  private byte processState_min = 0;
  private byte processState_step = 1;
  private long processState_generator_period = 100;
  private Step processState_direction = Step.UP;
  private long processStateCallbackPeriod;
  private long processState_callback_id;
  private byte processState_last_value_called_back;

  private byte programSchedulerState = 100;
  private byte programSchedulerState_max = 1000;
  private byte programSchedulerState_min = 0;
  private byte programSchedulerState_step = 1;
  private long programSchedulerState_generator_period = 100;
  private Step programSchedulerState_direction = Step.UP;
  private long programSchedulerStateCallbackPeriod;
  private long programSchedulerState_callback_id;
  private byte programSchedulerState_last_value_called_back;

  private byte processCommand = 100;
  private byte processCommand_max = 1000;
  private byte processCommand_min = 0;
  private byte processCommand_step = 1;
  private long processCommand_generator_period = 100;
  private Step processCommand_direction = Step.UP;
  private long processCommandCallbackPeriod;
  private long processCommand_callback_id;
  private byte processCommand_last_value_called_back;

  private byte directoryName = 100;
  private byte directoryName_max = 1000;
  private byte directoryName_min = 0;
  private byte directoryName_step = 1;
  private long directoryName_generator_period = 100;
  private Step directoryName_direction = Step.UP;
  private long directoryNameCallbackPeriod;
  private long directoryName_callback_id;
  private byte directoryName_last_value_called_back;

  private byte processStdio = 100;
  private byte processStdio_max = 1000;
  private byte processStdio_min = 0;
  private byte processStdio_step = 1;
  private long processStdio_generator_period = 100;
  private Step processStdio_direction = Step.UP;
  private long processStdioCallbackPeriod;
  private long processStdio_callback_id;
  private byte processStdio_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickRED.class);
    uidString = config().getString("uid");
    uidBytes = Utils.uid2long(uidString);

    vertx.eventBus().consumer(uidString, message -> {
      Buffer msgBuffer = (Buffer) message.body();
      Packet packet = new Packet(msgBuffer);
      logger.trace("got request: {}", packet.toString());
      Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
      for (Object handlerid : handlerids) {
        Buffer buffer = callFunction(packet);
        // TODO add logging
        if (packet.getResponseExpected()) {
            if (buffer != null) {
              logger.trace(
                  "sending answer: {}", new Packet(buffer).toString());
              vertx.eventBus().publish((String) handlerid, buffer);
            } else {
              logger.trace("buffer is null");
            }
        }
      }
      });

    // broadcast queue for enumeration requests
    vertx.eventBus().consumer(
        CommonServices.BROADCAST_UID,
        message -> {
          Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
          if (handlerids != null) {
            logger.debug("sending enumerate answer");
            for (Object handlerid : handlerids) {
              vertx.eventBus().publish((String) handlerid,
                  Utils.getEnumerateResponse(uidString, uidBytes, DEVICE_IDENTIFIER));
            }
          } else {
            logger.error("no handlerids found");
          }
        });

    startStringLengthGenerator();
    startNextDirectoryEntryGenerator();
    startProcessesGenerator();
    startProcessIdentityGenerator();
    startLastSpawnedProgramProcessGenerator();
    startFileInfoGenerator();
    startProgramIdentifierGenerator();
    startProgramsGenerator();
    startListItemGenerator();
    startCustomProgramOptionNamesGenerator();
    startListLengthGenerator();
    startProgramRootDirectoryGenerator();
    startProcessStateGenerator();
    startProgramSchedulerStateGenerator();
    startProcessCommandGenerator();
    startDirectoryNameGenerator();
    startProcessStdioGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_CREATE_SESSION) {
      buffer = createSession(packet);
    }
    else if (functionId == FUNCTION_EXPIRE_SESSION) {
      buffer = expireSession(packet);
    }
    else if (functionId == FUNCTION_EXPIRE_SESSION_UNCHECKED) {
      buffer = expireSessionUnchecked(packet);
    }
    else if (functionId == FUNCTION_KEEP_SESSION_ALIVE) {
      buffer = keepSessionAlive(packet);
    }
    else if (functionId == FUNCTION_RELEASE_OBJECT) {
      buffer = releaseObject(packet);
    }
    else if (functionId == FUNCTION_RELEASE_OBJECT_UNCHECKED) {
      buffer = releaseObjectUnchecked(packet);
    }
    else if (functionId == FUNCTION_ALLOCATE_STRING) {
      buffer = allocateString(packet);
    }
    else if (functionId == FUNCTION_TRUNCATE_STRING) {
      buffer = truncateString(packet);
    }
    else if (functionId == FUNCTION_GET_STRING_LENGTH) {
      buffer = getStringLength(packet);
    }
    else if (functionId == FUNCTION_SET_STRING_CHUNK) {
      buffer = setStringChunk(packet);
    }
    else if (functionId == FUNCTION_GET_STRING_CHUNK) {
      buffer = getStringChunk(packet);
    }
    else if (functionId == FUNCTION_ALLOCATE_LIST) {
      buffer = allocateList(packet);
    }
    else if (functionId == FUNCTION_GET_LIST_LENGTH) {
      buffer = getListLength(packet);
    }
    else if (functionId == FUNCTION_GET_LIST_ITEM) {
      buffer = getListItem(packet);
    }
    else if (functionId == FUNCTION_APPEND_TO_LIST) {
      buffer = appendToList(packet);
    }
    else if (functionId == FUNCTION_REMOVE_FROM_LIST) {
      buffer = removeFromList(packet);
    }
    else if (functionId == FUNCTION_OPEN_FILE) {
      buffer = openFile(packet);
    }
    else if (functionId == FUNCTION_CREATE_PIPE) {
      buffer = createPipe(packet);
    }
    else if (functionId == FUNCTION_GET_FILE_INFO) {
      buffer = getFileInfo(packet);
    }
    else if (functionId == FUNCTION_READ_FILE) {
      buffer = readFile(packet);
    }
    else if (functionId == FUNCTION_READ_FILE_ASYNC) {
      buffer = readFileAsync(packet);
    }
    else if (functionId == FUNCTION_ABORT_ASYNC_FILE_READ) {
      buffer = abortAsyncFileRead(packet);
    }
    else if (functionId == FUNCTION_WRITE_FILE) {
      buffer = writeFile(packet);
    }
    else if (functionId == FUNCTION_WRITE_FILE_UNCHECKED) {
      buffer = writeFileUnchecked(packet);
    }
    else if (functionId == FUNCTION_WRITE_FILE_ASYNC) {
      buffer = writeFileAsync(packet);
    }
    else if (functionId == FUNCTION_SET_FILE_POSITION) {
      buffer = setFilePosition(packet);
    }
    else if (functionId == FUNCTION_GET_FILE_POSITION) {
      buffer = getFilePosition(packet);
    }
    else if (functionId == FUNCTION_SET_FILE_EVENTS) {
      buffer = setFileEvents(packet);
    }
    else if (functionId == FUNCTION_GET_FILE_EVENTS) {
      buffer = getFileEvents(packet);
    }
    else if (functionId == FUNCTION_OPEN_DIRECTORY) {
      buffer = openDirectory(packet);
    }
    else if (functionId == FUNCTION_GET_DIRECTORY_NAME) {
      buffer = getDirectoryName(packet);
    }
    else if (functionId == FUNCTION_GET_NEXT_DIRECTORY_ENTRY) {
      buffer = getNextDirectoryEntry(packet);
    }
    else if (functionId == FUNCTION_REWIND_DIRECTORY) {
      buffer = rewindDirectory(packet);
    }
    else if (functionId == FUNCTION_CREATE_DIRECTORY) {
      buffer = createDirectory(packet);
    }
    else if (functionId == FUNCTION_GET_PROCESSES) {
      buffer = getProcesses(packet);
    }
    else if (functionId == FUNCTION_SPAWN_PROCESS) {
      buffer = spawnProcess(packet);
    }
    else if (functionId == FUNCTION_KILL_PROCESS) {
      buffer = killProcess(packet);
    }
    else if (functionId == FUNCTION_GET_PROCESS_COMMAND) {
      buffer = getProcessCommand(packet);
    }
    else if (functionId == FUNCTION_GET_PROCESS_IDENTITY) {
      buffer = getProcessIdentity(packet);
    }
    else if (functionId == FUNCTION_GET_PROCESS_STDIO) {
      buffer = getProcessStdio(packet);
    }
    else if (functionId == FUNCTION_GET_PROCESS_STATE) {
      buffer = getProcessState(packet);
    }
    else if (functionId == FUNCTION_GET_PROGRAMS) {
      buffer = getPrograms(packet);
    }
    else if (functionId == FUNCTION_DEFINE_PROGRAM) {
      buffer = defineProgram(packet);
    }
    else if (functionId == FUNCTION_PURGE_PROGRAM) {
      buffer = purgeProgram(packet);
    }
    else if (functionId == FUNCTION_GET_PROGRAM_IDENTIFIER) {
      buffer = getProgramIdentifier(packet);
    }
    else if (functionId == FUNCTION_GET_PROGRAM_ROOT_DIRECTORY) {
      buffer = getProgramRootDirectory(packet);
    }
    else if (functionId == FUNCTION_SET_PROGRAM_COMMAND) {
      buffer = setProgramCommand(packet);
    }
    else if (functionId == FUNCTION_GET_PROGRAM_COMMAND) {
      buffer = getProgramCommand(packet);
    }
    else if (functionId == FUNCTION_SET_PROGRAM_STDIO_REDIRECTION) {
      buffer = setProgramStdioRedirection(packet);
    }
    else if (functionId == FUNCTION_GET_PROGRAM_STDIO_REDIRECTION) {
      buffer = getProgramStdioRedirection(packet);
    }
    else if (functionId == FUNCTION_SET_PROGRAM_SCHEDULE) {
      buffer = setProgramSchedule(packet);
    }
    else if (functionId == FUNCTION_GET_PROGRAM_SCHEDULE) {
      buffer = getProgramSchedule(packet);
    }
    else if (functionId == FUNCTION_GET_PROGRAM_SCHEDULER_STATE) {
      buffer = getProgramSchedulerState(packet);
    }
    else if (functionId == FUNCTION_CONTINUE_PROGRAM_SCHEDULE) {
      buffer = continueProgramSchedule(packet);
    }
    else if (functionId == FUNCTION_START_PROGRAM) {
      buffer = startProgram(packet);
    }
    else if (functionId == FUNCTION_GET_LAST_SPAWNED_PROGRAM_PROCESS) {
      buffer = getLastSpawnedProgramProcess(packet);
    }
    else if (functionId == FUNCTION_GET_CUSTOM_PROGRAM_OPTION_NAMES) {
      buffer = getCustomProgramOptionNames(packet);
    }
    else if (functionId == FUNCTION_SET_CUSTOM_PROGRAM_OPTION_VALUE) {
      buffer = setCustomProgramOptionValue(packet);
    }
    else if (functionId == FUNCTION_GET_CUSTOM_PROGRAM_OPTION_VALUE) {
      buffer = getCustomProgramOptionValue(packet);
    }
    else if (functionId == FUNCTION_REMOVE_CUSTOM_PROGRAM_OPTION) {
      buffer = removeCustomProgramOption(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startStringLengthGenerator() {
    if (stringLength_step == 0) {
      return;
    }
    vertx.setPeriodic(stringLength_generator_period, id -> {
      if (stringLength_direction == Step.UP) {
        if (stringLength >= stringLength_max) {
          stringLength_direction = Step.DOWN;
          this.stringLength = (byte) (stringLength - stringLength_step);
        } else {
          this.stringLength = (byte) (stringLength + stringLength_step);
        }
      } else {
        if (stringLength <= stringLength_min) {
          stringLength_direction = Step.UP;
          this.stringLength = (byte) (stringLength + stringLength_step);
        } else {
          this.stringLength = (byte) (stringLength - stringLength_step);
        }
      }
    });
  }
        
  private void startNextDirectoryEntryGenerator() {
    if (nextDirectoryEntry_step == 0) {
      return;
    }
    vertx.setPeriodic(nextDirectoryEntry_generator_period, id -> {
      if (nextDirectoryEntry_direction == Step.UP) {
        if (nextDirectoryEntry >= nextDirectoryEntry_max) {
          nextDirectoryEntry_direction = Step.DOWN;
          this.nextDirectoryEntry = (byte) (nextDirectoryEntry - nextDirectoryEntry_step);
        } else {
          this.nextDirectoryEntry = (byte) (nextDirectoryEntry + nextDirectoryEntry_step);
        }
      } else {
        if (nextDirectoryEntry <= nextDirectoryEntry_min) {
          nextDirectoryEntry_direction = Step.UP;
          this.nextDirectoryEntry = (byte) (nextDirectoryEntry + nextDirectoryEntry_step);
        } else {
          this.nextDirectoryEntry = (byte) (nextDirectoryEntry - nextDirectoryEntry_step);
        }
      }
    });
  }
        
  private void startProcessesGenerator() {
    if (processes_step == 0) {
      return;
    }
    vertx.setPeriodic(processes_generator_period, id -> {
      if (processes_direction == Step.UP) {
        if (processes >= processes_max) {
          processes_direction = Step.DOWN;
          this.processes = (byte) (processes - processes_step);
        } else {
          this.processes = (byte) (processes + processes_step);
        }
      } else {
        if (processes <= processes_min) {
          processes_direction = Step.UP;
          this.processes = (byte) (processes + processes_step);
        } else {
          this.processes = (byte) (processes - processes_step);
        }
      }
    });
  }
        
  private void startProcessIdentityGenerator() {
    if (processIdentity_step == 0) {
      return;
    }
    vertx.setPeriodic(processIdentity_generator_period, id -> {
      if (processIdentity_direction == Step.UP) {
        if (processIdentity >= processIdentity_max) {
          processIdentity_direction = Step.DOWN;
          this.processIdentity = (byte) (processIdentity - processIdentity_step);
        } else {
          this.processIdentity = (byte) (processIdentity + processIdentity_step);
        }
      } else {
        if (processIdentity <= processIdentity_min) {
          processIdentity_direction = Step.UP;
          this.processIdentity = (byte) (processIdentity + processIdentity_step);
        } else {
          this.processIdentity = (byte) (processIdentity - processIdentity_step);
        }
      }
    });
  }
        
  private void startLastSpawnedProgramProcessGenerator() {
    if (lastSpawnedProgramProcess_step == 0) {
      return;
    }
    vertx.setPeriodic(lastSpawnedProgramProcess_generator_period, id -> {
      if (lastSpawnedProgramProcess_direction == Step.UP) {
        if (lastSpawnedProgramProcess >= lastSpawnedProgramProcess_max) {
          lastSpawnedProgramProcess_direction = Step.DOWN;
          this.lastSpawnedProgramProcess = (byte) (lastSpawnedProgramProcess - lastSpawnedProgramProcess_step);
        } else {
          this.lastSpawnedProgramProcess = (byte) (lastSpawnedProgramProcess + lastSpawnedProgramProcess_step);
        }
      } else {
        if (lastSpawnedProgramProcess <= lastSpawnedProgramProcess_min) {
          lastSpawnedProgramProcess_direction = Step.UP;
          this.lastSpawnedProgramProcess = (byte) (lastSpawnedProgramProcess + lastSpawnedProgramProcess_step);
        } else {
          this.lastSpawnedProgramProcess = (byte) (lastSpawnedProgramProcess - lastSpawnedProgramProcess_step);
        }
      }
    });
  }
        
  private void startFileInfoGenerator() {
    if (fileInfo_step == 0) {
      return;
    }
    vertx.setPeriodic(fileInfo_generator_period, id -> {
      if (fileInfo_direction == Step.UP) {
        if (fileInfo >= fileInfo_max) {
          fileInfo_direction = Step.DOWN;
          this.fileInfo = (byte) (fileInfo - fileInfo_step);
        } else {
          this.fileInfo = (byte) (fileInfo + fileInfo_step);
        }
      } else {
        if (fileInfo <= fileInfo_min) {
          fileInfo_direction = Step.UP;
          this.fileInfo = (byte) (fileInfo + fileInfo_step);
        } else {
          this.fileInfo = (byte) (fileInfo - fileInfo_step);
        }
      }
    });
  }
        
  private void startProgramIdentifierGenerator() {
    if (programIdentifier_step == 0) {
      return;
    }
    vertx.setPeriodic(programIdentifier_generator_period, id -> {
      if (programIdentifier_direction == Step.UP) {
        if (programIdentifier >= programIdentifier_max) {
          programIdentifier_direction = Step.DOWN;
          this.programIdentifier = (byte) (programIdentifier - programIdentifier_step);
        } else {
          this.programIdentifier = (byte) (programIdentifier + programIdentifier_step);
        }
      } else {
        if (programIdentifier <= programIdentifier_min) {
          programIdentifier_direction = Step.UP;
          this.programIdentifier = (byte) (programIdentifier + programIdentifier_step);
        } else {
          this.programIdentifier = (byte) (programIdentifier - programIdentifier_step);
        }
      }
    });
  }
        
  private void startProgramsGenerator() {
    if (programs_step == 0) {
      return;
    }
    vertx.setPeriodic(programs_generator_period, id -> {
      if (programs_direction == Step.UP) {
        if (programs >= programs_max) {
          programs_direction = Step.DOWN;
          this.programs = (byte) (programs - programs_step);
        } else {
          this.programs = (byte) (programs + programs_step);
        }
      } else {
        if (programs <= programs_min) {
          programs_direction = Step.UP;
          this.programs = (byte) (programs + programs_step);
        } else {
          this.programs = (byte) (programs - programs_step);
        }
      }
    });
  }
        
  private void startListItemGenerator() {
    if (listItem_step == 0) {
      return;
    }
    vertx.setPeriodic(listItem_generator_period, id -> {
      if (listItem_direction == Step.UP) {
        if (listItem >= listItem_max) {
          listItem_direction = Step.DOWN;
          this.listItem = (byte) (listItem - listItem_step);
        } else {
          this.listItem = (byte) (listItem + listItem_step);
        }
      } else {
        if (listItem <= listItem_min) {
          listItem_direction = Step.UP;
          this.listItem = (byte) (listItem + listItem_step);
        } else {
          this.listItem = (byte) (listItem - listItem_step);
        }
      }
    });
  }
        
  private void startCustomProgramOptionNamesGenerator() {
    if (customProgramOptionNames_step == 0) {
      return;
    }
    vertx.setPeriodic(customProgramOptionNames_generator_period, id -> {
      if (customProgramOptionNames_direction == Step.UP) {
        if (customProgramOptionNames >= customProgramOptionNames_max) {
          customProgramOptionNames_direction = Step.DOWN;
          this.customProgramOptionNames = (byte) (customProgramOptionNames - customProgramOptionNames_step);
        } else {
          this.customProgramOptionNames = (byte) (customProgramOptionNames + customProgramOptionNames_step);
        }
      } else {
        if (customProgramOptionNames <= customProgramOptionNames_min) {
          customProgramOptionNames_direction = Step.UP;
          this.customProgramOptionNames = (byte) (customProgramOptionNames + customProgramOptionNames_step);
        } else {
          this.customProgramOptionNames = (byte) (customProgramOptionNames - customProgramOptionNames_step);
        }
      }
    });
  }
        
  private void startListLengthGenerator() {
    if (listLength_step == 0) {
      return;
    }
    vertx.setPeriodic(listLength_generator_period, id -> {
      if (listLength_direction == Step.UP) {
        if (listLength >= listLength_max) {
          listLength_direction = Step.DOWN;
          this.listLength = (byte) (listLength - listLength_step);
        } else {
          this.listLength = (byte) (listLength + listLength_step);
        }
      } else {
        if (listLength <= listLength_min) {
          listLength_direction = Step.UP;
          this.listLength = (byte) (listLength + listLength_step);
        } else {
          this.listLength = (byte) (listLength - listLength_step);
        }
      }
    });
  }
        
  private void startProgramRootDirectoryGenerator() {
    if (programRootDirectory_step == 0) {
      return;
    }
    vertx.setPeriodic(programRootDirectory_generator_period, id -> {
      if (programRootDirectory_direction == Step.UP) {
        if (programRootDirectory >= programRootDirectory_max) {
          programRootDirectory_direction = Step.DOWN;
          this.programRootDirectory = (byte) (programRootDirectory - programRootDirectory_step);
        } else {
          this.programRootDirectory = (byte) (programRootDirectory + programRootDirectory_step);
        }
      } else {
        if (programRootDirectory <= programRootDirectory_min) {
          programRootDirectory_direction = Step.UP;
          this.programRootDirectory = (byte) (programRootDirectory + programRootDirectory_step);
        } else {
          this.programRootDirectory = (byte) (programRootDirectory - programRootDirectory_step);
        }
      }
    });
  }
        
  private void startProcessStateGenerator() {
    if (processState_step == 0) {
      return;
    }
    vertx.setPeriodic(processState_generator_period, id -> {
      if (processState_direction == Step.UP) {
        if (processState >= processState_max) {
          processState_direction = Step.DOWN;
          this.processState = (byte) (processState - processState_step);
        } else {
          this.processState = (byte) (processState + processState_step);
        }
      } else {
        if (processState <= processState_min) {
          processState_direction = Step.UP;
          this.processState = (byte) (processState + processState_step);
        } else {
          this.processState = (byte) (processState - processState_step);
        }
      }
    });
  }
        
  private void startProgramSchedulerStateGenerator() {
    if (programSchedulerState_step == 0) {
      return;
    }
    vertx.setPeriodic(programSchedulerState_generator_period, id -> {
      if (programSchedulerState_direction == Step.UP) {
        if (programSchedulerState >= programSchedulerState_max) {
          programSchedulerState_direction = Step.DOWN;
          this.programSchedulerState = (byte) (programSchedulerState - programSchedulerState_step);
        } else {
          this.programSchedulerState = (byte) (programSchedulerState + programSchedulerState_step);
        }
      } else {
        if (programSchedulerState <= programSchedulerState_min) {
          programSchedulerState_direction = Step.UP;
          this.programSchedulerState = (byte) (programSchedulerState + programSchedulerState_step);
        } else {
          this.programSchedulerState = (byte) (programSchedulerState - programSchedulerState_step);
        }
      }
    });
  }
        
  private void startProcessCommandGenerator() {
    if (processCommand_step == 0) {
      return;
    }
    vertx.setPeriodic(processCommand_generator_period, id -> {
      if (processCommand_direction == Step.UP) {
        if (processCommand >= processCommand_max) {
          processCommand_direction = Step.DOWN;
          this.processCommand = (byte) (processCommand - processCommand_step);
        } else {
          this.processCommand = (byte) (processCommand + processCommand_step);
        }
      } else {
        if (processCommand <= processCommand_min) {
          processCommand_direction = Step.UP;
          this.processCommand = (byte) (processCommand + processCommand_step);
        } else {
          this.processCommand = (byte) (processCommand - processCommand_step);
        }
      }
    });
  }
        
  private void startDirectoryNameGenerator() {
    if (directoryName_step == 0) {
      return;
    }
    vertx.setPeriodic(directoryName_generator_period, id -> {
      if (directoryName_direction == Step.UP) {
        if (directoryName >= directoryName_max) {
          directoryName_direction = Step.DOWN;
          this.directoryName = (byte) (directoryName - directoryName_step);
        } else {
          this.directoryName = (byte) (directoryName + directoryName_step);
        }
      } else {
        if (directoryName <= directoryName_min) {
          directoryName_direction = Step.UP;
          this.directoryName = (byte) (directoryName + directoryName_step);
        } else {
          this.directoryName = (byte) (directoryName - directoryName_step);
        }
      }
    });
  }
        
  private void startProcessStdioGenerator() {
    if (processStdio_step == 0) {
      return;
    }
    vertx.setPeriodic(processStdio_generator_period, id -> {
      if (processStdio_direction == Step.UP) {
        if (processStdio >= processStdio_max) {
          processStdio_direction = Step.DOWN;
          this.processStdio = (byte) (processStdio - processStdio_step);
        } else {
          this.processStdio = (byte) (processStdio + processStdio_step);
        }
      } else {
        if (processStdio <= processStdio_min) {
          processStdio_direction = Step.UP;
          this.processStdio = (byte) (processStdio + processStdio_step);
        } else {
          this.processStdio = (byte) (processStdio - processStdio_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor fileEventsOccurred
//fixme start_generator callback without sensor programSchedulerStateChanged
//fixme start_generator callback without sensor programProcessSpawned
//fixme start_generator callback without sensor asyncFileWrite
//fixme start_generator callback without sensor asyncFileRead
//fixme start_generator callback without sensor processStateChanged
//fixme stop_generator callback without sensor fileEventsOccurred
//fixme stop_generator callback without sensor programSchedulerStateChanged
//fixme stop_generator callback without sensor programProcessSpawned
//fixme stop_generator callback without sensor asyncFileWrite
//fixme stop_generator callback without sensor asyncFileRead
//fixme stop_generator callback without sensor processStateChanged
//fixme getter callback without sensor fileEventsOccurred
//fixme getter callback without sensor programSchedulerStateChanged
//fixme getter callback without sensor programProcessSpawned
//fixme getter callback without sensor asyncFileWrite
//fixme getter callback without sensor asyncFileRead
//fixme getter callback without sensor processStateChanged

  private Buffer getStringLengthBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 5;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(stringLength));
    buffer.appendBytes(Utils.getUInt32(stringLength));

    return buffer;
  }
        
  private Buffer getStringLengthBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getStringLengthBuffer(FUNCTION_GET_STRING_LENGTH, options);
  }

  private Buffer getStringLength(Packet packet) {
    logger.debug("function getStringLength");
    if (packet.getResponseExpected()) {
      return getStringLengthBuffer(packet);
    }
    return null;
  }

  private Buffer getListLengthBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(listLength));
    buffer.appendBytes(Utils.getUInt16(listLength));

    return buffer;
  }
        
  private Buffer getListLengthBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getListLengthBuffer(FUNCTION_GET_LIST_LENGTH, options);
  }

  private Buffer getListLength(Packet packet) {
    logger.debug("function getListLength");
    if (packet.getResponseExpected()) {
      return getListLengthBuffer(packet);
    }
    return null;
  }

  private Buffer getListItemBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(listItem));
    buffer.appendBytes(Utils.getUInt16(listItem));
    buffer.appendBytes(Utils.getUInt8A(listItem));

    return buffer;
  }
        
  private Buffer getListItemBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getListItemBuffer(FUNCTION_GET_LIST_ITEM, options);
  }

  private Buffer getListItem(Packet packet) {
    logger.debug("function getListItem");
    if (packet.getResponseExpected()) {
      return getListItemBuffer(packet);
    }
    return null;
  }

  private Buffer getFileInfoBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 50;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(fileInfo));
    buffer.appendBytes(Utils.getUInt8A(fileInfo));
    buffer.appendBytes(Utils.getUInt16(fileInfo));
    buffer.appendBytes(Utils.getUInt32(fileInfo));
    buffer.appendBytes(Utils.getUInt16(fileInfo));
    buffer.appendBytes(Utils.getUInt32(fileInfo));
    buffer.appendBytes(Utils.getUInt32(fileInfo));
    buffer.appendBytes(Utils.longBuffer(fileInfo));
    buffer.appendBytes(Utils.longBuffer(fileInfo));
    buffer.appendBytes(Utils.longBuffer(fileInfo));
    buffer.appendBytes(Utils.longBuffer(fileInfo));

    return buffer;
  }
        
  private Buffer getFileInfoBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getFileInfoBuffer(FUNCTION_GET_FILE_INFO, options);
  }

  private Buffer getFileInfo(Packet packet) {
    logger.debug("function getFileInfo");
    if (packet.getResponseExpected()) {
      return getFileInfoBuffer(packet);
    }
    return null;
  }

  private Buffer getDirectoryNameBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(directoryName));
    buffer.appendBytes(Utils.getUInt16(directoryName));

    return buffer;
  }
        
  private Buffer getDirectoryNameBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getDirectoryNameBuffer(FUNCTION_GET_DIRECTORY_NAME, options);
  }

  private Buffer getDirectoryName(Packet packet) {
    logger.debug("function getDirectoryName");
    if (packet.getResponseExpected()) {
      return getDirectoryNameBuffer(packet);
    }
    return null;
  }

  private Buffer getNextDirectoryEntryBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(nextDirectoryEntry));
    buffer.appendBytes(Utils.getUInt16(nextDirectoryEntry));
    buffer.appendBytes(Utils.getUInt8A(nextDirectoryEntry));

    return buffer;
  }
        
  private Buffer getNextDirectoryEntryBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getNextDirectoryEntryBuffer(FUNCTION_GET_NEXT_DIRECTORY_ENTRY, options);
  }

  private Buffer getNextDirectoryEntry(Packet packet) {
    logger.debug("function getNextDirectoryEntry");
    if (packet.getResponseExpected()) {
      return getNextDirectoryEntryBuffer(packet);
    }
    return null;
  }

  private Buffer getProcessesBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(processes));
    buffer.appendBytes(Utils.getUInt16(processes));

    return buffer;
  }
        
  private Buffer getProcessesBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProcessesBuffer(FUNCTION_GET_PROCESSES, options);
  }

  private Buffer getProcesses(Packet packet) {
    logger.debug("function getProcesses");
    if (packet.getResponseExpected()) {
      return getProcessesBuffer(packet);
    }
    return null;
  }

  private Buffer getProcessCommandBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 9;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(processCommand));
    buffer.appendBytes(Utils.getUInt16(processCommand));
    buffer.appendBytes(Utils.getUInt16(processCommand));
    buffer.appendBytes(Utils.getUInt16(processCommand));
    buffer.appendBytes(Utils.getUInt16(processCommand));

    return buffer;
  }
        
  private Buffer getProcessCommandBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProcessCommandBuffer(FUNCTION_GET_PROCESS_COMMAND, options);
  }

  private Buffer getProcessCommand(Packet packet) {
    logger.debug("function getProcessCommand");
    if (packet.getResponseExpected()) {
      return getProcessCommandBuffer(packet);
    }
    return null;
  }

  private Buffer getProcessIdentityBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 13;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(processIdentity));
    buffer.appendBytes(Utils.getUInt32(processIdentity));
    buffer.appendBytes(Utils.getUInt32(processIdentity));
    buffer.appendBytes(Utils.getUInt32(processIdentity));

    return buffer;
  }
        
  private Buffer getProcessIdentityBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProcessIdentityBuffer(FUNCTION_GET_PROCESS_IDENTITY, options);
  }

  private Buffer getProcessIdentity(Packet packet) {
    logger.debug("function getProcessIdentity");
    if (packet.getResponseExpected()) {
      return getProcessIdentityBuffer(packet);
    }
    return null;
  }

  private Buffer getProcessStdioBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 7;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(processStdio));
    buffer.appendBytes(Utils.getUInt16(processStdio));
    buffer.appendBytes(Utils.getUInt16(processStdio));
    buffer.appendBytes(Utils.getUInt16(processStdio));

    return buffer;
  }
        
  private Buffer getProcessStdioBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProcessStdioBuffer(FUNCTION_GET_PROCESS_STDIO, options);
  }

  private Buffer getProcessStdio(Packet packet) {
    logger.debug("function getProcessStdio");
    if (packet.getResponseExpected()) {
      return getProcessStdioBuffer(packet);
    }
    return null;
  }

  private Buffer getProcessStateBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 11;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(processState));
    buffer.appendBytes(Utils.getUInt8A(processState));
    buffer.appendBytes(Utils.longBuffer(processState));
    buffer.appendBytes(Utils.getUInt8A(processState));

    return buffer;
  }
        
  private Buffer getProcessStateBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProcessStateBuffer(FUNCTION_GET_PROCESS_STATE, options);
  }

  private Buffer getProcessState(Packet packet) {
    logger.debug("function getProcessState");
    if (packet.getResponseExpected()) {
      return getProcessStateBuffer(packet);
    }
    return null;
  }

  private Buffer getProgramsBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(programs));
    buffer.appendBytes(Utils.getUInt16(programs));

    return buffer;
  }
        
  private Buffer getProgramsBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProgramsBuffer(FUNCTION_GET_PROGRAMS, options);
  }

  private Buffer getPrograms(Packet packet) {
    logger.debug("function getPrograms");
    if (packet.getResponseExpected()) {
      return getProgramsBuffer(packet);
    }
    return null;
  }

  private Buffer getProgramIdentifierBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(programIdentifier));
    buffer.appendBytes(Utils.getUInt16(programIdentifier));

    return buffer;
  }
        
  private Buffer getProgramIdentifierBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProgramIdentifierBuffer(FUNCTION_GET_PROGRAM_IDENTIFIER, options);
  }

  private Buffer getProgramIdentifier(Packet packet) {
    logger.debug("function getProgramIdentifier");
    if (packet.getResponseExpected()) {
      return getProgramIdentifierBuffer(packet);
    }
    return null;
  }

  private Buffer getProgramRootDirectoryBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(programRootDirectory));
    buffer.appendBytes(Utils.getUInt16(programRootDirectory));

    return buffer;
  }
        
  private Buffer getProgramRootDirectoryBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProgramRootDirectoryBuffer(FUNCTION_GET_PROGRAM_ROOT_DIRECTORY, options);
  }

  private Buffer getProgramRootDirectory(Packet packet) {
    logger.debug("function getProgramRootDirectory");
    if (packet.getResponseExpected()) {
      return getProgramRootDirectoryBuffer(packet);
    }
    return null;
  }

  private Buffer getProgramSchedulerStateBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 12;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(programSchedulerState));
    buffer.appendBytes(Utils.getUInt8A(programSchedulerState));
    buffer.appendBytes(Utils.longBuffer(programSchedulerState));
    buffer.appendBytes(Utils.getUInt16(programSchedulerState));

    return buffer;
  }
        
  private Buffer getProgramSchedulerStateBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getProgramSchedulerStateBuffer(FUNCTION_GET_PROGRAM_SCHEDULER_STATE, options);
  }

  private Buffer getProgramSchedulerState(Packet packet) {
    logger.debug("function getProgramSchedulerState");
    if (packet.getResponseExpected()) {
      return getProgramSchedulerStateBuffer(packet);
    }
    return null;
  }

  private Buffer getLastSpawnedProgramProcessBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 11;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(lastSpawnedProgramProcess));
    buffer.appendBytes(Utils.getUInt16(lastSpawnedProgramProcess));
    buffer.appendBytes(Utils.longBuffer(lastSpawnedProgramProcess));

    return buffer;
  }
        
  private Buffer getLastSpawnedProgramProcessBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getLastSpawnedProgramProcessBuffer(FUNCTION_GET_LAST_SPAWNED_PROGRAM_PROCESS, options);
  }

  private Buffer getLastSpawnedProgramProcess(Packet packet) {
    logger.debug("function getLastSpawnedProgramProcess");
    if (packet.getResponseExpected()) {
      return getLastSpawnedProgramProcessBuffer(packet);
    }
    return null;
  }

  private Buffer getCustomProgramOptionNamesBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(customProgramOptionNames));
    buffer.appendBytes(Utils.getUInt16(customProgramOptionNames));

    return buffer;
  }
        
  private Buffer getCustomProgramOptionNamesBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCustomProgramOptionNamesBuffer(FUNCTION_GET_CUSTOM_PROGRAM_OPTION_NAMES, options);
  }

  private Buffer getCustomProgramOptionNames(Packet packet) {
    logger.debug("function getCustomProgramOptionNames");
    if (packet.getResponseExpected()) {
      return getCustomProgramOptionNamesBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer releaseObjectUnchecked(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer openFile(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer allocateList(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer createPipe(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer expireSession(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer spawnProcess(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer killProcess(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer writeFileUnchecked(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer removeFromList(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer readFileAsync(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer startProgram(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer rewindDirectory(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer readFile(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer truncateString(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer writeFile(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer expireSessionUnchecked(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer createSession(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer abortAsyncFileRead(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer appendToList(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer openDirectory(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer continueProgramSchedule(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer allocateString(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer releaseObject(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer removeCustomProgramOption(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer purgeProgram(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer keepSessionAlive(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer defineProgram(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer writeFileAsync(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer createDirectory(Packet packet) {
    //TODO dummy method
    return null;
  }
}
