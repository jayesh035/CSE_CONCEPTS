package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;

public class TestVerticle  extends AbstractVerticle {


    @Override
    public void start() throws Exception {


        vertx.setPeriodic(2000,(id)->{

            System.out.println("Hello every 2 seconds" + Thread.currentThread().getName());
        });
    }
}
