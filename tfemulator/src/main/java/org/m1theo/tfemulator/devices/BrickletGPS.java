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
 * Determine position, velocity and altitude using GPS
 */
public class BrickletGPS extends AbstractVerticle {
long uidBytes;
int[] apiVersion = new int[3];
private Logger logger;

public final static int DEVICE_IDENTIFIER = 222;
public final static String DEVICE_DISPLAY_NAME = "GPS Bricklet";

  public final static byte FUNCTION_GET_COORDINATES = (byte)1;
  public final static byte FUNCTION_GET_STATUS = (byte)2;
  public final static byte FUNCTION_GET_ALTITUDE = (byte)3;
  public final static byte FUNCTION_GET_MOTION = (byte)4;
  public final static byte FUNCTION_GET_DATE_TIME = (byte)5;
  public final static byte FUNCTION_RESTART = (byte)6;
  public final static byte FUNCTION_SET_COORDINATES_CALLBACK_PERIOD = (byte)7;
  public final static byte FUNCTION_GET_COORDINATES_CALLBACK_PERIOD = (byte)8;
  public final static byte FUNCTION_SET_STATUS_CALLBACK_PERIOD = (byte)9;
  public final static byte FUNCTION_GET_STATUS_CALLBACK_PERIOD = (byte)10;
  public final static byte FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD = (byte)11;
  public final static byte FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD = (byte)12;
  public final static byte FUNCTION_SET_MOTION_CALLBACK_PERIOD = (byte)13;
  public final static byte FUNCTION_GET_MOTION_CALLBACK_PERIOD = (byte)14;
  public final static byte FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD = (byte)15;
  public final static byte FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD = (byte)16;
  public final static byte CALLBACK_COORDINATES = (byte)17;
  public final static byte CALLBACK_STATUS = (byte)18;
  public final static byte CALLBACK_ALTITUDE = (byte)19;
  public final static byte CALLBACK_MOTION = (byte)20;
  public final static byte CALLBACK_DATE_TIME = (byte)21;
  public final static byte FUNCTION_GET_IDENTITY = (byte)255;

  public final static short FIX_NO_FIX = (short)1;
  public final static short FIX_2D_FIX = (short)2;
  public final static short FIX_3D_FIX = (short)3;
  public final static short RESTART_TYPE_HOT_START = (short)0;
  public final static short RESTART_TYPE_WARM_START = (short)1;
  public final static short RESTART_TYPE_COLD_START = (short)2;
  public final static short RESTART_TYPE_FACTORY_RESET = (short)3;
  String uidString;
  private Buffer dateTimeCallbackPeriod = getDateTimeCallbackPeriodDefault();
  private Buffer coordinatesCallbackPeriod = getCoordinatesCallbackPeriodDefault();
  private Buffer altitudeCallbackPeriod = getAltitudeCallbackPeriodDefault();
  private Buffer statusCallbackPeriod = getStatusCallbackPeriodDefault();
  private Buffer motionCallbackPeriod = getMotionCallbackPeriodDefault();

  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    logger = LoggerFactory.getLogger(getClass());

