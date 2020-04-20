package org.demo.webclient;

import org.demo.webclient.client.DemoClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestClientWithResilence {

    @Autowired
    private DemoClient client;

    @Test
    public void test() {

        assertEquals("Hello", client.helloMono().block());
    }

    @Test
    public void testWithError() {

        List<CompletableFuture> tasks = new ArrayList();
        for (int i = 0; i < 10; i++) {
            tasks.add(CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        long start = System.currentTimeMillis();
                        String value = client.hello();
                        System.out.println("id:" + Thread.currentThread().getId() + " " + value + " cost time " + (System.currentTimeMillis() - start));

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }));
        }

        CompletableFuture future = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));

        future.join();
    }
}
