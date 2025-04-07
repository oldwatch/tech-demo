package com.demo.kafka.tools.service;


import com.demo.kafka.tools.entity.DataEntity;
import com.demo.kafka.tools.entity.EntityList;
import com.demo.kafka.tools.entity.StatusResult;
import com.demo.kafka.tools.entity.SummaryEntity;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.streams.KafkaStreamsInteractiveQueryService;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.PROCESSING_GUARANTEE_CONFIG;

@Configuration
public class StreamProductFactory {


    public static final String TOP_10_STORE = "top10-store";
    private final static Logger log = LoggerFactory.getLogger(StreamProductFactory.class);
    private final Serde<String> STRING_SERDE = Serdes.String();
    private final JsonSerde<EntityList> serdeList = new JsonSerde<>(EntityList.class);
    private final JsonSerde serde_entity = new JsonSerde<>(DataEntity.class);

    private final JsonSerde serde_summary = new JsonSerde<>(SummaryEntity.class);
    private final Map<String, Object> props = Map.of(
            BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
            DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
            DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName(),
            PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2
    );
    private final ProcessorSupplier<String, DataEntity, String, DataEntity> processorSupplier = new ProcessorSupplier<>() {

        @Override
        public Set<StoreBuilder<?>> stores() {
            var builder = Stores.keyValueStoreBuilder(
                    Stores.persistentKeyValueStore(TOP_10_STORE),
                    Serdes.Integer(),
                    serdeList
            ).withLoggingEnabled(Map.of(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "1"));

            return Set.of(builder);

        }

        @Override
        public Processor<String, DataEntity, String, DataEntity> get() {
            return new Top10SearchProcessor();
        }
    };

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

//    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
//    public KafkaStreamsConfiguration kStreamsConfig() {
//        return new KafkaStreamsConfiguration(props);
//    }

    private KafkaStreamsConfiguration getProp(String appId) {
        var params = new HashMap<>(props);
        params.put(APPLICATION_ID_CONFIG, appId);

        return new KafkaStreamsConfiguration(params);

    }

    @Bean
    public FactoryBean<StreamsBuilder> simpleKafkaStreamBuilder() {
        var builder = new StreamsBuilderFactoryBean(getProp("simple-stream"));
        builder.setAutoStartup(false);

        return builder;
    }

    @Bean
    public FactoryBean<StreamsBuilder> branchedKafkaStreamBuilder() {

        var builder = new StreamsBuilderFactoryBean(getProp("branchs-test"));
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

    @Bean
    public StreamsBuilderFactoryBean toStoreKafkaStreamBuilder() {

        var builder = new StreamsBuilderFactoryBean(getProp("store-test"));
        builder.setAutoStartup(false);

        return builder;
    }

    @Bean
    public KStream<String, DataEntity> toStoreTop10(@Qualifier("toStoreKafkaStreamBuilder") StreamsBuilder streamsBuilder) {


        KStream<String, DataEntity> dataStream = streamsBuilder
                .stream("test-stream-topic", Consumed.with(STRING_SERDE, serde_entity));

        dataStream
                .process(processorSupplier, TOP_10_STORE)
                .to("test-downstream-topic");

        return dataStream;
    }

    @Bean
    public KafkaStreamsInteractiveQueryService kafkaStreamsInteractiveQueryService(@Qualifier("&toStoreKafkaStreamBuilder")
                                                                                   StreamsBuilderFactoryBean streamsBuilderFactoryBean) {

//        streamsBuilderFactoryBean.start();
        return new KafkaStreamsInteractiveQueryService(streamsBuilderFactoryBean);
    }

    private static class Top10SearchProcessor implements Processor<String, DataEntity, String, DataEntity> {
        private KeyValueStore<Integer, EntityList> top10Store;
        private ProcessorContext<String, DataEntity> context;

        @Override
        public void init(final ProcessorContext<String, DataEntity> context) {
            this.context = context;

            top10Store = context.getStateStore(TOP_10_STORE);

            this.context.commit();
        }

        @Override
        public void process(Record<String, DataEntity> record) {

            if (record.value() == null) {
                return; // Skip null purchase amounts
            }

            var val = record.value().val();
            checkItems(val, record.value());
            context.forward(record);
        }


        private void checkItems(int val, DataEntity entity) {

            var sign = false;
            var newList = new EntityList(entity);

            try (var iterator = top10Store.reverseAll()) {

                if (!iterator.hasNext()) {
                    top10Store.put(val, newList);
                    return;
                }

                while (iterator.hasNext()) {

                    var record = iterator.next();
                    log.info(" record key: {}", record.key);
                    var i = record.key;
                    if (i > val) {
                        continue;
                    }
                    if (i < val) {
                        top10Store.put(val, newList);
                    } else {
                        record.value.addEntity(entity);
                    }
                    sign = true;
                    break;
                }

            } catch (InvalidStateStoreException e) {
                log.error("store operate fail, val:{} ", val, e);
            }

            if (sign) {
                removeAdditions();
            }
        }

        private void removeAdditions() {
            var sum = 0;
            var idList = new ArrayList<Integer>();
            try (var iter = top10Store.reverseAll()) {
                while (iter.hasNext()) {
                    var rec = iter.next();
                    log.info(" rec val:{}  name:{}", rec.key, rec.value);
                    sum += 1;
                    if (sum > 10) {
                        idList.add(rec.key);
                    }
                }

            } catch (InvalidStateStoreException e) {
                log.error("store remove elem fail ", e);
            }

            idList.forEach(id -> top10Store.delete(id));
        }

    }


}
