package com.qidao.application.controller.log;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.service.log.LogBehaveMemberService;
import com.qidao.application.vo.BehaveMemeberReq;
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

@RestController
@Slf4j
@RequestMapping("/behave")
@Api(value = "App足迹", tags = "App足迹")
public class LogBehaveMemberController {
    @Autowired
    private  LogBehaveMemberService logBehaveMemberService;

    @OperLog(title = "浏览记录",businessType = BusinessType.OTHER,isAsyncSaveToDB = true)
    @PostMapping(value = "/saveBehaveMemeber")
    @ApiOperation(value = "浏览记录",notes = "用户浏览过的痕迹记录")
    public ResponseResult saveBehaveMemeber(@RequestBody @Validated BehaveMemeberReq behaveMemeberReq) {
        log.info("LogBehaveMemberController-> ---- 浏览记录:{}",behaveMemeberReq);
        logBehaveMemberService.saveBehaveMemeber(behaveMemeberReq);
        return Result.ok();
    }
    @OperLog(title = "清空浏览记录",businessType = BusinessType.OTHER,isAsyncSaveToDB = true)
    @PostMapping(value = "/deleteBehaveMember")
    @ApiOperation(value = "清空浏览记录", notes = "清空掉本人的浏览记录")
    public ResponseResult deleteBehaveMember(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("LogBehaveMemberController-> ---- deleteBehaveMember 清空浏览痕迹");
        boolean flag = logBehaveMemberService.deleteBehaveMember(baseIdQuery);
        if(!flag){
            return Result.fail("清空异常");
        }
        return Result.ok();
    }
}
