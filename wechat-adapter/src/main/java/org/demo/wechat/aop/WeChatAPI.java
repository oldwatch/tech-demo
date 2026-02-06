package org.demo.wechat.aop;

import module java.base;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WeChatAPI {

    String path();

    HttpMethodTypes method();

}
