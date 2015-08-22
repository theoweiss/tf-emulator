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
  private Buffer programStdioRedirection = getProgramStdioRedirectionDefault();
  private Buffer programSchedule = getProgramScheduleDefault();
  private Buffer filePosition = getFilePositionDefault();
  private Buffer fileEvents = getFileEventsDefault();
  private Buffer customProgramOptionValue = getCustomProgramOptionValueDefault();
  private Buffer programCommand = getProgramCommandDefault();

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


  /**
   * 
   */
  private Buffer getStringLength(Packet packet) {
    logger.debug("function getStringLength");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_STRING_LENGTH;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getNextDirectoryEntry(Packet packet) {
    logger.debug("function getNextDirectoryEntry");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_NEXT_DIRECTORY_ENTRY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProcesses(Packet packet) {
    logger.debug("function getProcesses");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_PROCESSES;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProcessIdentity(Packet packet) {
    logger.debug("function getProcessIdentity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 13;
      byte functionId = FUNCTION_GET_PROCESS_IDENTITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getLastSpawnedProgramProcess(Packet packet) {
    logger.debug("function getLastSpawnedProgramProcess");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 11;
      byte functionId = FUNCTION_GET_LAST_SPAWNED_PROGRAM_PROCESS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getFileInfo(Packet packet) {
    logger.debug("function getFileInfo");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 50;
      byte functionId = FUNCTION_GET_FILE_INFO;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProgramIdentifier(Packet packet) {
    logger.debug("function getProgramIdentifier");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_PROGRAM_IDENTIFIER;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getPrograms(Packet packet) {
    logger.debug("function getPrograms");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_PROGRAMS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getListItem(Packet packet) {
    logger.debug("function getListItem");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_LIST_ITEM;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getCustomProgramOptionNames(Packet packet) {
    logger.debug("function getCustomProgramOptionNames");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_CUSTOM_PROGRAM_OPTION_NAMES;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getListLength(Packet packet) {
    logger.debug("function getListLength");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_LIST_LENGTH;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProgramRootDirectory(Packet packet) {
    logger.debug("function getProgramRootDirectory");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_PROGRAM_ROOT_DIRECTORY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProcessState(Packet packet) {
    logger.debug("function getProcessState");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 11;
      byte functionId = FUNCTION_GET_PROCESS_STATE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProgramSchedulerState(Packet packet) {
    logger.debug("function getProgramSchedulerState");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 12;
      byte functionId = FUNCTION_GET_PROGRAM_SCHEDULER_STATE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProcessCommand(Packet packet) {
    logger.debug("function getProcessCommand");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_PROCESS_COMMAND;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getDirectoryName(Packet packet) {
    logger.debug("function getDirectoryName");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_DIRECTORY_NAME;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getProcessStdio(Packet packet) {
    logger.debug("function getProcessStdio");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 7;
      byte functionId = FUNCTION_GET_PROCESS_STDIO;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getStringChunk(Packet packet) {
    logger.debug("function getStringChunk");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 64;
      byte functionId = FUNCTION_GET_STRING_CHUNK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.stringChunk);
      return buffer;
    }

    return null;
  }

  private Buffer getStringChunkDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.getCharRandomValue(63));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getProgramStdioRedirection(Packet packet) {
    logger.debug("function getProgramStdioRedirection");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 10;
      byte functionId = FUNCTION_GET_PROGRAM_STDIO_REDIRECTION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.programStdioRedirection);
      return buffer;
    }

    return null;
  }

  private Buffer getProgramStdioRedirectionDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getProgramSchedule(Packet packet) {
    logger.debug("function getProgramSchedule");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_PROGRAM_SCHEDULE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.programSchedule);
      return buffer;
    }

    return null;
  }

  private Buffer getProgramScheduleDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.getBoolRandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getFilePosition(Packet packet) {
    logger.debug("function getFilePosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_FILE_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.filePosition);
      return buffer;
    }

    return null;
  }

  private Buffer getFilePositionDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get8ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getFileEvents(Packet packet) {
    logger.debug("function getFileEvents");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_FILE_EVENTS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.fileEvents);
      return buffer;
    }

    return null;
  }

  private Buffer getFileEventsDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getCustomProgramOptionValue(Packet packet) {
    logger.debug("function getCustomProgramOptionValue");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_CUSTOM_PROGRAM_OPTION_VALUE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.customProgramOptionValue);
      return buffer;
    }

    return null;
  }

  private Buffer getCustomProgramOptionValueDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getProgramCommand(Packet packet) {
    logger.debug("function getProgramCommand");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_PROGRAM_COMMAND;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.programCommand);
      return buffer;
    }

    return null;
  }

  private Buffer getProgramCommandDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer setStringChunk(Packet packet) {
    logger.debug("function setStringChunk");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_SET_STRING_CHUNK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.stringChunk = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setProgramStdioRedirection(Packet packet) {
    logger.debug("function setProgramStdioRedirection");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_SET_PROGRAM_STDIO_REDIRECTION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.programStdioRedirection = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setProgramCommand(Packet packet) {
    logger.debug("function setProgramCommand");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_SET_PROGRAM_COMMAND;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.programCommand = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setProgramSchedule(Packet packet) {
    logger.debug("function setProgramSchedule");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_SET_PROGRAM_SCHEDULE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.programSchedule = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setFilePosition(Packet packet) {
    logger.debug("function setFilePosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_SET_FILE_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.filePosition = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setFileEvents(Packet packet) {
    logger.debug("function setFileEvents");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_SET_FILE_EVENTS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.fileEvents = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setCustomProgramOptionValue(Packet packet) {
    logger.debug("function setCustomProgramOptionValue");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_SET_CUSTOM_PROGRAM_OPTION_VALUE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.customProgramOptionValue = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer getIdentity(Packet packet) {
    logger.debug("function getIdentity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 25;
      byte functionId = FUNCTION_GET_IDENTITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
       buffer.appendBuffer(Utils.getIdentityPayload(uidString, uidBytes, DEVICE_IDENTIFIER));
      return buffer;
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
