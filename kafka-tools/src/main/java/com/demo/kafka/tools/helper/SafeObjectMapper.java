package com.demo.kafka.tools.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SafeObjectMapper {
    private final ObjectMapper objectMapper;

    public SafeObjectMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T readValue(String str, Class<T> cls) {
        try {
            return objectMapper.readValue(str, cls);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    public String writeValueAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    static class JsonException extends RuntimeException {

        JsonException(JsonProcessingException exception) {
            super(exception);
        }
    }
}
