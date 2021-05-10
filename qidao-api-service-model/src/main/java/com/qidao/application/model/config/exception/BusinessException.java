package com.qidao.application.model.config.exception;

import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ResultEnumInterface;

import java.util.function.Supplier;

/**
 * @author Autuan.Yu
 */
public class BusinessException extends RuntimeException implements Supplier<BusinessException> {
    protected String code;
    protected String message;

    public BusinessException(){
        this.code=BaseEnum.FAIL.getCode();
        this.message=BaseEnum.FAIL.getMessage();
    }
    public BusinessException(String message) {
        this.code = BaseEnum.FAIL.getCode();
        this.message = message;
    }
    public BusinessException(ResultEnumInterface enums){
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }
    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public String getCode(){return this.code;}
    @Override
    public String getMessage(){return this.message;}

    @Override
    public BusinessException get() {
        return this;
    }
}
