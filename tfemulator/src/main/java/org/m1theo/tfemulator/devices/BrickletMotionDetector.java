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
 * Passive infrared (PIR) motion sensor, 7m range
 */
public class BrickletMotionDetector extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 233;
public final static String DEVICE_DISPLAY_NAME = "Motion Detector Bricklet";

  public final static byte FUNCTION_GET_MOTION_DETECTED = (byte)1;
  public final static byte CALLBACK_MOTION_DETECTED = (byte)2;
  public final static byte CALLBACK_DETECTION_CYCLE_ENDED = (byte)3;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short MOTION_NOT_DETECTED = (short)0;
  public final static short MOTION_DETECTED = (short)1;
  String uidString;

  private byte motionDetected = 100;
  private byte motionDetected_max = 1000;
  private byte motionDetected_min = 0;
  private byte motionDetected_step = 1;
  private long motionDetected_generator_period = 100;
  private Step motionDetected_direction = Step.UP;
  private long motionDetectedCallbackPeriod;
  private long motionDetected_callback_id;
  private byte motionDetected_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletMotionDetector.class);
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

    startMotionDetectedGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_MOTION_DETECTED) {
      buffer = getMotionDetected(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startMotionDetectedGenerator() {
    if (motionDetected_step == 0) {
      return;
    }
    vertx.setPeriodic(motionDetected_generator_period, id -> {
      if (motionDetected_direction == Step.UP) {
        if (motionDetected >= motionDetected_max) {
          motionDetected_direction = Step.DOWN;
          this.motionDetected = (byte) (motionDetected - motionDetected_step);
        } else {
          this.motionDetected = (byte) (motionDetected + motionDetected_step);
        }
      } else {
        if (motionDetected <= motionDetected_min) {
          motionDetected_direction = Step.UP;
          this.motionDetected = (byte) (motionDetected + motionDetected_step);
        } else {
          this.motionDetected = (byte) (motionDetected - motionDetected_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor detectionCycleEnded

    private void stopMotionDetectedCallback() {
        vertx.cancelTimer(motionDetected_callback_id);
  }
        //fixme start_generator callback without sensor motionDetected
//fixme stop_generator callback without sensor detectionCycleEnded

  private void startMotionDetectedCallback() {
    logger.trace("motionDetectedCallbackPeriod is {}", motionDetectedCallbackPeriod);
    motionDetected_callback_id = vertx.setPeriodic(motionDetectedCallbackPeriod, id -> {
      if (motionDetected != motionDetected_last_value_called_back) {
        motionDetected_last_value_called_back = motionDetected;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("motionDetected sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getMotionDetected4Callback());
          }
        } else {
          logger.info("no handlerids found in motionDetected callback");
        }
      } else {
        logger.debug("motionDetected value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor motionDetected
//fixme getter callback without sensor detectionCycleEnded

  private Buffer getMotionDetected4Callback() {
      byte options = (byte) 0;
      return getMotionDetectedBuffer(CALLBACK_MOTION_DETECTED, options);
  }
        //fixme getter callback without sensor motionDetected

  private Buffer getMotionDetectedBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(motionDetected));

    return buffer;
  }
        
  private Buffer getMotionDetectedBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getMotionDetectedBuffer(FUNCTION_GET_MOTION_DETECTED, options);
  }

  private Buffer getMotionDetected(Packet packet) {
    logger.debug("function getMotionDetected");
    if (packet.getResponseExpected()) {
      return getMotionDetectedBuffer(packet);
    }
    return null;
  }
}
