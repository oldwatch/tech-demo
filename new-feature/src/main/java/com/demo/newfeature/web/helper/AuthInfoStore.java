package com.demo.newfeature.web.helper;

import jakarta.servlet.ServletException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthInfoStore {

    private static final ScopedValue<String> USER_INFO = ScopedValue.newInstance();

    public void init(String userInfo, Runnable callback) {

        ScopedValue.where(USER_INFO, userInfo).run(callback);
    }

    public String getUserInfo() {
        return USER_INFO.get();
    }

    public static class FilterException extends RuntimeException {
        private final Exception innerEx;

        //        private  final IOException ioEx;
        public FilterException(Exception exception) {
            this.innerEx = exception;
        }

        public void reThrow() throws IOException, ServletException {

            switch (innerEx) {
                case IOException e -> throw e;
                case ServletException e -> throw e;
                default -> {
                }
            }
        }
    }
}
