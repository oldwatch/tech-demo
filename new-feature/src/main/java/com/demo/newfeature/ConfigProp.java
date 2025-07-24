package com.demo.newfeature;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.demo.new-feature")
public record ConfigProp(String sqIdMask) {

}
