package com.demo.newfeature.web.vo;

import module id.encoder;

public record IdResp(
        @EntityIDMask
        Integer id, String status) implements CommResp {

}
