package com.qidao.application.model.msg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgSendTypeEnum {
    /**
     * 不使用消息模板
     */
    NO_TEMPLATE(0),
    /**
     * 发送验证码
     */
    CODE(1),
    /**
     * 发送合同到邮箱
     */
    CONTRACT_PDF(2),
    /**
     * 发送合同文案
     */
    CONTRACT_COPY(3),
    /**
     * 发送发票文案
     */
    INVOICE_COPY(4),

    /**
     * 未知
     */
    UNKNOWN(-1),
    ;
    private Integer val;

    public static MsgSendTypeEnum switchEnum(Integer val){
        for(MsgSendTypeEnum item : MsgSendTypeEnum.values()) {
            if(val.equals(item.val)) {
                return item;
            }
        }
        return UNKNOWN;
    }
}
