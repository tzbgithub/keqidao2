package com.qidao.application.model.common;

import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ResultEnumInterface;
import com.qidao.framework.web.ResponseResult;

import java.time.LocalDateTime;

public class Result {
    public static <T> ResponseResult<T> ok(){
        return ok(null);
    }
    public static <T> ResponseResult<T> ok(T data){
        return ok(BaseEnum.SUCCESS.getCode(),data);
    }
    public static <T> ResponseResult<T> ok(String code,T data){
        return response(code, data, BaseEnum.SUCCESS.getMessage());
    }
    public static <T> ResponseResult<T> fail(String message){
        return fail(BaseEnum.FAIL.getCode(),message);
    }
    public static <T> ResponseResult<T> fail(String code,String message){
        return  response(code,null,message);
    }
    public static <T> ResponseResult<T> fail(ResultEnumInterface enumInterface){
        return fail(enumInterface.getCode(), enumInterface.getMessage());
    }
    public static <T> ResponseResult<T> response(String code,T data,String message){
        return response(code, data, message,LocalDateTime.now());
    }
    public static <T> ResponseResult<T> response(String code, T data, String message, LocalDateTime timestamp){
        ResponseResult<T> result = new ResponseResult<>();
        result.setTimestamp(timestamp);
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }
}
