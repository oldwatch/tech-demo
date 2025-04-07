package com.demo.grpc.service.repo;

import com.demo.grpc.service.entity.FilmList;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DemoRepo extends R2dbcRepository<FilmList, Integer> {

    @Query("SELECT * FROM nicer_but_slower_film_list where rating = :rating and category=  :category ")
    public Flux<FilmList> getListByRatingAndCategory(String rating, String category);

    @Query("SELECT * FROM nicer_but_slower_film_list where  category=  :category ")
    public Flux<FilmList> getListByCategory(String category);

}
