package com.xy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UserException extends RuntimeException {

    @Getter @Setter
    private int code;

    @Getter @Setter
    private String msg;

    public UserException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
