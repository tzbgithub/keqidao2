package com.qidao.application.service.event.exception;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


/**
 * Exception 异常捕获事件
 * @author Autuan.Yu
 */
@Getter
public class ExceptionHandlerEvent extends ApplicationEvent {
    private String description;
    private String stackMsg;

    public ExceptionHandlerEvent(Exception e){
        super(e);
        String message = e.getMessage();
        String stackMsg = e.getStackTrace()[0].toString();
        this.description=message;
        this.stackMsg=stackMsg;
    }
}
