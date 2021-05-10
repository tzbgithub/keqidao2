package com.qidao.application.service.event.member;

import com.qidao.application.model.member.BecomeVipEventParam;

/**
 * 成功VIP 事件
 */
public class BecomeVipEvent extends MemberEvent {
    public BecomeVipEvent(BecomeVipEventParam source) {
        super(source);
    }
}
