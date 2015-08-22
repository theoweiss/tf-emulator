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
 * Full fledged AHRS with 9 degrees of freedom
 */
public class BrickIMUV2 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 18;
public final static String DEVICE_DISPLAY_NAME = "IMU Brick 2.0";

  public final static byte FUNCTION_GET_ACCELERATION = (byte)1;
  public final static byte FUNCTION_GET_MAGNETIC_FIELD = (byte)2;
  public final static byte FUNCTION_GET_ANGULAR_VELOCITY = (byte)3;
  public final static byte FUNCTION_GET_TEMPERATURE = (byte)4;
  public final static byte FUNCTION_GET_ORIENTATION = (byte)5;
  public final static byte FUNCTION_GET_LINEAR_ACCELERATION = (byte)6;
  public final static byte FUNCTION_GET_GRAVITY_VECTOR = (byte)7;
  public final static byte FUNCTION_GET_QUATERNION = (byte)8;
  public final static byte FUNCTION_GET_ALL_DATA = (byte)9;
  public final static byte FUNCTION_LEDS_ON = (byte)10;
  public final static byte FUNCTION_LEDS_OFF = (byte)11;
  public final static byte FUNCTION_ARE_LEDS_ON = (byte)12;
  public final static byte FUNCTION_SAVE_CALIBRATION = (byte)13;
  public final static byte FUNCTION_SET_ACCELERATION_PERIOD = (byte)14;
  public final static byte FUNCTION_GET_ACCELERATION_PERIOD = (byte)15;
  public final static byte FUNCTION_SET_MAGNETIC_FIELD_PERIOD = (byte)16;
  public final static byte FUNCTION_GET_MAGNETIC_FIELD_PERIOD = (byte)17;
  public final static byte FUNCTION_SET_ANGULAR_VELOCITY_PERIOD = (byte)18;
  public final static byte FUNCTION_GET_ANGULAR_VELOCITY_PERIOD = (byte)19;
  public final static byte FUNCTION_SET_TEMPERATURE_PERIOD = (byte)20;
  public final static byte FUNCTION_GET_TEMPERATURE_PERIOD = (byte)21;
  public final static byte FUNCTION_SET_ORIENTATION_PERIOD = (byte)22;
  public final static byte FUNCTION_GET_ORIENTATION_PERIOD = (byte)23;
  public final static byte FUNCTION_SET_LINEAR_ACCELERATION_PERIOD = (byte)24;
  public final static byte FUNCTION_GET_LINEAR_ACCELERATION_PERIOD = (byte)25;
  public final static byte FUNCTION_SET_GRAVITY_VECTOR_PERIOD = (byte)26;
  public final static byte FUNCTION_GET_GRAVITY_VECTOR_PERIOD = (byte)27;
  public final static byte FUNCTION_SET_QUATERNION_PERIOD = (byte)28;
  public final static byte FUNCTION_GET_QUATERNION_PERIOD = (byte)29;
  public final static byte FUNCTION_SET_ALL_DATA_PERIOD = (byte)30;
  public final static byte FUNCTION_GET_ALL_DATA_PERIOD = (byte)31;
  public final static byte CALLBACK_ACCELERATION = (byte)32;
  public final static byte CALLBACK_MAGNETIC_FIELD = (byte)33;
  public final static byte CALLBACK_ANGULAR_VELOCITY = (byte)34;
  public final static byte CALLBACK_TEMPERATURE = (byte)35;
  public final static byte CALLBACK_LINEAR_ACCELERATION = (byte)36;
  public final static byte CALLBACK_GRAVITY_VECTOR = (byte)37;
  public final static byte CALLBACK_ORIENTATION = (byte)38;
  public final static byte CALLBACK_QUATERNION = (byte)39;
  public final static byte CALLBACK_ALL_DATA = (byte)40;
  public final static byte FUNCTION_ENABLE_STATUS_LED = (byte)238;
  public final static byte FUNCTION_DISABLE_STATUS_LED = (byte)239;
  public final static byte FUNCTION_IS_STATUS_LED_ENABLED = (byte)240;
  public final static byte FUNCTION_GET_PROTOCOL1_BRICKLET_NAME = (byte)241;
  public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)242;
  public final static byte FUNCTION_RESET = (byte)243;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  String uidString;
  private Buffer temperaturePeriod = getTemperaturePeriodDefault();
  private Buffer leds = areLedsOnDefault();
  private Buffer orientationPeriod = getOrientationPeriodDefault();
  private Buffer StatusLED = isStatusLEDEnabledDefault();
  private Buffer magneticFieldPeriod = getMagneticFieldPeriodDefault();
  private Buffer accelerationPeriod = getAccelerationPeriodDefault();
  private Buffer linearAccelerationPeriod = getLinearAccelerationPeriodDefault();
  private Buffer angularVelocityPeriod = getAngularVelocityPeriodDefault();
  private Buffer allDataPeriod = getAllDataPeriodDefault();
  private Buffer quaternionPeriod = getQuaternionPeriodDefault();
  private Buffer gravityVectorPeriod = getGravityVectorPeriodDefault();

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickIMUV2.class);
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
    else if (functionId == FUNCTION_GET_ACCELERATION) {
      buffer = getAcceleration(packet);
    }
    else if (functionId == FUNCTION_GET_MAGNETIC_FIELD) {
      buffer = getMagneticField(packet);
    }
    else if (functionId == FUNCTION_GET_ANGULAR_VELOCITY) {
      buffer = getAngularVelocity(packet);
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE) {
      buffer = getTemperature(packet);
    }
    else if (functionId == FUNCTION_GET_ORIENTATION) {
      buffer = getOrientation(packet);
    }
    else if (functionId == FUNCTION_GET_LINEAR_ACCELERATION) {
      buffer = getLinearAcceleration(packet);
    }
    else if (functionId == FUNCTION_GET_GRAVITY_VECTOR) {
      buffer = getGravityVector(packet);
    }
    else if (functionId == FUNCTION_GET_QUATERNION) {
      buffer = getQuaternion(packet);
    }
    else if (functionId == FUNCTION_GET_ALL_DATA) {
      buffer = getAllData(packet);
    }
    else if (functionId == FUNCTION_LEDS_ON) {
      buffer = ledsOn(packet);
    }
    else if (functionId == FUNCTION_LEDS_OFF) {
      buffer = ledsOff(packet);
    }
    else if (functionId == FUNCTION_ARE_LEDS_ON) {
      buffer = areLedsOn(packet);
    }
    else if (functionId == FUNCTION_SAVE_CALIBRATION) {
      buffer = saveCalibration(packet);
    }
    else if (functionId == FUNCTION_SET_ACCELERATION_PERIOD) {
      buffer = setAccelerationPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ACCELERATION_PERIOD) {
      buffer = getAccelerationPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_MAGNETIC_FIELD_PERIOD) {
      buffer = setMagneticFieldPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_MAGNETIC_FIELD_PERIOD) {
      buffer = getMagneticFieldPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ANGULAR_VELOCITY_PERIOD) {
      buffer = setAngularVelocityPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ANGULAR_VELOCITY_PERIOD) {
      buffer = getAngularVelocityPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_TEMPERATURE_PERIOD) {
      buffer = setTemperaturePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_TEMPERATURE_PERIOD) {
      buffer = getTemperaturePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ORIENTATION_PERIOD) {
      buffer = setOrientationPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ORIENTATION_PERIOD) {
      buffer = getOrientationPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_LINEAR_ACCELERATION_PERIOD) {
      buffer = setLinearAccelerationPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_LINEAR_ACCELERATION_PERIOD) {
      buffer = getLinearAccelerationPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_GRAVITY_VECTOR_PERIOD) {
      buffer = setGravityVectorPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_GRAVITY_VECTOR_PERIOD) {
      buffer = getGravityVectorPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_QUATERNION_PERIOD) {
      buffer = setQuaternionPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_QUATERNION_PERIOD) {
      buffer = getQuaternionPeriod(packet);
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
  private Buffer getLinearAcceleration(Packet packet) {
    logger.debug("function getLinearAcceleration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 6;
      byte functionId = FUNCTION_GET_LINEAR_ACCELERATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAngularVelocity(Packet packet) {
    logger.debug("function getAngularVelocity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 6;
      byte functionId = FUNCTION_GET_ANGULAR_VELOCITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getOrientation(Packet packet) {
    logger.debug("function getOrientation");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 6;
      byte functionId = FUNCTION_GET_ORIENTATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getTemperature(Packet packet) {
    logger.debug("function getTemperature");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_TEMPERATURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteRandomValue(1));        

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
      byte length = (byte) 8 + 46;
      byte functionId = FUNCTION_GET_ALL_DATA;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(3));        
      buffer.appendBytes(Utils.get2ByteRandomValue(3));        
      buffer.appendBytes(Utils.get2ByteRandomValue(3));        
      buffer.appendBytes(Utils.get2ByteRandomValue(3));        
      buffer.appendBytes(Utils.get2ByteRandomValue(4));        
      buffer.appendBytes(Utils.get2ByteRandomValue(3));        
      buffer.appendBytes(Utils.get2ByteRandomValue(3));        
      buffer.appendBytes(Utils.get1ByteRandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getQuaternion(Packet packet) {
    logger.debug("function getQuaternion");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 8;
      byte functionId = FUNCTION_GET_QUATERNION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

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
  private Buffer getMagneticField(Packet packet) {
    logger.debug("function getMagneticField");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 6;
      byte functionId = FUNCTION_GET_MAGNETIC_FIELD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getGravityVector(Packet packet) {
    logger.debug("function getGravityVector");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 6;
      byte functionId = FUNCTION_GET_GRAVITY_VECTOR;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAcceleration(Packet packet) {
    logger.debug("function getAcceleration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 6;
      byte functionId = FUNCTION_GET_ACCELERATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getTemperaturePeriod(Packet packet) {
    logger.debug("function getTemperaturePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_TEMPERATURE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.temperaturePeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getTemperaturePeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer areLedsOn(Packet packet) {
    logger.debug("function areLedsOn");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_ARE_LEDS_ON;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.leds);
      return buffer;
    }

    return null;
  }

  private Buffer areLedsOnDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getBoolRandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getOrientationPeriod(Packet packet) {
    logger.debug("function getOrientationPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ORIENTATION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.orientationPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getOrientationPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
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
  private Buffer getMagneticFieldPeriod(Packet packet) {
    logger.debug("function getMagneticFieldPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_MAGNETIC_FIELD_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.magneticFieldPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getMagneticFieldPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getAccelerationPeriod(Packet packet) {
    logger.debug("function getAccelerationPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ACCELERATION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.accelerationPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getAccelerationPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getLinearAccelerationPeriod(Packet packet) {
    logger.debug("function getLinearAccelerationPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_LINEAR_ACCELERATION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.linearAccelerationPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getLinearAccelerationPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getAngularVelocityPeriod(Packet packet) {
    logger.debug("function getAngularVelocityPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ANGULAR_VELOCITY_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.angularVelocityPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getAngularVelocityPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
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
  private Buffer getQuaternionPeriod(Packet packet) {
    logger.debug("function getQuaternionPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_QUATERNION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.quaternionPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getQuaternionPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getGravityVectorPeriod(Packet packet) {
    logger.debug("function getGravityVectorPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_GRAVITY_VECTOR_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.gravityVectorPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getGravityVectorPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
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
  private Buffer setMagneticFieldPeriod(Packet packet) {
    logger.debug("function setMagneticFieldPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MAGNETIC_FIELD_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.magneticFieldPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAccelerationPeriod(Packet packet) {
    logger.debug("function setAccelerationPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ACCELERATION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.accelerationPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAngularVelocityPeriod(Packet packet) {
    logger.debug("function setAngularVelocityPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ANGULAR_VELOCITY_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.angularVelocityPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setTemperaturePeriod(Packet packet) {
    logger.debug("function setTemperaturePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_TEMPERATURE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.temperaturePeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setOrientationPeriod(Packet packet) {
    logger.debug("function setOrientationPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ORIENTATION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.orientationPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setQuaternionPeriod(Packet packet) {
    logger.debug("function setQuaternionPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_QUATERNION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.quaternionPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer ledsOff(Packet packet) {
    logger.debug("function ledsOff");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_LEDS_OFF;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.leds = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setLinearAccelerationPeriod(Packet packet) {
    logger.debug("function setLinearAccelerationPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_LINEAR_ACCELERATION_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.linearAccelerationPeriod = packet.getPayload();
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
  private Buffer setGravityVectorPeriod(Packet packet) {
    logger.debug("function setGravityVectorPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_GRAVITY_VECTOR_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.gravityVectorPeriod = packet.getPayload();
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
  private Buffer ledsOn(Packet packet) {
    logger.debug("function ledsOn");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_LEDS_ON;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.leds = packet.getPayload();
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
  private Buffer saveCalibration(Packet packet) {
    //TODO dummy method
    return null;
  }
}
