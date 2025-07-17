package com.demo.grpc.service.web;


import com.demo.grpc.service.management.DemoManager;
import com.demo.grpc.service.web.vo.FilmListVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/films")
@RestController
public class DemoController {


    private final DemoManager manager;

    public DemoController(DemoManager manager) {
        this.manager = manager;
    }

    @GetMapping("/category/{category}/rating/{rating}")
    public Flux<FilmListVO> getListByRatingAndCategory(@PathVariable("category") String category,
                                                       @PathVariable("rating") String rating) {

        return manager.getListByCategoryAndRating(category, rating)
                .map(FilmListVO::new);
    }


    @GetMapping("/category/{category}")
    public Flux<FilmListVO> getListByCategory(@PathVariable("category") String category) {

        return manager.getListByCategory(category)
                .map(FilmListVO::new);
    }

}
