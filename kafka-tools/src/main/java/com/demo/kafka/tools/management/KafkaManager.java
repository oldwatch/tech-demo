package com.demo.kafka.tools.management;

import com.demo.kafka.tools.entity.DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class KafkaManager {

    private final static Logger log = LoggerFactory.getLogger(KafkaManager.class);

    private final KafkaTemplate<String, DataEntity> template;

    public KafkaManager(KafkaTemplate<String, DataEntity> template) {
        this.template = template;
    }

    public void doSend(String key, DataEntity entry) {

        try {
            var future = template.sendDefault(key, entry);
            var result = future.get();
            log.info("result:{}", result.getRecordMetadata());

        } catch (InterruptedException e) {
            log.info(" been closed");
        } catch (ExecutionException | CancellationException e) {
            log.error(" send fail", e);
        }
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                log.info("success:{}", result);
//            } else {
//                log.error("exec fail:{}, data:{}, metaData: {}", ex.getMessage(), result.getProducerRecord(), result.getRecordMetadata());
//                throw new IllegalStateException("save to kafka fail");
//            }
//        });
    }

    public void doReceiveAndSave() {
        var record = template.receive("test-topic", 0, 0, Duration.ofMillis(10));
        if (record == null) {
            return;
        }
        var entity = record.value();
        var future = template.send("test-topic-operate", "operate-" + entity.seq(), entity);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("success finish operate:{}", result);
            } else {
                log.error("exec fail:{}, entity: {} ", ex.getMessage(), result.getProducerRecord().value());
                throw new IllegalStateException("move between topic fail");
            }
        });

    }
}
