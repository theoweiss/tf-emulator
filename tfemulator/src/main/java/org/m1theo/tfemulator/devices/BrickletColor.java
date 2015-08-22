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
import org.m1theo.tfemulator.protocol.Packet;

/**
 * Measures color (RGB value), illuminance and color temperature
 */
public class BrickletColor extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 243;
public final static String DEVICE_DISPLAY_NAME = "Color Bricklet";

  public final static byte FUNCTION_GET_COLOR = (byte)1;
  public final static byte FUNCTION_SET_COLOR_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_COLOR_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_COLOR_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_COLOR_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_COLOR = (byte)8;
  public final static byte CALLBACK_COLOR_REACHED = (byte)9;
  public final static byte FUNCTION_LIGHT_ON = (byte)10;
  public final static byte FUNCTION_LIGHT_OFF = (byte)11;
  public final static byte FUNCTION_IS_LIGHT_ON = (byte)12;
  public final static byte FUNCTION_SET_CONFIG = (byte)13;
  public final static byte FUNCTION_GET_CONFIG = (byte)14;
  public final static byte FUNCTION_GET_ILLUMINANCE = (byte)15;
  public final static byte FUNCTION_GET_COLOR_TEMPERATURE = (byte)16;
  public final static byte FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD = (byte)17;
  public final static byte FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD = (byte)18;
  public final static byte FUNCTION_SET_COLOR_TEMPERATURE_CALLBACK_PERIOD = (byte)19;
  public final static byte FUNCTION_GET_COLOR_TEMPERATURE_CALLBACK_PERIOD = (byte)20;
  public final static byte CALLBACK_ILLUMINANCE = (byte)21;
  public final static byte CALLBACK_COLOR_TEMPERATURE = (byte)22;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short LIGHT_ON = (short)0;
  public final static short LIGHT_OFF = (short)1;
  public final static short GAIN_1X = (short)0;
  public final static short GAIN_4X = (short)1;
  public final static short GAIN_16X = (short)2;
  public final static short GAIN_60X = (short)3;
  public final static short INTEGRATION_TIME_2MS = (short)0;
  public final static short INTEGRATION_TIME_24MS = (short)1;
  public final static short INTEGRATION_TIME_101MS = (short)2;
  public final static short INTEGRATION_TIME_154MS = (short)3;
  public final static short INTEGRATION_TIME_700MS = (short)4;
  String uidString;
  private Buffer colorCallbackPeriod = getColorCallbackPeriodDefault();
  private Buffer colorTemperatureCallbackPeriod = getColorTemperatureCallbackPeriodDefault();
  private Buffer config = getConfigDefault();
  private Buffer illuminanceCallbackPeriod = getIlluminanceCallbackPeriodDefault();
  private Buffer debouncePeriod = getDebouncePeriodDefault();
  private Buffer colorCallbackThreshold = getColorCallbackThresholdDefault();
  private Buffer light = isLightOnDefault();

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletColor.class);
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
    else if (functionId == FUNCTION_GET_COLOR) {
      buffer = getColor(packet);
    }
    else if (functionId == FUNCTION_SET_COLOR_CALLBACK_PERIOD) {
      buffer = setColorCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_CALLBACK_PERIOD) {
      buffer = getColorCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_COLOR_CALLBACK_THRESHOLD) {
      buffer = setColorCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_CALLBACK_THRESHOLD) {
      buffer = getColorCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_LIGHT_ON) {
      buffer = lightOn(packet);
    }
    else if (functionId == FUNCTION_LIGHT_OFF) {
      buffer = lightOff(packet);
    }
    else if (functionId == FUNCTION_IS_LIGHT_ON) {
      buffer = isLightOn(packet);
    }
    else if (functionId == FUNCTION_SET_CONFIG) {
      buffer = setConfig(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIG) {
      buffer = getConfig(packet);
    }
    else if (functionId == FUNCTION_GET_ILLUMINANCE) {
      buffer = getIlluminance(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_TEMPERATURE) {
      buffer = getColorTemperature(packet);
    }
    else if (functionId == FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = setIlluminanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = getIlluminanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_COLOR_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = setColorTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = getColorTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  /**
   * 
   */
  private Buffer getIlluminance(Packet packet) {
    logger.debug("function getIlluminance");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ILLUMINANCE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getColorTemperature(Packet packet) {
    logger.debug("function getColorTemperature");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_COLOR_TEMPERATURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getColor(Packet packet) {
    logger.debug("function getColor");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 8;
      byte functionId = FUNCTION_GET_COLOR;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getColorCallbackPeriod(Packet packet) {
    logger.debug("function getColorCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_COLOR_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.colorCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getColorCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getColorTemperatureCallbackPeriod(Packet packet) {
    logger.debug("function getColorTemperatureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_COLOR_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.colorTemperatureCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getColorTemperatureCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getConfig(Packet packet) {
    logger.debug("function getConfig");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_CONFIG;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.config);
      return buffer;
    }

    return null;
  }

  private Buffer getConfigDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
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
      buffer.appendBuffer(this.illuminanceCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getIlluminanceCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
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
  private Buffer getColorCallbackThreshold(Packet packet) {
    logger.debug("function getColorCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 17;
      byte functionId = FUNCTION_GET_COLOR_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.colorCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getColorCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer isLightOn(Packet packet) {
    logger.debug("function isLightOn");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_IS_LIGHT_ON;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.light);
      return buffer;
    }

    return null;
  }

  private Buffer isLightOnDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer lightOn(Packet packet) {
    logger.debug("function lightOn");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_LIGHT_ON;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.light = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setConfig(Packet packet) {
    logger.debug("function setConfig");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_CONFIG;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.config = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setColorTemperatureCallbackPeriod(Packet packet) {
    logger.debug("function setColorTemperatureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COLOR_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.colorTemperatureCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setColorCallbackThreshold(Packet packet) {
    logger.debug("function setColorCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COLOR_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.colorCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer lightOff(Packet packet) {
    logger.debug("function lightOff");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_LIGHT_OFF;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.light = packet.getPayload();
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
  private Buffer setIlluminanceCallbackPeriod(Packet packet) {
    logger.debug("function setIlluminanceCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.illuminanceCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setColorCallbackPeriod(Packet packet) {
    logger.debug("function setColorCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COLOR_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.colorCallbackPeriod = packet.getPayload();
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
