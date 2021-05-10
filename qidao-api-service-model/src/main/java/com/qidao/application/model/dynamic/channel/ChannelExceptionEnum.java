package com.qidao.application.model.dynamic.channel;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum ChannelExceptionEnum implements ResultEnumInterface {

    CHANNEL_NOT_EXIST("Lx-API-012001", "该频道不存在或已删除")
    ;

    private final String code;

    private final String message;

    ChannelExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
