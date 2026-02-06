module business.newfeature {
    requires jakarta.servlet;
    requires org.slf4j;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.micrometer.metrics;
    requires spring.context;
    requires spring.data.commons;
    requires spring.data.jdbc;
    requires spring.data.relational;
    requires spring.tx;
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.grpc.core;
    requires com.google.common;
    requires io.grpc;
    requires io.grpc.stub;
    requires io.grpc.util;
    requires io.grpc.protobuf;
    requires com.google.protobuf;
    requires com.fasterxml.jackson.annotation;
    requires micrometer.commons;
    requires micrometer.core;
    requires influxdb3.java;
    requires org.jspecify;
    requires jsr305;
    requires demo.utils;
    requires id.encoder;

}