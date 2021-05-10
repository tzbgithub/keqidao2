package com.qidao.application.service.event.member;

import com.qidao.application.model.member.MemberUpdateEventParam;
import org.springframework.context.ApplicationEvent;

/**
 * 会员修改事件
 **/
public class MemberUpdateEvent extends ApplicationEvent {

    public MemberUpdateEvent(MemberUpdateEventParam source) {
        super(source);
    }

    public MemberUpdateEventParam getSourceToParam() {
        return (MemberUpdateEventParam) super.getSource();
    }
}
