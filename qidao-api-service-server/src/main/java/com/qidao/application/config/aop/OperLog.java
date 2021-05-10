package com.qidao.application.config.aop;

import com.qidao.application.config.enums.BusinessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* 日志注解
* @author Autuan.Yu
*/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {

    String title() default "";

    public BusinessType businessType() default BusinessType.OTHER;

    String remark() default "";
    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 异步保存到数据库
     */
    boolean isAsyncSaveToDB() default false;
}
