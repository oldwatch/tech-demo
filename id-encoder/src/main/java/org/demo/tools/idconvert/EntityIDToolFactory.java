package org.demo.tools.idconvert;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(ConfigProp.class)
public class EntityIDToolFactory {

    @Bean
    public IdEncodeTool idEncodeTool(ConfigProp prop) {
        return new IdEncodeTool(prop.sqIdMask());
    }

}
