package com.qidao.application.model.invite.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InviteErrorEnum implements ResultEnumInterface {

    ORGANIZATION_IMPERFECT("LxAPI-023001" , "组织机构信息未完善"),
    LEADER("LxAPI-023002" , "企业只有负责人才可以邀请"),
    GENERATOR_FAIL("LxAPI-023004" , "生成失败：无效的邀请类型"),
    TEACHER("LxAPI-023003" , "您不是老师"),
    EXISTS("LxAPI-023005" , "您已接受邀请，不可重复提交哦"),
    ASSISTANT("LxAPI-023006" , "您不是助手"),
    REGISTERED("LxAPI-023006" , "您不是新用户，不可接受邀请"),
    ASSISTANT_LIMIT("LxAPI-023016" , "您的助手已到达上限"),
    MEMBER_LIMIT("LxAPI-023017" , "企业人员达到上限"),
    LABEL_OR_INDUSTRY_NOT_NULL("LxAPI-0230045" , "标签或行业不能为空"),
    EXISTS_MEMBER_NOT_ASSISTANT("LxAPI-0230055" , "已注册的用户不能成为助手"),
    ;
    private final String code;
    private final String message;

}
