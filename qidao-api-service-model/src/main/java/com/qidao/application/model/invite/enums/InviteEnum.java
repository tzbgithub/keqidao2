package com.qidao.application.model.invite.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InviteEnum {

    /**
     * 已绑定手机号
     */
    PHONE(0),
    /**
     * 已登录APP绑定邀请人
     */
    LOGIN(1),
    /**
     * 未登录APP过期
     */
    EXPIRE(2),
    /**
     * 老师邀请助手
     */
    TEACHER_INVITE_ASSISTANT(0),
    /**
     * 老师邀请老师
     */
    TEACHER_INVITE_TEACHER(1),
    /**
     * 企业邀请成员
     */
    ENTERPRISE_INVITE_MEMBER(2),
    ;
    private final Integer code;
}
