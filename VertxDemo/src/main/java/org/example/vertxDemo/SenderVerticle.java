package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;

public class SenderVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.setPeriodic(2000, id ->
        {
            vertx.eventBus().request(
                    "point.to.point",
                    "Hello from sender!",
                    reply ->
                    {
                if (reply.succeeded())
                {
                    System.out.println("Got reply: " + reply.result().body());
                }
                else
                {
                    System.out.println("No reply: " + reply.cause());
                }
            });
        });
    }
}
