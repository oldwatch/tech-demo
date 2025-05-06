package com.demo.kafka.tools.service;

import java.util.Map;

public record StreamConfig(String cmd) implements Config {


    public StreamConfig(Map<String, String> additions, String cmd) {
        this(cmd);
    }

    @Override
    public String getDetail() {
        return """
                -------
                """;
    }
}
