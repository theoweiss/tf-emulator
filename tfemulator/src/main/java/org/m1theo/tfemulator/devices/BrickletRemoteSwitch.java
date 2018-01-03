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
 * Controls remote mains switches
 */
public class BrickletRemoteSwitch extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 235;
public final static String DEVICE_DISPLAY_NAME = "Remote Switch Bricklet";

  public final static byte FUNCTION_SWITCH_SOCKET = (byte)1;
  public final static byte FUNCTION_GET_SWITCHING_STATE = (byte)2;
  public final static byte CALLBACK_SWITCHING_DONE = (byte)3;
  public final static byte FUNCTION_SET_REPEATS = (byte)4;
  public final static byte FUNCTION_GET_REPEATS = (byte)5;
  public final static byte FUNCTION_SWITCH_SOCKET_A = (byte)6;
  public final static byte FUNCTION_SWITCH_SOCKET_B = (byte)7;
  public final static byte FUNCTION_DIM_SOCKET_B = (byte)8;
  public final static byte FUNCTION_SWITCH_SOCKET_C = (byte)9;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short SWITCH_TO_OFF = (short)0;
  public final static short SWITCH_TO_ON = (short)1;
  public final static short SWITCHING_STATE_READY = (short)0;
  public final static short SWITCHING_STATE_BUSY = (short)1;
  String uidString;

  private Buffer repeats = getRepeatsDefault();
        
  private byte switchingState = 100;
  private byte switchingState_max = 1000;
  private byte switchingState_min = 0;
  private byte switchingState_step = 1;
  private long switchingState_generator_period = 100;
  private Step switchingState_direction = Step.UP;
  private long switchingStateCallbackPeriod;
  private long switchingState_callback_id;
  private byte switchingState_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 1;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletRemoteSwitch.class);
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

    startSwitchingStateGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_SWITCH_SOCKET) {
      buffer = switchSocket(packet);
    }
    else if (functionId == FUNCTION_GET_SWITCHING_STATE) {
      buffer = getSwitchingState(packet);
    }
    else if (functionId == FUNCTION_SET_REPEATS) {
      buffer = setRepeats(packet);
    }
    else if (functionId == FUNCTION_GET_REPEATS) {
      buffer = getRepeats(packet);
    }
    else if (functionId == FUNCTION_SWITCH_SOCKET_A) {
      buffer = switchSocketA(packet);
    }
    else if (functionId == FUNCTION_SWITCH_SOCKET_B) {
      buffer = switchSocketB(packet);
    }
    else if (functionId == FUNCTION_DIM_SOCKET_B) {
      buffer = dimSocketB(packet);
    }
    else if (functionId == FUNCTION_SWITCH_SOCKET_C) {
      buffer = switchSocketC(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startSwitchingStateGenerator() {
    if (switchingState_step == 0) {
      return;
    }
    vertx.setPeriodic(switchingState_generator_period, id -> {
      if (switchingState_direction == Step.UP) {
        if (switchingState >= switchingState_max) {
          switchingState_direction = Step.DOWN;
          this.switchingState = (byte) (switchingState - switchingState_step);
        } else {
          this.switchingState = (byte) (switchingState + switchingState_step);
        }
      } else {
        if (switchingState <= switchingState_min) {
          switchingState_direction = Step.UP;
          this.switchingState = (byte) (switchingState + switchingState_step);
        } else {
          this.switchingState = (byte) (switchingState - switchingState_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor switchingDone
//fixme stop_generator callback without sensor switchingDone
//fixme getter callback without sensor switchingDone

  private Buffer getSwitchingStateBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(switchingState));

    return buffer;
  }
        
  private Buffer getSwitchingStateBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getSwitchingStateBuffer(FUNCTION_GET_SWITCHING_STATE, options);
  }

  private Buffer getSwitchingState(Packet packet) {
    logger.debug("function getSwitchingState");
    if (packet.getResponseExpected()) {
      return getSwitchingStateBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer switchSocketA(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer switchSocketB(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer switchSocket(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer switchSocketC(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer dimSocketB(Packet packet) {
    //TODO dummy method
    return null;
  }
}
