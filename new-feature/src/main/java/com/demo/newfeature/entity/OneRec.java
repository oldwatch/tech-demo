package com.demo.newfeature.entity;

import com.demo.newfeature.web.grpc.proto.AddOneRequest;
import com.demo.newfeature.web.vo.AddOneReq;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

//(NAME,SUBMIT_TIME,INT_VAL,DEC_VAL,CREATED_DATE,STATUS)

@Table("T_ONE")
public record OneRec(
        @Id
        @Column("ID")
        Integer id,
        @Column("NAME")
        String name,
        @Column("SUBMIT_TIME")
        LocalDateTime submitTime,
        @Column("INT_VAL")
        Integer intValue,
        @Column("DEC_VAL")
        Float decValue,
        @Column("STATUS")
        StatusType status,
        @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
        CommFields commFields

) {

    public OneRec(AddOneReq req, String user) {
        this(null, req.name(), null,
                req.intValue(), req.decValue(), StatusType.RUN, new CommFields(user));
    }

    public OneRec(AddOneRequest req, String user) {
        this(null, req.getName(), null,
                req.getIntValue(), req.getDecValue(), StatusType.RUN, new CommFields(user));
    }


}
