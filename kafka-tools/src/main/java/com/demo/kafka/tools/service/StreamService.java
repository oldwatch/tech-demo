package com.demo.kafka.tools.service;

import org.apache.kafka.streams.StreamsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Component
public class StreamService implements ApplicationContextAware {

    private final static Logger log = LoggerFactory.getLogger(StreamService.class);

    private ApplicationContext applicationContext;

    public StreamService() {

    }

    private <T> Optional<T> getBeanByPrefix(String service, Class<T> cls) {
        return Arrays.stream(applicationContext.getBeanNamesForType(cls))
                .filter(n -> n.toLowerCase(Locale.ENGLISH).startsWith(service))
                .findFirst()
                .map(name -> applicationContext.getBean(name, cls));
    }

    public void start(StreamConfig config) {

        var factoryBean = getBeanByPrefix(config.factory(), StreamsBuilderFactoryBean.class).get();
        factoryBean.start();
    }


    public void stop(StreamConfig config) {

        var factoryBean = getBeanByPrefix(config.factory(), StreamsBuilderFactoryBean.class).get();
        factoryBean.stop();
    }

    public void showTopology(StreamConfig config) {

        var builder = getBeanByPrefix(config.service(), StreamsBuilder.class).get();
        var topology = builder.build();
        
        log.info("===========topology===========\n {}", topology.describe());

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
