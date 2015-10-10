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
 * 4 galvanically isolated solid state relays
 */
public class BrickletIndustrialQuadRelay extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 225;
public final static String DEVICE_DISPLAY_NAME = "Industrial Quad Relay Bricklet";

  public final static byte FUNCTION_SET_VALUE = (byte)1;
  public final static byte FUNCTION_GET_VALUE = (byte)2;
  public final static byte FUNCTION_SET_MONOFLOP = (byte)3;
  public final static byte FUNCTION_GET_MONOFLOP = (byte)4;
  public final static byte FUNCTION_SET_GROUP = (byte)5;
  public final static byte FUNCTION_GET_GROUP = (byte)6;
  public final static byte FUNCTION_GET_AVAILABLE_FOR_GROUP = (byte)7;
  public final static byte CALLBACK_MONOFLOP_DONE = (byte)8;
  public final static byte FUNCTION_SET_SELECTED_VALUES = (byte)9;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  String uidString;

  private Buffer monoflop = getMonoflopDefault();
        
  private Buffer group = getGroupDefault();
        
  private Buffer value = getValueDefault();
        
  private byte availableForGroup = 100;
  private byte availableForGroup_max = 1000;
  private byte availableForGroup_min = 0;
  private byte availableForGroup_step = 1;
  private long availableForGroup_generator_period = 100;
  private Step availableForGroup_direction = Step.UP;
  private long availableForGroupCallbackPeriod;
  private long availableForGroup_callback_id;
  private byte availableForGroup_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletIndustrialQuadRelay.class);
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

    startAvailableForGroupGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_SET_VALUE) {
      buffer = setValue(packet);
    }
    else if (functionId == FUNCTION_GET_VALUE) {
      buffer = getValue(packet);
    }
    else if (functionId == FUNCTION_SET_MONOFLOP) {
      buffer = setMonoflop(packet);
    }
    else if (functionId == FUNCTION_GET_MONOFLOP) {
      buffer = getMonoflop(packet);
    }
    else if (functionId == FUNCTION_SET_GROUP) {
      buffer = setGroup(packet);
    }
    else if (functionId == FUNCTION_GET_GROUP) {
      buffer = getGroup(packet);
    }
    else if (functionId == FUNCTION_GET_AVAILABLE_FOR_GROUP) {
      buffer = getAvailableForGroup(packet);
    }
    else if (functionId == FUNCTION_SET_SELECTED_VALUES) {
      buffer = setSelectedValues(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startAvailableForGroupGenerator() {
    if (availableForGroup_step == 0) {
      return;
    }
    vertx.setPeriodic(availableForGroup_generator_period, id -> {
      if (availableForGroup_direction == Step.UP) {
        if (availableForGroup >= availableForGroup_max) {
          availableForGroup_direction = Step.DOWN;
          this.availableForGroup = (byte) (availableForGroup - availableForGroup_step);
        } else {
          this.availableForGroup = (byte) (availableForGroup + availableForGroup_step);
        }
      } else {
        if (availableForGroup <= availableForGroup_min) {
          availableForGroup_direction = Step.UP;
          this.availableForGroup = (byte) (availableForGroup + availableForGroup_step);
        } else {
          this.availableForGroup = (byte) (availableForGroup - availableForGroup_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor monoflopDone
//fixme stop_generator callback without sensor monoflopDone
//fixme getter callback without sensor monoflopDone

  private Buffer getAvailableForGroupBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8(availableForGroup));

    return buffer;
  }
        
  private Buffer getAvailableForGroupBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getAvailableForGroupBuffer(FUNCTION_GET_AVAILABLE_FOR_GROUP, options);
  }

  private Buffer getAvailableForGroup(Packet packet) {
    logger.debug("function getAvailableForGroup");
    if (packet.getResponseExpected()) {
      return getAvailableForGroupBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer getMonoflop(Packet packet) {
    logger.debug("function getMonoflop");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 10;
      byte functionId = FUNCTION_GET_MONOFLOP;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.monoflop);
      return buffer;
    }

    return null;
  }

  private Buffer getMonoflopDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));
      buffer.appendBytes(Utils.get4ByteURandomValue(1));
      buffer.appendBytes(Utils.get4ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer getGroup(Packet packet) {
    logger.debug("function getGroup");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_GROUP;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.group);
      return buffer;
    }

    return null;
  }

  private Buffer getGroupDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.getCharRandomValue(1));

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
      buffer.appendBuffer(this.value);
      return buffer;
    }

    return null;
  }

  private Buffer getValueDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get2ByteURandomValue(1));

      return buffer;
  }

  /**
   * 
   */
  private Buffer setGroup(Packet packet) {
    logger.debug("function setGroup");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_GROUP;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.group = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setValue(Packet packet) {
    logger.debug("function setValue");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_VALUE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.value = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setMonoflop(Packet packet) {
    logger.debug("function setMonoflop");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MONOFLOP;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.monoflop = packet.getPayload();
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
  private Buffer setSelectedValues(Packet packet) {
    //TODO dummy method
    return null;
  }
}
