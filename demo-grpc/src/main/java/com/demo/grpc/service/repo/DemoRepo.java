package com.demo.grpc.service.repo;

import com.demo.grpc.service.entity.FilmList;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;

public interface DemoRepo extends Repository<FilmList, Integer> {

    @Query("SELECT * FROM public.nicer_but_slower_film_list where rating = :rating and category=  :category ")
    public Flux<FilmList> getListByRating(String rating, String category);
    
}
