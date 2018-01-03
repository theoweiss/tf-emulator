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
 * Measures distance up to 40m with laser light
 */
public class BrickletLaserRangeFinder extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 255;
public final static String DEVICE_DISPLAY_NAME = "Laser Range Finder Bricklet";

  public final static byte FUNCTION_GET_DISTANCE = (byte)1;
  public final static byte FUNCTION_GET_VELOCITY = (byte)2;
  public final static byte FUNCTION_SET_DISTANCE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_GET_DISTANCE_CALLBACK_PERIOD = (byte)4;
  public final static byte FUNCTION_SET_VELOCITY_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_VELOCITY_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD = (byte)7;
  public final static byte FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD = (byte)8;
  public final static byte FUNCTION_SET_VELOCITY_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_VELOCITY_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)11;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)12;
  public final static byte FUNCTION_SET_MOVING_AVERAGE = (byte)13;
  public final static byte FUNCTION_GET_MOVING_AVERAGE = (byte)14;
  public final static byte FUNCTION_SET_MODE = (byte)15;
  public final static byte FUNCTION_GET_MODE = (byte)16;
  public final static byte FUNCTION_ENABLE_LASER = (byte)17;
  public final static byte FUNCTION_DISABLE_LASER = (byte)18;
  public final static byte FUNCTION_IS_LASER_ENABLED = (byte)19;
  public final static byte CALLBACK_DISTANCE = (byte)20;
  public final static byte CALLBACK_VELOCITY = (byte)21;
  public final static byte CALLBACK_DISTANCE_REACHED = (byte)22;
  public final static byte CALLBACK_VELOCITY_REACHED = (byte)23;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short MODE_DISTANCE = (short)0;
  public final static short MODE_VELOCITY_MAX_13MS = (short)1;
  public final static short MODE_VELOCITY_MAX_32MS = (short)2;
  public final static short MODE_VELOCITY_MAX_64MS = (short)3;
  public final static short MODE_VELOCITY_MAX_127MS = (short)4;
  String uidString;

  private Buffer mode = getModeDefault();
        
  private Buffer movingAverage = getMovingAverageDefault();
        
  private Buffer velocityCallbackThreshold = Utils.getThresholdDefault();
  private Buffer distanceCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private short velocity = 100;
  private short velocity_max = 1000;
  private short velocity_min = 0;
  private short velocity_step = 1;
  private long velocity_generator_period = 100;
  private Step velocity_direction = Step.UP;
  private long velocityCallbackPeriod;
  private long velocity_callback_id;
  private short velocity_last_value_called_back;

  private short distance = 100;
  private short distance_max = 1000;
  private short distance_min = 0;
  private short distance_step = 1;
  private long distance_generator_period = 100;
  private Step distance_direction = Step.UP;
  private long distanceCallbackPeriod;
  private long distance_callback_id;
  private short distance_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletLaserRangeFinder.class);
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

    startVelocityGenerator();
    startDistanceGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_DISTANCE) {
      buffer = getDistance(packet);
    }
    else if (functionId == FUNCTION_GET_VELOCITY) {
      buffer = getVelocity(packet);
    }
    else if (functionId == FUNCTION_SET_DISTANCE_CALLBACK_PERIOD) {
      buffer = setDistanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DISTANCE_CALLBACK_PERIOD) {
      buffer = getDistanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_VELOCITY_CALLBACK_PERIOD) {
      buffer = setVelocityCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_VELOCITY_CALLBACK_PERIOD) {
      buffer = getVelocityCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD) {
      buffer = setDistanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD) {
      buffer = getDistanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_VELOCITY_CALLBACK_THRESHOLD) {
      buffer = setVelocityCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_VELOCITY_CALLBACK_THRESHOLD) {
      buffer = getVelocityCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_MOVING_AVERAGE) {
      buffer = setMovingAverage(packet);
    }
    else if (functionId == FUNCTION_GET_MOVING_AVERAGE) {
      buffer = getMovingAverage(packet);
    }
    else if (functionId == FUNCTION_SET_MODE) {
      buffer = setMode(packet);
    }
    else if (functionId == FUNCTION_GET_MODE) {
      buffer = getMode(packet);
    }
    else if (functionId == FUNCTION_ENABLE_LASER) {
      buffer = enableLaser(packet);
    }
    else if (functionId == FUNCTION_DISABLE_LASER) {
      buffer = disableLaser(packet);
    }
    else if (functionId == FUNCTION_IS_LASER_ENABLED) {
      buffer = isLaserEnabled(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startVelocityGenerator() {
    if (velocity_step == 0) {
      return;
    }
    vertx.setPeriodic(velocity_generator_period, id -> {
      if (velocity_direction == Step.UP) {
        if (velocity >= velocity_max) {
          velocity_direction = Step.DOWN;
          this.velocity = (short) (velocity - velocity_step);
        } else {
          this.velocity = (short) (velocity + velocity_step);
        }
      } else {
        if (velocity <= velocity_min) {
          velocity_direction = Step.UP;
          this.velocity = (short) (velocity + velocity_step);
        } else {
          this.velocity = (short) (velocity - velocity_step);
        }
      }
    });
  }
        
  private void startDistanceGenerator() {
    if (distance_step == 0) {
      return;
    }
    vertx.setPeriodic(distance_generator_period, id -> {
      if (distance_direction == Step.UP) {
        if (distance >= distance_max) {
          distance_direction = Step.DOWN;
          this.distance = (short) (distance - distance_step);
        } else {
          this.distance = (short) (distance + distance_step);
        }
      } else {
        if (distance <= distance_min) {
          distance_direction = Step.UP;
          this.distance = (short) (distance + distance_step);
        } else {
          this.distance = (short) (distance - distance_step);
        }
      }
    });
  }
        
    private void stopDistanceCallback() {
        vertx.cancelTimer(distance_callback_id);
  }
        //fixme start_generator callback without sensor distance
//fixme start_generator callback without sensor velocityReached
//fixme start_generator callback without sensor distanceReached

    private void stopVelocityCallback() {
        vertx.cancelTimer(velocity_callback_id);
  }
        //fixme start_generator callback without sensor velocity

  private void startDistanceCallback() {
    logger.trace("distanceCallbackPeriod is {}", distanceCallbackPeriod);
    distance_callback_id = vertx.setPeriodic(distanceCallbackPeriod, id -> {
      if (distance != distance_last_value_called_back) {
        distance_last_value_called_back = distance;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("distance sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getDistance4Callback());
          }
        } else {
          logger.info("no handlerids found in distance callback");
        }
      } else {
        logger.debug("distance value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor distance
//fixme stop_generator callback without sensor velocityReached
//fixme stop_generator callback without sensor distanceReached

  private void startVelocityCallback() {
    logger.trace("velocityCallbackPeriod is {}", velocityCallbackPeriod);
    velocity_callback_id = vertx.setPeriodic(velocityCallbackPeriod, id -> {
      if (velocity != velocity_last_value_called_back) {
        velocity_last_value_called_back = velocity;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("velocity sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getVelocity4Callback());
          }
        } else {
          logger.info("no handlerids found in velocity callback");
        }
      } else {
        logger.debug("velocity value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor velocity

  private Buffer getDistance4Callback() {
      byte options = (byte) 0;
      return getDistanceBuffer(CALLBACK_DISTANCE, options);
  }
        //fixme getter callback without sensor distance
//fixme getter callback without sensor velocityReached
//fixme getter callback without sensor distanceReached

  private Buffer getVelocity4Callback() {
      byte options = (byte) 0;
      return getVelocityBuffer(CALLBACK_VELOCITY, options);
  }
        //fixme getter callback without sensor velocity

  private Buffer setDistanceCallbackPeriod(Packet packet) {
    distanceCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (distanceCallbackPeriod == 0) {
      stopDistanceCallback();
    } else {
      startDistanceCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DISTANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setVelocityCallbackPeriod(Packet packet) {
    velocityCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (velocityCallbackPeriod == 0) {
      stopVelocityCallback();
    } else {
      startVelocityCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_VELOCITY_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getDistanceBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(distance));

    return buffer;
  }
        
  private Buffer getDistanceBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getDistanceBuffer(FUNCTION_GET_DISTANCE, options);
  }

  private Buffer getDistance(Packet packet) {
    logger.debug("function getDistance");
    if (packet.getResponseExpected()) {
      return getDistanceBuffer(packet);
    }
    return null;
  }

  private Buffer getVelocityBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(velocity));

    return buffer;
  }
        
  private Buffer getVelocityBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getVelocityBuffer(FUNCTION_GET_VELOCITY, options);
  }

  private Buffer getVelocity(Packet packet) {
    logger.debug("function getVelocity");
    if (packet.getResponseExpected()) {
      return getVelocityBuffer(packet);
    }
    return null;
  }
}
