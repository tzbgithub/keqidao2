package com.qidao.application.service.member.other.impl;

import com.qidao.application.entity.member.Member;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.sign.SignInReq;
import com.qidao.application.service.member.other.AbstractLoginStrategy;
import com.qidao.application.service.member.other.LoginTypeEnum;
import org.springframework.stereotype.Component;

/**
 * 手机号 + 机器码 登录
 **/
@Component
public class PhoneByMachineCodeLoginStrategy extends AbstractLoginStrategy {
    @Override
    public LoginTypeEnum getLoginType() {
        //2 手机号 + 机器码 登录
        return LoginTypeEnum.PHONE_BY_MACHINE_CODE;
    }

    @Override
    public Member login(SignInReq req) {
        throw new BusinessException(BaseEnum.LOGIN_TYPE_ERROR);
    }
}
