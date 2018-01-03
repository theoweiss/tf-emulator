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
 * Measures color (RGB value), illuminance and color temperature
 */
public class BrickletColor extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 243;
public final static String DEVICE_DISPLAY_NAME = "Color Bricklet";

  public final static byte FUNCTION_GET_COLOR = (byte)1;
  public final static byte FUNCTION_SET_COLOR_CALLBACK_PERIOD = (byte)2;
  public final static byte FUNCTION_GET_COLOR_CALLBACK_PERIOD = (byte)3;
  public final static byte FUNCTION_SET_COLOR_CALLBACK_THRESHOLD = (byte)4;
  public final static byte FUNCTION_GET_COLOR_CALLBACK_THRESHOLD = (byte)5;
  public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
  public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
  public final static byte CALLBACK_COLOR = (byte)8;
  public final static byte CALLBACK_COLOR_REACHED = (byte)9;
  public final static byte FUNCTION_LIGHT_ON = (byte)10;
  public final static byte FUNCTION_LIGHT_OFF = (byte)11;
  public final static byte FUNCTION_IS_LIGHT_ON = (byte)12;
  public final static byte FUNCTION_SET_CONFIG = (byte)13;
  public final static byte FUNCTION_GET_CONFIG = (byte)14;
  public final static byte FUNCTION_GET_ILLUMINANCE = (byte)15;
  public final static byte FUNCTION_GET_COLOR_TEMPERATURE = (byte)16;
  public final static byte FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD = (byte)17;
  public final static byte FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD = (byte)18;
  public final static byte FUNCTION_SET_COLOR_TEMPERATURE_CALLBACK_PERIOD = (byte)19;
  public final static byte FUNCTION_GET_COLOR_TEMPERATURE_CALLBACK_PERIOD = (byte)20;
  public final static byte CALLBACK_ILLUMINANCE = (byte)21;
  public final static byte CALLBACK_COLOR_TEMPERATURE = (byte)22;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static char THRESHOLD_OPTION_OFF = 'x';
  public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
  public final static char THRESHOLD_OPTION_INSIDE = 'i';
  public final static char THRESHOLD_OPTION_SMALLER = '<';
  public final static char THRESHOLD_OPTION_GREATER = '>';
  public final static short LIGHT_ON = (short)0;
  public final static short LIGHT_OFF = (short)1;
  public final static short GAIN_1X = (short)0;
  public final static short GAIN_4X = (short)1;
  public final static short GAIN_16X = (short)2;
  public final static short GAIN_60X = (short)3;
  public final static short INTEGRATION_TIME_2MS = (short)0;
  public final static short INTEGRATION_TIME_24MS = (short)1;
  public final static short INTEGRATION_TIME_101MS = (short)2;
  public final static short INTEGRATION_TIME_154MS = (short)3;
  public final static short INTEGRATION_TIME_700MS = (short)4;
  String uidString;

  private Buffer config = getConfigDefault();
        
  private Buffer colorCallbackThreshold = Utils.getThresholdDefault();
  private Buffer debouncePeriod = Buffer.buffer(Utils.getUInt16(100));
  private int illuminance = 100;
  private int illuminance_max = 1000;
  private int illuminance_min = 0;
  private int illuminance_step = 1;
  private long illuminance_generator_period = 100;
  private Step illuminance_direction = Step.UP;
  private long illuminanceCallbackPeriod;
  private long illuminance_callback_id;
  private int illuminance_last_value_called_back;

  private short colorTemperature = 100;
  private short colorTemperature_max = 1000;
  private short colorTemperature_min = 0;
  private short colorTemperature_step = 1;
  private long colorTemperature_generator_period = 100;
  private Step colorTemperature_direction = Step.UP;
  private long colorTemperatureCallbackPeriod;
  private long colorTemperature_callback_id;
  private short colorTemperature_last_value_called_back;

  private short color = 100;
  private short color_max = 1000;
  private short color_min = 0;
  private short color_step = 1;
  private long color_generator_period = 100;
  private Step color_direction = Step.UP;
  private long colorCallbackPeriod;
  private long color_callback_id;
  private short color_last_value_called_back;

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletColor.class);
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

    startIlluminanceGenerator();
    startColorTemperatureGenerator();
    startColorGenerator();
  }

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    byte functionId = packet.getFunctionId();
    if (functionId == 0 ){
      //TODO raise Exception or log error
    }
    else if (functionId == FUNCTION_GET_COLOR) {
      buffer = getColor(packet);
    }
    else if (functionId == FUNCTION_SET_COLOR_CALLBACK_PERIOD) {
      buffer = setColorCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_CALLBACK_PERIOD) {
      buffer = getColorCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_COLOR_CALLBACK_THRESHOLD) {
      buffer = setColorCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_CALLBACK_THRESHOLD) {
      buffer = getColorCallbackThreshold(packet);
    }
    else if (functionId == FUNCTION_SET_DEBOUNCE_PERIOD) {
      buffer = setDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DEBOUNCE_PERIOD) {
      buffer = getDebouncePeriod(packet);
    }
    else if (functionId == FUNCTION_LIGHT_ON) {
      buffer = lightOn(packet);
    }
    else if (functionId == FUNCTION_LIGHT_OFF) {
      buffer = lightOff(packet);
    }
    else if (functionId == FUNCTION_IS_LIGHT_ON) {
      buffer = isLightOn(packet);
    }
    else if (functionId == FUNCTION_SET_CONFIG) {
      buffer = setConfig(packet);
    }
    else if (functionId == FUNCTION_GET_CONFIG) {
      buffer = getConfig(packet);
    }
    else if (functionId == FUNCTION_GET_ILLUMINANCE) {
      buffer = getIlluminance(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_TEMPERATURE) {
      buffer = getColorTemperature(packet);
    }
    else if (functionId == FUNCTION_SET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = setIlluminanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ILLUMINANCE_CALLBACK_PERIOD) {
      buffer = getIlluminanceCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_COLOR_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = setColorTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_COLOR_TEMPERATURE_CALLBACK_PERIOD) {
      buffer = getColorTemperatureCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_IDENTITY) {
      buffer = getIdentity(packet);
    }
    else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }


  private void startIlluminanceGenerator() {
    if (illuminance_step == 0) {
      return;
    }
    vertx.setPeriodic(illuminance_generator_period, id -> {
      if (illuminance_direction == Step.UP) {
        if (illuminance >= illuminance_max) {
          illuminance_direction = Step.DOWN;
          this.illuminance = (int) (illuminance - illuminance_step);
        } else {
          this.illuminance = (int) (illuminance + illuminance_step);
        }
      } else {
        if (illuminance <= illuminance_min) {
          illuminance_direction = Step.UP;
          this.illuminance = (int) (illuminance + illuminance_step);
        } else {
          this.illuminance = (int) (illuminance - illuminance_step);
        }
      }
    });
  }
        
  private void startColorTemperatureGenerator() {
    if (colorTemperature_step == 0) {
      return;
    }
    vertx.setPeriodic(colorTemperature_generator_period, id -> {
      if (colorTemperature_direction == Step.UP) {
        if (colorTemperature >= colorTemperature_max) {
          colorTemperature_direction = Step.DOWN;
          this.colorTemperature = (short) (colorTemperature - colorTemperature_step);
        } else {
          this.colorTemperature = (short) (colorTemperature + colorTemperature_step);
        }
      } else {
        if (colorTemperature <= colorTemperature_min) {
          colorTemperature_direction = Step.UP;
          this.colorTemperature = (short) (colorTemperature + colorTemperature_step);
        } else {
          this.colorTemperature = (short) (colorTemperature - colorTemperature_step);
        }
      }
    });
  }
        
  private void startColorGenerator() {
    if (color_step == 0) {
      return;
    }
    vertx.setPeriodic(color_generator_period, id -> {
      if (color_direction == Step.UP) {
        if (color >= color_max) {
          color_direction = Step.DOWN;
          this.color = (short) (color - color_step);
        } else {
          this.color = (short) (color + color_step);
        }
      } else {
        if (color <= color_min) {
          color_direction = Step.UP;
          this.color = (short) (color + color_step);
        } else {
          this.color = (short) (color - color_step);
        }
      }
    });
  }
        
    private void stopColorCallback() {
        vertx.cancelTimer(color_callback_id);
  }
        //fixme start_generator callback without sensor color
