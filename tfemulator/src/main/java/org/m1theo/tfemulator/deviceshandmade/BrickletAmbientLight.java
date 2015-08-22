package org.m1theo.tfemulator.deviceshandmade;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;

import java.util.Set;

import org.m1theo.tfemulator.Brickd;
import org.m1theo.tfemulator.CommonServices;
import org.m1theo.tfemulator.Utils;

public class BrickletAmbientLight extends AbstractVerticle {
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

  long uidBytes;

  @Override
  public void start() throws Exception {
    System.out.println("Verticle started: " + BrickletAmbientLight.class);
    String uidString = config().getString("uid");
    uidBytes = Utils.uid2long(uidString);
    System.out.println("uid in start " + uidBytes);

    vertx.eventBus().consumer(
        uidString,
        message -> {
          Buffer msgBuffer = (Buffer) message.body();
          System.out.println(BrickletAmbientLight.class + " " + uidString + " got message: "
              + Utils.packetHeader2String(msgBuffer));
          // extract functionID, search and execute function, send a reply buffer
          // to all vertx.getLocalMap(HANDLERIDMAP) keys
          // implement a function map with function number to code
          // message.reply(Utils.packetHeader2String((Buffer) message.body()));
          // message.reply(getIlluminance(Utils.getSequenceNumberFromData((Buffer)
          // message.body())));
          Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
          for (Object handlerid : handlerids) {
            Utils.getFunctionIDFromData(msgBuffer);
            Buffer buffer =
                callFunction(Utils.getFunctionIDFromData(msgBuffer),
                    Utils.getOptionsFromData(msgBuffer));
            // TODO add logging
            System.out.println(BrickletAmbientLight.class + " sending answer "
                + Utils.packetHeader2String(buffer));
            vertx.eventBus().publish((String) handlerid, buffer);
          }
        });

    // broadcast queue for enumeration requests
    vertx.eventBus().consumer(
        CommonServices.BROADCAST_UID,
        message -> {
          Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
          if (handlerids != null) {
            System.out.println("sending enumerate answer");
            for (Object handlerid : handlerids) {
              vertx.eventBus().publish((String) handlerid,
                  Utils.getEnumerateResponse(uidString, uidBytes, DEVICE_IDENTIFIER));
            }
          } else {
            System.out.println("Error: no handlerids found");
          }
        });
  }

  private Buffer callFunction(byte functionIDFromData, byte options) {
    Buffer buffer = null;
    if (functionIDFromData == 0) {
      // TODO raise Exception or log error
    } else if (functionIDFromData == FUNCTION_GET_ILLUMINANCE) {
      buffer = getIlluminance(options);
    } else {
      // TODO: raise Exception or log error
    }
    return buffer;
  }

  private Buffer getIlluminance(byte options) {
    byte length = (byte) 10; // TODO must match data length
    byte functionId = FUNCTION_GET_ILLUMINANCE;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.get2ByteRandomValue(1));

    return buffer;
  }
}
