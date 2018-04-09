package com.xy.domain;

public enum  ErrorCode {

    PRODUCT_ID_ERROR("100001", "产品ID异常"),

    SEQ_ID_ERROR("200001", "工序ID异常");


    private String code;

    private String msg;

    private ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
