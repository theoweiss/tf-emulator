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
 * 2-axis joystick with push-button
 */
public class BrickletJoystick extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 210;
public final static String DEVICE_DISPLAY_NAME = "Joystick Bricklet";

  public final static byte FUNCTION_GET_POSITION = (byte)1;
  public final static byte FUNCTION_IS_PRESSED = (byte)2;
  public final static byte FUNCTION_GET_ANALOG_VALUE = (byte)3;
  public final static byte FUNCTION_CALIBRATE = (byte)4;
  public final static byte FUNCTION_SET_POSITION_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_POSITION_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)7;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)8;
  public final static byte FUNCTION_SET_POSITION_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_POSITION_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)11;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)12;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)13;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)14;
  public final static byte CALLBACK_POSITION = (byte)15;
  public final static byte CALLBACK_ANALOG_VALUE = (byte)16;
  public final static byte CALLBACK_POSITION_REACHED = (byte)17;
  public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte)18;
  public final static byte CALLBACK_PRESSED = (byte)19;
  public final static byte CALLBACK_RELEASED = (byte)20;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private Buffer analogValueCallbackThreshold = getAnalogValueCallbackThresholdDefault();
        
  private Buffer positionCallbackThreshold = getPositionCallbackThresholdDefault();
        
  private short analogValue = 100;
  private short analogValue_max = 1000;
  private short analogValue_min = 0;
  private short analogValue_step = 1;
  private long analogValue_generator_period = 100;
  private Step analogValue_direction = Step.UP;
  private long analogValueCallbackPeriod;
  private long analogValue_callback_id;
  private short analogValue_last_value_called_back;

  private short position = 100;
  private short position_max = 1000;
  private short position_min = 0;
  private short position_step = 1;
  private long position_generator_period = 100;
  private Step position_direction = Step.UP;
  private long positionCallbackPeriod;
  private long position_callback_id;
  private short position_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletJoystick.class);
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
    startPositionGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_POSITION) {
      buffer = getPosition(packet);
    }
    else if (functionId == FUNCTION_IS_PRESSED) {
      buffer = isPressed(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE) {
      buffer = getAnalogValue(packet);
    }
    else if (functionId == FUNCTION_CALIBRATE) {
      buffer = calibrate(packet);
    }
    else if (functionId == FUNCTION_SET_POSITION_CALLBACK_PERIOD) {
      buffer = setPositionCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_POSITION_CALLBACK_PERIOD) {
      buffer = getPositionCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = setAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = getAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_POSITION_CALLBACK_THRESHOLD) {
      buffer = setPositionCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_POSITION_CALLBACK_THRESHOLD) {
      buffer = getPositionCallbackThreshold(packet);
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
        
  private void startPositionGenerator() {
    if (position_step == 0) {
      return;
    }
    vertx.setPeriodic(position_generator_period, id -> {
      if (position_direction == Step.UP) {
        if (position >= position_max) {
          position_direction = Step.DOWN;
          this.position = (short) (position - position_step);
        } else {
          this.position = (short) (position + position_step);
        }
      } else {
        if (position <= position_min) {
          position_direction = Step.UP;
          this.position = (short) (position + position_step);
        } else {
          this.position = (short) (position - position_step);
        }
      }
    });
  }
        
    private void stopAnalogValueCallback() {
        vertx.cancelTimer(analogValue_callback_id);
  }
        //fixme start_generator callback without sensor analogValue
//fixme start_generator callback without sensor positionReached
//fixme start_generator callback without sensor released
//fixme start_generator callback without sensor pressed

    private void stopPositionCallback() {
        vertx.cancelTimer(position_callback_id);
  }
        //fixme start_generator callback without sensor position
//fixme start_generator callback without sensor analogValueReached

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
//fixme stop_generator callback without sensor positionReached
//fixme stop_generator callback without sensor released
//fixme stop_generator callback without sensor pressed

  private void startPositionCallback() {
    logger.trace("positionCallbackPeriod is {}", positionCallbackPeriod);
    position_callback_id = vertx.setPeriodic(positionCallbackPeriod, id -> {
      if (position != position_last_value_called_back) {
        position_last_value_called_back = position;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("position sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getPosition4Callback());
          }
        } else {
          logger.info("no handlerids found in position callback");
        }
      } else {
        logger.debug("position value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor position
//fixme stop_generator callback without sensor analogValueReached

  private Buffer getAnalogValue4Callback() {
      byte options = (byte) 0;
      return getAnalogValueBuffer(CALLBACK_ANALOG_VALUE, options);
  }
        //fixme getter callback without sensor analogValue
//fixme getter callback without sensor positionReached
//fixme getter callback without sensor released
//fixme getter callback without sensor pressed

  private Buffer getPosition4Callback() {
      byte options = (byte) 0;
      return getPositionBuffer(CALLBACK_POSITION, options);
  }
        //fixme getter callback without sensor position
//fixme getter callback without sensor analogValueReached

  private Buffer setPositionCallbackPeriod(Packet packet) {
    positionCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (positionCallbackPeriod == 0) {
      stopPositionCallback();
    } else {
      startPositionCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_POSITION_CALLBACK_PERIOD;
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
        
  private Buffer getPositionBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(position));
    buffer.appendBytes(Utils.getUInt16(position));

    return buffer;
  }
        
  private Buffer getPositionBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getPositionBuffer(FUNCTION_GET_POSITION, options);
  }

  private Buffer getPosition(Packet packet) {
    logger.debug("function getPosition");
    if (packet.getResponseExpected()) {
      return getPositionBuffer(packet);
    }
    return null;
  }

  private Buffer getAnalogValueBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(analogValue));
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
  private Buffer getDebouncePeriod(Packet packet) {
    logger.debug("function getDebouncePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_DEBOUNCE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.debouncePeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getDebouncePeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getAnalogValueCallbackThreshold(Packet packet) {
    logger.debug("function getAnalogValueCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.analogValueCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getAnalogValueCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getPositionCallbackThreshold(Packet packet) {
    logger.debug("function getPositionCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_POSITION_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.positionCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getPositionCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getAnalogValueCallbackPeriod(Packet packet) {
    logger.debug("function getAnalogValueCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(analogValueCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getPositionCallbackPeriod(Packet packet) {
    logger.debug("function getPositionCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_POSITION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(positionCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer setAnalogValueCallbackThreshold(Packet packet) {
    logger.debug("function setAnalogValueCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.analogValueCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setPositionCallbackThreshold(Packet packet) {
    logger.debug("function setPositionCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_POSITION_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.positionCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setDebouncePeriod(Packet packet) {
    logger.debug("function setDebouncePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DEBOUNCE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.debouncePeriod = packet.getPayload();
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
  private Buffer calibrate(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isPressed(Packet packet) {
    //TODO dummy method
    return null;
  }
}
