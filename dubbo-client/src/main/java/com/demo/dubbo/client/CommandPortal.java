package com.demo.dubbo.client;

import org.demo.idconvert.IdEncodeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final DemoClient client;
    private final Logger log = LoggerFactory.getLogger(CommandPortal.class);
    private final IdEncodeTool tool;
    private ApplicationContext applicationContext;


    public CommandPortal(DemoClient client, IdEncodeTool tool) {
        this.client = client;
        this.tool = tool;
    }

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

                var params = input.split("\\s+");
                if (params.length == 0) {
                    continue;
                }

                var cmd = params[0];
                try {
                    switch (cmd) {
                        case "add" -> {
                            var name = params[1];
                            var val = Integer.parseInt(params[2]);
                            var result = client.addEntity(name, val);
                            var id = tool.decode(result.etityId());
                            System.out.println("add entity: " + result + " id:" + id);
                        }
                        case "list" -> {
                            for (var entity : client.getList()) {
                                System.out.println(entity);
                            }
                        }
                        default -> {
                            System.out.println("unknown command");
                        }
                    }
                } catch (DemoClient.ServiceException e) {
                    log.error(" client call fail", e);
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
