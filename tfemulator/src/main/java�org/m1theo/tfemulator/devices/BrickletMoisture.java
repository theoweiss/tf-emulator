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
 * Measures soil moisture
 */
public class BrickletMoisture extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 232;
public final static String DEVICE_DISPLAY_NAME = "Moisture Bricklet";

  public final static byte FUNCTION_GET_MOISTURE_VALUE = (byte)1;
  public final static byte FUNCTION_SET_MOISTURE_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_MOISTURE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_MOISTURE_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_MOISTURE_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_MOISTURE = (byte)8;
  public final static byte CALLBACK_MOISTURE_REACHED = (byte)9;
  public final static byte FUNCTION_SET_MOVING_AVERAGE = (byte)10;
  public final static byte FUNCTION_GET_MOVING_AVERAGE = (byte)11;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer moistureCallbackThreshold = getMoistureCallbackThresholdDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private Buffer movingAverage = getMovingAverageDefault();
        
  private short moistureValue = 100;
  private short moistureValue_max = 1000;
  private short moistureValue_min = 0;
  private short moistureValue_step = 1;
  private long moistureValue_generator_period = 100;
  private Step moistureValue_direction = Step.UP;
  private long moistureValueCallbackPeriod;
  private long moistureValue_callback_id;
  private short moistureValue_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletMoisture.class);
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

    startMoistureValueGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_MOISTURE_VALUE) {
      buffer = getMoistureValue(packet);
    }
    else if (functionId == FUNCTION_SET_MOISTURE_CALLBACK_PERIOD) {
      buffer = setMoistureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_MOISTURE_CALLBACK_PERIOD) {
      buffer = getMoistureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_MOISTURE_CALLBACK_THRESHOLD) {
      buffer = setMoistureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_MOISTURE_CALLBACK_THRESHOLD) {
      buffer = getMoistureCallbackThreshold(packet);
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


  private void startMoistureValueGenerator() {
    if (moistureValue_step == 0) {
      return;
    }
    vertx.setPeriodic(moistureValue_generator_period, id -> {
      if (moistureValue_direction == Step.UP) {
        if (moistureValue >= moistureValue_max) {
          moistureValue_direction = Step.DOWN;
          this.moistureValue = (short) (moistureValue - moistureValue_step);
        } else {
          this.moistureValue = (short) (moistureValue + moistureValue_step);
        }
      } else {
        if (moistureValue <= moistureValue_min) {
          moistureValue_direction = Step.UP;
          this.moistureValue = (short) (moistureValue + moistureValue_step);
        } else {
          this.moistureValue = (short) (moistureValue - moistureValue_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor moisture
//fixme start_generator callback without sensor moistureReached
//fixme stop_generator callback without sensor moisture
//fixme stop_generator callback without sensor moistureReached
//fixme getter callback without sensor moisture
//fixme getter callback without sensor moistureReached

  private Buffer setMoistureCallbackPeriod(Packet packet) {
    moistureCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (moistureCallbackPeriod == 0) {
      stopMoistureCallback();
    } else {
      startMoistureCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MOISTURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getMoistureValueBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(moistureValue));

    return buffer;
  }
        
  private Buffer getMoistureValueBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getMoistureValueBuffer(FUNCTION_GET_MOISTURE_VALUE, options);
  }

  private Buffer getMoistureValue(Packet packet) {
    logger.debug("function getMoistureValue");
    if (packet.getResponseExpected()) {
      return getMoistureValueBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getMoistureCallbackThreshold(Packet packet) {
    logger.debug("function getMoistureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_MOISTURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.moistureCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getMoistureCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
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
  private Buffer getMoistureCallbackPeriod(Packet packet) {
    logger.debug("function getMoistureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_MOISTURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(moistureCallbackPeriod));

      return buffer;
    }

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
  private Buffer setMoistureCallbackThreshold(Packet packet) {
    logger.debug("function setMoistureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MOISTURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.moistureCallbackThreshold = packet.getPayload();
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
}
