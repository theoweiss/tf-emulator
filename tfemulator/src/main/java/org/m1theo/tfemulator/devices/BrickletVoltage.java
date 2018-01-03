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
 * Measures DC voltage between 0V and 50V
 */
public class BrickletVoltage extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 218;
public final static String DEVICE_DISPLAY_NAME = "Voltage Bricklet";

  public final static byte FUNCTION_GET_VOLTAGE = (byte)1;
  public final static byte FUNCTION_GET_ANALOG_VALUE = (byte)2;
  public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD = (byte)4;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD = (byte)7;
  public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD = (byte)8;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)11;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)12;
  public final static byte CALLBACK_VOLTAGE = (byte)13;
  public final static byte CALLBACK_ANALOG_VALUE = (byte)14;
  public final static byte CALLBACK_VOLTAGE_REACHED = (byte)15;
  public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte)16;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer voltageCallbackThreshold = Utils.getThresholdDefault();
  private Buffer analogValueCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private short analogValue = 100;
  private short analogValue_max = 1000;
  private short analogValue_min = 0;
  private short analogValue_step = 1;
  private long analogValue_generator_period = 100;
  private Step analogValue_direction = Step.UP;
  private long analogValueCallbackPeriod;
  private long analogValue_callback_id;
  private short analogValue_last_value_called_back;

  private short voltage = 100;
  private short voltage_max = 1000;
  private short voltage_min = 0;
  private short voltage_step = 1;
  private long voltage_generator_period = 100;
  private Step voltage_direction = Step.UP;
  private long voltageCallbackPeriod;
  private long voltage_callback_id;
  private short voltage_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletVoltage.class);
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
    else if (functionId == FUNCTION_GET_ANALOG_VALUE) {
      buffer = getAnalogValue(packet);
    }
    else if (functionId == FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD) {
      buffer = setVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD) {
      buffer = getVoltageCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = setAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = getAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = setVoltageCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD) {
      buffer = getVoltageCallbackThreshold(packet);
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
        
  private void startVoltageGenerator() {
    if (voltage_step == 0) {
      return;
    }
    vertx.setPeriodic(voltage_generator_period, id -> {
      if (voltage_direction == Step.UP) {
        if (voltage >= voltage_max) {
          voltage_direction = Step.DOWN;
          this.voltage = (short) (voltage - voltage_step);
        } else {
          this.voltage = (short) (voltage + voltage_step);
        }
      } else {
        if (voltage <= voltage_min) {
          voltage_direction = Step.UP;
          this.voltage = (short) (voltage + voltage_step);
        } else {
          this.voltage = (short) (voltage - voltage_step);
        }
      }
    });
  }
        
    private void stopAnalogValueCallback() {
        vertx.cancelTimer(analogValue_callback_id);
  }
        //fixme start_generator callback without sensor analogValue
//fixme start_generator callback without sensor voltageReached

    private void stopVoltageCallback() {
        vertx.cancelTimer(voltage_callback_id);
  }
        //fixme start_generator callback without sensor voltage
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
//fixme stop_generator callback without sensor analogValueReached

  private Buffer getAnalogValue4Callback() {
      byte options = (byte) 0;
      return getAnalogValueBuffer(CALLBACK_ANALOG_VALUE, options);
  }
        //fixme getter callback without sensor analogValue
//fixme getter callback without sensor voltageReached

  private Buffer getVoltage4Callback() {
      byte options = (byte) 0;
      return getVoltageBuffer(CALLBACK_VOLTAGE, options);
  }
        //fixme getter callback without sensor voltage
//fixme getter callback without sensor analogValueReached

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
        
  private Buffer getVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(voltage));

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

  private Buffer getAnalogValueBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
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
}
