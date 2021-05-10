package com.qidao.application.model.member.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TOKEN 常量枚举
 * @author Autuan.Yu
 */

@AllArgsConstructor
public enum TokenConstantEnum {
    ACCESS_TOKEN_NAME("AT"),
    HEADER_VERSION("VERSION"),
    HEADER_CANAL("CANAL"),
    HEADER_MACHINE("MACHINE"),
    REFRESH_TOKEN_KEY("QIDAO::REFRESH_TOKEN"),
    ;
    private String value;

    public String getValue(){
        return value;
    }
}
