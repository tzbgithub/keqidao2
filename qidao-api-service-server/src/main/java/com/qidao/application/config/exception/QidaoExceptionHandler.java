package com.qidao.application.config.exception;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.service.event.PublishEventComponent;
import com.qidao.application.service.event.exception.ExceptionHandlerEvent;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.framework.web.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author Autuan.Yu
 */
@RestControllerAdvice
@Slf4j
public class QidaoExceptionHandler {
    @Autowired
    private PublishEventComponent publishEventComponent;

    @ExceptionHandler({BusinessException.class})
    public ResponseResult<Object> handleBusiness(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Object> handleHttp(HttpRequestMethodNotSupportedException e) {
        return Result.fail(BaseEnum.NOT_SUPPORT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseResult<Object> handleValidate(Exception e) {
        if(e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodE = (MethodArgumentNotValidException) e;
            List<ObjectError> allErrors = methodE.getAllErrors();
            // 只需要返回第一个即可
            if(CollUtil.isNotEmpty(allErrors)){
                return Result.fail("Px000-000001",allErrors.get(0).getDefaultMessage());
            }
        }
        e.printStackTrace();
        if(e instanceof HttpMessageNotReadableException) {
            return Result.fail(BaseEnum.JSON_XML_FORMAT_ERROR);
        }
        return Result.fail(BaseEnum.JSON_XML_FORMAT_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResponseResult<Object> handleBusiness(Exception e) {
        log.error("handler exception ", e);
        publishEventComponent.publishEvent(new ExceptionHandlerEvent(e));
        return Result.fail(BaseEnum.FAIL);
    }
}