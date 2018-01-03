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
 * Controls up to 320 RGB LEDs
 */
public class BrickletLEDStrip extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 231;
public final static String DEVICE_DISPLAY_NAME = "LED Strip Bricklet";

  public final static byte FUNCTION_SET_RGB_VALUES = (byte)1;
  public final static byte FUNCTION_GET_RGB_VALUES = (byte)2;
  public final static byte FUNCTION_SET_FRAME_DURATION = (byte)3;
  public final static byte FUNCTION_GET_FRAME_DURATION = (byte)4;
  public final static byte FUNCTION_GET_SUPPLY_VOLTAGE = (byte)5;
  public final static byte CALLBACK_FRAME_RENDERED = (byte)6;
  public final static byte FUNCTION_SET_CLOCK_FREQUENCY = (byte)7;
  public final static byte FUNCTION_GET_CLOCK_FREQUENCY = (byte)8;
  public final static byte FUNCTION_SET_CHIP_TYPE = (byte)9;
  public final static byte FUNCTION_GET_CHIP_TYPE = (byte)10;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static int CHIP_TYPE_WS2801 = 2801;
  public final static int CHIP_TYPE_WS2811 = 2811;
  public final static int CHIP_TYPE_WS2812 = 2812;
  String uidString;

  private Buffer chipType = getChipTypeDefault();
        
  private Buffer rGBValues = getRGBValuesDefault();
        
  private Buffer frameDuration = getFrameDurationDefault();
        
  private Buffer clockFrequency = getClockFrequencyDefault();
        
  private short supplyVoltage = 100;
  private short supplyVoltage_max = 1000;
  private short supplyVoltage_min = 0;
  private short supplyVoltage_step = 1;
  private long supplyVoltage_generator_period = 100;
  private Step supplyVoltage_direction = Step.UP;
  private long supplyVoltageCallbackPeriod;
  private long supplyVoltage_callback_id;
  private short supplyVoltage_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 2;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletLEDStrip.class);
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

    startSupplyVoltageGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_SET_RGB_VALUES) {
      buffer = setRGBValues(packet);
    }
    else if (functionId == FUNCTION_GET_RGB_VALUES) {
      buffer = getRGBValues(packet);
    }
    else if (functionId == FUNCTION_SET_FRAME_DURATION) {
      buffer = setFrameDuration(packet);
    }
    else if (functionId == FUNCTION_GET_FRAME_DURATION) {
      buffer = getFrameDuration(packet);
    }
    else if (functionId == FUNCTION_GET_SUPPLY_VOLTAGE) {
      buffer = getSupplyVoltage(packet);
    }
    else if (functionId == FUNCTION_SET_CLOCK_FREQUENCY) {
      buffer = setClockFrequency(packet);
    }
    else if (functionId == FUNCTION_GET_CLOCK_FREQUENCY) {
      buffer = getClockFrequency(packet);
    }
    else if (functionId == FUNCTION_SET_CHIP_TYPE) {
      buffer = setChipType(packet);
    }
    else if (functionId == FUNCTION_GET_CHIP_TYPE) {
      buffer = getChipType(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startSupplyVoltageGenerator() {
    if (supplyVoltage_step == 0) {
      return;
    }
    vertx.setPeriodic(supplyVoltage_generator_period, id -> {
      if (supplyVoltage_direction == Step.UP) {
        if (supplyVoltage >= supplyVoltage_max) {
          supplyVoltage_direction = Step.DOWN;
          this.supplyVoltage = (short) (supplyVoltage - supplyVoltage_step);
        } else {
          this.supplyVoltage = (short) (supplyVoltage + supplyVoltage_step);
        }
      } else {
        if (supplyVoltage <= supplyVoltage_min) {
          supplyVoltage_direction = Step.UP;
          this.supplyVoltage = (short) (supplyVoltage + supplyVoltage_step);
        } else {
          this.supplyVoltage = (short) (supplyVoltage - supplyVoltage_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor frameRendered
//fixme stop_generator callback without sensor frameRendered
//fixme getter callback without sensor frameRendered

  private Buffer getSupplyVoltageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(supplyVoltage));

    return buffer;
  }
        
  private Buffer getSupplyVoltageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getSupplyVoltageBuffer(FUNCTION_GET_SUPPLY_VOLTAGE, options);
  }

  private Buffer getSupplyVoltage(Packet packet) {
    logger.debug("function getSupplyVoltage");
    if (packet.getResponseExpected()) {
      return getSupplyVoltageBuffer(packet);
    }
    return null;
  }
}
