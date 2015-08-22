package org.m1theo.tfemulator.testshandmade;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.m1theo.tfemulator.Utils;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.IPConnection;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BrickletColorTest {

  Vertx vertx;
  IPConnection ipcon;
  BrickletColor device;

  @Before
  public void before(TestContext context) {
    String uid = "col";
    String host = "localhost";
    int port = 1234;
    JsonObject emuconfig = new JsonObject().put("devices", new JsonArray()
        .add(new JsonObject().put("type", "BrickletColor").put("uid", uid).put("enabled", true)));
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
    device = new BrickletColor(uid, ipcon);
  }

  @After
  public void after(TestContext context) {
    // ipcon.disconnect();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testgetIlluminance(TestContext context) {
    try {
      context.assertEquals(device.getIlluminance(), (long) 100);
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetColorTemperature(TestContext context) {
    try {
      int value = device.getColorTemperature();
      context.assertEquals(Utils.getRandomInt(), value);
    } catch (Exception e) {
      context.fail(e);
    }
  }

  @Test
  public void testgetColor(TestContext context) {
    try {
      com.tinkerforge.BrickletColor.Color color = device.getColor();
      context.assertEquals(Utils.getRandomInt(), color.r);
      context.assertEquals(Utils.getRandomInt(), color.b);
      context.assertEquals(Utils.getRandomInt(), color.g);
      context.assertEquals(Utils.getRandomInt(), color.c);
    } catch (Exception e) {
      context.fail(e);
    }
  }

  public class Color {
    public int r;
    public int g;
    public int b;
    public int c;

    public String toString() {
      return "[" + "r = " + r + ", " + "g = " + g + ", " + "b = " + b + ", " + "c = " + c + "]";
    }
  }

  public class ColorCallbackThreshold {
    public char option;
    public int minR;
    public int maxR;
    public int minG;
    public int maxG;
    public int minB;
    public int maxB;
    public int minC;
    public int maxC;

    public String toString() {
      return "[" + "option = " + option + ", " + "minR = " + minR + ", " + "maxR = " + maxR + ", "
          + "minG = " + minG + ", " + "maxG = " + maxG + ", " + "minB = " + minB + ", " + "maxB = "
          + maxB + ", " + "minC = " + minC + ", " + "maxC = " + maxC + "]";
    }
  }

  public class Config {
    public short gain;
    public short integrationTime;

    public String toString() {
      return "[" + "gain = " + gain + ", " + "integrationTime = " + integrationTime + "]";
    }
  }
}
