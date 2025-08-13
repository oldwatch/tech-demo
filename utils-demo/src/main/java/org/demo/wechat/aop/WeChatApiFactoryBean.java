package org.demo.wechat.aop;

import org.demo.wechat.TokenStore;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

public class WeChatApiFactoryBean<T extends ApiService> implements FactoryBean<T> {

    private final TokenStore store;
    private final RestTemplate template;
    private final ClassLoader classLoader;
    private final Class<T> interfaceCls;

    public WeChatApiFactoryBean(TokenStore store, RestTemplate template, Class<T> interfaceCls) {
        this.store = store;
        this.template = template;
        this.classLoader = ClassUtils.getDefaultClassLoader();
        this.interfaceCls = interfaceCls;
    }


    private T generServiceInstance() {
        
        ProxyFactory result = new ProxyFactory();
        result.setTarget(new ApiService() {
        });
        result.setInterfaces(interfaceCls);

        result.addAdvice(new ApiCallMethodInterceptor(store, template));

        return (T) result.getProxy(classLoader);

    }

    @Override
    public T getObject() throws Exception {
        return generServiceInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceCls;
    }
}
