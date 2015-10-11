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
 * Drives one bipolar stepper motor with up to 38V and 2.5A per phase
 */
public class BrickStepper extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 15;
public final static String DEVICE_DISPLAY_NAME = "Stepper Brick";

  public final static byte FUNCTION_SET_MAX_VELOCITY = (byte)1;
  public final static byte FUNCTION_GET_MAX_VELOCITY = (byte)2;
  public final static byte FUNCTION_GET_CURRENT_VELOCITY = (byte)3;
  public final static byte FUNCTION_SET_SPEED_RAMPING = (byte)4;
  public final static byte FUNCTION_GET_SPEED_RAMPING = (byte)5;
  public final static byte FUNCTION_FULL_BRAKE = (byte)6;
  public final static byte FUNCTION_SET_CURRENT_POSITION = (byte)7;
  public final static byte FUNCTION_GET_CURRENT_POSITION = (byte)8;
  public final static byte FUNCTION_SET_TARGET_POSITION = (byte)9;
  public final static byte FUNCTION_GET_TARGET_POSITION = (byte)10;
  public final static byte FUNCTION_SET_STEPS = (byte)11;
  public final static byte FUNCTION_GET_STEPS = (byte)12;
  public final static byte FUNCTION_GET_REMAINING_STEPS = (byte)13;
  public final static byte FUNCTION_SET_STEP_MODE = (byte)14;
  public final static byte FUNCTION_GET_STEP_MODE = (byte)15;
  public final static byte FUNCTION_DRIVE_FORWARD = (byte)16;
  public final static byte FUNCTION_DRIVE_BACKWARD = (byte)17;
  public final static byte FUNCTION_STOP = (byte)18;
  public final static byte FUNCTION_GET_STACK_INPUT_VOLTAGE = (byte)19;
  public final static byte FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE = (byte)20;
  public final static byte FUNCTION_GET_CURRENT_CONSUMPTION = (byte)21;
  public final static byte FUNCTION_SET_MOTOR_CURRENT = (byte)22;
  public final static byte FUNCTION_GET_MOTOR_CURRENT = (byte)23;
  public final static byte FUNCTION_ENABLE = (byte)24;
  public final static byte FUNCTION_DISABLE = (byte)25;
  public final static byte FUNCTION_IS_ENABLED = (byte)26;
  public final static byte FUNCTION_SET_DECAY = (byte)27;
  public final static byte FUNCTION_GET_DECAY = (byte)28;
  public final static byte FUNCTION_SET_MINIMUM_VOLTAGE = (byte)29;
  public final static byte FUNCTION_GET_MINIMUM_VOLTAGE = (byte)30;
  public final static byte CALLBACK_UNDER_VOLTAGE = (byte)31;
  public final static byte CALLBACK_POSITION_REACHED = (byte)32;
  public final static byte FUNCTION_SET_SYNC_RECT = (byte)33;
  public final static byte FUNCTION_IS_SYNC_RECT = (byte)34;
  public final static byte FUNCTION_SET_TIME_BASE = (byte)35;
  public final static byte FUNCTION_GET_TIME_BASE = (byte)36;
  public final static byte FUNCTION_GET_ALL_DATA = (byte)37;
  public final static byte FUNCTION_SET_ALL_DATA_PERIOD = (byte)38;
  public final static byte FUNCTION_GET_ALL_DATA_PERIOD = (byte)39;
  public final static byte CALLBACK_ALL_DATA = (byte)40;
  public final static byte CALLBACK_NEW_STATE = (byte)41;
  public final static byte FUNCTION_ENABLE_STATUS_LED = (byte)238;
  public final static byte FUNCTION_DISABLE_STATUS_LED = (byte)239;
  public final static byte FUNCTION_IS_STATUS_LED_ENABLED = (byte)240;
  public final static byte FUNCTION_GET_PROTOCOL1_BRICKLET_NAME = (byte)241;
  public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)242;
  public final static byte FUNCTION_RESET = (byte)243;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short STEP_MODE_FULL_STEP = (short)1;
  public final static short STEP_MODE_HALF_STEP = (short)2;
  public final static short STEP_MODE_QUARTER_STEP = (short)4;
  public final static short STEP_MODE_EIGHTH_STEP = (short)8;
  public final static short STATE_STOP = (short)1;
  public final static short STATE_ACCELERATION = (short)2;
  public final static short STATE_RUN = (short)3;
  public final static short STATE_DEACCELERATION = (short)4;
  public final static short STATE_DIRECTION_CHANGE_TO_FORWARD = (short)5;
  public final static short STATE_DIRECTION_CHANGE_TO_BACKWARD = (short)6;
  String uidString;

  private Buffer minimumVoltage = getMinimumVoltageDefault();
        
  private Buffer maxVelocity = getMaxVelocityDefault();
        
  private Buffer decay = getDecayDefault();
        
  private Buffer currentPosition = getCurrentPositionDefault();
        
  private Buffer motorCurrent = getMotorCurrentDefault();
        
  private Buffer enabled = getEnabledDefault();
        
  private Buffer timeBase = getTimeBaseDefault();
        
  private Buffer speedRamping = getSpeedRampingDefault();
        
  private Buffer steps = getStepsDefault();
        
  private Buffer allDataPeriod = getAllDataPeriodDefault();
        
  private Buffer stepMode = getStepModeDefault();
        
  private Buffer StatusLED = getStatusLEDDefault();
        
  private Buffer targetPosition = getTargetPositionDefault();
        
  private int remainingSteps = 100;
  private int remainingSteps_max = 1000;
  private int remainingSteps_min = 0;
  private int remainingSteps_step = 1;
  private long remainingSteps_generator_period = 100;
  private Step remainingSteps_direction = Step.UP;
  private long remainingStepsCallbackPeriod;
  private long remainingSteps_callback_id;
  private int remainingSteps_last_value_called_back;

  private short currentConsumption = 100;
  private short currentConsumption_max = 1000;
  private short currentConsumption_min = 0;
  private short currentConsumption_step = 1;
  private long currentConsumption_generator_period = 100;
  private Step currentConsumption_direction = Step.UP;
  private long currentConsumptionCallbackPeriod;
  private long currentConsumption_callback_id;
  private short currentConsumption_last_value_called_back;

  private short allData = 100;
  private short allData_max = 1000;
  private short allData_min = 0;
  private short allData_step = 1;
  private long allData_generator_period = 100;
  private Step allData_direction = Step.UP;
  private long allDataCallbackPeriod;
  private long allData_callback_id;
  private short allData_last_value_called_back;

  private short chipTemperature = 100;
  private short chipTemperature_max = 1000;
  private short chipTemperature_min = 0;
  private short chipTemperature_step = 1;
  private long chipTemperature_generator_period = 100;
  private Step chipTemperature_direction = Step.UP;
  private long chipTemperatureCallbackPeriod;
  private long chipTemperature_callback_id;
  private short chipTemperature_last_value_called_back;

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

    logger.info("Verticle started: " + BrickStepper.class);
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

    startRemainingStepsGenerator();
    startCurrentConsumptionGenerator();
    startAllDataGenerator();
    startChipTemperatureGenerator();
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
    else if (functionId == FUNCTION_SET_MAX_VELOCITY) {
      buffer = setMaxVelocity(packet);
    }
    else if (functionId == FUNCTION_GET_MAX_VELOCITY) {
      buffer = getMaxVelocity(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_VELOCITY) {
      buffer = getCurrentVelocity(packet);
    }
    else if (functionId == FUNCTION_SET_SPEED_RAMPING) {
      buffer = setSpeedRamping(packet);
    }
    else if (functionId == FUNCTION_GET_SPEED_RAMPING) {
      buffer = getSpeedRamping(packet);
    }
    else if (functionId == FUNCTION_FULL_BRAKE) {
      buffer = fullBrake(packet);
    }
    else if (functionId == FUNCTION_SET_CURRENT_POSITION) {
      buffer = setCurrentPosition(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_POSITION) {
      buffer = getCurrentPosition(packet);
    }
    else if (functionId == FUNCTION_SET_TARGET_POSITION) {
      buffer = setTargetPosition(packet);
    }
    else if (functionId == FUNCTION_GET_TARGET_POSITION) {
      buffer = getTargetPosition(packet);
    }
    else if (functionId == FUNCTION_SET_STEPS) {
      buffer = setSteps(packet);
    }
    else if (functionId == FUNCTION_GET_STEPS) {
      buffer = getSteps(packet);
    }
    else if (functionId == FUNCTION_GET_REMAINING_STEPS) {
      buffer = getRemainingSteps(packet);
    }
    else if (functionId == FUNCTION_SET_STEP_MODE) {
      buffer = setStepMode(packet);
    }
    else if (functionId == FUNCTION_GET_STEP_MODE) {
      buffer = getStepMode(packet);
    }
    else if (functionId == FUNCTION_DRIVE_FORWARD) {
      buffer = driveForward(packet);
    }
    else if (functionId == FUNCTION_DRIVE_BACKWARD) {
      buffer = driveBackward(packet);
    }
    else if (functionId == FUNCTION_STOP) {
      buffer = stop(packet);
    }
    else if (functionId == FUNCTION_GET_STACK_INPUT_VOLTAGE) {
      buffer = getStackInputVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE) {
      buffer = getExternalInputVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT_CONSUMPTION) {
      buffer = getCurrentConsumption(packet);
    }
    else if (functionId == FUNCTION_SET_MOTOR_CURRENT) {
      buffer = setMotorCurrent(packet);
    }
    else if (functionId == FUNCTION_GET_MOTOR_CURRENT) {
      buffer = getMotorCurrent(packet);
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
    else if (functionId == FUNCTION_SET_DECAY) {
      buffer = setDecay(packet);
    }
    else if (functionId == FUNCTION_GET_DECAY) {
      buffer = getDecay(packet);
    }
    else if (functionId == FUNCTION_SET_MINIMUM_VOLTAGE) {
      buffer = setMinimumVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_MINIMUM_VOLTAGE) {
      buffer = getMinimumVoltage(packet);
    }
    else if (functionId == FUNCTION_SET_SYNC_RECT) {
      buffer = setSyncRect(packet);
    }
    else if (functionId == FUNCTION_IS_SYNC_RECT) {
      buffer = isSyncRect(packet);
    }
    else if (functionId == FUNCTION_SET_TIME_BASE) {
      buffer = setTimeBase(packet);
    }
    else if (functionId == FUNCTION_GET_TIME_BASE) {
      buffer = getTimeBase(packet);
    }
    else if (functionId == FUNCTION_GET_ALL_DATA) {
      buffer = getAllData(packet);
    }
    else if (functionId == FUNCTION_SET_ALL_DATA_PERIOD) {
      buffer = setAllDataPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ALL_DATA_PERIOD) {
      buffer = getAllDataPeriod(packet);
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


  private void startRemainingStepsGenerator() {
    if (remainingSteps_step == 0) {
      return;
    }
    vertx.setPeriodic(remainingSteps_generator_period, id -> {
      if (remainingSteps_direction == Step.UP) {
        if (remainingSteps >= remainingSteps_max) {
          remainingSteps_direction = Step.DOWN;
          this.remainingSteps = (int) (remainingSteps - remainingSteps_step);
        } else {
          this.remainingSteps = (int) (remainingSteps + remainingSteps_step);
        }
      } else {
        if (remainingSteps <= remainingSteps_min) {
          remainingSteps_direction = Step.UP;
          this.remainingSteps = (int) (remainingSteps + remainingSteps_step);
        } else {
          this.remainingSteps = (int) (remainingSteps - remainingSteps_step);
        }
      }
    });
  }
        
  private void startCurrentConsumptionGenerator() {
    if (currentConsumption_step == 0) {
      return;
    }
    vertx.setPeriodic(currentConsumption_generator_period, id -> {
      if (currentConsumption_direction == Step.UP) {
        if (currentConsumption >= currentConsumption_max) {
          currentConsumption_direction = Step.DOWN;
          this.currentConsumption = (short) (currentConsumption - currentConsumption_step);
        } else {
          this.currentConsumption = (short) (currentConsumption + currentConsumption_step);
        }
      } else {
        if (currentConsumption <= currentConsumption_min) {
          currentConsumption_direction = Step.UP;
          this.currentConsumption = (short) (currentConsumption + currentConsumption_step);
        } else {
          this.currentConsumption = (short) (currentConsumption - currentConsumption_step);
        }
      }
    });
  }
        
  private void startAllDataGenerator() {
    if (allData_step == 0) {
      return;
    }
    vertx.setPeriodic(allData_generator_period, id -> {
      if (allData_direction == Step.UP) {
        if (allData >= allData_max) {
          allData_direction = Step.DOWN;
          this.allData = (short) (allData - allData_step);
        } else {
          this.allData = (short) (allData + allData_step);
        }
      } else {
        if (allData <= allData_min) {
          allData_direction = Step.UP;
          this.allData = (short) (allData + allData_step);
        } else {
          this.allData = (short) (allData - allData_step);
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
//fixme start_generator callback without sensor newState

    private void stopAllDataCallback() {
        vertx.cancelTimer(allData_callback_id);
  }
        //fixme start_generator callback without sensor allData
//fixme start_generator callback without sensor positionReached
//fixme stop_generator callback without sensor underVoltage
//fixme stop_generator callback without sensor newState

  private void startAllDataCallback() {
    logger.trace("allDataCallbackPeriod is {}", allDataCallbackPeriod);
    allData_callback_id = vertx.setPeriodic(allDataCallbackPeriod, id -> {
      if (allData != allData_last_value_called_back) {
        allData_last_value_called_back = allData;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("allData sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAllData4Callback());
          }
        } else {
          logger.info("no handlerids found in allData callback");
        }
      } else {
        logger.debug("allData value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor allData
//fixme stop_generator callback without sensor positionReached
//fixme getter callback without sensor underVoltage
//fixme getter callback without sensor newState

  private Buffer getAllData4Callback() {
      byte options = (byte) 0;
      return getAllDataBuffer(CALLBACK_ALL_DATA, options);
  }
        //fixme getter callback without sensor allData
//fixme getter callback without sensor positionReached

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

  private Buffer getRemainingStepsBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(remainingSteps));

    return buffer;
  }
        
  private Buffer getRemainingStepsBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getRemainingStepsBuffer(FUNCTION_GET_REMAINING_STEPS, options);
  }

  private Buffer getRemainingSteps(Packet packet) {
    logger.debug("function getRemainingSteps");
    if (packet.getResponseExpected()) {
      return getRemainingStepsBuffer(packet);
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

  private Buffer getCurrentConsumptionBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(currentConsumption));

    return buffer;
  }
        
  private Buffer getCurrentConsumptionBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCurrentConsumptionBuffer(FUNCTION_GET_CURRENT_CONSUMPTION, options);
  }

  private Buffer getCurrentConsumption(Packet packet) {
    logger.debug("function getCurrentConsumption");
    if (packet.getResponseExpected()) {
      return getCurrentConsumptionBuffer(packet);
    }
    return null;
  }

  private Buffer getAllDataBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 16;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt32(allData));
    buffer.appendBytes(Utils.getUInt32(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));

    return buffer;
  }
        
  private Buffer getAllDataBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAllDataBuffer(FUNCTION_GET_ALL_DATA, options);
  }

  private Buffer getAllData(Packet packet) {
    logger.debug("function getAllData");
    if (packet.getResponseExpected()) {
      return getAllDataBuffer(packet);
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
  private Buffer getMaxVelocity(Packet packet) {
    logger.debug("function getMaxVelocity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_MAX_VELOCITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.maxVelocity);
      return buffer;
    }

    return null;
  }

  private Buffer getMaxVelocityDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getDecay(Packet packet) {
    logger.debug("function getDecay");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_DECAY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.decay);
      return buffer;
    }

    return null;
  }

  private Buffer getDecayDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getCurrentPosition(Packet packet) {
    logger.debug("function getCurrentPosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_CURRENT_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.currentPosition);
      return buffer;
    }

    return null;
  }

  private Buffer getCurrentPositionDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getMotorCurrent(Packet packet) {
    logger.debug("function getMotorCurrent");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_MOTOR_CURRENT;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.motorCurrent);
      return buffer;
    }

    return null;
  }

  private Buffer getMotorCurrentDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

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
  private Buffer getTimeBase(Packet packet) {
    logger.debug("function getTimeBase");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_TIME_BASE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.timeBase);
      return buffer;
    }

    return null;
  }

  private Buffer getTimeBaseDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getSpeedRamping(Packet packet) {
    logger.debug("function getSpeedRamping");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_SPEED_RAMPING;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.speedRamping);
      return buffer;
    }

    return null;
  }

  private Buffer getSpeedRampingDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getSteps(Packet packet) {
    logger.debug("function getSteps");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_STEPS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.steps);
      return buffer;
    }

    return null;
  }

  private Buffer getStepsDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getAllDataPeriod(Packet packet) {
    logger.debug("function getAllDataPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ALL_DATA_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.allDataPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getAllDataPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getStepMode(Packet packet) {
    logger.debug("function getStepMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_STEP_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.stepMode);
      return buffer;
    }

    return null;
  }

  private Buffer getStepModeDefault() {
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
  private Buffer getTargetPosition(Packet packet) {
    logger.debug("function getTargetPosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_TARGET_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.targetPosition);
      return buffer;
    }

    return null;
  }

  private Buffer getTargetPositionDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer setSpeedRamping(Packet packet) {
    logger.debug("function setSpeedRamping");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_SPEED_RAMPING;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.speedRamping = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setMaxVelocity(Packet packet) {
    logger.debug("function setMaxVelocity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MAX_VELOCITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.maxVelocity = packet.getPayload();
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
  private Buffer setAllDataPeriod(Packet packet) {
    logger.debug("function setAllDataPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ALL_DATA_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.allDataPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setTimeBase(Packet packet) {
    logger.debug("function setTimeBase");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_TIME_BASE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.timeBase = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setStepMode(Packet packet) {
    logger.debug("function setStepMode");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STEP_MODE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.stepMode = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setDecay(Packet packet) {
    logger.debug("function setDecay");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DECAY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.decay = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setTargetPosition(Packet packet) {
    logger.debug("function setTargetPosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_TARGET_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.targetPosition = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setCurrentPosition(Packet packet) {
    logger.debug("function setCurrentPosition");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CURRENT_POSITION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.currentPosition = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setMotorCurrent(Packet packet) {
    logger.debug("function setMotorCurrent");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MOTOR_CURRENT;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.motorCurrent = packet.getPayload();
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
  private Buffer setSteps(Packet packet) {
    logger.debug("function setSteps");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STEPS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.steps = packet.getPayload();
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

  /**
   * 
   */
  private Buffer fullBrake(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer driveForward(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer stop(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer driveBackward(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer setSyncRect(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isSyncRect(Packet packet) {
    //TODO dummy method
    return null;
  }
}
