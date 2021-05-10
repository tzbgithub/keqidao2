package com.qidao.application.service.member.other.impl;

import com.qidao.application.entity.member.Member;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.sign.SignInReq;
import com.qidao.application.service.member.other.AbstractLoginStrategy;
import com.qidao.application.service.member.other.LoginTypeEnum;
import org.springframework.stereotype.Component;

/**
 * token登录
 **/
@Component
public class TokenLoginStrategy extends AbstractLoginStrategy {
    @Override
    public LoginTypeEnum getLoginType() {
        //4 token登录
        return LoginTypeEnum.TOKEN;
    }

    @Override
    public Member login(SignInReq req) {
        throw new BusinessException(BaseEnum.LOGIN_TYPE_ERROR);
    }
}
