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
 * Reads and writes NFC and RFID tags
 */
public class BrickletNFCRFID extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 246;
public final static String DEVICE_DISPLAY_NAME = "NFC/RFID Bricklet";

  public final static byte FUNCTION_REQUEST_TAG_ID = (byte)1;
  public final static byte FUNCTION_GET_TAG_ID = (byte)2;
  public final static byte FUNCTION_GET_STATE = (byte)3;
  public final static byte FUNCTION_AUTHENTICATE_MIFARE_CLASSIC_PAGE = (byte)4;
  public final static byte FUNCTION_WRITE_PAGE = (byte)5;
  public final static byte FUNCTION_REQUEST_PAGE = (byte)6;
  public final static byte FUNCTION_GET_PAGE = (byte)7;
  public final static byte CALLBACK_STATE_CHANGED = (byte)8;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short TAG_TYPE_MIFARE_CLASSIC = (short)0;
  public final static short TAG_TYPE_TYPE1 = (short)1;
  public final static short TAG_TYPE_TYPE2 = (short)2;
  public final static short STATE_INITIALIZATION = (short)0;
  public final static short STATE_IDLE = (short)128;
  public final static short STATE_ERROR = (short)192;
  public final static short STATE_REQUEST_TAG_ID = (short)2;
  public final static short STATE_REQUEST_TAG_ID_READY = (short)130;
  public final static short STATE_REQUEST_TAG_ID_ERROR = (short)194;
  public final static short STATE_AUTHENTICATING_MIFARE_CLASSIC_PAGE = (short)3;
  public final static short STATE_AUTHENTICATING_MIFARE_CLASSIC_PAGE_READY = (short)131;
  public final static short STATE_AUTHENTICATING_MIFARE_CLASSIC_PAGE_ERROR = (short)195;
  public final static short STATE_WRITE_PAGE = (short)4;
  public final static short STATE_WRITE_PAGE_READY = (short)132;
  public final static short STATE_WRITE_PAGE_ERROR = (short)196;
  public final static short STATE_REQUEST_PAGE = (short)5;
  public final static short STATE_REQUEST_PAGE_READY = (short)133;
  public final static short STATE_REQUEST_PAGE_ERROR = (short)197;
  public final static short KEY_A = (short)0;
  public final static short KEY_B = (short)1;
  String uidString;

  private byte state = 100;
  private byte state_max = 1000;
  private byte state_min = 0;
  private byte state_step = 1;
  private long state_generator_period = 100;
  private Step state_direction = Step.UP;
  private long stateCallbackPeriod;
  private long state_callback_id;
  private byte state_last_value_called_back;

  private byte page = 100;
  private byte page_max = 1000;
  private byte page_min = 0;
  private byte page_step = 1;
  private long page_generator_period = 100;
  private Step page_direction = Step.UP;
  private long pageCallbackPeriod;
  private long page_callback_id;
  private byte page_last_value_called_back;

  private byte tagID = 100;
  private byte tagID_max = 1000;
  private byte tagID_min = 0;
  private byte tagID_step = 1;
  private long tagID_generator_period = 100;
  private Step tagID_direction = Step.UP;
  private long tagIDCallbackPeriod;
  private long tagID_callback_id;
  private byte tagID_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletNFCRFID.class);
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

    startStateGenerator();
    startPageGenerator();
    startTagIDGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_REQUEST_TAG_ID) {
      buffer = requestTagID(packet);
    }
    else if (functionId == FUNCTION_GET_TAG_ID) {
      buffer = getTagID(packet);
    }
    else if (functionId == FUNCTION_GET_STATE) {
      buffer = getState(packet);
    }
    else if (functionId == FUNCTION_AUTHENTICATE_MIFARE_CLASSIC_PAGE) {
      buffer = authenticateMifareClassicPage(packet);
    }
    else if (functionId == FUNCTION_WRITE_PAGE) {
      buffer = writePage(packet);
    }
    else if (functionId == FUNCTION_REQUEST_PAGE) {
      buffer = requestPage(packet);
    }
    else if (functionId == FUNCTION_GET_PAGE) {
      buffer = getPage(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startStateGenerator() {
    if (state_step == 0) {
      return;
    }
    vertx.setPeriodic(state_generator_period, id -> {
      if (state_direction == Step.UP) {
        if (state >= state_max) {
          state_direction = Step.DOWN;
          this.state = (byte) (state - state_step);
        } else {
          this.state = (byte) (state + state_step);
        }
      } else {
        if (state <= state_min) {
          state_direction = Step.UP;
          this.state = (byte) (state + state_step);
        } else {
          this.state = (byte) (state - state_step);
        }
      }
    });
  }
        
  private void startPageGenerator() {
    if (page_step == 0) {
      return;
    }
    vertx.setPeriodic(page_generator_period, id -> {
      if (page_direction == Step.UP) {
        if (page >= page_max) {
          page_direction = Step.DOWN;
          this.page = (byte) (page - page_step);
        } else {
          this.page = (byte) (page + page_step);
        }
      } else {
        if (page <= page_min) {
          page_direction = Step.UP;
          this.page = (byte) (page + page_step);
        } else {
          this.page = (byte) (page - page_step);
        }
      }
    });
  }
        
  private void startTagIDGenerator() {
    if (tagID_step == 0) {
      return;
    }
    vertx.setPeriodic(tagID_generator_period, id -> {
      if (tagID_direction == Step.UP) {
        if (tagID >= tagID_max) {
          tagID_direction = Step.DOWN;
          this.tagID = (byte) (tagID - tagID_step);
        } else {
          this.tagID = (byte) (tagID + tagID_step);
        }
      } else {
        if (tagID <= tagID_min) {
          tagID_direction = Step.UP;
          this.tagID = (byte) (tagID + tagID_step);
        } else {
          this.tagID = (byte) (tagID - tagID_step);
        }
      }
    });
  }
        //fixme start_generator callback without sensor stateChanged
//fixme stop_generator callback without sensor stateChanged
//fixme getter callback without sensor stateChanged

  private Buffer getTagIDBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 9;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(tagID));
    buffer.appendBytes(Utils.getUInt8A(tagID));
    buffer.appendBytes(Utils.getUInt8A(tagID));

    return buffer;
  }
        
  private Buffer getTagIDBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getTagIDBuffer(FUNCTION_GET_TAG_ID, options);
  }

  private Buffer getTagID(Packet packet) {
    logger.debug("function getTagID");
    if (packet.getResponseExpected()) {
      return getTagIDBuffer(packet);
    }
    return null;
  }

  private Buffer getStateBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(state));
    buffer.appendBytes(Utils.getUInt8A(state));

    return buffer;
  }
        
  private Buffer getStateBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getStateBuffer(FUNCTION_GET_STATE, options);
  }

  private Buffer getState(Packet packet) {
    logger.debug("function getState");
    if (packet.getResponseExpected()) {
      return getStateBuffer(packet);
    }
    return null;
  }

  private Buffer getPageBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 16;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(page));

    return buffer;
  }
        
  private Buffer getPageBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getPageBuffer(FUNCTION_GET_PAGE, options);
  }

  private Buffer getPage(Packet packet) {
    logger.debug("function getPage");
    if (packet.getResponseExpected()) {
      return getPageBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer requestTagID(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer authenticateMifareClassicPage(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer writePage(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer requestPage(Packet packet) {
    //TODO dummy method
    return null;
  }
}
