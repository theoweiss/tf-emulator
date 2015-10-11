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
 * Detects inclination of Bricklet (tilt switch open/closed)
 */
public class BrickletTilt extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 239;
public final static String DEVICE_DISPLAY_NAME = "Tilt Bricklet";

  public final static byte FUNCTION_GET_TILT_STATE = (byte)1;
  public final static byte FUNCTION_ENABLE_TILT_STATE_CALLBACK = (byte)2;
  public final static byte FUNCTION_DISABLE_TILT_STATE_CALLBACK = (byte)3;
  public final static byte FUNCTION_IS_TILT_STATE_CALLBACK_ENABLED = (byte)4;
  public final static byte CALLBACK_TILT_STATE = (byte)5;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short TILT_STATE_CLOSED = (short)0;
  public final static short TILT_STATE_OPEN = (short)1;
  public final static short TILT_STATE_CLOSED_VIBRATING = (short)2;
  String uidString;

  private Buffer TiltStateCallback = getTiltStateCallbackDefault();
        
  private byte tiltState = 100;
  private byte tiltState_max = 1000;
  private byte tiltState_min = 0;
  private byte tiltState_step = 1;
  private long tiltState_generator_period = 100;
  private Step tiltState_direction = Step.UP;
  private long tiltStateCallbackPeriod;
  private long tiltState_callback_id;
  private byte tiltState_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletTilt.class);
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

    startTiltStateGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_TILT_STATE) {
      buffer = getTiltState(packet);
    }
    else if (functionId == FUNCTION_ENABLE_TILT_STATE_CALLBACK) {
      buffer = enableTiltStateCallback(packet);
    }
    else if (functionId == FUNCTION_DISABLE_TILT_STATE_CALLBACK) {
      buffer = disableTiltStateCallback(packet);
    }
    else if (functionId == FUNCTION_IS_TILT_STATE_CALLBACK_ENABLED) {
      buffer = isTiltStateCallbackEnabled(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startTiltStateGenerator() {
    if (tiltState_step == 0) {
      return;
    }
    vertx.setPeriodic(tiltState_generator_period, id -> {
      if (tiltState_direction == Step.UP) {
        if (tiltState >= tiltState_max) {
          tiltState_direction = Step.DOWN;
          this.tiltState = (byte) (tiltState - tiltState_step);
        } else {
          this.tiltState = (byte) (tiltState + tiltState_step);
        }
      } else {
        if (tiltState <= tiltState_min) {
          tiltState_direction = Step.UP;
          this.tiltState = (byte) (tiltState + tiltState_step);
        } else {
          this.tiltState = (byte) (tiltState - tiltState_step);
        }
      }
    });
  }
        
    private void stopTiltStateCallback() {
        vertx.cancelTimer(tiltState_callback_id);
  }
        //fixme start_generator callback without sensor tiltState

  private void startTiltStateCallback() {
    logger.trace("tiltStateCallbackPeriod is {}", tiltStateCallbackPeriod);
    tiltState_callback_id = vertx.setPeriodic(tiltStateCallbackPeriod, id -> {
      if (tiltState != tiltState_last_value_called_back) {
        tiltState_last_value_called_back = tiltState;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("tiltState sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getTiltState4Callback());
          }
        } else {
          logger.info("no handlerids found in tiltState callback");
        }
      } else {
        logger.debug("tiltState value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor tiltState

  private Buffer getTiltState4Callback() {
      byte options = (byte) 0;
      return getTiltStateBuffer(CALLBACK_TILT_STATE, options);
  }
        //fixme getter callback without sensor tiltState

  private Buffer getTiltStateBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(tiltState));

    return buffer;
  }
        
  private Buffer getTiltStateBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getTiltStateBuffer(FUNCTION_GET_TILT_STATE, options);
  }

  private Buffer getTiltState(Packet packet) {
    logger.debug("function getTiltState");
    if (packet.getResponseExpected()) {
      return getTiltStateBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer isTiltStateCallbackEnabled(Packet packet) {
    logger.debug("function isTiltStateCallbackEnabled");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_TILT_STATE_CALLBACK_ENABLED;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.TiltStateCallback);
      return buffer;
    }

    return null;
  }

  private Buffer isTiltStateCallbackEnabledDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getBoolRandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer enableTiltStateCallback(Packet packet) {
    logger.debug("function enableTiltStateCallback");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_ENABLE_TILT_STATE_CALLBACK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.TiltStateCallback = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer disableTiltStateCallback(Packet packet) {
    logger.debug("function disableTiltStateCallback");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_DISABLE_TILT_STATE_CALLBACK;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.TiltStateCallback = packet.getPayload();
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
