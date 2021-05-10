package com.qidao.application.model.member.feedback;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

/**
 * @author: xinfeng
 * @create: 2021-01-29 10:33
 */
@Getter
public enum  FeedbackExceptionEnum implements ResultEnumInterface {

    MEMBER_NOT_EXIST("LxAPI-011001", "该用户不存在或已注销"),
    REASON_NOT_EXIST("LxAPI-011002", "该原因不存在已停用")
    ;

    private final String code;

    private final String message;

    FeedbackExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
