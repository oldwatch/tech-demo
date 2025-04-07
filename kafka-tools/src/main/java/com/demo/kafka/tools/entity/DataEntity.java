package com.demo.kafka.tools.entity;

import com.demo.kafka.tools.helper.Utils;

import java.time.LocalDateTime;

public record DataEntity(int seq, String text, LocalDateTime timestamp, Status status, Integer val) {

    public DataEntity(String text, int seq) {
        this(seq, text, LocalDateTime.now(), Status.Start, Utils.getRandomInt());
    }

    public DataEntity(DataEntity entity) {
        var status = entity.seq % 3 == 0 ? Status.End : Status.Operate;
        this(entity.seq, entity.text, entity.timestamp, status, entity.val);
    }


    public enum Status {
        Start, Operate, End, Error
    }
}
