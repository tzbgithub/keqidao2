package com.qidao.application.model.dynamic;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum ComplaintExceptionEnum implements ResultEnumInterface {

    /** 该动态不存在 **/
    COMPLAINT_DYNAMIC_NOT_EXISTS("LxAPI-020001", "该动态不存在"),
    /** 该用户不存在 **/
    COMPLAINT_MEMBER_NOT_EXISTS("LxAPI-020002", "该用户不存在"),
    /** 该用户自己不能投诉自己 **/
    COMPLAINT_ONESELF_EXISTS("LxAPI-020003", "该用户自己不能投诉自己"),
    /** 投诉原因不存在 **/
    COMPLAINT_REASON_NOT_EXISTS("Lx-API-020004", "投诉原因不存在"),
    /** 重复投诉 **/
    DYNAMIC_AGAIN_EXISTS("Lx-API-0200050", "该用户已经投诉过该动态不能重复投诉"),
    MEMBER_AGAIN_EXISTS("Lx-API-0200051", "不能重复投诉"),
    ;

    private final String code;
    private final String message;

    ComplaintExceptionEnum(String code, String message){
        this.code = code;
        this.message = message;
    }
}
