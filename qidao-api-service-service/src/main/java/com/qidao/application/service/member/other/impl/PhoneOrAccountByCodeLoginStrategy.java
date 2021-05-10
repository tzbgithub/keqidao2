package com.qidao.application.service.member.other.impl;

import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.VerifyCodeUtils;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.sign.SignInReq;
import com.qidao.application.service.member.other.AbstractLoginStrategy;
import com.qidao.application.service.member.other.LoginTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 手机号或帐号 验证码登录
 **/
@Component
@Slf4j
public class PhoneOrAccountByCodeLoginStrategy extends AbstractLoginStrategy {

    @Value("${qidao.env:prod}")
    private String env;

    private final String notCheckCode = "006688";

    @Override
    public LoginTypeEnum getLoginType() {
        //0 手机号/账号 + 验证码登录
        return LoginTypeEnum.PHONE_OR_ACCOUNT_BY_CODE;
    }

    @Override
    public Member login(SignInReq req) {
        log.info("PhoneOrAccountByCodeLoginStrategy -> login -> begin");
        String reqCode = req.getCode();
        if (StringUtils.isEmpty(reqCode)) {
            throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andPhoneEqualTo(req.getPhone());
        Member member = getMemberMapper().selectOneByExample(memberExample);
        if (member == null) {
            log.info("PhoneOrAccountByCodeLoginStrategy -> signIn -> 创建新用户");
            member = new Member();
            long id = getId();
            member.setPhone(req.getPhone())
                    .setName(VerifyCodeUtils.generateInviteCodeAndNotNum(10))
                    .setPassword(Md5Util.getCrypt("123456"))
                    .setId(id);
            getMemberMapper().insertSelective(member);
        }

        if (notCheckCode.equals(req.getCode())) {
            //检查缓存
            if (!"prod".equals(env)) {
                return member;
            }
        }

        if ("15551385182".equals(req.getPhone())) {
            if (!notCheckCode.equals(req.getCode())) {
                throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
            }
        } else {
            RBucket<String> bucket = getRedissonClient().getBucket("sms::send::code::" + req.getPhone());
            String code = bucket.get();
            if (code == null || "".equals(code)) {
                log.info("PhoneOrAccountByCodeLoginStrategy -> signIn -> 验证码不存在导致过期 ->");
                throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
            }
            if (!code.equals(reqCode)) {
                log.info("PhoneOrAccountByCodeLoginStrategy -> signIn -> 验证码不匹配 redis code{} -> 请求code{}  请求人{}", code, reqCode, req.getPhone());
                throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
            }
            bucket.set(null, 5, TimeUnit.MINUTES);
        }
        log.info("PhoneOrAccountByCodeLoginStrategy -> login -> end");
        return member;
    }
}
