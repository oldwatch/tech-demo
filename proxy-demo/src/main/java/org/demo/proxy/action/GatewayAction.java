package org.demo.proxy.action;

import com.fasterxml.jackson.databind.util.RawValue;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RestController
public class GatewayAction {

    private String baseUrl = "http://localhost:9090/";

    private String appID;

    private String accessToken;

    @GetMapping(path = "/api/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono doGetProxy(ServerHttpRequest request) {

        String url = request.getPath().toString();

        String subUrl = StringUtils.substringAfter(url, "/api/");

        return WebClient
                .create(baseUrl + subUrl)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .log()
                .map((val) -> new RawValue(val));
    }


    @PostMapping(path = "/api/**", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono doPostProxy(ServerHttpRequest request, @RequestBody Param context) {
        String url = request.getPath().toString();

        String subUrl = StringUtils.substringAfter(url, "/api/");


        return WebClient
                .create(baseUrl + subUrl)
                .post()
                .bodyValue(context)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .map((val) -> new RawValue(val));

    }

    public static class Param {
        private String name;

        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
