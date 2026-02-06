module wechat.adapter {

    requires org.slf4j;
    requires tools.jackson.databind;
    requires spring.beans;
    requires spring.aop;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.boot.jackson;

    exports org.demo.wechat;

    
}