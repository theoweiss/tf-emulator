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
 * Measures acceleration in three axis
 */
public class BrickletAccelerometer extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 250;
public final static String DEVICE_DISPLAY_NAME = "Accelerometer Bricklet";

  public final static byte FUNCTION_GET_ACCELERATION = (byte)1;
  public final static byte FUNCTION_SET_ACCELERATION_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_ACCELERATION_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_ACCELERATION_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_ACCELERATION_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte FUNCTION_GET_TEMPERATURE = (byte)8;
  public final static byte FUNCTION_SET_CONFIGURATION = (byte)9;
  public final static byte FUNCTION_GET_CONFIGURATION = (byte)10;
  public final static byte FUNCTION_LED_ON = (byte)11;
  public final static byte FUNCTION_LED_OFF = (byte)12;
  public final static byte FUNCTION_IS_LED_ON = (byte)13;
  public final static byte CALLBACK_ACCELERATION = (byte)14;
  public final static byte CALLBACK_ACCELERATION_REACHED = (byte)15;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short DATA_RATE_OFF = (short)0;
  public final static short DATA_RATE_3HZ = (short)1;
  public final static short DATA_RATE_6HZ = (short)2;
  public final static short DATA_RATE_12HZ = (short)3;
  public final static short DATA_RATE_25HZ = (short)4;
  public final static short DATA_RATE_50HZ = (short)5;
  public final static short DATA_RATE_100HZ = (short)6;
  public final static short DATA_RATE_400HZ = (short)7;
  public final static short DATA_RATE_800HZ = (short)8;
  public final static short DATA_RATE_1600HZ = (short)9;
  public final static short FULL_SCALE_2G = (short)0;
  public final static short FULL_SCALE_4G = (short)1;
  public final static short FULL_SCALE_6G = (short)2;
  public final static short FULL_SCALE_8G = (short)3;
  public final static short FULL_SCALE_16G = (short)4;
  public final static short FILTER_BANDWIDTH_800HZ = (short)0;
  public final static short FILTER_BANDWIDTH_400HZ = (short)1;
  public final static short FILTER_BANDWIDTH_200HZ = (short)2;
  public final static short FILTER_BANDWIDTH_50HZ = (short)3;
  String uidString;

  private Buffer configuration = getConfigurationDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private Buffer accelerationCallbackThreshold = getAccelerationCallbackThresholdDefault();
        
  private Buffer led = getLedDefault();
        
  private short temperature = 100;
  private short temperature_max = 1000;
  private short temperature_min = 0;
  private short temperature_step = 1;
  private long temperature_generator_period = 100;
  private Step temperature_direction = Step.UP;
  private long temperatureCallbackPeriod;
  private long temperature_callback_id;
  private short temperature_last_value_called_back;

  private short acceleration = 100;
  private short acceleration_max = 1000;
  private short acceleration_min = 0;
  private short acceleration_step = 1;
  private long acceleration_generator_period = 100;
  private Step acceleration_direction = Step.UP;
  private long accelerationCallbackPeriod;
  private long acceleration_callback_id;
  private short acceleration_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletAccelerometer.class);
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

    startTemperatureGenerator();
    startAccelerationGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_ACCELERATION) {
      buffer = getAcceleration(packet);
    }
    else if (functionId == FUNCTION_SET_ACCELERATION_CALLBACK_PERIOD) {
      buffer = setAccelerationCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ACCELERATION_CALLBACK_PERIOD) {
      buffer = getAccelerationCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ACCELERATION_CALLBACK_THRESHOLD) {
      buffer = setAccelerationCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_ACCELERATION_CALLBACK_THRESHOLD) {
      buffer = getAccelerationCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE) {
      buffer = getTemperature(packet);
    }
    else if (functionId == FUNCTION_SET_CONFIGURATION) {
      buffer = setConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIGURATION) {
      buffer = getConfiguration(packet);
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
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startTemperatureGenerator() {
    if (temperature_step == 0) {
      return;
    }
    vertx.setPeriodic(temperature_generator_period, id -> {
      if (temperature_direction == Step.UP) {
        if (temperature >= temperature_max) {
          temperature_direction = Step.DOWN;
          this.temperature = (short) (temperature - temperature_step);
        } else {
          this.temperature = (short) (temperature + temperature_step);
        }
      } else {
        if (temperature <= temperature_min) {
          temperature_direction = Step.UP;
          this.temperature = (short) (temperature + temperature_step);
        } else {
          this.temperature = (short) (temperature - temperature_step);
        }
      }
    });
  }
        
  private void startAccelerationGenerator() {
    if (acceleration_step == 0) {
      return;
    }
    vertx.setPeriodic(acceleration_generator_period, id -> {
      if (acceleration_direction == Step.UP) {
        if (acceleration >= acceleration_max) {
          acceleration_direction = Step.DOWN;
          this.acceleration = (short) (acceleration - acceleration_step);
        } else {
          this.acceleration = (short) (acceleration + acceleration_step);
        }
      } else {
        if (acceleration <= acceleration_min) {
          acceleration_direction = Step.UP;
          this.acceleration = (short) (acceleration + acceleration_step);
        } else {
          this.acceleration = (short) (acceleration - acceleration_step);
        }
      }
    });
  }
        
    private void stopAccelerationCallback() {
        vertx.cancelTimer(acceleration_callback_id);
  }
        //fixme start_generator callback without sensor acceleration
//fixme start_generator callback without sensor accelerationReached

  private void startAccelerationCallback() {
    logger.trace("accelerationCallbackPeriod is {}", accelerationCallbackPeriod);
    acceleration_callback_id = vertx.setPeriodic(accelerationCallbackPeriod, id -> {
      if (acceleration != acceleration_last_value_called_back) {
        acceleration_last_value_called_back = acceleration;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("acceleration sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAcceleration4Callback());
          }
        } else {
          logger.info("no handlerids found in acceleration callback");
        }
      } else {
        logger.debug("acceleration value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor acceleration
//fixme stop_generator callback without sensor accelerationReached

  private Buffer getAcceleration4Callback() {
      byte options = (byte) 0;
      return getAccelerationBuffer(CALLBACK_ACCELERATION, options);
  }
        //fixme getter callback without sensor acceleration
//fixme getter callback without sensor accelerationReached

  private Buffer setAccelerationCallbackPeriod(Packet packet) {
    accelerationCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (accelerationCallbackPeriod == 0) {
      stopAccelerationCallback();
    } else {
      startAccelerationCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ACCELERATION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getAccelerationBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 6;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(acceleration));
    buffer.appendBytes(Utils.getUInt16(acceleration));
    buffer.appendBytes(Utils.getUInt16(acceleration));

    return buffer;
  }
        
  private Buffer getAccelerationBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAccelerationBuffer(FUNCTION_GET_ACCELERATION, options);
  }

  private Buffer getAcceleration(Packet packet) {
    logger.debug("function getAcceleration");
    if (packet.getResponseExpected()) {
      return getAccelerationBuffer(packet);
    }
    return null;
  }

  private Buffer getTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(temperature));

    return buffer;
  }
        
  private Buffer getTemperatureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getTemperatureBuffer(FUNCTION_GET_TEMPERATURE, options);
  }

  private Buffer getTemperature(Packet packet) {
    logger.debug("function getTemperature");
    if (packet.getResponseExpected()) {
      return getTemperatureBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getConfiguration(Packet packet) {
    logger.debug("function getConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
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
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

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
  private Buffer getAccelerationCallbackThreshold(Packet packet) {
    logger.debug("function getAccelerationCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 13;
      byte functionId = FUNCTION_GET_ACCELERATION_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.accelerationCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getAccelerationCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));

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
  private Buffer getAccelerationCallbackPeriod(Packet packet) {
    logger.debug("function getAccelerationCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ACCELERATION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(accelerationCallbackPeriod));

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
  private Buffer setAccelerationCallbackThreshold(Packet packet) {
    logger.debug("function setAccelerationCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ACCELERATION_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.accelerationCallbackThreshold = packet.getPayload();
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
}
