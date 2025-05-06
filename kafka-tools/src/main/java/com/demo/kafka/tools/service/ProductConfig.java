package com.demo.kafka.tools.service;

import com.demo.kafka.tools.helper.Utils;

import java.util.Map;

public record ProductConfig(int count, int repeat, String param) implements Config {

    public ProductConfig(Map<String, String> additions, String param) {
        var count = Utils.getIntValue(additions.get("count"), 1);
        var repeat = Utils.getIntValue(additions.get("repeat"), 1);
        this(count, repeat, Utils.removeOuter(param));

    }


    @Override
    public String getDetail() {
        return """
                count: default 1, thread's number.
                repeat: default 10, the messages send count.
                """;
    }
}
