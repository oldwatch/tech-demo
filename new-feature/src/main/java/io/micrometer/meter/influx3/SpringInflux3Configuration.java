package io.micrometer.meter.influx3;


import module spring.boot.autoconfigure;
import module spring.context;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.micrometer.metrics.autoconfigure.export.ConditionalOnEnabledMetricsExport;

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
