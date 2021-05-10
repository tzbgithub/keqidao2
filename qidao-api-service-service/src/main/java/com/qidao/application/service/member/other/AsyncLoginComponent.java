package com.qidao.application.service.member.other;

import com.qidao.application.entity.log.LogMemberLogin;
import com.qidao.application.mapper.log.LogMemberLoginMapper;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;
import com.qidao.application.service.member.MemberTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 异步登录
 **/
@Component("asyncLoginComponent")
@Slf4j
public class AsyncLoginComponent {

    @Autowired
    private MemberTokenService memberTokenService;
    @Resource
    private LogMemberLoginMapper logMemberLoginMapper;

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void asyncSignIn(GeneratorRefreshTokenDTO req, LogMemberLogin logMemberLogin) {
        log.info("AsyncLoginComponent -> asyncSignIn -> SignIn异步处理 start");
        memberTokenService.generatorRefreshToken(req);
        logMemberLoginMapper.insertSelective(logMemberLogin);
        log.info("AsyncLoginComponent -> asyncSignIn -> SignIn异步处理 end");
    }

}
