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
package org.m1theo.tfemulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;


public class Utils {
  private final static String BASE58 =
      "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
  // private final static String BASE58 =
  // "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
  public final static byte CALLBACK_ENUMERATE = (byte) 253;

  // TODO Start Move to protocol
  public static Buffer getPayloadFromData(Buffer buffer) {

    return null;
  }

  public static byte getOptionsFromData(Buffer buffer) {
    byte[] data = buffer.getBytes();
    return data[6];
  }

  public static byte getSequenceNumberFromData(Buffer buffer) {
    byte[] data = buffer.getBytes();
    return (byte) ((((int) data[6]) >> 4) & 0x0F);
  }

  public static byte[] getUidFromData(Buffer buffer) {
    byte[] data = buffer.getBytes();
    byte[] uid = new byte[] {data[0], data[1], data[2], data[3]};
    return uid;
  }

  public static byte getFunctionIDFromData(Buffer buffer) {
    byte[] data = buffer.getBytes();
    return data[5];
  }

  public static Buffer packetHeader2String(Buffer buffer) {
    byte[] packet = buffer.getBytes();
    // ByteBuffer bb = ByteBuffer.wrap(packet, 8, packet.length - 8);
    ByteBuffer bb = ByteBuffer.wrap(packet);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    int uid = bb.getInt();
    byte length = bb.get();
    byte functionId = bb.get();
    Buffer bufferOut = Buffer.buffer();
    bufferOut.appendString("uid: ");
    bufferOut.appendString(Utils.base58Encode(uid));
    bufferOut.appendString(" length: ");
    bufferOut.appendString(String.valueOf(unsignedByte(length)));
    bufferOut.appendString(" functionid: ");
    bufferOut.appendString(String.valueOf(unsignedByte(functionId)));

    return bufferOut;
  }

  // This is the sequenceNumber for responses to incoming requests
  // Callback to Enumerate have: sequenceNumber == 0
  static byte getResponseSequenceNumberFromData(byte[] data) {
    return (byte) ((((int) data[6]) >> 4) & 0x0F);
  }

  public static Buffer createHeader(long uid, byte length, byte functionId, byte options,
      byte flags) {
    Buffer buffer = Buffer.buffer();
    // uid
    byte[] uidbytes = getUInt32(uid);
    buffer.appendBytes(uidbytes);
    // length
    buffer.appendByte(length);
    // functionId
    buffer.appendByte(functionId);
    // options: sequence number
    buffer.appendByte(options);
    // flags
    buffer.appendByte(flags);

    return buffer;
  }
  // TODO END Move to protocol

