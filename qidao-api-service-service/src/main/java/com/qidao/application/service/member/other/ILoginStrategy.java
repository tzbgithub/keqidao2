package com.qidao.application.service.member.other;

import com.qidao.application.entity.member.Member;
import com.qidao.application.model.member.sign.SignInReq;

/**
 * 登录策略接口
 **/
public interface ILoginStrategy {

    /**
     * 返回登录类型
     * @return {@link LoginTypeEnum}
     **/
    LoginTypeEnum getLoginType();

    /**
     * 会员登录
     * @param req
     * @return {@link Member}
     **/
    Member login(SignInReq req);
}
