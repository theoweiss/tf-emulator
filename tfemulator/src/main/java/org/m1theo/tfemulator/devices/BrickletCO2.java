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
 * Measures CO2 concentration in ppm
 */
public class BrickletCO2 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 262;
public final static String DEVICE_DISPLAY_NAME = "CO2 Bricklet";

  public final static byte FUNCTION_GET_CO2_CONCENTRATION = (byte)1;
  public final static byte FUNCTION_SET_CO2_CONCENTRATION_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_CO2_CONCENTRATION_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_CO2_CONCENTRATION_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_CO2_CONCENTRATION_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_CO2_CONCENTRATION = (byte)8;
  public final static byte CALLBACK_CO2_CONCENTRATION_REACHED = (byte)9;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer cO2ConcentrationCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private short cO2Concentration = 100;
  private short cO2Concentration_max = 1000;
  private short cO2Concentration_min = 0;
  private short cO2Concentration_step = 1;
  private long cO2Concentration_generator_period = 100;
  private Step cO2Concentration_direction = Step.UP;
  private long cO2ConcentrationCallbackPeriod;
  private long cO2Concentration_callback_id;
  private short cO2Concentration_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletCO2.class);
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

    startCO2ConcentrationGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_CO2_CONCENTRATION) {
      buffer = getCO2Concentration(packet);
    }
    else if (functionId == FUNCTION_SET_CO2_CONCENTRATION_CALLBACK_PERIOD) {
      buffer = setCO2ConcentrationCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_CO2_CONCENTRATION_CALLBACK_PERIOD) {
      buffer = getCO2ConcentrationCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_CO2_CONCENTRATION_CALLBACK_THRESHOLD) {
      buffer = setCO2ConcentrationCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_CO2_CONCENTRATION_CALLBACK_THRESHOLD) {
      buffer = getCO2ConcentrationCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startCO2ConcentrationGenerator() {
    if (cO2Concentration_step == 0) {
      return;
    }
    vertx.setPeriodic(cO2Concentration_generator_period, id -> {
      if (cO2Concentration_direction == Step.UP) {
        if (cO2Concentration >= cO2Concentration_max) {
          cO2Concentration_direction = Step.DOWN;
          this.cO2Concentration = (short) (cO2Concentration - cO2Concentration_step);
        } else {
          this.cO2Concentration = (short) (cO2Concentration + cO2Concentration_step);
        }
      } else {
        if (cO2Concentration <= cO2Concentration_min) {
          cO2Concentration_direction = Step.UP;
          this.cO2Concentration = (short) (cO2Concentration + cO2Concentration_step);
        } else {
          this.cO2Concentration = (short) (cO2Concentration - cO2Concentration_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor co2ConcentrationReached
//fixme start_generator callback without sensor co2Concentration
//fixme stop_generator callback without sensor co2ConcentrationReached
//fixme stop_generator callback without sensor co2Concentration
//fixme getter callback without sensor co2ConcentrationReached
//fixme getter callback without sensor co2Concentration

  private Buffer setCO2ConcentrationCallbackPeriod(Packet packet) {
    cO2ConcentrationCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (cO2ConcentrationCallbackPeriod == 0) {
      stopCO2ConcentrationCallback();
    } else {
      startCO2ConcentrationCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CO2_CONCENTRATION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getCO2ConcentrationBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(cO2Concentration));

    return buffer;
  }
        
  private Buffer getCO2ConcentrationBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCO2ConcentrationBuffer(FUNCTION_GET_CO2_CONCENTRATION, options);
  }

  private Buffer getCO2Concentration(Packet packet) {
    logger.debug("function getCO2Concentration");
    if (packet.getResponseExpected()) {
      return getCO2ConcentrationBuffer(packet);
    }
    return null;
  }
}
