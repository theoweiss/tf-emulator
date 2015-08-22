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
package org.m1theo.tfemulator.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.m1theo.tfemulator.Utils;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BrickletGPSTest {

  Vertx vertx;
  IPConnection ipcon;
  BrickletGPS device;

  @Before
  public void before(TestContext context) {
    String uid = "gps";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray().add(
        new JsonObject().put("type", "BrickletGPS").put("uid", uid).put("enabled", true)));
    System.out.println(emuconfig.encodePrettily());
    DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(emuconfig);

    vertx = Vertx.vertx();
    Async async = context.async();
    vertx.deployVerticle("org.m1theo.tfemulator.Brickd", deploymentOptions, res -> {
      async.complete();
    });
    ipcon = new IPConnection(); // Create IP connection
    try {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      ipcon.connect(host, port);
    } catch (AlreadyConnectedException | IOException e) {
      context.fail(e);
    }
    device = new BrickletGPS(uid, ipcon);
  }

  @After
  public void after(TestContext context) {
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testgetDateTime(TestContext context) {
    try {
      BrickletGPS.DateTime value = device.getDateTime();
      context.assertEquals(Utils.getRandomLong(), value.date);
      context.assertEquals(Utils.getRandomLong(), value.time);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetStatus(TestContext context) {
    try {
      BrickletGPS.Status value = device.getStatus();
      context.assertEquals(Utils.getRandomShort(), value.fix);
      context.assertEquals(Utils.getRandomShort(), value.satellitesView);
      context.assertEquals(Utils.getRandomShort(), value.satellitesUsed);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetMotion(TestContext context) {
    try {
      BrickletGPS.Motion value = device.getMotion();
      context.assertEquals(Utils.getRandomLong(), value.course);
      context.assertEquals(Utils.getRandomLong(), value.speed);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetAltitude(TestContext context) {
    try {
      BrickletGPS.Altitude value = device.getAltitude();
      context.assertEquals(Utils.getRandomLong(), value.altitude);
      context.assertEquals(Utils.getRandomLong(), value.geoidalSeparation);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetCoordinates(TestContext context) {
    try {
      BrickletGPS.Coordinates value = device.getCoordinates();
      context.assertEquals(Utils.getRandomLong(), value.latitude);
      context.assertEquals(Utils.getRandomChar(), value.ns);
      context.assertEquals(Utils.getRandomLong(), value.longitude);
      context.assertEquals(Utils.getRandomChar(), value.ew);
      context.assertEquals(Utils.getRandomInt(), value.pdop);
      context.assertEquals(Utils.getRandomInt(), value.hdop);
      context.assertEquals(Utils.getRandomInt(), value.vdop);
      context.assertEquals(Utils.getRandomInt(), value.epe);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }
}
