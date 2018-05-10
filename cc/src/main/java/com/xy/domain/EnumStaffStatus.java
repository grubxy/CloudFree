package com.xy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeConverter;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  EnumStaffStatus {

    ALL(0, "所有"),

    POSITIONING(1, "在职"),

    LEAVED(2, "离职");

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String desc;

    EnumStaffStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static class Convert implements AttributeConverter<EnumStaffStatus, String> {
        @Override
        public String convertToDatabaseColumn(EnumStaffStatus attribute) {
            return attribute == null?null:attribute.getDesc();
        }

        @Override
        public EnumStaffStatus convertToEntityAttribute(String desc) {
            for (EnumStaffStatus type:EnumStaffStatus.values()) {
                if (desc.equals(type.getDesc())) {
                    return type;
                }
            }
            throw new UserException(ErrorCode.STAFF_ENUM_ERROR.getCode(), ErrorCode.STAFF_ENUM_ERROR.getMsg());
        }
    }
}
