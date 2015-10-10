/*
 * Copyright (c) 2015 Thomas Weiss <theo@m1theo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
/*
 * *********************************************************** This file was automatically
 * generated. * * If you have a bugfix for this file and want to commit it, * please fix the bug in
 * the emulator generator. *
 *************************************************************/

package org.m1theo.tfemulator.deviceshandmade;

import java.util.Set;

import org.m1theo.tfemulator.Brickd;
import org.m1theo.tfemulator.CommonServices;
import org.m1theo.tfemulator.Utils;
import org.m1theo.tfemulator.Utils.Step;
import org.m1theo.tfemulator.protocol.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;

/**
 * Measures ambient light up to 900lux
 */
public class BrickletAmbientLightOld extends AbstractVerticle {
  long uidBytes;
  int[] apiVersion = new int[3];
  private Logger logger;

  public final static int DEVICE_IDENTIFIER = 21;
  public final static String DEVICE_DISPLAY_NAME = "Ambient Light Bricklet";

  public final static byte FUNCTION_GET_ILLUMINANCE = (byte) 1;
  public final static byte FUNCTION_GET_ANALOG_VALUE = (byte) 2;
  public final static byte FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD = (byte) 3;
  public final static byte FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD = (byte) 4;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte) 5;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte) 6;
  public final static byte FUNCTION_SET_ILLUMINANCE_CALLBACK_THRESHOLD = (byte) 7;
  public final static byte FUNCTION_GET_ILLUMINANCE_CALLBACK_THRESHOLD = (byte) 8;
  public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte) 9;
  public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte) 10;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte) 11;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte) 12;
  public final static byte CALLBACK_ILLUMINANCE = (byte) 13;
  public final static byte CALLBACK_ANALOG_VALUE = (byte) 14;
  public final static byte CALLBACK_ILLUMINANCE_REACHED = (byte) 15;
  public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte) 16;
  public final static byte FUNCTION_GET_IDENTITY = (byte) 255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  String uidString;
  private Buffer analogValueCallbackPeriod = getAnalogValueCallbackPeriodDefault();
  private Buffer debouncePeriod = getDebouncePeriodDefault();
  private Buffer analogValueCallbackThreshold = getAnalogValueCallbackThresholdDefault();
  private Buffer illuminanceCallbackThreshold = getIlluminanceCallbackThresholdDefault();
  private Buffer illuminanceCallbackPeriod = getIlluminanceCallbackPeriodDefault();

  // Element.get_java_byte_buffer_storage_type()
  private short illuminance;
  private short illuminance_max = 100;
  private short illuminance_min = 0;
  private short illuminance_step = 1;
  private long illuminance_generator_period = 100;
  private Step illuminance_direction = Step.UP;
  private long illuminance_callback_period;
  private long illuminance_callback_id;
  private short illuminance_last_value_called_back;

  private Buffer setIlluminanceCallbackPeriod(Packet packet) {
    logger.debug("function setIlluminanceCallbackPeriod");
    illuminance_callback_period = Utils.unsignedInt(packet.getPayload());
    if (illuminance_callback_period == 0) {
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

  private void startIlluminanceGenerator() {
    if (illuminance_step == 0) {
      return;
    }
    vertx.setPeriodic(illuminance_generator_period, id -> {
      if (illuminance_direction == Step.UP) {
        if (illuminance >= illuminance_max) {
          illuminance_direction = Step.DOWN;
          this.illuminance = (short) (illuminance - illuminance_step);
        } else {
          this.illuminance = (short) (illuminance + illuminance_step);
        }
      } else {
        if (illuminance >= illuminance_min) {
          illuminance_direction = Step.UP;
          this.illuminance = (short) (illuminance + illuminance_step);
        } else {
          this.illuminance = (short) (illuminance - illuminance_step);
        }
      }
    });
  }

  private Buffer getIlluminanceCallbackBuffer() {
    byte length = (byte) 8 + 2;
    byte functionId = CALLBACK_ILLUMINANCE;
    byte flags = (byte) 0;
    byte sequenceNumberAndOptions = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, sequenceNumberAndOptions, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(illuminance));
    return buffer;
  }

  private void stopIlluminanceCallback() {
    vertx.cancelTimer(illuminance_callback_id);
  }

  private void startIlluminanceCallback() {
    illuminance_callback_id = vertx.setPeriodic(illuminance_callback_period, id -> {
      if (illuminance != illuminance_last_value_called_back) {
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("sending enumerate answer");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getIlluminanceCallbackBuffer());
          }
        } else {
          logger.info("no handlerids found in illuminance callback");
        }
      }
    });
  }

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 1;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletAmbientLightOld.class);
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
            logger.trace("sending answer: {}", new Packet(buffer).toString());
            vertx.eventBus().publish((String) handlerid, buffer);
          } else {
            logger.trace("buffer is null");
          }
        }
      }
    });

    // broadcast queue for enumeration requests
    vertx.eventBus().consumer(CommonServices.BROADCAST_UID, message -> {
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
    if (functionId == 0) {
      // TODO raise Exception or log error
    } else if (functionId == FUNCTION_GET_ILLUMINANCE) {
      buffer = getIlluminance(packet);
    } else if (functionId == FUNCTION_GET_ANALOG_VALUE) {
      buffer = getAnalogValue(packet);
    } else if (functionId == FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = setIlluminanceCallbackPeriod(packet);
    } else if (functionId == FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = getIlluminanceCallbackPeriod(packet);
    } else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = setAnalogValueCallbackPeriod(packet);
    } else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD) {
      buffer = getAnalogValueCallbackPeriod(packet);
    } else if (functionId == FUNCTION_SET_ILLUMINANCE_CALLBACK_THRESHOLD) {
      buffer = setIlluminanceCallbackThreshold(packet);
    } else if (functionId == FUNCTION_GET_ILLUMINANCE_CALLBACK_THRESHOLD) {
      buffer = getIlluminanceCallbackThreshold(packet);
    } else if (functionId == FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD) {
      buffer = setAnalogValueCallbackThreshold(packet);
    } else if (functionId == FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD) {
      buffer = getAnalogValueCallbackThreshold(packet);
    } else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    } else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    } else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    } else {
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
  private Buffer getIlluminance(Packet packet) {
    logger.debug("function getIlluminance");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 2;
      byte functionId = FUNCTION_GET_ILLUMINANCE;
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
  private Buffer getIlluminanceCallbackThreshold(Packet packet) {
    logger.debug("function getIlluminanceCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 5;
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
    buffer.appendBytes(Utils.get2ByteURandomValue(1));
    buffer.appendBytes(Utils.get2ByteURandomValue(1));
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
  private Buffer setAnalogValueCallbackThreshold(Packet packet) {
    logger.debug("function setAnalogValueCallbackThreshold");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      // TODO response expected bei settern
      return buffer;
    }
    this.analogValueCallbackThreshold = packet.getPayload();
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
      // TODO response expected bei settern
      return buffer;
    }
    this.illuminanceCallbackThreshold = packet.getPayload();
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
      // TODO response expected bei settern
      return buffer;
    }
    this.debouncePeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  // private Buffer setIlluminanceCallbackPeriod(Packet packet) {
  // logger.debug("function setIlluminanceCallbackPeriod");
  // if (packet.getResponseExpected()) {
  // byte length = (byte) 8 + 0;
  // byte functionId = FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD;
  // byte flags = (byte) 0;
  // Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
  // Buffer buffer = Buffer.buffer();
  // buffer.appendBuffer(header);
  // // TODO response expected bei settern
  // return buffer;
  // }
  // this.illuminanceCallbackPeriod = packet.getPayload();
  // return null;
  // }

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
      // TODO response expected bei settern
      return buffer;
    }
    this.analogValueCallbackPeriod = packet.getPayload();
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
