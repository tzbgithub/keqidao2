package com.qidao.application.model.msg.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgExceptionEnum implements ResultEnumInterface {

    /**该消息已被删除或不存在**/
    DELETE_TRUE("LxAPI-020001" , "该消息已被删除或不存在"),
    CONTENT("Mx000-000001", "消息内容为空"),
    SUPPORT("Mx000-000002", "不支持的消息类型"),
    BATCH("Mx000-000003", "验证码不支持批量发送"),
    FREQUENT("Mx000-000005", "短信发送过于频繁，请稍后再试吧"),
    LIMIT("Mx000-000006", "超出今日上限，明天再试吧"),
    ATTACHMENT("Mx000-000007", "附件内容为空"),
    TITLE("Mx000-000008", "标题为空"),
    ;

    private final String code;

    private final String message;

}
