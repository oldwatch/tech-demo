package io.micrometer.meter.influx3;

import org.springframework.boot.actuate.autoconfigure.metrics.export.ConditionalOnEnabledMetricsExport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(SpringInflux3Properties.class)
@ConditionalOnEnabledMetricsExport("influx3")
public class SpringInflux3Configuration {

    @Bean
    public Influx3Config influx3RegistryConfig(SpringInflux3Properties properties) {
        return new SpringInflux3ConfigAdapter(properties);
    }

    @Bean
    public Influx3MeterRegistry influx3MeterRegistry(Influx3Config influx3Config) {
        return Influx3MeterRegistry.builder(influx3Config).build();
    }
}
