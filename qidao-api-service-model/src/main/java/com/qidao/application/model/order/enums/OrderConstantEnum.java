package com.qidao.application.model.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderConstantEnum {

    /**
     * 虚拟订单
     */
    VIRTUAL_ORDER("0"),

    /**
     * 物理订单
     */
    PHYSICAL_ORDER("1"),
    ;

    private final String code;

    public Byte getByte(){
        return Byte.parseByte(code);
    }
    public Integer getInteger(){
        return Integer.valueOf(code);
    }
    public static Byte getPhysicalFlag(boolean isPhysicalOrder){
        return isPhysicalOrder ? OrderConstantEnum.PHYSICAL_ORDER.getByte() : OrderConstantEnum.VIRTUAL_ORDER.getByte();
    }
}
