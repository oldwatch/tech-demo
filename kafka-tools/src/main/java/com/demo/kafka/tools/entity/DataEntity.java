package com.demo.kafka.tools.entity;

import java.time.LocalDateTime;

public record DataEntity(int seq, String text, LocalDateTime timestamp, Status status) {

    public DataEntity(String text, int seq) {
        this(seq, text, LocalDateTime.now(), Status.Start);
    }

    public DataEntity(DataEntity entity, DataEntity.Status status) {
        this(entity.seq, entity.text, entity.timestamp, status);
    }


    public enum Status {
        Start, Operate, End, Error
    }
}
