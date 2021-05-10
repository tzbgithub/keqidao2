package com.qidao.application.service.event.exception;

import com.qidao.application.service.dingtalk.IDingTalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExceptionHandlerListen implements SmartApplicationListener {
    @Autowired
    private IDingTalkService dingTalkService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return aClass == ExceptionHandlerEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        ExceptionHandlerEvent event = (ExceptionHandlerEvent) applicationEvent;
        String description = event.getDescription();
        String stackMsg = event.getStackMsg();

        dingTalkService.sendMsg(description+"\n"+stackMsg);
    }
}
