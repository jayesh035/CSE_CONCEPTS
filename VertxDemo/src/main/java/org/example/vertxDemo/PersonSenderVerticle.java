package org.example.vertxDemo;

import Util.Person;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class PersonSenderVerticle extends AbstractVerticle {

    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();

        // Register the same codec on the sender side
        eventBus.registerDefaultCodec(Person.class, new PersonMessageCodec());

        // Create a sample Person object
        Person person = new Person("John Doe", 35);

        // Send a request to the designated address; Vert.x will route it to the consumer on the cluster


        vertx.setPeriodic(2000,res->{

            eventBus.request("person.request", person
                , reply -> {
            if (reply.succeeded()) {
//                Person replyPerson = (Person) reply.result().body();
                System.out.println("Received reply: " + reply.result().body());
            } else {
                System.err.println("Failed to get reply: " + reply.cause().getMessage());
            }
        }
            );
        });

    }
}
