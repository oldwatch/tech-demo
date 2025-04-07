package com.demo.newfeature;

import com.demo.newfeature.helper.SealedClsJacksonModule;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class NewFeatureApp {

    public static void main(String[] argv) {

        SpringApplication.run(NewFeatureApp.class, argv);

    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.postConfigurer(objectMapper -> {
                // Customize the ObjectMapper here
                objectMapper.registerModule(new SealedClsJacksonModule());
            });
        };
    }


}
