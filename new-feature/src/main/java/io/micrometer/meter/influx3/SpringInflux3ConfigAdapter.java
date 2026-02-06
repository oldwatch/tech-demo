package io.micrometer.meter.influx3;

import module java.base;
import org.springframework.boot.micrometer.metrics.autoconfigure.export.properties.StepRegistryPropertiesConfigAdapter;

public class SpringInflux3ConfigAdapter extends StepRegistryPropertiesConfigAdapter<SpringInflux3Properties> implements Influx3Config {

    public SpringInflux3ConfigAdapter(SpringInflux3Properties properties) {
        super(properties);
    }

    @Override
    public String db() {

        return get(SpringInflux3Properties::getDb, Influx3Config.super::db);
    }

    @Override
    public String token() {

        return get(SpringInflux3Properties::getToken, Influx3Config.super::token);
    }

    @Override
    public Duration timeout() {
        return get(SpringInflux3Properties::getTimeout, Influx3Config.super::timeout);
    }

    @Override
    public String uri() {
        return get(SpringInflux3Properties::getUri, Influx3Config.super::uri);
    }
}

