package com.demo.newfeature.web.helper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

public class TokenFilter implements Filter {


    @Autowired
    private AuthInfoStore store;


    @Override
    public void init(FilterConfig cfg) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        var userId = "test";

        try {
            store.init(userId, () -> {
                try {
                    filterChain.doFilter(servletRequest, servletResponse);
                } catch (IOException | ServletException e) {
                    throw new AuthInfoStore.FilterException(e);
                }
            });
        } catch (AuthInfoStore.FilterException e) {
            e.reThrow();
        }

    }
}
