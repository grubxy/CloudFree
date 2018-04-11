package com.xy.domain;

public enum EnumConstructStatus {
    WAITING,    // 等待材料出库

    WORKING,    // 制作过程中

    COMPLETE,   // 完工

    STORED,     // 入库

    APPROVING,    // 审批中

    APPROVED      // 审批完成
}
