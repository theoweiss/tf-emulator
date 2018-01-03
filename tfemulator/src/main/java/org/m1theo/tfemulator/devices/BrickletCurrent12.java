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
 * Measures AC/DC current between -12.5A and +12.5A
 */
public class BrickletCurrent12 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 23;
public final static String DEVICE_DISPLAY_NAME = "Current12 Bricklet";

  public final static byte FUNCTION_GET_CURRENT = (byte)1;
  public final static byte FUNCTION_CALIBRATE = (byte)2;
  public final static byte FUNCTION_IS_OVER_CURRENT = (byte)3;
  public final static byte FUNCTION_GET_ANALOG_VALUE = (byte)4;
  public final static byte FUNCTION_SET_CURRENT_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_CURRENT_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)7;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)8;
  public final static byte FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)11;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)12;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)13;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)14;
  public final static byte CALLBACK_CURRENT = (byte)15;
  public final static byte CALLBACK_ANALOG_VALUE = (byte)16;
  public final static byte CALLBACK_CURRENT_REACHED = (byte)17;
  public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte)18;
  public final static byte CALLBACK_OVER_CURRENT = (byte)19;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer analogValueCallbackThreshold = Utils.getThresholdDefault();
  private Buffer currentCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private short analogValue = 100;
  private short analogValue_max = 1000;
  private short analogValue_min = 0;
  private short analogValue_step = 1;
  private long analogValue_generator_period = 100;
  private Step analogValue_direction = Step.UP;
  private long analogValueCallbackPeriod;
  private long analogValue_callback_id;
  private short analogValue_last_value_called_back;

  private short current = 100;
  private short current_max = 1000;
  private short current_min = 0;
  private short current_step = 1;
  private long current_generator_period = 100;
  private Step current_direction = Step.UP;
  private long currentCallbackPeriod;
  private long current_callback_id;
  private short current_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletCurrent12.class);
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

    startAnalogValueGenerator();
    startCurrentGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_CURRENT) {
      buffer = getCurrent(packet);
    }
    else if (functionId == FUNCTION_CALIBRATE) {
      buffer = calibrate(packet);
    }
    else if (functionId == FUNCTION_IS_OVER_CURRENT) {
      buffer = isOverCurrent(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE) {
      buffer = getAnalogValue(packet);
    }
    else if (functionId == FUNCTION_SET_CURRENT_CALLBACK_PERIOD) {
      buffer = setCurrentCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_CALLBACK_PERIOD) {
      buffer = getCurrentCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = setAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = getAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD) {
      buffer = setCurrentCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD) {
      buffer = getCurrentCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD) {
      buffer = setAnalogValueCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD) {
      buffer = getAnalogValueCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startAnalogValueGenerator() {
    if (analogValue_step == 0) {
      return;
    }
    vertx.setPeriodic(analogValue_generator_period, id -> {
      if (analogValue_direction == Step.UP) {
        if (analogValue >= analogValue_max) {
          analogValue_direction = Step.DOWN;
          this.analogValue = (short) (analogValue - analogValue_step);
        } else {
          this.analogValue = (short) (analogValue + analogValue_step);
        }
      } else {
        if (analogValue <= analogValue_min) {
          analogValue_direction = Step.UP;
          this.analogValue = (short) (analogValue + analogValue_step);
        } else {
          this.analogValue = (short) (analogValue - analogValue_step);
        }
      }
    });
  }
        
  private void startCurrentGenerator() {
    if (current_step == 0) {
      return;
    }
    vertx.setPeriodic(current_generator_period, id -> {
      if (current_direction == Step.UP) {
        if (current >= current_max) {
          current_direction = Step.DOWN;
          this.current = (short) (current - current_step);
        } else {
          this.current = (short) (current + current_step);
        }
      } else {
        if (current <= current_min) {
          current_direction = Step.UP;
          this.current = (short) (current + current_step);
        } else {
          this.current = (short) (current - current_step);
        }
      }
    });
  }
        
    private void stopCurrentCallback() {
        vertx.cancelTimer(current_callback_id);
  }
        //fixme start_generator callback without sensor current

    private void stopAnalogValueCallback() {
        vertx.cancelTimer(analogValue_callback_id);
  }
        //fixme start_generator callback without sensor analogValue
//fixme start_generator callback without sensor overCurrent
//fixme start_generator callback without sensor currentReached
//fixme start_generator callback without sensor analogValueReached

  private void startCurrentCallback() {
    logger.trace("currentCallbackPeriod is {}", currentCallbackPeriod);
    current_callback_id = vertx.setPeriodic(currentCallbackPeriod, id -> {
      if (current != current_last_value_called_back) {
        current_last_value_called_back = current;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("current sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getCurrent4Callback());
          }
        } else {
          logger.info("no handlerids found in current callback");
        }
      } else {
        logger.debug("current value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor current

  private void startAnalogValueCallback() {
    logger.trace("analogValueCallbackPeriod is {}", analogValueCallbackPeriod);
    analogValue_callback_id = vertx.setPeriodic(analogValueCallbackPeriod, id -> {
      if (analogValue != analogValue_last_value_called_back) {
        analogValue_last_value_called_back = analogValue;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("analogValue sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAnalogValue4Callback());
          }
        } else {
          logger.info("no handlerids found in analogValue callback");
        }
      } else {
        logger.debug("analogValue value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor analogValue
//fixme stop_generator callback without sensor overCurrent
//fixme stop_generator callback without sensor currentReached
//fixme stop_generator callback without sensor analogValueReached

  private Buffer getCurrent4Callback() {
      byte options = (byte) 0;
      return getCurrentBuffer(CALLBACK_CURRENT, options);
  }
        //fixme getter callback without sensor current

  private Buffer getAnalogValue4Callback() {
      byte options = (byte) 0;
      return getAnalogValueBuffer(CALLBACK_ANALOG_VALUE, options);
  }
        //fixme getter callback without sensor analogValue
//fixme getter callback without sensor overCurrent
//fixme getter callback without sensor currentReached
//fixme getter callback without sensor analogValueReached

  private Buffer setCurrentCallbackPeriod(Packet packet) {
    currentCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (currentCallbackPeriod == 0) {
      stopCurrentCallback();
    } else {
      startCurrentCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CURRENT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setAnalogValueCallbackPeriod(Packet packet) {
    analogValueCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (analogValueCallbackPeriod == 0) {
      stopAnalogValueCallback();
    } else {
      startAnalogValueCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getCurrentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(current));

    return buffer;
  }
        
  private Buffer getCurrentBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCurrentBuffer(FUNCTION_GET_CURRENT, options);
  }

  private Buffer getCurrent(Packet packet) {
    logger.debug("function getCurrent");
    if (packet.getResponseExpected()) {
      return getCurrentBuffer(packet);
    }
    return null;
  }

  private Buffer getIsOverCurrentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(isOverCurrent));

    return buffer;
  }
        
  private Buffer getIsOverCurrentBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIsOverCurrentBuffer(FUNCTION_IS_OVER_CURRENT, options);
  }

  private Buffer getIsOverCurrent(Packet packet) {
    logger.debug("function getIsOverCurrent");
    if (packet.getResponseExpected()) {
      return getIsOverCurrentBuffer(packet);
    }
    return null;
  }

  private Buffer getAnalogValueBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(analogValue));

    return buffer;
  }
        
  private Buffer getAnalogValueBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAnalogValueBuffer(FUNCTION_GET_ANALOG_VALUE, options);
  }

  private Buffer getAnalogValue(Packet packet) {
    logger.debug("function getAnalogValue");
    if (packet.getResponseExpected()) {
      return getAnalogValueBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer calibrate(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isOverCurrent(Packet packet) {
    //TODO dummy method
    return null;
  }
}
