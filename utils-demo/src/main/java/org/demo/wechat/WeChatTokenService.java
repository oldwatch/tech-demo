package org.demo.wechat;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


public class WeChatTokenService {

//    private static final String apiSecret = "93b674499092eed8e0fe999401b0c785";
//    private static final String apiKey = "wxb88fedeef81ca30b";

    private static final String hostPrimate = "api.weixin.qq.com";
    private static final String hostSecond = "api2.weixin.qq.com";

    private static final String tokenPath = "/cgi-bin/stable_token";

    private final String apiSecret;
    private final String apiKey;

    private final RestTemplate template;


    public WeChatTokenService(RestTemplate template, WeChatApiAuth auth) {
        this.template = template;

        this.apiKey = auth.apiKey();
        this.apiSecret = auth.apiSecret();
    }


    @Retryable(retryFor = ServiceUnavailableException.class, recover = "doFallback", maxAttempts = 2, backoff = @Backoff(delay = 100, maxDelay = 500))
    public TokenStore.WechatToken bindToken() {

        return doExecute(hostPrimate);

    }

    @Recover
    public TokenStore.WechatToken doFallback(ServiceUnavailableException e) {
        return doExecute(hostSecond);
    }


    private TokenStore.WechatToken doExecute(String host) {
        var reqParam = new TokenReq(apiKey, apiSecret);

        var url = UriComponentsBuilder.newInstance().scheme("https").host(host).path(tokenPath).build();

        try {
            var response = template
                    .postForEntity(url.toUri(), reqParam, TokenStore.WechatToken.class);
            var statusCode = response.getStatusCode();
            if (statusCode.is2xxSuccessful()) {
                var token = response.getBody();
                if (token == null) {
                    throw new IllegalArgumentException("null response");
                }
                return token;

            } else if (statusCode.is5xxServerError()) {
                throw new ServiceUnavailableException(statusCode.toString());
            } else {
                throw new IllegalArgumentException(statusCode.toString());
            }
        } catch (ResourceAccessException e) {
            throw new ServiceUnavailableException(e);
        }
    }


    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TokenReq(String grantType,
                    String appid,
                    String secret) {
        public TokenReq(String appId, String secret) {
            this("client_credential", appId, secret);
        }


    }

    public static class ServiceUnavailableException extends RuntimeException {

        public ServiceUnavailableException(String msg) {
            super(msg);
        }

        public ServiceUnavailableException(Exception e) {
            super(e);
        }

    }
};




