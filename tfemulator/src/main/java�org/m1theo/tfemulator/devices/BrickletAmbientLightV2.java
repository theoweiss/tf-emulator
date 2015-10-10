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
 * Measures ambient light up to 64000lux
 */
public class BrickletAmbientLightV2 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 259;
public final static String DEVICE_DISPLAY_NAME = "Ambient Light Bricklet 2.0";

  public final static byte FUNCTION_GET_ILLUMINANCE = (byte)1;
  public final static byte FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_ILLUMINANCE_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_ILLUMINANCE_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte FUNCTION_SET_CONFIGURATION = (byte)8;
  public final static byte FUNCTION_GET_CONFIGURATION = (byte)9;
  public final static byte CALLBACK_ILLUMINANCE = (byte)10;
  public final static byte CALLBACK_ILLUMINANCE_REACHED = (byte)11;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short ILLUMINANCE_RANGE_64000LUX = (short)0;
  public final static short ILLUMINANCE_RANGE_32000LUX = (short)1;
  public final static short ILLUMINANCE_RANGE_16000LUX = (short)2;
  public final static short ILLUMINANCE_RANGE_8000LUX = (short)3;
  public final static short ILLUMINANCE_RANGE_1300LUX = (short)4;
  public final static short ILLUMINANCE_RANGE_600LUX = (short)5;
  public final static short INTEGRATION_TIME_50MS = (short)0;
  public final static short INTEGRATION_TIME_100MS = (short)1;
  public final static short INTEGRATION_TIME_150MS = (short)2;
  public final static short INTEGRATION_TIME_200MS = (short)3;
  public final static short INTEGRATION_TIME_250MS = (short)4;
  public final static short INTEGRATION_TIME_300MS = (short)5;
  public final static short INTEGRATION_TIME_350MS = (short)6;
  public final static short INTEGRATION_TIME_400MS = (short)7;
  String uidString;

  private Buffer configuration = getConfigurationDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private Buffer illuminanceCallbackThreshold = getIlluminanceCallbackThresholdDefault();
        
  private int illuminance = 100;
  private int illuminance_max = 1000;
  private int illuminance_min = 0;
  private int illuminance_step = 1;
  private long illuminance_generator_period = 100;
  private Step illuminance_direction = Step.UP;
  private long illuminanceCallbackPeriod;
  private long illuminance_callback_id;
  private int illuminance_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletAmbientLightV2.class);
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

    startIlluminanceGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_ILLUMINANCE) {
      buffer = getIlluminance(packet);
    }
    else if (functionId == FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = setIlluminanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = getIlluminanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ILLUMINANCE_CALLBACK_THRESHOLD) {
      buffer = setIlluminanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_ILLUMINANCE_CALLBACK_THRESHOLD) {
      buffer = getIlluminanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
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


  private void startIlluminanceGenerator() {
    if (illuminance_step == 0) {
      return;
    }
    vertx.setPeriodic(illuminance_generator_period, id -> {
      if (illuminance_direction == Step.UP) {
        if (illuminance >= illuminance_max) {
          illuminance_direction = Step.DOWN;
          this.illuminance = (int) (illuminance - illuminance_step);
        } else {
          this.illuminance = (int) (illuminance + illuminance_step);
        }
      } else {
        if (illuminance <= illuminance_min) {
          illuminance_direction = Step.UP;
          this.illuminance = (int) (illuminance + illuminance_step);
        } else {
          this.illuminance = (int) (illuminance - illuminance_step);
        }
      }
    });
  }
        
    private void stopIlluminanceCallback() {
        vertx.cancelTimer(illuminance_callback_id);
  }
        //fixme start_generator callback without sensor illuminance
//fixme start_generator callback without sensor illuminanceReached

  private void startIlluminanceCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    illuminance_callback_id = vertx.setPeriodic(illuminanceCallbackPeriod, id -> {
      if (illuminance != illuminance_last_value_called_back) {
        illuminance_last_value_called_back = illuminance;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("illuminance sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getIlluminance4Callback());
          }
        } else {
          logger.info("no handlerids found in illuminance callback");
        }
      } else {
        logger.debug("illuminance value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor illuminance
//fixme stop_generator callback without sensor illuminanceReached

  private Buffer getIlluminance4Callback() {
      byte options = (byte) 0;
      return getIlluminanceBuffer(CALLBACK_ILLUMINANCE, options);
  }
        //fixme getter callback without sensor illuminance
//fixme getter callback without sensor illuminanceReached

  private Buffer setIlluminanceCallbackPeriod(Packet packet) {
    illuminanceCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (illuminanceCallbackPeriod == 0) {
      stopIlluminanceCallback();
    } else {
      startIlluminanceCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getIlluminanceBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(illuminance));

    return buffer;
  }
        
  private Buffer getIlluminanceBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIlluminanceBuffer(FUNCTION_GET_ILLUMINANCE, options);
  }

  private Buffer getIlluminance(Packet packet) {
    logger.debug("function getIlluminance");
    if (packet.getResponseExpected()) {
      return getIlluminanceBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getConfiguration(Packet packet) {
    logger.debug("function getConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.configuration);
      return buffer;
    }

    return null;
  }

  private Buffer getConfigurationDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));
      buffer.appendBytes(Utils.get1ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getDebouncePeriod(Packet packet) {
    logger.debug("function getDebouncePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_DEBOUNCE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.debouncePeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getDebouncePeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getIlluminanceCallbackThreshold(Packet packet) {
    logger.debug("function getIlluminanceCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 9;
      byte functionId = FUNCTION_GET_ILLUMINANCE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.illuminanceCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getIlluminanceCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));
      buffer.appendBytes(Utils.get4ByteURandomValue(1));
      buffer.appendBytes(Utils.get4ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getIlluminanceCallbackPeriod(Packet packet) {
    logger.debug("function getIlluminanceCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(illuminanceCallbackPeriod));

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer setIlluminanceCallbackThreshold(Packet packet) {
    logger.debug("function setIlluminanceCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ILLUMINANCE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.illuminanceCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setConfiguration(Packet packet) {
    logger.debug("function setConfiguration");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CONFIGURATION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.configuration = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setDebouncePeriod(Packet packet) {
    logger.debug("function setDebouncePeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DEBOUNCE_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.debouncePeriod = packet.getPayload();
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
