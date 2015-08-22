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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;

public class CommonServices extends AbstractVerticle {
  public static String BROADCAST_UID = "1";
  public final static byte FUNCTION_ENUMERATE = (byte) 254;
  public final static byte CALLBACK_ENUMERATE = (byte) 253;

  @Override
  public void start() throws Exception {
    Logger logger = LoggerFactory.getLogger(getClass());
    EventBus eb = vertx.eventBus();
    eb.consumer(BROADCAST_UID, message -> {
      logger.debug("recieved message: {}", Utils.packetHeader2String((Buffer) message.body()));
    });
    logger.info("CommonService is started!");
  }

}
