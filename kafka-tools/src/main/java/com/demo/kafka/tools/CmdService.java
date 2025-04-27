package com.demo.kafka.tools;

import com.demo.kafka.tools.entity.InputParams;
import com.demo.kafka.tools.helper.Utils;
import com.demo.kafka.tools.service.Config;
import com.demo.kafka.tools.service.ConsumerService;
import com.demo.kafka.tools.service.ProductConfig;
import com.demo.kafka.tools.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;


@Component
public class CmdService implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(CmdService.class);
    private final ProductService productService;

    private final ConsumerService consumerService;


    public CmdService(ProductService productService, ConsumerService consumerService) {
        this.productService = productService;
        this.consumerService = consumerService;
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

    private void executeCommand(String input) {

        var params = new InputParams(input);
        log.info(" params:{}", params.config());
        switch (params.config()) {
            case ProductConfig cfg -> productService.execute(params.param(), cfg);
            default -> {
            }
        }
    }


    @Override
    public void run(String... args) throws Exception {
        var mode = "command";
        if (args.length > 0) {
            mode = args[0];
        }

        if (mode.equals("command")) {

            System.out.println("input command,or help");
            var reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {

                var input = reader.readLine();
                if (input == null || input.isBlank()) {
                    continue;
                }
                input = input.trim().toLowerCase(Locale.ENGLISH);
                if (input.equals("exit")) {
                    break;
                }

                if (input.startsWith("help")) {
                    outputHelp(input);
                    continue;
                }

                executeCommand(input);

            }
        } else if (mode.equals("consumer")) {
            var number = Integer.parseInt(args[1]);
            consumerService.execute(number);
        }
    }


}
