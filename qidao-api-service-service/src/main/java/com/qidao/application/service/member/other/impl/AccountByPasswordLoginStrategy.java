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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 帐号 密码 登录
 **/
@Component
@Slf4j
public class AccountByPasswordLoginStrategy extends AbstractLoginStrategy {
    @Override
    public LoginTypeEnum getLoginType() {
        //5 账号(编号) + 密码登录
        return LoginTypeEnum.ACCOUNT_BY_PASSWORD;
    }

    @Override
    public Member login(SignInReq req) {
        throw new BusinessException(BaseEnum.LOGIN_TYPE_ERROR);
        /*log.info("AccountByPasswordLoginStrategy -> login -> begin");
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andNoEqualTo(req.getAccount())
                .andPasswordEqualTo(Md5Util.getCrypt(req.getPassword()));
        Member member = getMemberMapper().selectOneByExample(memberExample);
        checkMember(member);
        log.info("AccountByPasswordLoginStrategy -> login -> end");
        return member;*/
    }
}
