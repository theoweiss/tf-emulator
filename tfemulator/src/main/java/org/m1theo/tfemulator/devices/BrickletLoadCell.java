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
 * Measures weight with a load cell
 */
public class BrickletLoadCell extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 253;
public final static String DEVICE_DISPLAY_NAME = "Load Cell Bricklet";

  public final static byte FUNCTION_GET_WEIGHT = (byte)1;
  public final static byte FUNCTION_SET_WEIGHT_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_WEIGHT_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_WEIGHT_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_WEIGHT_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte FUNCTION_SET_MOVING_AVERAGE = (byte)8;
  public final static byte FUNCTION_GET_MOVING_AVERAGE = (byte)9;
  public final static byte FUNCTION_LED_ON = (byte)10;
  public final static byte FUNCTION_LED_OFF = (byte)11;
  public final static byte FUNCTION_IS_LED_ON = (byte)12;
  public final static byte FUNCTION_CALIBRATE = (byte)13;
  public final static byte FUNCTION_TARE = (byte)14;
  public final static byte FUNCTION_SET_CONFIGURATION = (byte)15;
  public final static byte FUNCTION_GET_CONFIGURATION = (byte)16;
  public final static byte CALLBACK_WEIGHT = (byte)17;
  public final static byte CALLBACK_WEIGHT_REACHED = (byte)18;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short RATE_10HZ = (short)0;
  public final static short RATE_80HZ = (short)1;
  public final static short GAIN_128X = (short)0;
  public final static short GAIN_64X = (short)1;
  public final static short GAIN_32X = (short)2;
  String uidString;

  private Buffer configuration = getConfigurationDefault();
        
  private Buffer movingAverage = getMovingAverageDefault();
        
  private Buffer weightCallbackThreshold = getWeightCallbackThresholdDefault();
        
  private Buffer led = getLedDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private int weight = 100;
  private int weight_max = 1000;
  private int weight_min = 0;
  private int weight_step = 1;
  private long weight_generator_period = 100;
  private Step weight_direction = Step.UP;
  private long weightCallbackPeriod;
  private long weight_callback_id;
  private int weight_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletLoadCell.class);
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

    startWeightGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_WEIGHT) {
      buffer = getWeight(packet);
    }
    else if (functionId == FUNCTION_SET_WEIGHT_CALLBACK_PERIOD) {
      buffer = setWeightCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_WEIGHT_CALLBACK_PERIOD) {
      buffer = getWeightCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_WEIGHT_CALLBACK_THRESHOLD) {
      buffer = setWeightCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_WEIGHT_CALLBACK_THRESHOLD) {
      buffer = getWeightCallbackThreshold(packet);
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
    else if (functionId == FUNCTION_LED_ON) {
      buffer = ledOn(packet);
    }
    else if (functionId == FUNCTION_LED_OFF) {
      buffer = ledOff(packet);
    }
    else if (functionId == FUNCTION_IS_LED_ON) {
      buffer = isLEDOn(packet);
    }
    else if (functionId == FUNCTION_CALIBRATE) {
      buffer = calibrate(packet);
    }
    else if (functionId == FUNCTION_TARE) {
      buffer = tare(packet);
    }
    else if (functionId == FUNCTION_SET_CONFIGURATION) {
      buffer = setConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIGURATION) {
      buffer = getConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startWeightGenerator() {
    if (weight_step == 0) {
      return;
    }
    vertx.setPeriodic(weight_generator_period, id -> {
      if (weight_direction == Step.UP) {
        if (weight >= weight_max) {
          weight_direction = Step.DOWN;
          this.weight = (int) (weight - weight_step);
        } else {
          this.weight = (int) (weight + weight_step);
        }
      } else {
        if (weight <= weight_min) {
          weight_direction = Step.UP;
          this.weight = (int) (weight + weight_step);
        } else {
          this.weight = (int) (weight - weight_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor weightReached

    private void stopWeightCallback() {
        vertx.cancelTimer(weight_callback_id);
  }
        //fixme start_generator callback without sensor weight
//fixme stop_generator callback without sensor weightReached

  private void startWeightCallback() {
    logger.trace("weightCallbackPeriod is {}", weightCallbackPeriod);
    weight_callback_id = vertx.setPeriodic(weightCallbackPeriod, id -> {
      if (weight != weight_last_value_called_back) {
        weight_last_value_called_back = weight;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("weight sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getWeight4Callback());
          }
        } else {
          logger.info("no handlerids found in weight callback");
        }
      } else {
        logger.debug("weight value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor weight
//fixme getter callback without sensor weightReached

  private Buffer getWeight4Callback() {
      byte options = (byte) 0;
      return getWeightBuffer(CALLBACK_WEIGHT, options);
  }
        //fixme getter callback without sensor weight

  private Buffer setWeightCallbackPeriod(Packet packet) {
    weightCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (weightCallbackPeriod == 0) {
      stopWeightCallback();
    } else {
      startWeightCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WEIGHT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getWeightBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(weight));

    return buffer;
  }
        
  private Buffer getWeightBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getWeightBuffer(FUNCTION_GET_WEIGHT, options);
  }

  private Buffer getWeight(Packet packet) {
    logger.debug("function getWeight");
    if (packet.getResponseExpected()) {
      return getWeightBuffer(packet);
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
  private Buffer getConfiguration(Packet packet) {
    logger.debug("function getConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.configuration);
      return buffer;
    }

    return null;
  }

  private Buffer getConfigurationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getMovingAverage(Packet packet) {
    logger.debug("function getMovingAverage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_MOVING_AVERAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.movingAverage);
      return buffer;
    }

    return null;
  }

  private Buffer getMovingAverageDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getWeightCallbackThreshold(Packet packet) {
    logger.debug("function getWeightCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_WEIGHT_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.weightCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getWeightCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer isLEDOn(Packet packet) {
    logger.debug("function isLEDOn");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_LED_ON;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.led);
      return buffer;
    }

    return null;
  }

  private Buffer isLEDOnDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getWeightCallbackPeriod(Packet packet) {
    logger.debug("function getWeightCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_WEIGHT_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(weightCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer ledOff(Packet packet) {
    logger.debug("function ledOff");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_LED_OFF;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.led = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer ledOn(Packet packet) {
    logger.debug("function ledOn");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_LED_ON;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.led = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setConfiguration(Packet packet) {
    logger.debug("function setConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.configuration = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setWeightCallbackThreshold(Packet packet) {
    logger.debug("function setWeightCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WEIGHT_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.weightCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setMovingAverage(Packet packet) {
    logger.debug("function setMovingAverage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MOVING_AVERAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.movingAverage = packet.getPayload();
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
  private Buffer tare(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer calibrate(Packet packet) {
    //TODO dummy method
    return null;
  }
}
