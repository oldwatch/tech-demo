package com.demo.grpc.service;

import org.demo.idconvert.IdEncodeTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableConfigurationProperties(AdditionCfgProp.class)
public class ReactiveApplication {

    public static void main(String[] argv) {

        SpringApplication.run(ReactiveApplication.class, argv);

    }

    @Bean
    public IdEncodeTool idEncodeTool(AdditionCfgProp prop) {
        return new IdEncodeTool(prop.sqIdMask());
    }

//    @Bean
//    public  entityIDJacksonModule(IdEncodeTool encodeTool) {
//        return new EntityIDJacksonModule(encodeTool);
//    }

//    @GlobalServerInterceptor
//    @Bean
//    @Order(100)
//    public ServerInterceptor authInterceptor() {
//        return new GRpcInterceptor();
//    }
}
