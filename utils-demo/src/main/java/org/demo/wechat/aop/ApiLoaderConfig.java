package org.demo.wechat.aop;

import org.demo.wechat.TokenStore;
import org.demo.wechat.WeChatApiAuth;
import org.demo.wechat.WeChatTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@EnableConfigurationProperties(WeChatApiAuth.class)
public class ApiLoaderConfig {


    private final Logger log = LoggerFactory.getLogger(ApiLoaderConfig.class);

    @Bean
    public RestTemplate restTemplate() {

        var template = new RestTemplate();
        var factory = new HttpComponentsClientHttpRequestFactory();
        template.setRequestFactory(new BufferingClientHttpRequestFactory(factory));

        return template;
    }

    @Bean
    public WeChatTokenService weChatTokenService(RestTemplate template, WeChatApiAuth auth) {
        return new WeChatTokenService(template, auth);
    }

    @Bean
    public TokenStore tokenStore(WeChatTokenService service) {
        return new TokenStore(service);
    }

    //    @DependsOn({"tokenStore", "restTemplate"})
    @Bean
    public BeanFactoryPostProcessForService getPostProcess(TokenStore store, RestTemplate template) {
        return new BeanFactoryPostProcessForService(store, template);
    }

}
