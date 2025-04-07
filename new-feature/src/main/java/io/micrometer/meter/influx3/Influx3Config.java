package io.micrometer.meter.influx3;

import com.influxdb.v3.client.config.ClientConfig;
import com.influxdb.v3.client.write.WritePrecision;
import io.micrometer.core.instrument.config.MeterRegistryConfigValidator;
import io.micrometer.core.instrument.config.validate.PropertyValidator;
import io.micrometer.core.instrument.config.validate.Validated;
import io.micrometer.core.instrument.step.StepRegistryConfig;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Objects;

import static io.micrometer.core.instrument.config.validate.PropertyValidator.getDuration;


public interface Influx3Config extends StepRegistryConfig {

    Influx3Config DEFAULT = (k) -> null;

    default String prefix() {
        return "influx3";
    }

    default String db() {
        return PropertyValidator.getString(this, "db").orElse("mydb");
    }

    default String token() {
        return PropertyValidator.getString(this, "token").orElse(null);
    }

    default Duration timeout() {
        return getDuration(this, "timeout").orElse(Duration.ofSeconds(1));
    }

    default String uri() {
        return PropertyValidator.getUrlString(this, "uri").orElse("http://localhost:8181");
    }


    default ClientConfig getClientConfig() {

        return new ClientConfig.Builder()
                .token(Objects.requireNonNull(token()).toCharArray())
                .host(uri())
                .database(db())
                .writePrecision(WritePrecision.MS)
                .gzipThreshold(65536)
                .timeout(this.timeout())
                .build();

    }


    @Nonnull
    default Validated<?> validate() {
        return MeterRegistryConfigValidator.checkAll(this, (c) -> StepRegistryConfig.validate(c),
                MeterRegistryConfigValidator.checkRequired("db", Influx3Config::db),
                MeterRegistryConfigValidator.checkRequired("token", Influx3Config::token),
//                MeterRegistryConfigValidator.checkRequired("table", Influx3Config::table),
                MeterRegistryConfigValidator.checkRequired("uri", Influx3Config::uri));
    }
}
