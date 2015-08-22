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
import com.tinkerforge.BrickServo;
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BrickServoTest {

  Vertx vertx;
  IPConnection ipcon;
  BrickServo device;

  @Before
  public void before(TestContext context) {
    String uid = "ser";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray().add(
        new JsonObject().put("type", "BrickServo").put("uid", uid).put("enabled", true)));
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
    device = new BrickServo(uid, ipcon);
  }

  @After
  public void after(TestContext context) {
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testgetCurrentPosition(TestContext context) {
    try {
      short value = device.getCurrentPosition((short) 1);
      context.assertEquals(Utils.getRandomShort(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetServoCurrent(TestContext context) {
    try {
      int value = device.getServoCurrent((short) 1);
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetChipTemperature(TestContext context) {
    try {
      short value = device.getChipTemperature();
      context.assertEquals(Utils.getRandomShort(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetOverallCurrent(TestContext context) {
    try {
      int value = device.getOverallCurrent();
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetCurrentVelocity(TestContext context) {
    try {
      int value = device.getCurrentVelocity((short) 1);
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetExternalInputVoltage(TestContext context) {
    try {
      int value = device.getExternalInputVoltage();
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetStackInputVoltage(TestContext context) {
    try {
      int value = device.getStackInputVoltage();
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testVelocityReachedCallback(TestContext context) {
    try {
      device.setResponseExpectedAll(true);
      device.enableVelocityReachedCallback(); //enable
      //assert enabled
      short value = device.isVelocityReachedCallbackEnabled();
      context.assertEquals(value, 1);

      device.disableVelocityReachedCallback(); //disable
      // assert disabled
      value = device.isVelocityReachedCallbackEnabled();
      context.assertEquals(value, 0);

    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testStatusLED(TestContext context) {
    try {
      device.setResponseExpectedAll(true);
      device.enableStatusLED(); //enable
      //assert enabled
      boolean value = device.isStatusLEDEnabled();
      context.assertEquals(value, 1);

      device.disableStatusLED(); //disable
      // assert disabled
      value = device.isStatusLEDEnabled();
      context.assertEquals(value, 0);

    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testPositionReachedCallback(TestContext context) {
    try {
      device.setResponseExpectedAll(true);
      device.enablePositionReachedCallback(); //enable
      //assert enabled
      short value = device.isPositionReachedCallbackEnabled();
      context.assertEquals(value, 1);

      device.disablePositionReachedCallback(); //disable
      // assert disabled
      value = device.isPositionReachedCallbackEnabled();
      context.assertEquals(value, 0);

    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testenabled(TestContext context) {
    try {
      device.setResponseExpectedAll(true);
      device.enable(); //enable
      //assert enabled
      boolean value = device.isEnabled();
      context.assertEquals(value, 1);

      device.disable(); //disable
      // assert disabled
      value = device.isEnabled();
      context.assertEquals(value, 0);

    } catch (Exception e) {
      context.fail(e);
    }
  }
}
