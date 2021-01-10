package org.mcallydevelops;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Context {

    private final ObjectMapper objectMapper;

    public Context(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static Context createDefaultContext() {
        var objectMapper = new ObjectMapper();
        return new Context(objectMapper);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
