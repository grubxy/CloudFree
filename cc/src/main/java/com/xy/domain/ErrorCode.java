package com.xy.domain;

public enum  ErrorCode {

    PRODUCT_ID_ERROR(1001, "产品ID异常"),

    SEQ_ID_ERROR(2001, "工序ID异常"),

    CONSTRUCTION_COUNTS_ERROR(3001, "工单数量错误，必须小于总量大于前批完成量"),

    CONSTRUCTION_SEQINFO_ERROR(3002, "工单找不到匹配的工序详情，系统有异常"),

    CONSTRUCTION_ENUM_ERROR(3003, "工单状态枚举错误"),

    CONSTRUCTION_COMPLETECOUNTS_ERROR(3003, "完工数量和次品数量与工单数量不匹配"),

    USER_USERNAME_EXIST(4001, "用户名已经存在"),

    HOUSE_ORIGIN_ID_ERROR(5001, "原料有误"),

    HOUSE_ORIGIN_COUNT_ERROR(5002, "数量有误");


    private int code;

    private String msg;

    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
