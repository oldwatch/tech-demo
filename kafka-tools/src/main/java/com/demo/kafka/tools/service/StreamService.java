package com.demo.kafka.tools.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@DependsOn("localKafkaStreamBuilder")
@Component
public class StreamService {

    private final StreamsBuilderFactoryBean factoryBean;

//    private final KStream<String, DataEntity> stream;

    public StreamService(@Qualifier("&localKafkaStreamBuilder") StreamsBuilderFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    public void doCommand(String param, StreamConfig config) {

        switch (param) {
            case "start" -> {
                factoryBean.start();
            }
            case "stop" -> {
                factoryBean.stop();
            }
        }

    }
}
