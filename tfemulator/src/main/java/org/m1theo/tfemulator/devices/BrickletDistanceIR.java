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
 * Measures distance up to 150cm with infrared light
 */
public class BrickletDistanceIR extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 25;
public final static String DEVICE_DISPLAY_NAME = "Distance IR Bricklet";

  public final static byte FUNCTION_GET_DISTANCE = (byte)1;
  public final static byte FUNCTION_GET_ANALOG_VALUE = (byte)2;
  public final static byte FUNCTION_SET_SAMPLING_POINT = (byte)3;
  public final static byte FUNCTION_GET_SAMPLING_POINT = (byte)4;
  public final static byte FUNCTION_SET_DISTANCE_CALLBACK_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_DISTANCE_CALLBACK_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)7;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)8;
  public final static byte FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD = (byte)9;
  public final static byte FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD = (byte)10;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)11;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)12;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)13;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)14;
  public final static byte CALLBACK_DISTANCE = (byte)15;
  public final static byte CALLBACK_ANALOG_VALUE = (byte)16;
  public final static byte CALLBACK_DISTANCE_REACHED = (byte)17;
  public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte)18;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;
  private Buffer analogValueCallbackPeriod = getAnalogValueCallbackPeriodDefault();
  private Buffer distanceCallbackPeriod = getDistanceCallbackPeriodDefault();
  private Buffer debouncePeriod = getDebouncePeriodDefault();
  private Buffer analogValueCallbackThreshold = getAnalogValueCallbackThresholdDefault();
  private Buffer distanceCallbackThreshold = getDistanceCallbackThresholdDefault();
  private Buffer samplingPoint = getSamplingPointDefault();

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletDistanceIR.class);
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
    else if (functionId == FUNCTION_GET_DISTANCE) {
      buffer = getDistance(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE) {
      buffer = getAnalogValue(packet);
    }
    else if (functionId == FUNCTION_SET_SAMPLING_POINT) {
      buffer = setSamplingPoint(packet);
    }
    else if (functionId == FUNCTION_GET_SAMPLING_POINT) {
      buffer = getSamplingPoint(packet);
    }
    else if (functionId == FUNCTION_SET_DISTANCE_CALLBACK_PERIOD) {
      buffer = setDistanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DISTANCE_CALLBACK_PERIOD) {
      buffer = getDistanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = setAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = getAnalogValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD) {
      buffer = setDistanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD) {
      buffer = getDistanceCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD) {
      buffer = setAnalogValueCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD) {
      buffer = getAnalogValueCallbackThreshold(packet);
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
  private Buffer getAnalogValue(Packet packet) {
    logger.debug("function getAnalogValue");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_ANALOG_VALUE;
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
  private Buffer getDistance(Packet packet) {
    logger.debug("function getDistance");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_DISTANCE;
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
  private Buffer getAnalogValueCallbackPeriod(Packet packet) {
    logger.debug("function getAnalogValueCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.analogValueCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getAnalogValueCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getDistanceCallbackPeriod(Packet packet) {
    logger.debug("function getDistanceCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_DISTANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.distanceCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getDistanceCallbackPeriodDefault() {
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
  private Buffer getAnalogValueCallbackThreshold(Packet packet) {
    logger.debug("function getAnalogValueCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.analogValueCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getAnalogValueCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getDistanceCallbackThreshold(Packet packet) {
    logger.debug("function getDistanceCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.distanceCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getDistanceCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getSamplingPoint(Packet packet) {
    logger.debug("function getSamplingPoint");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_SAMPLING_POINT;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.samplingPoint);
      return buffer;
    }

    return null;
  }

  private Buffer getSamplingPointDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer setDistanceCallbackThreshold(Packet packet) {
    logger.debug("function setDistanceCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.distanceCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAnalogValueCallbackPeriod(Packet packet) {
    logger.debug("function setAnalogValueCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.analogValueCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setDistanceCallbackPeriod(Packet packet) {
    logger.debug("function setDistanceCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DISTANCE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.distanceCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setAnalogValueCallbackThreshold(Packet packet) {
    logger.debug("function setAnalogValueCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.analogValueCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setSamplingPoint(Packet packet) {
    logger.debug("function setSamplingPoint");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_SAMPLING_POINT;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.samplingPoint = packet.getPayload();
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
