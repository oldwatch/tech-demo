package com.demo.kafka.tools;

import com.demo.kafka.tools.entity.DataEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JacksonUtils;

import java.util.List;

//@EnableTransactionManagement
@EnableKafka
//@EnableKafkaStreams
@SpringBootApplication
public class KafkaApplication {


    private final static long seed = System.currentTimeMillis() / 1000;

    public static void main(String[] argv) {

        System.setProperty("timestamp", String.valueOf(System.currentTimeMillis() / 1000));

//        SpringApplication.run(KafkaApplication.class, argv);
        var app = new SpringApplication(KafkaApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(argv);

    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public KafkaTemplate<String, DataEntity> storeTemplate(ProducerFactory<String, DataEntity> productFactory) {
        var template = new KafkaTemplate<>(productFactory);
        template.setTransactionIdPrefix("tx-store-" + seed);
        template.setDefaultTopic("test-store-topic");
        
        return template;
    }

    @Bean
    public KafkaTemplate<String, DataEntity> commonTemplate(ProducerFactory<String, DataEntity> productFactory, ConsumerFactory<String, DataEntity> consumerFactory) {
        var template = new KafkaTemplate<>(productFactory);
        template.setConsumerFactory(consumerFactory);
        template.setDefaultTopic("test-topic");
//        template.setTransactionIdPrefix("tx-product-" + seed);
        return template;
    }

    @Bean
    public Consumer<String, DataEntity> customer(ConsumerFactory<String, DataEntity> consumerFactory) {
        var consumer = consumerFactory.createConsumer("test-group-2", "manager");
        consumer.assign(List.of(new TopicPartition("test-topic", 0)
        ));
        return consumer;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DataEntity>> kafkaListenerContainerFactory(ConsumerFactory<String, DataEntity> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, DataEntity> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
}
