package org.m1theo.tfemulator.testshandmade;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BrickletAmbientLightTestHandmade {

  Vertx vertx;
  IPConnection ipcon;
  BrickletAmbientLight device;

  @Before
  public void before(TestContext context) {
    String uid = "abc";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray().add(
        new JsonObject().put("type", "BrickletAmbientLight").put("uid", uid).put("enabled", true)));
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
    device = new BrickletAmbientLight(uid, ipcon);
  }

  @After
  public void after(TestContext context) {
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void test1(TestContext context) {
    try {
      context.assertEquals(device.getIlluminance(), 100);
    } catch (Exception e) {
      context.fail(e);
    }
  }

}
