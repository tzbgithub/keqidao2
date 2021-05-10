package com.qidao.application.model.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 4:10 PM
 */
@Getter
@AllArgsConstructor
public enum LogErrorConstantEnum {
    /**
     * 异常/崩溃信息，处理状态
     */
    STATUS_COMMITTED(0,"已提交"),
    STATUS_HANDLED(1,"已处理");

    private final int intVal;
    private final String strVal;
}
