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
import com.tinkerforge.BrickMaster;
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BrickMasterTest {

  Vertx vertx;
  IPConnection ipcon;
  BrickMaster device;

  @Before
  public void before(TestContext context) {
    String uid = "mas";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray().add(
        new JsonObject().put("type", "BrickMaster").put("uid", uid).put("enabled", true)));
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
    device = new BrickMaster(uid, ipcon);
  }

  @After
  public void after(TestContext context) {
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testgetUSBVoltage(TestContext context) {
    try {
      int value = device.getUSBVoltage();
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetWifiStatus(TestContext context) {
    try {
      BrickMaster.WifiStatus value = device.getWifiStatus();
            for (int i = 0; i < value.macAddress.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.macAddress[i]);
      }
      for (int i = 0; i < value.bssid.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.bssid[i]);
      }
context.assertEquals(Utils.getRandomShort(), value.channel);
      context.assertEquals(Utils.getRandomShort(), value.rssi);
            for (int i = 0; i < value.ip.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.ip[i]);
      }
      for (int i = 0; i < value.subnetMask.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.subnetMask[i]);
      }
      for (int i = 0; i < value.gateway.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.gateway[i]);
      }
context.assertEquals(Utils.getRandomLong(), value.rxCount);
      context.assertEquals(Utils.getRandomLong(), value.txCount);
      context.assertEquals(Utils.getRandomShort(), value.state);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetChibiErrorLog(TestContext context) {
    try {
      BrickMaster.ChibiErrorLog value = device.getChibiErrorLog();
      context.assertEquals(Utils.getRandomInt(), value.underrun);
      context.assertEquals(Utils.getRandomInt(), value.crcError);
      context.assertEquals(Utils.getRandomInt(), value.noAck);
      context.assertEquals(Utils.getRandomInt(), value.overflow);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetWifiBufferInfo(TestContext context) {
    try {
      BrickMaster.WifiBufferInfo value = device.getWifiBufferInfo();
      context.assertEquals(Utils.getRandomLong(), value.overflow);
      context.assertEquals(Utils.getRandomInt(), value.lowWatermark);
      context.assertEquals(Utils.getRandomInt(), value.used);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetStackVoltage(TestContext context) {
    try {
      int value = device.getStackVoltage();
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetChibiSignalStrength(TestContext context) {
    try {
      short value = device.getChibiSignalStrength();
      context.assertEquals(Utils.getRandomShort(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetRS485ErrorLog(TestContext context) {
    try {
      int value = device.getRS485ErrorLog();
      context.assertEquals(Utils.getRandomInt(), value);
      
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetEthernetStatus(TestContext context) {
    try {
      BrickMaster.EthernetStatus value = device.getEthernetStatus();
            for (int i = 0; i < value.macAddress.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.macAddress[i]);
      }
      for (int i = 0; i < value.ip.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.ip[i]);
      }
      for (int i = 0; i < value.subnetMask.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.subnetMask[i]);
      }
      for (int i = 0; i < value.gateway.length; i++) {
        context.assertEquals(Utils.getRandomShort(), value.gateway[i]);
      }
context.assertEquals(Utils.getRandomLong(), value.rxCount);
      context.assertEquals(Utils.getRandomLong(), value.txCount);
      context.assertEquals(Utils.getRandomString(), value.hostname);
      
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
  public void testgetStackCurrent(TestContext context) {
    try {
      int value = device.getStackCurrent();
      context.assertEquals(Utils.getRandomInt(), value);
      
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
}
