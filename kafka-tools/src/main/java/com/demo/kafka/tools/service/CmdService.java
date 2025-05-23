package com.demo.kafka.tools.service;

import com.demo.kafka.tools.entity.InputParams;
import com.demo.kafka.tools.helper.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;


@Component
public class CmdService implements CommandLineRunner, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(CmdService.class);
    private final ProductService productService;

    private final ConsumerService consumerService;

    private final StreamService streamService;

    private ApplicationContext applicationContext;

    public CmdService(ProductService productService, ConsumerService consumerService, StreamService streamService) {
        this.productService = productService;
        this.consumerService = consumerService;
        this.streamService = streamService;
    }

    private static String getCleanInput(BufferedReader reader) throws IOException {
        var input = reader.readLine();
        if (input == null || input.isBlank()) {
            return null;
        }
        input = input.trim().toLowerCase(Locale.ENGLISH);
        return input;
    }

    private void outputHelp(String input) {
        var params = input.split("\\s+");
        if (params.length == 1) {
            //show command list.
            System.out.println("command:");
            for (var cls : Config.class.getPermittedSubclasses()) {
                System.out.println(Utils.getFirstWord(cls.getSimpleName()));
            }
            return;
        }
        var config = Config.createConfig(params[1]);
        System.out.println(config.getDetail());
    }


    @Override
    public void run(String... args) throws Exception {
        var mode = "command";
        if (args.length > 0) {
            mode = args[0];
        }

        if (mode.equals("command")) {

            operateCommand();
        } else if (mode.equals("consumer")) {

            operateService();
        }
    }

    private void operateService() throws IOException {
        System.out.println("input start with number or stop command");
        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            var input = getCleanInput(reader);
            if (input == null) continue;
            if (input.equals("exit")) {
                break;
            }

            var operate = Utils.getFirstWord(input);

            switch (operate) {
                case "start" -> {
                    consumerService.start(Utils.getIntValue(Utils.getSecordWord(input), 0));
                    System.out.println("consumerService is running.");
                }
                case "stop" -> {
                    consumerService.stop();
                    System.out.println("consumerService is stopped.");
                }
                case "start2" -> {
                    consumerService.start2();
                    System.out.println("consumer Service II is running.");
                }
                case "stop2" -> {
                    consumerService.stop2();
                    System.out.println("consumer Service II is stopped.");
                }
            }
        }
    }

    private void operateCommand() throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("input command,or help:");

            try {
                var input = getCleanInput(reader);
                if (input == null) continue;
                if (input.equals("exit")) {
                    break;
                }

                if (input.startsWith("help")) {
                    outputHelp(input);
                    continue;
                }

                var params = new InputParams(input);
                log.info(" cmd:{},additions:{} ", params.command(), params.config());
                switch (params.config()) {
                    case ProductConfig cfg -> productService.execute(cfg.param(), cfg);
                    case StreamConfig cfg -> streamService.doCommand(cfg.cmd(), cfg);
                    default -> {
                        System.out.println("unknown operate");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

        }
        reader.close();
        System.exit(SpringApplication.exit(applicationContext, () -> 0)); // Exit with code

    }

    private int getNumber(String line) {
        var strs = line.split("\\s+");
        if (strs.length < 2) {
            return 3;
        }
        return Integer.parseInt(strs[1]);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
