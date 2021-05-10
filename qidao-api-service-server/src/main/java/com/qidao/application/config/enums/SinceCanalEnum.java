package com.qidao.application.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SinceCanalEnum {
    ALL("all"),
    IOS("AppStore"),
    XiaoMi("XiaoMi"),
    HuaWei("HuaWei"),

    DEFAULT_VERSION("0.0.0")
    ;
    String value;


}
