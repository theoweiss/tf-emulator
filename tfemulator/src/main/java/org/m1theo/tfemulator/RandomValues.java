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

import io.vertx.core.buffer.Buffer;

/*
 * Not yet used anywhere. Die Idee ist die zufallswerte pro device konfigurierbar zu machen. die
 * geräte bekommen von aussen via rest oder config einen zufallswert vorgegeben. dieser wird dann an
 * die getrandomvalue funktionen uebergeben. das problem sind im moment die datentypen. von json
 * kann man nur eine zahl bekommen und muss diese dann in long/int/short/float uebersetzen. wie man
 * das mit dem generator hinbekommt?
 */
public class RandomValues {
  /*
   * get char values
   */
  public static byte[] getCharRandomValue(int cardinality, char[] randomValue) {
    // char[] randomValue = new char[] {'a'}; // TODO randomize
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
  public static byte[] getBoolRandomValue(int cardinality, short randomValue) {
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendByte(Utils.getUInt8(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint8 compatible byte values cardinality means the number of uint8 values
   */
  public static byte[] get1ByteRandomValue(int cardinality, byte randomValue) {
    // byte randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendByte(randomValue);
    }
    return buffer.getBytes();
  }

  /*
   * get uint8 compatible byte values cardinality means the number of uint8 values
   */
  public static byte[] get1ByteURandomValue(int cardinality, short randomValue) {
    // short randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendByte(Utils.getUInt8(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get int16 compatible byte values cardinality means the number of int16 values
   */
  public static byte[] get2ByteRandomValue(int cardinality, int randomValue) {
    // int randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(Utils.getUInt16(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint16 compatible byte values cardinality means the number of uint16 values
   */
  public static byte[] get2ByteURandomValue(int cardinality, int randomValue) {
    // int randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(Utils.getUInt16(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get int32 compatible byte values cardinality means the number of int32 values
   */
  public static byte[] get4ByteRandomValue(int cardinality, int randomValue) {
    // int randomValue = 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(Utils.getUInt32(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint32 compatible byte values cardinality means the number of uint32 values
   */
  public static byte[] get4ByteURandomValue(int cardinality, long randomValue) {
    // Long randomValue = (long) 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(Utils.getUInt32(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get uint64 compatible byte values cardinality means the number of uint64 values
   */
  public static byte[] get8ByteURandomValue(int cardinality, long randomValue) {
    // Long randomValue = (long) 100; // TODO randomize
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(Utils.getUInt32(randomValue));
    }
    return buffer.getBytes();
  }

  /*
   * get float byte values cardinality means the number of float values
   */
  public static byte[] getFloatRandomValue(int cardinality, float randomValue) {
    // float randomValue = 100; // TODO randomize
    // Buffer buffer = Buffer.buffer();
    // for (int i = 0; i < cardinality - 1; i++) {
    // // TODO not sure if this is right: BrickRED uses bb.getLong in getFileInfo()
    // buffer.appendFloat(0);
    // }
    // buffer.appendFloat(randomValue);
    // return buffer.getBytes();
    Buffer buffer = Buffer.buffer();
    for (int i = 0; i < cardinality; i++) {
      buffer.appendBytes(Utils.getFloat(randomValue));
    }
    return buffer.getBytes();
  }
}
