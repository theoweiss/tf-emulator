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
 * Measures contactless object temperature between -70°C and +380°C
 */
public class BrickletTemperatureIR extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 217;
public final static String DEVICE_DISPLAY_NAME = "Temperature IR Bricklet";

  public final static byte FUNCTION_GET_AMBIENT_TEMPERATURE = (byte)1;
  public final static byte FUNCTION_GET_OBJECT_TEMPERATURE = (byte)2;
  public final static byte FUNCTION_SET_EMISSIVITY = (byte)3;
  public final static byte FUNCTION_GET_EMISSIVITY = (byte)4;
  public final static byte FUNCTION_SET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_OBJECT_TEMPERATURE_CALLBACK_PERIOD = (byte)7;
  public final static byte FUNCTION_GET_OBJECT_TEMPERATURE_CALLBACK_PERIOD = (byte)8;
  public final static byte FUNCTION_SET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)11;
  public final static byte FUNCTION_GET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD = (byte)12;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)13;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)14;
  public final static byte CALLBACK_AMBIENT_TEMPERATURE = (byte)15;
  public final static byte CALLBACK_OBJECT_TEMPERATURE = (byte)16;
  public final static byte CALLBACK_AMBIENT_TEMPERATURE_REACHED = (byte)17;
  public final static byte CALLBACK_OBJECT_TEMPERATURE_REACHED = (byte)18;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;
  private Buffer ambientTemperatureCallbackPeriod = getAmbientTemperatureCallbackPeriodDefault();
  private Buffer objectTemperatureCallbackThreshold = getObjectTemperatureCallbackThresholdDefault();
  private Buffer emissivity = getEmissivityDefault();
  private Buffer objectTemperatureCallbackPeriod = getObjectTemperatureCallbackPeriodDefault();
  private Buffer ambientTemperatureCallbackThreshold = getAmbientTemperatureCallbackThresholdDefault();
  private Buffer debouncePeriod = getDebouncePeriodDefault();

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletTemperatureIR.class);
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
    else if (functionId == FUNCTION_GET_AMBIENT_TEMPERATURE) {
      buffer = getAmbientTemperature(packet);
    }
    else if (functionId == FUNCTION_GET_OBJECT_TEMPERATURE) {
      buffer = getObjectTemperature(packet);
    }
    else if (functionId == FUNCTION_SET_EMISSIVITY) {
      buffer = setEmissivity(packet);
    }
    else if (functionId == FUNCTION_GET_EMISSIVITY) {
      buffer = getEmissivity(packet);
    }
    else if (functionId == FUNCTION_SET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = setAmbientTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = getAmbientTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_OBJECT_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = setObjectTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_OBJECT_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = getObjectTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = setAmbientTemperatureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = getAmbientTemperatureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = setObjectTemperatureCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD) {
      buffer = getObjectTemperatureCallbackThreshold(packet);
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


  /**
   * 
   */
  private Buffer getObjectTemperature(Packet packet) {
    logger.debug("function getObjectTemperature");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_OBJECT_TEMPERATURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAmbientTemperature(Packet packet) {
    logger.debug("function getAmbientTemperature");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_AMBIENT_TEMPERATURE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAmbientTemperatureCallbackPeriod(Packet packet) {
    logger.debug("function getAmbientTemperatureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.ambientTemperatureCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getAmbientTemperatureCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getObjectTemperatureCallbackThreshold(Packet packet) {
    logger.debug("function getObjectTemperatureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.objectTemperatureCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getObjectTemperatureCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getEmissivity(Packet packet) {
    logger.debug("function getEmissivity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_EMISSIVITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.emissivity);
      return buffer;
    }

    return null;
  }

  private Buffer getEmissivityDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getObjectTemperatureCallbackPeriod(Packet packet) {
    logger.debug("function getObjectTemperatureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_OBJECT_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.objectTemperatureCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getObjectTemperatureCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getAmbientTemperatureCallbackThreshold(Packet packet) {
    logger.debug("function getAmbientTemperatureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.ambientTemperatureCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getAmbientTemperatureCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteRandomValue(1));        
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
  private Buffer setObjectTemperatureCallbackThreshold(Packet packet) {
    logger.debug("function setObjectTemperatureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_OBJECT_TEMPERATURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.objectTemperatureCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setObjectTemperatureCallbackPeriod(Packet packet) {
    logger.debug("function setObjectTemperatureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_OBJECT_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.objectTemperatureCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAmbientTemperatureCallbackThreshold(Packet packet) {
    logger.debug("function setAmbientTemperatureCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_AMBIENT_TEMPERATURE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.ambientTemperatureCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAmbientTemperatureCallbackPeriod(Packet packet) {
    logger.debug("function setAmbientTemperatureCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.ambientTemperatureCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setEmissivity(Packet packet) {
    logger.debug("function setEmissivity");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_EMISSIVITY;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.emissivity = packet.getPayload();
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
