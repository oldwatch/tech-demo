package com.demo.newfeature;

import com.demo.newfeature.helper.SealedClsJacksonModule;
import com.demo.newfeature.web.helper.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableConfigurationProperties(ConfigProp.class)
@SpringBootApplication
//@Configuration
public class NewFeatureApp {

    public static void main(String[] argv) {

        SpringApplication.run(NewFeatureApp.class, argv);

    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.postConfigurer(objectMapper -> {
                // Customize the ObjectMapper here
                objectMapper.registerModule(new SealedClsJacksonModule());
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
