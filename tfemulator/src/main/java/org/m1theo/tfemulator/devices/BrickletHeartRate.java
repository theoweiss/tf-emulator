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
 * Measures heart rate
 */
public class BrickletHeartRate extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 245;
public final static String DEVICE_DISPLAY_NAME = "Heart Rate Bricklet";

  public final static byte FUNCTION_GET_HEART_RATE = (byte)1;
  public final static byte FUNCTION_SET_HEART_RATE_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_HEART_RATE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_HEART_RATE_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_HEART_RATE_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_HEART_RATE = (byte)8;
  public final static byte CALLBACK_HEART_RATE_REACHED = (byte)9;
  public final static byte CALLBACK_BEAT_STATE_CHANGED = (byte)10;
  public final static byte FUNCTION_ENABLE_BEAT_STATE_CHANGED_CALLBACK = (byte)11;
  public final static byte FUNCTION_DISABLE_BEAT_STATE_CHANGED_CALLBACK = (byte)12;
  public final static byte FUNCTION_IS_BEAT_STATE_CHANGED_CALLBACK_ENABLED = (byte)13;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short BEAT_STATE_FALLING = (short)0;
  public final static short BEAT_STATE_RISING = (short)1;
  String uidString;

  private Buffer heartRateCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private short heartRate = 100;
  private short heartRate_max = 1000;
  private short heartRate_min = 0;
  private short heartRate_step = 1;
  private long heartRate_generator_period = 100;
  private Step heartRate_direction = Step.UP;
  private long heartRateCallbackPeriod;
  private long heartRate_callback_id;
  private short heartRate_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletHeartRate.class);
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

    startHeartRateGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_HEART_RATE) {
      buffer = getHeartRate(packet);
    }
    else if (functionId == FUNCTION_SET_HEART_RATE_CALLBACK_PERIOD) {
      buffer = setHeartRateCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_HEART_RATE_CALLBACK_PERIOD) {
      buffer = getHeartRateCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_HEART_RATE_CALLBACK_THRESHOLD) {
      buffer = setHeartRateCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_HEART_RATE_CALLBACK_THRESHOLD) {
      buffer = getHeartRateCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_ENABLE_BEAT_STATE_CHANGED_CALLBACK) {
      buffer = enableBeatStateChangedCallback(packet);
    }
    else if (functionId == FUNCTION_DISABLE_BEAT_STATE_CHANGED_CALLBACK) {
      buffer = disableBeatStateChangedCallback(packet);
    }
    else if (functionId == FUNCTION_IS_BEAT_STATE_CHANGED_CALLBACK_ENABLED) {
      buffer = isBeatStateChangedCallbackEnabled(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startHeartRateGenerator() {
    if (heartRate_step == 0) {
      return;
    }
    vertx.setPeriodic(heartRate_generator_period, id -> {
      if (heartRate_direction == Step.UP) {
        if (heartRate >= heartRate_max) {
          heartRate_direction = Step.DOWN;
          this.heartRate = (short) (heartRate - heartRate_step);
        } else {
          this.heartRate = (short) (heartRate + heartRate_step);
        }
      } else {
        if (heartRate <= heartRate_min) {
          heartRate_direction = Step.UP;
          this.heartRate = (short) (heartRate + heartRate_step);
        } else {
          this.heartRate = (short) (heartRate - heartRate_step);
        }
      }
    });
  }
        
    private void stopHeartRateCallback() {
        vertx.cancelTimer(heartRate_callback_id);
  }
        //fixme start_generator callback without sensor heartRate
//fixme start_generator callback without sensor heartRateReached
//fixme start_generator callback without sensor beatStateChanged

  private void startHeartRateCallback() {
    logger.trace("heartRateCallbackPeriod is {}", heartRateCallbackPeriod);
    heartRate_callback_id = vertx.setPeriodic(heartRateCallbackPeriod, id -> {
      if (heartRate != heartRate_last_value_called_back) {
        heartRate_last_value_called_back = heartRate;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("heartRate sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getHeartRate4Callback());
          }
        } else {
          logger.info("no handlerids found in heartRate callback");
        }
      } else {
        logger.debug("heartRate value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor heartRate
//fixme stop_generator callback without sensor heartRateReached
//fixme stop_generator callback without sensor beatStateChanged

  private Buffer getHeartRate4Callback() {
      byte options = (byte) 0;
      return getHeartRateBuffer(CALLBACK_HEART_RATE, options);
  }
        //fixme getter callback without sensor heartRate
//fixme getter callback without sensor heartRateReached
//fixme getter callback without sensor beatStateChanged

  private Buffer setHeartRateCallbackPeriod(Packet packet) {
    heartRateCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (heartRateCallbackPeriod == 0) {
      stopHeartRateCallback();
    } else {
      startHeartRateCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_HEART_RATE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getHeartRateBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(heartRate));

    return buffer;
  }
        
  private Buffer getHeartRateBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getHeartRateBuffer(FUNCTION_GET_HEART_RATE, options);
  }

  private Buffer getHeartRate(Packet packet) {
    logger.debug("function getHeartRate");
    if (packet.getResponseExpected()) {
      return getHeartRateBuffer(packet);
    }
    return null;
  }
}
