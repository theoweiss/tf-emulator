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
 * Measures dust density
 */
public class BrickletDustDetector extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 260;
public final static String DEVICE_DISPLAY_NAME = "Dust Detector Bricklet";

  public final static byte FUNCTION_GET_DUST_DENSITY = (byte)1;
  public final static byte FUNCTION_SET_DUST_DENSITY_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_DUST_DENSITY_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_DUST_DENSITY_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_DUST_DENSITY_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_DUST_DENSITY = (byte)8;
  public final static byte CALLBACK_DUST_DENSITY_REACHED = (byte)9;
  public final static byte FUNCTION_SET_MOVING_AVERAGE = (byte)10;
  public final static byte FUNCTION_GET_MOVING_AVERAGE = (byte)11;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer movingAverage = getMovingAverageDefault();
        
  private Buffer dustDensityCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private short dustDensity = 100;
  private short dustDensity_max = 1000;
  private short dustDensity_min = 0;
  private short dustDensity_step = 1;
  private long dustDensity_generator_period = 100;
  private Step dustDensity_direction = Step.UP;
  private long dustDensityCallbackPeriod;
  private long dustDensity_callback_id;
  private short dustDensity_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletDustDetector.class);
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

    startDustDensityGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_DUST_DENSITY) {
      buffer = getDustDensity(packet);
    }
    else if (functionId == FUNCTION_SET_DUST_DENSITY_CALLBACK_PERIOD) {
      buffer = setDustDensityCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DUST_DENSITY_CALLBACK_PERIOD) {
      buffer = getDustDensityCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_DUST_DENSITY_CALLBACK_THRESHOLD) {
      buffer = setDustDensityCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_DUST_DENSITY_CALLBACK_THRESHOLD) {
      buffer = getDustDensityCallbackThreshold(packet);
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
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startDustDensityGenerator() {
    if (dustDensity_step == 0) {
      return;
    }
    vertx.setPeriodic(dustDensity_generator_period, id -> {
      if (dustDensity_direction == Step.UP) {
        if (dustDensity >= dustDensity_max) {
          dustDensity_direction = Step.DOWN;
          this.dustDensity = (short) (dustDensity - dustDensity_step);
        } else {
          this.dustDensity = (short) (dustDensity + dustDensity_step);
        }
      } else {
        if (dustDensity <= dustDensity_min) {
          dustDensity_direction = Step.UP;
          this.dustDensity = (short) (dustDensity + dustDensity_step);
        } else {
          this.dustDensity = (short) (dustDensity - dustDensity_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor dustDensityReached

    private void stopDustDensityCallback() {
        vertx.cancelTimer(dustDensity_callback_id);
  }
        //fixme start_generator callback without sensor dustDensity
//fixme stop_generator callback without sensor dustDensityReached

  private void startDustDensityCallback() {
    logger.trace("dustDensityCallbackPeriod is {}", dustDensityCallbackPeriod);
    dustDensity_callback_id = vertx.setPeriodic(dustDensityCallbackPeriod, id -> {
      if (dustDensity != dustDensity_last_value_called_back) {
        dustDensity_last_value_called_back = dustDensity;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("dustDensity sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getDustDensity4Callback());
          }
        } else {
          logger.info("no handlerids found in dustDensity callback");
        }
      } else {
        logger.debug("dustDensity value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor dustDensity
//fixme getter callback without sensor dustDensityReached

  private Buffer getDustDensity4Callback() {
      byte options = (byte) 0;
      return getDustDensityBuffer(CALLBACK_DUST_DENSITY, options);
  }
        //fixme getter callback without sensor dustDensity

  private Buffer setDustDensityCallbackPeriod(Packet packet) {
    dustDensityCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (dustDensityCallbackPeriod == 0) {
      stopDustDensityCallback();
    } else {
      startDustDensityCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DUST_DENSITY_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getDustDensityBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(dustDensity));

    return buffer;
  }
        
  private Buffer getDustDensityBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getDustDensityBuffer(FUNCTION_GET_DUST_DENSITY, options);
  }

  private Buffer getDustDensity(Packet packet) {
    logger.debug("function getDustDensity");
    if (packet.getResponseExpected()) {
      return getDustDensityBuffer(packet);
    }
    return null;
  }
}
