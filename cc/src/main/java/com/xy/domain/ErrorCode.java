package com.xy.domain;

public enum  ErrorCode {

    PRODUCT_ID_ERROR(1001, "产品ID异常"),

    PRODUCT_NO_SEQ(1002, "产品没有找到工序"),

    PRODUCT_NO_SEQINFO(1003, "产品没有找到工序信息"),

    PRODUCT_NO(1004, "没有找到产品"),

    SEQ_ID_ERROR(2001, "工序ID异常"),

    SEQ_NO_ERROR(2002, "工序列表中没有工序"),

    CONSTRUCTION_COUNTS_ERROR(3001, "工单数量错误，必须小于总量大于前批完成量"),

    CONSTRUCTION_SEQINFO_ERROR(3002, "工单找不到匹配的工序详情，系统有异常"),

    CONSTRUCTION_ENUM_ERROR(3003, "工单状态枚举错误"),

    CONSTRUCTION_COMPLETECOUNTS_ERROR(3003, "完工数量和次品数量与工单数量不匹配"),

    CONSTRUCTION_NO(3004, "没有找到对应工单"),

    USER_USERNAME_EXIST(4001, "用户名已经存在"),

    HOUSE_ORIGIN_ID_ERROR(5001, "原料有误"),

    HOUSE_NO_ERROR(5002, "没有仓库信息"),

    HOUSE_HAVE_ORIGIN(5003, "仓库中有物料无法删除"),

    HOUSE_ORIGIN_COUNT_ERROR(5002, "数量有误"),

    STAFF_ENUM_ERROR(6001, "员工状态有误"),

    STAFF_NO_ERROR(6002, "员工列表中没有员工"),

    STAFF_NO_DEFAULT_ERR(6003, "工序没有设置默认员工"),

    FLOW_NO(7001, "没有找到生产流程"),

    FLOW_EXISTS(7002, "流程已经建立无法删除");

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
