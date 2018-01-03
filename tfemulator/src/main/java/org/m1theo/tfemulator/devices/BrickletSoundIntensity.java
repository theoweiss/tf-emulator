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
 * Measures sound intensity
 */
public class BrickletSoundIntensity extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 238;
public final static String DEVICE_DISPLAY_NAME = "Sound Intensity Bricklet";

  public final static byte FUNCTION_GET_INTENSITY = (byte)1;
  public final static byte FUNCTION_SET_INTENSITY_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_INTENSITY_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_INTENSITY_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_INTENSITY_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_INTENSITY = (byte)8;
  public final static byte CALLBACK_INTENSITY_REACHED = (byte)9;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;

  private Buffer intensityCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private short intensity = 100;
  private short intensity_max = 1000;
  private short intensity_min = 0;
  private short intensity_step = 1;
  private long intensity_generator_period = 100;
  private Step intensity_direction = Step.UP;
  private long intensityCallbackPeriod;
  private long intensity_callback_id;
  private short intensity_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletSoundIntensity.class);
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

    startIntensityGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_INTENSITY) {
      buffer = getIntensity(packet);
    }
    else if (functionId == FUNCTION_SET_INTENSITY_CALLBACK_PERIOD) {
      buffer = setIntensityCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_INTENSITY_CALLBACK_PERIOD) {
      buffer = getIntensityCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_INTENSITY_CALLBACK_THRESHOLD) {
      buffer = setIntensityCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_INTENSITY_CALLBACK_THRESHOLD) {
      buffer = getIntensityCallbackThreshold(packet);
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


  private void startIntensityGenerator() {
    if (intensity_step == 0) {
      return;
    }
    vertx.setPeriodic(intensity_generator_period, id -> {
      if (intensity_direction == Step.UP) {
        if (intensity >= intensity_max) {
          intensity_direction = Step.DOWN;
          this.intensity = (short) (intensity - intensity_step);
        } else {
          this.intensity = (short) (intensity + intensity_step);
        }
      } else {
        if (intensity <= intensity_min) {
          intensity_direction = Step.UP;
          this.intensity = (short) (intensity + intensity_step);
        } else {
          this.intensity = (short) (intensity - intensity_step);
        }
      }
    });
  }
        
    private void stopIntensityCallback() {
        vertx.cancelTimer(intensity_callback_id);
  }
        //fixme start_generator callback without sensor intensity
//fixme start_generator callback without sensor intensityReached

  private void startIntensityCallback() {
    logger.trace("intensityCallbackPeriod is {}", intensityCallbackPeriod);
    intensity_callback_id = vertx.setPeriodic(intensityCallbackPeriod, id -> {
      if (intensity != intensity_last_value_called_back) {
        intensity_last_value_called_back = intensity;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("intensity sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getIntensity4Callback());
          }
        } else {
          logger.info("no handlerids found in intensity callback");
        }
      } else {
        logger.debug("intensity value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor intensity
//fixme stop_generator callback without sensor intensityReached

  private Buffer getIntensity4Callback() {
      byte options = (byte) 0;
      return getIntensityBuffer(CALLBACK_INTENSITY, options);
  }
        //fixme getter callback without sensor intensity
//fixme getter callback without sensor intensityReached

  private Buffer setIntensityCallbackPeriod(Packet packet) {
    intensityCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (intensityCallbackPeriod == 0) {
      stopIntensityCallback();
    } else {
      startIntensityCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_INTENSITY_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getIntensityBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(intensity));

    return buffer;
  }
        
  private Buffer getIntensityBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIntensityBuffer(FUNCTION_GET_INTENSITY, options);
  }

  private Buffer getIntensity(Packet packet) {
    logger.debug("function getIntensity");
    if (packet.getResponseExpected()) {
      return getIntensityBuffer(packet);
    }
    return null;
  }
}
