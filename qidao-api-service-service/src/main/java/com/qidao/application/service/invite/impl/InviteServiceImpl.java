package com.qidao.application.service.invite.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.qidao.application.entity.invite.Invite;
import com.qidao.application.entity.invite.InviteExample;
import com.qidao.application.entity.invite.InvitedMember;
import com.qidao.application.entity.label.Label;
import com.qidao.application.entity.label.LabelExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.relation.LinkLabel;
import com.qidao.application.entity.relation.LinkLabelExample;
import com.qidao.application.entity.relation.LinkSelect;
import com.qidao.application.entity.relation.LinkSelectExample;
import com.qidao.application.mapper.invite.CustomInviteMapper;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.mapper.invite.InviteMapper;
import com.qidao.application.mapper.label.CustomLabelMapper;
import com.qidao.application.mapper.label.LabelMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.CustomOrganizationMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.mapper.relation.LinkLabelMapper;
import com.qidao.application.mapper.relation.LinkSelectMapper;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.VerifyCodeUtils;
import com.qidao.application.model.common.VerifyMatch;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.invite.*;
import com.qidao.application.model.invite.enums.InviteEnum;
import com.qidao.application.model.invite.enums.InviteErrorEnum;
import com.qidao.application.model.label.LinkLabelEnum;
import com.qidao.application.model.member.*;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.SmsSendReq;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.SmsSendReq;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;
import com.qidao.application.service.invite.InviteService;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.service.invite.InviteService;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.redis.MemberRedissonService;
import com.qidao.application.vo.SelectConfigResp;
import com.qidao.framework.service.ServicePage;
import com.qidao.application.vo.MemberVo;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.application.vo.MemberVo;
import com.qidao.framework.service.ServicePage;
import com.qidao.application.service.msg.impl.PhoneMsgSendImpl;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Collections;
import java.util.List;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("inviteService")
@Slf4j
public class InviteServiceImpl implements InviteService {

    @Resource
    private MemberRedissonService memberRedissonService;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private CustomMemberMapper customMemberMapper;

    @Resource
    private InviteMapper inviteMapper;

    @Autowired
    private MemberService memberService;

    @Resource
    private CustomInviteMapper customInviteMapper;

    @Resource(name = "memberRedisson")
    private RedissonClient memberRedisson;

    @Resource
    private RedissonClient  redissonClient;

    @Autowired
    @Qualifier("PhoneMsgSendImpl")
    private MsgSendService phoneMsgSendImpl;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Resource
    private CustomLabelMapper customLabelMapper;

    @Resource
    private LinkSelectMapper linkSelectMapper;

    @Resource
    private LinkLabelMapper linkLabelMapper;

    @Resource
    private CustomOrganizationMapper customOrganizationMapper;

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private LabelMapper labelMapper;

    private static final Long shortUrlTime = 60 * 72L;

    @Value("${invite.urlPrefix}")
    private String url;

