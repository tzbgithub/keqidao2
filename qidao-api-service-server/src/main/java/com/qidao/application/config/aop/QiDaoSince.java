package com.qidao.application.config.aop;

import com.qidao.application.config.enums.SinceCanalEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 版本控制注解
 * @author 晚成
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QiDaoSince {
    /**
     * 最低支持版本号
     * 默认 0.0.0 : 全部支持
     */
    String value() default "0.0.0";

    /**
     * 支持渠道
     * 默认：不分我各个渠道
     *
     * @return
     */
    SinceCanalEnum canal() default SinceCanalEnum.ALL;

    /**
     * 最后支持版本
     * @return
     */
    String endSupport() default "";

}
