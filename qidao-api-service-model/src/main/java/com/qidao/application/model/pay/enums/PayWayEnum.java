package com.qidao.application.model.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式枚举类
 */
@AllArgsConstructor
@Getter
public enum PayWayEnum {
    /**
     * 微信APP支付
     */
    WECHAT_APP(0,"微信APP支付"),
    /**
     * 微信小程序支付
     */
    WECHAT_MP(1,"微信小程序支付"),
    /**
     * 微信H5支付
     */
    WECHAT_H5(2,"微信H5支付"),
    /**
     * 支付宝app支付
     */
    ALIPAY(3,"支付宝app支付"),
    /**
     * 支付宝 H5
     */
    ALIPAY_H5(4,"支付宝 H5"),
    /**
     * 银联支付
     */
    UNION_PAY(5,"银联支付"),
    /**
     * 支付宝网页
     */
    ALIPAY_PAGE(6,"支付宝网页"),
    /**
     * 微信 网页支付 (native)
     */
    WECHAT_PAGE(7,"微信 网页支付 (native)"),
    /**
     * APPLE 苹果商店支付
     */
    APPLE(8,"APPLE 苹果商店支付"),

    /**
     * 无/未知
     */
    UNKNOWN(-1,"无/未知"),

    ;
    private final Integer type;
    private final String val;
}
