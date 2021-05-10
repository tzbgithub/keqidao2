package com.qidao.application.model.server;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 3:14 PM
 */
@Getter
public enum ServerCodeMessageEnum implements ResultEnumInterface {
    /**
     * ContractController、Service里用到的魔法字段
     * 上面统一列举 清单里的，下面再列举我自己定义的
     * SUCCESS 成功的情况
     * EXCEPTION_ 异常情况
     * FAILED_ 失败情况(参数错误、业务逻辑错误等)
     * FAILED_LXAPI_ 内部业务逻辑出现错误时返回L头状态码
     */
    SUCCESS("Sx200", "请求成功"),

    EXCEPTION_HX000_000007("Hx000-000007", "数据库内部错误"),

    FAILED_PX000_000001("Px000-000001", "必填项错误");


    private final String code;
    private final String message;

    ServerCodeMessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
