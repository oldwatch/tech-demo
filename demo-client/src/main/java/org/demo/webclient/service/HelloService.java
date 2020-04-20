package org.demo.webclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class HelloService {

    @Autowired
    private WebClient client;

    //    @HystrixCommand(fallbackMethod = "reliable")
    public Mono<String> hello() {
        Mono<String> result = client.get()
                .uri("/hello")
                .exchange()
                .flatMap(response -> response.bodyToMono(String.class));

        return result;
    }

}
