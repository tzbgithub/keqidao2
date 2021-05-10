package com.qidao.application.model.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付 常量
 */
@AllArgsConstructor
public enum PayConstantsEnum {
    CURRENCY("CNY"),
    ;
    private String val;

    public String getStr(){
        return val;
    }
}
