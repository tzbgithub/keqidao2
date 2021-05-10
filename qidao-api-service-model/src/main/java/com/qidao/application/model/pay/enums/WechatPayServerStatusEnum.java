package com.qidao.application.model.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信支付服务器响应码
 */
@Getter
@AllArgsConstructor
public enum WechatPayServerStatusEnum {
    /**
     * 200 : 处理成功
     */
    OK(200, "响应成功"),

    /**
     * 处理成功 无返回Body
     */
    OK_NO_BODY(204, "响应成功,无BODY"),

    ;
    private Integer code;
    private String message;
}
