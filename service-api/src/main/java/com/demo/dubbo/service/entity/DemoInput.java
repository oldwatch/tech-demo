package com.demo.dubbo.service.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public record DemoInput(
        String name,
        LocalDateTime submitTime,
        Integer intValue,
        Float decValue
) implements Serializable {
}
