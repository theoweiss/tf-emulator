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
package org.m1theo.tfemulator.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.m1theo.tfemulator.Utils;

import io.vertx.core.buffer.Buffer;

public class Packet {

  String uid; // 4 byte
  Buffer length; // 1 byte
  byte functionId; // 1 byte

  // byte squenceNumberAndOptions; // 1 byte
  byte sequenceNumber; // 4 bit
  boolean responseExpected; // 1 bit
  Buffer otherOptions; // 3 bit reserved for future use
  // the whole options block
  byte options;

  // Buffer flags; // 1 byte
  Buffer errorCode; // 2 bit
  // Buffer otherFlags; // 6 bit reserved for future use

  Buffer payload;

  public Packet(Buffer buffer) {
    byte[] data = buffer.getBytes();
    uid = getUidFromData(data);

    length = Buffer.buffer();
    length.appendByte(data[4]);

    functionId = data[5];

    options = data[6];
    sequenceNumber = getSequenceNumberFromData(data);

    responseExpected = getResponseExpectedFromData(data);

    errorCode = Buffer.buffer();
    errorCode.appendByte(getErrorCodeFromData(data));

    if (data.length > 8) {
      payload = Buffer.buffer();
      payload.appendBytes(data, 8, data.length - 8);
    }
  }


  /**
   * @return the uid
   */
  public String getUid() {
    return uid;
  }


  /**
   * @return the length
   */
  public Buffer getLength() {
    return length;
  }


  /**
   * @return the functionId
   */
  public byte getFunctionId() {
    return functionId;
  }

  /**
   * @return the sequenceNumber
   */
  public byte getSequenceNumber() {
    return sequenceNumber;
  }


  /**
   * @return the responseExpected
   */
  public boolean getResponseExpected() {
    return responseExpected;
  }


  /**
   * @return the otherOptions
   */
  public Buffer getOtherOptions() {
    return otherOptions;
  }


  /**
   * @return the errorCode
   */
  public Buffer getErrorCode() {
    return errorCode;
  }


  /**
   * @return the payload
   */
  public Buffer getPayload() {
    return payload;
  }

  private static byte getSequenceNumberFromData(byte[] data) {
    return (byte) ((((int) data[6]) >> 4) & 0x0F);
  }

  private static boolean getResponseExpectedFromData(byte[] data) {
    return ((((int) data[6]) >> 3) & 0x01) == 0x01;
  }

  private static String getUidFromData(byte[] data) {
    ByteBuffer bb = ByteBuffer.wrap(data);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    int uid = bb.getInt();
    return Utils.base58Encode(uid);
  }

  private static byte getErrorCodeFromData(byte[] data) {
    return (byte) ((((int) data[7]) >> 6) & 0x03);
  }


  // This is the sequenceNumber for responses to incoming requests
  // Callback to Enumerate have: sequenceNumber == 0
  private static byte getResponseSequenceNumberFromData(byte[] data) {
    return (byte) ((((int) data[6]) >> 4) & 0x0F);
  }


  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("uid: ");
    sb.append(uid);
    sb.append(" length: ");
    sb.append(length);
    sb.append(" functionid: ");
    sb.append(functionId);
    sb.append(" sequenceNumber: ");
    sb.append(sequenceNumber);
    sb.append(" responseExpected: ");
    sb.append(responseExpected);
    sb.append(" errorCode: ");
    sb.append(errorCode);
    sb.append(" payload length: ");
    if (payload != null) {
      sb.append(payload.length());
    } else {
      sb.append(0);
    }
    return sb.toString();
  }


  public byte getOptions() {
    return options;
  }

}
