package com.demo.kafka.tools.management;

import com.demo.kafka.tools.entity.DataEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
@Transactional
public class KafkaManager {

    private final static Logger log = LoggerFactory.getLogger(KafkaManager.class);

    private final KafkaTemplate<String, DataEntity> template;
    private final BiConsumer<? super SendResult<String, DataEntity>, ? super Throwable> callback = (result, exception) -> {
        if (exception == null) {
            log.info("send data: \n {}", result.getProducerRecord().value());
            log.info("data meta: \n {}", result.getRecordMetadata());
        } else {
            log.error(" send fail: ", exception);
            log.info(" record: {}", result);
        }
    };


    public KafkaManager(KafkaTemplate<String, DataEntity> template, ConsumerFactory<String, DataEntity> factory) {
        this.template = template;
        this.template.setConsumerFactory(factory);
    }

    public CompletableFuture<SendResult<String, DataEntity>> doSend(String key, DataEntity record) {
        var future = template.sendDefault(key, record);

        future.whenCompleteAsync(callback);
        return future;
    }

    public Result doReceiveAndSave(long offset) {

        var record = template.receive("test-topic", 0, offset, Duration.ofMillis(10));
        if (record == null) {
            log.info("nothing found");
            return null;
        }
        var entity = record.value();
        var future = template.send("test-store-topic", "operate-" + entity.seq(), entity);
        future.whenCompleteAsync(callback);
        return new Result(future, record);

    }

    public record Result(CompletableFuture<SendResult<String, DataEntity>> future,
                         ConsumerRecord<String, DataEntity> record) {

    }
}
