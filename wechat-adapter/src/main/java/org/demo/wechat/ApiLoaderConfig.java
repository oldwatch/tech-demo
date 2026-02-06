package org.demo.wechat;


import module java.base;
import module org.slf4j;
import module spring.context;
import module spring.web;
import module tools.jackson.databind;
import org.demo.wechat.aop.BeanFactoryPostProcessForService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
