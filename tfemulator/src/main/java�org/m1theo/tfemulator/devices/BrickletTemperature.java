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
 * Measures ambient temperature with 0.5°C accuracy
 */
public class BrickletTemperature extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 216;
public final static String DEVICE_DISPLAY_NAME = "Temperature Bricklet";

  public final static byte FUNCTION_GET_TEMPERATURE = (byte)1;
  public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_TEMPERATURE = (byte)8;
  public final static byte CALLBACK_TEMPERATURE_REACHED = (byte)9;
  public final static byte FUNCTION_SET_I2C_MODE = (byte)10;
  public final static byte FUNCTION_GET_I2C_MODE = (byte)11;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short I2C_MODE_FAST = (short)0;
  public final static short I2C_MODE_SLOW = (short)1;
  String uidString;

  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private Buffer temperatureCallbackThreshold = getTemperatureCallbackThresholdDefault();
        
  private Buffer i2CMode = getI2CModeDefault();
        
  private short temperature = 100;
  private short temperature_max = 1000;
  private short temperature_min = 0;
  private short temperature_step = 1;
  private long temperature_generator_period = 100;
  private Step temperature_direction = Step.UP;
  private long temperatureCallbackPeriod;
  private long temperature_callback_id;
  private short temperature_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletTemperature.class);
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
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE) {
      buffer = getTemperature(packet);
    }
    else if (functionId == FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = setTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = getTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = setTemperatureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = getTemperatureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_I2C_MODE) {
      buffer = setI2CMode(packet);
    }
    else if (functionId == FUNCTION_GET_I2C_MODE) {
      buffer = getI2CMode(packet);
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
        //fixme start_generator callback without sensor temperatureReached

    private void stopTemperatureCallback() {
        vertx.cancelTimer(temperature_callback_id);
  }
        //fixme start_generator callback without sensor temperature
//fixme stop_generator callback without sensor temperatureReached

  private void startTemperatureCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    temperature_callback_id = vertx.setPeriodic(temperatureCallbackPeriod, id -> {
      if (temperature != temperature_last_value_called_back) {
        temperature_last_value_called_back = temperature;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("temperature sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getTemperature4Callback());
          }
        } else {
          logger.info("no handlerids found in temperature callback");
        }
      } else {
        logger.debug("temperature value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor temperature
//fixme getter callback without sensor temperatureReached

  private Buffer getTemperature4Callback() {
      byte options = (byte) 0;
      return getTemperatureBuffer(CALLBACK_TEMPERATURE, options);
  }
        //fixme getter callback without sensor temperature

  private Buffer setTemperatureCallbackPeriod(Packet packet) {
    temperatureCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (temperatureCallbackPeriod == 0) {
      stopTemperatureCallback();
    } else {
      startTemperatureCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
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
  private Buffer getTemperatureCallbackThreshold(Packet packet) {
    logger.debug("function getTemperatureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.temperatureCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getTemperatureCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getI2CMode(Packet packet) {
    logger.debug("function getI2CMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_I2C_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.i2CMode);
      return buffer;
    }

    return null;
  }

  private Buffer getI2CModeDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getTemperatureCallbackPeriod(Packet packet) {
    logger.debug("function getTemperatureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(temperatureCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer setI2CMode(Packet packet) {
    logger.debug("function setI2CMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_I2C_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.i2CMode = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setTemperatureCallbackThreshold(Packet packet) {
    logger.debug("function setTemperatureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.temperatureCallbackThreshold = packet.getPayload();
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
