package com.qidao.application.model.member;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum CanalExceptionEnum implements ResultEnumInterface {

    VERSION_NOT_FOUND("LxAPI-100002","版本号不存在"),

            ;

    private final String code;

    private final String message;

    CanalExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
