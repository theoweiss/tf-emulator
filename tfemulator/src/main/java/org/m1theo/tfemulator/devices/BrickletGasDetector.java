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
 * Measures concentration of different gases
 */
public class BrickletGasDetector extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 252;
public final static String DEVICE_DISPLAY_NAME = "Gas Detector Bricklet";

  public final static byte FUNCTION_GET_VALUE = (byte)1;
  public final static byte FUNCTION_SET_VALUE_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_VALUE_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_VALUE_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_VALUE_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte FUNCTION_SET_MOVING_AVERAGE = (byte)8;
  public final static byte FUNCTION_GET_MOVING_AVERAGE = (byte)9;
  public final static byte FUNCTION_SET_DETECTOR_TYPE = (byte)10;
  public final static byte FUNCTION_GET_DETECTOR_TYPE = (byte)11;
  public final static byte FUNCTION_HEATER_ON = (byte)12;
  public final static byte FUNCTION_HEATER_OFF = (byte)13;
  public final static byte FUNCTION_IS_HEATER_ON = (byte)14;
  public final static byte CALLBACK_VALUE = (byte)15;
  public final static byte CALLBACK_VALUE_REACHED = (byte)16;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short DETECTOR_TYPE_0 = (short)0;
  public final static short DETECTOR_TYPE_1 = (short)1;
  String uidString;
  private Buffer detectorType = getDetectorTypeDefault();
  private Buffer debouncePeriod = getDebouncePeriodDefault();
  private Buffer movingAverage = getMovingAverageDefault();
  private Buffer valueCallbackPeriod = getValueCallbackPeriodDefault();
  private Buffer valueCallbackThreshold = getValueCallbackThresholdDefault();

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletGasDetector.class);
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
    else if (functionId == FUNCTION_GET_VALUE) {
      buffer = getValue(packet);
    }
    else if (functionId == FUNCTION_SET_VALUE_CALLBACK_PERIOD) {
      buffer = setValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_VALUE_CALLBACK_PERIOD) {
      buffer = getValueCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_VALUE_CALLBACK_THRESHOLD) {
      buffer = setValueCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_VALUE_CALLBACK_THRESHOLD) {
      buffer = getValueCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_MOVING_AVERAGE) {
      buffer = setMovingAverage(packet);
    }
    else if (functionId == FUNCTION_GET_MOVING_AVERAGE) {
      buffer = getMovingAverage(packet);
    }
    else if (functionId == FUNCTION_SET_DETECTOR_TYPE) {
      buffer = setDetectorType(packet);
    }
    else if (functionId == FUNCTION_GET_DETECTOR_TYPE) {
      buffer = getDetectorType(packet);
    }
    else if (functionId == FUNCTION_HEATER_ON) {
      buffer = heaterOn(packet);
    }
    else if (functionId == FUNCTION_HEATER_OFF) {
      buffer = heaterOff(packet);
    }
    else if (functionId == FUNCTION_IS_HEATER_ON) {
      buffer = isHeaterOn(packet);
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
  private Buffer getValue(Packet packet) {
    logger.debug("function getValue");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_VALUE;
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
  private Buffer getDetectorType(Packet packet) {
    logger.debug("function getDetectorType");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_DETECTOR_TYPE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.detectorType);
      return buffer;
    }

    return null;
  }

  private Buffer getDetectorTypeDefault() {
      Buffer buffer = Buffer.buffer();
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
  private Buffer getMovingAverage(Packet packet) {
    logger.debug("function getMovingAverage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 1;
      byte functionId = FUNCTION_GET_MOVING_AVERAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.movingAverage);
      return buffer;
    }

    return null;
  }

  private Buffer getMovingAverageDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getValueCallbackPeriod(Packet packet) {
    logger.debug("function getValueCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_VALUE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.valueCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getValueCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getValueCallbackThreshold(Packet packet) {
    logger.debug("function getValueCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
      byte functionId = FUNCTION_GET_VALUE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.valueCallbackThreshold);
      return buffer;
    }

    return null;
  }

  private Buffer getValueCallbackThresholdDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer setValueCallbackPeriod(Packet packet) {
    logger.debug("function setValueCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_VALUE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.valueCallbackPeriod = packet.getPayload();
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
  private Buffer setDetectorType(Packet packet) {
    logger.debug("function setDetectorType");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DETECTOR_TYPE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.detectorType = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setValueCallbackThreshold(Packet packet) {
    logger.debug("function setValueCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_VALUE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.valueCallbackThreshold = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setMovingAverage(Packet packet) {
    logger.debug("function setMovingAverage");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MOVING_AVERAGE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.movingAverage = packet.getPayload();
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

  /**
   * 
   */
  private Buffer heaterOn(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isHeaterOn(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer heaterOff(Packet packet) {
    //TODO dummy method
    return null;
  }
}
