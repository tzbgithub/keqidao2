package com.qidao.application.model.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常量枚举
 * @author Autuan.Yu
 */

@AllArgsConstructor
public enum ConstantEnum {
    /**
     * 逻辑删除未删除
     */
    NOT_DEL("0"),
    /**
     * 逻辑删除已删除
     */
    DELETED("1"),
    /**
     * 常用变量： memberId
     */
    VAR_MEMBER_ID("memberId"),
    VAR_ID("id"),
    VAR_MEMBER_ID_PARTY_A("memberIdPartyA"),
    /**
     * 逗号分割符
     */
    SPLIT_STR_COMMA(","),
    /**
     * . 分割符
     */
    SPLIT_STR_DOT("."),
    /**
     * 无值空串
     */
    EMPTY_STR(""),
    ;
    private final String value;

    public byte getByte(){
        return Byte.parseByte(value);
    }

    public int getInt(){
        return Integer.parseInt(value);
    }

    public String getStr(){
        return value;
    }
}
