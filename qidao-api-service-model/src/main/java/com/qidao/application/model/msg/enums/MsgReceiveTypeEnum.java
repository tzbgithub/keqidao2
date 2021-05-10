package com.qidao.application.model.msg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgReceiveTypeEnum {
    /**
     * 0. 全平台发送
     */
    ALL_MEMBER(0),
    /**
     * 1. 指定用户发送
     */
    GIVEN_MEMBER(1),
    /**
     * 2.所有VIP用户
     */
    @Deprecated
    ALL_VIP_MEMBER(2),
    /**
     * 3. 所有实验室用户及其助手（审核中和审核通过）
     */
    ALL_LABORATORY_ASSISTANT_MEMBER(3),
    /**
     * 4. 所有实验室用户及其助手 （审核通过）
     */
    ADOPT_LABORATORY_ASSISTANT_MEMBER(4),
    /**
     * 5. 所有企业用户
     */
    ALL_ENTERPRISE_MEMBER(5),
    /**
     * 6. 所有vip企业用户
     */
    ALL_ENTERPRISE_VIP_MEMBER(6),
    /**
     * 7.当前触发用户
     */
    CURRENT_TRIGGER_MEMBER(7),
    /**
     * 8-未修改名称&头像用户
     */
    NOT_UPDATE_NAME_HEAD_IMG_MEMBER(8),

    /**
     * 未知
     */
    UNKNOWN(-1),
    ;
    private Integer val;

    public static MsgReceiveTypeEnum switchEnum(Integer val) {
        for (MsgReceiveTypeEnum item : MsgReceiveTypeEnum.values()) {
            if (val.equals(item.val)) {
                return item;
            }
        }
        return UNKNOWN;
    }
}
