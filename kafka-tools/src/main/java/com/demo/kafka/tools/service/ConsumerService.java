package com.demo.kafka.tools.service;

import com.demo.kafka.tools.management.KafkaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public final class ConsumerService {

    private final Logger log = LoggerFactory.getLogger(ConsumerService.class);
    private final KafkaManager manager;
    private final ExecutorService executorService = Executors.newFixedThreadPool(16);
    private final AtomicBoolean sign = new AtomicBoolean(true);

    public ConsumerService(KafkaManager manager) {
        this.manager = manager;
    }


    public void execute(int count) {
        var countDown = new CountDownLatch(count);
        for (var i = 0; i < count; i++) {
            executorService.submit(() -> {
                while (sign.get()) {
                    try {
                        manager.doReceiveAndSave();
                        Thread.sleep(Duration.ofMillis(10));
                    } catch (Exception e) {
                        break;
                    }
                }
                countDown.countDown();
            });
        }
        try {
            countDown.await();
        } catch (InterruptedException e) {
            log.warn(" interrupted by outside");
        }
    }
}
