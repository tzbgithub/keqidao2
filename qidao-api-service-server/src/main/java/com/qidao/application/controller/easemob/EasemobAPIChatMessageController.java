package com.qidao.application.controller.easemob;

import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.easemob.EasemobGetChatGroupsReq;
import com.qidao.application.model.easemob.EasemobGetChatGroupsRes;
import com.qidao.application.model.easemob.EasemobSendTextMessageReq;
import com.qidao.application.model.easemob.EasemobSendTextMessageRes;
import com.qidao.application.service.im.EasemobMsgService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/23 5:18 PM
 */
@Api(tags = "环信IM-发送消息")
@Slf4j
@RestController
@RequestMapping("/easemob/api/message")
public class EasemobAPIChatMessageController {

    @Autowired
    private EasemobMsgService easemobMsgService;

    @PostMapping("/sendTextMessage")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "发送文本/透传消息", hidden = true)
    public ResponseResult<EasemobSendTextMessageRes> sendTextMessage(@RequestBody @Validated EasemobSendTextMessageReq req) {
        log.info("EasemobAPIChatMessageController -> sendTextMessage -> start -> req -> {}", req);
        EasemobSendTextMessageRes result = easemobMsgService.sendTextMessage(req);
        log.info("EasemobAPIChatMessageController -> sendTextMessage -> end -> result -> {}", result);
        return Result.ok(result);
    }
}
