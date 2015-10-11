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
 * 360° rotary encoder with push-button
 */
public class BrickletRotaryEncoder extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 236;
public final static String DEVICE_DISPLAY_NAME = "Rotary Encoder Bricklet";

  public final static byte FUNCTION_GET_COUNT = (byte)1;
  public final static byte FUNCTION_SET_COUNT_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_COUNT_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_COUNT_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_COUNT_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_COUNT = (byte)8;
  public final static byte CALLBACK_COUNT_REACHED = (byte)9;
  public final static byte FUNCTION_IS_PRESSED = (byte)10;
  public final static byte CALLBACK_PRESSED = (byte)11;
  public final static byte CALLBACK_RELEASED = (byte)12;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private Buffer countCallbackThreshold = getCountCallbackThresholdDefault();
        
  private int count = 100;
  private int count_max = 1000;
  private int count_min = 0;
  private int count_step = 1;
  private long count_generator_period = 100;
  private Step count_direction = Step.UP;
  private long countCallbackPeriod;
  private long count_callback_id;
  private int count_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletRotaryEncoder.class);
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

    startCountGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_COUNT) {
      buffer = getCount(packet);
    }
    else if (functionId == FUNCTION_SET_COUNT_CALLBACK_PERIOD) {
      buffer = setCountCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_COUNT_CALLBACK_PERIOD) {
      buffer = getCountCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_COUNT_CALLBACK_THRESHOLD) {
      buffer = setCountCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_COUNT_CALLBACK_THRESHOLD) {
      buffer = getCountCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_IS_PRESSED) {
      buffer = isPressed(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startCountGenerator() {
    if (count_step == 0) {
      return;
    }
    vertx.setPeriodic(count_generator_period, id -> {
      if (count_direction == Step.UP) {
        if (count >= count_max) {
          count_direction = Step.DOWN;
          this.count = (int) (count - count_step);
        } else {
          this.count = (int) (count + count_step);
        }
      } else {
        if (count <= count_min) {
          count_direction = Step.UP;
          this.count = (int) (count + count_step);
        } else {
          this.count = (int) (count - count_step);
        }
      }
    });
  }
        
    private void stopCountCallback() {
        vertx.cancelTimer(count_callback_id);
  }
        //fixme start_generator callback without sensor count
//fixme start_generator callback without sensor countReached
//fixme start_generator callback without sensor pressed
//fixme start_generator callback without sensor released

  private void startCountCallback() {
    logger.trace("countCallbackPeriod is {}", countCallbackPeriod);
    count_callback_id = vertx.setPeriodic(countCallbackPeriod, id -> {
      if (count != count_last_value_called_back) {
        count_last_value_called_back = count;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("count sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getCount4Callback());
          }
        } else {
          logger.info("no handlerids found in count callback");
        }
      } else {
        logger.debug("count value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor count
//fixme stop_generator callback without sensor countReached
//fixme stop_generator callback without sensor pressed
//fixme stop_generator callback without sensor released

  private Buffer getCount4Callback() {
      byte options = (byte) 0;
      return getCountBuffer(CALLBACK_COUNT, options);
  }
        //fixme getter callback without sensor count
//fixme getter callback without sensor countReached
//fixme getter callback without sensor pressed
//fixme getter callback without sensor released

  private Buffer setCountCallbackPeriod(Packet packet) {
    countCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (countCallbackPeriod == 0) {
      stopCountCallback();
    } else {
      startCountCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COUNT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getCountBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(count));

    return buffer;
  }
        
  private Buffer getCountBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCountBuffer(FUNCTION_GET_COUNT, options);
  }

  private Buffer getCount(Packet packet) {
    logger.debug("function getCount");
    if (packet.getResponseExpected()) {
      return getCountBuffer(packet);
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
  private Buffer getCountCallbackThreshold(Packet packet) {
    logger.debug("function getCountCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_COUNT_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.countCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getCountCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getCountCallbackPeriod(Packet packet) {
    logger.debug("function getCountCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_COUNT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(countCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer setCountCallbackThreshold(Packet packet) {
    logger.debug("function setCountCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COUNT_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.countCallbackThreshold = packet.getPayload();
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
  private Buffer isPressed(Packet packet) {
    //TODO dummy method
    return null;
  }
}
