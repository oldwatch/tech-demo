package com.demo.newfeature.entity;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

public record CommFields(@Column("CREATED_DATE")
                         @ReadOnlyProperty
                         LocalDateTime createdDate,
                         @Column("IS_DELETED")
                         Boolean deleted,
                         @Column("CREATED_BY")
                         String createdBy,
                         @Version
                         @Column("VERSION")
                         String version) {

    public CommFields(String owner){
        this(null,false,owner,null);
    }
}
