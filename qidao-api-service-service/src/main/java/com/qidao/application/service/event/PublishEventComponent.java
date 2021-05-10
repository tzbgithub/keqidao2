package com.qidao.application.service.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author Autuan.Yu
 */
@Component
public class PublishEventComponent implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
