package com.demo.kafka.tools.service;

import com.demo.kafka.tools.management.KafkaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public final class ConsumerService {

    private final Logger log = LoggerFactory.getLogger(ConsumerService.class);
    private final KafkaManager manager;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final AtomicBoolean sign = new AtomicBoolean(true);
    private final AtomicBoolean sign2 = new AtomicBoolean(true);
    private AtomicLong offset = new AtomicLong(0);


    public ConsumerService(KafkaManager manager) {
        this.manager = manager;


    }

    public void start(int offset) {
        sign.set(true);
        this.offset.set(offset);
        executorService.submit(() -> {
            while (sign.get()) {
                try {
                    execute();
                } catch (Exception e) {
                    log.error(" receive data fail:", e);
                }
            }
        });
    }

    public void stop() {
        sign.set(false);
    }

    public void start2() {
        sign2.set(true);
        executorService.submit(() -> {
            while (sign2.get()) {
                try {
                    execute2();

                } catch (Exception e) {
                    log.error(" receive data fail:", e);
                }
            }
        });
    }

    public void stop2() {
        sign2.set(false);
    }

    private void execute2() throws InterruptedException {
        
        var list = manager.doReceive();
        if (list == null) {
            Thread.sleep(Duration.ofSeconds(10));
            return;
        }
        list.forEach(entry -> log.info(" entry: {}", entry));
    }

    private void execute() throws InterruptedException {
        var result = manager.doReceiveAndSave(offset.get());
        if (result == null) {
            Thread.sleep(Duration.ofSeconds(1));
            return;
        }
        offset.incrementAndGet();

        var entity = result.record().value();
        log.info("==================> receive the data: <==========================");
        log.info("{}", entity);

        var record = result.record();
        log.info(" offset:{}, timestamp:{}, key:{} ", record.offset(), record.timestamp(), record.key());
        for (var v : result.record().headers().toArray()) {
            log.info(" header: {} - {}", v.key(), v.value());
        }
        try {
            result.future().get(100, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | TimeoutException e) {
            log.error(" consumer error ", e);
            throw new RuntimeException(e);
        }

    }
}
