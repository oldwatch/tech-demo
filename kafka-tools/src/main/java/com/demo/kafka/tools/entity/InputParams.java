package com.demo.kafka.tools.entity;

import com.demo.kafka.tools.service.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record InputParams(String command, Config config) {


    static Pattern paramReg = Pattern.compile("^\\s*(\\w+)(\\s+(\\S+))?(\\s.+)?$");
    static Pattern argReg = Pattern.compile("^-([^=]+)=(\\S+)");

    public InputParams(String input) {

        var matcher = paramReg.matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException();
        }

        var cmd = matcher.group(1);
        var text = matcher.group(3);
        var params = generAdditions(matcher.groupCount() > 3 ? matcher.group(4) : "");

        var config = Config.createConfig(params, text, cmd);

        this(cmd, config);
    }

    static Map<String, String> generAdditions(String params) {

        if (!(params == null || params.isBlank())) {
            var arrays = params.split("\\s+");
            return Arrays.stream(arrays)
                    .map(argReg::matcher)
                    .map(Arg::new)
                    .filter(r -> !r.name.isBlank())
                    .collect(HashMap::new,
                            (m, a) -> {
                                m.put(a.name(), a.value());
                            },
                            HashMap::putAll);
        } else {
            return Map.of();
        }
    }

    record Arg(String name, String value) {
        Arg(Matcher match) {
            var name = "";
            var value = "";
            if (match.find()) {
                name = match.group(1);
                value = match.group(2);
            }
            this(name, value);
        }
    }


}



