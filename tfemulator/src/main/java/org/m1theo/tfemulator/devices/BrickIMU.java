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
 * Full fledged AHRS with 9 degrees of freedom
 */
public class BrickIMU extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 16;
public final static String DEVICE_DISPLAY_NAME = "IMU Brick";

  public final static byte FUNCTION_GET_ACCELERATION = (byte)1;
  public final static byte FUNCTION_GET_MAGNETIC_FIELD = (byte)2;
  public final static byte FUNCTION_GET_ANGULAR_VELOCITY = (byte)3;
  public final static byte FUNCTION_GET_ALL_DATA = (byte)4;
  public final static byte FUNCTION_GET_ORIENTATION = (byte)5;
  public final static byte FUNCTION_GET_QUATERNION = (byte)6;
  public final static byte FUNCTION_GET_IMU_TEMPERATURE = (byte)7;
  public final static byte FUNCTION_LEDS_ON = (byte)8;
  public final static byte FUNCTION_LEDS_OFF = (byte)9;
  public final static byte FUNCTION_ARE_LEDS_ON = (byte)10;
  public final static byte FUNCTION_SET_ACCELERATION_RANGE = (byte)11;
  public final static byte FUNCTION_GET_ACCELERATION_RANGE = (byte)12;
  public final static byte FUNCTION_SET_MAGNETOMETER_RANGE = (byte)13;
  public final static byte FUNCTION_GET_MAGNETOMETER_RANGE = (byte)14;
  public final static byte FUNCTION_SET_CONVERGENCE_SPEED = (byte)15;
  public final static byte FUNCTION_GET_CONVERGENCE_SPEED = (byte)16;
  public final static byte FUNCTION_SET_CALIBRATION = (byte)17;
  public final static byte FUNCTION_GET_CALIBRATION = (byte)18;
  public final static byte FUNCTION_SET_ACCELERATION_PERIOD = (byte)19;
  public final static byte FUNCTION_GET_ACCELERATION_PERIOD = (byte)20;
  public final static byte FUNCTION_SET_MAGNETIC_FIELD_PERIOD = (byte)21;
  public final static byte FUNCTION_GET_MAGNETIC_FIELD_PERIOD = (byte)22;
  public final static byte FUNCTION_SET_ANGULAR_VELOCITY_PERIOD = (byte)23;
  public final static byte FUNCTION_GET_ANGULAR_VELOCITY_PERIOD = (byte)24;
  public final static byte FUNCTION_SET_ALL_DATA_PERIOD = (byte)25;
  public final static byte FUNCTION_GET_ALL_DATA_PERIOD = (byte)26;
  public final static byte FUNCTION_SET_ORIENTATION_PERIOD = (byte)27;
  public final static byte FUNCTION_GET_ORIENTATION_PERIOD = (byte)28;
  public final static byte FUNCTION_SET_QUATERNION_PERIOD = (byte)29;
  public final static byte FUNCTION_GET_QUATERNION_PERIOD = (byte)30;
  public final static byte CALLBACK_ACCELERATION = (byte)31;
  public final static byte CALLBACK_MAGNETIC_FIELD = (byte)32;
  public final static byte CALLBACK_ANGULAR_VELOCITY = (byte)33;
  public final static byte CALLBACK_ALL_DATA = (byte)34;
  public final static byte CALLBACK_ORIENTATION = (byte)35;
  public final static byte CALLBACK_QUATERNION = (byte)36;
  public final static byte FUNCTION_ORIENTATION_CALCULATION_ON = (byte)37;
  public final static byte FUNCTION_ORIENTATION_CALCULATION_OFF = (byte)38;
  public final static byte FUNCTION_IS_ORIENTATION_CALCULATION_ON = (byte)39;
  public final static byte FUNCTION_ENABLE_STATUS_LED = (byte)238;
  public final static byte FUNCTION_DISABLE_STATUS_LED = (byte)239;
  public final static byte FUNCTION_IS_STATUS_LED_ENABLED = (byte)240;
  public final static byte FUNCTION_GET_PROTOCOL1_BRICKLET_NAME = (byte)241;
  public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)242;
  public final static byte FUNCTION_RESET = (byte)243;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short CALIBRATION_TYPE_ACCELEROMETER_GAIN = (short)0;
  public final static short CALIBRATION_TYPE_ACCELEROMETER_BIAS = (short)1;
  public final static short CALIBRATION_TYPE_MAGNETOMETER_GAIN = (short)2;
  public final static short CALIBRATION_TYPE_MAGNETOMETER_BIAS = (short)3;
  public final static short CALIBRATION_TYPE_GYROSCOPE_GAIN = (short)4;
  public final static short CALIBRATION_TYPE_GYROSCOPE_BIAS = (short)5;
  String uidString;

  private Buffer orientationPeriod = getOrientationPeriodDefault();
        
  private Buffer leds = getLedsDefault();
        
  private Buffer magneticFieldPeriod = getMagneticFieldPeriodDefault();
        
  private Buffer StatusLED = getStatusLEDDefault();
        
  private Buffer convergenceSpeed = getConvergenceSpeedDefault();
        
  private Buffer accelerationPeriod = getAccelerationPeriodDefault();
        
  private Buffer accelerationRange = getAccelerationRangeDefault();
        
  private Buffer calibration = getCalibrationDefault();
        
  private Buffer magnetometerRange = getMagnetometerRangeDefault();
        
  private Buffer angularVelocityPeriod = getAngularVelocityPeriodDefault();
        
  private Buffer allDataPeriod = getAllDataPeriodDefault();
        
  private Buffer quaternionPeriod = getQuaternionPeriodDefault();
        
  private short iMUTemperature = 100;
  private short iMUTemperature_max = 1000;
  private short iMUTemperature_min = 0;
  private short iMUTemperature_step = 1;
  private long iMUTemperature_generator_period = 100;
  private Step iMUTemperature_direction = Step.UP;
  private long iMUTemperatureCallbackPeriod;
  private long iMUTemperature_callback_id;
  private short iMUTemperature_last_value_called_back;

  private float quaternion = 100;
  private float quaternion_max = 1000;
  private float quaternion_min = 0;
  private float quaternion_step = 1;
  private long quaternion_generator_period = 100;
  private Step quaternion_direction = Step.UP;
  private long quaternionCallbackPeriod;
  private long quaternion_callback_id;
  private float quaternion_last_value_called_back;

  private short orientation = 100;
  private short orientation_max = 1000;
  private short orientation_min = 0;
  private short orientation_step = 1;
  private long orientation_generator_period = 100;
  private Step orientation_direction = Step.UP;
  private long orientationCallbackPeriod;
  private long orientation_callback_id;
  private short orientation_last_value_called_back;

  private short allData = 100;
  private short allData_max = 1000;
  private short allData_min = 0;
  private short allData_step = 1;
  private long allData_generator_period = 100;
  private Step allData_direction = Step.UP;
  private long allDataCallbackPeriod;
  private long allData_callback_id;
  private short allData_last_value_called_back;

  private short angularVelocity = 100;
  private short angularVelocity_max = 1000;
  private short angularVelocity_min = 0;
  private short angularVelocity_step = 1;
  private long angularVelocity_generator_period = 100;
  private Step angularVelocity_direction = Step.UP;
  private long angularVelocityCallbackPeriod;
  private long angularVelocity_callback_id;
  private short angularVelocity_last_value_called_back;

  private short chipTemperature = 100;
  private short chipTemperature_max = 1000;
  private short chipTemperature_min = 0;
  private short chipTemperature_step = 1;
  private long chipTemperature_generator_period = 100;
  private Step chipTemperature_direction = Step.UP;
  private long chipTemperatureCallbackPeriod;
  private long chipTemperature_callback_id;
  private short chipTemperature_last_value_called_back;

  private short magneticField = 100;
  private short magneticField_max = 1000;
  private short magneticField_min = 0;
  private short magneticField_step = 1;
  private long magneticField_generator_period = 100;
  private Step magneticField_direction = Step.UP;
  private long magneticFieldCallbackPeriod;
  private long magneticField_callback_id;
  private short magneticField_last_value_called_back;

  private short acceleration = 100;
  private short acceleration_max = 1000;
  private short acceleration_min = 0;
  private short acceleration_step = 1;
  private long acceleration_generator_period = 100;
  private Step acceleration_direction = Step.UP;
  private long accelerationCallbackPeriod;
  private long acceleration_callback_id;
  private short acceleration_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 1;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickIMU.class);
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

    startIMUTemperatureGenerator();
    startQuaternionGenerator();
    startOrientationGenerator();
    startAllDataGenerator();
    startAngularVelocityGenerator();
    startChipTemperatureGenerator();
    startMagneticFieldGenerator();
    startAccelerationGenerator();
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
    else if (functionId == FUNCTION_GET_ALL_DATA) {
      buffer = getAllData(packet);
    }
    else if (functionId == FUNCTION_GET_ORIENTATION) {
      buffer = getOrientation(packet);
    }
    else if (functionId == FUNCTION_GET_QUATERNION) {
      buffer = getQuaternion(packet);
    }
    else if (functionId == FUNCTION_GET_IMU_TEMPERATURE) {
      buffer = getIMUTemperature(packet);
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
    else if (functionId == FUNCTION_SET_ACCELERATION_RANGE) {
      buffer = setAccelerationRange(packet);
    }
    else if (functionId == FUNCTION_GET_ACCELERATION_RANGE) {
      buffer = getAccelerationRange(packet);
    }
    else if (functionId == FUNCTION_SET_MAGNETOMETER_RANGE) {
      buffer = setMagnetometerRange(packet);
    }
    else if (functionId == FUNCTION_GET_MAGNETOMETER_RANGE) {
      buffer = getMagnetometerRange(packet);
    }
    else if (functionId == FUNCTION_SET_CONVERGENCE_SPEED) {
      buffer = setConvergenceSpeed(packet);
    }
    else if (functionId == FUNCTION_GET_CONVERGENCE_SPEED) {
      buffer = getConvergenceSpeed(packet);
    }
    else if (functionId == FUNCTION_SET_CALIBRATION) {
      buffer = setCalibration(packet);
    }
    else if (functionId == FUNCTION_GET_CALIBRATION) {
      buffer = getCalibration(packet);
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
    else if (functionId == FUNCTION_SET_ALL_DATA_PERIOD) {
      buffer = setAllDataPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ALL_DATA_PERIOD) {
      buffer = getAllDataPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ORIENTATION_PERIOD) {
      buffer = setOrientationPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ORIENTATION_PERIOD) {
      buffer = getOrientationPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_QUATERNION_PERIOD) {
      buffer = setQuaternionPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_QUATERNION_PERIOD) {
      buffer = getQuaternionPeriod(packet);
    }
    else if (functionId == FUNCTION_ORIENTATION_CALCULATION_ON) {
      buffer = orientationCalculationOn(packet);
    }
    else if (functionId == FUNCTION_ORIENTATION_CALCULATION_OFF) {
      buffer = orientationCalculationOff(packet);
    }
    else if (functionId == FUNCTION_IS_ORIENTATION_CALCULATION_ON) {
      buffer = isOrientationCalculationOn(packet);
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


  private void startIMUTemperatureGenerator() {
    if (iMUTemperature_step == 0) {
      return;
    }
    vertx.setPeriodic(iMUTemperature_generator_period, id -> {
      if (iMUTemperature_direction == Step.UP) {
        if (iMUTemperature >= iMUTemperature_max) {
          iMUTemperature_direction = Step.DOWN;
          this.iMUTemperature = (short) (iMUTemperature - iMUTemperature_step);
        } else {
          this.iMUTemperature = (short) (iMUTemperature + iMUTemperature_step);
        }
      } else {
        if (iMUTemperature <= iMUTemperature_min) {
          iMUTemperature_direction = Step.UP;
          this.iMUTemperature = (short) (iMUTemperature + iMUTemperature_step);
        } else {
          this.iMUTemperature = (short) (iMUTemperature - iMUTemperature_step);
        }
      }
    });
  }
        
  private void startQuaternionGenerator() {
    if (quaternion_step == 0) {
      return;
    }
    vertx.setPeriodic(quaternion_generator_period, id -> {
      if (quaternion_direction == Step.UP) {
        if (quaternion >= quaternion_max) {
          quaternion_direction = Step.DOWN;
          this.quaternion = (float) (quaternion - quaternion_step);
        } else {
          this.quaternion = (float) (quaternion + quaternion_step);
        }
      } else {
        if (quaternion <= quaternion_min) {
          quaternion_direction = Step.UP;
          this.quaternion = (float) (quaternion + quaternion_step);
        } else {
          this.quaternion = (float) (quaternion - quaternion_step);
        }
      }
    });
  }
        
  private void startOrientationGenerator() {
    if (orientation_step == 0) {
      return;
    }
    vertx.setPeriodic(orientation_generator_period, id -> {
      if (orientation_direction == Step.UP) {
        if (orientation >= orientation_max) {
          orientation_direction = Step.DOWN;
          this.orientation = (short) (orientation - orientation_step);
        } else {
          this.orientation = (short) (orientation + orientation_step);
        }
      } else {
        if (orientation <= orientation_min) {
          orientation_direction = Step.UP;
          this.orientation = (short) (orientation + orientation_step);
        } else {
          this.orientation = (short) (orientation - orientation_step);
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
        
  private void startAngularVelocityGenerator() {
    if (angularVelocity_step == 0) {
      return;
    }
    vertx.setPeriodic(angularVelocity_generator_period, id -> {
      if (angularVelocity_direction == Step.UP) {
        if (angularVelocity >= angularVelocity_max) {
          angularVelocity_direction = Step.DOWN;
          this.angularVelocity = (short) (angularVelocity - angularVelocity_step);
        } else {
          this.angularVelocity = (short) (angularVelocity + angularVelocity_step);
        }
      } else {
        if (angularVelocity <= angularVelocity_min) {
          angularVelocity_direction = Step.UP;
          this.angularVelocity = (short) (angularVelocity + angularVelocity_step);
        } else {
          this.angularVelocity = (short) (angularVelocity - angularVelocity_step);
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
        
  private void startMagneticFieldGenerator() {
    if (magneticField_step == 0) {
      return;
    }
    vertx.setPeriodic(magneticField_generator_period, id -> {
      if (magneticField_direction == Step.UP) {
        if (magneticField >= magneticField_max) {
          magneticField_direction = Step.DOWN;
          this.magneticField = (short) (magneticField - magneticField_step);
        } else {
          this.magneticField = (short) (magneticField + magneticField_step);
        }
      } else {
        if (magneticField <= magneticField_min) {
          magneticField_direction = Step.UP;
          this.magneticField = (short) (magneticField + magneticField_step);
        } else {
          this.magneticField = (short) (magneticField - magneticField_step);
        }
      }
    });
  }
        
  private void startAccelerationGenerator() {
    if (acceleration_step == 0) {
      return;
    }
    vertx.setPeriodic(acceleration_generator_period, id -> {
      if (acceleration_direction == Step.UP) {
        if (acceleration >= acceleration_max) {
          acceleration_direction = Step.DOWN;
          this.acceleration = (short) (acceleration - acceleration_step);
        } else {
          this.acceleration = (short) (acceleration + acceleration_step);
        }
      } else {
        if (acceleration <= acceleration_min) {
          acceleration_direction = Step.UP;
          this.acceleration = (short) (acceleration + acceleration_step);
        } else {
          this.acceleration = (short) (acceleration - acceleration_step);
        }
      }
    });
  }
        
    private void stopAccelerationCallback() {
        vertx.cancelTimer(acceleration_callback_id);
  }
        //fixme start_generator callback without sensor acceleration

    private void stopOrientationCallback() {
        vertx.cancelTimer(orientation_callback_id);
  }
        //fixme start_generator callback without sensor orientation

    private void stopMagneticFieldCallback() {
        vertx.cancelTimer(magneticField_callback_id);
  }
        //fixme start_generator callback without sensor magneticField

    private void stopAngularVelocityCallback() {
        vertx.cancelTimer(angularVelocity_callback_id);
  }
        //fixme start_generator callback without sensor angularVelocity

    private void stopQuaternionCallback() {
        vertx.cancelTimer(quaternion_callback_id);
  }
        //fixme start_generator callback without sensor quaternion

    private void stopAllDataCallback() {
        vertx.cancelTimer(allData_callback_id);
  }
        //fixme start_generator callback without sensor allData

  private void startAccelerationCallback() {
    logger.trace("accelerationCallbackPeriod is {}", accelerationCallbackPeriod);
    acceleration_callback_id = vertx.setPeriodic(accelerationCallbackPeriod, id -> {
      if (acceleration != acceleration_last_value_called_back) {
        acceleration_last_value_called_back = acceleration;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("acceleration sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAcceleration4Callback());
          }
        } else {
          logger.info("no handlerids found in acceleration callback");
        }
      } else {
        logger.debug("acceleration value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor acceleration

  private void startOrientationCallback() {
    logger.trace("orientationCallbackPeriod is {}", orientationCallbackPeriod);
    orientation_callback_id = vertx.setPeriodic(orientationCallbackPeriod, id -> {
      if (orientation != orientation_last_value_called_back) {
        orientation_last_value_called_back = orientation;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("orientation sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getOrientation4Callback());
          }
        } else {
          logger.info("no handlerids found in orientation callback");
        }
      } else {
        logger.debug("orientation value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor orientation

  private void startMagneticFieldCallback() {
    logger.trace("magneticFieldCallbackPeriod is {}", magneticFieldCallbackPeriod);
    magneticField_callback_id = vertx.setPeriodic(magneticFieldCallbackPeriod, id -> {
      if (magneticField != magneticField_last_value_called_back) {
        magneticField_last_value_called_back = magneticField;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("magneticField sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getMagneticField4Callback());
          }
        } else {
          logger.info("no handlerids found in magneticField callback");
        }
      } else {
        logger.debug("magneticField value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor magneticField

  private void startAngularVelocityCallback() {
    logger.trace("angularVelocityCallbackPeriod is {}", angularVelocityCallbackPeriod);
    angularVelocity_callback_id = vertx.setPeriodic(angularVelocityCallbackPeriod, id -> {
      if (angularVelocity != angularVelocity_last_value_called_back) {
        angularVelocity_last_value_called_back = angularVelocity;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("angularVelocity sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAngularVelocity4Callback());
          }
        } else {
          logger.info("no handlerids found in angularVelocity callback");
        }
      } else {
        logger.debug("angularVelocity value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor angularVelocity

  private void startQuaternionCallback() {
    logger.trace("quaternionCallbackPeriod is {}", quaternionCallbackPeriod);
    quaternion_callback_id = vertx.setPeriodic(quaternionCallbackPeriod, id -> {
      if (quaternion != quaternion_last_value_called_back) {
        quaternion_last_value_called_back = quaternion;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("quaternion sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getQuaternion4Callback());
          }
        } else {
          logger.info("no handlerids found in quaternion callback");
        }
      } else {
        logger.debug("quaternion value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor quaternion

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

  private Buffer getAcceleration4Callback() {
      byte options = (byte) 0;
      return getAccelerationBuffer(CALLBACK_ACCELERATION, options);
  }
        //fixme getter callback without sensor acceleration

  private Buffer getOrientation4Callback() {
      byte options = (byte) 0;
      return getOrientationBuffer(CALLBACK_ORIENTATION, options);
  }
        //fixme getter callback without sensor orientation

  private Buffer getMagneticField4Callback() {
      byte options = (byte) 0;
      return getMagneticFieldBuffer(CALLBACK_MAGNETIC_FIELD, options);
  }
        //fixme getter callback without sensor magneticField

  private Buffer getAngularVelocity4Callback() {
      byte options = (byte) 0;
      return getAngularVelocityBuffer(CALLBACK_ANGULAR_VELOCITY, options);
  }
        //fixme getter callback without sensor angularVelocity

  private Buffer getQuaternion4Callback() {
      byte options = (byte) 0;
      return getQuaternionBuffer(CALLBACK_QUATERNION, options);
  }
        //fixme getter callback without sensor quaternion

  private Buffer getAllData4Callback() {
      byte options = (byte) 0;
      return getAllDataBuffer(CALLBACK_ALL_DATA, options);
  }
        //fixme getter callback without sensor allData

  private Buffer getAccelerationBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 6;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(acceleration));
    buffer.appendBytes(Utils.getUInt16(acceleration));
    buffer.appendBytes(Utils.getUInt16(acceleration));

    return buffer;
  }
        
  private Buffer getAccelerationBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAccelerationBuffer(FUNCTION_GET_ACCELERATION, options);
  }

  private Buffer getAcceleration(Packet packet) {
    logger.debug("function getAcceleration");
    if (packet.getResponseExpected()) {
      return getAccelerationBuffer(packet);
    }
    return null;
  }

  private Buffer getMagneticFieldBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 6;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(magneticField));
    buffer.appendBytes(Utils.getUInt16(magneticField));
    buffer.appendBytes(Utils.getUInt16(magneticField));

    return buffer;
  }
        
  private Buffer getMagneticFieldBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getMagneticFieldBuffer(FUNCTION_GET_MAGNETIC_FIELD, options);
  }

  private Buffer getMagneticField(Packet packet) {
    logger.debug("function getMagneticField");
    if (packet.getResponseExpected()) {
      return getMagneticFieldBuffer(packet);
    }
    return null;
  }

  private Buffer getAngularVelocityBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 6;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(angularVelocity));
    buffer.appendBytes(Utils.getUInt16(angularVelocity));
    buffer.appendBytes(Utils.getUInt16(angularVelocity));

    return buffer;
  }
        
  private Buffer getAngularVelocityBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAngularVelocityBuffer(FUNCTION_GET_ANGULAR_VELOCITY, options);
  }

  private Buffer getAngularVelocity(Packet packet) {
    logger.debug("function getAngularVelocity");
    if (packet.getResponseExpected()) {
      return getAngularVelocityBuffer(packet);
    }
    return null;
  }

  private Buffer getAllDataBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 20;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
    buffer.appendBytes(Utils.getUInt16(allData));
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

  private Buffer getOrientationBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 6;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(orientation));
    buffer.appendBytes(Utils.getUInt16(orientation));
    buffer.appendBytes(Utils.getUInt16(orientation));

    return buffer;
  }
        
  private Buffer getOrientationBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getOrientationBuffer(FUNCTION_GET_ORIENTATION, options);
  }

  private Buffer getOrientation(Packet packet) {
    logger.debug("function getOrientation");
    if (packet.getResponseExpected()) {
      return getOrientationBuffer(packet);
    }
    return null;
  }

  private Buffer getQuaternionBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 16;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.floatBuffer(quaternion));
    buffer.appendBytes(Utils.floatBuffer(quaternion));
    buffer.appendBytes(Utils.floatBuffer(quaternion));
    buffer.appendBytes(Utils.floatBuffer(quaternion));

    return buffer;
  }
        
  private Buffer getQuaternionBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getQuaternionBuffer(FUNCTION_GET_QUATERNION, options);
  }

  private Buffer getQuaternion(Packet packet) {
    logger.debug("function getQuaternion");
    if (packet.getResponseExpected()) {
      return getQuaternionBuffer(packet);
    }
    return null;
  }

  private Buffer getIMUTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(iMUTemperature));

    return buffer;
  }
        
  private Buffer getIMUTemperatureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIMUTemperatureBuffer(FUNCTION_GET_IMU_TEMPERATURE, options);
  }

  private Buffer getIMUTemperature(Packet packet) {
    logger.debug("function getIMUTemperature");
    if (packet.getResponseExpected()) {
      return getIMUTemperatureBuffer(packet);
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
  private Buffer getConvergenceSpeed(Packet packet) {
    logger.debug("function getConvergenceSpeed");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CONVERGENCE_SPEED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.convergenceSpeed);
      return buffer;
    }

    return null;
  }

  private Buffer getConvergenceSpeedDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

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
  private Buffer getAccelerationRange(Packet packet) {
    logger.debug("function getAccelerationRange");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_ACCELERATION_RANGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.accelerationRange);
      return buffer;
    }

    return null;
  }

  private Buffer getAccelerationRangeDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getCalibration(Packet packet) {
    logger.debug("function getCalibration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 20;
      byte functionId = FUNCTION_GET_CALIBRATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.calibration);
      return buffer;
    }

    return null;
  }

  private Buffer getCalibrationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteRandomValue(10));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getMagnetometerRange(Packet packet) {
    logger.debug("function getMagnetometerRange");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_MAGNETOMETER_RANGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.magnetometerRange);
      return buffer;
    }

    return null;
  }

  private Buffer getMagnetometerRangeDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

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
  private Buffer setCalibration(Packet packet) {
    logger.debug("function setCalibration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CALIBRATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.calibration = packet.getPayload();
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
  private Buffer setMagnetometerRange(Packet packet) {
    logger.debug("function setMagnetometerRange");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MAGNETOMETER_RANGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.magnetometerRange = packet.getPayload();
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
  private Buffer setConvergenceSpeed(Packet packet) {
    logger.debug("function setConvergenceSpeed");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CONVERGENCE_SPEED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.convergenceSpeed = packet.getPayload();
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
  private Buffer setAccelerationRange(Packet packet) {
    logger.debug("function setAccelerationRange");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ACCELERATION_RANGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.accelerationRange = packet.getPayload();
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
  private Buffer isOrientationCalculationOn(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer orientationCalculationOff(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer orientationCalculationOn(Packet packet) {
    //TODO dummy method
    return null;
  }
}