    @Override
    public String generatorShortUrl(GeneratorShortUrlReq req) {
        log.info("InviteServiceImpl -> generatorShortUrl -> start -> params -> {}", req);
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        log.info("InviteServiceImpl -> generatorShortUrl -> 判断用户是不存在 -> {}", ObjectUtil.isEmpty(member));
        if (ObjectUtil.isEmpty(member)) {
            log.error("InviteServiceImpl -> generatorShortUrl -> error -> {}", MemberExceptionEnum.NOT_FOUND_PERSONAL.getMessage());
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        String key = "teacher";
        switch (req.getType()) {
            //无break为key变更值
            case 0 :
                key="assistant";
                MemberExample memberExample = new MemberExample();
                memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andTeacherIdEqualTo(req.getMemberId()).andRoleEqualTo(MemberEnum.ROLE_ASSISTANT.getValue());
                List<Member> members = memberMapper.selectByExample(memberExample);
                if (members.size()>=10){
                    throw new BusinessException(InviteErrorEnum.ASSISTANT_LIMIT);
                }
            case 1:
                // todo yqj laboratory info prefect
                if (member.getTeacherId() != null) {
                    log.error("InviteServiceImpl -> generatorShortUrl -> error -> {}", InviteErrorEnum.TEACHER.getMessage());
                    throw new BusinessException(InviteErrorEnum.TEACHER);
                }
                log.info("InviteServiceImpl -> generatorShortUrl -> end");
                return baseShortUrl(key, req);
            case 2:

                boolean isPrefect = memberService.findMemberIsPrefect(req.getMemberId());
                if (!isPrefect) {
                    log.error("InviteServiceImpl -> generatorShortUrl -> error -> {}", InviteErrorEnum.ORGANIZATION_IMPERFECT.getMessage());
                    throw new BusinessException(InviteErrorEnum.ORGANIZATION_IMPERFECT);
                }
                MemberExample example = new MemberExample();
                example.createCriteria().andOrganizationIdEqualTo(member.getOrganizationId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
                List<Member> memberList = memberMapper.selectByExample(example);
                if (memberList.size()>=6){
                    throw new BusinessException(InviteErrorEnum.MEMBER_LIMIT);
                }
                key = "member";
                log.info("InviteServiceImpl -> generatorShortUrl -> end");
                return baseShortUrl(key , req);
            case 3:
                key = "assistant::teacher";
                if (!member.getRole().equals(MemberEnum.ROLE_ASSISTANT.getValue())) {
                    log.error("InviteServiceImpl -> generatorShortUrl -> error -> {}",InviteErrorEnum.ASSISTANT.getMessage());
                    throw new BusinessException(InviteErrorEnum.ASSISTANT);
                }
                log.info("InviteServiceImpl -> generatorShortUrl -> end");
                return baseShortUrl(key, req);
            default:
                log.error("InviteServiceImpl -> generatorShortUrl -> error -> {}", InviteErrorEnum.GENERATOR_FAIL.getMessage());
                throw new BusinessException(InviteErrorEnum.GENERATOR_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InviteRegisterRes insertInvite(InviteAddReq req) {
        log.info("InviteServiceImpl -> insertInvite -> start -> params -> {}",req);
        RBucket<String> codeBucket = redissonClient.getBucket("sms::send::code::" + req.getInvitePhone());
        if (!req.getCode().equals(codeBucket.get())){
            throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
        }
        InviteExample inviteExample = new InviteExample();
        inviteExample.createCriteria().andInvitePhoneEqualTo(req.getInvitePhone()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Invite queryInvite = inviteMapper.selectOneByExample(inviteExample);
        log.info("InviteServiceImpl -> insertInvite -> queryInvite != null -> {}", queryInvite != null);
        if (queryInvite != null) {
            log.error("InviteServiceImpl -> insertInvite -> error -> {}", InviteErrorEnum.EXISTS.getMessage());
            throw new BusinessException(InviteErrorEnum.EXISTS);
        }
        InviteRegisterRes res = new InviteRegisterRes();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andPhoneEqualTo(req.getInvitePhone());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (ObjectUtil.isEmpty(member)){
            long memberId = snowflakeIdWorker53.nextId();
            member = new Member();
            member.setPhone(req.getInvitePhone()).
                    setName(StrUtil.isNotEmpty(req.getInviteName()) ? req.getInviteName() :  VerifyCodeUtils.generateInviteCode(10)).
                    setPassword(Md5Util.getCrypt("123456")).setHeadImage("defaultlogo.png").
                    setId(memberId);
            if (req.getType() == 2) {
                baseInviteMember(req.getMemberId(), member);
            }
            memberMapper.insertSelective(member);
        }else {
            if (req.getType().equals(InviteEnum.TEACHER_INVITE_ASSISTANT.getCode())){
                throw new BusinessException(InviteErrorEnum.EXISTS_MEMBER_NOT_ASSISTANT);
            }
            if (req.getType() == 2){
                baseInviteMember(req.getMemberId(), member);
                memberMapper.updateByPrimaryKeySelective(member);
            }
            List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(member.getId());
            List<Label> labelList = customLabelMapper.getByMemberId(member.getId());
            res.setIndustry(memberIndustry.stream().map(this::selectConfigRespToMemberIndustryRes).collect(Collectors.toList()));
            res.setLabel(labelList.stream().map(this::labelToMemberLabelRes).collect(Collectors.toList()));
            res.setEmail(member.getEmail());
        }
        Invite invite = new Invite();
        BeanUtils.copyProperties(req, invite);
        invite.setBindTime(LocalDateTime.now());
        invite.setExpireTime(LocalDateTime.now().plusDays(7L));
        invite.setStatus(InviteEnum.PHONE.getCode());
        invite.setId(snowflakeIdWorker53.nextId());
        inviteMapper.insertSelective(invite);
        codeBucket.delete();
        res.setMemberId(member.getId());
        res.setType(req.getType());
        res.setInviteMemberId(req.getMemberId());
        return res;
    }

    @Override
    public ServicePage<List<InvitedMemberRes>> findInvitedMember(InvitedMemberReq req) {
        log.info("InviteServiceImpl -> findInvitedMember -> start -> params -> {}", req);
        ServicePage<List<InvitedMemberRes>> res = new ServicePage<>();
        Page<InvitedMember> page = PageUtil.start(req, () -> customInviteMapper.findInvitedMember(req.getMemberId()));
        BeanUtils.copyProperties(page, res);
        List<InvitedMember> result = page.getResult();
        List<InvitedMemberRes> memberResList = new ArrayList<>();
        result.forEach(invitedMember -> {
            InvitedMemberRes memberRes = new InvitedMemberRes();
            BeanUtils.copyProperties(invitedMember, memberRes);
            memberResList.add(memberRes);
        });
        res.setResult(memberResList);
        log.info("InviteServiceImpl -> findInvitedMember -> end");
        return res;
    }

    @Override
    public InviteMemberInfo backInviteMemberInfo(String shortUrl) {
        log.info("InviteServiceImpl -> backInviteMemberInfo -> start -> params -> {}",shortUrl);
        RBucket<String> bucket = memberRedisson.getBucket("member::invite::url::"+shortUrl);
        if (!bucket.isExists()){
            throw new BusinessException("邀请链接已失效");
        }
        InviteMemberInfo inviteMemberInfo = JSONObject.parseObject(bucket.get(), InviteMemberInfo.class);

        log.info("InviteServiceImpl -> backInviteMemberInfo -> end");
        return inviteMemberInfo;
    }

    @Override
    public void inviteCode(SmsSendReq req) {
        log.info("InviteServiceImpl -> inviteCode -> start -> params -> {}", req);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andPhoneEqualTo(req.getPhone());
        Member member = memberMapper.selectOneByExample(memberExample);
        log.info("InviteServiceImpl -> inviteCode -> 判断用户是否存在 -> {}" ,ObjectUtil.isNotEmpty(member));
        if (ObjectUtil.isNotEmpty(member)){
            if (member.getOrganizationId() != null){
                log.error("InviteServiceImpl -> inviteCode -> error -> {}",InviteErrorEnum.REGISTERED.getMessage());
                throw new BusinessException(InviteErrorEnum.REGISTERED);
            }
        }
        phoneMsgSendImpl.send(MsgSendDTO.builder()
                .receivers(Collections.singletonList(req.getPhone()))
                .contentType(MsgSendTypeEnum.CODE.getVal())
                .build());
        log.info("InviteServiceImpl -> inviteCode -> end -> 验证码发送成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void perfectInformation(PerfectInformationReq req) {
        //邀请人（老用户）信息
        Member member = memberMapper.selectByPrimaryKey(req.getInviteMemberId());

        //受邀请用户
        Member inviteMember = memberMapper.selectByPrimaryKey(req.getId());

        //删除受邀请用户之前的标签,后面会重新设置
        LinkLabelExample linkLabelExample = new LinkLabelExample();
        linkLabelExample.createCriteria().andTypeEqualTo(LinkLabelEnum.LINK_LABEL_MEMBER.getCode()).andSourceIdEqualTo(req.getId());
        linkLabelMapper.updateByExampleSelective(LinkLabel.builder().delFlag(ConstantEnum.DELETED.getByte()).build(), linkLabelExample);

        //删除受邀请用户之前的行业,后面会重新设置
        LinkSelectExample linkSelectExample = new LinkSelectExample();
        linkSelectExample.createCriteria().andTypeEqualTo(DictConstantEnum.INDUSTRY.getId()).andSourceIdEqualTo(req.getId());
        linkSelectMapper.updateByExampleSelective(LinkSelect.builder().delFlag(ConstantEnum.DELETED.getByte()).build() , linkSelectExample);

        inviteMember.setName(StrUtil.isNotEmpty(req.getName()) ? req.getName() : null ).setEmail(req.getEmail());
        if (req.getType() == 0) {
            inviteMember.setTeacherId(req.getInviteMemberId())
                    .setBelong(member.getBelong())
                    .setRole(MemberEnum.ROLE_ASSISTANT.getValue())
                    .setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue())
                    .setVipStartTime(member.getVipStartTime())
                    .setOrganizationId(member.getOrganizationId())
                    .setLevel(MemberEnum.LEVEL_VIP.getValue())
                    .setVipEndTime(member.getVipEndTime());
            memberMapper.updateByPrimaryKeySelective(inviteMember);
            List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(req.getInviteMemberId());
            List<Label> labels = customLabelMapper.listAchievementLabel(req.getInviteMemberId());
            baseIndustryLabel(labels , memberIndustry , req.getId());
        }else{
            if (CollUtil.isEmpty(req.getIndustryIds()) && CollUtil.isEmpty(req.getLabels())){
                throw new BusinessException(InviteErrorEnum.LABEL_OR_INDUSTRY_NOT_NULL);
            }
            Organization entity = customOrganizationMapper.findByName(req.getOrganizationName(), 0, req.getBelong());
            if (ObjectUtil.isEmpty(entity)){
                long organizationId = snowflakeIdWorker53.nextId();
                Organization organization = Organization.builder()
                        .id(organizationId)
                        .type(OrganizationEnum.TYPE_LABORATORY.getValue())
                        .belong(req.getBelong())
                        .name(req.getOrganizationName())
                        .responsibleMemberId(req.getId())
                        .build();
                organizationMapper.insertSelective(organization);
                inviteMember.setBelong(req.getBelong()).setOrganizationId(organizationId).setVerifyStatus(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
            }else {
                inviteMember.setBelong(entity.getBelong())
                        .setOrganizationId(entity.getId())
                        .setVerifyStatus(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
            }
            memberMapper.updateByPrimaryKeySelective(inviteMember);
            req.getLabels().forEach(label -> {
                if (VerifyMatch.containsAll(label)){
                    throw new BusinessException("科研方向非法字符");
                }
            });
            List<String> labels = req.getLabels().stream().map(StrUtil::trim).filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList());
            List<Label> labelList = new ArrayList<>();
            List<Long> labelIds = new ArrayList<>();
            labels.forEach(label -> {
                LabelExample labelExample = new LabelExample();
                labelExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andValEqualTo(label);
                Label label1 = labelMapper.selectOneByExample(labelExample);
                if (ObjectUtil.isNotEmpty(label1)){
                    labelIds.add(label1.getId());
                }else{
                    long newLabelId = snowflakeIdWorker53.nextId();
                    Label newLabel = Label.builder()
                            .id(newLabelId)
                            .createTime(LocalDateTime.now())
                            .delFlag(ConstantEnum.NOT_DEL.getByte())
                            .val(label)
                            .build();
                    labelList.add(newLabel);
                    labelIds.add(newLabelId);
                }
            });
            labelMapper.batchInsert(labelList);
            List<LinkLabel> linkLabels = new ArrayList<>();
            labelIds.forEach(id -> {
                LinkLabel linkLabel = LinkLabel.builder()
                        .id(snowflakeIdWorker53.nextId())
                        .type(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                        .labelId(id)
                        .createTime(LocalDateTime.now())
                        .sourceId(req.getId())
                        .delFlag(ConstantEnum.NOT_DEL.getByte())
                        .build();
                linkLabels.add(linkLabel);
            });
            linkLabelMapper.batchInsert(linkLabels);
            List<LinkSelect> linkSelects =  new ArrayList<>();
            req.getIndustryIds().forEach(industryId -> {
                LinkSelect linkSelect = LinkSelect.builder()
                        .id(snowflakeIdWorker53.nextId())
                        .delFlag(ConstantEnum.NOT_DEL.getByte())
                        .type(DictConstantEnum.INDUSTRY.getId())
                        .selectConfigId(industryId)
                        .createTime(LocalDateTime.now())
                        .sourceId(req.getId())
                        .build();
                linkSelects.add(linkSelect);
            });
            linkSelectMapper.batchInsert(linkSelects);
        }
    }

    @Override
    public ServicePage<List<InvitedAssistantTeacherRes>> listInvitedAssistantTeacher(InvitedMemberReq req) {
        log.info("InviteServiceImpl -> listInvitedAssistantTeacher -> start -> params -> {}",req);
        ServicePage<List<InvitedAssistantTeacherRes>> res = new ServicePage<>();
        Page<InvitedMember> page = PageUtil.start(req, () -> customInviteMapper.invitedAssistantTeacher(req.getMemberId()));
        BeanUtils.copyProperties(page , res);
        List<InvitedMember> result = page.getResult();
        List<InvitedAssistantTeacherRes> assistantTeacherList = new ArrayList<>();
        result.forEach(invitedMember -> {
            InvitedAssistantTeacherRes assistantTeacherRes = new InvitedAssistantTeacherRes();
            BeanUtils.copyProperties(invitedMember , assistantTeacherRes);
            if (invitedMember.getTeacherId()!= null && invitedMember.getTeacherId().equals(req.getMemberId())){
                assistantTeacherRes.setFlag(true);
            }else{
                assistantTeacherRes.setFlag(false);
            }
            assistantTeacherList.add(assistantTeacherRes);
        });
        res.setResult(assistantTeacherList);
        log.info("InviteServiceImpl -> listInvitedAssistantTeacher -> end");
        return res;
    }

    @Override
    public ServicePage<List<InvitedMemberRes>> listAssistantInvitedTeacher(InvitedMemberReq req) {
        log.info("InviteServiceImpl -> listAssistantInvitedTeacher -> start -> params -> {}",req);
        ServicePage<List<InvitedMemberRes>> res = new ServicePage<>();
        Page<InvitedMember> page = PageUtil.start(req, () -> customInviteMapper.listAssistantInvitedTeacher(req.getMemberId()));
        BeanUtils.copyProperties(page , res);
        List<InvitedMember> result = page.getResult();
        List<InvitedMemberRes> memberResList = new ArrayList<>();
        result.forEach(invitedMember -> {
            InvitedMemberRes memberRes = new InvitedMemberRes();
            BeanUtils.copyProperties(invitedMember , memberRes);
            memberResList.add(memberRes);
        });
        res.setResult(memberResList);
        log.info("InviteServiceImpl -> listAssistantInvitedTeacher -> end");
        return res;
    }

    private String baseShortUrl(String key , GeneratorShortUrlReq req){
        key = "invite::"+key+"::id::"+ req.getMemberId();
        String res = Md5Util.getCrypt(key);
        MemberVo memberVo = customMemberMapper.findMemberById(req.getMemberId());
        RBucket<String> bucket = memberRedisson.getBucket("member::invite::url::"+res);
        InviteMemberInfo memberInfo = new InviteMemberInfo();
        BeanUtils.copyProperties(memberVo , memberInfo);
        memberInfo.setType(req.getType());
        memberInfo.setShortUrl(res);
        memberInfo.setGeneratorTime(LocalDateTime.now());
        bucket.set(JSON.toJSONString(memberInfo) , shortUrlTime , TimeUnit.MINUTES);
        return url+res;
    }

    public void baseIndustryLabel(List<Label> labels , List<SelectConfigResp> memberIndustry , Long memberId){
        if (CollUtil.isNotEmpty(memberIndustry)) {
            LinkSelectExample linkSelectExample = new LinkSelectExample();
            linkSelectExample.createCriteria().andSourceIdEqualTo(memberId).andTypeEqualTo(DictConstantEnum.INDUSTRY.getId());
            linkSelectMapper.updateByExampleSelective(LinkSelect.builder().delFlag(ConstantEnum.DELETED.getByte()).build() , linkSelectExample);
            List<LinkSelect> linkSelects = new ArrayList<>();
            memberIndustry.forEach(linkSelect -> {
                LinkSelect select = LinkSelect.builder()
                        .id(snowflakeIdWorker53.nextId())
                        .type(DictConstantEnum.INDUSTRY.getId())
                        .sourceId(memberId)
                        .createTime(LocalDateTime.now())
                        .delFlag(ConstantEnum.NOT_DEL.getByte())
                        .selectConfigId(linkSelect.getId())
                        .build();
                linkSelects.add(select);
            });
            linkSelectMapper.batchInsert(linkSelects);
        }
        if (CollUtil.isNotEmpty(labels)) {
            LinkLabelExample linkLabelExample = new LinkLabelExample();
            linkLabelExample.createCriteria().andSourceIdEqualTo(memberId).andTypeEqualTo(LinkLabelEnum.LINK_LABEL_MEMBER.getCode());
            linkLabelMapper.updateByExampleSelective(LinkLabel.builder().delFlag(ConstantEnum.DELETED.getByte()).build(), linkLabelExample);

            List<LinkLabel> linkLabels = new ArrayList<>();
            labels.forEach(label -> {
                LinkLabel linkLabel = LinkLabel.builder()
                        .id(snowflakeIdWorker53.nextId())
                        .labelId(label.getId())
                        .createTime(LocalDateTime.now())
                        .type(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                        .sourceId(memberId)
                        .delFlag(ConstantEnum.NOT_DEL.getByte())
                        .build();
                linkLabels.add(linkLabel);
            });
            linkLabelMapper.batchInsert(linkLabels);
        }
    }

    /**
     * 企业邀请员工公共方法（邀请人的行业、标签赋予被邀请人）
     * @param memberId 邀请人id
     * @param member 被邀请人信息
     */
    public void baseInviteMember(Long memberId , Member member){
        Member inviteMember = memberMapper.selectByPrimaryKey(memberId);
        List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(memberId);
        List<Label> labels = customLabelMapper.listAchievementLabel(memberId);
        member.setBelong(inviteMember.getBelong())
                .setOrganizationId(inviteMember.getOrganizationId())
                .setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());
        baseIndustryLabel(labels , memberIndustry , member.getId());
    }

    /**
     * {@link SelectConfigResp} 对象 转 {@link MemberIndustryRes} 对象
     * @param industry {@link SelectConfigResp}
     * @return {@link MemberIndustryRes}
     */
    private MemberIndustryRes selectConfigRespToMemberIndustryRes(SelectConfigResp industry){
        MemberIndustryRes res = new MemberIndustryRes();
        BeanUtils.copyProperties(industry , res);
        return res;
    }

    private MemberLabelRes labelToMemberLabelRes(Label label){
        MemberLabelRes res = new MemberLabelRes();
        BeanUtils.copyProperties(label , res);
        return res;
    }
}
