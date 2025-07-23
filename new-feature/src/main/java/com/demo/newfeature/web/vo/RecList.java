package com.demo.newfeature.web.vo;

import com.demo.newfeature.entity.OneRec;
import org.demo.idconvert.IdEncodeTool;
import org.demo.idconvert.Pager;
import org.demo.utils.DatetimeUtils;

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
        var pager = new Pager(pageSize, maxDate);

        var voList = recList.stream().map(e -> new OneVO(e)).toList();
        this(voList, idEncodeTool.encodePager(pager));
    }
}
