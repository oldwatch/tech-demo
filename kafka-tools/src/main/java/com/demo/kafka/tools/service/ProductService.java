package com.demo.kafka.tools.service;

import com.demo.kafka.tools.entity.DataEntity;
import com.demo.kafka.tools.management.KafkaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public final class ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductService.class);

    private final AtomicInteger sequence = new AtomicInteger(100);

    private final KafkaManager manager;

    private final ExecutorService executorService;

    private final String[] params = {"count", "repeat"};

    public ProductService(KafkaManager manager) {
        this.manager = manager;
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();

    }


    public void execute(String param, ProductConfig config) {

        int end = sequence.get() + config.repeat();

        while (sequence.get() < end) {
            for (var i = 0; i < config.count(); i++) {
                executorService.submit(() -> {
                    int seq = sequence.incrementAndGet();
                    var key = String.format("seq-%d", seq);
                    var entry = new DataEntity(param, seq);
                    manager.doSend(key, entry);

                });
            }
        }

    }


}
