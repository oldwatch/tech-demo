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
import java.io.InputStreamReader;


@Component
public class CommandPortal implements CommandLineRunner, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(CommandPortal.class);
    private final ProductService productService;

    private final ConsumerService consumerService;

    private final StreamService streamService;

    private final StoreService storeService;

    private final SourceService sourceService;

    private ApplicationContext applicationContext;

    public CommandPortal(ProductService productService, ConsumerService consumerService, StreamService streamService, StoreService storeService, SourceService sourceService) {
        this.productService = productService;
        this.consumerService = consumerService;
        this.streamService = streamService;
        this.storeService = storeService;
        this.sourceService = sourceService;
    }


    @Override
    public void run(String... args) throws Exception {
        var mode = "command";
        if (args.length > 0) {
            mode = args[0];
        }

        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println(">>>>>>input command,or help:");

            try {
                var input = getCleanInput(reader);
                if (input == null) continue;
                if (input.equals("exit")) {
                    break;
                }

                switch (mode) {
                    case "command" -> operateCommand(input);
                    case "service" -> operateService(input);
                }


            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

        }
        reader.close();
        System.exit(SpringApplication.exit(applicationContext, () -> 0)); // Exit with code

    }


    private void operateService(String input) {

        var service = Utils.getFirstWord(input);
        var operate = Utils.getSecondWord(input);

        switch (service) {
            case "consumer" -> {
                switch (operate) {
                    case "start" -> consumerService.start(Utils.getIntValue(Utils.getSecondWord(input), 0));
                    case "stop" -> consumerService.stop();
                }
            }
            case "product" -> {
                switch (operate) {
                    case "start" -> sourceService.start(Utils.getThirdWord(input));
                    case "stop" -> sourceService.stop();
                }
            }
            case "stream" -> {

                var config = new StreamConfig(input);
                switch (operate) {
                    case "start" -> streamService.start(config);
                    case "stop" -> streamService.stop(config);
                    case "topology" -> streamService.showTopology(config);

                }
            }
            case "store" -> {
                switch (operate) {
                    case "show" -> storeService.showStore(Utils.getThirdWord(input));
                    case "detail" -> {
                    }
                }
            }
        }

    }

    private void operateCommand(String input) {

        var params = new InputParams(input);
        log.info(" cmd:{},additions:{} ", params.command(), params.config());
        switch (params.config()) {
            case ProductConfig cfg -> productService.execute(cfg.param(), cfg);
            case StoreConfig cfg -> {
                switch (cfg.operate()) {
                    case "show" -> storeService.showStore(params.command());
                }
            }
            default -> {
                System.out.println("unknown operate");
            }
        }

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
