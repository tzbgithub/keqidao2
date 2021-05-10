package com.qidao.application.model.order.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderExceptionEnum implements ResultEnumInterface {


    /**
     * 订单已被删除或不存在
     */
    DELETE_TRUE("LxAPI-060001" , "订单已被删除或不存在"),

    /**
     * 订单不是未支付状态
     */
    UNPAID("LxAPI-060002" , "订单不是未支付状态"),

    /**
     * 订单状态不对
     */
    STATUS("LxAPI-060003" , "订单状态校验失败"),

    /**
     * 不支持的产品类型
     */
    SUPPORT("LxAPI-060004" , "目前只支持计时类型产品"),
    ;


    private final String code;
    private final String message;

}
