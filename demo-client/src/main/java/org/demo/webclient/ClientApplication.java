package org.demo.webclient;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

//@EnableCircuitBreaker
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] argv) {

        SpringApplication.run(ClientApplication.class, argv);

    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> slowBreakerCustomizer() {
        return factory -> factory.configure(
                builder -> builder.circuitBreakerConfig(
                        CircuitBreakerConfig.custom()
                                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                .slidingWindowSize(5)
                                .slowCallDurationThreshold(Duration.ofMillis(50))
                                .build()
                )
//                .timeLimiterConfig(
//                        TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(100)).build())
                        .build(), "slow");
    }

//    @Bean(name = "slowCircuitBreaker")
//    public ReactiveCircuitBreaker getSlowCircuitBreaker(ReactiveResilience4JCircuitBreakerFactory factory){
//
//        return factory.create("slow");
//
//    }


    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8080").build();

    }
}
