/*
 * Copyright (c) 2015 Thomas Weiss <theo@m1theo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package org.m1theo.tfemulator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;

public class Router {
  private EventBus eb;

  public Router(EventBus eb) {
    this.eb = eb;
  }

  /*
   * Extracts the uid and sends the buffer over the eventbus to device with this uid.
   */
  public void route(Buffer buffer) {
    Logger logger = LoggerFactory.getLogger(getClass());
    byte[] packet = buffer.getBytes();
    // ByteBuffer bb = ByteBuffer.wrap(packet, 8, packet.length - 8);
    ByteBuffer bb = ByteBuffer.wrap(packet);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    int uid = bb.getInt();
    String uidString = Utils.base58Encode(uid);
    logger.trace("routing msg for uid: {}", uidString);
    eb.publish(uidString, buffer);
  }


}
