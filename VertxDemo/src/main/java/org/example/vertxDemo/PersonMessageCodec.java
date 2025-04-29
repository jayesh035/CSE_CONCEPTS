package org.example.vertxDemo;

import Util.Person;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class PersonMessageCodec implements MessageCodec<Person, Person> {

    @Override
    public void encodeToWire(Buffer buffer, Person person) {
        String jsonStr = person.toJson().encode();
        buffer.appendInt(jsonStr.length());
        buffer.appendString(jsonStr);
    }

    @Override
    public Person decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        String jsonStr = buffer.getString(pos + 4, pos + 4 + length);
        return Person.fromJson(jsonStr);
    }

    @Override
    public Person transform(Person person) {
        // Deep copy for local messages
        return new Person(person.getName(), person.getAge());
    }

    @Override
    public String name() {
        return "personCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1; // User codec
    }
}
