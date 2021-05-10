package com.qidao.application.model.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    /**
     * 未支付
     */
    UNPAID(0,"未支付"),
    /**
     * 已支付
     */
    PAID(1,"已支付"),
    /**
     * 已关闭
     */
    CLOSE(2,"已关闭"),
    /**
     * 已退款
     */
    REFUND(3,"已退款"),
    /**
     * 申诉
     */
    COMPLAINT(4,"申诉"),
    /**
     * 完成
     */
    DONE(5,"完成"),
    ;
    private final Integer type;
    private final String val;

    /**
     * 是否是允许的状态
     * @param code
     * @return
     */
    public static boolean contains(Integer code,OrderStatusEnum... enumArray){
        for(OrderStatusEnum orderConstantEnum : enumArray) {
            if(orderConstantEnum.type.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
