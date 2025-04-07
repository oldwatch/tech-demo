package com.demo.newfeature.web.vo;


import com.demo.newfeature.entity.CommFields;
import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.entity.StatusType;

public record AddOneReq(String name,
                        Integer intValue,
                        Float decValue) {

    public OneRec generRec(String user){
        return new OneRec(null,name,null,
                intValue,decValue, StatusType.RUN,new CommFields(user));
    }
}
