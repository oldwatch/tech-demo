package org.demo.wechat;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TokenStore {

    private final AtomicReference<String> tokenStore = new AtomicReference<>();

    private final AtomicLong countDown = new AtomicLong(0L);

    private final WeChatTokenService service;

    private final Lock lock = new ReentrantLock();


    public TokenStore(WeChatTokenService service) {
        this.service = service;
    }


    private void fillToken() {

        synchronized (tokenStore) {

            long timeStamp = countDown.get();
            if (timeStamp > System.currentTimeMillis()) {
                return;
            }
            var token = service.bindToken();
            tokenStore.set(token.accessToken);
            countDown.set(System.currentTimeMillis() + (long) (token.expiresIn * 0.75 * 1000));
        }

    }

    public void settingToken(String token) {
        tokenStore.set(token);
        countDown.set(System.currentTimeMillis() + 7200 * 1000);
    }

    public String getCurrentToken() {

        long timeStamp = countDown.get();
        if (timeStamp > System.currentTimeMillis()) {
            return tokenStore.get();
        }

        fillToken();

        return tokenStore.get();

    }


    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record WechatToken(
            String accessToken,
            Long expiresIn,
            @JsonUnwrapped ErrorInfo err
    ) {

        public WechatToken() {
            this("_mock_", 7200l, null);
        }

    }


}
