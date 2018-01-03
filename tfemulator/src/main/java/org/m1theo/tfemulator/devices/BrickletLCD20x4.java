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
 * 20x4 character alphanumeric display with blue backlight
 */
public class BrickletLCD20x4 extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 212;
public final static String DEVICE_DISPLAY_NAME = "LCD 20x4 Bricklet";

  public final static byte FUNCTION_WRITE_LINE = (byte)1;
  public final static byte FUNCTION_CLEAR_DISPLAY = (byte)2;
  public final static byte FUNCTION_BACKLIGHT_ON = (byte)3;
  public final static byte FUNCTION_BACKLIGHT_OFF = (byte)4;
  public final static byte FUNCTION_IS_BACKLIGHT_ON = (byte)5;
  public final static byte FUNCTION_SET_CONFIG = (byte)6;
  public final static byte FUNCTION_GET_CONFIG = (byte)7;
  public final static byte FUNCTION_IS_BUTTON_PRESSED = (byte)8;
  public final static byte CALLBACK_BUTTON_PRESSED = (byte)9;
  public final static byte CALLBACK_BUTTON_RELEASED = (byte)10;
  public final static byte FUNCTION_SET_CUSTOM_CHARACTER = (byte)11;
  public final static byte FUNCTION_GET_CUSTOM_CHARACTER = (byte)12;
  public final static byte FUNCTION_SET_DEFAULT_TEXT = (byte)13;
  public final static byte FUNCTION_GET_DEFAULT_TEXT = (byte)14;
  public final static byte FUNCTION_SET_DEFAULT_TEXT_COUNTER = (byte)15;
  public final static byte FUNCTION_GET_DEFAULT_TEXT_COUNTER = (byte)16;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  String uidString;

  private Buffer customCharacter = getCustomCharacterDefault();
        
  private Buffer defaultText = getDefaultTextDefault();
        
  private Buffer defaultTextCounter = getDefaultTextCounterDefault();
        
  private Buffer config = getConfigDefault();
        
  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletLCD20x4.class);
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
    else if (functionId == FUNCTION_WRITE_LINE) {
      buffer = writeLine(packet);
    }
    else if (functionId == FUNCTION_CLEAR_DISPLAY) {
      buffer = clearDisplay(packet);
    }
    else if (functionId == FUNCTION_BACKLIGHT_ON) {
      buffer = backlightOn(packet);
    }
    else if (functionId == FUNCTION_BACKLIGHT_OFF) {
      buffer = backlightOff(packet);
    }
    else if (functionId == FUNCTION_IS_BACKLIGHT_ON) {
      buffer = isBacklightOn(packet);
    }
    else if (functionId == FUNCTION_SET_CONFIG) {
      buffer = setConfig(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIG) {
      buffer = getConfig(packet);
    }
    else if (functionId == FUNCTION_IS_BUTTON_PRESSED) {
      buffer = isButtonPressed(packet);
    }
    else if (functionId == FUNCTION_SET_CUSTOM_CHARACTER) {
      buffer = setCustomCharacter(packet);
    }
    else if (functionId == FUNCTION_GET_CUSTOM_CHARACTER) {
      buffer = getCustomCharacter(packet);
    }
    else if (functionId == FUNCTION_SET_DEFAULT_TEXT) {
      buffer = setDefaultText(packet);
    }
    else if (functionId == FUNCTION_GET_DEFAULT_TEXT) {
      buffer = getDefaultText(packet);
    }
    else if (functionId == FUNCTION_SET_DEFAULT_TEXT_COUNTER) {
      buffer = setDefaultTextCounter(packet);
    }
    else if (functionId == FUNCTION_GET_DEFAULT_TEXT_COUNTER) {
      buffer = getDefaultTextCounter(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }

//fixme start_generator callback without sensor buttonReleased
//fixme start_generator callback without sensor buttonPressed
//fixme stop_generator callback without sensor buttonReleased
//fixme stop_generator callback without sensor buttonPressed
//fixme getter callback without sensor buttonReleased
//fixme getter callback without sensor buttonPressed

  private Buffer getIsButtonPressedBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 1;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt8A(isButtonPressed));

    return buffer;
  }
        
  private Buffer getIsButtonPressedBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIsButtonPressedBuffer(FUNCTION_IS_BUTTON_PRESSED, options);
  }

  private Buffer getIsButtonPressed(Packet packet) {
    logger.debug("function getIsButtonPressed");
    if (packet.getResponseExpected()) {
      return getIsButtonPressedBuffer(packet);
    }
    return null;
  }

  /**
   * 
   */
  private Buffer clearDisplay(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer writeLine(Packet packet) {
    //TODO dummy method
    return null;
  }

  /**
   * 
   */
  private Buffer isButtonPressed(Packet packet) {
    //TODO dummy method
    return null;
  }
}
