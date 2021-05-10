package com.qidao.application.service.member.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.label.Label;
import com.qidao.application.entity.label.LabelExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberDetailed;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.member.MemberInfo;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.organization.OrganizationExample;
import com.qidao.application.entity.relation.LinkLabel;
import com.qidao.application.entity.relation.LinkLabelExample;
import com.qidao.application.entity.server.ServerExample;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.label.CustomLabelMapper;
import com.qidao.application.mapper.label.LabelMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.mapper.relation.CustomLinkSelectMapper;
import com.qidao.application.mapper.relation.LinkLabelMapper;
import com.qidao.application.mapper.relation.LinkSelectMapper;
import com.qidao.application.mapper.server.ServerMapper;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.QiDaoEncodeUtil;
import com.qidao.application.model.common.VerifyCodeUtils;
import com.qidao.application.model.common.VerifyMatch;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.SelectConfigEnum;
import com.qidao.application.model.config.SelectConfigExceptionEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.dto.UpdateOriganizationDto;
import com.qidao.application.model.label.LinkLabelEnum;
import com.qidao.application.model.log.LogBehaveMemberErrorEnum;
import com.qidao.application.model.member.*;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;
import com.qidao.application.model.member.token.TokenConstantEnum;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.model.organization.enums.OrganizaitonErrorEnum;
import com.qidao.application.model.server.ServerEnum;
import com.qidao.application.service.event.PublishEventComponent;
import com.qidao.application.service.event.member.MemberUpdateEvent;
import com.qidao.application.service.member.AssistantService;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.organization.other.IndustrySyncComponent;
import com.qidao.application.service.redis.MemberRedissonService;
import com.qidao.application.vo.*;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private CustomMemberMapper customMemberMapper;
    @Autowired
    private LinkLabelMapper linkLabelMapper;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private SelectConfigMapper selectConfigMapper;
    @Autowired
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Autowired
    private CustomLabelMapper customLabelMapper;
    @Autowired
    private CustomLinkSelectMapper customLinkSelectMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private AssistantService assistantService;
    @Resource
    private LinkSelectMapper linkSelectMapper;
    @Resource
    private QiDaoEncodeUtil qiDaoEncodeUtil;
    @Resource
    private MemberRedissonService memberRedissonService;
    @Autowired
    private PublishEventComponent publishEventComponent;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private IndustrySyncComponent industrySyncComponent;

    /**
     * 根据union查询实验室信息
     */
    @Override
    public Member getOrganizationByUnionId(String unionId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUnionIdEqualTo(unionId).
                andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).
                andOrganizationIdIsNotNull();
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            return null;
        }
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andIdEqualTo(member.getOrganizationId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            return null;
        }
        return member;
    }

    /**
     * 查询用户个人名片
     *
     * @param memberId
     */
    @Override
    public MemberVo findMemberMessage(Long memberId) {
        MemberVo members = customMemberMapper.findMemberById(memberId);

        if (members == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }

        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(members, memberVo);


        Long loginMemberId = getLoginMemberId();
        List<CompletableFuture> futureList = new ArrayList<>();
        CompletableFuture<Void> dynamicCountFuture = CompletableFuture.runAsync(() -> {
            // 统计该用户的各类型动态数量 需要有 类型ID，类型值，合计值 todo 会员发布动态过多时 并发 执行效率低
            List<MemberDynamicCountVo> dynamicCounts = customMemberMapper.dynamicCountByMemberId(memberId, !memberId.equals(loginMemberId));
            // 助手角色查询的各类型动态数量 为老师的数据
            if (members.getRole().equals(MemberEnum.ROLE_ASSISTANT.getValue()) && members.getTeacherId() != null){
                Long teacherId = members.getTeacherId();
                dynamicCounts = customMemberMapper.dynamicCountByMemberId(teacherId, !memberId.equals(loginMemberId));
            }
            memberVo.setMemberDynamicCountVoList(dynamicCounts);
        }, threadPoolExecutor);
        futureList.add(dynamicCountFuture);

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            Integer positionType = DictConstantEnum.POSITION.getId();
            Long scaleId = null;
            ArrayList<Long> ids = new ArrayList<>();

            if (members.getOrganizationId() != null) {
                log.info("MemberServiceImpl -> findMemberMessage --> 用户拥有组织 -> 查询公司地址 ");
                Organization organization = organizationMapper.selectByPrimaryKey(members.getOrganizationId());
                if (organization != null && organization.getDelFlag().equals(ConstantEnum.NOT_DEL.getByte())) {
                    memberVo.setCityName(organization.getAddressCityName());
                    memberVo.setAreaName(organization.getAddressAreaName());
                    if (organization.getType().equals(OrganizationEnum.TYPE_LABORATORY.getValue())) {
                        positionType = DictConstantEnum.POSITION_TITLE.getId();
                    }
                    scaleId = organization.getScaleId();
                    ids.add(scaleId);
                }
            }
            memberVo.setOrganizationName(members.getOrganizationName());

            ids.add(memberVo.getEducationId());
            ids.add(memberVo.getPositionId());
            CollUtil.removeNull(ids);

            if (CollUtil.isNotEmpty(ids)) {
                SelectConfigExample selectConfigExample = new SelectConfigExample();
                selectConfigExample.createCriteria()
                        .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                        .andIdIn(ids);
                List<SelectConfig> selectConfigs = selectConfigMapper.selectByExample(selectConfigExample);

                if (CollUtil.isNotEmpty(selectConfigs)) {
                    for (SelectConfig x : selectConfigs) {
                        if (DictConstantEnum.DEGREE.getId().equals(x.getType())) {
                            memberVo.setEducationName(x.getVal());
                            continue;
                        }
                        if (x.getType().equals(positionType)) {
                            memberVo.setPositionName(x.getVal());
                            continue;
                        }
                        if (x.getId().equals(scaleId)) {
                            memberVo.setScaleId(scaleId);
                            memberVo.setScaleName(x.getVal());
                        }
                    }
                }
            }
        }, threadPoolExecutor);
        futureList.add(future);


        CompletableFuture<Void> selectLinkLabelFuture = CompletableFuture.runAsync(() -> {
            String result = customLabelMapper.selectLinkLabel(memberId, LinkLabelEnum.LINK_LABEL_MEMBER.getCode());
            if (result != null) {
                List<String> string = Arrays.asList(result.split(","));
                string = string.stream().distinct().collect(Collectors.toList());
                memberVo.setLabelName(string);
            }
        }, threadPoolExecutor);
        futureList.add(selectLinkLabelFuture);

        CompletableFuture<Void> serverSizeFuture = CompletableFuture.runAsync(() -> {
            ServerExample serverExample = new ServerExample();
            serverExample.createCriteria().andDelFlagEqualTo((byte) ServerEnum.DELETE_FLAG_NO.getValue()).
                    andMemberIdPartyBEqualTo(memberId).
                    andStatusEqualTo(ServerEnum.STATUS_ACCEPTED.getValue());
            long serverCount = serverMapper.countByExample(serverExample);
            memberVo.setServerSize((int) serverCount);
        }, threadPoolExecutor);
        futureList.add(serverSizeFuture);

        try {
            CompletableFuture.allOf(
                    futureList.toArray(new CompletableFuture[futureList.size()])
            ).get(5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("MemberServiceImpl -> findMemberMessage -> 查询用户个人名片异常 -> error:{}", e);
            throw new BusinessException("查询用户个人名片失败,请稍候重试");
        }

//        List<AssistantBaseInfoDTO> assistantList = assistantService.listAllAssistant(memberId);
        return memberVo;

    }

    /**
     * 获取登录用户id
     *
     * @return
     */
    private Long getLoginMemberId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String accessToken = request.getHeader(TokenConstantEnum.ACCESS_TOKEN_NAME.getValue());
        if (!StrUtil.isBlank(accessToken)) {
            RBucket<String> accessTokenBucket = redissonClient.getBucket(accessToken);
            if (accessTokenBucket.isExists()) {
                String refreshToken = accessTokenBucket.get();
                String jsonStr = qiDaoEncodeUtil.desDecrypt(refreshToken);
                GeneratorRefreshTokenDTO generatorRefresh = JSONUtil.toBean(jsonStr, GeneratorRefreshTokenDTO.class);
                log.info("MemberServiceImpl -> getLoginMemberId --> 登录用户id:{} ", generatorRefresh.getMemberId());
                return generatorRefresh.getMemberId();
            }
        }
        return null;
    }


    /**
     * 查询用户个人信息
     *
     * @param memberId
     */
    @Override
    public MemberDetailVo findMemberMessageDetail(Long memberId) {
        byte flag = ConstantEnum.NOT_DEL.getByte();
        log.info("MemberServiceImpl -> findMemberMessageDetail -> start -> memberId:{} ", memberId);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(flag).andIdEqualTo(memberId);
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        MemberDetailVo memberDetailVo = new MemberDetailVo();
        BeanUtils.copyProperties(member, memberDetailVo);

        List<CompletableFuture> futureList = new ArrayList<>(5);

        CompletableFuture<Void> futureOrganization = CompletableFuture.runAsync(() -> {
            Organization organization = organizationMapper.selectByPrimaryKey(member.getOrganizationId());
            if (organization != null && organization.getDelFlag().equals(flag)) {
                memberDetailVo.setOrganizationName(organization.getName());
                memberDetailVo.setOrganizationType(organization.getType());
            }
        }, threadPoolExecutor);
        futureList.add(futureOrganization);

        CompletableFuture<Void> futureLinkLabel = CompletableFuture.runAsync(() -> {
            LinkLabelExample linkLabelExample = new LinkLabelExample();
            linkLabelExample.createCriteria()
                    .andSourceIdEqualTo(member.getId())
                    .andDelFlagEqualTo(flag);
            List<LinkLabel> linkLabels = linkLabelMapper.selectByExample(linkLabelExample);
            if (linkLabels.size() > ConstantEnum.NOT_DEL.getInt()) {
                String result = customLabelMapper.selectLinkLabel(memberId, LinkLabelEnum.LINK_LABEL_MEMBER.getCode());
                if (result != null) {
                    List<String> string = Arrays.asList(result.split(","));
                    string = string.stream().distinct().collect(Collectors.toList());
                    memberDetailVo.setLabel(string);
                }
            }
        }, threadPoolExecutor);
        futureList.add(futureLinkLabel);

        if (member.getEducationId() != null) {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                SelectConfig selectConfig = selectConfigMapper.selectByPrimaryKey(member.getEducationId());
                if (selectConfig != null) {
                    memberDetailVo.setEducationName(selectConfig.getVal());
                }
            }, threadPoolExecutor);
            futureList.add(voidCompletableFuture);
        }

        if (member.getPositionId() != null) {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                SelectConfig selectConfig = selectConfigMapper.selectByPrimaryKey(member.getPositionId());
                if (selectConfig != null) {
                    memberDetailVo.setPositionName(selectConfig.getVal());
                }
            }, threadPoolExecutor);
            futureList.add(voidCompletableFuture);
        }

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(memberDetailVo.getId());
            if (CollUtil.isNotEmpty(memberIndustry)) {
                Map<Long, String> map = memberIndustry.stream().collect(Collectors.toMap(SelectConfigResp::getId, SelectConfigResp::getVal, (oldVal, newVal) -> newVal));
                memberDetailVo.setIndustryIds(new ArrayList<>(map.keySet()));
                memberDetailVo.setIndustryNames(new ArrayList<>(map.values()));
            } else {
                SelectConfig selectConfig = selectConfigMapper.selectByPrimaryKey(memberDetailVo.getIndustryId());
                if (selectConfig != null) {
                    memberDetailVo.setIndustryId(selectConfig.getId());
                    memberDetailVo.setIndustryName(selectConfig.getVal());
                }
            }
        }, threadPoolExecutor);
        futureList.add(voidCompletableFuture);

        try {
            CompletableFuture.allOf(
                    futureList.toArray(new CompletableFuture[futureList.size()])
            ).get(5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("MemberServiceImpl -> findMemberMessageDetail -> 异步失败 -> error: {}", e);
            throw new BusinessException("查询用户个人信息失败,请稍候重试");
        }
        log.info("MemberServiceImpl -> findMemberMessageDetail -> end");
        return memberDetailVo;
    }

    /**
     * 修改用户个人信息
     *
     * @param memberDetailVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMemberMessage(MemberDetailVo memberDetailVo) {
        for (String match : memberDetailVo.getLabel()) {
            boolean fa = VerifyMatch.containsAll(match);
            if (fa) {
                throw new BusinessException(OrganizaitonErrorEnum.CHAR_ORGANIZATION_ERROR);
            }
            if (match.length() > 16) {
                throw new BusinessException(OrganizaitonErrorEnum.LABELNO_LONG_ERROR);
            }
        }
        if (StrUtil.isNotBlank(memberDetailVo.getName()) && VerifyCodeUtils.isNum(memberDetailVo.getName())) {
            throw new BusinessException(MemberExceptionEnum.MEMBER_NAME_IS_NUMBER);
        }
        Member member = memberMapper.selectByPrimaryKey(memberDetailVo.getId());
        if (member == null) {
            log.info("MemberServiceImpl updateMemberMessage --用户不存在------");
            throw new BusinessException(OrganizaitonErrorEnum.SENDTYPE_AHIECEMENTEQUIPMENT_ERROR);
        }
        byte flag = ConstantEnum.NOT_DEL.getByte();
        member.setUpdateTime(LocalDateTime.now())
                .setPositionId(memberDetailVo.getPositionId())
                .setEducationId(memberDetailVo.getEducationId())
                .setEmail(memberDetailVo.getEmail())
                .setName(memberDetailVo.getName())
                .setHeadImage(memberDetailVo.getHeadImage());
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(member.getId());
        int num = memberMapper.updateByExampleSelective(member, memberExample);
        if (num > ConstantEnum.NOT_DEL.getInt()) {
            List<Long> industryIds = new ArrayList<>();
            if (CollUtil.isEmpty(memberDetailVo.getIndustryIds()) && memberDetailVo.getIndustryId() != null) {
                industryIds.add(memberDetailVo.getIndustryId());
            } else {
                industryIds = memberDetailVo.getIndustryIds();
            }
            //修改会员行业
            editIndustry(industryIds, memberDetailVo.getId());
            //管理员
            if (MemberEnum.ROLE_ADMINISTRATOR.getValue().equals(member.getRole())) {
                //修改公司行业
                editIndustry(industryIds, member.getOrganizationId());
            }

            //如果修改成功开始删掉原有标签 重新录入标签 同时还要反查所属机构是否含有
            List<String> label = memberDetailVo.getLabel().stream()
                    .map(StrUtil::trim)
                    .filter(StrUtil::isNotBlank)
                    .distinct()
                    .collect(Collectors.toList());
            if (label.size() <= ConstantEnum.NOT_DEL.getInt()) {
                throw new BusinessException(OrganizaitonErrorEnum.LABELNO_ORGANIZATION_ERROR);
            }
            LinkLabelExample linkLabelExample = new LinkLabelExample();
            linkLabelExample.createCriteria().andTypeEqualTo(LinkLabelEnum.LINK_LABEL_MEMBER.getCode()).andDelFlagEqualTo(flag).andSourceIdEqualTo(memberDetailVo.getId());
            int i = linkLabelMapper.deleteByExample(linkLabelExample);
            log.info("- MemberServiceImpl updateMemberMessage 删除个人标签数量:{} -", i);
            for (String la : label) {
                LabelExample labelExample = new LabelExample();
                labelExample.createCriteria().andValEqualTo(la.replace(" ", ""));
                LinkLabel linklb = new LinkLabel();
                Label lb = labelMapper.selectOneByExample(labelExample);
                log.info("- MemberServiceImpl updateMemberMessage 标签已经存在直接建立个人关系组织机构反查 -");
                if (lb != null) {
                    linklb.setDelFlag(flag).setId(snowflakeIdWorker53.nextId()).setType(LinkLabelEnum.LINK_LABEL_MEMBER.getCode()).setLabelId(lb.getId()).setSourceId(memberDetailVo.getId());
                    //个人的
                    linkLabelMapper.insertSelective(linklb);
                    labelExample.clear();
                } else {
                    log.info("- MemberServiceImpl updateMemberMessage 标签不存在建立标签库同时搭建关系 -");
                    Label lab = new Label();
                    long labelId = snowflakeIdWorker53.nextId();
                    lab.setId(labelId).setDelFlag(flag).setVal(la.replace(" ", "")).setCreateTime(LocalDateTime.now());
                    // todo batch insert
                    labelMapper.insert(lab);
                    linklb.setDelFlag(flag).setId(snowflakeIdWorker53.nextId()).setType(LinkLabelEnum.LINK_LABEL_MEMBER.getCode()).setLabelId(labelId).setSourceId(memberDetailVo.getId());
                    LinkLabel organizationLink = new LinkLabel();
                    organizationLink.setDelFlag(flag).setId(snowflakeIdWorker53.nextId()).setType(LinkLabelEnum.LINK_LABEL_ORGANIZATION.getCode()).setLabelId(labelId).setSourceId(member.getOrganizationId());
                    linkLabelMapper.insertSelective(linklb);
                    linkLabelMapper.insertSelective(organizationLink);
                }
            }

            //会员修改事件
            publishEventComponent.publishEvent(new MemberUpdateEvent(
                    MemberUpdateEventParam.builder()
                            .memberId(memberDetailVo.getId())
                            .memberHeadImg(memberDetailVo.getHeadImage())
                            .education(memberDetailVo.getEducationId() == null ? null : Optional.ofNullable(selectConfigMapper.selectByPrimaryKey(memberDetailVo.getEducationId())).orElse(new SelectConfig()).getVal())
                            .memberPosition(memberDetailVo.getPositionId() == null ? null : Optional.ofNullable(selectConfigMapper.selectByPrimaryKey(memberDetailVo.getPositionId())).orElse(new SelectConfig()).getVal())
                            .belong(memberDetailVo.getBelong())
                            .memberUpdateEnum(MemberUpdateEventParam.MemberUpdateEventEnum.UPDATE_MEMBER_MESSAGE)
                            .build()
            ));

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrganization(UpdateOriganizationDto updateOriganizationDto) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(updateOriganizationDto.getOperator()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(OrganizaitonErrorEnum.USERNOTFOUD_ORGANIZATION_ERROR);
        }
        Long organizationId = member.getOrganizationId();
        if (!organizationId.equals(updateOriganizationDto.getOrganizationId())) {
            throw new BusinessException(OrganizaitonErrorEnum.MESSAGE_AHIECEMENTEQUIPMENT_ERROR);
        }
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria()
                .andIdEqualTo(updateOriganizationDto.getOrganizationId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
        organization.setBelong(updateOriganizationDto.getBelong())
                .setUpdateTime(LocalDateTime.now())
                .setName(updateOriganizationDto.getName())
                .setScaleId(updateOriganizationDto.getScaleId())
                .setAddressAreaName(updateOriganizationDto.getAddressAreaName())
                .setAddressAreaId(updateOriganizationDto.getAddressAreaId())
                .setAddressProvinceName(updateOriganizationDto.getAddressProvinceName())
                .setAddressProvinceId(updateOriganizationDto.getAddressProvinceId())
                .setAddressCityName(updateOriganizationDto.getAddressCityName())
                .setAddressCityId(updateOriganizationDto.getAddressCityId())
                .setScaleId(updateOriganizationDto.getScaleId());
        organizationMapper.updateByExampleSelective(organization, organizationExample);

        //会员修改事件
        publishEventComponent.publishEvent(new MemberUpdateEvent(
                MemberUpdateEventParam.builder()
                        .memberId(organization.getResponsibleMemberId())
                        .memberOrganizationName(updateOriganizationDto.getName())
                        //0-实验室；1-公司 to 0-会员 1-组织机构 2-实验室
                        .memberType(organization.getType() == 0 ? 2 : 1)
                        .memberUpdateEnum(MemberUpdateEventParam.MemberUpdateEventEnum.UPDATE_ORGANIZATION)
                        .build()
        ));
    }


    /**
     * 根据会员ID查询会员基本信息
     *
     * @param
     * @return
     */
    @Override
    public MemberInfoRes getMemberByMemberId(Long memberId) {
        log.info("MemberService -> getMemberByMemberId -> start -> memberId :{}", memberId);
        MemberInfoRes res = new MemberInfoRes();
        MemberInfo memberInfo = customMemberMapper.getMemberByMemberId(memberId);
        List<Label> labels = customLabelMapper.getByMemberId(memberId);
        log.info("MemberService -> getMemberByMemberId -> memberInfo != null memberInfo :{}", memberInfo);
        if (memberInfo != null) {
            BeanUtils.copyProperties(memberInfo, res);
            if (StrUtil.isNotEmpty(memberInfo.getIndustryIds())) {
                res.setIndustryIds(convertIndustry(memberInfo.getIndustryIds()).stream().map(Long::valueOf).collect(Collectors.toList()));
            }
            if (StrUtil.isNotEmpty(memberInfo.getIndustryNames())) {
                res.setIndustryNames(convertIndustry(memberInfo.getIndustryNames()));
            }
            if (StrUtil.isNotEmpty(memberInfo.getQualifications())) {
                res.setQualifications(Arrays.asList(memberInfo.getQualifications().split(",")));
            }
        }
        log.info("MemberService -> getMemberByMemberId -> labels != null labels :{}", labels);
        setLabels(res, labels);
        log.info("MemberService -> getMemberByMemberId -> end -> res:{}", res);
        return res;
    }

    /**
     * 根据unionId查询会员基本信息
     *
     * @param unionId
     * @return
     */
    @Override
    public MemberInfoRes getMemberByUnionId(String unionId) {
        log.info("MemberService -> getMemberByUnionId -> start -> unionId:{}", unionId);
        MemberInfoRes res = new MemberInfoRes();
        MemberInfo memberInfo = customMemberMapper.getMemberByUnionId(unionId);
        log.info("MemberService -> getMemberByUnionId -> memberInfo != null -> memberInfo :{}", memberInfo);
        if (memberInfo != null) {
            BeanUtils.copyProperties(memberInfo, res);
            if (StrUtil.isNotEmpty(memberInfo.getIndustryIds())) {
                res.setIndustryIds(convertIndustry(memberInfo.getIndustryIds()).stream().map(Long::valueOf).collect(Collectors.toList()));
            }
            if (StrUtil.isNotEmpty(memberInfo.getIndustryNames())) {
                res.setIndustryNames(convertIndustry(memberInfo.getIndustryNames()));
            }
            if (StrUtil.isNotEmpty(memberInfo.getQualifications())) {
                res.setQualifications(Arrays.asList(memberInfo.getQualifications().split(",")));
            }
        }
        List<Label> labelList = customLabelMapper.getByMemberId(res.getId());
        setLabels(res, labelList);
        log.info("MemberService -> getMemberByUnionId -> end -> res:{}", res);
        return res;
    }

    /**
     * 根据会员ID查询会员详细信息
     *
     * @param memberId
     * @return
     */
    @Override
    public MemberDetailedRes getDetailedByMemberId(Long memberId) {
        log.info("MemberService -> getDetailedByMemberId -> start -> memberId:{}", memberId);
        MemberDetailedRes res = new MemberDetailedRes();
        MemberDetailed detailed = customMemberMapper.getDetailedById(memberId);
        log.info("MemberService -> getDetailedByMemberId -> detailed != null -> detailed:{}", detailed);
        if (detailed != null) {
            BeanUtils.copyProperties(detailed, res);
            res.setPassword(Md5Util.getCrypt(String.valueOf(res.getImEasemobUsername())));
            if (StrUtil.isNotEmpty(detailed.getIndustryIds())) {
                res.setIndustryIds(convertIndustry(detailed.getIndustryIds()).stream().map(Long::valueOf).collect(Collectors.toList()));
            }
            if (StrUtil.isNotEmpty(detailed.getIndustryNames())) {
                res.setIndustryNames(convertIndustry(detailed.getIndustryNames()));
            }
            if (StrUtil.isNotEmpty(detailed.getQualifications())) {
                res.setQualifications(Arrays.asList(detailed.getQualifications().split(",")));
            }
        }
        List<Label> labels = customLabelMapper.getByMemberId(memberId);
        setLabels(res, labels);
        log.info("MemberService -> getDetailedByMemberId -> end -> res:{}", res);
        return res;
    }

    @Override
    public MemberDetailedRes getDetailedByUnionId(String unionId) {
        log.info("MemberService -> getDetailedByUnionId -> start -> unionId:{}", unionId);
        MemberDetailedRes res = new MemberDetailedRes();
        MemberDetailed detailed = customMemberMapper.getDetailedByUnionId(unionId);
        log.info("MemberService -> getDetailedByUnionId -> detailed != null -> detailed:{}", detailed);
        if (detailed != null) {
            BeanUtils.copyProperties(detailed, res);
            if (StrUtil.isNotEmpty(detailed.getIndustryIds())) {
                res.setIndustryIds(convertIndustry(detailed.getIndustryIds()).stream().map(Long::valueOf).collect(Collectors.toList()));
            }
            if (StrUtil.isNotEmpty(detailed.getIndustryNames())) {
                res.setIndustryNames(convertIndustry(detailed.getIndustryNames()));
            }
            if (StrUtil.isNotEmpty(detailed.getQualifications())) {
                res.setQualifications(Arrays.asList(detailed.getQualifications().split(",")));
            }
            List<Label> labels = customLabelMapper.getByMemberId(detailed.getId());
            setLabels(res, labels);
        }
        log.info("MemberService -> getDetailedByUnionId -> end -> res:{}", res);
        return res;
    }


    /**
     * 修改会员信息
     *
     * @param req
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(MemberInfoUpdateReq req) {
        log.info("MemberService -> update -> start -> req ：{}", req);
        List<Long> industryIds = new ArrayList<>();
        if (CollUtil.isEmpty(req.getIndustryIds()) && req.getIndustryId() != null) {
            industryIds.add(req.getIndustryId());
        } else {
            industryIds = req.getIndustryIds();
        }


        MemberExample memberExampleQuery = new MemberExample();
        memberExampleQuery.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(req.getId());
        Member memberQuery = memberMapper.selectOneByExample(memberExampleQuery);
        log.info("MemberService -> update -> memberQuery==null : {}", memberQuery == null);
        if (memberQuery == null) {
            log.info("MemberService -> update -> error :{}", MemberExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MemberExceptionEnum.DELETE_TRUE);
        }
        //删除原有标签
        LinkLabelExample linkLabelExample = new LinkLabelExample();
        linkLabelExample.createCriteria().andSourceIdEqualTo(req.getId()).andTypeEqualTo(LinkLabelEnum.LINK_LABEL_MEMBER.getCode());
        linkLabelMapper.updateByExampleSelective(LinkLabel.builder().delFlag(ConstantEnum.DELETED.getByte()).build(), linkLabelExample);
        //添加新标签
        if (StrUtil.isNotEmpty(req.getLabels())) {
            List<String> sourceLabel = Arrays.asList(req.getLabels().replace("，", ",").split(","));
            List<String> newLabel = sourceLabel.stream()
                    .map(StrUtil::trim)
                    .filter(StrUtil::isNotBlank)
                    .distinct()
                    .collect(Collectors.toList());
            for (String match : newLabel) {
                boolean fa = VerifyMatch.containsAll(match);
                if (fa) {
                    throw new BusinessException(OrganizaitonErrorEnum.CHAR_ORGANIZATION_ERROR);
                }
                if (match.length() > 16) {
                    throw new BusinessException(OrganizaitonErrorEnum.LABELNO_LONG_ERROR);
                }
            }
            List<LinkLabel> linkLabels = new ArrayList<>();
            newLabel.forEach(label -> {
                LabelExample labelExample = new LabelExample();
                labelExample.createCriteria().andValEqualTo(label).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
                Label queryLabel = labelMapper.selectOneByExample(labelExample);
                if (ObjectUtil.isNotEmpty(queryLabel)) {
                    LinkLabel linkLabel = LinkLabel.builder()
                            .id(snowflakeIdWorker53.nextId())
                            .createTime(LocalDateTime.now())
                            .delFlag(ConstantEnum.NOT_DEL.getByte())
                            .sourceId(req.getId())
                            .labelId(queryLabel.getId())
                            .type(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                            .build();
                    linkLabels.add(linkLabel);
                } else {
                    Long labelId = snowflakeIdWorker53.nextId();
                    // batch insert todo
                    labelMapper.insertSelective(Label.builder().id(labelId).val(label).build());
                    linkLabels.add(LinkLabel.builder()
                            .id(snowflakeIdWorker53.nextId())
                            .createTime(LocalDateTime.now())
                            .delFlag(ConstantEnum.NOT_DEL.getByte())
                            .sourceId(req.getId())
                            .labelId(labelId)
                            .type(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                            .build());
                }
            });

            //删除原来的会员标签
            LinkLabelExample linkLabelExampleDel = new LinkLabelExample();
            linkLabelExampleDel.createCriteria()
                    .andTypeEqualTo(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                    .andSourceIdEqualTo(req.getId());
            linkLabelMapper.updateByExampleSelective(LinkLabel.builder().delFlag(ConstantEnum.DELETED.getByte()).build(), linkLabelExampleDel);
            //添加会员标签
            if (CollUtil.isNotEmpty(linkLabels)) {
                linkLabelMapper.batchInsert(linkLabels);
            }
        }


        //添加行业
        editIndustry(industryIds, req.getId());
        //管理员
        if (MemberEnum.ROLE_ADMINISTRATOR.getValue().equals(memberQuery.getRole())) {
            //修改公司行业
            editIndustry(industryIds, memberQuery.getOrganizationId());
        }

        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(req.getId());
        Member member = memberMapper.selectByPrimaryKey(req.getId());
        member.setEducationId(req.getEducationId())
                .setPositionId(req.getPositionId())
                .setEmail(req.getEmail())
                .setBackendImage(req.getBackendImage())
                .setHeadImage(req.getHeadImage());
        memberMapper.updateByExampleSelective(member, memberExample);


        //会员修改事件
        publishEventComponent.publishEvent(new MemberUpdateEvent(
                MemberUpdateEventParam.builder()
                        .memberId(req.getId())
                        .memberHeadImg(req.getHeadImage())
                        .education(req.getEducationId() == null ? null : Optional.ofNullable(selectConfigMapper.selectByPrimaryKey(req.getEducationId())).orElse(new SelectConfig()).getVal())
                        .memberPosition(req.getPositionId() == null ? null : Optional.ofNullable(selectConfigMapper.selectByPrimaryKey(req.getPositionId())).orElse(new SelectConfig()).getVal())
                        .memberUpdateEnum(MemberUpdateEventParam.MemberUpdateEventEnum.UPDATE)
                        .build()
        ));

        log.info("MemberService -> update -> end");
    }

    /**
     * 给res对象labels赋值
     *
     * @param res
     * @param labelList
     */
    private void setLabels(MemberInfoRes res, List<Label> labelList) {
        log.info("MemberService -> setLabels -> start -> res:{} , labelList :{}", res, labelList);
        if (CollUtil.isNotEmpty(labelList)) {
            List<MemberLabelRes> labelResList = new ArrayList<>();
            for (Label label : labelList) {
                MemberLabelRes labelRes = new MemberLabelRes();
                BeanUtils.copyProperties(label, labelRes);
                labelResList.add(labelRes);
            }
            res.setLabels(labelResList);
        }
        log.info("MemberService -> setLabels -> end");
    }

    private void setLabels(MemberDetailedRes res, List<Label> labelList) {
        log.info("MemberService -> setLabels -> start -> res:{} , labelList :{}", res, labelList);
        if (CollUtil.isNotEmpty(labelList)) {
            List<MemberLabelRes> labelResList = new ArrayList<>();
            for (Label label : labelList) {
                MemberLabelRes labelRes = new MemberLabelRes();
                BeanUtils.copyProperties(label, labelRes);
                labelResList.add(labelRes);
            }
            res.setLabels(labelResList);
        }
        log.info("MemberService -> setLabels -> end");
    }

    @Override
    public List<SelectConfigResp> findSelectConfigByOrganization(Long organizationId) {
        return customLinkSelectMapper.findSelectConfigByOrganization(organizationId);
    }

    @Override
    public List<Long> listMemberByPositionAndTime(int howMany, List<Long> blockList) {
        return customLinkSelectMapper.listMemberByPositionAndTime(howMany, blockList);
    }


    /**
     * 验证用户是否是会员
     */

    public boolean verifyIsVip(Long id) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(id).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            log.info("MemberService -> verifyIsVip -> 认证用户不存在");
            return false;
        }
        if (null != member.getOrganizationId()) {
            LocalDateTime organizationVipEndTime = Optional.ofNullable(organizationMapper.selectByPrimaryKey(member.getOrganizationId()))
                    .map(Organization::getVipEndTime)
                    .orElse(LocalDateTime.MIN);
            if (LocalDateTime.now().isBefore(organizationVipEndTime)) {
                return true;
            }
        }
        Integer level = member.getLevel();
        if (MemberEnum.LEVEL_VIP.getValue() != level.intValue()) {
            log.info("MemberService -> verifyIsVip -> 普通用户");
            return false;
        }
        return true;
    }


    /**
     * 推送开关
     */
    @Override
    public void offPush(Integer status, Long memberId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().
                andIdEqualTo(memberId).
                andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            log.info("MemberService -> offPush -> 用户不存在");
            throw new BusinessException(LogBehaveMemberErrorEnum.USER_LOGBEHAVEMEMBER_ERROR);
        }
        member.setPushStatus(status);
        memberMapper.updateByExampleSelective(member, memberExample);
    }

    @Override
    public OrganizationRep findOrganizationByMemberId(Long memberId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().
                andIdEqualTo(memberId).
                andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).
                andOrganizationIdIsNotNull();
        Member member = memberMapper.selectOneByExample(memberExample);
        OrganizationRep organizationRep = new OrganizationRep();
        if (member == null) {
            log.info("MemberService -> findOrganizationByMemberId -> 用户不存在");
            organizationRep.setFlag(false);
            return organizationRep;
        }
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andIdEqualTo(member.getOrganizationId()).
                andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            log.info("MemberService -> findOrganizationByMemberId -> 组织机构不存在");
            organizationRep.setFlag(false);
            return organizationRep;
        } else {
            organizationRep.setFlag(true);
            organizationRep.setRoleId(member.getRole());
            organizationRep.setType(organization.getType());
        }
        return organizationRep;
    }

    @Override
    public List<MemberInfoBashRes> listAllMemberPhone() {
        MemberExample example = new MemberExample();
        example.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<Member> members = memberMapper.selectByExample(example);

        List<MemberInfoBashRes> result = members.stream().map(member -> {
            MemberInfoBashRes bean = new MemberInfoBashRes();
            bean.setId(member.getId());
            bean.setMobile(member.getPhone());
            return bean;
        }).collect(Collectors.toList());
        return result;
    }


    /**
     * 根据用户表示绑定行业
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void memberBindingIndustry(BindIndustryReq req) {
        log.info("MemberService -> memberBindingIndustry -> start -> params -> {}", req);
        Member member = memberMapper.selectByPrimaryKey(req.getMemberId());
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        List<Long> industry = new ArrayList<>();
        if (CollUtil.isEmpty(req.getIndustryIds())) {
            industry.add(req.getIndustry());
        } else {
            industry = req.getIndustryIds();
        }
        SelectConfigExample selectConfigExample = new SelectConfigExample();
        selectConfigExample.createCriteria().andIdIn(industry).
                andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).
                andTypeEqualTo(DictConstantEnum.INDUSTRY.getId())
                .andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue());
        List<SelectConfig> selectConfig = selectConfigMapper.selectByExample(selectConfigExample);
        if (selectConfig.size() != industry.size()) {
            throw new BusinessException(SelectConfigExceptionEnum.INDUSTRYID_NOTFOUND);
        }
//        member.setIndustryId(industry);
//        MemberExample memberExample = new MemberExample();
//        memberExample.createCriteria().andIdEqualTo(memberId);
//        memberMapper.updateByExampleSelective(member, memberExample);
        editIndustry(industry, req.getMemberId());
        //管理员
        if (MemberEnum.ROLE_ADMINISTRATOR.getValue().equals(member.getRole())) {
            //修改公司行业
            editIndustry(industry, member.getOrganizationId());
        }
        // 生成 refresh token
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(member.getId());
        bucket.set(JSONUtil.toJsonStr(member), 120L, TimeUnit.MINUTES);
    }


    /**
     * 根据用户表示绑定邮箱
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void memberBindingEmail(Long memberId, String email) {
        log.info("MemberService -> memberBindingEmail -> 根据用户表示绑定邮箱用户标识{} 邮箱{}", memberId, email);
        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (member == null) {
            throw new BusinessException(LogBehaveMemberErrorEnum.USER_LOGBEHAVEMEMBER_ERROR);
        }
        member.setEmail(email);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(memberId);
        memberMapper.updateByExampleSelective(member, memberExample);
        // 生成 refresh token
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(member.getId());
        bucket.set(JSONUtil.toJsonStr(member), 120L, TimeUnit.MINUTES);
    }

    /**
     * 简单修改用户基本信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyMemberBasic(MemberBasicReq memberBasicReq) {
        if (StrUtil.isNotBlank(memberBasicReq.getName()) && VerifyCodeUtils.isNum(memberBasicReq.getName())) {
            throw new BusinessException(MemberExceptionEnum.MEMBER_NAME_IS_NUMBER);
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andIdEqualTo(memberBasicReq.getId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectByPrimaryKey(memberBasicReq.getId());
        if (member == null) {
            throw new BusinessException(LogBehaveMemberErrorEnum.USER_LOGBEHAVEMEMBER_ERROR);
        }
        member.setName(memberBasicReq.getName())
                .setEmail(memberBasicReq.getEmail())
                .setHeadImage(memberBasicReq.getHandUrl())
                .setPositionId(memberBasicReq.getPositionId())
                .setUpdateTime(null);
        memberMapper.updateByExampleSelective(member, memberExample);
        editIndustry(memberBasicReq.getIndustryIds(), memberBasicReq.getId());
        //管理员
        if (MemberEnum.ROLE_ADMINISTRATOR.getValue().equals(member.getRole())) {
            //修改公司行业
            editIndustry(memberBasicReq.getIndustryIds(), member.getOrganizationId());
        }
    }


    /**
     * 通过用户ID查询组织机构表的审核状态
     *
     * @param memberId 入参：用户ID
     * @return verifyStatus -- 0-未审核 1-审核失败 2-审核成功    入驻审核
     */
    @Override
    public Long findMemberStatus(Long memberId) {
        Long verifyStatus = organizationMapper.getOrganStaus(memberId);
        if (ObjectUtil.isEmpty(verifyStatus)) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_ORGAN);
        }
        return verifyStatus;
    }

    /**
     * 实验室上传验证证件接口
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void memberAuthentication(MemberAuthenticationReq memberAuthenticationReq) {
        Long memberId = memberAuthenticationReq.getMemberId();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(MemberEnum.ROLE_ORDINARY.getValue().byteValue()).
                andIdEqualTo(memberId);
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
        Long organizationId = member.getOrganizationId();
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andIdEqualTo(organizationId).andDelFlagEqualTo(OrganizationEnum.DELETE_FLAG_NO.getValue().byteValue());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);

        if (organization == null || !memberAuthenticationReq.getType().equals(organization.getType())) {
            throw new BusinessException(OrganizaitonErrorEnum.PARAMS_ORGANIZATION_ERROR);
        }
        List<String> licenses = memberAuthenticationReq.getLicense();
        String qualifications = null;
        if (organization.getType().equals(OrganizationEnum.TYPE_LABORATORY.getValue())) {
            List<String> qualificationList = memberAuthenticationReq.getQualifications();

            Organization organizationUpdate = null;
            if (CollUtil.isNotEmpty(qualificationList)) {
                qualifications = qualificationList.stream().collect(Collectors.joining(","));
                organizationUpdate = new Organization();
                organizationUpdate.setId(organizationId);
                organizationUpdate.setQualifications(qualifications);
            }
            if (CollUtil.isNotEmpty(licenses)) {
                String license = licenses.stream().collect(Collectors.joining(","));
                if (organizationUpdate == null) {
                    organizationUpdate = new Organization();
                    organizationUpdate.setId(organizationId);
                }
                organizationUpdate.setLicense(license);
            }

            if (organizationUpdate != null) {
                organizationMapper.updateByPrimaryKeySelective(organizationUpdate);
            }

            member.setVerifyStatus(MemberEnum.VERIFY_STATUS_ING.getValue());
        } else {
            if (CollUtil.isNotEmpty(licenses)) {
                String license = licenses.stream().collect(Collectors.joining(","));
                member.setLicense(license);
            }
            member.setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());

        }

        memberMapper.updateByExampleSelective(member, memberExample);
    }

    @Override
    public List<Long> listMemberWithoutSubscribe(Long memberId) {
        return customLinkSelectMapper.listMemberWithoutSubscribe(memberId);
    }

    @Override
    public List<MemberSummaryRes> listMemberSummaryByMemberId(List<Long> ids) {
        log.info("MemberService -> listMemberByMemberId -> start -> req -> {}", ids);

        if (CollUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }

        MemberExample example = new MemberExample();
        example.createCriteria()
                .andIdIn(ids)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<Member> members = memberMapper.selectByExample(example);

        List<MemberSummaryRes> result = new ArrayList<>();
        for (Member member : members) {
            MemberSummaryRes bean = new MemberSummaryRes();
            BeanUtils.copyProperties(member, bean);
            result.add(bean);
        }
        log.info("MemberService -> listMemberByMemberId -> end");
        return result;
    }


    /**
     * 查询用户是否完善公司信息
     *
     * @param memberId
     * @return
     */
    @Override
    public boolean findMemberIsPrefect(Long memberId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(memberId).andDelFlagEqualTo(MemberEnum.ROLE_ORDINARY.getValue().byteValue());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        String phone = member.getPhone();
        String organizationName = phone + "的公司";
        Long organizationId = member.getOrganizationId();
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andIdEqualTo(organizationId).
                andDelFlagEqualTo(OrganizationEnum.STATUS_UNAUTHORIZED.getValue().byteValue());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
        String name = organization.getName();
        if (organizationName.equals(name)) {
            return false;
        }
        return true;
    }

    @Override
    public void openVipAuthorizedTimed(Long memberId, Long serverVal) {
        log.info("MemberService -> openVipAuthorizedTimed -> start -> memberId -> {} serverVal -> {}", memberId, serverVal);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andIdEqualTo(memberId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime vipStartTime = null;
        LocalDateTime vipEndTime = null;

        // 是否已是会员
        // 定时器执行时有时间偏差：可能会出现会员已过期但状态还是1的情况  所以使用双值判断
        LocalDateTime oldEndTime = member.getVipEndTime();
        boolean isVip = MemberEnum.LEVEL_VIP.getValue().equals(member.getLevel()) && oldEndTime.isAfter(now);

        log.info("MemberService -> openVipAuthorizedTimed -> memberId -> {} isVip -> {}", memberId, isVip);
        if (isVip) {
            vipStartTime = oldEndTime;
            vipEndTime = oldEndTime.plusMinutes(serverVal);
            member.setVipEndTime(vipEndTime);
        } else {
            vipStartTime = now;
            vipEndTime = now.plusMinutes(serverVal);
            member.setVipStartTime(vipStartTime);
            member.setVipEndTime(vipEndTime);
            member.setLevel(MemberEnum.LEVEL_VIP.getValue());
        }
        log.info("MemberService -> openVipAuthorizedTimed -> memberId -> {} vipStartTime -> {} vipEndTime -> {}", memberId, vipStartTime, vipEndTime);
        memberMapper.updateByPrimaryKeySelective(member);
    }

    @Override
    public List<MemberIndustryRes> findMemberIndustry(Long memberId) {
        log.info("MemberService -> findMemberIndustry -> start -> params -> {}", memberId);
        List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(memberId);
        List<MemberIndustryRes> res = new ArrayList<>();
        memberIndustry.forEach(industry -> {
            MemberIndustryRes memberIndustryRes = new MemberIndustryRes();
            BeanUtils.copyProperties(industry, memberIndustryRes);
            res.add(memberIndustryRes);
        });
        log.info("MemberService -> findMemberIndustry -> end");
        return res;
    }

    @Override
    public Boolean cancellationMember(MemberCancellationReq req) {
        log.info("MemberService -> cancellationMember -> start -> params -> {}", req);
        String sign = Md5Util.getEncrypt(req.getMemberId().toString());
        sign = Md5Util.getEncrypt(sign + req.getMemberId());
        if (!sign.equals(req.getSign())) {
            throw new BusinessException("注销用户签名不正确");
        }

        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andIdEqualTo(req.getMemberId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        int i = memberMapper.updateByPrimaryKeySelective(
                new Member()
                        .setId(member.getId())
                        .setDelFlag(ConstantEnum.DELETED.getByte()),
                Member.Column.delFlag);

        log.info("MemberService -> cancellationMember -> end -> sql.update.count:{}", i);

        if (i == 1) {
            //会员注销事件
            publishEventComponent.publishEvent(new MemberUpdateEvent(
                    MemberUpdateEventParam.builder()
                            .memberId(req.getMemberId())
                            .memberUpdateEnum(MemberUpdateEventParam.MemberUpdateEventEnum.CANCELLATION_MEMBER)
                            .build()
            ));
            return true;
        }
        return false;
    }

    /**
     * string 行业转换为 行业集合
     *
     * @param industry 行业
     * @return 行业集合
     */
    private List<String> convertIndustry(String industry) {
        List<String> res = new ArrayList<>();
        if (StrUtil.isNotEmpty(industry)) {
            res = Arrays.asList(industry.split(","));
        }
        return res;
    }

    /**
     * 修改行业
     *
     * @param industry 行业ID集合
     * @param memberId 用户ID
     */
    private void editIndustry(List<Long> industry, Long memberId) {
        industrySyncComponent.industrySync(industry, memberId);
    }

    @Override
    public Boolean hasOrganization(Long memberId) {
        MemberExample example = new MemberExample();
        example.createCriteria()
                .andIdEqualTo(memberId)
                .andOrganizationIdIsNotNull()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());

        return memberMapper.countByExample(example) > 0;
    }
}

