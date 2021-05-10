package com.qidao.application.model.msg.enums;

import lombok.Getter;

@Getter
public enum MsgConstantEnum {

    /**状态 0-正常**/
    STATUS_TRUE(0),
    /**状态 1-停用**/
    STATUS_FALSE(1),
    /**
     * 不推送
     */
    TYPE_NOT_SEND(0),
    /**
     * 立即推送
     */
    TYPE_SEND_NOW(1),
    /**
     * 每日推送
     */
    TYPE_DAY(2),
    /**
     * 定时发送
     */
    TYPE_SCHEDULE(3),
    /**
     * 触发类型：用户注册
     */
    TYPE_EVENT_SIGN_UP(4),
    /**
     * 滚动消息：达成合作
     */
    TYPE_CAROUSEL_COOPERATION(5),
    /**
     * 用户获取短信验证码
     */
    TYPE_VERIFY_CODE(6),
    /**
     * 触发类型：购买VIP成功
     */
    TYPE_BECOME_VIP(7),
    /**
     * msg_record : status : 未读
     */
    RECORD_UNREAD(0),
    /**
     *  msg_record : status : 已读
     */
    RECORD_READ(1),
    /**
     * msg_record : status : 3 未发送状态
     */
    RECORD_NOT_SEND(3),
    ;
    private final Integer code;

    MsgConstantEnum(Integer code) {
        this.code = code;
    }
}
