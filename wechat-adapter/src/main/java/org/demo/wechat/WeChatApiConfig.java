package org.demo.wechat;


import module java.base;
import module org.slf4j;
import module spring.context;
import module spring.web;
import module tools.jackson.databind;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(WeChatApiAuth.class)
public class WeChatApiConfig {

    private final Logger log = LoggerFactory.getLogger(WeChatApiConfig.class);


    @Bean
    public RestTemplate restTemplate() {

        var template = new RestTemplate();
        var factory = new HttpComponentsClientHttpRequestFactory();
        template.setRequestFactory(new BufferingClientHttpRequestFactory(factory));
        return template;
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
