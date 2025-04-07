package com.demo.kafka.tools.service;

import com.demo.kafka.tools.helper.Utils;

import java.util.Map;

public record StoreConfig(String operate) implements Config {

    public StoreConfig(Map<String, String> additions, String param) {
        var operate = Utils.getStrValue(additions.get("oper"), "show");
        this(operate);

    }

    @Override
    public String getDetail() {
        return "";
    }
}
