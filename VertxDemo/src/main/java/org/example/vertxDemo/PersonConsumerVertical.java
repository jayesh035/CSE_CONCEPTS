package org.example.vertxDemo;

import Util.Person;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class PersonConsumerVertical extends AbstractVerticle {

    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();

        // Register the codec once for Person class
        eventBus.registerDefaultCodec(Person.class, new PersonMessageCodec());

        eventBus.consumer("person.request", message -> {
            // Safely cast the incoming message to Person
            Person person = (Person) message.body();

//            JsonObject data=(JsonObject) message.body();

            System.out.println("Received person: " + person.toString());

            // Optionally send a reply
            Person reply = new Person("Hello " + person.getName(), person.getAge() + 1);
            message.reply(reply);  // This will also go through PersonMessageCodec
//            System.out.println("send "+ reply);
        });
    }
}
