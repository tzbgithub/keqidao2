package com.qidao.application.service.event.member;

import org.springframework.context.ApplicationEvent;

public class MemberEvent extends ApplicationEvent {
    public MemberEvent(Object source) {
        super(source);
    }
}
