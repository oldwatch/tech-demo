package com.demo.kafka.tools;

import com.demo.kafka.tools.entity.DataEntity;
import com.demo.kafka.tools.entity.StatusResult;
import com.demo.kafka.tools.entity.SummaryEntity;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

@Configuration
public class StreamProductFactory {


    private final static Logger log = LoggerFactory.getLogger(StreamProductFactory.class);

    private final Serde<String> STRING_SERDE = Serdes.String();


    private final JsonSerde serde_entity = new JsonSerde<>(DataEntity.class);

    private final JsonSerde serde_summary = new JsonSerde<>(SummaryEntity.class);


    public StreamProductFactory() {

        Map<String, ?> props = Map.of(JsonDeserializer.TYPE_MAPPINGS,
                "entity: com.demo.kafka.tools.entity.DataEntity," +
                        "summary: com.demo.kafka.tools.entity.SummaryEntity," +
                        "status: com.demo.kafka.tools.entity.StatusResult",
                JsonDeserializer.TRUSTED_PACKAGES,
                "com.demo.kafka.tools.entity",
                JsonDeserializer.USE_TYPE_INFO_HEADERS,
                true,
                JsonSerializer.ADD_TYPE_INFO_HEADERS,
                true
        );
        serde_entity.configure(props, false);
        serde_summary.configure(props, false);
    }


    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
//        props.put(APPLICATION_ID_CONFIG, "streams-test");
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());

        return new KafkaStreamsConfiguration(props);
    }


    @Bean
    public FactoryBean<StreamsBuilder> simpleKafkaStreamBuilder(KafkaStreamsConfiguration streamsConfig) {
        streamsConfig.asProperties().setProperty(APPLICATION_ID_CONFIG, "streams-test");

        var builder = new StreamsBuilderFactoryBean(streamsConfig);
        builder.setAutoStartup(false);

        return builder;
    }

    @Bean
    public FactoryBean<StreamsBuilder> branchedKafkaStreamBuilder(KafkaStreamsConfiguration streamsConfig) {
        streamsConfig.asProperties().setProperty(APPLICATION_ID_CONFIG, "branchs-test");

        var builder = new StreamsBuilderFactoryBean(streamsConfig);
        builder.setAutoStartup(false);

        return builder;
    }

    @Bean
    public KStream<String, DataEntity> numCountStream(@Qualifier("simpleKafkaStreamBuilder") StreamsBuilder streamsBuilder) {

        KStream<String, DataEntity> messageStream = streamsBuilder
                .stream("test-topic", Consumed.with(STRING_SERDE, serde_entity));

        messageStream
                .filter((s, e) -> e.text() != null && !e.text().isBlank())
                .map((s, dataEntity) -> {
                            log.info("data:{}", dataEntity);
                            return new KeyValue<>(dataEntity.text(), dataEntity.val());
                        }
                )
                .groupByKey()
                .aggregate(SummaryEntity::new,
                        (s, integer, summary) -> summary.add(s, integer),
                        Materialized.<String, SummaryEntity, KeyValueStore<Bytes, byte[]>>as("test-aggregate-store")
                                .withKeySerde(STRING_SERDE)
                                .withValueSerde(serde_summary)
                )
                .toStream()
                .to("count-topic", Produced.with(STRING_SERDE, serde_summary));

        return messageStream;
    }

    @Bean
    public KStream<String, StatusResult> splitResultStatus(@Qualifier("branchedKafkaStreamBuilder") StreamsBuilder streamsBuilder) {

        KStream<String, DataEntity> messageStream = streamsBuilder
                .stream("test-topic", Consumed.with(STRING_SERDE, serde_entity));

        KStream<String, DataEntity> statusStream = streamsBuilder.stream("test-store-topic", Consumed.with(STRING_SERDE, serde_entity));

        KStream<String, StatusResult> resultStream = messageStream.join(statusStream,
                (entity1, entity2) -> new StatusResult(entity1, entity2),
                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(5)),
                StreamJoined.with(STRING_SERDE, serde_entity, serde_entity));

        return new KafkaStreamBrancher<String, StatusResult>()
                .branch((key, value) -> value.status() == DataEntity.Status.End,
                        ks -> ks.to("status-end-topic"))
                .branch((key, value) -> value.status() == DataEntity.Status.Error,
                        ks -> ks.to("status-error-topic"))
                .defaultBranch(ks -> ks.to("status-other-topic"))
                .onTopOf(resultStream);

//        return resultStream;
    }


}
