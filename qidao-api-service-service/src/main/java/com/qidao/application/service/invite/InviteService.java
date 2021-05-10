package com.qidao.application.service.invite;

import com.qidao.application.model.invite.GeneratorShortUrlReq;
import com.qidao.application.model.invite.InviteAddReq;
import com.qidao.application.model.invite.*;
import com.qidao.application.model.member.InvitedMemberReq;
import com.qidao.application.model.member.InvitedMemberRes;
import com.qidao.framework.service.ServicePage;

import java.util.List;
import com.qidao.application.model.invite.InviteMemberInfo;

import com.qidao.application.model.msg.SmsSendReq;
import com.qidao.application.model.sms.SmsSendRequest;

public interface InviteService {

    /**
     * 生成邀请短链接
     * @param req {@link GeneratorShortUrlReq 请求对象}
     * @return 短链接
     */
    String generatorShortUrl(GeneratorShortUrlReq req);

    /**
     * 新增邀请
     * @param req {@link InviteAddReq 请求对象}
     */
    InviteRegisterRes insertInvite(InviteAddReq req);

    /**
     * 受邀请用户列表
     * @param req {@link InvitedMemberReq 受邀请用户请求参数}
     * @return {@link InvitedMemberRes 分页集合}
     */
    ServicePage<List<InvitedMemberRes>> findInvitedMember(InvitedMemberReq req);

    /**
     * 根据邀请短链接返回邀请人信息
     * @param shortUrl 邀请短链接
     * @return {@link InviteMemberInfo 邀请人基本信息}
     */
    InviteMemberInfo backInviteMemberInfo(String shortUrl);

    /**
     * 获取短信验证码
     */
    void inviteCode(SmsSendReq req);

    /**
     * 老师、助手完善信息
     * @param req
     */
    void perfectInformation(PerfectInformationReq req);

    /**
     * 实验室老师获取我邀请的用户
     * @param req 请求对象 {@link InvitedMemberReq}
     * @return 分页集合 {@link InvitedAssistantTeacherRes}
     */
    ServicePage<List<InvitedAssistantTeacherRes>> listInvitedAssistantTeacher(InvitedMemberReq req);

    /**
     * 助手获取我邀请的老师列表
     * @param req 请求对象 {@link InvitedMemberReq}
     * @return 分页集合 {@link InvitedMemberRes}
     */
    ServicePage<List<InvitedMemberRes>> listAssistantInvitedTeacher(InvitedMemberReq req);


}
