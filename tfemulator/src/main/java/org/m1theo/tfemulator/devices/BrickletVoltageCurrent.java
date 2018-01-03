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
 * Measures power, DC voltage and DC current up to 720W/36V/20A
 */
public class BrickletVoltageCurrent extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 227;
public final static String DEVICE_DISPLAY_NAME = "Voltage/Current Bricklet";

  public final static byte FUNCTION_GET_CURRENT = (byte)1;
  public final static byte FUNCTION_GET_VOLTAGE = (byte)2;
  public final static byte FUNCTION_GET_POWER = (byte)3;
  public final static byte FUNCTION_SET_CONFIGURATION = (byte)4;
  public final static byte FUNCTION_GET_CONFIGURATION = (byte)5;
  public final static byte FUNCTION_SET_CALIBRATION = (byte)6;
  public final static byte FUNCTION_GET_CALIBRATION = (byte)7;
  public final static byte FUNCTION_SET_CURRENT_CALLBACK_PERIOD = (byte)8;
  public final static byte FUNCTION_GET_CURRENT_CALLBACK_PERIOD = (byte)9;
  public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD = (byte)10;
  public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD = (byte)11;
  public final static byte FUNCTION_SET_POWER_CALLBACK_PERIOD = (byte)12;
  public final static byte FUNCTION_GET_POWER_CALLBACK_PERIOD = (byte)13;
  public final static byte FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD = (byte)14;
  public final static byte FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD = (byte)15;
  public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD = (byte)16;
  public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD = (byte)17;
  public final static byte FUNCTION_SET_POWER_CALLBACK_THRESHOLD = (byte)18;
  public final static byte FUNCTION_GET_POWER_CALLBACK_THRESHOLD = (byte)19;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)20;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)21;
  public final static byte CALLBACK_CURRENT = (byte)22;
  public final static byte CALLBACK_VOLTAGE = (byte)23;
  public final static byte CALLBACK_POWER = (byte)24;
  public final static byte CALLBACK_CURRENT_REACHED = (byte)25;
  public final static byte CALLBACK_VOLTAGE_REACHED = (byte)26;
  public final static byte CALLBACK_POWER_REACHED = (byte)27;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short AVERAGING_1 = (short)0;
  public final static short AVERAGING_4 = (short)1;
  public final static short AVERAGING_16 = (short)2;
  public final static short AVERAGING_64 = (short)3;
  public final static short AVERAGING_128 = (short)4;
  public final static short AVERAGING_256 = (short)5;
  public final static short AVERAGING_512 = (short)6;
  public final static short AVERAGING_1024 = (short)7;
  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer configuration = getConfigurationDefault();
        
  private Buffer calibration = getCalibrationDefault();
        
  private Buffer powerCallbackThreshold = Utils.getThresholdDefault();
  private Buffer voltageCallbackThreshold = Utils.getThresholdDefault();
  private Buffer currentCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private int current = 100;
  private int current_max = 1000;
  private int current_min = 0;
  private int current_step = 1;
  private long current_generator_period = 100;
  private Step current_direction = Step.UP;
  private long currentCallbackPeriod;
  private long current_callback_id;
  private int current_last_value_called_back;

  private int voltage = 100;
  private int voltage_max = 1000;
  private int voltage_min = 0;
  private int voltage_step = 1;
  private long voltage_generator_period = 100;
  private Step voltage_direction = Step.UP;
  private long voltageCallbackPeriod;
  private long voltage_callback_id;
  private int voltage_last_value_called_back;

  private int power = 100;
  private int power_max = 1000;
  private int power_min = 0;
  private int power_step = 1;
  private long power_generator_period = 100;
  private Step power_direction = Step.UP;
  private long powerCallbackPeriod;
  private long power_callback_id;
  private int power_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletVoltageCurrent.class);
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

    startCurrentGenerator();
    startVoltageGenerator();
    startPowerGenerator();
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
    else if (functionId == FUNCTION_GET_VOLTAGE) {
      buffer = getVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_POWER) {
      buffer = getPower(packet);
    }
    else if (functionId == FUNCTION_SET_CONFIGURATION) {
      buffer = setConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIGURATION) {
      buffer = getConfiguration(packet);
    }
    else if (functionId == FUNCTION_SET_CALIBRATION) {
      buffer = setCalibration(packet);
    }
    else if (functionId == FUNCTION_GET_CALIBRATION) {
      buffer = getCalibration(packet);
    }
    else if (functionId == FUNCTION_SET_CURRENT_CALLBACK_PERIOD) {
      buffer = setCurrentCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_CALLBACK_PERIOD) {
      buffer = getCurrentCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD) {
      buffer = setVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD) {
      buffer = getVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_POWER_CALLBACK_PERIOD) {
      buffer = setPowerCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_POWER_CALLBACK_PERIOD) {
      buffer = getPowerCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD) {
      buffer = setCurrentCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD) {
      buffer = getCurrentCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = setVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = getVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_POWER_CALLBACK_THRESHOLD) {
      buffer = setPowerCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_POWER_CALLBACK_THRESHOLD) {
      buffer = getPowerCallbackThreshold(packet);
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


  private void startCurrentGenerator() {
    if (current_step == 0) {
      return;
    }
    vertx.setPeriodic(current_generator_period, id -> {
      if (current_direction == Step.UP) {
        if (current >= current_max) {
          current_direction = Step.DOWN;
          this.current = (int) (current - current_step);
        } else {
          this.current = (int) (current + current_step);
        }
      } else {
        if (current <= current_min) {
          current_direction = Step.UP;
          this.current = (int) (current + current_step);
        } else {
          this.current = (int) (current - current_step);
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
        
  private void startPowerGenerator() {
    if (power_step == 0) {
      return;
    }
    vertx.setPeriodic(power_generator_period, id -> {
      if (power_direction == Step.UP) {
        if (power >= power_max) {
          power_direction = Step.DOWN;
          this.power = (int) (power - power_step);
        } else {
          this.power = (int) (power + power_step);
        }
      } else {
        if (power <= power_min) {
          power_direction = Step.UP;
          this.power = (int) (power + power_step);
        } else {
          this.power = (int) (power - power_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor powerReached

    private void stopPowerCallback() {
        vertx.cancelTimer(power_callback_id);
  }
        //fixme start_generator callback without sensor power

    private void stopCurrentCallback() {
        vertx.cancelTimer(current_callback_id);
  }
        //fixme start_generator callback without sensor current
//fixme start_generator callback without sensor voltageReached

    private void stopVoltageCallback() {
        vertx.cancelTimer(voltage_callback_id);
  }
        //fixme start_generator callback without sensor voltage
//fixme start_generator callback without sensor currentReached
//fixme stop_generator callback without sensor powerReached

  private void startPowerCallback() {
    logger.trace("powerCallbackPeriod is {}", powerCallbackPeriod);
    power_callback_id = vertx.setPeriodic(powerCallbackPeriod, id -> {
      if (power != power_last_value_called_back) {
        power_last_value_called_back = power;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("power sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getPower4Callback());
          }
        } else {
          logger.info("no handlerids found in power callback");
        }
      } else {
        logger.debug("power value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor power

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
//fixme stop_generator callback without sensor currentReached
//fixme getter callback without sensor powerReached

  private Buffer getPower4Callback() {
      byte options = (byte) 0;
      return getPowerBuffer(CALLBACK_POWER, options);
  }
        //fixme getter callback without sensor power

  private Buffer getCurrent4Callback() {
      byte options = (byte) 0;
      return getCurrentBuffer(CALLBACK_CURRENT, options);
  }
        //fixme getter callback without sensor current
//fixme getter callback without sensor voltageReached

  private Buffer getVoltage4Callback() {
      byte options = (byte) 0;
      return getVoltageBuffer(CALLBACK_VOLTAGE, options);
  }
        //fixme getter callback without sensor voltage
//fixme getter callback without sensor currentReached

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
        
  private Buffer setPowerCallbackPeriod(Packet packet) {
    powerCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (powerCallbackPeriod == 0) {
      stopPowerCallback();
    } else {
      startPowerCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_POWER_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getCurrentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(current));

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

  private Buffer getPowerBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(power));

    return buffer;
  }
        
  private Buffer getPowerBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getPowerBuffer(FUNCTION_GET_POWER, options);
  }

  private Buffer getPower(Packet packet) {
    logger.debug("function getPower");
    if (packet.getResponseExpected()) {
      return getPowerBuffer(packet);
    }
    return null;
  }
}
