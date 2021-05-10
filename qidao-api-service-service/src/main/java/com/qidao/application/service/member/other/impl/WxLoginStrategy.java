package com.qidao.application.service.member.other.impl;

import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.sign.SignInReq;
import com.qidao.application.service.member.other.AbstractLoginStrategy;
import com.qidao.application.service.member.other.LoginTypeEnum;
import org.springframework.stereotype.Component;

/**
 * 微信 登录
 **/
@Component
public class WxLoginStrategy extends AbstractLoginStrategy {
    @Override
    public LoginTypeEnum getLoginType() {
        //1 微信第三方union id 登录
        return LoginTypeEnum.WX;
    }

    @Override
    public Member login(SignInReq req) {
        throw new BusinessException(BaseEnum.LOGIN_TYPE_ERROR);
    }
}
