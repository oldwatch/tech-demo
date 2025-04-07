package com.demo.kafka.tools.service;

import com.demo.kafka.tools.entity.DataEntity;
import com.demo.kafka.tools.management.KafkaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SourceService {


    private final static Logger log = LoggerFactory.getLogger(SourceService.class);

    private final AtomicInteger sequence = new AtomicInteger(100);

    private final KafkaManager manager;

    private final ExecutorService executorService;

    private final AtomicBoolean sign = new AtomicBoolean(false);
    private final int BATCH_SIZE = 10;

    public SourceService(KafkaManager manager) {

        this.manager = manager;
        this.executorService = Executors.newSingleThreadExecutor();

    }

    private CompletableFuture<SendResult<String, DataEntity>> execute(String name) {

        int seq = sequence.incrementAndGet();
        var key = String.format("seq-%d-tm-%d", seq, System.currentTimeMillis());
        var entry = new DataEntity(name, seq);
        return manager.doStreamSend(key, entry);

    }

    public void start(String name) {
        sign.set(true);
        executorService.submit(() -> {

            while (sign.get()) {
                var allTask = new ArrayList<CompletableFuture<?>>();
                for (int i = 0; i < BATCH_SIZE; i++) {
                    try {
                        allTask.add(execute(name));
                    } catch (Exception e) {
                        log.error(" send data fail:", e);
                    }
                }
                var allFuture = CompletableFuture.allOf(allTask.toArray(new CompletableFuture[0]));
                try {
                    var result = allFuture.get(100, TimeUnit.MILLISECONDS);
                    log.info(" product result: {}", result);
                } catch (InterruptedException e) {
                    log.warn("been interrupted ");
                    break;
                } catch (ExecutionException | TimeoutException e) {
                    log.error("something wrong ", e);
                }

            }
        });
    }

    public void stop() {
        sign.set(false);
    }


}
