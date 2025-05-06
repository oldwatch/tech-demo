package com.demo.kafka.tools.service;

import com.demo.kafka.tools.helper.Utils;

import java.util.Arrays;
import java.util.Map;

public sealed interface Config permits ProductConfig, StreamConfig, Config.NullConfig {

    Config NULL_CONFIG = new NullConfig();

    static Config createConfig(String command) {
        return createConfig(Map.of(), "", command);
    }


    static Config createConfig(Map<String, String> params, String text, String command) {

        var cfgOpt = Arrays.stream(Config.class.getPermittedSubclasses())
                .filter(cls -> {
                    var cmdName = Utils.getSimpleClsName(cls.getSimpleName());
                    return command.startsWith(cmdName);
                })
                .findFirst();

        if (cfgOpt.isEmpty()) {
            return Config.NULL_CONFIG;
        }
        var cfgCls = cfgOpt.get();
        try {
            return (Config) cfgCls.getConstructor(Map.class, String.class).newInstance(params, text);
        } catch (ReflectiveOperationException e) {
            return Config.NULL_CONFIG;
        }

    }

    String getDetail();

    record NullConfig() implements Config {

        @Override
        public String getDetail() {
            return "null command";
        }
    }
}
