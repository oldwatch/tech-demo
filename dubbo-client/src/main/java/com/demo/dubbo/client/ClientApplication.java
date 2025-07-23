package com.demo.dubbo.client;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.demo.idconvert.EntityIDToolFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EntityIDToolFactory.class)
@EnableDubbo
public class ClientApplication {

    public static void main(String[] argv) {

        var app = new SpringApplication(ClientApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(argv);
    }

}
