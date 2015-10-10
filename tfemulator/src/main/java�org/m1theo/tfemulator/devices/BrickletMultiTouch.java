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
 * Capacitive touch sensor for 12 electrodes
 */
public class BrickletMultiTouch extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 234;
public final static String DEVICE_DISPLAY_NAME = "Multi Touch Bricklet";

  public final static byte FUNCTION_GET_TOUCH_STATE = (byte)1;
  public final static byte FUNCTION_RECALIBRATE = (byte)2;
  public final static byte FUNCTION_SET_ELECTRODE_CONFIG = (byte)3;
  public final static byte FUNCTION_GET_ELECTRODE_CONFIG = (byte)4;
  public final static byte CALLBACK_TOUCH_STATE = (byte)5;
  public final static byte FUNCTION_SET_ELECTRODE_SENSITIVITY = (byte)6;
  public final static byte FUNCTION_GET_ELECTRODE_SENSITIVITY = (byte)7;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  String uidString;

  private Buffer electrodeConfig = getElectrodeConfigDefault();
        
  private Buffer electrodeSensitivity = getElectrodeSensitivityDefault();
        
  private short touchState = 100;
  private short touchState_max = 1000;
  private short touchState_min = 0;
  private short touchState_step = 1;
  private long touchState_generator_period = 100;
  private Step touchState_direction = Step.UP;
  private long touchStateCallbackPeriod;
  private long touchState_callback_id;
  private short touchState_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletMultiTouch.class);
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

    startTouchStateGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_TOUCH_STATE) {
      buffer = getTouchState(packet);
    }
    else if (functionId == FUNCTION_RECALIBRATE) {
      buffer = recalibrate(packet);
    }
    else if (functionId == FUNCTION_SET_ELECTRODE_CONFIG) {
      buffer = setElectrodeConfig(packet);
    }
    else if (functionId == FUNCTION_GET_ELECTRODE_CONFIG) {
      buffer = getElectrodeConfig(packet);
    }
    else if (functionId == FUNCTION_SET_ELECTRODE_SENSITIVITY) {
      buffer = setElectrodeSensitivity(packet);
    }
    else if (functionId == FUNCTION_GET_ELECTRODE_SENSITIVITY) {
      buffer = getElectrodeSensitivity(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startTouchStateGenerator() {
    if (touchState_step == 0) {
      return;
    }
    vertx.setPeriodic(touchState_generator_period, id -> {
      if (touchState_direction == Step.UP) {
        if (touchState >= touchState_max) {
          touchState_direction = Step.DOWN;
          this.touchState = (short) (touchState - touchState_step);
        } else {
          this.touchState = (short) (touchState + touchState_step);
        }
      } else {
        if (touchState <= touchState_min) {
          touchState_direction = Step.UP;
          this.touchState = (short) (touchState + touchState_step);
        } else {
          this.touchState = (short) (touchState - touchState_step);
        }
      }
    });
  }
        
    private void stopTouchStateCallback() {
        vertx.cancelTimer(touchState_callback_id);
  }
        //fixme start_generator callback without sensor touchState

  private void startTouchStateCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    touchState_callback_id = vertx.setPeriodic(touchStateCallbackPeriod, id -> {
      if (touchState != touchState_last_value_called_back) {
        touchState_last_value_called_back = touchState;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("touchState sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getTouchState4Callback());
          }
        } else {
          logger.info("no handlerids found in touchState callback");
        }
      } else {
        logger.debug("touchState value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor touchState

  private Buffer getTouchState4Callback() {
      byte options = (byte) 0;
      return getTouchStateBuffer(CALLBACK_TOUCH_STATE, options);
  }
        //fixme getter callback without sensor touchState

  private Buffer getTouchStateBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(touchState));

    return buffer;
  }
        
  private Buffer getTouchStateBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getTouchStateBuffer(FUNCTION_GET_TOUCH_STATE, options);
  }

  private Buffer getTouchState(Packet packet) {
    logger.debug("function getTouchState");
    if (packet.getResponseExpected()) {
      return getTouchStateBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getElectrodeConfig(Packet packet) {
    logger.debug("function getElectrodeConfig");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_ELECTRODE_CONFIG;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.electrodeConfig);
      return buffer;
    }

    return null;
  }

  private Buffer getElectrodeConfigDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getElectrodeSensitivity(Packet packet) {
    logger.debug("function getElectrodeSensitivity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_ELECTRODE_SENSITIVITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.electrodeSensitivity);
      return buffer;
    }

    return null;
  }

  private Buffer getElectrodeSensitivityDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer setElectrodeSensitivity(Packet packet) {
    logger.debug("function setElectrodeSensitivity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ELECTRODE_SENSITIVITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.electrodeSensitivity = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setElectrodeConfig(Packet packet) {
    logger.debug("function setElectrodeConfig");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ELECTRODE_CONFIG;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.electrodeConfig = packet.getPayload();
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
  private Buffer recalibrate(Packet packet) {
    //TODO dummy method
    return null;
  }
}
