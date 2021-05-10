package com.qidao.application.controller.member;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.member.token.GeneratorAccessTokenReq;
import com.qidao.application.service.member.MemberTokenService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;


/**
 * @author Autuan.Yu
 */
@Api(tags = "接口调用 token")
@RestController
@RequestMapping("/member/token")
public class MemberTokenController {
    private final MemberTokenService memberTokenService;

    @Autowired
    public MemberTokenController(MemberTokenService memberTokenService) {
        this.memberTokenService = memberTokenService;
    }

    @ApiOperation("获取调用接口的 access token")
    @OperLog(title = "获取AccessToken",businessType = BusinessType.OTHER)
    @PostMapping("/getToken")
    public ResponseResult<String> getToken(@RequestBody @Validated GeneratorAccessTokenReq req) {
        String accessToken = memberTokenService.generatorAccessToken(req);
        return Result.ok(accessToken);
    }

}
