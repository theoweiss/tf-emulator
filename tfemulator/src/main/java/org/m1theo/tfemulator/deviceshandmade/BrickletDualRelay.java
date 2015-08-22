/*
 * *********************************************************** This file was automatically generated
 * on 2015-07-12. * * Bindings Version 1.0.0 * * If you have a bugfix for this file and want to
 * commit it, * please fix the bug in the generator. You can find a link * to the generators git
 * repository on tinkerforge.com *
 *************************************************************/

package org.m1theo.tfemulator.deviceshandmade;

import java.util.Set;

import org.m1theo.tfemulator.Brickd;
import org.m1theo.tfemulator.CommonServices;
import org.m1theo.tfemulator.Utils;
import org.m1theo.tfemulator.protocol.Packet;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;

/**
 * Two relays to switch AC/DC devices
 */
public class BrickletDualRelay extends AbstractVerticle {
  long uidBytes;
  int[] apiVersion = new int[3];
  private Buffer state;

  public final static int DEVICE_IDENTIFIER = 26;
  public final static String DEVICE_DISPLAY_NAME = "Dual Relay Bricklet";

  public final static byte FUNCTION_SET_STATE = (byte) 1;
  public final static byte FUNCTION_GET_STATE = (byte) 2;
  public final static byte FUNCTION_SET_MONOFLOP = (byte) 3;
  public final static byte FUNCTION_GET_MONOFLOP = (byte) 4;
  public final static byte CALLBACK_MONOFLOP_DONE = (byte) 5;
  public final static byte FUNCTION_SET_SELECTED_STATE = (byte) 6;
  public final static byte FUNCTION_GET_IDENTITY = (byte) 255;


