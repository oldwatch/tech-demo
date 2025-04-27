//package com.demo.kafka.tools.service;
//
//import com.demo.kafka.tools.helper.Utils;
//
//import java.util.Map;
//
//public record ConsumerConfig(int offset, boolean fromBegin, int number) implements Config {
//
//    public ConsumerConfig(Map<String, String> additions) {
//        var offset = Utils.getIntValue(additions.get("offset"), 0);
//        var fromBegin = Utils.getBooleanValue(additions.get("fromBegin"), false);
//        var number = Utils.getIntValue(additions.get("number"), 50);
//        this(offset, fromBegin, number);
//    }
//
//    @Override
//    public String getDetail() {
//        return "";
//    }
//}
