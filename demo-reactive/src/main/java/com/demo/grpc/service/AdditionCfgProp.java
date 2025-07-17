package com.demo.grpc.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.demo.demo-reactive")
public record AdditionCfgProp(String sqIdMask) {
}