    logger.info("Verticle started: " + BrickletGPS.class);
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
    else if (functionId == FUNCTION_GET_COORDINATES) {
      buffer = getCoordinates(packet);
    }
    else if (functionId == FUNCTION_GET_STATUS) {
      buffer = getStatus(packet);
    }
    else if (functionId == FUNCTION_GET_ALTITUDE) {
      buffer = getAltitude(packet);
    }
    else if (functionId == FUNCTION_GET_MOTION) {
      buffer = getMotion(packet);
    }
    else if (functionId == FUNCTION_GET_DATE_TIME) {
      buffer = getDateTime(packet);
    }
    else if (functionId == FUNCTION_RESTART) {
      buffer = restart(packet);
    }
    else if (functionId == FUNCTION_SET_COORDINATES_CALLBACK_PERIOD) {
      buffer = setCoordinatesCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_COORDINATES_CALLBACK_PERIOD) {
      buffer = getCoordinatesCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_STATUS_CALLBACK_PERIOD) {
      buffer = setStatusCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_STATUS_CALLBACK_PERIOD) {
      buffer = getStatusCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD) {
      buffer = setAltitudeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD) {
      buffer = getAltitudeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_MOTION_CALLBACK_PERIOD) {
      buffer = setMotionCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_MOTION_CALLBACK_PERIOD) {
      buffer = getMotionCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD) {
      buffer = setDateTimeCallbackPeriod(packet);
    }
    else if (functionId == FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD) {
      buffer = getDateTimeCallbackPeriod(packet);
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
  private Buffer getDateTime(Packet packet) {
    logger.debug("function getDateTime");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 8;
      byte functionId = FUNCTION_GET_DATE_TIME;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getStatus(Packet packet) {
    logger.debug("function getStatus");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 3;
      byte functionId = FUNCTION_GET_STATUS;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        
      buffer.appendBytes(Utils.get1ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getMotion(Packet packet) {
    logger.debug("function getMotion");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 8;
      byte functionId = FUNCTION_GET_MOTION;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getAltitude(Packet packet) {
    logger.debug("function getAltitude");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 8;
      byte functionId = FUNCTION_GET_ALTITUDE;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getCoordinates(Packet packet) {
    logger.debug("function getCoordinates");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 18;
      byte functionId = FUNCTION_GET_COORDINATES;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      buffer.appendBytes(Utils.getCharRandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        
      buffer.appendBytes(Utils.get2ByteURandomValue(1));        

      return buffer;
    }

    return null;
  }

  /**
   * 
   */
  private Buffer getDateTimeCallbackPeriod(Packet packet) {
    logger.debug("function getDateTimeCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.dateTimeCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getDateTimeCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getCoordinatesCallbackPeriod(Packet packet) {
    logger.debug("function getCoordinatesCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_COORDINATES_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.coordinatesCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getCoordinatesCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getAltitudeCallbackPeriod(Packet packet) {
    logger.debug("function getAltitudeCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.altitudeCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getAltitudeCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getStatusCallbackPeriod(Packet packet) {
    logger.debug("function getStatusCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_STATUS_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.statusCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getStatusCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer getMotionCallbackPeriod(Packet packet) {
    logger.debug("function getMotionCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 4;
      byte functionId = FUNCTION_GET_MOTION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      buffer.appendBuffer(this.motionCallbackPeriod);
      return buffer;
    }

    return null;
  }

  private Buffer getMotionCallbackPeriodDefault() {
      Buffer buffer = Buffer.buffer();
      buffer.appendBytes(Utils.get4ByteURandomValue(1));        
      return buffer;
  }

  /**
   * 
   */
  private Buffer setAltitudeCallbackPeriod(Packet packet) {
    logger.debug("function setAltitudeCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.altitudeCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setStatusCallbackPeriod(Packet packet) {
    logger.debug("function setStatusCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STATUS_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.statusCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setDateTimeCallbackPeriod(Packet packet) {
    logger.debug("function setDateTimeCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.dateTimeCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setMotionCallbackPeriod(Packet packet) {
    logger.debug("function setMotionCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_MOTION_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.motionCallbackPeriod = packet.getPayload();
    return null;
  }

  /**
   * 
   */
  private Buffer setCoordinatesCallbackPeriod(Packet packet) {
    logger.debug("function setCoordinatesCallbackPeriod");
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_COORDINATES_CALLBACK_PERIOD;
      byte flags = (byte) 0;
      Buffer header = Utils.createHeader(uidBytes, length, functionId, packet.getOptions(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
//TODO response expected bei settern
      return buffer;
    }
    this.coordinatesCallbackPeriod = packet.getPayload();
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
  private Buffer restart(Packet packet) {
    //TODO dummy method
    return null;
  }
}
