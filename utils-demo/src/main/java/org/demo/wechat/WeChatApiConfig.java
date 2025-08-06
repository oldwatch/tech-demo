package org.demo.wechat;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableConfigurationProperties(WeChatApiAuth.class)
public class WeChatApiConfig {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WeChatTokenService weChatTokenService(RestTemplate template, WeChatApiAuth auth) {
        return new WeChatTokenService(restTemplate(), auth);
    }

    @Bean
    public TokenStore tokenStore(WeChatTokenService service) {
        return new TokenStore(service);
    }

    @Bean
    public WeChatBusinessService weChatBusinessService(TokenStore store, RestTemplate template) {
        return new WeChatBusinessService(store, template);
    }
}
