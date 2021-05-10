package com.qidao.application.controller.msg;

import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.SmsSendReq;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.application.service.msg.MsgService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/sms")
@Slf4j
@Api(value = "创蓝短信", tags = "创蓝短信")
public class ChangLanSmsSendController {
    @Autowired
    private MsgService msgService;

    @ApiOperation(value = "登录发送短信", notes = "创蓝短信发送验证码  一分钟只能发送一次,手机号格式校验")
    @PostMapping("/login/send")
    @QiDaoPermission
    public ResponseResult loginSend(@RequestBody @Validated SmsSendReq smsSendReq) {
        msgService.smsSend(smsSendReq);
        return Result.ok(true);
    }
}
