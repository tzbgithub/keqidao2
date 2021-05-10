package com.qidao.application.model.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductTypeEnum {
    /**
     * 计费方式：时间
     */
    TYPE_TIME(0),
    /**
     * 计费方式：次数
     */
    TYPE_NUMBER(1),

    /**
     * 虚拟订单
     */
    PRODUCT_VIRTUAL(0),
    /**
     * 实物订单
     */
    PRODUCT_PHYSICAL(1),


    ;

    private final Integer code;
}
