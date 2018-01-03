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
 * Creates beep with configurable frequency
 */
public class BrickletPiezoSpeaker extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 242;
public final static String DEVICE_DISPLAY_NAME = "Piezo Speaker Bricklet";

  public final static byte FUNCTION_BEEP = (byte)1;
  public final static byte FUNCTION_MORSE_CODE = (byte)2;
  public final static byte FUNCTION_CALIBRATE = (byte)3;
  public final static byte CALLBACK_BEEP_FINISHED = (byte)4;
  public final static byte CALLBACK_MORSE_CODE_FINISHED = (byte)5;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static long BEEP_DURATION_OFF = 0L;
  public final static long BEEP_DURATION_INFINITE = 4294967295L;
  String uidString;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletPiezoSpeaker.class);
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
    else if (functionId == FUNCTION_BEEP) {
      buffer = beep(packet);
    }
    else if (functionId == FUNCTION_MORSE_CODE) {
      buffer = morseCode(packet);
    }
    else if (functionId == FUNCTION_CALIBRATE) {
      buffer = calibrate(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }

//fixme start_generator callback without sensor beepFinished
//fixme start_generator callback without sensor morseCodeFinished
//fixme stop_generator callback without sensor beepFinished
//fixme stop_generator callback without sensor morseCodeFinished
//fixme getter callback without sensor beepFinished
//fixme getter callback without sensor morseCodeFinished

  /**
   * 
   */
  private Buffer beep(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer morseCode(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer calibrate(Packet packet) {
    //TODO dummy method
    return null;
  }
}
