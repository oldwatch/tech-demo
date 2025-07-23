package com.demo.dubbo.service;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.demo.utils.IOUtils.getCleanInput;

@Component
public class CommandPortal implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {

        var mode = "command";
        if (args.length > 0) {
            mode = args[0];
        }

        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {

                System.out.println(">>>>>>input command,or help:");

                var input = getCleanInput(reader);
                if (input == null) continue;
                if (input.equals("exit")) {
                    break;
                }

            }
            System.exit(SpringApplication.exit(applicationContext, () -> 0)); // Exit with code
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
