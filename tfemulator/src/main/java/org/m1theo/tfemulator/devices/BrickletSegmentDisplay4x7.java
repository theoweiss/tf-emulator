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
 * Four 7-segment displays with switchable colon
 */
public class BrickletSegmentDisplay4x7 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 237;
public final static String DEVICE_DISPLAY_NAME = "Segment Display 4x7 Bricklet";

  public final static byte FUNCTION_SET_SEGMENTS = (byte)1;
  public final static byte FUNCTION_GET_SEGMENTS = (byte)2;
  public final static byte FUNCTION_START_COUNTER = (byte)3;
  public final static byte FUNCTION_GET_COUNTER_VALUE = (byte)4;
  public final static byte CALLBACK_COUNTER_FINISHED = (byte)5;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  String uidString;

  private Buffer segments = getSegmentsDefault();
        
  private short counterValue = 100;
  private short counterValue_max = 1000;
  private short counterValue_min = 0;
  private short counterValue_step = 1;
  private long counterValue_generator_period = 100;
  private Step counterValue_direction = Step.UP;
  private long counterValueCallbackPeriod;
  private long counterValue_callback_id;
  private short counterValue_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletSegmentDisplay4x7.class);
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

    startCounterValueGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_SET_SEGMENTS) {
      buffer = setSegments(packet);
    }
    else if (functionId == FUNCTION_GET_SEGMENTS) {
      buffer = getSegments(packet);
    }
    else if (functionId == FUNCTION_START_COUNTER) {
      buffer = startCounter(packet);
    }
    else if (functionId == FUNCTION_GET_COUNTER_VALUE) {
      buffer = getCounterValue(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startCounterValueGenerator() {
    if (counterValue_step == 0) {
      return;
    }
    vertx.setPeriodic(counterValue_generator_period, id -> {
      if (counterValue_direction == Step.UP) {
        if (counterValue >= counterValue_max) {
          counterValue_direction = Step.DOWN;
          this.counterValue = (short) (counterValue - counterValue_step);
        } else {
          this.counterValue = (short) (counterValue + counterValue_step);
        }
      } else {
        if (counterValue <= counterValue_min) {
          counterValue_direction = Step.UP;
          this.counterValue = (short) (counterValue + counterValue_step);
        } else {
          this.counterValue = (short) (counterValue - counterValue_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor counterFinished
//fixme stop_generator callback without sensor counterFinished
//fixme getter callback without sensor counterFinished

  private Buffer getCounterValueBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(counterValue));

    return buffer;
  }
        
  private Buffer getCounterValueBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getCounterValueBuffer(FUNCTION_GET_COUNTER_VALUE, options);
  }

  private Buffer getCounterValue(Packet packet) {
    logger.debug("function getCounterValue");
    if (packet.getResponseExpected()) {
      return getCounterValueBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer startCounter(Packet packet) {
    //TODO dummy method
    return null;
  }
}
