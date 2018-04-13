package com.xy.domain;

public enum  ErrorCode {

    PRODUCT_ID_ERROR("100001", "产品ID异常"),

    SEQ_ID_ERROR("200001", "工序ID异常"),

    CONSTRUCTION_COUNTS_ERROR("300001", "工单数量错误，必须小于总量大于前批完成量"),

    CONSTRUCTION_SEQINFO_ERROR("300002", "工单找不到匹配的工序详情，系统有异常"),

    CONSTRUCTION_COMPLETECOUNTS_ERROR("300003", "完工数量和次品数量与工单数量不匹配");

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
