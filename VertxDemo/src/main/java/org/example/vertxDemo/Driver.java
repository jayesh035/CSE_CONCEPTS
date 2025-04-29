package org.example.vertxDemo;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Driver {


    public static void main(String[] args) {

        Vertx vertx=Vertx.vertx();
//        Vertx vertx1=Vertx.vertx();
        vertx.deployVerticle(new HttpServer());
        vertx.deployVerticle(new HttpServer());
        vertx.deployVerticle(HttpServer.class.getName(), new DeploymentOptions().setInstances(7));


    }
}
