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
import com.tinkerforge.BrickIMUV2;
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BrickIMUV2Test {

  Vertx vertx;
  IPConnection ipcon;
  BrickIMUV2 device;

  @Before
  public void before(TestContext context) {
    String uid = "imu2";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray().add(
        new JsonObject().put("type", "BrickIMUV2").put("uid", uid).put("enabled", true)));
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
    device = new BrickIMUV2(uid, ipcon);
  }

  @After
  public void after(TestContext context) {
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testgetLinearAcceleration(TestContext context) {
    try {
      BrickIMUV2.LinearAcceleration value = device.getLinearAcceleration();
      context.assertEquals(Utils.getRandomShort(), value.x);
      context.assertEquals(Utils.getRandomShort(), value.y);
      context.assertEquals(Utils.getRandomShort(), value.z);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetAngularVelocity(TestContext context) {
    try {
      BrickIMUV2.AngularVelocity value = device.getAngularVelocity();
      context.assertEquals(Utils.getRandomShort(), value.x);
      context.assertEquals(Utils.getRandomShort(), value.y);
      context.assertEquals(Utils.getRandomShort(), value.z);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetOrientation(TestContext context) {
    try {
      BrickIMUV2.Orientation value = device.getOrientation();
      context.assertEquals(Utils.getRandomShort(), value.heading);
      context.assertEquals(Utils.getRandomShort(), value.roll);
      context.assertEquals(Utils.getRandomShort(), value.pitch);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetTemperature(TestContext context) {
    try {
      byte value = device.getTemperature();
      context.assertEquals(Utils.getRandomByte(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetAllData(TestContext context) {
    try {
      BrickIMUV2.AllData value = device.getAllData();
            for (int i = 0; i < value.acceleration.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.acceleration[i]);
      }
      for (int i = 0; i < value.magneticField.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.magneticField[i]);
      }
      for (int i = 0; i < value.angularVelocity.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.angularVelocity[i]);
      }
      for (int i = 0; i < value.eulerAngle.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.eulerAngle[i]);
      }
      for (int i = 0; i < value.quaternion.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.quaternion[i]);
      }
      for (int i = 0; i < value.linearAcceleration.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.linearAcceleration[i]);
      }
      for (int i = 0; i < value.gravityVector.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.gravityVector[i]);
      }
context.assertEquals(Utils.getRandomByte(), value.temperature);
      context.assertEquals(Utils.getRandomShort(), value.calibrationStatus);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetQuaternion(TestContext context) {
    try {
      BrickIMUV2.Quaternion value = device.getQuaternion();
      context.assertEquals(Utils.getRandomShort(), value.w);
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
      BrickIMUV2.MagneticField value = device.getMagneticField();
      context.assertEquals(Utils.getRandomShort(), value.x);
      context.assertEquals(Utils.getRandomShort(), value.y);
      context.assertEquals(Utils.getRandomShort(), value.z);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetGravityVector(TestContext context) {
    try {
      BrickIMUV2.GravityVector value = device.getGravityVector();
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
      BrickIMUV2.Acceleration value = device.getAcceleration();
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
