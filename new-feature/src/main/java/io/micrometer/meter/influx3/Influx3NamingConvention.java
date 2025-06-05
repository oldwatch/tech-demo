package io.micrometer.meter.influx3;

import io.micrometer.common.lang.Nullable;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.NamingConvention;

import java.util.regex.Pattern;

public class Influx3NamingConvention implements NamingConvention {
    private static final Pattern PATTERN_SPECIAL_CHARACTERS = Pattern.compile("([, \\.=\"])");

    public String name(String name, Meter.Type type, @Nullable String baseUnit) {
        return this.escape(name);
    }

    public String tagKey(String key) {
        if (key.equals("time")) {
            throw new IllegalArgumentException("'time' is an invalid tag key in InfluxDB");
        } else {
            return this.escape(key);
        }
    }

    public String tagValue(String value) {
        return this.escape(value);
    }

    private String escape(String string) {

        var var = string.replaceAll("\n", "");

        return PATTERN_SPECIAL_CHARACTERS.matcher(var).replaceAll("_");
    }


}
