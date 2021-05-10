package com.qidao.application.service.member;

import com.qidao.application.model.member.sign.*;
import com.qidao.framework.web.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface SignService {
    /**
     * 登录接口
     * todo 0 手机号/账号 + 验证码登录
     * todo 1 微信第三方union id 登录  （？）[21.1.15]
     * todo 2 手机号 + 机器码 登录 （？）[21.1.15]
     * todo 3 手机号 + 密码登录
     * todo 4 token登录
     * todo 5 账号 + 密码登录
     * @param req
     * @return
     */
    SignInRes signIn(SignInReq req);

    AutoSignRes shortCutSignIn(AutoSignReq req);

    AutoSignRes shortChuanglanSignIn(AutoChuangLanReq req, HttpServletRequest request);

    SignUpRes signUp(SignUpReq req);


}
