package org.example.vertxDemo;

import io.vertx.core.*;

public class PointToPointExample extends AbstractVerticle {
    @Override
    public void start() {
        vertx.eventBus().consumer("task.address", message -> {
            System.out.println("Consumer 1 received: " + message.body());
            message.reply("hiii");
        });

        vertx.eventBus().consumer("task.address", message -> {
            System.out.println("Consumer 2 received: " + message.body());
        });

        // Sending 4 messages, will be distributed between consumers
        for (int i = 1; i <= 4; i++) {
            vertx.eventBus().send("task.address", "Task " + i);
        }
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new PointToPointExample());
    }
}
