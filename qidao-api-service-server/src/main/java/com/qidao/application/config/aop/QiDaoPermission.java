package com.qidao.application.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * 权限验证
 * @author Autuan.Yu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QiDaoPermission {
    /**
     * 基本验证： 验证请求体里是否含有memberId , 并和 token进行一次验证
     */
    boolean value() default true;

    /**
     * 允许通过的memberId,  ','分割
     */
    String ableMember() default "";

    int accessLevel() default 0;
}
