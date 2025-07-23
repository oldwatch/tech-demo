package com.demo.dubbo.service.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public record DemoEntity(

        String etityId,

        String name,

        LocalDateTime submitTime,
        Integer intValue,
        Float decValue,
        StatusType status,
        LocalDateTime createdDate,
        String createdBy) implements Serializable {

    public DemoEntity(DemoInput input, String id, String user) {
        this(id, input.name(), input.submitTime(), input.intValue(), input.decValue(), StatusType.RUN, LocalDateTime.now(), user);
    }

    public DemoEntity(DemoInput input, DemoEntity oldEntity) {
        this(oldEntity.etityId,
                input.name(), input.submitTime(), input.intValue(), input.decValue(),
                StatusType.RUN, oldEntity.createdDate, oldEntity.createdBy());
    }

    public enum StatusType {
        RUN,
        STOP,
        DELETED
    }

}
