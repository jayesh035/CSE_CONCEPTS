package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;

public class ReceiverVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.eventBus().consumer("point.to.point", message -> {
            System.out.println("Receiver got message: " + message.body());
            message.reply("Hi back from receiver!");
        });
    }
}
