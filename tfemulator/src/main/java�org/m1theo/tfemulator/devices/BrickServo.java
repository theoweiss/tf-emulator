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
 * Drives up to 7 RC Servos with up to 3A
 */
public class BrickServo extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 14;
public final static String DEVICE_DISPLAY_NAME = "Servo Brick";

  public final static byte FUNCTION_ENABLE = (byte)1;
  public final static byte FUNCTION_DISABLE = (byte)2;
  public final static byte FUNCTION_IS_ENABLED = (byte)3;
  public final static byte FUNCTION_SET_POSITION = (byte)4;
  public final static byte FUNCTION_GET_POSITION = (byte)5;
  public final static byte FUNCTION_GET_CURRENT_POSITION = (byte)6;
  public final static byte FUNCTION_SET_VELOCITY = (byte)7;
  public final static byte FUNCTION_GET_VELOCITY = (byte)8;
  public final static byte FUNCTION_GET_CURRENT_VELOCITY = (byte)9;
  public final static byte FUNCTION_SET_ACCELERATION = (byte)10;
  public final static byte FUNCTION_GET_ACCELERATION = (byte)11;
  public final static byte FUNCTION_SET_OUTPUT_VOLTAGE = (byte)12;
  public final static byte FUNCTION_GET_OUTPUT_VOLTAGE = (byte)13;
  public final static byte FUNCTION_SET_PULSE_WIDTH = (byte)14;
  public final static byte FUNCTION_GET_PULSE_WIDTH = (byte)15;
  public final static byte FUNCTION_SET_DEGREE = (byte)16;
  public final static byte FUNCTION_GET_DEGREE = (byte)17;
  public final static byte FUNCTION_SET_PERIOD = (byte)18;
  public final static byte FUNCTION_GET_PERIOD = (byte)19;
  public final static byte FUNCTION_GET_SERVO_CURRENT = (byte)20;
  public final static byte FUNCTION_GET_OVERALL_CURRENT = (byte)21;
  public final static byte FUNCTION_GET_STACK_INPUT_VOLTAGE = (byte)22;
  public final static byte FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE = (byte)23;
  public final static byte FUNCTION_SET_MINIMUM_VOLTAGE = (byte)24;
  public final static byte FUNCTION_GET_MINIMUM_VOLTAGE = (byte)25;
  public final static byte CALLBACK_UNDER_VOLTAGE = (byte)26;
  public final static byte CALLBACK_POSITION_REACHED = (byte)27;
  public final static byte CALLBACK_VELOCITY_REACHED = (byte)28;
  public final static byte FUNCTION_ENABLE_POSITION_REACHED_CALLBACK = (byte)29;
  public final static byte FUNCTION_DISABLE_POSITION_REACHED_CALLBACK = (byte)30;
  public final static byte FUNCTION_IS_POSITION_REACHED_CALLBACK_ENABLED = (byte)31;
  public final static byte FUNCTION_ENABLE_VELOCITY_REACHED_CALLBACK = (byte)32;
  public final static byte FUNCTION_DISABLE_VELOCITY_REACHED_CALLBACK = (byte)33;
  public final static byte FUNCTION_IS_VELOCITY_REACHED_CALLBACK_ENABLED = (byte)34;
  public final static byte FUNCTION_ENABLE_STATUS_LED = (byte)238;
  public final static byte FUNCTION_DISABLE_STATUS_LED = (byte)239;
  public final static byte FUNCTION_IS_STATUS_LED_ENABLED = (byte)240;
  public final static byte FUNCTION_GET_PROTOCOL1_BRICKLET_NAME = (byte)241;
  public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)242;
  public final static byte FUNCTION_RESET = (byte)243;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  String uidString;

  private Buffer pulseWidth = getPulseWidthDefault();
        
  private Buffer degree = getDegreeDefault();
        
  private Buffer velocity = getVelocityDefault();
        
  private Buffer VelocityReachedCallback = getVelocityReachedCallbackDefault();
        
  private Buffer enabled = getEnabledDefault();
        
  private Buffer minimumVoltage = getMinimumVoltageDefault();
        
  private Buffer outputVoltage = getOutputVoltageDefault();
        
  private Buffer period = getPeriodDefault();
        
  private Buffer PositionReachedCallback = getPositionReachedCallbackDefault();
        
  private Buffer StatusLED = getStatusLEDDefault();
        
  private Buffer position = getPositionDefault();
        
  private Buffer acceleration = getAccelerationDefault();
        
  private short currentPosition = 100;
  private short currentPosition_max = 1000;
  private short currentPosition_min = 0;
  private short currentPosition_step = 1;
  private long currentPosition_generator_period = 100;
  private Step currentPosition_direction = Step.UP;
  private long currentPositionCallbackPeriod;
  private long currentPosition_callback_id;
  private short currentPosition_last_value_called_back;

  private short servoCurrent = 100;
  private short servoCurrent_max = 1000;
  private short servoCurrent_min = 0;
  private short servoCurrent_step = 1;
  private long servoCurrent_generator_period = 100;
  private Step servoCurrent_direction = Step.UP;
  private long servoCurrentCallbackPeriod;
  private long servoCurrent_callback_id;
  private short servoCurrent_last_value_called_back;

  private short chipTemperature = 100;
  private short chipTemperature_max = 1000;
  private short chipTemperature_min = 0;
  private short chipTemperature_step = 1;
  private long chipTemperature_generator_period = 100;
  private Step chipTemperature_direction = Step.UP;
  private long chipTemperatureCallbackPeriod;
  private long chipTemperature_callback_id;
  private short chipTemperature_last_value_called_back;

  private short overallCurrent = 100;
  private short overallCurrent_max = 1000;
  private short overallCurrent_min = 0;
  private short overallCurrent_step = 1;
  private long overallCurrent_generator_period = 100;
  private Step overallCurrent_direction = Step.UP;
  private long overallCurrentCallbackPeriod;
  private long overallCurrent_callback_id;
  private short overallCurrent_last_value_called_back;

  private short currentVelocity = 100;
  private short currentVelocity_max = 1000;
  private short currentVelocity_min = 0;
  private short currentVelocity_step = 1;
  private long currentVelocity_generator_period = 100;
  private Step currentVelocity_direction = Step.UP;
  private long currentVelocityCallbackPeriod;
  private long currentVelocity_callback_id;
  private short currentVelocity_last_value_called_back;

  private short externalInputVoltage = 100;
  private short externalInputVoltage_max = 1000;
  private short externalInputVoltage_min = 0;
  private short externalInputVoltage_step = 1;
  private long externalInputVoltage_generator_period = 100;
  private Step externalInputVoltage_direction = Step.UP;
  private long externalInputVoltageCallbackPeriod;
  private long externalInputVoltage_callback_id;
  private short externalInputVoltage_last_value_called_back;

  private short stackInputVoltage = 100;
  private short stackInputVoltage_max = 1000;
  private short stackInputVoltage_min = 0;
  private short stackInputVoltage_step = 1;
  private long stackInputVoltage_generator_period = 100;
  private Step stackInputVoltage_direction = Step.UP;
  private long stackInputVoltageCallbackPeriod;
  private long stackInputVoltage_callback_id;
  private short stackInputVoltage_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickServo.class);
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

    startCurrentPositionGenerator();
    startServoCurrentGenerator();
    startChipTemperatureGenerator();
    startOverallCurrentGenerator();
    startCurrentVelocityGenerator();
    startExternalInputVoltageGenerator();
    startStackInputVoltageGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_ENABLE) {
      buffer = enable(packet);
    }
    else if (functionId == FUNCTION_DISABLE) {
      buffer = disable(packet);
    }
    else if (functionId == FUNCTION_IS_ENABLED) {
      buffer = isEnabled(packet);
    }
    else if (functionId == FUNCTION_SET_POSITION) {
      buffer = setPosition(packet);
    }
    else if (functionId == FUNCTION_GET_POSITION) {
      buffer = getPosition(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_POSITION) {
      buffer = getCurrentPosition(packet);
    }
    else if (functionId == FUNCTION_SET_VELOCITY) {
      buffer = setVelocity(packet);
    }
    else if (functionId == FUNCTION_GET_VELOCITY) {
      buffer = getVelocity(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_VELOCITY) {
      buffer = getCurrentVelocity(packet);
    }
    else if (functionId == FUNCTION_SET_ACCELERATION) {
      buffer = setAcceleration(packet);
    }
    else if (functionId == FUNCTION_GET_ACCELERATION) {
      buffer = getAcceleration(packet);
    }
    else if (functionId == FUNCTION_SET_OUTPUT_VOLTAGE) {
      buffer = setOutputVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_OUTPUT_VOLTAGE) {
      buffer = getOutputVoltage(packet);
    }
    else if (functionId == FUNCTION_SET_PULSE_WIDTH) {
      buffer = setPulseWidth(packet);
    }
    else if (functionId == FUNCTION_GET_PULSE_WIDTH) {
      buffer = getPulseWidth(packet);
    }
    else if (functionId == FUNCTION_SET_DEGREE) {
      buffer = setDegree(packet);
    }
    else if (functionId == FUNCTION_GET_DEGREE) {
      buffer = getDegree(packet);
    }
    else if (functionId == FUNCTION_SET_PERIOD) {
      buffer = setPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_PERIOD) {
      buffer = getPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_SERVO_CURRENT) {
      buffer = getServoCurrent(packet);
    }
    else if (functionId == FUNCTION_GET_OVERALL_CURRENT) {
      buffer = getOverallCurrent(packet);
    }
    else if (functionId == FUNCTION_GET_STACK_INPUT_VOLTAGE) {
      buffer = getStackInputVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE) {
      buffer = getExternalInputVoltage(packet);
    }
    else if (functionId == FUNCTION_SET_MINIMUM_VOLTAGE) {
      buffer = setMinimumVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_MINIMUM_VOLTAGE) {
      buffer = getMinimumVoltage(packet);
    }
    else if (functionId == FUNCTION_ENABLE_POSITION_REACHED_CALLBACK) {
      buffer = enablePositionReachedCallback(packet);
    }
    else if (functionId == FUNCTION_DISABLE_POSITION_REACHED_CALLBACK) {
      buffer = disablePositionReachedCallback(packet);
    }
    else if (functionId == FUNCTION_IS_POSITION_REACHED_CALLBACK_ENABLED) {
      buffer = isPositionReachedCallbackEnabled(packet);
    }
    else if (functionId == FUNCTION_ENABLE_VELOCITY_REACHED_CALLBACK) {
      buffer = enableVelocityReachedCallback(packet);
    }
    else if (functionId == FUNCTION_DISABLE_VELOCITY_REACHED_CALLBACK) {
      buffer = disableVelocityReachedCallback(packet);
    }
    else if (functionId == FUNCTION_IS_VELOCITY_REACHED_CALLBACK_ENABLED) {
      buffer = isVelocityReachedCallbackEnabled(packet);
    }
    else if (functionId == FUNCTION_ENABLE_STATUS_LED) {
      buffer = enableStatusLED(packet);
    }
    else if (functionId == FUNCTION_DISABLE_STATUS_LED) {
      buffer = disableStatusLED(packet);
    }
    else if (functionId == FUNCTION_IS_STATUS_LED_ENABLED) {
      buffer = isStatusLEDEnabled(packet);
    }
    else if (functionId == FUNCTION_GET_CHIP_TEMPERATURE) {
      buffer = getChipTemperature(packet);
    }
    else if (functionId == FUNCTION_RESET) {
      buffer = reset(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startCurrentPositionGenerator() {
    if (currentPosition_step == 0) {
      return;
    }
    vertx.setPeriodic(currentPosition_generator_period, id -> {
      if (currentPosition_direction == Step.UP) {
        if (currentPosition >= currentPosition_max) {
          currentPosition_direction = Step.DOWN;
          this.currentPosition = (short) (currentPosition - currentPosition_step);
        } else {
          this.currentPosition = (short) (currentPosition + currentPosition_step);
        }
      } else {
        if (currentPosition <= currentPosition_min) {
          currentPosition_direction = Step.UP;
          this.currentPosition = (short) (currentPosition + currentPosition_step);
        } else {
          this.currentPosition = (short) (currentPosition - currentPosition_step);
        }
      }
    });
  }
        
  private void startServoCurrentGenerator() {
    if (servoCurrent_step == 0) {
      return;
    }
    vertx.setPeriodic(servoCurrent_generator_period, id -> {
      if (servoCurrent_direction == Step.UP) {
        if (servoCurrent >= servoCurrent_max) {
          servoCurrent_direction = Step.DOWN;
          this.servoCurrent = (short) (servoCurrent - servoCurrent_step);
        } else {
          this.servoCurrent = (short) (servoCurrent + servoCurrent_step);
        }
      } else {
        if (servoCurrent <= servoCurrent_min) {
          servoCurrent_direction = Step.UP;
          this.servoCurrent = (short) (servoCurrent + servoCurrent_step);
        } else {
          this.servoCurrent = (short) (servoCurrent - servoCurrent_step);
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
        
  private void startOverallCurrentGenerator() {
    if (overallCurrent_step == 0) {
      return;
    }
    vertx.setPeriodic(overallCurrent_generator_period, id -> {
      if (overallCurrent_direction == Step.UP) {
        if (overallCurrent >= overallCurrent_max) {
          overallCurrent_direction = Step.DOWN;
          this.overallCurrent = (short) (overallCurrent - overallCurrent_step);
        } else {
          this.overallCurrent = (short) (overallCurrent + overallCurrent_step);
        }
      } else {
        if (overallCurrent <= overallCurrent_min) {
          overallCurrent_direction = Step.UP;
          this.overallCurrent = (short) (overallCurrent + overallCurrent_step);
        } else {
          this.overallCurrent = (short) (overallCurrent - overallCurrent_step);
        }
      }
    });
  }
        
  private void startCurrentVelocityGenerator() {
    if (currentVelocity_step == 0) {
      return;
    }
    vertx.setPeriodic(currentVelocity_generator_period, id -> {
      if (currentVelocity_direction == Step.UP) {
        if (currentVelocity >= currentVelocity_max) {
          currentVelocity_direction = Step.DOWN;
          this.currentVelocity = (short) (currentVelocity - currentVelocity_step);
        } else {
          this.currentVelocity = (short) (currentVelocity + currentVelocity_step);
        }
      } else {
        if (currentVelocity <= currentVelocity_min) {
          currentVelocity_direction = Step.UP;
          this.currentVelocity = (short) (currentVelocity + currentVelocity_step);
        } else {
          this.currentVelocity = (short) (currentVelocity - currentVelocity_step);
        }
      }
    });
  }
        
  private void startExternalInputVoltageGenerator() {
    if (externalInputVoltage_step == 0) {
      return;
    }
    vertx.setPeriodic(externalInputVoltage_generator_period, id -> {
      if (externalInputVoltage_direction == Step.UP) {
        if (externalInputVoltage >= externalInputVoltage_max) {
          externalInputVoltage_direction = Step.DOWN;
          this.externalInputVoltage = (short) (externalInputVoltage - externalInputVoltage_step);
        } else {
          this.externalInputVoltage = (short) (externalInputVoltage + externalInputVoltage_step);
        }
      } else {
        if (externalInputVoltage <= externalInputVoltage_min) {
          externalInputVoltage_direction = Step.UP;
          this.externalInputVoltage = (short) (externalInputVoltage + externalInputVoltage_step);
        } else {
          this.externalInputVoltage = (short) (externalInputVoltage - externalInputVoltage_step);
        }
      }
    });
  }
        
  private void startStackInputVoltageGenerator() {
    if (stackInputVoltage_step == 0) {
      return;
    }
    vertx.setPeriodic(stackInputVoltage_generator_period, id -> {
      if (stackInputVoltage_direction == Step.UP) {
        if (stackInputVoltage >= stackInputVoltage_max) {
          stackInputVoltage_direction = Step.DOWN;
          this.stackInputVoltage = (short) (stackInputVoltage - stackInputVoltage_step);
        } else {
          this.stackInputVoltage = (short) (stackInputVoltage + stackInputVoltage_step);
        }
      } else {
        if (stackInputVoltage <= stackInputVoltage_min) {
          stackInputVoltage_direction = Step.UP;
          this.stackInputVoltage = (short) (stackInputVoltage + stackInputVoltage_step);
        } else {
          this.stackInputVoltage = (short) (stackInputVoltage - stackInputVoltage_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor underVoltage
//fixme start_generator callback without sensor velocityReached
//fixme start_generator callback without sensor positionReached
//fixme stop_generator callback without sensor underVoltage
//fixme stop_generator callback without sensor velocityReached
//fixme stop_generator callback without sensor positionReached
//fixme getter callback without sensor underVoltage
//fixme getter callback without sensor velocityReached
//fixme getter callback without sensor positionReached

  private Buffer getCurrentPositionBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(currentPosition));

    return buffer;
  }
        
  private Buffer getCurrentPositionBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCurrentPositionBuffer(FUNCTION_GET_CURRENT_POSITION, options);
  }

  private Buffer getCurrentPosition(Packet packet) {
    logger.debug("function getCurrentPosition");
    if (packet.getResponseExpected()) {
      return getCurrentPositionBuffer(packet);
    }
    return null;
  }

  private Buffer getCurrentVelocityBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(currentVelocity));

    return buffer;
  }
        
  private Buffer getCurrentVelocityBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCurrentVelocityBuffer(FUNCTION_GET_CURRENT_VELOCITY, options);
  }

  private Buffer getCurrentVelocity(Packet packet) {
    logger.debug("function getCurrentVelocity");
    if (packet.getResponseExpected()) {
      return getCurrentVelocityBuffer(packet);
    }
    return null;
  }

  private Buffer getServoCurrentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(servoCurrent));

    return buffer;
  }
        
  private Buffer getServoCurrentBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getServoCurrentBuffer(FUNCTION_GET_SERVO_CURRENT, options);
  }

  private Buffer getServoCurrent(Packet packet) {
    logger.debug("function getServoCurrent");
    if (packet.getResponseExpected()) {
      return getServoCurrentBuffer(packet);
    }
    return null;
  }

  private Buffer getOverallCurrentBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(overallCurrent));

    return buffer;
  }
        
  private Buffer getOverallCurrentBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getOverallCurrentBuffer(FUNCTION_GET_OVERALL_CURRENT, options);
  }

  private Buffer getOverallCurrent(Packet packet) {
    logger.debug("function getOverallCurrent");
    if (packet.getResponseExpected()) {
      return getOverallCurrentBuffer(packet);
    }
    return null;
  }

  private Buffer getStackInputVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(stackInputVoltage));

    return buffer;
  }
        
  private Buffer getStackInputVoltageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getStackInputVoltageBuffer(FUNCTION_GET_STACK_INPUT_VOLTAGE, options);
  }

  private Buffer getStackInputVoltage(Packet packet) {
    logger.debug("function getStackInputVoltage");
    if (packet.getResponseExpected()) {
      return getStackInputVoltageBuffer(packet);
    }
    return null;
  }

  private Buffer getExternalInputVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(externalInputVoltage));

    return buffer;
  }
        
  private Buffer getExternalInputVoltageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getExternalInputVoltageBuffer(FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE, options);
  }

  private Buffer getExternalInputVoltage(Packet packet) {
    logger.debug("function getExternalInputVoltage");
    if (packet.getResponseExpected()) {
      return getExternalInputVoltageBuffer(packet);
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
  private Buffer getPulseWidth(Packet packet) {
    logger.debug("function getPulseWidth");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_PULSE_WIDTH;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.pulseWidth);
      return buffer;
    }

    return null;
  }

  private Buffer getPulseWidthDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getDegree(Packet packet) {
    logger.debug("function getDegree");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_DEGREE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.degree);
      return buffer;
    }

    return null;
  }

  private Buffer getDegreeDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteRandomValue(1));
      buffer.appendBytes(Utils.get2ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getVelocity(Packet packet) {
    logger.debug("function getVelocity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_VELOCITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.velocity);
      return buffer;
    }

    return null;
  }

  private Buffer getVelocityDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer isVelocityReachedCallbackEnabled(Packet packet) {
    logger.debug("function isVelocityReachedCallbackEnabled");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_VELOCITY_REACHED_CALLBACK_ENABLED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.VelocityReachedCallback);
      return buffer;
    }

    return null;
  }

  private Buffer isVelocityReachedCallbackEnabledDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer isEnabled(Packet packet) {
    logger.debug("function isEnabled");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_ENABLED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.enabled);
      return buffer;
    }

    return null;
  }

  private Buffer isEnabledDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getBoolRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getMinimumVoltage(Packet packet) {
    logger.debug("function getMinimumVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_MINIMUM_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.minimumVoltage);
      return buffer;
    }

    return null;
  }

  private Buffer getMinimumVoltageDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getOutputVoltage(Packet packet) {
    logger.debug("function getOutputVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_OUTPUT_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.outputVoltage);
      return buffer;
    }

    return null;
  }

  private Buffer getOutputVoltageDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getPeriod(Packet packet) {
    logger.debug("function getPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.period);
      return buffer;
    }

    return null;
  }

  private Buffer getPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer isPositionReachedCallbackEnabled(Packet packet) {
    logger.debug("function isPositionReachedCallbackEnabled");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_POSITION_REACHED_CALLBACK_ENABLED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.PositionReachedCallback);
      return buffer;
    }

    return null;
  }

  private Buffer isPositionReachedCallbackEnabledDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer isStatusLEDEnabled(Packet packet) {
    logger.debug("function isStatusLEDEnabled");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_STATUS_LED_ENABLED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.StatusLED);
      return buffer;
    }

    return null;
  }

  private Buffer isStatusLEDEnabledDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getBoolRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getPosition(Packet packet) {
    logger.debug("function getPosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.position);
      return buffer;
    }

    return null;
  }

  private Buffer getPositionDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getAcceleration(Packet packet) {
    logger.debug("function getAcceleration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_ACCELERATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.acceleration);
      return buffer;
    }

    return null;
  }

  private Buffer getAccelerationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer setPeriod(Packet packet) {
    logger.debug("function setPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.period = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAcceleration(Packet packet) {
    logger.debug("function setAcceleration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ACCELERATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.acceleration = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer disable(Packet packet) {
    logger.debug("function disable");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_DISABLE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.enabled = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer disablePositionReachedCallback(Packet packet) {
    logger.debug("function disablePositionReachedCallback");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_DISABLE_POSITION_REACHED_CALLBACK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.PositionReachedCallback = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setDegree(Packet packet) {
    logger.debug("function setDegree");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DEGREE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.degree = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer enableVelocityReachedCallback(Packet packet) {
    logger.debug("function enableVelocityReachedCallback");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_ENABLE_VELOCITY_REACHED_CALLBACK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.VelocityReachedCallback = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer enablePositionReachedCallback(Packet packet) {
    logger.debug("function enablePositionReachedCallback");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_ENABLE_POSITION_REACHED_CALLBACK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.PositionReachedCallback = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setOutputVoltage(Packet packet) {
    logger.debug("function setOutputVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_OUTPUT_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.outputVoltage = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer enable(Packet packet) {
    logger.debug("function enable");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_ENABLE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.enabled = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setPulseWidth(Packet packet) {
    logger.debug("function setPulseWidth");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_PULSE_WIDTH;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.pulseWidth = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer enableStatusLED(Packet packet) {
    logger.debug("function enableStatusLED");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_ENABLE_STATUS_LED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.StatusLED = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setVelocity(Packet packet) {
    logger.debug("function setVelocity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_VELOCITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.velocity = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setMinimumVoltage(Packet packet) {
    logger.debug("function setMinimumVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MINIMUM_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.minimumVoltage = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setPosition(Packet packet) {
    logger.debug("function setPosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.position = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer disableVelocityReachedCallback(Packet packet) {
    logger.debug("function disableVelocityReachedCallback");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_DISABLE_VELOCITY_REACHED_CALLBACK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.VelocityReachedCallback = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer disableStatusLED(Packet packet) {
    logger.debug("function disableStatusLED");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_DISABLE_STATUS_LED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.StatusLED = packet.getPayload();
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
  private Buffer reset(Packet packet) {
    //TODO dummy method
    return null;
  }
}
