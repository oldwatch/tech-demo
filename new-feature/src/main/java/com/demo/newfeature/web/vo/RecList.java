package com.demo.newfeature.web.vo;

import module demo.utils;
import module id.encoder;
import module java.base;
import com.demo.newfeature.entity.OneRec;


public record RecList(List<OneVO> recList, String nextToken) {

    public RecList(List<OneRec> recList, int pageSize, IdEncodeTool idEncodeTool) {
        LocalDateTime maxDate = recList.stream()
                .map(e -> e.commFields().createdDate())
                .max(Comparator.comparing(e ->
                        e)
                ).orElse(DatetimeUtils.getLocalTime(0));
        var pager = new Pager(pageSize, maxDate);

        var voList = recList.stream().map(e -> new OneVO(e)).toList();
        this(voList, idEncodeTool.encodePager(pager));
    }
}
