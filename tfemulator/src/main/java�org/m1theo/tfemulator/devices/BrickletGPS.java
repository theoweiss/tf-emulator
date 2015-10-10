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
 * Determine position, velocity and altitude using GPS
 */
public class BrickletGPS extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 222;
public final static String DEVICE_DISPLAY_NAME = "GPS Bricklet";

  public final static byte FUNCTION_GET_COORDINATES = (byte)1;
  public final static byte FUNCTION_GET_STATUS = (byte)2;
  public final static byte FUNCTION_GET_ALTITUDE = (byte)3;
  public final static byte FUNCTION_GET_MOTION = (byte)4;
  public final static byte FUNCTION_GET_DATE_TIME = (byte)5;
  public final static byte FUNCTION_RESTART = (byte)6;
  public final static byte FUNCTION_SET_COORDINATES_CALLBACK_PERIOD = (byte)7;
  public final static byte FUNCTION_GET_COORDINATES_CALLBACK_PERIOD = (byte)8;
  public final static byte FUNCTION_SET_STATUS_CALLBACK_PERIOD = (byte)9;
  public final static byte FUNCTION_GET_STATUS_CALLBACK_PERIOD = (byte)10;
  public final static byte FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD = (byte)11;
  public final static byte FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD = (byte)12;
  public final static byte FUNCTION_SET_MOTION_CALLBACK_PERIOD = (byte)13;
  public final static byte FUNCTION_GET_MOTION_CALLBACK_PERIOD = (byte)14;
  public final static byte FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD = (byte)15;
  public final static byte FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD = (byte)16;
  public final static byte CALLBACK_COORDINATES = (byte)17;
  public final static byte CALLBACK_STATUS = (byte)18;
  public final static byte CALLBACK_ALTITUDE = (byte)19;
  public final static byte CALLBACK_MOTION = (byte)20;
  public final static byte CALLBACK_DATE_TIME = (byte)21;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short FIX_NO_FIX = (short)1;
  public final static short FIX_2D_FIX = (short)2;
  public final static short FIX_3D_FIX = (short)3;
  public final static short RESTART_TYPE_HOT_START = (short)0;
  public final static short RESTART_TYPE_WARM_START = (short)1;
  public final static short RESTART_TYPE_COLD_START = (short)2;
  public final static short RESTART_TYPE_FACTORY_RESET = (short)3;
  String uidString;

  private int dateTime = 100;
  private int dateTime_max = 1000;
  private int dateTime_min = 0;
  private int dateTime_step = 1;
  private long dateTime_generator_period = 100;
  private Step dateTime_direction = Step.UP;
  private long dateTimeCallbackPeriod;
  private long dateTime_callback_id;
  private int dateTime_last_value_called_back;

  private byte status = 100;
  private byte status_max = 1000;
  private byte status_min = 0;
  private byte status_step = 1;
  private long status_generator_period = 100;
  private Step status_direction = Step.UP;
  private long statusCallbackPeriod;
  private long status_callback_id;
  private byte status_last_value_called_back;

  private int motion = 100;
  private int motion_max = 1000;
  private int motion_min = 0;
  private int motion_step = 1;
  private long motion_generator_period = 100;
  private Step motion_direction = Step.UP;
  private long motionCallbackPeriod;
  private long motion_callback_id;
  private int motion_last_value_called_back;

  private int altitude = 100;
  private int altitude_max = 1000;
  private int altitude_min = 0;
  private int altitude_step = 1;
  private long altitude_generator_period = 100;
  private Step altitude_direction = Step.UP;
  private long altitudeCallbackPeriod;
  private long altitude_callback_id;
  private int altitude_last_value_called_back;

  private int coordinates = 100;
  private int coordinates_max = 1000;
  private int coordinates_min = 0;
  private int coordinates_step = 1;
  private long coordinates_generator_period = 100;
  private Step coordinates_direction = Step.UP;
  private long coordinatesCallbackPeriod;
  private long coordinates_callback_id;
  private int coordinates_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletGPS.class);
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

    startDateTimeGenerator();
    startStatusGenerator();
    startMotionGenerator();
    startAltitudeGenerator();
    startCoordinatesGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_COORDINATES) {
      buffer = getCoordinates(packet);
    }
    else if (functionId == FUNCTION_GET_STATUS) {
      buffer = getStatus(packet);
    }
    else if (functionId == FUNCTION_GET_ALTITUDE) {
      buffer = getAltitude(packet);
    }
    else if (functionId == FUNCTION_GET_MOTION) {
      buffer = getMotion(packet);
    }
    else if (functionId == FUNCTION_GET_DATE_TIME) {
      buffer = getDateTime(packet);
    }
    else if (functionId == FUNCTION_RESTART) {
      buffer = restart(packet);
    }
    else if (functionId == FUNCTION_SET_COORDINATES_CALLBACK_PERIOD) {
      buffer = setCoordinatesCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_COORDINATES_CALLBACK_PERIOD) {
      buffer = getCoordinatesCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_STATUS_CALLBACK_PERIOD) {
      buffer = setStatusCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_STATUS_CALLBACK_PERIOD) {
      buffer = getStatusCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD) {
      buffer = setAltitudeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD) {
      buffer = getAltitudeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_MOTION_CALLBACK_PERIOD) {
      buffer = setMotionCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_MOTION_CALLBACK_PERIOD) {
      buffer = getMotionCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD) {
      buffer = setDateTimeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD) {
      buffer = getDateTimeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startDateTimeGenerator() {
    if (dateTime_step == 0) {
      return;
    }
    vertx.setPeriodic(dateTime_generator_period, id -> {
      if (dateTime_direction == Step.UP) {
        if (dateTime >= dateTime_max) {
          dateTime_direction = Step.DOWN;
          this.dateTime = (int) (dateTime - dateTime_step);
        } else {
          this.dateTime = (int) (dateTime + dateTime_step);
        }
      } else {
        if (dateTime <= dateTime_min) {
          dateTime_direction = Step.UP;
          this.dateTime = (int) (dateTime + dateTime_step);
        } else {
          this.dateTime = (int) (dateTime - dateTime_step);
        }
      }
    });
  }
        
  private void startStatusGenerator() {
    if (status_step == 0) {
      return;
    }
    vertx.setPeriodic(status_generator_period, id -> {
      if (status_direction == Step.UP) {
        if (status >= status_max) {
          status_direction = Step.DOWN;
          this.status = (byte) (status - status_step);
        } else {
          this.status = (byte) (status + status_step);
        }
      } else {
        if (status <= status_min) {
          status_direction = Step.UP;
          this.status = (byte) (status + status_step);
        } else {
          this.status = (byte) (status - status_step);
        }
      }
    });
  }
        
  private void startMotionGenerator() {
    if (motion_step == 0) {
      return;
    }
    vertx.setPeriodic(motion_generator_period, id -> {
      if (motion_direction == Step.UP) {
        if (motion >= motion_max) {
          motion_direction = Step.DOWN;
          this.motion = (int) (motion - motion_step);
        } else {
          this.motion = (int) (motion + motion_step);
        }
      } else {
        if (motion <= motion_min) {
          motion_direction = Step.UP;
          this.motion = (int) (motion + motion_step);
        } else {
          this.motion = (int) (motion - motion_step);
        }
      }
    });
  }
        
  private void startAltitudeGenerator() {
    if (altitude_step == 0) {
      return;
    }
    vertx.setPeriodic(altitude_generator_period, id -> {
      if (altitude_direction == Step.UP) {
        if (altitude >= altitude_max) {
          altitude_direction = Step.DOWN;
          this.altitude = (int) (altitude - altitude_step);
        } else {
          this.altitude = (int) (altitude + altitude_step);
        }
      } else {
        if (altitude <= altitude_min) {
          altitude_direction = Step.UP;
          this.altitude = (int) (altitude + altitude_step);
        } else {
          this.altitude = (int) (altitude - altitude_step);
        }
      }
    });
  }
        
  private void startCoordinatesGenerator() {
    if (coordinates_step == 0) {
      return;
    }
    vertx.setPeriodic(coordinates_generator_period, id -> {
      if (coordinates_direction == Step.UP) {
        if (coordinates >= coordinates_max) {
          coordinates_direction = Step.DOWN;
          this.coordinates = (int) (coordinates - coordinates_step);
        } else {
          this.coordinates = (int) (coordinates + coordinates_step);
        }
      } else {
        if (coordinates <= coordinates_min) {
          coordinates_direction = Step.UP;
          this.coordinates = (int) (coordinates + coordinates_step);
        } else {
          this.coordinates = (int) (coordinates - coordinates_step);
        }
      }
    });
  }
        
    private void stopStatusCallback() {
        vertx.cancelTimer(status_callback_id);
  }
        //fixme start_generator callback without sensor status

    private void stopMotionCallback() {
        vertx.cancelTimer(motion_callback_id);
  }
        //fixme start_generator callback without sensor motion

    private void stopAltitudeCallback() {
        vertx.cancelTimer(altitude_callback_id);
  }
        //fixme start_generator callback without sensor altitude

    private void stopCoordinatesCallback() {
        vertx.cancelTimer(coordinates_callback_id);
  }
        //fixme start_generator callback without sensor coordinates

    private void stopDateTimeCallback() {
        vertx.cancelTimer(dateTime_callback_id);
  }
        //fixme start_generator callback without sensor dateTime

  private void startStatusCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    status_callback_id = vertx.setPeriodic(statusCallbackPeriod, id -> {
      if (status != status_last_value_called_back) {
        status_last_value_called_back = status;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("status sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getStatus4Callback());
          }
        } else {
          logger.info("no handlerids found in status callback");
        }
      } else {
        logger.debug("status value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor status

  private void startMotionCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    motion_callback_id = vertx.setPeriodic(motionCallbackPeriod, id -> {
      if (motion != motion_last_value_called_back) {
        motion_last_value_called_back = motion;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("motion sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getMotion4Callback());
          }
        } else {
          logger.info("no handlerids found in motion callback");
        }
      } else {
        logger.debug("motion value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor motion

  private void startAltitudeCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    altitude_callback_id = vertx.setPeriodic(altitudeCallbackPeriod, id -> {
      if (altitude != altitude_last_value_called_back) {
        altitude_last_value_called_back = altitude;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("altitude sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAltitude4Callback());
          }
        } else {
          logger.info("no handlerids found in altitude callback");
        }
      } else {
        logger.debug("altitude value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor altitude

  private void startCoordinatesCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    coordinates_callback_id = vertx.setPeriodic(coordinatesCallbackPeriod, id -> {
      if (coordinates != coordinates_last_value_called_back) {
        coordinates_last_value_called_back = coordinates;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("coordinates sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getCoordinates4Callback());
          }
        } else {
          logger.info("no handlerids found in coordinates callback");
        }
      } else {
        logger.debug("coordinates value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor coordinates

  private void startDateTimeCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    dateTime_callback_id = vertx.setPeriodic(dateTimeCallbackPeriod, id -> {
      if (dateTime != dateTime_last_value_called_back) {
        dateTime_last_value_called_back = dateTime;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("dateTime sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getDateTime4Callback());
          }
        } else {
          logger.info("no handlerids found in dateTime callback");
        }
      } else {
        logger.debug("dateTime value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor dateTime

  private Buffer getStatus4Callback() {
      byte options = (byte) 0;
      return getStatusBuffer(CALLBACK_STATUS, options);
  }
        //fixme getter callback without sensor status

  private Buffer getMotion4Callback() {
      byte options = (byte) 0;
      return getMotionBuffer(CALLBACK_MOTION, options);
  }
        //fixme getter callback without sensor motion

  private Buffer getAltitude4Callback() {
      byte options = (byte) 0;
      return getAltitudeBuffer(CALLBACK_ALTITUDE, options);
  }
        //fixme getter callback without sensor altitude

  private Buffer getCoordinates4Callback() {
      byte options = (byte) 0;
      return getCoordinatesBuffer(CALLBACK_COORDINATES, options);
  }
        //fixme getter callback without sensor coordinates

  private Buffer getDateTime4Callback() {
      byte options = (byte) 0;
      return getDateTimeBuffer(CALLBACK_DATE_TIME, options);
  }
        //fixme getter callback without sensor dateTime

  private Buffer setCoordinatesCallbackPeriod(Packet packet) {
    coordinatesCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (coordinatesCallbackPeriod == 0) {
      stopCoordinatesCallback();
    } else {
      startCoordinatesCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COORDINATES_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setStatusCallbackPeriod(Packet packet) {
    statusCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (statusCallbackPeriod == 0) {
      stopStatusCallback();
    } else {
      startStatusCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STATUS_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setAltitudeCallbackPeriod(Packet packet) {
    altitudeCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (altitudeCallbackPeriod == 0) {
      stopAltitudeCallback();
    } else {
      startAltitudeCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setMotionCallbackPeriod(Packet packet) {
    motionCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (motionCallbackPeriod == 0) {
      stopMotionCallback();
    } else {
      startMotionCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MOTION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setDateTimeCallbackPeriod(Packet packet) {
    dateTimeCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (dateTimeCallbackPeriod == 0) {
      stopDateTimeCallback();
    } else {
      startDateTimeCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getCoordinatesBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 18;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(coordinates));
    buffer.appendBytes(Utils.getUInt8(coordinates));
    buffer.appendBytes(Utils.getUInt32(coordinates));
    buffer.appendBytes(Utils.getUInt8(coordinates));
    buffer.appendBytes(Utils.getUInt16(coordinates));
    buffer.appendBytes(Utils.getUInt16(coordinates));
    buffer.appendBytes(Utils.getUInt16(coordinates));
    buffer.appendBytes(Utils.getUInt16(coordinates));

    return buffer;
  }
        
  private Buffer getCoordinatesBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCoordinatesBuffer(FUNCTION_GET_COORDINATES, options);
  }

  private Buffer getCoordinates(Packet packet) {
    logger.debug("function getCoordinates");
    if (packet.getResponseExpected()) {
      return getCoordinatesBuffer(packet);
    }
    return null;
  }

  private Buffer getStatusBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 3;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8(status));
    buffer.appendBytes(Utils.getUInt8(status));
    buffer.appendBytes(Utils.getUInt8(status));

    return buffer;
  }
        
  private Buffer getStatusBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getStatusBuffer(FUNCTION_GET_STATUS, options);
  }

  private Buffer getStatus(Packet packet) {
    logger.debug("function getStatus");
    if (packet.getResponseExpected()) {
      return getStatusBuffer(packet);
    }
    return null;
  }

  private Buffer getAltitudeBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 8;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(altitude));
    buffer.appendBytes(Utils.getUInt32(altitude));

    return buffer;
  }
        
  private Buffer getAltitudeBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAltitudeBuffer(FUNCTION_GET_ALTITUDE, options);
  }

  private Buffer getAltitude(Packet packet) {
    logger.debug("function getAltitude");
    if (packet.getResponseExpected()) {
      return getAltitudeBuffer(packet);
    }
    return null;
  }

  private Buffer getMotionBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 8;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(motion));
    buffer.appendBytes(Utils.getUInt32(motion));

    return buffer;
  }
        
  private Buffer getMotionBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getMotionBuffer(FUNCTION_GET_MOTION, options);
  }

  private Buffer getMotion(Packet packet) {
    logger.debug("function getMotion");
    if (packet.getResponseExpected()) {
      return getMotionBuffer(packet);
    }
    return null;
  }

  private Buffer getDateTimeBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 8;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(dateTime));
    buffer.appendBytes(Utils.getUInt32(dateTime));

    return buffer;
  }
        
  private Buffer getDateTimeBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getDateTimeBuffer(FUNCTION_GET_DATE_TIME, options);
  }

  private Buffer getDateTime(Packet packet) {
    logger.debug("function getDateTime");
    if (packet.getResponseExpected()) {
      return getDateTimeBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getDateTimeCallbackPeriod(Packet packet) {
    logger.debug("function getDateTimeCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(dateTimeCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getMotionCallbackPeriod(Packet packet) {
    logger.debug("function getMotionCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_MOTION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(motionCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getCoordinatesCallbackPeriod(Packet packet) {
    logger.debug("function getCoordinatesCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_COORDINATES_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(coordinatesCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAltitudeCallbackPeriod(Packet packet) {
    logger.debug("function getAltitudeCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(altitudeCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getStatusCallbackPeriod(Packet packet) {
    logger.debug("function getStatusCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_STATUS_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(statusCallbackPeriod));

      return buffer;
    }

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
  private Buffer restart(Packet packet) {
    //TODO dummy method
    return null;
  }
}
