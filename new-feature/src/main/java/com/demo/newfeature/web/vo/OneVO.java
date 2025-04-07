package com.demo.newfeature.web.vo;

import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.entity.StatusType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.demo.helper.jackson.EntityIDMask;

import java.time.LocalDateTime;

public record OneVO(
        @JsonProperty("entityId")
        @EntityIDMask
        Integer id,

        String name,

        LocalDateTime submitTime,
        Integer intValue,
        Float decValue,
        StatusType status,
        LocalDateTime createdDate,
        String createdBy) {

    public OneVO(OneRec rec) {
        this(rec.id(),
                rec.name(), rec.submitTime(), rec.intValue(), rec.decValue(), rec.status(), rec.commFields().createdDate(), rec.commFields().createdBy());

    }


}
