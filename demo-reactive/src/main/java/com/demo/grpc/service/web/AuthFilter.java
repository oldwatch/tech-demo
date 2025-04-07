package com.demo.grpc.service.web;


import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class AuthFilter implements WebFilter {


    public AuthFilter() {


    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        var request = exchange.getRequest();

        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        var token = request.getHeaders().getFirst("authorization");

        if (StringUtils.isBlank(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return chain.filter(exchange).contextWrite(ctx -> ctx.put("TOKEN", token));


    }


}
