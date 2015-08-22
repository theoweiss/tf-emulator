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

import org.m1theo.tfemulator.Utils;

import io.vertx.core.buffer.Buffer;

public class Protocol {
  public static Buffer createHeader(long uid, byte length, byte functionId, byte options,
      byte flags) {
    Buffer buffer = Buffer.buffer();
    // uid
    byte[] uidbytes = Utils.getUInt32(uid);
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

}
