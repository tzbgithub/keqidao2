package com.qidao.application.model.member;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum MemberExceptionEnum implements ResultEnumInterface {

    /**该用户已被删除或不存在**/
    DELETE_TRUE("LxAPI-030001" , "该用户已被删除或不存在"),
    NOT_LOGIN("LxAPI-030002","您还没有登陆，请登陆后重试"),
    NOT_FOUND_PERSONAL("LxAPI-030003","没有查询到个人信息"),
    VERIFY_ERROR_PERSONAL("LxAPI-030004","条件不足"),
    INDUSTRY_LIMIT("LxAPI-030005","选择的行业过多"),
    MEMBER_NAME_IS_NUMBER("LxAPI-030006","用户名称不能是纯数字"),
    ROLE_SET("LxAPI-030007","设置用户角色失败"),

    /**
     * 助手相关
     */
    ASSISTANT_UNABLE("LxAPI-030100","此会员不能成为助手"),
    ASSISTANT_LIMIT("LxAPI-030101","助手超出上限"),
    NOT_FOUND_ORGAN("LxAPI-030004","没有查询到组织机构信息")

    ;


    private final String code;

    private final String message;

    MemberExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
