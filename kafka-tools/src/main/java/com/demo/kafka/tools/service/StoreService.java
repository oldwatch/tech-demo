package com.demo.kafka.tools.service;

import com.demo.kafka.tools.entity.EntityList;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.streams.KafkaStreamsInteractiveQueryService;
import org.springframework.stereotype.Component;

@Component
public class StoreService {

    private final static Logger log = LoggerFactory.getLogger(StoreService.class);

    private final StreamsBuilderFactoryBean factoryBean;
    private final KafkaStreamsInteractiveQueryService storeService;

    public StoreService(KafkaStreamsInteractiveQueryService interactiveQueryService,
                        @Qualifier("&toStoreKafkaStreamBuilder")
                        StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.storeService = interactiveQueryService;
        this.factoryBean = streamsBuilderFactoryBean;
    }

    public void showStore(String store) {

//        factoryBean.start();
        ReadOnlyKeyValueStore<Integer, EntityList> keyValueStore =
                storeService.retrieveQueryableStore(store, QueryableStoreTypes.keyValueStore());

        log.info(" store approximate num: {} ", keyValueStore.approximateNumEntries());

        try (var iter = keyValueStore.all()) {

            while (iter.hasNext()) {
                var info = iter.next();
                log.info(" key:{} ", info.key);
                log.info(" value: >>>> \n {} ", info.value);
            }

        } catch (InvalidStateStoreException e) {
            log.error(" operate store fail", e);
        }
        ;

    }
}
