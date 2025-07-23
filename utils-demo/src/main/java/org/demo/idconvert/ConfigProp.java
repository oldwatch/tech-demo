package org.demo.idconvert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.demo.id-convert")
public record ConfigProp(String sqIdMask) {

}
