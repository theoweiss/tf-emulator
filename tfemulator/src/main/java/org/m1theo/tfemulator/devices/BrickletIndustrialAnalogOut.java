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
 * Generates configurable DC voltage and current, 0V to 10V and 4mA to 20mA
 */
public class BrickletIndustrialAnalogOut extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 258;
public final static String DEVICE_DISPLAY_NAME = "Industrial Analog Out Bricklet";

  public final static byte FUNCTION_ENABLE = (byte)1;
  public final static byte FUNCTION_DISABLE = (byte)2;
  public final static byte FUNCTION_IS_ENABLED = (byte)3;
  public final static byte FUNCTION_SET_VOLTAGE = (byte)4;
  public final static byte FUNCTION_GET_VOLTAGE = (byte)5;
  public final static byte FUNCTION_SET_CURRENT = (byte)6;
  public final static byte FUNCTION_GET_CURRENT = (byte)7;
  public final static byte FUNCTION_SET_CONFIGURATION = (byte)8;
  public final static byte FUNCTION_GET_CONFIGURATION = (byte)9;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short VOLTAGE_RANGE_0_TO_5V = (short)0;
  public final static short VOLTAGE_RANGE_0_TO_10V = (short)1;
  public final static short CURRENT_RANGE_4_TO_20MA = (short)0;
  public final static short CURRENT_RANGE_0_TO_20MA = (short)1;
  public final static short CURRENT_RANGE_0_TO_24MA = (short)2;
  String uidString;

  private Buffer current = getCurrentDefault();
        
  private Buffer configuration = getConfigurationDefault();
        
  private Buffer voltage = getVoltageDefault();
        
  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletIndustrialAnalogOut.class);
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
    else if (functionId == FUNCTION_ENABLE) {
      buffer = enable(packet);
    }
    else if (functionId == FUNCTION_DISABLE) {
      buffer = disable(packet);
    }
    else if (functionId == FUNCTION_IS_ENABLED) {
      buffer = isEnabled(packet);
    }
    else if (functionId == FUNCTION_SET_VOLTAGE) {
      buffer = setVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_VOLTAGE) {
      buffer = getVoltage(packet);
    }
    else if (functionId == FUNCTION_SET_CURRENT) {
      buffer = setCurrent(packet);
    }
    else if (functionId == FUNCTION_GET_CURRENT) {
      buffer = getCurrent(packet);
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

}
