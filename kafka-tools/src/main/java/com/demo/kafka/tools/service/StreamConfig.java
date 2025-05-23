package com.demo.kafka.tools.service;

import java.util.Map;

public record StreamConfig(String cmd, String service, String factory) implements Config {


    public StreamConfig(Map<String, String> additions, String cmd) {
        var service = additions.get("service");
        if (service == null || service.isBlank()) {
            service = "simple";
        }
        this(cmd, service, "&" + service);
    }


    @Override
    public String getDetail() {
        return """
                -------
                """;
    }
}
