package com.demo.newfeature.web.vo;

import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.helper.DatetimeUtils;
import com.demo.newfeature.helper.IdEncodeTool;
import com.demo.newfeature.management.DemoManagement;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public record RecList(List<OneVO> recList, String nextToken) {

    public RecList(List<OneRec> recList, int pageSize, IdEncodeTool idEncodeTool) {
        LocalDateTime maxDate = recList.stream()
                .map(e -> e.commFields().createdDate())
                .max(Comparator.comparing(e ->
                        e)
                ).orElse(DatetimeUtils.getLocalTime(0));
        var pager = new DemoManagement.Pager(pageSize, maxDate);

        var voList = recList.stream().map(e -> new OneVO(e)).toList();
        this(voList, idEncodeTool.encodePager(pager));
    }
}
