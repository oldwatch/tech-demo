package com.demo.newfeature;

import com.demo.newfeature.web.helper.GRpcInterceptor;
import com.demo.newfeature.web.helper.TokenFilter;
import io.grpc.ServerInterceptor;
import io.micrometer.meter.influx3.SpringInflux3Configuration;
import org.demo.idconvert.HelperFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@Import({SpringInflux3Configuration.class, HelperFactory.class})
public class NewFeatureApp {

    public static void main(String[] argv) {

        SpringApplication.run(NewFeatureApp.class, argv);

    }

    //    @Bean
//    public En
//    @Bean
//    public IdEncodeTool idEncodeTool(ConfigProp prop) {
//        return new IdEncodeTool(prop.sqIdMask());
//    }
//
//    @Bean
//    public EntityIDJacksonModule entityIDJacksonModule(IdEncodeTool encodeTool) {
//        return new EntityIDJacksonModule(encodeTool);
//    }

//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(EntityIDJacksonModule module) {
//        return builder -> {
//            builder.postConfigurer(objectMapper -> {
//                // Customize the ObjectMapper here
//
//                objectMapper.registerModule(new SealedClsJacksonModule());
//                objectMapper.registerModule(module);
//            });
//        };
//    }

    @GlobalServerInterceptor
    @Bean
    @Order(100)
    public ServerInterceptor authInterceptor() {
        return new GRpcInterceptor();
    }

    @Bean
    public FilterRegistrationBean<TokenFilter> myFilterRegistrationBean() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenFilter());
        registrationBean.addUrlPatterns("/demo/*"); // Specify URL patterns to apply the filter
        registrationBean.setOrder(1);
        return registrationBean;
    }


}
