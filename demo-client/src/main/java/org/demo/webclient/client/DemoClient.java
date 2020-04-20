package org.demo.webclient.client;

import org.demo.webclient.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

//@EnableCircuitBreaker
@Component
public class DemoClient {

    private AtomicReference<ReactiveCircuitBreaker> circuitBreakerRef = new AtomicReference<>();

    @Autowired
    private ReactiveResilience4JCircuitBreakerFactory factory;


    @Autowired
    private HelloService service;

    private ReactiveCircuitBreaker getDelayCircuitBreaker() {

        return circuitBreakerRef.updateAndGet((circuitBreaker) -> {
            if (circuitBreaker == null) {
                return factory.create("slow");
            } else {
                return circuitBreaker;
            }
        });

    }

    public Mono<String> helloMono() {
        return service.hello();
    }

    //
    public String hello() {

        return getDelayCircuitBreaker().run(service.hello(), (exception) -> {
            return Mono.just(exception.getMessage());

        }).block();
    }

}
