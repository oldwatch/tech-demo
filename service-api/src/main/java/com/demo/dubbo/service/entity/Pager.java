package com.demo.dubbo.service.entity;

import java.io.Serializable;

public record Pager(String token, Integer pageNum, Integer pageSize) implements Serializable {
}
