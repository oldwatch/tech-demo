package org.demo.idconvert;

import org.demo.idconvert.jackson.EntityIDJacksonModule;
import org.demo.idconvert.jackson.SealedClsJacksonModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(EntityIDToolFactory.class)
public class HelperFactory {

    @Bean
    public EntityIDJacksonModule entityIDJacksonModule(IdEncodeTool encodeTool) {
        return new EntityIDJacksonModule(encodeTool);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(EntityIDJacksonModule module) {
        return builder -> {
            builder.postConfigurer(objectMapper -> {
                // Customize the ObjectMapper here

                objectMapper.registerModule(new SealedClsJacksonModule());
                objectMapper.registerModule(module);
            });
        };
    }

}
