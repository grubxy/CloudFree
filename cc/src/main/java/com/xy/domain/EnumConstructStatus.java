package com.xy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeConverter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumConstructStatus {
    ALL(0, "所有状态"),

    WAITING(1, "等待材料出库"),

    WORKING(2, "制作过程中"),

    COMPLETE(3, "完工待入库"),

    STORED(4, "入库完待审批"),

    APPROVED(5, "审批完毕"),

    SCHEDULE(6, "等待出库与制作中");

    EnumConstructStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Setter @Getter
    //@Getter(onMethod = @__(@JsonValue))
    private int value;

    @Setter @Getter
    private String desc;

    public static class Convert implements AttributeConverter<EnumConstructStatus, String> {
        @Override
        public String convertToDatabaseColumn(EnumConstructStatus attribute) {
            return attribute == null?null:attribute.getDesc();
        }

        @Override
        public EnumConstructStatus convertToEntityAttribute(String desc) {
            for (EnumConstructStatus type:EnumConstructStatus.values()) {
                if (desc.equals(type.getDesc())) {
                    return type;
                }
            }
            throw new UserException(ErrorCode.CONSTRUCTION_ENUM_ERROR.getCode(), ErrorCode.CONSTRUCTION_ENUM_ERROR.getMsg());
        }
    }

}
