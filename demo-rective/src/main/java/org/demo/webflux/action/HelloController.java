package org.demo.webflux.action;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HelloController {

    private final static AtomicLong seq = new AtomicLong(0);

    @GetMapping(path = "/hello")
    public Mono<Map> hello() {

        return Mono.just(Collections.singletonMap("text", "hello"));
    }


    @GetMapping(path = "/delay")
    public Mono<Map> delay() {

        return Mono.create((sink) -> {
            try {
                Thread.sleep(5000l);
                sink.success(Collections.singletonMap("text", "delay"));

            } catch (InterruptedException e) {
                e.printStackTrace();
                sink.error(e);
            }
        });

    }

    @PostMapping(path = "/query")
    public Mono<Map> check(@RequestBody Param param) {

        return Mono.create((sink) -> {
            Map<String, String> result = new HashMap();
            result.put("name", param.name);
            result.put("value", "value:" + param.value);
            result.put("result", "result");

            sink.success(result);
        });
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
