package org.example.vertxDemo;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class RequestReplyExample extends AbstractVerticle {
    @Override
    public void start() {

        // Consumer that replies
        vertx.eventBus().consumer("reply.address", message -> {
            System.out.println("Received request: " + message.body());
//            message.reply("Reply from 1 -> " + message.body());
        });

        // Consumer that replies
        vertx.eventBus().consumer("reply.address", message -> {
            System.out.println("Received request: " + message.body());
//            message.reply("Reply from 2 -> " + message.body());
        });

        // Requesting message and handling reply

        for(int i=0;i<4;i++)
        {

            vertx.eventBus().request("reply.address", "Hello from sender "+i, reply -> {
                if (reply.succeeded()) {
                    System.out.println("Got reply: " + reply.result().body());
                } else {
                    System.out.println("Failed to get reply: " + reply.cause());
                }
            });

        }

    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new RequestReplyExample());
    }
}

