package org.demo.wechat.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.demo.wechat.TokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ApiCallMethodInterceptor implements MethodInterceptor {

    private static final String hostPrimate = "api.weixin.qq.com";
    private final Logger log = LoggerFactory.getLogger(ApiCallMethodInterceptor.class);
    private final TokenStore store;
    private final RestTemplate template;

    public ApiCallMethodInterceptor(TokenStore store, RestTemplate template) {
        this.store = store;
        this.template = template;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        var method = invocation.getMethod();

        var apiInfo = method.getAnnotation(WeChatAPI.class);
        if (apiInfo == null) {
            return null;
        }

        var url = UriComponentsBuilder.newInstance()
                .scheme("https").host(hostPrimate)
                .pathSegment(apiInfo.path())
                .queryParam("access_token", store.getCurrentToken()).build().toUriString();

        var returnCls = method.getReturnType();

        switch (apiInfo.method()) {

            case GET -> {
                return template.getForObject(url, returnCls);

            }
            case POST -> {
                var args = invocation.getArguments();

                if (args.length == 0) {
                    throw new IllegalArgumentException("not enough args");
                }
                return template.postForObject(url, args[0], returnCls);
            }
            default -> {

                throw new IllegalArgumentException("not found match http method");

            }
        }
    }


}
