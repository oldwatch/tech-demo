package com.demo.dubbo.service;

import java.io.Serializable;

public class DemoServiceException extends RuntimeException implements Serializable {

    public DemoServiceException(Exception e) {
        super(e);
    }

    public DemoServiceException(String msg) {
        super(msg);
    }
}
