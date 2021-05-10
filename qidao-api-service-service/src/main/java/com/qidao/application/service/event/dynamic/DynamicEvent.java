package com.qidao.application.service.event.dynamic;

import com.qidao.application.model.dynamic.DynamicEventParam;
import org.springframework.context.ApplicationEvent;

/**
 * 动态事件
 */
public abstract class DynamicEvent extends ApplicationEvent {

    public DynamicEvent(DynamicEventParam source) {
        super(source);
    }
}
