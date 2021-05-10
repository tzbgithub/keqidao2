package com.qidao.application.model.config;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum SelectConfigExceptionEnum implements ResultEnumInterface {

    /**类型不合法**/
    TYPE_WRONGFUL("LxAPI-050001","类型不合法"),
    INDUSTRYID_NOTFOUND("LxAPI-050001","行业不存在")
    ;

    private final String code;

    private final String message;


    SelectConfigExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