//fixme start_generator callback without sensor colorReached

    private void stopColorTemperatureCallback() {
        vertx.cancelTimer(colorTemperature_callback_id);
  }
        //fixme start_generator callback without sensor colorTemperature

    private void stopIlluminanceCallback() {
        vertx.cancelTimer(illuminance_callback_id);
  }
        //fixme start_generator callback without sensor illuminance

  private void startColorCallback() {
    logger.trace("colorCallbackPeriod is {}", colorCallbackPeriod);
    color_callback_id = vertx.setPeriodic(colorCallbackPeriod, id -> {
      if (color != color_last_value_called_back) {
        color_last_value_called_back = color;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("color sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getColor4Callback());
          }
        } else {
          logger.info("no handlerids found in color callback");
        }
      } else {
        logger.debug("color value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor color
//fixme stop_generator callback without sensor colorReached

  private void startColorTemperatureCallback() {
    logger.trace("colorTemperatureCallbackPeriod is {}", colorTemperatureCallbackPeriod);
    colorTemperature_callback_id = vertx.setPeriodic(colorTemperatureCallbackPeriod, id -> {
      if (colorTemperature != colorTemperature_last_value_called_back) {
        colorTemperature_last_value_called_back = colorTemperature;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("colorTemperature sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getColorTemperature4Callback());
          }
        } else {
          logger.info("no handlerids found in colorTemperature callback");
        }
      } else {
        logger.debug("colorTemperature value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor colorTemperature

  private void startIlluminanceCallback() {
    logger.trace("illuminanceCallbackPeriod is {}", illuminanceCallbackPeriod);
    illuminance_callback_id = vertx.setPeriodic(illuminanceCallbackPeriod, id -> {
      if (illuminance != illuminance_last_value_called_back) {
        illuminance_last_value_called_back = illuminance;
        Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
        if (handlerids != null) {
          logger.debug("illuminance sending callback value");
          for (Object handlerid : handlerids) {
            vertx.eventBus().publish((String) handlerid, getIlluminance4Callback());
          }
        } else {
          logger.info("no handlerids found in illuminance callback");
        }
      } else {
        logger.debug("illuminance value already called back");
      }
    });
  }

//fixme stop_generator callback without sensor illuminance

  private Buffer getColor4Callback() {
      byte options = (byte) 0;
      return getColorBuffer(CALLBACK_COLOR, options);
  }
        //fixme getter callback without sensor color
//fixme getter callback without sensor colorReached

  private Buffer getColorTemperature4Callback() {
      byte options = (byte) 0;
      return getColorTemperatureBuffer(CALLBACK_COLOR_TEMPERATURE, options);
  }
        //fixme getter callback without sensor colorTemperature

  private Buffer getIlluminance4Callback() {
      byte options = (byte) 0;
      return getIlluminanceBuffer(CALLBACK_ILLUMINANCE, options);
  }
        //fixme getter callback without sensor illuminance

  private Buffer setColorCallbackPeriod(Packet packet) {
    colorCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (colorCallbackPeriod == 0) {
      stopColorCallback();
    } else {
      startColorCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COLOR_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer setIlluminanceCallbackPeriod(Packet packet) {
    illuminanceCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (illuminanceCallbackPeriod == 0) {
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
        
  private Buffer setColorTemperatureCallbackPeriod(Packet packet) {
    colorTemperatureCallbackPeriod =  Utils.unsignedInt(packet.getPayload());
    if (colorTemperatureCallbackPeriod == 0) {
      stopColorTemperatureCallback();
    } else {
      startColorTemperatureCallback();
    }
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COLOR_TEMPERATURE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    return null;
  }
        
  private Buffer getColorBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 8;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(color));
    buffer.appendBytes(Utils.getUInt16(color));
    buffer.appendBytes(Utils.getUInt16(color));
    buffer.appendBytes(Utils.getUInt16(color));

    return buffer;
  }
        
  private Buffer getColorBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getColorBuffer(FUNCTION_GET_COLOR, options);
  }

  private Buffer getColor(Packet packet) {
    logger.debug("function getColor");
    if (packet.getResponseExpected()) {
      return getColorBuffer(packet);
    }
    return null;
  }

  private Buffer getIlluminanceBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 4;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt32(illuminance));

    return buffer;
  }
        
  private Buffer getIlluminanceBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getIlluminanceBuffer(FUNCTION_GET_ILLUMINANCE, options);
  }

  private Buffer getIlluminance(Packet packet) {
    logger.debug("function getIlluminance");
    if (packet.getResponseExpected()) {
      return getIlluminanceBuffer(packet);
    }
    return null;
  }

  private Buffer getColorTemperatureBuffer(byte functionId, byte options) {
    byte length = (byte) 8 + 2;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getUInt16(colorTemperature));

    return buffer;
  }
        
  private Buffer getColorTemperatureBuffer(Packet packet) {
      byte options = packet.getOptions();
      return getColorTemperatureBuffer(FUNCTION_GET_COLOR_TEMPERATURE, options);
  }

  private Buffer getColorTemperature(Packet packet) {
    logger.debug("function getColorTemperature");
    if (packet.getResponseExpected()) {
      return getColorTemperatureBuffer(packet);
    }
    return null;
  }
}
