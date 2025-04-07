package com.demo.grpc.service.management;

import com.demo.grpc.service.entity.FilmList;
import com.demo.grpc.service.repo.DemoRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@Transactional
public class DemoManager {
    private final DemoRepo demoRepo;

    public DemoManager(DemoRepo demoRepo) {
        this.demoRepo = demoRepo;
    }

    public Flux<FilmList> getListByCategoryAndRating(String rating, String category) {
        return demoRepo.getListByRatingAndCategory(rating, category);
    }

    public Flux<FilmList> getListByCategory(String category) {
        return demoRepo.getListByCategory(category);
    }

}
