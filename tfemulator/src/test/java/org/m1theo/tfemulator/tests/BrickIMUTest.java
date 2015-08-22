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
import com.tinkerforge.BrickIMU;
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BrickIMUTest {

  Vertx vertx;
  IPConnection ipcon;
  BrickIMU device;

  @Before
  public void before(TestContext context) {
    String uid = "imu";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray().add(
        new JsonObject().put("type", "BrickIMU").put("uid", uid).put("enabled", true)));
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
    device = new BrickIMU(uid, ipcon);
  }

  @After
  public void after(TestContext context) {
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testgetIMUTemperature(TestContext context) {
    try {
      short value = device.getIMUTemperature();
      context.assertEquals(Utils.getRandomShort(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetQuaternion(TestContext context) {
    try {
      BrickIMU.Quaternion value = device.getQuaternion();
      context.assertEquals(Utils.getRandomFloat(), value.x);
      context.assertEquals(Utils.getRandomFloat(), value.y);
      context.assertEquals(Utils.getRandomFloat(), value.z);
      context.assertEquals(Utils.getRandomFloat(), value.w);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetOrientation(TestContext context) {
    try {
      BrickIMU.Orientation value = device.getOrientation();
      context.assertEquals(Utils.getRandomShort(), value.roll);
      context.assertEquals(Utils.getRandomShort(), value.pitch);
      context.assertEquals(Utils.getRandomShort(), value.yaw);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetAllData(TestContext context) {
    try {
      BrickIMU.AllData value = device.getAllData();
      context.assertEquals(Utils.getRandomShort(), value.accX);
      context.assertEquals(Utils.getRandomShort(), value.accY);
      context.assertEquals(Utils.getRandomShort(), value.accZ);
      context.assertEquals(Utils.getRandomShort(), value.magX);
      context.assertEquals(Utils.getRandomShort(), value.magY);
      context.assertEquals(Utils.getRandomShort(), value.magZ);
      context.assertEquals(Utils.getRandomShort(), value.angX);
      context.assertEquals(Utils.getRandomShort(), value.angY);
      context.assertEquals(Utils.getRandomShort(), value.angZ);
      context.assertEquals(Utils.getRandomShort(), value.temperature);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetAngularVelocity(TestContext context) {
    try {
      BrickIMU.AngularVelocity value = device.getAngularVelocity();
      context.assertEquals(Utils.getRandomShort(), value.x);
      context.assertEquals(Utils.getRandomShort(), value.y);
      context.assertEquals(Utils.getRandomShort(), value.z);
      
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
  public void testgetMagneticField(TestContext context) {
    try {
      BrickIMU.MagneticField value = device.getMagneticField();
      context.assertEquals(Utils.getRandomShort(), value.x);
      context.assertEquals(Utils.getRandomShort(), value.y);
      context.assertEquals(Utils.getRandomShort(), value.z);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetAcceleration(TestContext context) {
    try {
      BrickIMU.Acceleration value = device.getAcceleration();
      context.assertEquals(Utils.getRandomShort(), value.x);
      context.assertEquals(Utils.getRandomShort(), value.y);
      context.assertEquals(Utils.getRandomShort(), value.z);
      
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
context.assertTrue(value);

      device.disableStatusLED(); //disable
      // assert disabled
      value = device.isStatusLEDEnabled();
context.assertFalse(value);

    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testleds(TestContext context) {
    try {
      device.setResponseExpectedAll(true);
      device.ledsOn(); //enable
      //assert enabled
      boolean value = device.areLedsOn();
context.assertTrue(value);

      device.ledsOff(); //disable
      // assert disabled
      value = device.areLedsOn();
context.assertFalse(value);

    } catch (Exception e) {
      context.fail(e);
    }
  }
}
