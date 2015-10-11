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

  private Buffer emissivity = getEmissivityDefault();
        
  private Buffer ambientTemperatureCallbackThreshold = getAmbientTemperatureCallbackThresholdDefault();
        
  private Buffer objectTemperatureCallbackThreshold = getObjectTemperatureCallbackThresholdDefault();
        
  private Buffer debouncePeriod = getDebouncePeriodDefault();
        
  private short objectTemperature = 100;
  private short objectTemperature_max = 1000;
  private short objectTemperature_min = 0;
  private short objectTemperature_step = 1;
  private long objectTemperature_generator_period = 100;
  private Step objectTemperature_direction = Step.UP;
  private long objectTemperatureCallbackPeriod;
  private long objectTemperature_callback_id;
  private short objectTemperature_last_value_called_back;

  private short ambientTemperature = 100;
  private short ambientTemperature_max = 1000;
  private short ambientTemperature_min = 0;
  private short ambientTemperature_step = 1;
  private long ambientTemperature_generator_period = 100;
  private Step ambientTemperature_direction = Step.UP;
  private long ambientTemperatureCallbackPeriod;
  private long ambientTemperature_callback_id;
  private short ambientTemperature_last_value_called_back;

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

    startObjectTemperatureGenerator();
    startAmbientTemperatureGenerator();
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


  private void startObjectTemperatureGenerator() {
    if (objectTemperature_step == 0) {
      return;
    }
    vertx.setPeriodic(objectTemperature_generator_period, id -> {
      if (objectTemperature_direction == Step.UP) {
        if (objectTemperature >= objectTemperature_max) {
          objectTemperature_direction = Step.DOWN;
          this.objectTemperature = (short) (objectTemperature - objectTemperature_step);
        } else {
          this.objectTemperature = (short) (objectTemperature + objectTemperature_step);
        }
      } else {
        if (objectTemperature <= objectTemperature_min) {
          objectTemperature_direction = Step.UP;
          this.objectTemperature = (short) (objectTemperature + objectTemperature_step);
        } else {
          this.objectTemperature = (short) (objectTemperature - objectTemperature_step);
        }
      }
    });
  }
        
  private void startAmbientTemperatureGenerator() {
    if (ambientTemperature_step == 0) {
      return;
    }
    vertx.setPeriodic(ambientTemperature_generator_period, id -> {
      if (ambientTemperature_direction == Step.UP) {
        if (ambientTemperature >= ambientTemperature_max) {
          ambientTemperature_direction = Step.DOWN;
          this.ambientTemperature = (short) (ambientTemperature - ambientTemperature_step);
        } else {
          this.ambientTemperature = (short) (ambientTemperature + ambientTemperature_step);
        }
      } else {
        if (ambientTemperature <= ambientTemperature_min) {
          ambientTemperature_direction = Step.UP;
          this.ambientTemperature = (short) (ambientTemperature + ambientTemperature_step);
        } else {
          this.ambientTemperature = (short) (ambientTemperature - ambientTemperature_step);
        }
      }
    });
  }
        
    private void stopObjectTemperatureCallback() {
        vertx.cancelTimer(objectTemperature_callback_id);
  }
        //fixme start_generator callback without sensor objectTemperature
//fixme start_generator callback without sensor ambientTemperatureReached

    private void stopAmbientTemperatureCallback() {
        vertx.cancelTimer(ambientTemperature_callback_id);
  }
        //fixme start_generator callback without sensor ambientTemperature
//fixme start_generator callback without sensor objectTemperatureReached

  private void startObjectTemperatureCallback() {
    logger.trace("objectTemperatureCallbackPeriod is {}", objectTemperatureCallbackPeriod);
    objectTemperature_callback_id = vertx.setPeriodic(objectTemperatureCallbackPeriod, id -> {
      if (objectTemperature != objectTemperature_last_value_called_back) {
        objectTemperature_last_value_called_back = objectTemperature;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("objectTemperature sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getObjectTemperature4Callback());
          }
        } else {
          logger.info("no handlerids found in objectTemperature callback");
        }
      } else {
        logger.debug("objectTemperature value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor objectTemperature
//fixme stop_generator callback without sensor ambientTemperatureReached

  private void startAmbientTemperatureCallback() {
    logger.trace("ambientTemperatureCallbackPeriod is {}", ambientTemperatureCallbackPeriod);
    ambientTemperature_callback_id = vertx.setPeriodic(ambientTemperatureCallbackPeriod, id -> {
      if (ambientTemperature != ambientTemperature_last_value_called_back) {
        ambientTemperature_last_value_called_back = ambientTemperature;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("ambientTemperature sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getAmbientTemperature4Callback());
          }
        } else {
          logger.info("no handlerids found in ambientTemperature callback");
        }
      } else {
        logger.debug("ambientTemperature value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor ambientTemperature
//fixme stop_generator callback without sensor objectTemperatureReached

  private Buffer getObjectTemperature4Callback() {
      byte options = (byte) 0;
      return getObjectTemperatureBuffer(CALLBACK_OBJECT_TEMPERATURE, options);
  }
        //fixme getter callback without sensor objectTemperature
//fixme getter callback without sensor ambientTemperatureReached

  private Buffer getAmbientTemperature4Callback() {
      byte options = (byte) 0;
      return getAmbientTemperatureBuffer(CALLBACK_AMBIENT_TEMPERATURE, options);
  }
        //fixme getter callback without sensor ambientTemperature
//fixme getter callback without sensor objectTemperatureReached

  private Buffer setAmbientTemperatureCallbackPeriod(Packet packet) {
    ambientTemperatureCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (ambientTemperatureCallbackPeriod == 0) {
      stopAmbientTemperatureCallback();
    } else {
      startAmbientTemperatureCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_AMBIENT_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setObjectTemperatureCallbackPeriod(Packet packet) {
    objectTemperatureCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (objectTemperatureCallbackPeriod == 0) {
      stopObjectTemperatureCallback();
    } else {
      startObjectTemperatureCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_OBJECT_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getAmbientTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(ambientTemperature));

    return buffer;
  }
        
  private Buffer getAmbientTemperatureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAmbientTemperatureBuffer(FUNCTION_GET_AMBIENT_TEMPERATURE, options);
  }

  private Buffer getAmbientTemperature(Packet packet) {
    logger.debug("function getAmbientTemperature");
    if (packet.getResponseExpected()) {
      return getAmbientTemperatureBuffer(packet);
    }
    return null;
  }

  private Buffer getObjectTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(objectTemperature));

    return buffer;
  }
        
  private Buffer getObjectTemperatureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getObjectTemperatureBuffer(FUNCTION_GET_OBJECT_TEMPERATURE, options);
  }

  private Buffer getObjectTemperature(Packet packet) {
    logger.debug("function getObjectTemperature");
    if (packet.getResponseExpected()) {
      return getObjectTemperatureBuffer(packet);
    }
    return null;
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
    buffer.appendBytes(Utils.getUInt32(objectTemperatureCallbackPeriod));

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
    buffer.appendBytes(Utils.getUInt32(ambientTemperatureCallbackPeriod));

      return buffer;
    }

    return null;
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
