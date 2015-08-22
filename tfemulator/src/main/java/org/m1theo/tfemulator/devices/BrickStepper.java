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
  private Buffer enabled = isEnabledDefault();
  private Buffer timeBase = getTimeBaseDefault();
  private Buffer speedRamping = getSpeedRampingDefault();
  private Buffer steps = getStepsDefault();
  private Buffer allDataPeriod = getAllDataPeriodDefault();
  private Buffer stepMode = getStepModeDefault();
  private Buffer StatusLED = isStatusLEDEnabledDefault();
  private Buffer targetPosition = getTargetPositionDefault();

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


  /**
   * 
   */
  private Buffer getRemainingSteps(Packet packet) {
    logger.debug("function getRemainingSteps");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_REMAINING_STEPS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get4ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getCurrentConsumption(Packet packet) {
    logger.debug("function getCurrentConsumption");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CURRENT_CONSUMPTION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAllData(Packet packet) {
    logger.debug("function getAllData");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 16;
      byte functionId = FUNCTION_GET_ALL_DATA;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteRandomValue(1));        
      buffer.appendBytes(Utils.get4ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getChipTemperature(Packet packet) {
    logger.debug("function getChipTemperature");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CHIP_TEMPERATURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getCurrentVelocity(Packet packet) {
    logger.debug("function getCurrentVelocity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CURRENT_VELOCITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getExternalInputVoltage(Packet packet) {
    logger.debug("function getExternalInputVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getStackInputVoltage(Packet packet) {
    logger.debug("function getStackInputVoltage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_STACK_INPUT_VOLTAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
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
