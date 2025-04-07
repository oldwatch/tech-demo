package com.demo.grpc.service.entity;

import org.javamoney.moneta.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("NICER_BUT_SLOWER_FILM_LIST")
public record FilmList(

        @Id
        @Column("FID")
        Integer id,
        @Column("TITLE")
        String title,
        @Column("DESCRIPTION")
        String description,
        @Column("CATEGORY")
        String category,
        @Column("PRICE")
        Money price,
        @Column("LENGTH")
        Integer length,
        @Column("RATING")
        String rating,
        @Column("ACTORS")
        String actors
) {

}


