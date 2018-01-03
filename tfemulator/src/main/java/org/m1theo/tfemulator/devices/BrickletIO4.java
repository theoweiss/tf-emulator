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
 * 4-channel digital input/output
 */
public class BrickletIO4 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 29;
public final static String DEVICE_DISPLAY_NAME = "IO-4 Bricklet";

  public final static byte FUNCTION_SET_VALUE = (byte)1;
  public final static byte FUNCTION_GET_VALUE = (byte)2;
  public final static byte FUNCTION_SET_CONFIGURATION = (byte)3;
  public final static byte FUNCTION_GET_CONFIGURATION = (byte)4;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)5;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_SET_INTERRUPT = (byte)7;
  public final static byte FUNCTION_GET_INTERRUPT = (byte)8;
  public final static byte CALLBACK_INTERRUPT = (byte)9;
  public final static byte FUNCTION_SET_MONOFLOP = (byte)10;
  public final static byte FUNCTION_GET_MONOFLOP = (byte)11;
  public final static byte CALLBACK_MONOFLOP_DONE = (byte)12;
  public final static byte FUNCTION_SET_SELECTED_VALUES = (byte)13;
  public final static byte FUNCTION_GET_EDGE_COUNT = (byte)14;
  public final static byte FUNCTION_SET_EDGE_COUNT_CONFIG = (byte)15;
  public final static byte FUNCTION_GET_EDGE_COUNT_CONFIG = (byte)16;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char DIRECTION_IN = 'i';
  public final static char DIRECTION_OUT = 'o';
  public final static short EDGE_TYPE_RISING = (short)0;
  public final static short EDGE_TYPE_FALLING = (short)1;
  public final static short EDGE_TYPE_BOTH = (short)2;
  String uidString;

  private Buffer edgeCountConfig = getEdgeCountConfigDefault();
        
  private Buffer interrupt = getInterruptDefault();
        
  private Buffer configuration = getConfigurationDefault();
        
  private Buffer monoflop = getMonoflopDefault();
        
  private Buffer value = getValueDefault();
        
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private int edgeCount = 100;
  private int edgeCount_max = 1000;
  private int edgeCount_min = 0;
  private int edgeCount_step = 1;
  private long edgeCount_generator_period = 100;
  private Step edgeCount_direction = Step.UP;
  private long edgeCountCallbackPeriod;
  private long edgeCount_callback_id;
  private int edgeCount_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 1;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletIO4.class);
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

    startEdgeCountGenerator();
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
    else if (functionId == FUNCTION_SET_CONFIGURATION) {
      buffer = setConfiguration(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIGURATION) {
      buffer = getConfiguration(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_SET_INTERRUPT) {
      buffer = setInterrupt(packet);
    }
    else if (functionId == FUNCTION_GET_INTERRUPT) {
      buffer = getInterrupt(packet);
    }
    else if (functionId == FUNCTION_SET_MONOFLOP) {
      buffer = setMonoflop(packet);
    }
    else if (functionId == FUNCTION_GET_MONOFLOP) {
      buffer = getMonoflop(packet);
    }
    else if (functionId == FUNCTION_SET_SELECTED_VALUES) {
      buffer = setSelectedValues(packet);
    }
    else if (functionId == FUNCTION_GET_EDGE_COUNT) {
      buffer = getEdgeCount(packet);
    }
    else if (functionId == FUNCTION_SET_EDGE_COUNT_CONFIG) {
      buffer = setEdgeCountConfig(packet);
    }
    else if (functionId == FUNCTION_GET_EDGE_COUNT_CONFIG) {
      buffer = getEdgeCountConfig(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startEdgeCountGenerator() {
    if (edgeCount_step == 0) {
      return;
    }
    vertx.setPeriodic(edgeCount_generator_period, id -> {
      if (edgeCount_direction == Step.UP) {
        if (edgeCount >= edgeCount_max) {
          edgeCount_direction = Step.DOWN;
          this.edgeCount = (int) (edgeCount - edgeCount_step);
        } else {
          this.edgeCount = (int) (edgeCount + edgeCount_step);
        }
      } else {
        if (edgeCount <= edgeCount_min) {
          edgeCount_direction = Step.UP;
          this.edgeCount = (int) (edgeCount + edgeCount_step);
        } else {
          this.edgeCount = (int) (edgeCount - edgeCount_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor interrupt
//fixme start_generator callback without sensor monoflopDone
//fixme stop_generator callback without sensor interrupt
//fixme stop_generator callback without sensor monoflopDone
//fixme getter callback without sensor interrupt
//fixme getter callback without sensor monoflopDone

  private Buffer getEdgeCountBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(edgeCount));

    return buffer;
  }
        
  private Buffer getEdgeCountBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getEdgeCountBuffer(FUNCTION_GET_EDGE_COUNT, options);
  }

  private Buffer getEdgeCount(Packet packet) {
    logger.debug("function getEdgeCount");
    if (packet.getResponseExpected()) {
      return getEdgeCountBuffer(packet);
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
