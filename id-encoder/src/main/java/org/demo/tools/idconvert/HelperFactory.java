package org.demo.tools.idconvert;


import module spring.boot.jackson;
import module spring.context;

@Import(EntityIDToolFactory.class)
public class HelperFactory {

    @Bean
    public EntityIDJacksonModule entityIDJacksonModule(IdEncodeTool encodeTool) {
        return new EntityIDJacksonModule(encodeTool);
    }

//    @Bean
//    public JsonMapperBuilderCustomizer jsonCustomizer(EntityIDJacksonModule module) {
//
//        return jsonMapperBuilder -> {
//            jsonMapperBuilder.addModule(module);
//        };
//    }

}
