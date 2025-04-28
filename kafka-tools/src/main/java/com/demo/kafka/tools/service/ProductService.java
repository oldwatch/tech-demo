package com.demo.kafka.tools.service;

import com.demo.kafka.tools.entity.DataEntity;
import com.demo.kafka.tools.management.KafkaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public final class ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductService.class);

    private final AtomicInteger sequence = new AtomicInteger(100);

    private final KafkaManager manager;

    public ProductService(KafkaManager manager) {
        this.manager = manager;
    }


    public void execute(String param, ProductConfig config) {

        var allTask = new CompletableFuture[config.count()];
        for (var i = 0; i < config.count(); i++) {
            int seq = sequence.incrementAndGet();
            var key = String.format("seq-%d-timestamp-%d", seq, System.currentTimeMillis());
            var entry = new DataEntity(param, seq);
            var future = manager.doSend(key, entry);
            allTask[i] = future;
        }
        var allFuture = CompletableFuture.allOf(allTask);
        try {
            allFuture.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.warn("been interrupted ");
        } catch (ExecutionException | TimeoutException e) {
            log.error("something wrong ", e);
            throw new RuntimeException(e);
        }

    }


}
