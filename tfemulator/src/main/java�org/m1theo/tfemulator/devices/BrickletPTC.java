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
 * Reads temperatures from Pt100 und Pt1000 sensors
 */
public class BrickletPTC extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 226;
public final static String DEVICE_DISPLAY_NAME = "PTC Bricklet";

  public final static byte FUNCTION_GET_TEMPERATURE = (byte)1;
  public final static byte FUNCTION_GET_RESISTANCE = (byte)2;
  public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD = (byte)4;
  public final static byte FUNCTION_SET_RESISTANCE_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_RESISTANCE_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)7;
  public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)8;
  public final static byte FUNCTION_SET_RESISTANCE_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_RESISTANCE_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)11;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)12;
  public final static byte CALLBACK_TEMPERATURE = (byte)13;
  public final static byte CALLBACK_TEMPERATURE_REACHED = (byte)14;
  public final static byte CALLBACK_RESISTANCE = (byte)15;
  public final static byte CALLBACK_RESISTANCE_REACHED = (byte)16;
  public final static byte FUNCTION_SET_NOISE_REJECTION_FILTER = (byte)17;
  public final static byte FUNCTION_GET_NOISE_REJECTION_FILTER = (byte)18;
  public final static byte FUNCTION_IS_SENSOR_CONNECTED = (byte)19;
  public final static byte FUNCTION_SET_WIRE_MODE = (byte)20;
  public final static byte FUNCTION_GET_WIRE_MODE = (byte)21;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short FILTER_OPTION_50HZ = (short)0;
  public final static short FILTER_OPTION_60HZ = (short)1;
  public final static short WIRE_MODE_2 = (short)2;
  public final static short WIRE_MODE_3 = (short)3;
  public final static short WIRE_MODE_4 = (short)4;
  String uidString;

  private Buffer wireMode = getWireModeDefault();
        
  private Buffer temperatureCallbackThreshold = getTemperatureCallbackThresholdDefault();
        
  private Buffer resistanceCallbackThreshold = getResistanceCallbackThresholdDefault();
        
  private Buffer noiseRejectionFilter = getNoiseRejectionFilterDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private int temperature = 100;
  private int temperature_max = 1000;
  private int temperature_min = 0;
  private int temperature_step = 1;
  private long temperature_generator_period = 100;
  private Step temperature_direction = Step.UP;
  private long temperatureCallbackPeriod;
  private long temperature_callback_id;
  private int temperature_last_value_called_back;

  private short resistance = 100;
  private short resistance_max = 1000;
  private short resistance_min = 0;
  private short resistance_step = 1;
  private long resistance_generator_period = 100;
  private Step resistance_direction = Step.UP;
  private long resistanceCallbackPeriod;
  private long resistance_callback_id;
  private short resistance_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletPTC.class);
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
    startResistanceGenerator();
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
    else if (functionId == FUNCTION_GET_RESISTANCE) {
      buffer = getResistance(packet);
    }
    else if (functionId == FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = setTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = getTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_RESISTANCE_CALLBACK_PERIOD) {
      buffer = setResistanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_RESISTANCE_CALLBACK_PERIOD) {
      buffer = getResistanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = setTemperatureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = getTemperatureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_RESISTANCE_CALLBACK_THRESHOLD) {
      buffer = setResistanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_RESISTANCE_CALLBACK_THRESHOLD) {
      buffer = getResistanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_NOISE_REJECTION_FILTER) {
      buffer = setNoiseRejectionFilter(packet);
    }
    else if (functionId == FUNCTION_GET_NOISE_REJECTION_FILTER) {
      buffer = getNoiseRejectionFilter(packet);
    }
    else if (functionId == FUNCTION_IS_SENSOR_CONNECTED) {
      buffer = isSensorConnected(packet);
    }
    else if (functionId == FUNCTION_SET_WIRE_MODE) {
      buffer = setWireMode(packet);
    }
    else if (functionId == FUNCTION_GET_WIRE_MODE) {
      buffer = getWireMode(packet);
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
          this.temperature = (int) (temperature - temperature_step);
        } else {
          this.temperature = (int) (temperature + temperature_step);
        }
      } else {
        if (temperature <= temperature_min) {
          temperature_direction = Step.UP;
          this.temperature = (int) (temperature + temperature_step);
        } else {
          this.temperature = (int) (temperature - temperature_step);
        }
      }
    });
  }
        
  private void startResistanceGenerator() {
    if (resistance_step == 0) {
      return;
    }
    vertx.setPeriodic(resistance_generator_period, id -> {
      if (resistance_direction == Step.UP) {
        if (resistance >= resistance_max) {
          resistance_direction = Step.DOWN;
          this.resistance = (short) (resistance - resistance_step);
        } else {
          this.resistance = (short) (resistance + resistance_step);
        }
      } else {
        if (resistance <= resistance_min) {
          resistance_direction = Step.UP;
          this.resistance = (short) (resistance + resistance_step);
        } else {
          this.resistance = (short) (resistance - resistance_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor temperatureReached
//fixme start_generator callback without sensor resistanceReached

    private void stopResistanceCallback() {
        vertx.cancelTimer(resistance_callback_id);
  }
        //fixme start_generator callback without sensor resistance

    private void stopTemperatureCallback() {
        vertx.cancelTimer(temperature_callback_id);
  }
        //fixme start_generator callback without sensor temperature
//fixme stop_generator callback without sensor temperatureReached
//fixme stop_generator callback without sensor resistanceReached

  private void startResistanceCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    resistance_callback_id = vertx.setPeriodic(resistanceCallbackPeriod, id -> {
      if (resistance != resistance_last_value_called_back) {
        resistance_last_value_called_back = resistance;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("resistance sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getResistance4Callback());
          }
        } else {
          logger.info("no handlerids found in resistance callback");
        }
      } else {
        logger.debug("resistance value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor resistance

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
//fixme getter callback without sensor resistanceReached

  private Buffer getResistance4Callback() {
      byte options = (byte) 0;
      return getResistanceBuffer(CALLBACK_RESISTANCE, options);
  }
        //fixme getter callback without sensor resistance

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
        
  private Buffer setResistanceCallbackPeriod(Packet packet) {
    resistanceCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (resistanceCallbackPeriod == 0) {
      stopResistanceCallback();
    } else {
      startResistanceCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_RESISTANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(temperature));

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

  private Buffer getResistanceBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(resistance));

    return buffer;
  }
        
  private Buffer getResistanceBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getResistanceBuffer(FUNCTION_GET_RESISTANCE, options);
  }

  private Buffer getResistance(Packet packet) {
    logger.debug("function getResistance");
    if (packet.getResponseExpected()) {
      return getResistanceBuffer(packet);
    }
    return null;
  }

  private Buffer getIsSensorConnectedBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8(isSensorConnected));

    return buffer;
  }
        
  private Buffer getIsSensorConnectedBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIsSensorConnectedBuffer(FUNCTION_IS_SENSOR_CONNECTED, options);
  }

  private Buffer getIsSensorConnected(Packet packet) {
    logger.debug("function getIsSensorConnected");
    if (packet.getResponseExpected()) {
      return getIsSensorConnectedBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getWireMode(Packet packet) {
    logger.debug("function getWireMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_WIRE_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.wireMode);
      return buffer;
    }

    return null;
  }

  private Buffer getWireModeDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getNoiseRejectionFilter(Packet packet) {
    logger.debug("function getNoiseRejectionFilter");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_NOISE_REJECTION_FILTER;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.noiseRejectionFilter);
      return buffer;
    }

    return null;
  }

  private Buffer getNoiseRejectionFilterDefault() {
      Buffer buffer = Buffer.buffer();
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
  private Buffer getTemperatureCallbackThreshold(Packet packet) {
    logger.debug("function getTemperatureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
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
      buffer.appendBytes(Utils.get4ByteRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getResistanceCallbackThreshold(Packet packet) {
    logger.debug("function getResistanceCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_RESISTANCE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.resistanceCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getResistanceCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getResistanceCallbackPeriod(Packet packet) {
    logger.debug("function getResistanceCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_RESISTANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(resistanceCallbackPeriod));

      return buffer;
    }

    return null;
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
  private Buffer setResistanceCallbackThreshold(Packet packet) {
    logger.debug("function setResistanceCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_RESISTANCE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.resistanceCallbackThreshold = packet.getPayload();
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
  private Buffer setWireMode(Packet packet) {
    logger.debug("function setWireMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_WIRE_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.wireMode = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setNoiseRejectionFilter(Packet packet) {
    logger.debug("function setNoiseRejectionFilter");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_NOISE_REJECTION_FILTER;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.noiseRejectionFilter = packet.getPayload();
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
  private Buffer isSensorConnected(Packet packet) {
    //TODO dummy method
    return null;
  }
}
