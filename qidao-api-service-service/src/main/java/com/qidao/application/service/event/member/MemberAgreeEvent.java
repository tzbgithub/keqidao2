package com.qidao.application.service.event.member;

import com.qidao.application.model.dynamic.comment.RegisterEventParam;

public class MemberAgreeEvent extends MemberEvent {

    public MemberAgreeEvent(RegisterEventParam source) {
        super(source);
    }
}
