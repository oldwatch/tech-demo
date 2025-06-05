package com.demo.newfeature;

import com.demo.newfeature.helper.jackson.EntityIDJacksonModule;
import com.demo.newfeature.helper.jackson.SealedClsJacksonModule;
import com.demo.newfeature.web.helper.TokenFilter;
import io.micrometer.meter.influx3.SpringInflux3Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableConfigurationProperties(ConfigProp.class)
@SpringBootApplication
@Import(SpringInflux3Configuration.class)
public class NewFeatureApp {

    public static void main(String[] argv) {

        SpringApplication.run(NewFeatureApp.class, argv);

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

    @Bean
    public FilterRegistrationBean<TokenFilter> myFilterRegistrationBean() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenFilter());
        registrationBean.addUrlPatterns("/demo/*"); // Specify URL patterns to apply the filter
        registrationBean.setOrder(1);
        return registrationBean;
    }


}
