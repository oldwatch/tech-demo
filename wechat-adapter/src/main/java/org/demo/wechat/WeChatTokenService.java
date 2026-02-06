package org.demo.wechat;

import module org.slf4j;
import module spring.beans;
import module spring.core;
import module spring.web;
import module tools.jackson.databind;
import org.springframework.resilience.annotation.Retryable;


public class WeChatTokenService {

    /*
    curl --location 'https://api.weixin.qq.com/cgi-bin/stable_token' \
--header 'Content-Type: application/json' \
--data '{
    "grant_type": "client_credential",
    "appid": "wxb88fedeef81ca30b",
    "secret": "93b674499092eed8e0fe999401b0c785"
}
     */
    private static final String hostPrimate = "api.weixin.qq.com";
    private static final String hostSecond = "api2.weixin.qq.com";
    private static final String tokenPath = "cgi-bin/stable_token";
    private final Logger log = LoggerFactory.getLogger(WeChatTokenService.class);
    private final String apiSecret;
    private final String apiKey;

    private final RestTemplate template;


    public WeChatTokenService(RestTemplate template, WeChatApiAuth auth) {
        this.template = template;

        this.apiKey = auth.apiKey();
        this.apiSecret = auth.apiSecret();
    }


    @Retryable(includes = ServiceUnavailableException.class,
            maxRetries = 2
    )
    public TokenStore.WechatToken bindToken() {

        return doExecute(hostPrimate);

    }

    //    @Recov
//    public TokenStore.WechatToken doFallback(ServiceUnavailableException e) {
//        return doExecute(hostSecond);
//    }


    private TokenStore.WechatToken doExecute(String host) {
        var reqParam = new TokenReq(apiKey, apiSecret);

        var url = UriComponentsBuilder.newInstance().scheme("https").host(host).path(tokenPath).build();

        try {

            var response = template
                    .postForEntity(url.toUri(), reqParam, TokenStore.WechatToken.class);
            var statusCode = response.getStatusCode();
            if (statusCode.is2xxSuccessful()) {

                return response.getBody();

            } else if (statusCode.is5xxServerError() || statusCode.is4xxClientError()) {
                throw new ServiceUnavailableException(statusCode.toString());
            } else {
                throw new IllegalArgumentException(statusCode.toString());
            }
        }
//        catch (ResourceAccessException e) {
//            throw new ServiceUnavailableException(e);
//        }
        catch (RestClientException e) {
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




