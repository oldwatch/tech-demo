package io.micrometer.meter.influx3;//


//import module influxdb3.java;

import module java.base;
import module micrometer.core;
import module org.slf4j;
import com.influxdb.v3.client.InfluxDBClient;
import com.influxdb.v3.client.Point;
import io.micrometer.common.util.StringUtils;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Timer;

public class Influx3MeterRegistry extends StepMeterRegistry {

    private static final ThreadFactory DEFAULT_THREAD_FACTORY = Thread.ofPlatform()
            .daemon()
            .name("influx3-meter-pool", 1).factory();
    private final static Logger logger = LoggerFactory.getLogger(Influx3MeterRegistry.class);

    private final Influx3Config config;
    private final InfluxDBClient client;

    private Influx3MeterRegistry(Influx3Config config, Clock clock, ThreadFactory threadFactory) {
        super(config, clock);
        this.config().namingConvention(new Influx3NamingConvention());
        this.config = config;
        this.client = InfluxDBClient.getInstance(config.getClientConfig());
        this.start(threadFactory);
    }

    public static Builder builder(Influx3Config config) {
        return new Builder(config);
    }

    public void start(ThreadFactory threadFactory) {
        super.start(threadFactory);
        if (this.config.enabled()) {
            logger.info("Using InfluxDB API version 3 to write metrics");
        }

    }


    @Override
    protected void publish() {

        try {
            for (var batch : MeterPartition.partition(this, this.config.batchSize())) {

                var points = batch.stream().flatMap(
                        (m) -> m.match(
                                (gauge) -> this.writeGauge(gauge.getId(), gauge.value()),
                                (counter) -> this.writeCounter(counter.getId(), counter.count()),
                                this::writeTimer,
                                this::writeSummary,
                                this::writeLongTaskTimer,
                                (gauge) -> this.writeGauge(gauge.getId(), gauge.value(this.getBaseTimeUnit())),
                                (counter) -> this.writeCounter(counter.getId(), counter.count()),
                                this::writeFunctionTimer,
                                this::writeMeter
                        )
                ).peek(s -> logger.info("point:{}", s.toLineProtocol())).toList();

                client.writePoints(points);
            }
        } catch (Throwable e) {
            logger.error("failed to send metrics to influx", e);
        }

    }

    private Stream<Point> writeMeter(Meter m) {
        List<Field> fields = new ArrayList<>();

        for (Measurement measurement : m.measure()) {
            double value = measurement.getValue();
            if (Double.isFinite(value)) {
                String fieldKey = measurement.getStatistic().getTagValueRepresentation().replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase(Locale.ROOT);
                fields.add(new Field(fieldKey, value));
            }
        }

        if (fields.isEmpty()) {
            return Stream.empty();
        } else {
            Meter.Id id = m.getId();
            return Stream.of(this.influxLineProtocol(id, id.getType().name().toLowerCase(Locale.ROOT), fields.stream()));
        }
    }

    private Stream<Point> writeLongTaskTimer(LongTaskTimer timer) {
        Stream<Field> fields = Stream.of(new Field("active_tasks", (double) timer.activeTasks()), new Field("duration", timer.duration(this.getBaseTimeUnit())));
        return Stream.of(this.influxLineProtocol(timer.getId(), "long_task_timer", fields));
    }

    private Stream<Point> writeCounter(Meter.Id id, double count) {
        return Double.isFinite(count) ?
                Stream.of(this.influxLineProtocol(id, "counter", Stream.of(new Field("value", count)))) : Stream.empty();
    }

    private Stream<Point> writeGauge(Meter.Id id, Double value) {
        return Double.isFinite(value) ? Stream.of(this.influxLineProtocol(id, "gauge", Stream.of(new Field("value", value)))) : Stream.empty();
    }

    private Stream<Point> writeFunctionTimer(FunctionTimer timer) {
        double sum = timer.totalTime(this.getBaseTimeUnit());
        if (Double.isFinite(sum)) {
            Stream.Builder<Field> builder = Stream.builder();
            builder.add(new Field("sum", sum));
            builder.add(new Field("count", timer.count()));
            double mean = timer.mean(this.getBaseTimeUnit());
            if (Double.isFinite(mean)) {
                builder.add(new Field("mean", mean));
            }

            return Stream.of(this.influxLineProtocol(timer.getId(), "histogram", builder.build()));
        } else {
            return Stream.empty();
        }
    }

    private Stream<Point> writeTimer(Timer timer) {
        Stream<Field> fields = Stream.of(new Field("sum", timer.totalTime(this.getBaseTimeUnit())), new Field("count", (double) timer.count()), new Field("mean", timer.mean(this.getBaseTimeUnit())), new Field("upper", timer.max(this.getBaseTimeUnit())));
        return Stream.of(this.influxLineProtocol(timer.getId(), "histogram", fields));
    }

    private Stream<Point> writeSummary(DistributionSummary summary) {
        Stream<Field> fields = Stream.of(new Field("sum", summary.totalAmount()), new Field("count", (double) summary.count()), new Field("mean", summary.mean()), new Field("upper", summary.max()));
        return Stream.of(this.influxLineProtocol(summary.getId(), "histogram", fields));
    }

    private String influxLineProtocolForClient(Meter.Id id, String metricType, Stream<Field> fields) {
        String tags = (String)
                this.getConventionTags(id).stream()
                        .filter((t) -> StringUtils.isNotBlank(t.getValue()))
                        .map((t) -> "," + t.getKey() + "=" + t.getValue())
                        .collect(Collectors.joining(""));
        return this.getConventionName(id) + tags + ",metric_type=" + metricType + " " + (String) fields.map(Field::toString).collect(Collectors.joining(",")) + " " + this.clock.wallTime();
    }

    private Point influxLineProtocol(Meter.Id id, String metricType, Stream<Field> fields) {

        var point = Point.measurement(this.getConventionName(id));

        this.getConventionTags(id).stream()
                .filter(t -> StringUtils.isNotBlank(t.getValue()))
                .forEach(t -> {
                    point.setTag(t.getKey(), t.getValue());
                });

        point.setTag("metric_type", metricType);

        fields.forEach(f -> {
            point.setField(f.key, f.value);
        });

        point.setTimestamp(Instant.ofEpochMilli(this.clock.wallTime()));

        return point;
    }

    @Override
    protected final TimeUnit getBaseTimeUnit() {
        return TimeUnit.MILLISECONDS;
    }

    public static class Builder {
        private final Influx3Config config;
        private final Clock clock;
        private final ThreadFactory threadFactory;

        Builder(Influx3Config config) {
            this.clock = Clock.SYSTEM;
            this.threadFactory = Influx3MeterRegistry.DEFAULT_THREAD_FACTORY;
            this.config = config;
        }


        public Influx3MeterRegistry build() {
            return new Influx3MeterRegistry(this.config, this.clock, this.threadFactory);
        }
    }

    record Field(String key, double value) {
        Field(String key, double value) {
            if (key.equals("time")) {
                throw new IllegalArgumentException("'time' is an invalid field key in InfluxDB");
            } else {
                this.key = key;
                this.value = value;
            }
        }

    }
}
