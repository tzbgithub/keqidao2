package com.qidao.application.controller.member;

import cn.hutool.extra.servlet.ServletUtil;
import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.member.sign.*;
import com.qidao.application.service.member.SignService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Autuan.Yu
 */
@Api(tags = "登录退出注册")
@RestController
@RequestMapping("/member/sign")
public class MemberSignController {
    @Autowired
    private SignService signService;

    @ApiOperation(value = "会员登录",notes = "0 手机号加验证码 1 微信第三方union id 登录 2 手机号 + 机器码 登录 3 手机号 + 密码登录 4 token登录 5 账号 + 密码登录 目前只有0 3 ，5 实现")
    @PostMapping("/signIn")
    ResponseResult<SignInRes> signIn(@RequestBody @Validated SignInReq req, HttpServletRequest request){
        // IP 获取
        String clientIp = ServletUtil.getClientIP(request);
        req.setIp(clientIp);
        SignInRes signInRes = signService.signIn(req);
        return Result.ok(signInRes);
    }

    @ApiOperation(value = "注册",notes = "<strong style='color:red'>未实现，请忽略此接口</strong>")
    @PostMapping("/signUp")
    @OperLog(title = "注册", businessType = BusinessType.INSERT,isAsyncSaveToDB = true)
    ResponseResult<SignUpRes> signUp(@RequestBody @Validated SignUpReq req){
        SignUpRes signUpRes = signService.signUp(req);
        return Result.ok(signUpRes);
    }

    @ApiOperation(value = "一键登录",notes = "<strong style='color:red'>未实现，请忽略此接口</strong><br>如果没有会员信息，会创建账号再登录")
    @PostMapping("/autoSign")
    ResponseResult<AutoSignRes> autoSign(@RequestBody @Validated AutoSignReq req){
        AutoSignRes autoSignRes = signService.shortCutSignIn(req);
        return Result.ok(autoSignRes);
    }

    @ApiOperation(value = "退出登录",notes = "<strong style='color:red'>未实现，请忽略此接口</strong>")
    @PostMapping("/signOut")
    ResponseResult<Object> signOut(SignOutReq req){
        return null;
    }
    @ApiOperation(value = "创蓝登录",notes = "<strong style='color:red'>未实现，请忽略此接口</strong><br>如果没有会员信息，会创建账号再登录")
    @PostMapping("/shortChuanglanSignIn")
    public ResponseResult<AutoSignRes> shortChuanglanSignIn(@RequestBody AutoChuangLanReq req,HttpServletRequest request) {
        AutoSignRes autoSignRes = signService.shortChuanglanSignIn(req,request);
        return  Result.ok(autoSignRes);
    }
}
