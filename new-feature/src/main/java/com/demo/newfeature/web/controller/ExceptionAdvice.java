package com.demo.newfeature.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getLocalizedMessage());
    }

}
