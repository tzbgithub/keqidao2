package com.qidao.application.service.member.other;

import lombok.AllArgsConstructor;

/**
 * 登录类型
 **/
@AllArgsConstructor
public enum LoginTypeEnum {
    /**
     * 手机号/账号 + 验证码 登录
     */
    PHONE_OR_ACCOUNT_BY_CODE(0),
    /**
     * 微信 登录
     */
    WX(1),
    /**
     * 手机号 + 机器码 登录
     */
    PHONE_BY_MACHINE_CODE(2),
    /**
     * 手机号 + 密码 登录
     */
    PHONE_BY_PASSWORD(3),
    /**
     * token登录
     */
    TOKEN(4),
    /**
     * 账号(编号) 密码 登录
     */
    ACCOUNT_BY_PASSWORD(5);


    /**
     * 登录类型
     */
    Integer type;

    public Integer getType() {
        return type;
    }
}
