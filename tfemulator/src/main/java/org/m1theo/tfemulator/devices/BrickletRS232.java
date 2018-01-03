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
 * Communicates with RS232 devices
 */
public class BrickletRS232 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 254;
public final static String DEVICE_DISPLAY_NAME = "RS232 Bricklet";

  public final static byte FUNCTION_WRITE = (byte)1;
  public final static byte FUNCTION_READ = (byte)2;
  public final static byte FUNCTION_ENABLE_READ_CALLBACK = (byte)3;
  public final static byte FUNCTION_DISABLE_READ_CALLBACK = (byte)4;
  public final static byte FUNCTION_IS_READ_CALLBACK_ENABLED = (byte)5;
  public final static byte FUNCTION_SET_CONFIGURATION = (byte)6;
  public final static byte FUNCTION_GET_CONFIGURATION = (byte)7;
  public final static byte CALLBACK_READ_CALLBACK = (byte)8;
  public final static byte CALLBACK_ERROR_CALLBACK = (byte)9;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short BAUDRATE_300 = (short)0;
  public final static short BAUDRATE_600 = (short)1;
  public final static short BAUDRATE_1200 = (short)2;
  public final static short BAUDRATE_2400 = (short)3;
  public final static short BAUDRATE_4800 = (short)4;
  public final static short BAUDRATE_9600 = (short)5;
  public final static short BAUDRATE_14400 = (short)6;
  public final static short BAUDRATE_19200 = (short)7;
  public final static short BAUDRATE_28800 = (short)8;
  public final static short BAUDRATE_38400 = (short)9;
  public final static short BAUDRATE_57600 = (short)10;
  public final static short BAUDRATE_115200 = (short)11;
  public final static short BAUDRATE_230400 = (short)12;
  public final static short PARITY_NONE = (short)0;
  public final static short PARITY_ODD = (short)1;
  public final static short PARITY_EVEN = (short)2;
  public final static short PARITY_FORCED_PARITY_1 = (short)3;
  public final static short PARITY_FORCED_PARITY_0 = (short)4;
  public final static short STOPBITS_1 = (short)1;
  public final static short STOPBITS_2 = (short)2;
  public final static short WORDLENGTH_5 = (short)5;
  public final static short WORDLENGTH_6 = (short)6;
  public final static short WORDLENGTH_7 = (short)7;
  public final static short WORDLENGTH_8 = (short)8;
  public final static short HARDWARE_FLOWCONTROL_OFF = (short)0;
  public final static short HARDWARE_FLOWCONTROL_ON = (short)1;
  public final static short SOFTWARE_FLOWCONTROL_OFF = (short)0;
  public final static short SOFTWARE_FLOWCONTROL_ON = (short)1;
  public final static short ERROR_OVERRUN = (short)1;
  public final static short ERROR_PARITY = (short)2;
  public final static short ERROR_FRAMING = (short)4;
  String uidString;

  private Buffer configuration = getConfigurationDefault();
        
  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 1;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletRS232.class);
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
    else if (functionId == FUNCTION_WRITE) {
      buffer = write(packet);
    }
    else if (functionId == FUNCTION_READ) {
      buffer = read(packet);
    }
    else if (functionId == FUNCTION_ENABLE_READ_CALLBACK) {
      buffer = enableReadCallback(packet);
    }
    else if (functionId == FUNCTION_DISABLE_READ_CALLBACK) {
      buffer = disableReadCallback(packet);
    }
    else if (functionId == FUNCTION_IS_READ_CALLBACK_ENABLED) {
      buffer = isReadCallbackEnabled(packet);
    }
    else if (functionId == FUNCTION_SET_CONFIGURATION) {
      buffer = setConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIGURATION) {
      buffer = getConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }

//fixme start_generator callback without sensor readCallback
//fixme start_generator callback without sensor errorCallback
//fixme stop_generator callback without sensor readCallback
//fixme stop_generator callback without sensor errorCallback
//fixme getter callback without sensor readCallback
//fixme getter callback without sensor errorCallback

  /**
   * 
   */
  private Buffer write(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer read(Packet packet) {
    //TODO dummy method
    return null;
  }
}
