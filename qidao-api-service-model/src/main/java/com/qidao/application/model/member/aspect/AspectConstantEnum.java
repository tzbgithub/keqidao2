package com.qidao.application.model.member.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 切面枚举常量
 * @author Autuan.Yu
 */
@AllArgsConstructor
@Getter
public enum AspectConstantEnum {
    LOG_MEMBER_ID("logMemberId"),
    UNION_ID("unionId"),
    MEMBER_ID("memberId"),
    /**
     * 日志字符串长度最大
     */
    LOG_MAX_LENGTH("2000"),

    /**
     * 第三方  微信
     */
    WECHAT("0"),
    /**
     * oper type : 其他
     */
    TYPE_OTHER("0"),
    /**
     *
     */
    TYPE_MP("1"),
    ;
    private String value;

    public int getInt(){
        return Integer.parseInt(value);
    }

    public static Integer switchOperType(String canal){
        if(TYPE_OTHER.value.equals(canal)) {
            return TYPE_OTHER.getInt();
        }
        if(TYPE_MP.value.equals(canal)) {
            return TYPE_MP.getInt();
        }
        return TYPE_OTHER.getInt();
    }
}
