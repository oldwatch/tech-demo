package com.demo.kafka.tools.management;

import com.demo.kafka.tools.entity.DataEntity;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

@Service
@Transactional(transactionManager = "kafkaTransactionManager")
public class KafkaManager {

    private final static Logger log = LoggerFactory.getLogger(KafkaManager.class);

    private final KafkaTemplate<String, DataEntity> template;
    private final Consumer<String, DataEntity> consumer;
    private final KafkaTemplate<String, DataEntity> storeProduct;
    private final BiConsumer<? super SendResult<String, DataEntity>, ? super Throwable> callback = (result, exception) -> {
        if (exception == null) {
            log.info("send data: \n {}", result.getProducerRecord().value());
            log.info("data meta: \n {}", result.getRecordMetadata());
        } else {
            log.error(" send fail: ", exception);
            log.info(" record: {}", result);
        }
    };


    public KafkaManager(@Qualifier("commonTemplate") KafkaTemplate<String, DataEntity> template,
                        Consumer<String, DataEntity> consumer,
                        @Qualifier("storeTemplate") KafkaTemplate<String, DataEntity> storeProduct) {
        this.template = template;
        this.consumer = consumer;
        this.storeProduct = storeProduct;
    }

    public CompletableFuture<SendResult<String, DataEntity>> doSend(String key, DataEntity record) {
        var future = template.sendDefault(key, record);

        future.whenCompleteAsync(callback);
        return future;
    }


    public List<DataEntity> doReceive() throws InterruptedException {

        var records = consumer.poll(Duration.ofMillis(10));

        if (records.isEmpty()) {
            log.info("nothing found in consumer");
            return null;
        }

        log.info("record count:{}", records.count());
        var iter = records.iterator();
        int idx = 0;
        var futureArray = new CompletableFuture[records.count()];
        List<DataEntity> list = new ArrayList<>();
        while (iter.hasNext()) {
            var record = iter.next();
            var entity = record.value();
            if (entity.text().contains("error")) {
                
                throw new IllegalArgumentException();
            }
            var future = storeProduct.sendDefault("operate-" + record.offset(), entity);
            futureArray[idx] = future;
            idx++;
            list.add(record.value());
        }
        var future = CompletableFuture.allOf(futureArray);

        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        } catch (TimeoutException e) {
            log.error(" time out ", e);
        }

        return list;


    }

    public Result doReceiveAndSave(long offset) {

        log.info(" in transaction :{}", template.inTransaction());

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
