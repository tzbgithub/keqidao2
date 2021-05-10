package com.qidao.application.controller.invite;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.invite.GeneratorShortUrlReq;
import com.qidao.application.model.invite.InviteAddReq;
import com.qidao.application.model.invite.*;
import com.qidao.application.model.member.InvitedMemberReq;
import com.qidao.application.model.member.InvitedMemberRes;
import com.qidao.application.model.invite.InviteMemberInfo;
import com.qidao.application.model.invite.InviteMemberInfoReq;
import com.qidao.application.model.msg.SmsSendReq;
import com.qidao.application.model.msg.SmsSendReq;
import com.qidao.application.service.invite.InviteService;
import com.qidao.framework.service.ServicePage;
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

@RestController
@Slf4j
@RequestMapping("/invite/")
@Api(tags = "邀请相关")
public class InviteController {

    @Autowired
    private InviteService inviteService;

    @ApiOperation(value = "生成邀请短链接 --yqj")
    @PostMapping("/generatorShortUrl")
    public ResponseResult<String> generatorShortUrl(@RequestBody @Validated GeneratorShortUrlReq req) {
        return Result.ok(inviteService.generatorShortUrl(req));
    }

    @OperLog(title = "新增邀请", businessType = BusinessType.INSERT)
    @ApiOperation(value = "新增邀请")
    @PostMapping("/insertInvite")
    public ResponseResult<InviteRegisterRes> insertInvite(@RequestBody @Validated InviteAddReq req) {
        log.info("InviteController -> insertInvite -> start -> params -> {}", req);
        InviteRegisterRes id = inviteService.insertInvite(req);
        log.info("InviteController -> insertInvite -> end");
        return Result.ok(id);
    }

    @ApiOperation("新增邀请获取验证码")
    @PostMapping("/verificationCode")
    public ResponseResult<Object> verificationCode(@RequestBody @Validated SmsSendReq req) {
        log.info("InviteController -> verificationCode -> start -> params -> {}", req);
        inviteService.inviteCode(req);
        log.info("InviteController -> verificationCode -> end");
        return Result.ok();
    }

    @ApiOperation(value = "获取受邀请的用户")
    @PostMapping("/findInvitedMember")
    public ResponseResult<ServicePage<List<InvitedMemberRes>>> findInvitedMember(@RequestBody @Validated InvitedMemberReq req) {
        log.info("InviteController -> findInvitedMember -> start -> params -> {}", req);
        ServicePage<List<InvitedMemberRes>> invitedMember = inviteService.findInvitedMember(req);
        log.info("InviteController -> findInvitedMember -> end");
        return Result.ok(invitedMember);
    }

    @ApiOperation(value = "根据短链接返回用户信息", notes = "author -- yqj")
    @PostMapping("/backInviteMemberInfo")
    public ResponseResult<InviteMemberInfo> backInviteMemberInfo(@RequestBody @Validated InviteMemberInfoReq req) {
        log.info("InviteController -> backInviteMemberInfo -> start -> params -> {}", req);
        InviteMemberInfo inviteMemberInfo = inviteService.backInviteMemberInfo(req.getShortUrl());
        log.info("InviteController -> backInviteMemberInfo -> end");
        return Result.ok(inviteMemberInfo);
    }


    @ApiOperation(value = "完善被邀请人信息", notes = "author -- yqj")
    @PostMapping("/perfectInformation")
    public ResponseResult<Object> perfectInformation(@RequestBody @Validated PerfectInformationReq req) {
        log.info("InviteController -> perfectInformation -> start -> params -> {}", req);
        inviteService.perfectInformation(req);
        log.info("InviteController -> perfectInformation -> end");
        return Result.ok();
    }

    @ApiOperation(value = "获取实验室老师邀请的用户")
    @PostMapping("/listInvitedAssistantTeacher")
    public ResponseResult<ServicePage<List<InvitedAssistantTeacherRes>>> listInvitedAssistantTeacher(@RequestBody @Validated InvitedMemberReq req) {
        log.info("InviteController -> listInvitedAssistantTeacher -> start -> params -> {}", req);
        ServicePage<List<InvitedAssistantTeacherRes>> invitedMember = inviteService.listInvitedAssistantTeacher(req);
        log.info("InviteController -> listInvitedAssistantTeacher -> end");
        return Result.ok(invitedMember);
    }

    @ApiOperation(value = "助手获取我邀请的老师列表")
    @PostMapping("/listAssistantInvitedTeacher")
    public ResponseResult<ServicePage<List<InvitedMemberRes>>> listAssistantInvitedTeacher(@RequestBody @Validated InvitedMemberReq req) {
        log.info("InviteController -> listAssistantInvitedTeacher -> start -> params -> {}", req);
        ServicePage<List<InvitedMemberRes>> invitedMember = inviteService.listAssistantInvitedTeacher(req);
        log.info("InviteController -> listAssistantInvitedTeacher -> end");
        return Result.ok(invitedMember);
    }

}