  /*
   * get char values
   */
  public static byte[] getCharRandomValue(int cardinality) {
    char[] randomValue = new char[] {'a'}; // TODO randomize
    byte[] bytes = new String(randomValue).getBytes();
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(bytes);
    }
    return buffer.getBytes();

  }

  /*
   * get boolean value: 0 means false, 1 is true
   */
  public static byte[] getBoolRandomValue(int cardinality) {
    short randomValue = 0; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendByte(getUInt8(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint8 compatible byte values cardinality means the number of uint8 values
   */
  public static byte[] get1ByteRandomValue(int cardinality) {
    byte randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendByte(randomValue);
    }
    return buffer.getBytes();
  }

  /*
   * get uint8 compatible byte values cardinality means the number of uint8 values
   */
  public static byte[] get1ByteURandomValue(int cardinality) {
    short randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendByte(getUInt8(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get int16 compatible byte values cardinality means the number of int16 values
   */
  public static byte[] get2ByteRandomValue(int cardinality) {
    int randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(getUInt16(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint16 compatible byte values cardinality means the number of uint16 values
   */
  public static byte[] get2ByteURandomValue(int cardinality) {
    int randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(getUInt16(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get int32 compatible byte values cardinality means the number of int32 values
   */
  public static byte[] get4ByteRandomValue(int cardinality) {
    int randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(getUInt32(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint32 compatible byte values cardinality means the number of uint32 values
   */
  public static byte[] get4ByteURandomValue(int cardinality) {
    Long randomValue = (long) 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(getUInt32(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint64 compatible byte values cardinality means the number of uint64 values
   */
  public static byte[] get8ByteURandomValue(int cardinality) {
    Long randomValue = (long) 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(getUInt32(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get float byte values cardinality means the number of float values
   */
  public static byte[] getFloatRandomValue(int cardinality) {
    float randomValue = 100; // TODO randomize
    // Buffer buffer = Buffer.buffer();
    // for (int i = 0; i < cardinality - 1; i++) {
    // // TODO not sure if this is right: BrickRED uses bb.getLong in getFileInfo()
    // buffer.appendFloat(0);
    // }
    // buffer.appendFloat(randomValue);
    // return buffer.getBytes();
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(getFloat(randomValue));
    }
    return buffer.getBytes();
  }

  private static char[] getUidAs8ByteChar(String uid) {
    char[] cs = uid.toCharArray();
    char[] ncs = new char[8];

    for (int i = 0; i < 8; i++) {
      if (cs.length > i) {
        ncs[i] = cs[i];
      } else {
        ncs[i] = '\0';
      }
    }
    return ncs;
  }


  public static Buffer getEnumerateResponse(String uidString, long uid, int deviceIdentifier) {
    Buffer buffer = Buffer.buffer();
    // header
    buffer
        .appendBuffer(createHeader(uid, (byte) 34, (byte) CALLBACK_ENUMERATE, (byte) 0, (byte) 0));
    buffer.appendBuffer(getIdentityPayload(uidString, uid, deviceIdentifier));

    // enumeration type: short
    buffer.appendByte((byte) 0);
    System.out.println("length " + buffer.getBytes().length);


    return buffer;
  }

  public static Buffer getIdentityPayload(String uidString, long uid, int deviceIdentifier) {
    Buffer buffer = Buffer.buffer();
    // body
    // 8 byte uid as char
    char[] uidAs8ByteChar = getUidAs8ByteChar(uidString);
    for (int i = 0; i < uidAs8ByteChar.length; i++) {
      buffer.appendByte((byte) uidAs8ByteChar[i]);
    }

    // 8 byte connnected uid
    // TODO for now add own uid
    for (int i = 0; i < uidAs8ByteChar.length; i++) {
      buffer.appendByte((byte) uidAs8ByteChar[i]);
    }

    // position
    // Die Position kann '0'-'8' (Stack Position) sein.
    // type char
    buffer.appendByte((byte) '3');

    // hardware version 3 byte
    buffer.appendByte((byte) 2);
    buffer.appendByte((byte) 1);
    buffer.appendByte((byte) 1);

    // firmware version 3 byte
    buffer.appendByte((byte) 1);
    buffer.appendByte((byte) 1);
    buffer.appendByte((byte) 2);

    // deviceIdentifier 2 bytes
    buffer.appendBytes(getUInt16(deviceIdentifier));

    return buffer;
  }

  public static byte[] getFloat(float numfloat) {
    // extract 3 bytes from the 4 byte java float
    int num = Float.floatToIntBits(numfloat);
    byte[] bytes = new byte[4];
    bytes[0] = (byte) (num & 0xFF);
    bytes[1] = (byte) ((num >> 8) & 0xFF);
    bytes[2] = (byte) ((num >> 16) & 0xFF);
    bytes[3] = (byte) ((num >> 24) & 0xFF);

    return bytes;
  }

  public static byte[] getUInt32(long num) {
    // java long is used for storing uint32 from / for network bufferes
    // extract 4 bytes from the 8 byte java int
    byte[] bytes = new byte[4];
    bytes[0] = (byte) (num & 0xFF);
    bytes[1] = (byte) ((num >> 8) & 0xFF);
    bytes[2] = (byte) ((num >> 16) & 0xFF);
    bytes[3] = (byte) ((num >> 24) & 0xFF);

    return bytes;
  }

  public static byte[] getUInt16(int num) {
    // java int is used for storing uint16 from / for network bufferes
    // extract two bytes from the 4 byte java int
    byte[] bytes = new byte[2];
    bytes[0] = (byte) (num & 0xFF);
    bytes[1] = (byte) ((num >> 8) & 0xFF);

    return bytes;
  }

  public static byte getUInt8(short num) {
    // java short is used for storing uint8 from / for network bufferes
    // extract one byte from the 2 byte java short
    byte b = (byte) (num & 0xFF);
    return b;
  }

  public static long uid2long(String uid) {
    long uidTmp = base58Decode(uid);
    if (uidTmp > 0xFFFFFFFFL) {
      // convert from 64bit to 32bit
      long value1 = uidTmp & 0xFFFFFFFFL;
      long value2 = (uidTmp >> 32) & 0xFFFFFFFFL;

      uidTmp = (value1 & 0x00000FFFL);
      uidTmp |= (value1 & 0x0F000000L) >> 12;
      uidTmp |= (value2 & 0x0000003FL) << 16;
      uidTmp |= (value2 & 0x000F0000L) << 6;
      uidTmp |= (value2 & 0x3F000000L) << 2;
    }
    return uidTmp;
  }

  public static short unsignedByte(byte data) {
    return (short) ((short) data & 0xFF);
  }

  public static String base58Encode(long value) {
    String encoded = "";

    while (value >= 58) {
      long div = value / 58;
      int mod = (int) (value % 58);
      encoded = BASE58.charAt(mod) + encoded;
      value = div;
    }

    return BASE58.charAt((int) value) + encoded;
  }

  private static long base58Decode(String encoded) {
    long value = 0;
    long columnMultiplier = 1;

    for (int i = encoded.length() - 1; i >= 0; i--) {
      int column = BASE58.indexOf(encoded.charAt(i));

      if (column < 0) {
        throw new IllegalArgumentException("Invalid Base58 value: " + encoded.charAt(i));
      }

      value += column * columnMultiplier;
      columnMultiplier *= 58;
    }

    return value;
  }

  public static JsonObject getConfigFromFile(String fileName) {
    JsonObject conf = null;

    if (fileName != null) {
      File confFile = new File(fileName);
      try (Scanner scanner = new Scanner(confFile).useDelimiter("\\A")) {
        String sconf = scanner.next();
        try {
          conf = new JsonObject(sconf);
        } catch (DecodeException e) {
          System.out
              .println("Configuration file " + sconf + " does not contain a valid JSON object");
        }
      } catch (FileNotFoundException e) {
        try {
          conf = new JsonObject(fileName);
        } catch (DecodeException e2) {
          System.out.println("no file or valid JSON object: " + fileName);
        }
      }
    } else {
      conf = null;
    }
    return conf;
  }

  public static int getRandomInt() {
    return 100;
  }

  public static long getRandomLong() {
    return 100L;
  }

  public static short getRandomShort() {
    return 100;
  }

  public static float getRandomFloat() {
    return (float) 100.0;
  }

  public static char getRandomChar() {
    return 'a';
  }

  public static boolean getRandomBoolean() {
    return false;
  }

  public static String getRandomString() {
    return "abc";
  }

  public static byte getRandomByte() {
    return 100;
  }
}
