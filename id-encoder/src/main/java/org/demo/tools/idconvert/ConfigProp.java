package org.demo.tools.idconvert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.demo.tools.id-convert")
public record ConfigProp(String sqIdMask) {

}
