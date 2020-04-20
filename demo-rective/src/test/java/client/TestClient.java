package client;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClient {


    private WebClient client;

    @Before
    public void init() {

        client = WebClient.builder().baseUrl("http://localhost:8080").build();

    }

    @Test
    public void hello() {
        Mono<String> result = client.get()
                .uri("/hello")
                .exchange()
                .flatMap(response -> response.bodyToMono(String.class));

        assertEquals(result.block(), "Hello");

    }
}
