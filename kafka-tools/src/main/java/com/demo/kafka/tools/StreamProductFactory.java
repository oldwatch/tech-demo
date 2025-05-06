package com.demo.kafka.tools;

import com.demo.kafka.tools.entity.DataEntity;
import com.demo.kafka.tools.entity.SummaryEntity;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;

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
    private final Serde<Integer> INT_SERDE = Serdes.Integer();

    //    @Bean
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, "streams-test");
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public FactoryBean<StreamsBuilder> localKafkaStreamBuilder(KafkaStreamsConfiguration streamsConfig) {
        var builder = new StreamsBuilderFactoryBean(streamsConfig);
        builder.setAutoStartup(false);

        return builder;
    }


    @Bean
    public KStream<String, DataEntity> numCountStream(@Qualifier("localKafkaStreamBuilder") StreamsBuilder streamsBuilder) {
//
        var serde = new JsonSerde<DataEntity>();
        serde.ignoreTypeHeaders();

        KStream<String, DataEntity> messageStream = streamsBuilder
                .stream("test-topic", Consumed.with(STRING_SERDE, serde));

        messageStream
                .filter((s, e) -> e.text() != null && !e.text().isBlank())
                .map((s, dataEntity) -> {
                            log.info("data:{}", dataEntity);
                            return new KeyValue<>(dataEntity.text(), dataEntity.val());
                        }
                )
                .groupByKey()
                .aggregate(SummaryEntity::new, (s, integer, summary) -> summary.add(s, integer))
                .toStream().to("count-topic");

        return messageStream;
    }


}
