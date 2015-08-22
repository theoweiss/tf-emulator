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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AsyncResult;
import io.vertx.core.AsyncResultHandler;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxException;
import io.vertx.core.impl.Args;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

public class Starter {
  private static final Logger log = LoggerFactory.getLogger(Starter.class);
  private final CountDownLatch stopLatch = new CountDownLatch(1);

  public static void main(String[] sargs) {
    Args args = new Args(sargs);
    new Starter().run(args);
  }

  private void run(Args args) {
    String main = readMainVerticleFromManifest();

    if (main != null) {
      runVerticle(main, args);
    }
  }

  private void runVerticle(String main, Args args) {
    Vertx vertx = Vertx.vertx();
    if (vertx == null) {
      // Throwable should have been logged at this point
      return;
    }

    String confArg = args.map.get("-conf");
    JsonObject conf;

    if (confArg != null) {
      try (Scanner scanner = new Scanner(new File(confArg)).useDelimiter("\\A")) {
        String sconf = scanner.next();
        try {
          conf = new JsonObject(sconf);
        } catch (DecodeException e) {
          log.error("Configuration file {} does not contain a valid JSON object", sconf);
          return;
        }
      } catch (FileNotFoundException e) {
        try {
          conf = new JsonObject(confArg);
        } catch (DecodeException e2) {
          log.error("-conf option does not point to a file and is not valid JSON: {}", confArg);
          return;
        }
      }
    } else {
      displayUsage();
      return;
    }
    DeploymentOptions deploymentOptions = new DeploymentOptions();
    deploymentOptions.setConfig(conf);
    String message = "deploying emulator verticle";
    vertx.deployVerticle(main, deploymentOptions, createLoggingHandler(message, res -> {
      if (res.failed()) {
        // Failed to deploy
        unblock();
      }
    }));

    addShutdownHook(vertx);
    block();
  }

  private void addShutdownHook(Vertx vertx) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        final CountDownLatch latch = new CountDownLatch(1);
        vertx.close(ar -> {
          if (!ar.succeeded()) {
            log.error("Failure in stopping Vert.x", ar.cause());
          }
          latch.countDown();
        });
        try {
          if (!latch.await(2, TimeUnit.MINUTES)) {
            log.error("Timed out waiting to undeploy all");
          }
        } catch (InterruptedException e) {
          throw new IllegalStateException(e);
        }
      }
    });
  }

  public void block() {
    try {
      stopLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void unblock() {
    stopLatch.countDown();
  }

  private void displayUsage() {
    String usage = "    tfemulator -conf <config>  Specifies configuration that should be provided";
    log.info(usage);
  }

  private <T> AsyncResultHandler<T> createLoggingHandler(final String message,
      final Handler<AsyncResult<T>> completionHandler) {
    return res -> {
      if (res.failed()) {
        Throwable cause = res.cause();
        if (cause instanceof VertxException) {
          VertxException ve = (VertxException) cause;
          log.error(ve.getMessage());
          if (ve.getCause() != null) {
            log.error("cause: {}", ve.getCause());
          }
        } else {
          log.error("Failed in {}", message, cause);
        }
      } else {
        log.info("Succeeded in {}", message);
      }
      if (completionHandler != null) {
        completionHandler.handle(res);
      }
    };
  }

  private String readMainVerticleFromManifest() {
    try {
      Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
      while (resources.hasMoreElements()) {
        Manifest manifest = new Manifest(resources.nextElement().openStream());
        Attributes attributes = manifest.getMainAttributes();
        String mainClass = attributes.getValue("Main-Class");
        if (Starter.class.getName().equals(mainClass)) {
          String theMainVerticle = attributes.getValue("Main-Verticle");
          if (theMainVerticle != null) {
            return theMainVerticle;
          }
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
    return null;
  }


}
