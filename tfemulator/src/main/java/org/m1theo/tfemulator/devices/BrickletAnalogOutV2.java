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
 * Generates configurable DC voltage between 0V and 12V
 */
public class BrickletAnalogOutV2 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 256;
public final static String DEVICE_DISPLAY_NAME = "Analog Out Bricklet 2.0";

  public final static byte FUNCTION_SET_OUTPUT_VOLTAGE = (byte)1;
  public final static byte FUNCTION_GET_OUTPUT_VOLTAGE = (byte)2;
  public final static byte FUNCTION_GET_INPUT_VOLTAGE = (byte)3;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  String uidString;

  private Buffer outputVoltage = getOutputVoltageDefault();
        
  private short inputVoltage = 100;
  private short inputVoltage_max = 1000;
  private short inputVoltage_min = 0;
  private short inputVoltage_step = 1;
  private long inputVoltage_generator_period = 100;
  private Step inputVoltage_direction = Step.UP;
  private long inputVoltageCallbackPeriod;
  private long inputVoltage_callback_id;
  private short inputVoltage_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletAnalogOutV2.class);
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

    startInputVoltageGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_SET_OUTPUT_VOLTAGE) {
      buffer = setOutputVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_OUTPUT_VOLTAGE) {
      buffer = getOutputVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_INPUT_VOLTAGE) {
      buffer = getInputVoltage(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startInputVoltageGenerator() {
    if (inputVoltage_step == 0) {
      return;
    }
    vertx.setPeriodic(inputVoltage_generator_period, id -> {
      if (inputVoltage_direction == Step.UP) {
        if (inputVoltage >= inputVoltage_max) {
          inputVoltage_direction = Step.DOWN;
          this.inputVoltage = (short) (inputVoltage - inputVoltage_step);
        } else {
          this.inputVoltage = (short) (inputVoltage + inputVoltage_step);
        }
      } else {
        if (inputVoltage <= inputVoltage_min) {
          inputVoltage_direction = Step.UP;
          this.inputVoltage = (short) (inputVoltage + inputVoltage_step);
        } else {
          this.inputVoltage = (short) (inputVoltage - inputVoltage_step);
        }
      }
    });
  }
        
  private Buffer getInputVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(inputVoltage));

    return buffer;
  }
        
  private Buffer getInputVoltageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getInputVoltageBuffer(FUNCTION_GET_INPUT_VOLTAGE, options);
  }

  private Buffer getInputVoltage(Packet packet) {
    logger.debug("function getInputVoltage");
    if (packet.getResponseExpected()) {
      return getInputVoltageBuffer(packet);
    }
    return null;
  }
}
