package com.demo.kafka.tools.management;

import com.demo.kafka.tools.entity.DataEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KafkaListenerOperate {

    private final Logger log = LoggerFactory.getLogger(KafkaListenerOperate.class);

    @KafkaListener(id = "store-monitor", topics = {"test-store-topic"}, groupId = "monitor")
    public void onMessage(ConsumerRecord<String, DataEntity> record) {

        log.info("receive msg: {} - {} ", record.value(), record.key());
    }
}
