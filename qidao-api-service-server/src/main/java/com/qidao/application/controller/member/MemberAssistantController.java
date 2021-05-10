package com.qidao.application.controller.member;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.member.assistant.AssistantBaseInfoDTO;
import com.qidao.application.model.member.assistant.AssistantInfoRes;
import com.qidao.application.model.member.assistant.ListAllAssistantReq;
import com.qidao.application.model.member.assistant.RemoveAssistantReq;
import com.qidao.application.service.member.AssistantService;
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

import java.util.List;

@RequestMapping("/member/assistant")
@RestController
@Api(tags = "会员助手接口")
@Slf4j
public class MemberAssistantController {
    @Autowired
    private AssistantService assistantService;

    @ApiOperation("实验室老师移除助手")
    @PostMapping("/removeAssistant")
    @OperLog(title = "实验室老师移除助手", businessType = BusinessType.DELETE,isAsyncSaveToDB = true)
    public ResponseResult<Object> removeAssistant(@RequestBody @Validated RemoveAssistantReq req){
        log.info("MemberAssistantController -> removeAssistant -> start -> req -> {}",req);
        Boolean result = assistantService.removeAssistant(req.getTeacherId(), req.getAssistantId());
        log.info("MemberAssistantController -> removeAssistant -> end -> result -> {}",result);
        return Result.ok();
    }

    @ApiOperation(value = "查询实验室老师下的所有助手信息",notes = "助手有上限限制，此接口不进行分页")
    @PostMapping("/listAllAssistant")
    public ResponseResult<List<AssistantBaseInfoDTO>> listAllAssistant(@RequestBody @Validated ListAllAssistantReq req){
        log.info("MemberAssistantController -> listAllAssistant -> start -> req -> {}",req);
        List<AssistantBaseInfoDTO> result = assistantService.listAllAssistant(req.getTeacherId());
        log.info("MemberAssistantController -> removeAssistant -> end -> result -> size -> {}", CollUtil.size(result));
        return Result.ok(result);
    }

    @ApiOperation(value = "查询助手下的老师和同老师助手信息",notes = "助手有上限限制，此接口不进行分页")
    @PostMapping("/listTeacherAssistant")
    public ResponseResult<AssistantInfoRes> listTeacherAssistant(@RequestBody @Validated BaseIdQuery req){
        log.info("MemberAssistantController -> listTeacherAssistant -> start -> req -> {}",req);
        AssistantInfoRes result = assistantService.listTeacherAssistant(req.getId());
        log.info("MemberAssistantController -> listTeacherAssistant -> end");
        return Result.ok(result);
    }
}
