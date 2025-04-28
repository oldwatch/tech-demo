package com.demo.kafka.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableKafka
//@EnableKafkaStreams
@SpringBootApplication
public class KafkaApplication {


    public static void main(String[] argv) {

//        SpringApplication.run(KafkaApplication.class, argv);
        var app = new SpringApplication(KafkaApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(argv);

    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }
}
