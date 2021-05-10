package com.qidao.application.service.member.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.qidao.application.entity.invite.Invite;
import com.qidao.application.entity.invite.InviteExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.relation.LinkPublishContent;
import com.qidao.application.entity.relation.LinkPublishContentExample;
import com.qidao.application.mapper.invite.InviteMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.relation.LinkPublishContentMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dynamic.DynamicConstantEnum;
import com.qidao.application.model.invite.enums.InviteEnum;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.member.assistant.AssistantBaseInfoDTO;
import com.qidao.application.model.member.assistant.AssistantInfoRes;
import com.qidao.application.service.member.AssistantService;
import com.qidao.application.service.redis.MemberRedissonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AssistantServiceImpl implements AssistantService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberRedissonService memberRedissonService;
    @Resource
    private LinkPublishContentMapper linkPublishContentMapper;

    @Resource
    private InviteMapper inviteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeAssistant(@NotNull Long teacherId, @NotNull Long assistantId) {
        log.info("AssistantServiceImpl -> removeAssistant -> start -> teacherId -> {} assistantId -> {}",teacherId,assistantId);
        MemberExample example = new MemberExample();
        example.createCriteria()
                .andTeacherIdEqualTo(teacherId)
                .andIdEqualTo(assistantId)
                .andRoleEqualTo(MemberEnum.ROLE_ASSISTANT.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member assistant = memberMapper.selectOneByExample(example);
        if(null == assistant) {
            log.warn("AssistantServiceImpl -> removeAssistant");
            log.warn("AssistantServiceImpl -> removeAssistant -> null = assistant");
            throw new BusinessException(MemberExceptionEnum.DELETE_TRUE);
        }

        //删除邀请信息
        InviteExample inviteExample = new InviteExample();
        inviteExample.createCriteria().andInvitePhoneEqualTo(assistant.getPhone());
        inviteMapper.updateByExampleSelective(Invite.builder().delFlag(ConstantEnum.DELETED.getByte()).build() , inviteExample);

        assistant.setLevel(MemberEnum.LEVEL_ORDINARY.getValue());
        assistant.setVipStartTime(null);
        assistant.setVipEndTime(null);
        assistant.setTeacherId(null);
        assistant.setBelong(null);
        assistant.setOrganizationId(null);
        assistant.setRole(MemberEnum.ROLE_ORDINARY.getValue());
        memberMapper.updateByPrimaryKey(assistant);

        //删除代发布动态
        LinkPublishContentExample contentExample = new LinkPublishContentExample();
        contentExample.createCriteria()
                .andPublishTypeEqualTo(DynamicConstantEnum.PUBLISH_ASSISTANT.getCode())
                .andTypeEqualTo(DynamicConstantEnum.REPLACE_TYPE_DYNAMIC.getCode())
                .andSalesmanIdEqualTo(assistant.getId());
        linkPublishContentMapper.updateByExampleSelective(LinkPublishContent.builder().delFlag(ConstantEnum.DELETED.getByte()).build() , contentExample);

        // 刷新助手缓存信息
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(assistantId);
        bucket.set(JSONUtil.toJsonStr(assistant), 120L, TimeUnit.MINUTES);
        log.warn("AssistantServiceImpl -> removeAssistant -> update -> assistant -> {}",assistant);

        return true;
    }

    @Override
    public Boolean addAssistant(@NotNull Long teacherId, @NotNull Long assistantId) {
        log.info("AssistantServiceImpl -> addAssistant -> start -> teacherId -> {} assistantId -> {}",teacherId,assistantId);
        MemberExample example = new MemberExample();
        example.createCriteria()
                .andIdEqualTo(assistantId)
                .andRoleEqualTo(MemberEnum.ROLE_ORDINARY.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member assistant = memberMapper.selectOneByExample(example);
        if(null == assistant) {
            log.warn("AssistantServiceImpl -> addAssistant -> null == assistant");
            throw new BusinessException(MemberExceptionEnum.ASSISTANT_UNABLE);
        }

        example.clear();
        example.createCriteria()
                .andIdEqualTo(teacherId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member teacher = memberMapper.selectOneByExample(example);
        if(null == teacher) {
            log.warn("AssistantServiceImpl -> addAssistant -> null == teacher");
            throw new BusinessException(MemberExceptionEnum.DELETE_TRUE);
        }

        example.clear();
        example.createCriteria()
                .andTeacherIdEqualTo(teacherId)
                .andRoleEqualTo(MemberEnum.ROLE_ORDINARY.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        long count = memberMapper.countByExample(example);
        long limit = 10L;
        if(count > limit) {
            log.warn("AssistantServiceImpl -> addAssistant -> count > limit");
            throw new BusinessException(MemberExceptionEnum.ASSISTANT_LIMIT);
        }

        // 设置助手
        assistant.setRole(MemberEnum.ROLE_ASSISTANT.getValue());
        assistant.setLevel(teacher.getLevel());
        assistant.setVipStartTime(teacher.getVipStartTime());
        assistant.setVipEndTime(teacher.getVipEndTime());
        assistant.setOrganizationId(teacher.getOrganizationId());
        assistant.setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());
        memberMapper.updateByPrimaryKeySelective(assistant);
        log.info("AssistantServiceImpl -> addAssistant -> end -> assistant -> {}",assistant);
        return true;
    }

    @Override
    public List<AssistantBaseInfoDTO> listAllAssistant(@NotNull Long teacherId) {
        log.info("AssistantServiceImpl -> listAllAssistant -> start -> teacherId -> {}",teacherId);
        InviteExample inviteExample = new InviteExample();
        inviteExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andStatusEqualTo(InviteEnum.LOGIN.getCode())
                .andMemberIdEqualTo(teacherId)
                .andTypeEqualTo(InviteEnum.TEACHER_INVITE_ASSISTANT.getCode());
        List<Invite> invites = inviteMapper.selectByExample(inviteExample);
        List<String> phones = invites.stream().map(Invite::getInvitePhone).collect(Collectors.toList());
        MemberExample example = new MemberExample();
        example.createCriteria()
                .andTeacherIdEqualTo(teacherId)
                .andPhoneIn(phones)
                .andRoleEqualTo(MemberEnum.ROLE_ASSISTANT.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<Member> assistantList = memberMapper.selectByExample(example);
        log.info("AssistantServiceImpl -> listAllAssistant -> assistantList -> size -> {} ",CollUtil.size(assistantList));

        List<AssistantBaseInfoDTO> result = assistantList.stream()
                .map(this::memberConvertToAssistantBaseInfo)
                .collect(Collectors.toList());
        log.info("AssistantServiceImpl -> listAllAssistant -> result -> size -> {} ",CollUtil.size(result));
        return result;
    }

    @Override
    public AssistantInfoRes listTeacherAssistant(@NotNull Long memberId) {
        log.info("AssistantServiceImpl -> listTeacherAssistant -> start -> memberId -> {}",memberId);
        AssistantInfoRes res = new AssistantInfoRes();
        Member member = memberMapper.selectByPrimaryKey(memberId);
        InviteExample inviteExample = new InviteExample();
        inviteExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andStatusEqualTo(InviteEnum.LOGIN.getCode())
                .andMemberIdEqualTo(member.getTeacherId())
                .andTypeEqualTo(InviteEnum.TEACHER_INVITE_ASSISTANT.getCode());
        List<Invite> invites = inviteMapper.selectByExample(inviteExample);
        List<String> phones = invites.stream().map(Invite::getInvitePhone).collect(Collectors.toList());
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andPhoneIn(phones)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andTeacherIdEqualTo(member.getTeacherId())
                .andRoleEqualTo(MemberEnum.ROLE_ASSISTANT.getValue());
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        List<AssistantBaseInfoDTO> assistantBaseInfoDTOS = memberList.stream().map(this::memberConvertToAssistantBaseInfo).collect(Collectors.toList());
        res.setAssistant(assistantBaseInfoDTOS);
        Member teacher = memberMapper.selectByPrimaryKey(member.getTeacherId());
        res.setTeacher(memberConvertToAssistantBaseInfo(teacher));
        log.info("AssistantServiceImpl -> listTeacherAssistant -> end");
        return res;
    }

    /**
     * 将 Member 对象 转换为 AssistantBaseInfoDTO 对象
     * @param member {@link Member}
     * @return {@link AssistantBaseInfoDTO}
     */
    private AssistantBaseInfoDTO memberConvertToAssistantBaseInfo(Member member){
        AssistantBaseInfoDTO result = new AssistantBaseInfoDTO();
        BeanUtil.copyProperties(member,result);
        return result;
    }
}
