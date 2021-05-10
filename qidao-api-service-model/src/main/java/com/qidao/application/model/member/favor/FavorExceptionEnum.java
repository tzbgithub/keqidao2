package com.qidao.application.model.member.favor;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum FavorExceptionEnum implements ResultEnumInterface {

    /**已删除请勿重复操作**/
    DELETE_TRUE("LxAPI-040001" , "已删除请勿重复操作"),
    /**不能收藏已被屏蔽用户的动态**/
    MEMBER_BLOCK("LxAPI-040002" , "不能收藏已被屏蔽用户的动态"),
    /**已收藏该动态请勿重复提交**/
    FAVOR_REPEAT("LxAPI-040003" , "已收藏该动态请勿重复提交")
    ;

    private final String code;
    private final String message;

    FavorExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
