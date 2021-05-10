package com.qidao.application.model.member.subscribe;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/18 4:20 PM
 */
@Getter
public enum SubscribeCodeMessageEnum implements ResultEnumInterface {
    /**
     * SubscribeController、Service里用到的魔法字段
     * 上面统一列举 清单里的，下面再列举我自己定义的
     * SUCCESS 成功的情况
     * EXCEPTION_ 异常情况
     * FAILED_ 失败情况(参数错误、业务逻辑错误等)
     * FAILED_LXAPI_ 内部业务逻辑出现错误时返回L头状态码
     */
    SUCCESS("Sx200", "请求成功"),

    EXCEPTION_HX000_000007("Hx000-000007", "数据库内部错误"),

    FAILED_PX000_000001("Px000-000001", "必填项错误"),

    FAILED_LXAPI_000001("LxAPI-000001", "您不能对自己进行关注或屏蔽"),
    FAILED_LXAPI_000002("LxAPI-000002", "您不能关注已屏蔽的对象"),
    FAILED_LXAPI_000003("LxAPI-000003", "预删除的记录不存在，请勿重复删除"),
    FAILED_LXAPI_000004("LxAPI-000004", "不合法的关注/屏蔽对象类型");

    private final String code;
    private final String message;

    SubscribeCodeMessageEnum(String code, String message) {
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
