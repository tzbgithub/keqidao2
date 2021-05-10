package com.qidao.application.model.log;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  LogBehaveMemberErrorEnum implements ResultEnumInterface {
    USER_LOGBEHAVEMEMBER_ERROR("LxAPI-001001", "用户不存在"),
    VISITOR_LOGBEHAVEMEMBER_ERROR("LxAPI-001002", "浏览人不存在");


    private final String code;
    private final String message;
}
