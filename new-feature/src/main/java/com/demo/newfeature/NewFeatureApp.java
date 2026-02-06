package com.demo.newfeature;

import module id.encoder;
import module spring.boot;
import module spring.boot.autoconfigure;
import module spring.context;
import module spring.core;
import module spring.grpc.core;
import module spring.tx;
import com.demo.newfeature.web.helper.GRpcInterceptor;
import com.demo.newfeature.web.helper.TokenFilter;
import io.grpc.ServerInterceptor;
import io.micrometer.meter.influx3.SpringInflux3Configuration;


@EnableTransactionManagement
@SpringBootApplication
@Import({SpringInflux3Configuration.class, HelperFactory.class})
public class NewFeatureApp {

    public static void main(String[] argv) {

        SpringApplication.run(NewFeatureApp.class, argv);

    }

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
