package com.demo.newfeature.web.vo;

import com.demo.newfeature.helper.jackson.EntityIDMask;

public record IdResp(
        @EntityIDMask
        Integer id, String status) implements CommResp {

}
