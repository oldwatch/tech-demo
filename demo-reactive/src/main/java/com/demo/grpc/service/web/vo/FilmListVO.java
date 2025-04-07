package com.demo.grpc.service.web.vo;

import com.demo.grpc.service.entity.FilmList;
import org.demo.helper.jackson.EntityIDMask;
import org.demo.money.MoneyTool;
import org.springframework.util.StringUtils;

public record FilmListVO(
        @EntityIDMask
        Integer entityId,
        String title,
        String description,
        String category,
        String price,
        Integer length,
        String rating,
        String[] actorList
) {
    public FilmListVO(FilmList entry) {

        var price = MoneyTool.formatPrice(entry.price());
        var actors = StringUtils.split(entry.actors(), ", ");

        this(entry.id(), entry.title(), entry.description(),
                entry.category(), price, entry.length(),
                entry.rating(), actors);
    }


}
