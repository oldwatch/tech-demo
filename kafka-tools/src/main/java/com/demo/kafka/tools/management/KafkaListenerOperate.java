package com.demo.kafka.tools.management;

import com.demo.kafka.tools.entity.DataEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(transactionManager = "kafkaTransactionManager")
public class KafkaListenerOperate {

    private final Logger log = LoggerFactory.getLogger(KafkaListenerOperate.class);

    private final KafkaTemplate<String, DataEntity> storeProduct;
    private final AtomicInteger seq = new AtomicInteger(1);

    public KafkaListenerOperate(@Qualifier("storeTemplate") KafkaTemplate<String, DataEntity> storeProduct) {
        this.storeProduct = storeProduct;
    }

    @KafkaListener(id = "store-monitor", topics = {"test-topic"}, groupId = "listener")
    public void onMessage(ConsumerRecord<String, DataEntity> record) {

        log.info("receive msg: {} - {} ", record.value(), record.key());

        var entity = record.value();
        if (entity.text() == null) {
            log.warn("empty entity {}", entity.seq());
            return;
        }

        if (entity.text().contains("error") && seq.incrementAndGet() % 2 == 0) {

            throw new IllegalArgumentException();
        }

        var future = storeProduct.sendDefault(record.key(), new DataEntity(entity));

        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalStateException(e);
        } catch (TimeoutException e) {
            log.error(" time out ", e);
        }


    }
}
