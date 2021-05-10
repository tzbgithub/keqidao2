package com.qidao.application.model.pay.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayExceptionEnum implements ResultEnumInterface {
    PRODUCT("Px000-000001","产品不存在或已下架"),
    PERMISSION("Px000-000002","您没有此商品的购买权限"),
    ORDER_NULL("Px000-000003","未查询到订单"),
    ORDER("Px000-000004","该订单不能支付"),
    ORDER_CLOSE("Px000-000004","该订单已被关闭"),
    NOT_PAY("Px000-000005","该订单未支付"),
    REPEAT("Px000-000006","重复订单"),
    ADDRESS("Px000-000007","请输入正确的地址信息"),

    APPLE_FAIL("Px000-008000","支付失败"),
    APPLE_VERIFY("Px000-008001","校验失败"),
    ;
    private final String code;
    private final String message;
}
