package com.xy.domain;

import lombok.Data;

@Data
public class UserException extends RuntimeException {
    private String code;

    private String msg;

    public UserException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
