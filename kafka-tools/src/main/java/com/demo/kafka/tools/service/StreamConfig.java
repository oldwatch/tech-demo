package com.demo.kafka.tools.service;

import com.demo.kafka.tools.helper.Utils;

public record StreamConfig(String cmd, String service, String factory) {


    public StreamConfig(String command) {
        var cmd = Utils.getSecondWord(command);
        var service = Utils.getThirdWord(command);
        this(cmd, service, "&" + service);
    }


}
