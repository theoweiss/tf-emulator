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
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/*
 * @author Theo Weiss
 */
public class Brickd extends AbstractVerticle {
  public static String HANDLERIDMAP = "handlerids";
  private Logger logger;

  @Override
  public void start() throws Exception {
    logger = LoggerFactory.getLogger(getClass());
    Integer port = config().getInteger("port");
    if (port == null) {
      port = 1234;
    }
    deployDeviceVerticals();
    Router router = new Router(vertx.eventBus());
    vertx.createNetServer().connectHandler(sock -> {
      vertx.sharedData().getLocalMap(HANDLERIDMAP).put(sock.writeHandlerID(), "");
      sock.closeHandler(n -> {
        logger.trace("====removing handlerid on socket close: {}", sock.writeHandlerID());
        vertx.sharedData().getLocalMap(HANDLERIDMAP).remove(sock.writeHandlerID());
      });
      sock.handler(buffer -> {
        router.route(buffer);
        logger.debug(Utils.packetHeader2String(buffer).toString());
      });
    }).listen(port);
    logger.info("Brickd is now listening on port: {}", port);

  }

  private void deployDeviceVerticals() throws BrickdException {
    logger.debug("config: {}", config().encodePrettily());
    JsonArray devicesConf = config().getJsonArray("devices");
    if (devicesConf == null) {
      String msg = "Devices Configuration is missing! Fix config!";
      logger.error(msg);
      throw new BrickdException(msg);
    }

    // deploy common services
    CommonServices commonServices = new CommonServices();
    vertx.deployVerticle(commonServices, new DeploymentOptions().setConfig(config()));

    // deploy configured devices: Bricks and Bricklets
    for (Object deviceObj : devicesConf) {
      JsonObject device = (JsonObject) deviceObj;
      Boolean enabled = device.getBoolean("enabled", false);
      String type = device.getString("type");
      String uid = device.getString("uid");
      if (enabled) {
        vertx.deployVerticle("org.m1theo.tfemulator.devices." + type,
            new DeploymentOptions().setConfig(new JsonObject().put("uid", uid)));
      }
    }
  }
}
