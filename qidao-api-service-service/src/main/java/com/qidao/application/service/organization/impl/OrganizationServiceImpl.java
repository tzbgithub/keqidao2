package com.qidao.application.service.organization.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.inner.Salesman;
import com.qidao.application.entity.inner.SalesmanExample;
import com.qidao.application.entity.invite.Invite;
import com.qidao.application.entity.invite.InviteExample;
import com.qidao.application.entity.label.Label;
import com.qidao.application.entity.label.LabelExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberDetailed;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.member.OrganizationMember;
import com.qidao.application.entity.organization.MemberOrganizationTypeDO;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.organization.OrganizationDetail;
import com.qidao.application.entity.organization.OrganizationExample;
import com.qidao.application.entity.relation.*;
import com.qidao.application.entity.server.Server;
import com.qidao.application.entity.server.ServerExample;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.inner.SalesmanMapper;
import com.qidao.application.mapper.invite.InviteMapper;
import com.qidao.application.mapper.label.LabelMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.CustomOrganizationMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.mapper.relation.CustomLinkLabelMapper;
import com.qidao.application.mapper.relation.LinkLabelMapper;
import com.qidao.application.mapper.relation.LinkOrganizationSalesmanMapper;
import com.qidao.application.mapper.relation.LinkSelectMapper;
import com.qidao.application.mapper.server.ServerMapper;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.VerifyMatch;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.SelectConfigDTO;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dto.*;
import com.qidao.application.model.label.LabelDTO;
import com.qidao.application.model.label.LinkLabelEnum;
import com.qidao.application.model.member.*;
import com.qidao.application.model.organization.KickOutMemberReq;
import com.qidao.application.model.organization.OrganizationDetailRes;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.model.organization.SignOutEnterpriseReq;
import com.qidao.application.model.organization.enums.OrganizaitonErrorEnum;
import com.qidao.application.service.event.PublishEventComponent;
import com.qidao.application.service.event.member.MemberUpdateEvent;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.organization.OrganizationService;
import com.qidao.application.service.organization.other.IndustrySyncComponent;
import com.qidao.application.vo.*;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Autowired
    private CustomOrganizationMapper customOrganizationMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private LinkLabelMapper linkLabelMapper;
    @Autowired
    private SalesmanMapper salesmanMapper;
    @Autowired
    private LinkOrganizationSalesmanMapper linkOrganizationSalesmanMapper;
    @Autowired
    private SelectConfigMapper selectConfigMapper;
    @Autowired
    private CustomMemberMapper customMemberMapper;
    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private CustomLinkLabelMapper customLinkLabelMapper;
    @Resource
    private LinkSelectMapper linkSelectMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PublishEventComponent publishEventComponent;
    @Resource
    private InviteMapper inviteMapper;
    @Autowired
    private IndustrySyncComponent industrySyncComponent;

    /**
     * ??????/???????????????    ???????????? ????????????????????????????????????????????????????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized String save(HttpServletRequest request, OrganizationDto organizationDto, int type) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andPhoneEqualTo(organizationDto.getPhone());
        Member members = memberMapper.selectOneByExample(memberExample);
        if (members != null && members.getOrganizationId() != null) {
            log.info("OrganizationServiceImpl -> save -- ??????????????????????????? --");
            throw new BusinessException(OrganizaitonErrorEnum.INSERT_ORGANIZATION_NOTEXISE);
        }
        baseOrganizationSave(organizationDto.getLabel());
        long organizationId = snowflakeIdWorker53.nextId();
        Organization entity = customOrganizationMapper.findByName(organizationDto.getName(), type, organizationDto.getBelong());
        log.info("OrganizationServiceImpl -> save -- ?????????????????? -- type {}---", type);
        Organization organization = new Organization();
        long memberId = snowflakeIdWorker53.nextId();
        Member member = new Member();
        switch (type) {
            case 0:
                break;
            case 1:
                log.info("OrganizationServiceImpl -> save -- ?????? entity --!=null case [1] ---");
                if (members == null) {
                    member.setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());
                } else {
                    members.setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());
                }
                break;
            default:
                throw new BusinessException(OrganizaitonErrorEnum.INSERT_ORGANIZATION_ERROR);
        }
        log.info("OrganizationServiceImpl -> save -- ?????????????????? ---");
        List<LinkLabel> insertLinkLabelList = new ArrayList<>();
        HashSet<String> labels = new HashSet<>();
        String[] label = organizationDto.getLabel();
        for (String labe : label) {
            String trim = labe.trim();
            if ("".equals(trim)) {
                continue;
            }
            if (labe.length() > 16) {
                throw new BusinessException("??????????????????16?????????");
            }
            labels.add(trim);
        }

        for (String la : labels) {
            LabelExample labelExample = new LabelExample();
            labelExample.createCriteria().andValEqualTo(la.replace(" ", ""));

            Label lb = labelMapper.selectOneByExample(labelExample);
            log.info("-OrganizationServiceImpl -> save --- ?????????????????? -> {}", null != lb);
            LinkLabel memberLink = new LinkLabel();
            LinkLabel linklb = new LinkLabel();
            linklb.setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                    .setId(snowflakeIdWorker53.nextId())
                    .setType(LinkLabelEnum.LINK_LABEL_ORGANIZATION.getCode())
                    .setSourceId(organizationId);
            BeanUtil.copyProperties(linklb, memberLink);
            memberLink.setId(snowflakeIdWorker53.nextId())
                    .setType(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                    .setSourceId(members == null ? memberId : members.getId());
            if (null != lb) {
                linklb.setLabelId(lb.getId());
                memberLink.setLabelId(lb.getId());
                linkLabelAddList(memberLink, insertLinkLabelList);
                linkLabelAddList(linklb, insertLinkLabelList);
            } else {
                log.info("-OrganizationServiceImpl -> save --- ???????????????????????????????????????????????? -");
                long lableId = snowflakeIdWorker53.nextId();
                Label lab = new Label();
                lab.setId(lableId)
                        .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                        .setVal(la.replace(" ", ""))
                        .setCreateTime(LocalDateTime.now());
                // todo batch insert
                labelMapper.insertSelective(lab);
                linklb.setLabelId(lableId);
                memberLink.setLabelId(lableId);
                insertLinkLabelList.add(memberLink);
                insertLinkLabelList.add(linklb);
            }
        }
        linkLabelMapper.batchInsert(insertLinkLabelList);
        log.info("OrganizationServiceImpl -> consoleSave -- request ??????logMemberId{} ---", memberId);
        request.setAttribute("logMemberId", memberId);
        log.info("OrganizationServiceImpl -> save -> ?????????????????????????????? ????????????????????????????????????????????? ");
        List<String> qualifications = organizationDto.getQualifications();
        String substring = null;
        if (qualifications != null) {
            substring = String.join(",", qualifications);
        }
        List<Long> industryIds = null;
        if (CollUtil.isEmpty(organizationDto.getIndustryIds()) && organizationDto.getIndustryId() != null) {
            industryIds = new ArrayList<>();
            industryIds.add(organizationDto.getIndustryId());
        } else {
            industryIds = organizationDto.getIndustryIds();
        }

        String result = null;

        if (entity == null) {
            organization.setId(organizationId)
                    .setCreateTime(LocalDateTime.now())
                    .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                    .setBelong(organizationDto.getBelong())
                    .setType(type)
                    .setName(organizationDto.getName())
                    .setAddressAreaId(organizationDto.getAddressAreaId())
                    .setTechScaleId(organizationDto.getTechScaleId())
                    .setAddressCityId(organizationDto.getAddressCityId())
                    .setAddressCityName(organizationDto.getAddressCityName())
                    .setLicense(organizationDto.getLicense())
                    .setAddressAreaName(organizationDto.getAddressAreaName())
                    .setAddressProvinceName(organizationDto.getAddressProvinceName())
                    .setAddressProvinceId(organizationDto.getAddressProvinceId())
                    .setSignTime(organizationDto.getSignTime())
                    .setScaleId(organizationDto.getScaleId())
                    .setIndustryRemark(organizationDto.getIndustryRemark())
                    .setSummary(organizationDto.getSummary())
                    .setVerifyStatus(OrganizationEnum.VERIFY_STATUS_UNVERIFIED.getValue())
                    .setResponsibleMemberId(memberId);
            customOrganizationMapper.insert(organization);
            //??????????????????id
            editIndustry(industryIds, organizationId);
            saveOrUpdate(members, member, organizationDto, type, organizationId, substring, memberId, industryIds);
            result = "????????????,????????????";
        } else {
            //??????????????????id
            editIndustry(industryIds, entity.getId());
            saveOrUpdate(members, member, organizationDto, type, entity.getId(), substring, memberId, industryIds);
            result = "????????????,??????????????????";
        }
        //????????????
        publishEvent(organization.getResponsibleMemberId(), organization.getType());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(UpdateOriganizationDto updateOriganizationDto) {
        log.info("OrganizationServiceImpl -> update -> start -> req:{}", updateOriganizationDto);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andIdEqualTo(updateOriganizationDto.getOperator())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(OrganizaitonErrorEnum.USERNOTFOUD_ORGANIZATION_ERROR);
        }
        baseOrganizationSave(updateOriganizationDto.getLabel());
        Long organizationId = member.getOrganizationId();
        if (organizationId == null) {
            throw new BusinessException("????????????id?????????");
        }
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
        //?????? ??????????????? ?????????0-????????????1-??????
        updateOriganizationDto.setName(null);
        // ????????? ?????? ????????????????????? ?????????0-????????????1-??????
        if (OrganizationEnum.TYPE_LABORATORY.getValue().equals(organization.getType())) {
            updateOriganizationDto.setBelong(null);
        }

        log.info("OrganizationServiceImpl -> update -- ?????????????????? ---");
        HashSet<String> labels = new HashSet<>();
        String[] label = updateOriganizationDto.getLabel();
        for (String labe : label) {
            String trim = labe.trim();
            if ("".equals(trim)) {
                continue;
            }
            if (labe.length() > 16) {
                throw new BusinessException("??????????????????16?????????");
            }
            labels.add(trim);
        }
        //??????????????? ?????? ????????????
        updateOrDelLinkLabel(labels.toArray(new String[0]), member.getId(), organizationId);

        List<String> qualifications = updateOriganizationDto.getQualifications();
        String substring = null;
        if (qualifications != null) {
            substring = String.join(",", qualifications);
        }
        List<Long> industryIds = updateOriganizationDto.getIndustryIds();

        Organization updateOrganization = new Organization();
        updateOrganization.setId(organizationId)
                .setCreateTime(LocalDateTime.now())
                .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                .setBelong(updateOriganizationDto.getBelong())
                .setName(updateOriganizationDto.getName())
                .setAddressAreaId(updateOriganizationDto.getAddressAreaId())
                .setTechScaleId(updateOriganizationDto.getTechScaleId())
                .setAddressCityId(updateOriganizationDto.getAddressCityId())
                .setAddressCityName(updateOriganizationDto.getAddressCityName())
                .setLicense(updateOriganizationDto.getLicense())
                .setAddressAreaName(updateOriganizationDto.getAddressAreaName())
                .setAddressProvinceName(updateOriganizationDto.getAddressProvinceName())
                .setAddressProvinceId(updateOriganizationDto.getAddressProvinceId())
                .setSignTime(updateOriganizationDto.getSignTime())
                .setScaleId(updateOriganizationDto.getScaleId())
                .setIndustryRemark(updateOriganizationDto.getIndustryRemark())
                .setSummary(updateOriganizationDto.getSummary());
        organizationMapper.updateByPrimaryKeySelective(updateOrganization);
        //???????????? ??????id
        editIndustry(industryIds, organizationId);
        //???????????? ??????id
        editIndustry(industryIds, member.getId());


        Member updateMember = new Member();
        updateMember.setId(member.getId())
                .setPositionId(updateOriganizationDto.getPositionId())
                .setQualifications(substring)
                .setLicense(updateOriganizationDto.getLicense())
                .setEducationId(updateOriganizationDto.getEducationId())
                .setPhone(updateOriganizationDto.getPhone())
                .setEmail(updateOriganizationDto.getEmail())
                .setName(updateOriganizationDto.getCreateMan())
                .setUnionId(updateOriganizationDto.getUnionId())
                .setBelong(updateOriganizationDto.getBelong());
        memberMapper.updateByPrimaryKeySelective(updateMember);

        log.info("OrganizationServiceImpl -> update -> end");

        //??????????????????
        publishEventComponent.publishEvent(new MemberUpdateEvent(
                MemberUpdateEventParam.builder()
                        .memberId(member.getId())
                        .education(updateOriganizationDto.getEducationId() == null ? null : Optional.ofNullable(selectConfigMapper.selectByPrimaryKey(updateOriganizationDto.getEducationId())).orElse(new SelectConfig()).getVal())
                        .memberPosition(updateOriganizationDto.getPositionId() == null ? null : Optional.ofNullable(selectConfigMapper.selectByPrimaryKey(updateOriganizationDto.getPositionId())).orElse(new SelectConfig()).getVal())
                        .memberUpdateEnum(MemberUpdateEventParam.MemberUpdateEventEnum.UPDATE_ORGANIZATION)
                        .build()
        ));
        return "????????????";
    }

    /**
     * ??????????????? ?????? ????????????
     */
    private void updateOrDelLinkLabel(String[] labels, Long memberId, Long organizationId) {
        //???????????? ??????
        LinkLabelExample example = new LinkLabelExample();
        example.createCriteria()
                .andSourceIdIn(Arrays.asList(memberId, organizationId))
                .andTypeIn(Arrays.asList(3, 4))
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<LinkLabel> linkLabels = linkLabelMapper.selectByExample(example);
        //key:LinkLabel.labelId  value:(key:LinkLabel.type  value:LinkLabel.id)
        Map<Long, Map<Integer, Long>> linkLabelMap = linkLabels.stream().collect(Collectors.groupingBy(
                LinkLabel::getLabelId,
                Collectors.toMap(LinkLabel::getType, LinkLabel::getId, (oldVal, newVal) -> newVal)
        ));

        List<LinkLabel> insertLinkLabelList = new ArrayList<>(labels.length);
        for (String la : labels) {
            LabelExample labelExample = new LabelExample();
            labelExample.createCriteria().andValEqualTo(la.replace(" ", ""));

            Label lb = labelMapper.selectOneByExample(labelExample);
            log.info("-OrganizationServiceImpl -> update --- ?????????????????? -> {}", null != lb);
            LinkLabel memberLink = new LinkLabel();
            LinkLabel linklb = new LinkLabel();
            linklb.setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                    .setId(snowflakeIdWorker53.nextId())
                    .setType(LinkLabelEnum.LINK_LABEL_ORGANIZATION.getCode())
                    .setSourceId(organizationId);
            BeanUtil.copyProperties(linklb, memberLink);
            memberLink.setId(snowflakeIdWorker53.nextId())
                    .setType(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                    .setSourceId(memberId);
            if (null != lb) {
                linklb.setLabelId(lb.getId());
                memberLink.setLabelId(lb.getId());

                if (linkLabelMap.containsKey(lb.getId())) {
                    linkLabelMap.remove(lb.getId());
                } else {
                    insertLinkLabelList.add(memberLink);
                    insertLinkLabelList.add(linklb);
                }
            } else {
                log.info("-OrganizationServiceImpl -> update --- ???????????????????????????????????????????????? -");
                long lableId = snowflakeIdWorker53.nextId();
                Label lab = new Label();
                lab.setId(lableId)
                        .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                        .setVal(la.replace(" ", ""))
                        .setCreateTime(LocalDateTime.now());
                // todo batch insert
                labelMapper.insertSelective(lab);
                linklb.setLabelId(lableId);
                memberLink.setLabelId(lableId);
                insertLinkLabelList.add(memberLink);
                insertLinkLabelList.add(linklb);
            }
        }


        //??????????????????ids
        List<Long> deleteIds = linkLabelMap.values().stream().flatMap((item) -> item.values().stream()).collect(Collectors.toList());

        log.info("OrganizationServiceImpl -> updateOrDelLinkLabel -> ??????????????? size:{}  ???????????? size:{}", insertLinkLabelList.size(), deleteIds.size());
        //?????????????????????
        if (CollUtil.isNotEmpty(insertLinkLabelList)) {
            linkLabelMapper.batchInsert(insertLinkLabelList);
        }
        //????????????
        if (CollUtil.isNotEmpty(deleteIds)) {
            LinkLabel delLinkLabel = new LinkLabel();
            delLinkLabel.setDelFlag(ConstantEnum.DELETED.getByte());
            LinkLabelExample delExample = new LinkLabelExample();
            delExample.createCriteria()
                    .andIdIn(deleteIds);
            linkLabelMapper.updateByExampleSelective(delLinkLabel, delExample);
        }
    }

    private void linkLabelAddList(LinkLabel linklb, List<LinkLabel> insertLinkLabelList) {
        LinkLabelExample linkLabelExample = new LinkLabelExample();
        linkLabelExample.createCriteria()
                .andSourceIdEqualTo(linklb.getId())
                .andTypeEqualTo(linklb.getType())
                .andLabelIdEqualTo(linklb.getLabelId())
                .andDelFlagEqualTo(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue());
        LinkLabel orelation = linkLabelMapper.selectOneByExample(linkLabelExample);
        if (orelation == null) {
            insertLinkLabelList.add(linklb);
        }
    }

    private void saveOrUpdate(Member members,
                              Member member,
                              OrganizationDto organizationDto,
                              int type,
                              Long organizationId,
                              String substring,
                              Long memberId,
                              List<Long> industryIds) {
        if (members == null) {
            memberInit(member, organizationDto, type, organizationId, substring);
            member.setPassword(Md5Util.getCrypt("123456"))
                    .setId(memberId);
            memberMapper.insertSelective(member);
            //??????????????????id
            editIndustry(industryIds, memberId);
        } else {
            MemberExample memberExample = new MemberExample();
            memberExample.createCriteria().andIdEqualTo(members.getId());
            memberInit(members, organizationDto, type, organizationId, substring);
            memberMapper.updateByExampleSelective(members, memberExample);
            //??????????????????id
            editIndustry(industryIds, members.getId());
        }
    }

    private void memberInit(Member member, OrganizationDto organizationDto, int type, Long organizationId, String substring) {
        member.setLevel(0)
                .setPositionId(organizationDto.getPositionId())
                .setQualifications(substring)
                .setLicense(organizationDto.getLicense())
                .setVerifyStatus(type == 1 ? MemberEnum.VERIFY_STATUS_SUCCESS.getValue() : MemberEnum.VERIFY_STATUS_CLOSE.getValue())
                //?????????0-????????????1-??????
                .setRole(type == 1 ? MemberEnum.ROLE_ADMINISTRATOR.getValue() : MemberEnum.ROLE_ORDINARY.getValue())
                .setEducationId(organizationDto.getEducationId())
                .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                .setPhone(organizationDto.getPhone())
                .setPushStatus(0)
                .setCreateTime(LocalDateTime.now())
                .setEmail(organizationDto.getEmail())
                .setName(organizationDto.getCreateMan())
                .setOrganizationId(organizationId)
                .setUnionId(organizationDto.getUnionId())
                .setBelong(organizationDto.getBelong());
    }

    /**
     * ??????????????????????????????
     */
    public void baseOrganizationSave(String[] label) {
        if (label != null) {
            for (String match : label) {
                boolean fa = VerifyMatch.containsAll(match);
                if (fa) {
                    throw new BusinessException(OrganizaitonErrorEnum.CHAR_ORGANIZATION_ERROR);
                }
            }
        }

    }


    /**
     * ?????????????????????????????????    ???????????? ????????????????????????????????????????????????????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String consoleSave(OrganizationDto organizationDto, HttpServletRequest request, int type) {
        //int minute = LocalDate.now().lengthOfYear() * 24 * 60;
        Long salesmanId = organizationDto.getSalesmanId();
        if (salesmanId == null) {
            throw new BusinessException(OrganizaitonErrorEnum.USERNOTFOUD_ORGANIZATION_ERROR);
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andPhoneEqualTo(organizationDto.getPhone());
        Member members = memberMapper.selectOneByExample(memberExample);
        if (members != null && members.getOrganizationId() != null) {
            log.info("OrganizationServiceImpl -> consoleSave -- ??????????????????????????? --");
            throw new BusinessException(OrganizaitonErrorEnum.INSERT_ORGANIZATION_NOTEXISE);
        }
        baseOrganizationSave(organizationDto.getLabel());
        log.info("OrganizationServiceImpl -> consoleSave -- ??????????????? --");
        SalesmanExample salesmanExample = new SalesmanExample();
        salesmanExample.createCriteria().andIdEqualTo(organizationDto.getSalesmanId());
        Salesman salesman = salesmanMapper.selectOneByExample(salesmanExample);
        log.info("OrganizationServiceImpl -> consoleSave -- salesman !=null ---");
        if (salesman == null) {
            throw new BusinessException(OrganizaitonErrorEnum.USERNOTFOUD_ORGANIZATION_ERROR);
        }
        log.info("OrganizationServiceImpl -> consoleSave -- request ??????logMemberId{} ---", salesman.getId());
        request.setAttribute("logMemberId", salesman.getId());
        byte flag = OrganizationEnum.DELETE_FLAG_NO.getValue().byteValue();
        long organizationId = snowflakeIdWorker53.nextId();
        long memberId = snowflakeIdWorker53.nextId();
        Organization entity = customOrganizationMapper.findByName(organizationDto.getName(), type, organizationDto.getBelong());
        log.info("OrganizationServiceImpl -> consoleSave -- ?????????????????? -- type {}---", type);
        Organization organization = new Organization();
        Member member = new Member();
        switch (type) {
            case 0:
                break;
            case 1:
                if (members == null) {
                    member.setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());
                } else {
                    members.setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue());
                }
                log.info("OrganizationServiceImpl -> save -- ?????? entity --!=null case [1] ---");
                break;
            default:
                throw new BusinessException(OrganizaitonErrorEnum.INSERT_ORGANIZATION_ERROR);
        }
        log.info("OrganizationServiceImpl -> save -- ?????? entity --!=null case [0] ---");
        log.info("OrganizationServiceImpl -> save -- ?????????????????? ---");
        List<LinkLabel> insertLinkLabelList = new ArrayList<>();
        HashSet<String> labels = new HashSet<>();
        String[] label = organizationDto.getLabel();
        for (String labe : label) {
            String trim = labe.trim();
            if ("".equals(trim)) {
                continue;
            }
            if (labe.length() > 16) {
                throw new BusinessException("??????????????????16?????????");
            }
            labels.add(trim);
        }
        for (String la : labels) {

            LabelExample labelExample = new LabelExample();
            labelExample.createCriteria().andValEqualTo(la.replace(" ", ""));
            LinkLabel linklb = new LinkLabel();
            Label lb = labelMapper.selectOneByExample(labelExample);
            log.info("-OrganizationServiceImpl -> save --- ?????????????????? -> {}", null != lb);
            LinkLabel memberLink = new LinkLabel();

            linklb.setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                    .setId(snowflakeIdWorker53.nextId())
                    .setType(LinkLabelEnum.LINK_LABEL_ORGANIZATION.getCode())
                    .setSourceId(organizationId);
            BeanUtil.copyProperties(linklb, memberLink);
            memberLink.setId(snowflakeIdWorker53.nextId()).setType(LinkLabelEnum.LINK_LABEL_MEMBER.getCode());
            memberLink.setSourceId(members == null ? memberId : members.getId());

            if (null != lb) {
                linklb.setLabelId(lb.getId());
                memberLink.setLabelId(lb.getId());
                linkLabelAddList(memberLink, insertLinkLabelList);
                linkLabelAddList(linklb, insertLinkLabelList);
            } else {
                log.info("-OrganizationServiceImpl -> save --- ???????????????????????????????????????????????? -");
                Label lab = new Label();
                long lableId = snowflakeIdWorker53.nextId();
                lab.setId(lableId)
                        .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                        .setVal(la.replace(" ", ""))
                        .setCreateTime(LocalDateTime.now());
                // todo batch insert
                labelMapper.insertSelective(lab);

                linklb.setLabelId(lableId);
                memberLink.setLabelId(lableId);
                insertLinkLabelList.add(linklb);
                insertLinkLabelList.add(memberLink);
            }
        }
        linkLabelMapper.batchInsert(insertLinkLabelList);
        List<String> qualifications = organizationDto.getQualifications();
        String substring = null;
        if (qualifications != null) {
            substring = qualifications.stream().collect(Collectors.joining(","));
        }
        LinkOrganizationSalesman linkOrganizationSalesman = new LinkOrganizationSalesman();
        linkOrganizationSalesman.setDelFlag(flag)
                .setId(snowflakeIdWorker53.nextId())
                .setOrganizationId(organizationId).setType(0)
                .setSalesmanId(salesman.getId())
                .setOrganizationName(organizationDto.getName())
                .setSalesmanName(salesman.getName());
        // linkOrganizationSalesmanMapper.insertSelective(linkOrganizationSalesman);
        //log.info("OrganizationServiceImpl -> consoleSave -> ???????????????????????????????????? ");

        log.info("OrganizationServiceImpl -> consoleSave -> ?????????????????????????????? ????????????????????????????????????????????? ");
        List<Long> industryIds = null;
        if (CollUtil.isEmpty(organizationDto.getIndustryIds()) && organizationDto.getIndustryId() != null) {
            industryIds = new ArrayList<>();
            industryIds.add(organizationDto.getIndustryId());
        } else {
            industryIds = organizationDto.getIndustryIds();
        }
        String result = null;
        if (entity == null) {
            organization.setId(organizationId)
                    .setCreateTime(LocalDateTime.now())
                    .setDelFlag(flag)
                    .setBelong(organizationDto.getBelong())
                    .setType(type)
                    .setName(organizationDto.getName())
                    .setAddressAreaId(organizationDto.getAddressAreaId())
                    .setVerifyReason("????????????")
                    .setAddressCityId(organizationDto.getAddressCityId())
                    .setAddressCityName(organizationDto.getAddressCityName())
                    .setAddressAreaName(organizationDto.getAddressAreaName())
                    .setAddressProvinceName(organizationDto.getAddressProvinceName())
                    .setAddressProvinceId(organizationDto.getAddressProvinceId())
                    .setSignTime(organizationDto.getSignTime())
                    .setScaleId(organizationDto.getScaleId())
                    .setIndustryRemark(organizationDto.getIndustryRemark())
                    .setSummary(organizationDto.getSummary())
                    .setSalesmanId(organizationDto.getSalesmanId())
                    .setResponsibleMemberId(memberId)
                    .setBelong(organizationDto.getBelong())
                    .setVerifyStatus(OrganizationEnum.VERIFY_STATUS_UNVERIFIED.getValue())
                    .setTechScaleId(organizationDto.getTechScaleId());
            customOrganizationMapper.insert(organization);
            editIndustry(industryIds, organizationId);

            consoleSave(members, member, organizationDto, substring, organizationId, memberId, linkOrganizationSalesman, industryIds, false);
            return "????????????,?????????";

        } else {
            editIndustry(industryIds, entity.getId());
            consoleSave(members, member, organizationDto, substring, entity.getId(), memberId, linkOrganizationSalesman, industryIds, true);
            result = "????????????,??????????????????";
        }
        //????????????
        publishEvent(organization.getResponsibleMemberId(), organization.getType());
        return result;

    }

    private void consoleSave(Member members,
                             Member member,
                             OrganizationDto organizationDto,
                             String substring,
                             Long organizationId,
                             Long memberId,
                             LinkOrganizationSalesman linkOrganizationSalesman,
                             List<Long> industryIds,
                             boolean isEditIndustry) {
        if (members == null) {
            log.info("OrganizationServiceImpl -> consoleSave -> ??????????????????,??????????????????????????? ");

            memberInit(member, organizationDto, 0, organizationId, substring);

            member.setVerifyStatus(MemberEnum.VERIFY_STATUS_CLOSE.getValue())
                    .setPassword(Md5Util.getCrypt("123456"))
                    .setId(memberId);
            memberMapper.insertSelective(member);
            linkOrganizationSalesman.setMemberId(memberId).setMemberName(organizationDto.getCreateMan());
            linkOrganizationSalesmanMapper.insertSelective(linkOrganizationSalesman);
            editIndustry(industryIds, memberId);
        } else {
            MemberExample memberExample = new MemberExample();
            memberExample.createCriteria().andIdEqualTo(members.getId());

            memberInit(members, organizationDto, 0, organizationId, substring);
            member.setVerifyStatus(MemberEnum.VERIFY_STATUS_CLOSE.getValue());

            memberMapper.updateByExampleSelective(members, memberExample);

            editIndustry(isEditIndustry ? organizationDto.getIndustryIds() : industryIds, members.getId());
            linkOrganizationSalesman.setMemberId(members.getId()).setMemberName(members.getName());
            LinkOrganizationSalesmanExample linkOrganizationSalesmanExample = new LinkOrganizationSalesmanExample();
            linkOrganizationSalesmanExample.createCriteria().andSalesmanIdEqualTo(organizationDto.getSalesmanId()).andMemberIdEqualTo(members.getId());
            LinkOrganizationSalesman linkOrganizationSalesmans = linkOrganizationSalesmanMapper.selectOneByExample(linkOrganizationSalesmanExample);
            if (linkOrganizationSalesmans == null) {
                linkOrganizationSalesmanMapper.insertSelective(linkOrganizationSalesman);
            }
        }
    }


    /**
     * ?????????????????????????????????
     */
    @Override
    public ArrayList<Map> findOrganizationByBelong(String belong, Integer type) {
        byte flag = OrganizationEnum.DELETE_FLAG_NO.getValue().byteValue();
        if (StringUtils.isEmpty(belong) || type == null) {
            throw new BusinessException(BaseEnum.VERIFY);
        }
        log.info("OrganizationController -> findOrganizationByBelong -> : belong={}", belong);
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andBelongEqualTo(belong).andTypeEqualTo(type).andDelFlagEqualTo(flag);
        List<Organization> organizations = organizationMapper.selectByExample(organizationExample);
        ArrayList<Map> objects = new ArrayList<>();
        log.info("OrganizationController -> findOrganizationByBelong -> ????????????/??????");
        organizations.stream().forEach(x -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("organizationId", x.getId());
            map.put("name", x.getName());
            objects.add(map);
        });
        return objects;
    }

    @Override
    /**
     * ???????????????????????????????????????????????????
     */
    public ReturnOrganizationDto findByOrganizationId(Long id) {
//        Organization organization = organizationMapper.selectByPrimaryKey(id);
        log.info("OrganizationController -> findByOrganizationId -> start -> id -> {}", id);
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria()
                .andIdEqualTo(id)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
//        else {
//            if (organization.getDelFlag() != 0) {
//                throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
//            }
        log.info("OrganizationController -> findByOrganizationId -> ?????????????????????");
        ReturnOrganizationDto returnOrganizationDto = new ReturnOrganizationDto();
        LinkLabelExample linkLabelExample = new LinkLabelExample();
        linkLabelExample.createCriteria().andSourceIdEqualTo(id).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<LinkLabel> linkLabels = linkLabelMapper.selectByExample(linkLabelExample);
        List<Long> labelIds = linkLabels.stream().map(LinkLabel::getLabelId).collect(Collectors.toList());

        String qualifications = organization.getQualifications();
        if (StringUtils.isNotBlank(qualifications)) {
            List<String> qualification = Arrays.asList(qualifications.split(","));
            returnOrganizationDto.setQualifications(qualification);
        }
        String license = organization.getLicense();
        if (StringUtils.isNotBlank(license)) {
            List<String> licenses = Arrays.asList(license.split(","));
            returnOrganizationDto.setLicense(licenses);
        }

        //????????????
        if (CollUtil.isNotEmpty(labelIds)) {
            LabelExample example = new LabelExample();
            example.createCriteria()
                    .andIdIn(labelIds)
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            List<Label> labels = labelMapper.selectByExample(example);
            List<LabelDTO> labelDTOList = labels.stream()
                    .map(label -> {
                        LabelDTO labelDTO = new LabelDTO(label.getId(), label.getVal());
                        return labelDTO;
                    })
                    .collect(Collectors.toList());
            returnOrganizationDto.setLabelList(labelDTOList);
        }

        //????????????id??????
        LinkSelectExample linkSelectExample = new LinkSelectExample();
        linkSelectExample.createCriteria()
                .andSourceIdEqualTo(organization.getId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<LinkSelect> linkSelects = linkSelectMapper.selectByExample(linkSelectExample);
        if (CollUtil.isNotEmpty(linkSelects)) {
            List<Long> ids = linkSelects.stream().map(LinkSelect::getSelectConfigId).collect(Collectors.toList());
            //??????????????????
            SelectConfigExample example = new SelectConfigExample();
            example.createCriteria()
                    .andIdIn(ids)
                    //?????? 0-?????? 1-??????
                    .andStatusEqualTo(0)
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            List<SelectConfig> selectConfigs = selectConfigMapper.selectByExample(example);
            List<SelectConfigDTO> list = selectConfigs.stream()
                    .map(item -> {
                        SelectConfigDTO dto = new SelectConfigDTO(item.getId(), item.getVal());
                        return dto;
                    })
                    .collect(Collectors.toList());
            returnOrganizationDto.setIndustryList(list);
        }


        //????????????Id
        returnOrganizationDto.setLabel(labelIds)
                .setEmail(Optional.ofNullable(memberMapper.selectByPrimaryKey(organization.getResponsibleMemberId())).orElse(new Member()).getEmail())
                .setAddressProvinceName(organization.getAddressProvinceName())
                .setAddressAreaId(organization.getAddressAreaId())
                .setFundsId(organization.getFundsId())
                .setSummary(organization.getSummary())
                .setAddressProvinceId(organization.getAddressProvinceId())
                .setAddressAreaName(organization.getAddressAreaName())
                .setAddressCityId(organization.getAddressCityId())
                .setAddressCityName(organization.getAddressCityName())
                .setBelong(organization.getBelong())
                .setName(organization.getName())
                .setIndustryId(organization.getIndustryId())
                .setTechScaleId(organization.getTechScaleId());
        baseSelectConfigReturn(organization.getIndustryId(), organization.getScaleId(), organization.getTechScaleId(), returnOrganizationDto);
        return returnOrganizationDto;
//        }
    }


    public void baseSelectConfigReturn(Long industryId, Long scaleId, Long techScaleId, ReturnOrganizationDto returnOrganizationDto) {
        SelectConfigExample selectConfigExample = new SelectConfigExample();
        if (null != industryId) {
            log.info("OrganizationServiceImpl -> baseSelectConfigReturn -> industryId -> {}", industryId);
            selectConfigExample.or()
                    .andIdEqualTo(industryId);
        }
        if (null != scaleId) {
            log.info("OrganizationServiceImpl -> baseSelectConfigReturn -> scaleId -> {} ", scaleId);
            selectConfigExample.or()
                    .andIdEqualTo(scaleId);
        }
        if (null != techScaleId) {
            log.info("OrganizationServiceImpl -> baseSelectConfigReturn -> techScaleId -> {} ", techScaleId);
            selectConfigExample.or()
                    .andIdEqualTo(techScaleId);
        }
        int size = CollUtil.size(selectConfigExample.getOredCriteria());
        log.info("size -> {}", size);
        if (CollUtil.isEmpty(selectConfigExample.getOredCriteria())) {
            log.info("baseSelectConfigReturn -> baseSelectConfigReturn -> ??????????????? ????????????");
            return;
        }

        selectConfigExample.limit(3);
        List<SelectConfig> list = selectConfigMapper.selectByExample(selectConfigExample);

        String industryName = list.stream()
                .filter(item -> item.getId().equals(industryId))
                .map(SelectConfig::getVal)
                .findFirst()
                .orElse(null);
        returnOrganizationDto.setIndustryName(industryName);
        returnOrganizationDto.setIndustryId(industryId);

        String scaleName = list.stream()
                .filter(item -> item.getId().equals(scaleId))
                .map(SelectConfig::getVal)
                .findFirst()
                .orElse(null);
        returnOrganizationDto.setScaleName(scaleName);
        returnOrganizationDto.setScaleId(scaleId);

        String techScaleName = list.stream()
                .filter(item -> item.getId().equals(techScaleId))
                .map(SelectConfig::getVal)
                .findFirst()
                .orElse(null);
        returnOrganizationDto.setTechScaleName(techScaleName);
        returnOrganizationDto.setTechScaleId(techScaleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        log.info("OrganizationServiceImpl -> delete -> ?????????????????? {}", id);
        byte flag = OrganizationEnum.DELETE_FLAG_YES.getValue().byteValue();
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andIdEqualTo(id);
        Organization organization = organizationMapper.selectOneByExample(example);
        if (organization != null) {
            organization.setDelFlag(flag);
            log.info("OrganizationServiceImpl -> delete -> ?????????????????? ");
            return organizationMapper.updateByExampleSelective(organization, example);
        } else {
            return 0;
        }
    }

    /**
     * ???????????????   ????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authOrganization(AuthOrganizationDto authOrganizationDto) {
        log.info("OrganizationServiceImpl -> authOrganization -> ??????????????? {}", authOrganizationDto);
        byte flag = 0;
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(authOrganizationDto.getMemberId()).andDelFlagEqualTo(flag);
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(OrganizaitonErrorEnum.USERNOTFOUD_ORGANIZATION_ERROR);
        } else {
            if (member.getRole() != 2) {
                throw new BusinessException(BaseEnum.UN_ROOT_ERROR);
            }
            Long organizationId = member.getOrganizationId();
            if (organizationId == null) {
                throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
            }
            OrganizationExample organizationExample = new OrganizationExample();
            organizationExample.createCriteria().andDelFlagEqualTo(flag).andIdEqualTo(organizationId);
            Organization organization = organizationMapper.selectOneByExample(organizationExample);
            if (organization == null) {
                throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
            }
            List<String> imgs = authOrganizationDto.getImgs();
            String urls = imgs.stream().collect(Collectors.joining(","));
            organization.setLicense(urls).setStatus(1);
            organizationMapper.updateByExampleSelective(organization, organizationExample);
        }
    }

    /**
     * ???????????????
     */
    @Override
    public OrganizationSpaceRes findOrganizationSpace(Long organizationId) {
        OrganizationSpaceRes organizationSpaceRes = new OrganizationSpaceRes();

        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria()
                .andIdEqualTo(organizationId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
        BeanUtil.copyProperties(organization, organizationSpaceRes);
        // ????????????
        List<LinkLabelVo> labelList = customLinkLabelMapper.findLinkLabelBySourceId(organizationId);
        organizationSpaceRes.setLabels(labelList);

        organizationSpaceRes.setName(organization.getName());
        organizationSpaceRes.setStatus(organization.getStatus());
        if (OrganizationEnum.TYPE_LABORATORY.getValue().equals(organization.getType())) {
            List<TutorInfoReq> organizationMember = customMemberMapper.findOrganizationMember(organizationId);
            organizationSpaceRes.setTutors(organizationMember);

            ServerExample serverExample = new ServerExample();
            serverExample.createCriteria()
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                    .andOrganizedIdPartyBEqualTo(organizationId)
                    .andStatusEqualTo(OrganizationEnum.VERIFY_STATUS_VERIFIED.getValue());
            long serverSize = serverMapper.countByExample(serverExample);
            organizationSpaceRes.setServerSize((int) serverSize);
            MemberExample memberExample = new MemberExample();
            memberExample.createCriteria()
                    .andOrganizationIdEqualTo(organizationId)
                    .andTeacherIdIsNotNull()
                    .andRoleEqualTo(MemberEnum.ROLE_ASSISTANT.getValue());
            List<Member> members = memberMapper.selectByExample(memberExample);
            organizationSpaceRes.setMembers(members.stream().map(this::memberConvertToOrganizationMember).collect(Collectors.toList()));
        } else {
            organizationSpaceRes.setFunds(organization.getFundsId());
            String addressCityName = organization.getAddressCityName();
            String addressAreaName = organization.getAddressAreaName();
            StringBuilder stringBuilder = new StringBuilder();
            if (StringUtils.isNotBlank(addressCityName)) {
                stringBuilder.append(addressCityName);
            }
            if (StringUtils.isNotBlank(addressAreaName)) {
                stringBuilder.append(addressAreaName);
            }
            organizationSpaceRes.setAddress(stringBuilder.toString());

            SelectConfigExample selectConfigExample = new SelectConfigExample();
            log.info("OrganizationServiceImpl -> findOrganizationSpace -> ???????????????");
            if (organization.getScaleId() != null) {
                selectConfigExample.createCriteria().andIdEqualTo(organization.getScaleId());
                SelectConfig selectConfig = selectConfigMapper.selectOneByExample(selectConfigExample);
                if (selectConfig != null) {
                    organizationSpaceRes.setScale(selectConfig.getVal());
                }
                selectConfigExample.clear();
            }
            log.info("OrganizationServiceImpl -> findOrganizationSpace -> ??????????????? ");
            if (organization.getTechScaleId() != null) {
                selectConfigExample.createCriteria().andIdEqualTo(organization.getTechScaleId());
                SelectConfig selectConfig = selectConfigMapper.selectOneByExample(selectConfigExample);
                if (selectConfig != null) {
                    organizationSpaceRes.setTechScale(selectConfig.getVal());
                }
            }
            MemberExample memberExample = new MemberExample();
            memberExample.createCriteria().andOrganizationIdEqualTo(organizationId).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            List<Member> members = memberMapper.selectByExample(memberExample);
            organizationSpaceRes.setMembers(members.stream().map(this::memberConvertToOrganizationMember).collect(Collectors.toList()));
        }
        organizationSpaceRes.setAddress(organization.getAddressDetail());
        return organizationSpaceRes;
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public List<TutorInfoReq> findOrganizationMembers(Long organizationId) {
        byte flag = 0;
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andIdEqualTo(organizationId).andDelFlagEqualTo(flag);
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
        List<TutorInfoReq> organizationMember = customMemberMapper.findOrganizationMembers(organizationId);
        return organizationMember;
    }

    @Override
    public OrganizationBaseDataRep findOrganizationData(Long organizationId) {
        byte flag = 0;
        List<OrganizationBaseDataRep> organizationBaseData = customOrganizationMapper.findOrganizationData(organizationId);
        if (CollUtil.isEmpty(organizationBaseData)) {
            throw new BusinessException(OrganizaitonErrorEnum.MESSAGE_AHIECEMENTEQUIPMENT_ERROR);
        }
        OrganizationDataRep organizationDataRep = new OrganizationDataRep();
        organizationDataRep.setBelong(organizationBaseData.get(0).getBelong());
        organizationDataRep.setId(organizationBaseData.get(0).getId());
        organizationDataRep.setName(organizationBaseData.get(0).getName());
        organizationDataRep.setScaleId(organizationBaseData.get(0).getScaleId());
        ArrayList<java.lang.String> val = new ArrayList<>();
        organizationBaseData.stream().filter((x) -> x.getVal() != null).forEach(x -> {
            val.add(x.getVal());
        });
        organizationDataRep.setValName(val);
        Long scaleId = organizationBaseData.get(0).getScaleId();
        if (scaleId != null) {
            SelectConfigExample selectConfigExample = new SelectConfigExample();
            selectConfigExample.createCriteria().andIdEqualTo(scaleId).andDelFlagEqualTo(flag);
            SelectConfig selectConfig = selectConfigMapper.selectOneByExample(selectConfigExample);
            if (selectConfig != null) {
                organizationDataRep.setScaleName(selectConfig.getVal());
            }
        }
        return organizationDataRep;
    }


    /**
     * ??????????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long generateCompany(GenerateCompanyReq generateCompanyReq) {
        log.info("-OrganizationServiceImpl -> modifyCompany --- params -> {}", generateCompanyReq);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(generateCompanyReq.getMemberId());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }

        Long organizationId = member.getOrganizationId();
        if (organizationId != null) {
            return organizationId;
        }

        long id = snowflakeIdWorker53.nextId();
        Organization organization = new Organization();
        organization.setId(id)
                .setType(OrganizationEnum.TYPE_COMPANY.getValue())
                .setSignTime(LocalDateTime.now())
                .setDelFlag(OrganizationEnum.STATUS_UNAUTHORIZED.getValue().byteValue())
                .setIndustryId(member.getIndustryId())
                .setResponsibleMemberId(member.getId())
                .setCreateTime(LocalDateTime.now())
                .setName(member.getPhone() + "?????????");
        organizationMapper.insertSelective(organization);
        //??????????????????  ??????????????? ?????? ??????id??????
        createCompanyUpdateMember(organization.getResponsibleMemberId(), null, MemberEnum.ROLE_ADMINISTRATOR.getValue(), id);
        //????????????
        publishEvent(organization.getResponsibleMemberId(), organization.getType());
        return id;
    }

    /**
     * ???????????? ?????????0-???????????????1-?????????2-????????? 3 ???????????? 4-??????
     * ??????????????????  ??????????????? ?????? ??????id??????
     *
     * @param memberId       ??????id
     * @param role           ??????id
     * @param organizationId ??????id
     */
    private void createCompanyUpdateMember(Long memberId, String email, Integer role, Long organizationId) {
        Member member = new Member();
        member.setId(memberId);
        member.setRole(role);
        member.setEmail(email);
        member.setOrganizationId(organizationId);
        int i = memberMapper.updateByPrimaryKeySelective(member);
        if (i != 1) {
            throw new BusinessException(MemberExceptionEnum.ROLE_SET);
        }
    }

    /**
     * ???????????????????????? ??????
     *
     * @param memberId ??????id
     * @param type     ?????????type ?????????0-????????????1-??????
     */
    private void publishEvent(Long memberId, Integer type) {
        if (type == null) {
            return;
        }
        //??????????????????
        publishEventComponent.publishEvent(new MemberUpdateEvent(
                MemberUpdateEventParam.builder()
                        .memberId(memberId)
                        .memberUpdateEnum(MemberUpdateEventParam.MemberUpdateEventEnum.CREATE_ORGANIZATION)
                        //?????? 0-?????? 1-???????????? 2-?????????
                        .memberType(type == 0 ? 2 : 1)
                        .build()
        ));
    }


    /**
     * ????????????????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyCompany(ModifyCompanyReq modifyCompanyReq) {
        log.info("OrganizationServiceImpl -> modifyCompany -> start req{}", modifyCompanyReq);
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria()
                .andIdEqualTo(modifyCompanyReq.getId())
                .andDelFlagEqualTo(OrganizationEnum.VERIFY_STATUS_UNVERIFIED.getValue().byteValue());
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }

        //????????????
        editLinkLabel(modifyCompanyReq.getLabel(), organization.getId());

        String lincenses = null;
        List<String> license = modifyCompanyReq.getLicense();
        if (CollUtil.isNotEmpty(license)) {
            lincenses = license.stream().collect(Collectors.joining(","));
        }
        List<Long> industryIds = null;
        if (CollUtil.isEmpty(modifyCompanyReq.getIndustryIds()) && modifyCompanyReq.getIndustryId() != null) {
            industryIds = new ArrayList<>();
            industryIds.add(modifyCompanyReq.getIndustryId());
        } else {
            industryIds = modifyCompanyReq.getIndustryIds();
        }
        Organization update = new Organization();
        BeanUtils.copyProperties(modifyCompanyReq, update);
        update.setLicense(lincenses);
        update.setId(null);

        editIndustry(industryIds, organization.getId());
        editIndustry(industryIds, organization.getResponsibleMemberId());

        if (!update.equals(new Organization())) {
            organizationMapper.updateByExampleSelective(update, organizationExample);
        }

        if (StrUtil.isNotBlank(modifyCompanyReq.getEmail())) {
            createCompanyUpdateMember(organization.getResponsibleMemberId(), modifyCompanyReq.getEmail(), null, null);
        }
        log.info("OrganizationServiceImpl -> modifyCompany -> end");
    }

    /**
     * ????????????
     *
     * @param labelArray ????????????
     * @param sourceId   sourceId
     */
    private void editLinkLabel(String[] labelArray, Long sourceId) {
        if (labelArray == null || labelArray.length == 0) {
            return;
        }
        checkLabelArray(labelArray);

        //???????????? ??????
        LinkLabelExample example = new LinkLabelExample();
        example.createCriteria()
                .andSourceIdEqualTo(sourceId)
                .andTypeIn(Arrays.asList(3, 4))
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<LinkLabel> linkLabels = linkLabelMapper.selectByExample(example);
        //key:LinkLabel.labelId  value:(key:LinkLabel.type  value:LinkLabel.id)
        Map<Long, Map<Integer, Long>> linkLabelMap = linkLabels.stream().collect(Collectors.groupingBy(
                LinkLabel::getLabelId,
                Collectors.toMap(LinkLabel::getType, LinkLabel::getId, (oldVal, newVal) -> newVal)
        ));

        List<LinkLabel> insertLinkLabelList = new ArrayList<>();
        for (String labelStr : labelArray) {
            LabelExample labelExample = new LabelExample();
            labelExample.createCriteria().andValEqualTo(labelStr.replace(" ", ""));
            Label label = labelMapper.selectOneByExample(labelExample);
            LinkLabel linklb = new LinkLabel();
            LinkLabel memberLink = new LinkLabel();
            boolean isNew = false;
            if (null != label) {
                linklb.setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                        .setId(snowflakeIdWorker53.nextId())
                        .setType(4)
                        .setLabelId(label.getId())
                        .setSourceId(sourceId);

                if (!isExists(linkLabelMap, linklb)) {
                    insertLinkLabelList.add(linklb);
                }
            } else {
                Label lab = new Label();
                long lableId = snowflakeIdWorker53.nextId();
                lab.setId(lableId)
                        .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                        .setVal(labelStr.replace(" ", ""))
                        .setCreateTime(LocalDateTime.now());
                // todo batch insert
                labelMapper.insertSelective(lab);
                linklb.setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                        .setId(snowflakeIdWorker53.nextId())
                        .setType(4)
                        .setLabelId(lableId)
                        .setSourceId(sourceId);
                insertLinkLabelList.add(linklb);
                isNew = true;
            }
            BeanUtils.copyProperties(linklb, memberLink);
            memberLink.setId(snowflakeIdWorker53.nextId())
                    .setType(3);
            if (isNew || !isExists(linkLabelMap, memberLink)) {
                insertLinkLabelList.add(memberLink);
            }
        }

        //??????????????????ids
        List<Long> deleteIds = linkLabelMap.values().stream().flatMap((item) -> item.values().stream()).collect(Collectors.toList());

        log.info("OrganizationServiceImpl -> editLinkLabel -> ??????????????? size:{}  ???????????? size:{}", insertLinkLabelList.size(), deleteIds.size());
        //?????????????????????
        if (CollUtil.isNotEmpty(insertLinkLabelList)) {
            linkLabelMapper.batchInsert(insertLinkLabelList);
        }
        //????????????
        if (CollUtil.isNotEmpty(deleteIds)) {
            LinkLabel delLinkLabel = new LinkLabel();
            delLinkLabel.setDelFlag(ConstantEnum.DELETED.getByte());
            LinkLabelExample delExample = new LinkLabelExample();
            delExample.createCriteria()
                    .andIdIn(deleteIds);
            linkLabelMapper.updateByExampleSelective(delLinkLabel, delExample);
        }
    }

    private boolean isExists(Map<Long, Map<Integer, Long>> linkLabelMap, LinkLabel linklb) {
        if (CollUtil.isNotEmpty(linkLabelMap)) {
            if (linkLabelMap.containsKey(linklb.getLabelId())) {
                Map<Integer, Long> integerLongMap = linkLabelMap.get(linklb.getLabelId());
                if (integerLongMap.containsKey(linklb.getType())) {
                    integerLongMap.remove(linklb.getType());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<MemberOrganizationTypeDO> queryOrganizationType(List<Long> memberIds) {
        return customOrganizationMapper.queryOrganizationType(memberIds);
    }

    /**
     * ?????? label ????????????????????????
     *
     * @param label
     */
    private void checkLabelArray(String[] label) {
        for (String match : label) {
            boolean fa = VerifyMatch.containsAll(match);
            if (fa) {
                throw new BusinessException(OrganizaitonErrorEnum.CHAR_ORGANIZATION_ERROR);
            }
            if (match.length() > 16) {
                throw new BusinessException(OrganizaitonErrorEnum.LABELNO_LONG_ERROR);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCompany(AddCompanyReq req) {
        Long memberId = req.getMemberId();
        Boolean hasOrganization = memberService.hasOrganization(memberId);
        if (hasOrganization) {
            throw new BusinessException("??????????????????");
        }

        String[] label = req.getLabel();

        List<LinkLabel> insertLinkLabelList = new ArrayList<>();
        List<Label> insertLabelList = new ArrayList<>();
        long id = snowflakeIdWorker53.nextId();
        if (label != null) {

            checkLabelArray(label);

            for (String lbs : label) {
                LabelExample labelExample = new LabelExample();
                labelExample.createCriteria().andValEqualTo(lbs.replace(" ", ""));
                Label lb = labelMapper.selectOneByExample(labelExample);
                log.info("OrganizationServiceImpl -> addCompany --- ?????????????????? -> {}", null != lb);

                LinkLabel linklb = new LinkLabel();
                LinkLabel memberLink = new LinkLabel();

                // ???????????? todo
                log.info("OrganizationServiceImpl -> modifyCompany --- ????????????????????? -> {}", null != lb);

                linklb.setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                        .setId(snowflakeIdWorker53.nextId())
                        .setType(LinkLabelEnum.LINK_LABEL_ORGANIZATION.getCode())
                        .setSourceId(id);
                if (null != lb) {
                    // ?????????????????????
                    linklb.setLabelId(lb.getId());
                    insertLinkLabelList.add(linklb);
                } else {
                    // ??????????????????????????????
                    Label lab = new Label();
                    long lableId = snowflakeIdWorker53.nextId();
                    lab.setId(lableId)
                            .setDelFlag(OrganizationEnum.TYPE_LABORATORY.getValue().byteValue())
                            .setVal(lbs.replace(" ", ""))
                            .setCreateTime(LocalDateTime.now());
                    insertLabelList.add(lab);

                    linklb.setLabelId(lableId);
                    insertLinkLabelList.add(linklb);
                }

                BeanUtil.copyProperties(linklb, memberLink);
                memberLink.setId(snowflakeIdWorker53.nextId())
                        .setType(LinkLabelEnum.LINK_LABEL_MEMBER.getCode())
                        .setSourceId(id);
                insertLinkLabelList.add(memberLink);
            }
            linkLabelMapper.batchInsert(insertLinkLabelList);

            if (CollUtil.isNotEmpty(insertLabelList)) {
                labelMapper.batchInsert(insertLabelList);
            }
        }
        String lincenses = null;
        List<String> license = req.getLicense();
        if (CollUtil.isNotEmpty(license)) {
            lincenses = license.stream().collect(Collectors.joining(","));
        }


        // ??????
        List<Long> industryList = req.getIndustryList();
        // ?????????????????????
        List<LinkSelect> industryAddList = new ArrayList<>();
        if (CollUtil.isNotEmpty(industryList)) {
            for (Long industryId : industryList) {
                industryAddList.add(LinkSelect.builder()
                        .id(snowflakeIdWorker53.nextId())
                        .sourceId(id)
                        .type(6)
                        .selectConfigId(industryId)
                        .build());
            }
        } else {
            // ??????????????? ???????????????????????????
            LinkSelectExample linkSelectExample = new LinkSelectExample();
            linkSelectExample.createCriteria()
                    .andTypeEqualTo(6)
                    .andSourceIdEqualTo(req.getMemberId())
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            List<LinkSelect> industries = linkSelectMapper.selectByExample(linkSelectExample);
            for (LinkSelect linkSelect : industries) {
                linkSelect.setId(snowflakeIdWorker53.nextId());
                linkSelect.setCreateTime(LocalDateTime.now());
                linkSelect.setCreateBy(req.getMemberId());
                linkSelect.setSourceId(id);
                industryAddList.add(linkSelect);
            }
        }
        if (CollUtil.isNotEmpty(industryAddList)) {
            linkSelectMapper.batchInsert(industryAddList);
        }

        Organization organization = new Organization();
        BeanUtil.copyProperties(req, organization);

        organization.setId(id)
                .setType(OrganizationEnum.TYPE_COMPANY.getValue())
                .setSignTime(LocalDateTime.now())
                .setLicense(lincenses)
                .setDelFlag(OrganizationEnum.STATUS_UNAUTHORIZED.getValue().byteValue())
                .setResponsibleMemberId(req.getMemberId());
        organizationMapper.insertSelective(organization);
        //??????????????????  ??????????????? ?????? ??????id??????
        createCompanyUpdateMember(organization.getResponsibleMemberId(), req.getEmail(), MemberEnum.ROLE_ADMINISTRATOR.getValue(), id);
        //????????????
        publishEvent(organization.getResponsibleMemberId(), organization.getType());
    }

    @Override
    public ServicePage<List<EnterpriseMemberRes>> findEnterpriseMember(EnterpriseMemberReq req) {
        log.info("OrganizationServiceImpl -> findEnterpriseMember -> start -> params -> {}", req);
        ServicePage<List<EnterpriseMemberRes>> res = new ServicePage<>();
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andOrganizationIdEqualTo(req.getOrganizationId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        if (req.getMemberId() != null) {
            criteria.andIdNotEqualTo(req.getMemberId());
        }
        Page<Member> page = PageUtil.start(req, () -> memberMapper.selectByExample(memberExample));
        BeanUtil.copyProperties(page, res);
        List<Member> result = page.getResult();
        List<EnterpriseMemberRes> memberResList = new ArrayList<>();
        result.forEach(member -> {
            EnterpriseMemberRes memberRes = new EnterpriseMemberRes();
            BeanUtil.copyProperties(member, memberRes);
            memberResList.add(memberRes);
        });
        res.setResult(memberResList);
        log.info("OrganizationServiceImpl -> findEnterpriseMember -> end");
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signOutEnterprise(SignOutEnterpriseReq req) {
        log.info("OrganizationServiceImpl -> signOutEnterprise -> start -> params -> {}", req);
        Organization organization = organizationMapper.selectByPrimaryKey(req.getOrganizationId());
        if (!organization.getType().equals(OrganizationEnum.TYPE_COMPANY.getValue())) {
            log.error("OrganizationServiceImpl -> signOutEnterprise -> error -> {} ", OrganizaitonErrorEnum.NOT_IN_CHARGE.getMessage());
            throw new BusinessException(OrganizaitonErrorEnum.NOT_IN_CHARGE);
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andOrganizationIdEqualTo(req.getOrganizationId())
                .andIdNotEqualTo(req.getMemberId());
        List<Member> members = memberMapper.selectByExample(memberExample);
        memberExample.clear();

        memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andIdEqualTo(req.getMemberId());
        Member member = memberMapper.selectOneByExample(memberExample);
        memberExample.clear();
        if (ObjectUtil.isEmpty(member)){
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        log.info("OrganizationServiceImpl -> signOutEnterprise -> judge member is enterprise charge ? -> {}", !organization.getResponsibleMemberId().equals(req.getMemberId()));
        if (organization.getResponsibleMemberId().equals(req.getMemberId())) {
            log.info("OrganizationServiceImpl -> signOutEnterprise ->  judge List<Member> is null ? -> {}", CollUtil.isEmpty(members));
            if (members.size() <= 0) {
                log.error("OrganizationServiceImpl -> signOutEnterprise -> error -> {} ", OrganizaitonErrorEnum.ENTERPRISE_IS_NULL.getMessage());
                throw new BusinessException(OrganizaitonErrorEnum.ENTERPRISE_IS_NULL);
            }

            if (!member.getOrganizationId().equals(req.getOrganizationId())) {
                log.error("OrganizationServiceImpl -> signOutEnterprise -> error -> {} ", OrganizaitonErrorEnum.APPOINT_MEMBER_ORGANIZATION.getMessage());
                throw new BusinessException(OrganizaitonErrorEnum.APPOINT_MEMBER_ORGANIZATION);
            }
            if (null == req.getSourceId()) {
                log.error("OrganizationServiceImpl -> signOutEnterprise -> error -> {}", OrganizaitonErrorEnum.APPOINT_ADMIN.getMessage());
                throw new BusinessException(OrganizaitonErrorEnum.APPOINT_ADMIN);
            } else {
                memberExample.createCriteria().andIdEqualTo(req.getSourceId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
                Member sourceMember = memberMapper.selectOneByExample(memberExample);
                if (ObjectUtil.isEmpty(sourceMember)){
                    throw new BusinessException("???????????????????????????");
                }
                ServerExample serverExample = new ServerExample();
                serverExample.createCriteria().andMemberIdPartyAEqualTo(req.getMemberId());
                serverMapper.updateByExampleSelective(Server.builder().memberIdPartyA(req.getSourceId()).build() , serverExample);

                member.setOrganizationId(null);
                member.setBelong(null);
                member.setRole(MemberEnum.ROLE_ORDINARY.getValue());
                organization.setResponsibleMemberId(req.getSourceId());
                organizationMapper.updateByPrimaryKeySelective(organization);
                memberMapper.updateByPrimaryKey(member);
                memberMapper.updateByPrimaryKeySelective(Member.builder().id(req.getSourceId()).role(MemberEnum.ROLE_ADMINISTRATOR.getValue()).build());
                log.info("OrganizationServiceImpl -> signOutEnterprise -> end");
                return;
            }
        }
        InviteExample inviteExample = new InviteExample();
        inviteExample.createCriteria().andInvitePhoneEqualTo(member.getPhone());
        inviteMapper.updateByExampleSelective(Invite.builder().delFlag(ConstantEnum.DELETED.getByte()).build() , inviteExample);

        ServerExample serverExample = new ServerExample();
        serverExample.createCriteria().andMemberIdPartyAEqualTo(req.getMemberId());
        serverMapper.updateByExampleSelective(Server.builder().memberIdPartyA(organization.getResponsibleMemberId()).build() , serverExample);

        member.setOrganizationId(null);
        member.setBelong(null);
        member.setRole(MemberEnum.ROLE_ORDINARY.getValue());
        memberMapper.updateByPrimaryKey(member);
        //todo ??????????????????????????????????????????
        log.info("OrganizationServiceImpl -> signOutEnterprise -> end");
    }

    @Override
    public Boolean hasOrganization(Long memberId) {
        return null;
    }

    @Override
    public OrganizationDetailRes findOrganizationDetail(Long id) {
        log.info("OrganizationServiceImpl -> findOrganizationDetail -> params -> {}",id);
        OrganizationDetail organizationDetail = customOrganizationMapper.findOrganizationDetail(id);
        log.info("OrganizationServiceImpl -> findOrganizationDetail -> ???????????????????????? -> {}",ObjectUtil.isEmpty(organizationDetail));
        if (ObjectUtil.isEmpty(organizationDetail)) {
            log.error("OrganizationServiceImpl -> findOrganizationDetail -> error -> {}",OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR.getMessage());
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
        OrganizationDetailRes res = new OrganizationDetailRes();
        BeanUtil.copyProperties(organizationDetail, res);
        String industry = organizationDetail.getIndustry();
        String label = organizationDetail.getLabel();
        if (StrUtil.isNotEmpty(industry)) {
            res.setIndustry(Arrays.asList(industry.split(",")));
        }
        if (StrUtil.isNotEmpty(label)) {
            res.setLabel(Arrays.asList(label.split(",")));
        }
        log.info("OrganizationServiceImpl -> findOrganizationDetail -> end");
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void kickOutMember(KickOutMemberReq req) {
        log.info("OrganizationServiceImpl -> kickOutMember -> start -> params -> {}",req);
        MemberDetailed adminMember = customMemberMapper.getDetailedById(req.getAdminId());
        log.info("OrganizationServiceImpl -> kickOutMember -> ????????????????????? -> {}",null == adminMember);
        if (null == adminMember) {
            log.error("OrganizationServiceImpl -> kickOutMember -> error -> ????????????????????????");
            throw new BusinessException("????????????????????????");
        }
        Organization organization = organizationMapper.selectByPrimaryKey(Long.parseLong(adminMember.getOrganizationId()));
        log.info("OrganizationServiceImpl -> kickOutMember -> ??????????????? -> {}",!organization.getResponsibleMemberId().equals(req.getAdminId()));
        if (!organization.getResponsibleMemberId().equals(req.getAdminId())) {
            log.error("OrganizationServiceImpl -> kickOutMember -> error -> ???????????????????????????????????????");
            throw new BusinessException("???????????????????????????????????????");
        }
        MemberDetailed memberInfo = customMemberMapper.getDetailedById(req.getMemberId());
        log.info("OrganizationServiceImpl -> kickOutMember -> ?????????????????????????????? -> {}",null == memberInfo);
        if (null == memberInfo) {
            log.error("OrganizationServiceImpl -> kickOutMember -> error -> ???????????????????????????????????????");
            throw new BusinessException("???????????????????????????????????????");
        }
        log.info("OrganizationServiceImpl -> kickOutMember -> ???????????? -> {}",!adminMember.getOrganizationId().equals(memberInfo.getOrganizationId()));
        if (!adminMember.getOrganizationId().equals(memberInfo.getOrganizationId())) {
            log.error("OrganizationServiceImpl -> kickOutMember -> error -> ????????????????????????????????????");
            throw new BusinessException("????????????????????????????????????");
        }
        //?????????????????????
        ServerExample serverExample = new ServerExample();
        serverExample.createCriteria().andMemberIdPartyAEqualTo(memberInfo.getId());
        serverMapper.updateByExampleSelective(Server.builder().memberIdPartyA(adminMember.getId()).build() , serverExample);

        //??????????????????
        InviteExample inviteExample = new InviteExample();
        inviteExample.createCriteria().andInvitePhoneEqualTo(memberInfo.getPhone());
        inviteMapper.updateByExampleSelective(Invite.builder().delFlag(ConstantEnum.DELETED.getByte()).build() , inviteExample);

        customMemberMapper.kickOutMember(memberInfo.getId());
        log.info("OrganizationServiceImpl -> kickOutMember -> end");
    }

    private void editIndustry(List<Long> industry, Long sourceId) {
        industrySyncComponent.industrySync(industry, sourceId);
    }

    /**
     * member ??????????????? OrganizationMember ??????
     *
     * @param member {@link Member }
     * @return {@link OrganizationMember}
     */
    private OrganizationMember memberConvertToOrganizationMember(Member member) {
        OrganizationMember res = new OrganizationMember();
        BeanUtil.copyProperties(member, res);
        return res;
    }

}
