package com.demo.dubbo.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.demo.idconvert.EntityIDToolFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@Import(EntityIDToolFactory.class)
@EnableDubbo
public class DubboServiceApplication {

    public static void main(String[] argv) {

        var app = new SpringApplication(DubboServiceApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(argv);
    }

}
