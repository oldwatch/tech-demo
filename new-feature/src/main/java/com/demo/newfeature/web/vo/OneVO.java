package com.demo.newfeature.web.vo;

import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.entity.StatusType;

import java.time.LocalDateTime;

public record OneVO(
        String entityId,

        String name,

        LocalDateTime submitTime,
        Integer intValue,
        Float decValue,
        StatusType status,
        LocalDateTime createdDate,
        String createdBy) {

    public OneVO(OneRec rec, String id) {
        this(id,
                rec.name(), rec.submitTime(), rec.intValue(), rec.decValue(), rec.status(), rec.commFields().createdDate(), rec.commFields().createdBy());

    }

}
