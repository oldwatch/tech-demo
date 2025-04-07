package com.demo.newfeature;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("com.demo.new-feature")
@Component
@Data
public class ConfigProp {

    private String sqIdMask;

}
