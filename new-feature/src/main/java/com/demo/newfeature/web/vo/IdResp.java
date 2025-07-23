package com.demo.newfeature.web.vo;


import org.demo.idconvert.jackson.EntityIDMask;

public record IdResp(
        @EntityIDMask
        Integer id, String status) implements CommResp {

}
