package com.xy.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    // 服务异常
    @ExceptionHandler(value = UserException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Reply errHandle(UserException ex) {
        return new Reply(ex.getMsg());
    }

    // 全局异常
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus
    public Reply globleErrHandle(Exception ex) {
        return new Reply(ex.getMessage());
    }
}
