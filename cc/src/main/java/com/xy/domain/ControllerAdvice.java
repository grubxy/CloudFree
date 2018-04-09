package com.xy.domain;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    // 服务异常
    @ExceptionHandler(value = UserException.class)
    public Map errHandle(UserException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        return map;
    }

    // 全局异常
    @ExceptionHandler(value = Exception.class)
    public Map globleErrHandle(Exception ex) {
        Map map = new HashMap();
        map.put("code", "100");
        map.put("msg", ex.getMessage());
        return map;
    }
}
