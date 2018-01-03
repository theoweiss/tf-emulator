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
 * Measures two DC voltages between -35V and +35V with 24bit resolution each
 */
public class BrickletIndustrialDualAnalogIn extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 249;
public final static String DEVICE_DISPLAY_NAME = "Industrial Dual Analog In Bricklet";

  public final static byte FUNCTION_GET_VOLTAGE = (byte)1;
  public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte FUNCTION_SET_SAMPLE_RATE = (byte)8;
  public final static byte FUNCTION_GET_SAMPLE_RATE = (byte)9;
  public final static byte FUNCTION_SET_CALIBRATION = (byte)10;
  public final static byte FUNCTION_GET_CALIBRATION = (byte)11;
  public final static byte FUNCTION_GET_ADC_VALUES = (byte)12;
  public final static byte CALLBACK_VOLTAGE = (byte)13;
  public final static byte CALLBACK_VOLTAGE_REACHED = (byte)14;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short SAMPLE_RATE_976_SPS = (short)0;
  public final static short SAMPLE_RATE_488_SPS = (short)1;
  public final static short SAMPLE_RATE_244_SPS = (short)2;
  public final static short SAMPLE_RATE_122_SPS = (short)3;
  public final static short SAMPLE_RATE_61_SPS = (short)4;
  public final static short SAMPLE_RATE_4_SPS = (short)5;
  public final static short SAMPLE_RATE_2_SPS = (short)6;
  public final static short SAMPLE_RATE_1_SPS = (short)7;
  String uidString;

  private Buffer sampleRate = getSampleRateDefault();
        
  private Buffer calibration = getCalibrationDefault();
        
  private Buffer voltageCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private int aDCValues = 100;
  private int aDCValues_max = 1000;
  private int aDCValues_min = 0;
  private int aDCValues_step = 1;
  private long aDCValues_generator_period = 100;
  private Step aDCValues_direction = Step.UP;
  private long aDCValuesCallbackPeriod;
  private long aDCValues_callback_id;
  private int aDCValues_last_value_called_back;

  private int voltage = 100;
  private int voltage_max = 1000;
  private int voltage_min = 0;
  private int voltage_step = 1;
  private long voltage_generator_period = 100;
  private Step voltage_direction = Step.UP;
  private long voltageCallbackPeriod;
  private long voltage_callback_id;
  private int voltage_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletIndustrialDualAnalogIn.class);
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

    startADCValuesGenerator();
    startVoltageGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_VOLTAGE) {
      buffer = getVoltage(packet);
    }
    else if (functionId == FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD) {
      buffer = setVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD) {
      buffer = getVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = setVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = getVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_SAMPLE_RATE) {
      buffer = setSampleRate(packet);
    }
    else if (functionId == FUNCTION_GET_SAMPLE_RATE) {
      buffer = getSampleRate(packet);
    }
    else if (functionId == FUNCTION_SET_CALIBRATION) {
      buffer = setCalibration(packet);
    }
    else if (functionId == FUNCTION_GET_CALIBRATION) {
      buffer = getCalibration(packet);
    }
    else if (functionId == FUNCTION_GET_ADC_VALUES) {
      buffer = getADCValues(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startADCValuesGenerator() {
    if (aDCValues_step == 0) {
      return;
    }
    vertx.setPeriodic(aDCValues_generator_period, id -> {
      if (aDCValues_direction == Step.UP) {
        if (aDCValues >= aDCValues_max) {
          aDCValues_direction = Step.DOWN;
          this.aDCValues = (int) (aDCValues - aDCValues_step);
        } else {
          this.aDCValues = (int) (aDCValues + aDCValues_step);
        }
      } else {
        if (aDCValues <= aDCValues_min) {
          aDCValues_direction = Step.UP;
          this.aDCValues = (int) (aDCValues + aDCValues_step);
        } else {
          this.aDCValues = (int) (aDCValues - aDCValues_step);
        }
      }
    });
  }
        
  private void startVoltageGenerator() {
    if (voltage_step == 0) {
      return;
    }
    vertx.setPeriodic(voltage_generator_period, id -> {
      if (voltage_direction == Step.UP) {
        if (voltage >= voltage_max) {
          voltage_direction = Step.DOWN;
          this.voltage = (int) (voltage - voltage_step);
        } else {
          this.voltage = (int) (voltage + voltage_step);
        }
      } else {
        if (voltage <= voltage_min) {
          voltage_direction = Step.UP;
          this.voltage = (int) (voltage + voltage_step);
        } else {
          this.voltage = (int) (voltage - voltage_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor voltageReached

    private void stopVoltageCallback() {
        vertx.cancelTimer(voltage_callback_id);
  }
        //fixme start_generator callback without sensor voltage
//fixme stop_generator callback without sensor voltageReached

  private void startVoltageCallback() {
    logger.trace("voltageCallbackPeriod is {}", voltageCallbackPeriod);
    voltage_callback_id = vertx.setPeriodic(voltageCallbackPeriod, id -> {
      if (voltage != voltage_last_value_called_back) {
        voltage_last_value_called_back = voltage;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("voltage sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getVoltage4Callback());
          }
        } else {
          logger.info("no handlerids found in voltage callback");
        }
      } else {
        logger.debug("voltage value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor voltage
//fixme getter callback without sensor voltageReached

  private Buffer getVoltage4Callback() {
      byte options = (byte) 0;
      return getVoltageBuffer(CALLBACK_VOLTAGE, options);
  }
        //fixme getter callback without sensor voltage

  private Buffer setVoltageCallbackPeriod(Packet packet) {
    voltageCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (voltageCallbackPeriod == 0) {
      stopVoltageCallback();
    } else {
      startVoltageCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(voltage));

    return buffer;
  }
        
  private Buffer getVoltageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getVoltageBuffer(FUNCTION_GET_VOLTAGE, options);
  }

  private Buffer getVoltage(Packet packet) {
    logger.debug("function getVoltage");
    if (packet.getResponseExpected()) {
      return getVoltageBuffer(packet);
    }
    return null;
  }

  private Buffer getADCValuesBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 8;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(aDCValues));

    return buffer;
  }
        
  private Buffer getADCValuesBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getADCValuesBuffer(FUNCTION_GET_ADC_VALUES, options);
  }

  private Buffer getADCValues(Packet packet) {
    logger.debug("function getADCValues");
    if (packet.getResponseExpected()) {
      return getADCValuesBuffer(packet);
    }
    return null;
  }
}
