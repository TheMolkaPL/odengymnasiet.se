package se.odengymnasiet;

import java.util.HashMap;

public class Attributes extends HashMap<String, Object> {

    protected Attributes() {
    }

    public Attributes add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public static Attributes create() {
        return new Attributes();
    }

    public static Attributes create(String key, Object value) {
        return create().add(key, value);
    }
}