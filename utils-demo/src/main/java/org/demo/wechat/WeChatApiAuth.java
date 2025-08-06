package org.demo.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("org.demo.wechat.auth")
public record WeChatApiAuth(String apiKey, String apiSecret) {
}
