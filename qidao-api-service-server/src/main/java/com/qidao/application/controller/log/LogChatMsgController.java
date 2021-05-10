package com.qidao.application.controller.log;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.log.LogChatMsgReq;
import com.qidao.application.service.log.LogChatMsgService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/log")
@Api(value = "日志记录", tags = "日志记录")
public class LogChatMsgController {
    @Autowired
    private LogChatMsgService logChatMsgService;

    @ApiOperation(value = "环信消息记录", notes = "环信消息记录")
    @OperLog(title = "环信消息记录", businessType = BusinessType.INSERT)
    @PostMapping(value = "/insert")
    public ResponseResult insert(@RequestBody LogChatMsgReq logChatMsgReq) {
        log.info("LogChatMsgController ->  insert ---params:{} ", logChatMsgReq);
        logChatMsgService.insert(logChatMsgReq);
        return Result.ok();
    }

}