  /**
   * Starts a verticle for the device with the unique device ID \c uid.
   */
  @Override
  public void start() throws Exception {

    apiVersion[0] = 2;
    apiVersion[1] = 0;
    apiVersion[2] = 0;

    System.out.println("Verticle started: " + BrickletDualRelay.class);
    String uidString = config().getString("uid");
    uidBytes = Utils.uid2long(uidString);

    vertx.eventBus().consumer(uidString, message -> {
      Buffer msgBuffer = (Buffer) message.body();
      Packet packet = new Packet(msgBuffer);
      // System.out.println(BrickletDualRelay.class + " " + uidString + " got message: " +
      // message.body());
      Set<Object> handlerids = vertx.sharedData().getLocalMap(Brickd.HANDLERIDMAP).keySet();
      for (Object handlerid : handlerids) {
        Buffer buffer = callFunction(packet);
        // TODO add logging
        if (packet.getResponseExpected()) {
          System.out.println(
              BrickletDualRelay.class + " sending answer " + Utils.packetHeader2String(buffer));
          vertx.eventBus().publish((String) handlerid, buffer);
        }
      }
    });

    // broadcast queue for enumeration requests
    vertx.eventBus().consumer(CommonServices.BROADCAST_UID, message -> {
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

  private Buffer callFunction(Packet packet) {
    Buffer buffer = null;
    // byte functionId = packet.getFunctionId();
    // if (functionId == 0) {
    // // TODO raise Exception or log error
    // } else if (functionId == FUNCTION_SET_STATE) {
    // buffer = setState(packet);
    // } else if (functionId == FUNCTION_GET_STATE) {
    // buffer = getState(packet);
    // } else if (functionId == FUNCTION_SET_MONOFLOP) {
    // buffer = setMonoflop(packet);
    // } else if (functionId == FUNCTION_GET_MONOFLOP) {
    // buffer = getMonoflop(packet);
    // } else if (functionId == FUNCTION_SET_SELECTED_STATE) {
    // buffer = setSelectedState(packet);
    // } else if (functionId == FUNCTION_GET_IDENTITY) {
    // buffer = getIdentity(packet);
    // } else {
    // // TODO: raise Exception or log error
    // }
    return buffer;
  }


  /**
   * Sets the state of the relays, *true* means on and *false* means off. For example: (true, false)
   * turns relay 1 on and relay 2 off.
   * 
   * If you just want to set one of the relays and don't know the current state of the other relay,
   * you can get the state with {@link BrickletDualRelay#getState()} or you can use
   * {@link BrickletDualRelay#setSelectedState(short, boolean)}.
   * 
   * Running monoflop timers will be overwritten if this function is called.
   * 
   * The default value is (*false*, *false*).
   */
  private Buffer setState(Packet packet) {
    // TODO
    // get payload
    // set state field to Buffer(payload)
    // response expected?
    if (packet.getResponseExpected()) {
      byte length = (byte) 8 + 0;
      byte functionId = FUNCTION_SET_STATE;
      byte flags = (byte) 0;
      Buffer header =
          Utils.createHeader(uidBytes, length, functionId, packet.getSequenceNumber(), flags);
      Buffer buffer = Buffer.buffer();
      buffer.appendBuffer(header);
      return buffer;
    }
    state = packet.getPayload();
    // TODO handle buffer == null in socket/eventbus write
    return null;
  }

  /**
   * Returns the state of the relays, *true* means on and *false* means off.
   */
  private Buffer getState(Packet packet) {
    // TODO read state from state field
    byte length = (byte) 8 + 2;
    byte functionId = FUNCTION_GET_STATE;
    byte flags = (byte) 0;
    Buffer header =
        Utils.createHeader(uidBytes, length, functionId, packet.getSequenceNumber(), flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBuffer(state);
    return buffer;
  }

  /**
   * The first parameter can be 1 or 2 (relay 1 or relay 2). The second parameter is the desired
   * state of the relay (*true* means on and *false* means off). The third parameter indicates the
   * time (in ms) that the relay should hold the state.
   * 
   * If this function is called with the parameters (1, true, 1500): Relay 1 will turn on and in
   * 1.5s it will turn off again.
   * 
   * A monoflop can be used as a failsafe mechanism. For example: Lets assume you have a RS485 bus
   * and a Dual Relay Bricklet connected to one of the slave stacks. You can now call this function
   * every second, with a time parameter of two seconds. The relay will be on all the time. If now
   * the RS485 connection is lost, the relay will turn off in at most two seconds.
   */
  private Buffer setMonoflop(byte options) {
    byte length = (byte) 8 + 0;
    byte functionId = FUNCTION_SET_MONOFLOP;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);

    return buffer;
  }

  /**
   * Returns (for the given relay) the current state and the time as set by
   * {@link BrickletDualRelay#setMonoflop(short, boolean, long)} as well as the remaining time until
   * the state flips.
   * 
   * If the timer is not running currently, the remaining time will be returned as 0.
   */
  private Buffer getMonoflop(byte options) {
    byte length = (byte) 8 + 9;
    byte functionId = FUNCTION_GET_MONOFLOP;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getBoolRandomValue(1));
    buffer.appendBytes(Utils.get4ByteURandomValue(1));
    buffer.appendBytes(Utils.get4ByteURandomValue(1));

    return buffer;
  }

  /**
   * Sets the state of the selected relay (1 or 2), *true* means on and *false* means off.
   * 
   * The other relay remains untouched.
   */
  private Buffer setSelectedState(byte options) {
    byte length = (byte) 8 + 0;
    byte functionId = FUNCTION_SET_SELECTED_STATE;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);

    return buffer;
  }

  /**
   * Returns the UID, the UID where the Bricklet is connected to, the position, the hardware and
   * firmware version as well as the device identifier.
   * 
   * The position can be 'a', 'b', 'c' or 'd'.
   * 
   * The device identifier numbers can be found :ref:`here &lt;device_identifier&gt;`.
   * |device_identifier_constant|
   */
  private Buffer getIdentity(byte options) {
    byte length = (byte) 8 + 25;
    byte functionId = FUNCTION_GET_IDENTITY;
    byte flags = (byte) 0;
    Buffer header = Utils.createHeader(uidBytes, length, functionId, options, flags);
    Buffer buffer = Buffer.buffer();
    buffer.appendBuffer(header);
    buffer.appendBytes(Utils.getCharRandomValue(8));
    buffer.appendBytes(Utils.getCharRandomValue(8));
    buffer.appendBytes(Utils.getCharRandomValue(1));
    buffer.appendBytes(Utils.get1ByteURandomValue(3));
    buffer.appendBytes(Utils.get1ByteURandomValue(3));
    buffer.appendBytes(Utils.get2ByteURandomValue(1));

    return buffer;
  }
}
