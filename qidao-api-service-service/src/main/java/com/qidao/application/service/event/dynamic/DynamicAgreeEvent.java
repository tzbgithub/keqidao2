package com.qidao.application.service.event.dynamic;

import com.qidao.application.model.dynamic.DynamicEventParam;

/**
 * 动态点赞事件
 */
public class DynamicAgreeEvent extends DynamicEvent{
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DynamicAgreeEvent(DynamicEventParam source) {
        super(source);
    }
}
