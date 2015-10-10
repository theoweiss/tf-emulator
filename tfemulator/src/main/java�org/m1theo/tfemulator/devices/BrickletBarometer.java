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
 * Measures air pressure and altitude changes
 */
public class BrickletBarometer extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 221;
public final static String DEVICE_DISPLAY_NAME = "Barometer Bricklet";

  public final static byte FUNCTION_GET_AIR_PRESSURE = (byte)1;
  public final static byte FUNCTION_GET_ALTITUDE = (byte)2;
  public final static byte FUNCTION_SET_AIR_PRESSURE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_GET_AIR_PRESSURE_CALLBACK_PERIOD = (byte)4;
  public final static byte FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_AIR_PRESSURE_CALLBACK_THRESHOLD = (byte)7;
  public final static byte FUNCTION_GET_AIR_PRESSURE_CALLBACK_THRESHOLD = (byte)8;
  public final static byte FUNCTION_SET_ALTITUDE_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_ALTITUDE_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)11;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)12;
  public final static byte FUNCTION_SET_REFERENCE_AIR_PRESSURE = (byte)13;
  public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)14;
  public final static byte CALLBACK_AIR_PRESSURE = (byte)15;
  public final static byte CALLBACK_ALTITUDE = (byte)16;
  public final static byte CALLBACK_AIR_PRESSURE_REACHED = (byte)17;
  public final static byte CALLBACK_ALTITUDE_REACHED = (byte)18;
  public final static byte FUNCTION_GET_REFERENCE_AIR_PRESSURE = (byte)19;
  public final static byte FUNCTION_SET_AVERAGING = (byte)20;
  public final static byte FUNCTION_GET_AVERAGING = (byte)21;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer airPressureCallbackThreshold = getAirPressureCallbackThresholdDefault();
        
  private Buffer altitudeCallbackThreshold = getAltitudeCallbackThresholdDefault();
        
  private Buffer referenceAirPressure = getReferenceAirPressureDefault();
        
  private Buffer averaging = getAveragingDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private int altitude = 100;
  private int altitude_max = 1000;
  private int altitude_min = 0;
  private int altitude_step = 1;
  private long altitude_generator_period = 100;
  private Step altitude_direction = Step.UP;
  private long altitudeCallbackPeriod;
  private long altitude_callback_id;
  private int altitude_last_value_called_back;

  private short chipTemperature = 100;
  private short chipTemperature_max = 1000;
  private short chipTemperature_min = 0;
  private short chipTemperature_step = 1;
  private long chipTemperature_generator_period = 100;
  private Step chipTemperature_direction = Step.UP;
  private long chipTemperatureCallbackPeriod;
  private long chipTemperature_callback_id;
  private short chipTemperature_last_value_called_back;

  private int airPressure = 100;
  private int airPressure_max = 1000;
  private int airPressure_min = 0;
  private int airPressure_step = 1;
  private long airPressure_generator_period = 100;
  private Step airPressure_direction = Step.UP;
  private long airPressureCallbackPeriod;
  private long airPressure_callback_id;
  private int airPressure_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 1;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletBarometer.class);
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

    startAltitudeGenerator();
    startChipTemperatureGenerator();
    startAirPressureGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_AIR_PRESSURE) {
      buffer = getAirPressure(packet);
    }
    else if (functionId == FUNCTION_GET_ALTITUDE) {
      buffer = getAltitude(packet);
    }
    else if (functionId == FUNCTION_SET_AIR_PRESSURE_CALLBACK_PERIOD) {
      buffer = setAirPressureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_AIR_PRESSURE_CALLBACK_PERIOD) {
      buffer = getAirPressureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD) {
      buffer = setAltitudeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD) {
      buffer = getAltitudeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_AIR_PRESSURE_CALLBACK_THRESHOLD) {
      buffer = setAirPressureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_AIR_PRESSURE_CALLBACK_THRESHOLD) {
      buffer = getAirPressureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_ALTITUDE_CALLBACK_THRESHOLD) {
      buffer = setAltitudeCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_ALTITUDE_CALLBACK_THRESHOLD) {
      buffer = getAltitudeCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_REFERENCE_AIR_PRESSURE) {
      buffer = setReferenceAirPressure(packet);
    }
    else if (functionId == FUNCTION_GET_CHIP_TEMPERATURE) {
      buffer = getChipTemperature(packet);
    }
    else if (functionId == FUNCTION_GET_REFERENCE_AIR_PRESSURE) {
      buffer = getReferenceAirPressure(packet);
    }
    else if (functionId == FUNCTION_SET_AVERAGING) {
      buffer = setAveraging(packet);
    }
    else if (functionId == FUNCTION_GET_AVERAGING) {
      buffer = getAveraging(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startAltitudeGenerator() {
    if (altitude_step == 0) {
      return;
    }
    vertx.setPeriodic(altitude_generator_period, id -> {
      if (altitude_direction == Step.UP) {
        if (altitude >= altitude_max) {
          altitude_direction = Step.DOWN;
          this.altitude = (int) (altitude - altitude_step);
        } else {
          this.altitude = (int) (altitude + altitude_step);
        }
      } else {
        if (altitude <= altitude_min) {
          altitude_direction = Step.UP;
          this.altitude = (int) (altitude + altitude_step);
        } else {
          this.altitude = (int) (altitude - altitude_step);
        }
      }
    });
  }
        
  private void startChipTemperatureGenerator() {
    if (chipTemperature_step == 0) {
      return;
    }
    vertx.setPeriodic(chipTemperature_generator_period, id -> {
      if (chipTemperature_direction == Step.UP) {
        if (chipTemperature >= chipTemperature_max) {
          chipTemperature_direction = Step.DOWN;
          this.chipTemperature = (short) (chipTemperature - chipTemperature_step);
        } else {
          this.chipTemperature = (short) (chipTemperature + chipTemperature_step);
        }
      } else {
        if (chipTemperature <= chipTemperature_min) {
          chipTemperature_direction = Step.UP;
          this.chipTemperature = (short) (chipTemperature + chipTemperature_step);
        } else {
          this.chipTemperature = (short) (chipTemperature - chipTemperature_step);
        }
      }
    });
  }
        
  private void startAirPressureGenerator() {
    if (airPressure_step == 0) {
      return;
    }
    vertx.setPeriodic(airPressure_generator_period, id -> {
      if (airPressure_direction == Step.UP) {
        if (airPressure >= airPressure_max) {
          airPressure_direction = Step.DOWN;
          this.airPressure = (int) (airPressure - airPressure_step);
        } else {
          this.airPressure = (int) (airPressure + airPressure_step);
        }
      } else {
        if (airPressure <= airPressure_min) {
          airPressure_direction = Step.UP;
          this.airPressure = (int) (airPressure + airPressure_step);
        } else {
          this.airPressure = (int) (airPressure - airPressure_step);
        }
      }
    });
  }
        
    private void stopAltitudeCallback() {
        vertx.cancelTimer(altitude_callback_id);
  }
        //fixme start_generator callback without sensor altitude
//fixme start_generator callback without sensor altitudeReached

    private void stopAirPressureCallback() {
        vertx.cancelTimer(airPressure_callback_id);
  }
        //fixme start_generator callback without sensor airPressure
//fixme start_generator callback without sensor airPressureReached

  private void startAltitudeCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    altitude_callback_id = vertx.setPeriodic(altitudeCallbackPeriod, id -> {
      if (altitude != altitude_last_value_called_back) {
        altitude_last_value_called_back = altitude;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("altitude sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAltitude4Callback());
          }
        } else {
          logger.info("no handlerids found in altitude callback");
        }
      } else {
        logger.debug("altitude value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor altitude
//fixme stop_generator callback without sensor altitudeReached

  private void startAirPressureCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    airPressure_callback_id = vertx.setPeriodic(airPressureCallbackPeriod, id -> {
      if (airPressure != airPressure_last_value_called_back) {
        airPressure_last_value_called_back = airPressure;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("airPressure sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAirPressure4Callback());
          }
        } else {
          logger.info("no handlerids found in airPressure callback");
        }
      } else {
        logger.debug("airPressure value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor airPressure
//fixme stop_generator callback without sensor airPressureReached

  private Buffer getAltitude4Callback() {
      byte options = (byte) 0;
      return getAltitudeBuffer(CALLBACK_ALTITUDE, options);
  }
        //fixme getter callback without sensor altitude
//fixme getter callback without sensor altitudeReached

  private Buffer getAirPressure4Callback() {
      byte options = (byte) 0;
      return getAirPressureBuffer(CALLBACK_AIR_PRESSURE, options);
  }
        //fixme getter callback without sensor airPressure
//fixme getter callback without sensor airPressureReached

  private Buffer setAirPressureCallbackPeriod(Packet packet) {
    airPressureCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (airPressureCallbackPeriod == 0) {
      stopAirPressureCallback();
    } else {
      startAirPressureCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_AIR_PRESSURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setAltitudeCallbackPeriod(Packet packet) {
    altitudeCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (altitudeCallbackPeriod == 0) {
      stopAltitudeCallback();
    } else {
      startAltitudeCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getAirPressureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(airPressure));

    return buffer;
  }
        
  private Buffer getAirPressureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAirPressureBuffer(FUNCTION_GET_AIR_PRESSURE, options);
  }

  private Buffer getAirPressure(Packet packet) {
    logger.debug("function getAirPressure");
    if (packet.getResponseExpected()) {
      return getAirPressureBuffer(packet);
    }
    return null;
  }

  private Buffer getAltitudeBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(altitude));

    return buffer;
  }
        
  private Buffer getAltitudeBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAltitudeBuffer(FUNCTION_GET_ALTITUDE, options);
  }

  private Buffer getAltitude(Packet packet) {
    logger.debug("function getAltitude");
    if (packet.getResponseExpected()) {
      return getAltitudeBuffer(packet);
    }
    return null;
  }

  private Buffer getChipTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(chipTemperature));

    return buffer;
  }
        
  private Buffer getChipTemperatureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getChipTemperatureBuffer(FUNCTION_GET_CHIP_TEMPERATURE, options);
  }

  private Buffer getChipTemperature(Packet packet) {
    logger.debug("function getChipTemperature");
    if (packet.getResponseExpected()) {
      return getChipTemperatureBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getAveraging(Packet packet) {
    logger.debug("function getAveraging");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_AVERAGING;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.averaging);
      return buffer;
    }

    return null;
  }

  private Buffer getAveragingDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));
      buffer.appendBytes(Utils.get1ByteURandomValue(1));
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getAirPressureCallbackThreshold(Packet packet) {
    logger.debug("function getAirPressureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_AIR_PRESSURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.airPressureCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getAirPressureCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

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
  private Buffer getAltitudeCallbackThreshold(Packet packet) {
    logger.debug("function getAltitudeCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_ALTITUDE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.altitudeCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getAltitudeCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getReferenceAirPressure(Packet packet) {
    logger.debug("function getReferenceAirPressure");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_REFERENCE_AIR_PRESSURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.referenceAirPressure);
      return buffer;
    }

    return null;
  }

  private Buffer getReferenceAirPressureDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getAirPressureCallbackPeriod(Packet packet) {
    logger.debug("function getAirPressureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_AIR_PRESSURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(airPressureCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAltitudeCallbackPeriod(Packet packet) {
    logger.debug("function getAltitudeCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(altitudeCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer setAltitudeCallbackThreshold(Packet packet) {
    logger.debug("function setAltitudeCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ALTITUDE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.altitudeCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setReferenceAirPressure(Packet packet) {
    logger.debug("function setReferenceAirPressure");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_REFERENCE_AIR_PRESSURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.referenceAirPressure = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAveraging(Packet packet) {
    logger.debug("function setAveraging");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_AVERAGING;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.averaging = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAirPressureCallbackThreshold(Packet packet) {
    logger.debug("function setAirPressureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_AIR_PRESSURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.airPressureCallbackThreshold = packet.getPayload();
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
