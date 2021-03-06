package com.qidao.application.service.member.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.member.Subscribe;
import com.qidao.application.entity.member.SubscribeExample;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.organization.OrganizationExample;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.member.CustomSubscribeMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.member.SubscribeMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.SelectConfigEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.member.MemberInfoRes;
import com.qidao.application.model.member.subscribe.*;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.model.organization.enums.OrganizaitonErrorEnum;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.member.SubscribeService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * {
 * ????????????????????????Controller/service???????????????
 * ??????????????????Controller?????????
 * ????????????????????????private??????
 * }
 * @date : 2020/12/14 3:49 PM
 */
@Slf4j
@Service("SubscribeService")
public class SubscribeServiceImpl implements SubscribeService {

    @Resource
    private SubscribeMapper subscribeMapper;

    @Resource
    private CustomSubscribeMapper customSubscribeMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private SelectConfigMapper selectConfigMapper;

    @Resource
    private OrganizationMapper organizationMapper;

    @Autowired
    private MemberService memberService;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker;

//    private final static String subscribeCacheKeyPrefix = "com:qidao:api:service:subscribe:cardid:";

//    private final static String subscribeEditLockKeyPrefix = "com:qidao:api:service:subscribe:idlock:";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubscribeEditDTO updateAllSubscribe(SubscribeUpdateAllReq req) {
        log.info("SubscribeServiceImpl -> updateAllSubscribe -> SubscribeUpdateAllReq req : {}", req);
        Subscribe updateCriteriaSubscribe = Subscribe.builder()
                .subscribeId(req.getSubscribeId())
                .build();
        Subscribe updateSubscribe = new Subscribe();
        BeanUtils.copyProperties(req, updateSubscribe);
        SubscribeDTO subscribeDTO = updateAll(updateSubscribe, updateCriteriaSubscribe);
        SubscribeEditDTO subscribeEditDTO = SubscribeEditDTO.builder()
                .sqlCount(subscribeDTO.getSqlCount())
                .success(subscribeDTO.getSuccess())
                .build();
        log.info("SubscribeServiceImpl -> updateAllSubscribe -> Return -> SubscribeEditDTO : {}", subscribeEditDTO);
        return subscribeEditDTO;
    }

    //TODO (ashiamd)[2020-12-2X] @Cacheable??????????????????=>????????????????????????????????????????????????
    @Override
    public SubscribeDTO getFollowList(SubscribeGetFollowListReq req) {
        log.info("SubscribeServiceImpl -> getFollowList -> SubscribeGetFollowListReq req : {}", req);
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeEnum.TYPE_FOLLOW.getValue())
                .build();
        BeanUtil.copyProperties(req, subscribe);
        SubscribeDTO subscribeDTO = getList(subscribe, req.getOffset(), req.getLimit());
        log.info("SubscribeServiceImpl -> getFollowList -> Return -> SubscribeDTO : {}", subscribeDTO);
        return subscribeDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubscribeDTO addFollow(SubscribeAddFollowReq req) {
        log.info("SubscribeServiceImpl -> addFollow -> SubscribeAddFollowReq req : {}", req);
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeEnum.TYPE_FOLLOW.getValue())
                .build();
        BeanUtil.copyProperties(req, subscribe);
        SubscribeDTO subscribeDTO = add(subscribe);
        log.info("SubscribeServiceImpl -> addFollow -> Return -> SubscribeDTO : {}", subscribeDTO);
        return subscribeDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubscribeDTO deleteFollow(SubscribeDeleteFollowReq req) {
        log.info("SubscribeServiceImpl -> deleteFollow -> SubscribeDeleteFollowReq req : {}", req);
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeEnum.TYPE_FOLLOW.getValue())
                .build();
        BeanUtil.copyProperties(req, subscribe);
        SubscribeDTO subscribeDTO = delete(subscribe);
        log.info("SubscribeServiceImpl -> deleteFollow -> Return -> SubscribeDTO : {}", subscribeDTO);
        return subscribeDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubscribeDTO deleteAllFollow(SubscribeDeleteAllFollowReq req) {
        log.info("SubscribeServiceImpl -> deleteAllFollow -> SubscribeDeleteAllFollowReq req : {}", req);
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeEnum.TYPE_FOLLOW.getValue())
                .build();
        BeanUtil.copyProperties(req, subscribe);
        SubscribeDTO subscribeDTO = deleteAll(subscribe);
        log.info("SubscribeServiceImpl -> deleteAllFollow -> Return -> SubscribeDTO : {}", subscribeDTO);
        return subscribeDTO;
    }

    //TODO (ashiamd)[2020-12-2X] @Cacheable??????????????????=>????????????????????????????????????????????????
    @Override
    public SubscribeDTO getBlockList(SubscribeGetBlockListReq req) {
        log.info("SubscribeServiceImpl -> getBlockList -> SubscribeGetBlockListReq req : {}", req);
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeEnum.TYPE_BLOCK.getValue())
                .build();
        BeanUtil.copyProperties(req, subscribe);
        SubscribeDTO subscribeDTO = getList(subscribe, req.getOffset(), req.getLimit());
        log.info("SubscribeServiceImpl -> getBlockList -> Return -> SubscribeDTO : {}", subscribeDTO);
        return subscribeDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubscribeDTO addBlock(SubscribeAddBlockReq req) {
        log.info("SubscribeServiceImpl -> addBlock -> SubscribeAddBlockReq req : {}", req);
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeEnum.TYPE_BLOCK.getValue())
                .build();
        BeanUtil.copyProperties(req, subscribe);
        SubscribeDTO subscribeDTO = add(subscribe);
        log.info("SubscribeServiceImpl -> addBlock -> Return -> SubscribeDTO : {}", subscribeDTO);
        return subscribeDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubscribeDTO deleteBlock(SubscribeDeleteBlockReq req) {
        log.info("SubscribeServiceImpl -> deleteBlock -> SubscribeDeleteBlockReq req : {}", req);
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeEnum.TYPE_BLOCK.getValue())
                .build();
        BeanUtil.copyProperties(req, subscribe);
        SubscribeDTO subscribeDTO = delete(subscribe);
        log.info("SubscribeServiceImpl -> deleteBlock -> Return -> SubscribeDTO : {}", subscribeDTO);
        return subscribeDTO;
    }

    /**
     * <p>
     * <pre>
     *  ??????????????????List??????{
     *      ?????????????????? ??????or???????????????????????? "??????????????????" ?????????
     *  }
     * </pre>
     * </p>
     *
     * @param subscribe ????????????{??????/??????id ; ???????????????/?????? ; ???????????????/??????}
     * @param offset    ????????????
     * @param limit     ????????????
     */
    private SubscribeDTO getList(Subscribe subscribe, Integer offset, Integer limit) {
        log.info("SubscribeServiceImpl -> getList -> Subscribe subscribe : {}, Integer offset : {}, Integer limit : {}", subscribe, offset, limit);
        SubscribeDTO dto = new SubscribeDTO();
        Page<Subscribe> page = null;
        SubscribeEnum subscribeType = SubscribeEnum.getSubscribeType(subscribe.getSubscribeType());
        switch (Objects.requireNonNull(subscribeType)) {
            case SUBSCRIBE_TYPE_MEMBER:
            case SUBSCRIBE_TYPE_ORGANIZATION: {
                SubscribeExample subscribeExample = new SubscribeExample();
                SubscribeExample.Criteria criteria = subscribeExample.createCriteria();
                criteria.andMemberIdEqualTo(subscribe.getMemberId())
                        .andTypeEqualTo(subscribe.getType())
                        .andSubscribeTypeEqualTo(subscribe.getSubscribeType())
                        .andDelFlagEqualTo((byte) SubscribeEnum.DELETE_FLAG_NO.getValue());
                page = PageHelper.startPage(offset, limit).doSelectPage(() -> subscribeMapper.selectByExample(subscribeExample));
                break;
            }
            case SUBSCRIBE_TYPE_LABORATORY: {
                page = PageHelper.startPage(offset, limit).doSelectPage(() -> customSubscribeMapper.getSubscribeWithOrganizationType(subscribe,SubscribeEnum.DELETE_FLAG_NO.getValue(), OrganizationEnum.TYPE_LABORATORY.getValue()));
                break;
            }
            default:
                break;
        }
        boolean pageIsNull = ObjectUtil.isNull(page);
        log.info("SubscribeServiceImpl -> getList -> boolean isNull : {}", pageIsNull);
        if (pageIsNull) {
            log.info("SubscribeServiceImpl -> getList -> Return -> SubscribeDTO : {}", dto);
            return dto;
        }
        ServicePage<List<SubscribeRes>> servicePage = new ServicePage<>();
        servicePage.setEndRow(page.getEndRow());
        servicePage.setPageNum(page.getPageNum());
        servicePage.setPages(page.getPages());
        servicePage.setPageSize(page.getPageSize());
        servicePage.setStartRow(page.getStartRow());
        servicePage.setTotal(page.getTotal());
        List<Subscribe> subscribeList = page.getResult();
        boolean subscribeListIsNotEmpty = ObjectUtil.isNotEmpty(subscribeList);
        log.info("SubscribeServiceImpl -> getList -> boolean subscribeListIsNotEmpty : {}", subscribeListIsNotEmpty);
        if (subscribeListIsNotEmpty) {
            List<SubscribeRes> subscribeResList = new ArrayList<>();
            for (Subscribe item : subscribeList) {
                SubscribeRes subscribeRes = new SubscribeRes();
                BeanUtils.copyProperties(item, subscribeRes);
                subscribeResList.add(subscribeRes);
            }
            servicePage.setResult(subscribeResList);
            dto.setResList(servicePage);
        }
        log.info("SubscribeServiceImpl -> getList -> Return -> SubscribeDTO : {}", dto);
        return dto;
    }

    /**
     * <p>??????add?????? {<br/>
     * <pre>
     *  ?????????????????? > ????????????????????????????????????????????????
     *
     *  add??????????????????
     *  1. ??????A  ?????? ??????B
     *  2. ??????A  ?????? ??????B??????????????????
     *  3. ??????A  ?????? ??????B
     *  4. ??????A  ?????? ??????B??????????????????
     *  5. ??????A ??????B ?????????
     *  ??????2???4?????????update????????????A????????????B
     *  ??????1???2???4?????????update????????????A????????????B
     *  ?????????????????????3???update???????????????????????????A???????????????B??????????????????????????????????????????update
     *
     * (0) ??????????????? ??????A ??????/??????????????? ????????? ??????A?????????????????????????????????
     * (1) ??????????????????A ??????/?????? ??????B ?????????(??????????????????);
     *    - ?????????????????????subscribe???type????????????(??????????????????null?????????????????????add??????)
     *   (1) ???????????? type = ??????
     *      (1) ????????????add??????????????????
     *          (1) ????????????????????????success=true(?????????????????????updateSuccess?????????????????????)
     *          (2) ?????????????????????????????????????????????????????????????????????update_by
     *      (2) ????????????add???????????????????????????????????????????????????????????????????????????????????????update_by
     *          (1) ?????????type ??????=>????????????????????????????????????update_by
     *          (2) ?????????????????????(1)???????????????????????????
     *   (2) ???????????? type = ??????
     *      (1) ????????????add?????????????????????????????????????????????(??????????????????????????????????????????????????????????????????????????????)???????????????
     *          (1) ??????????????????????????????success=false
     *          (2) ??????????????????type ??????=>????????????????????????????????????update_by
     *      (2) ????????????add????????????????????????????????????
     *          (1) ????????????????????????success=true(?????????????????????updateSuccess?????????????????????)
     *          (2) ????????????????????????????????????????????????update_by
     * (2) ???????????????????????? ?????????/????????????member(??????????????????)???????????????
     * (3) ??????member??????????????????select_config??????(???????????????????????????)???????????????null?????????????????????
     * (4) ??????member????????????????????????organization??????(??????????????????)???????????????null?????????????????????
     *
     * subscribe???type?????????????????????(?????????NULL???)???
     * subscribe???del_flag????????????0(?????????????????????)???
     * subscribe???add?????????FOLLOW??????BLOCK???
     * ??????????????????????????????????????????if?????????
     * boolean addTypeEqualsFollow = existedType == typeFollow;
     * boolean existedTypeEqualFollow = existedType == typeFollow;
     * boolean existedNoBeenDeleted = existedDelFlag == DELETE_FLAG_NO;
     * </pre>
     * }</p>
     *
     * @param subscribe ??????????????? {memeberId ??????Id; subscribeId ?????????/???????????????Id; type ?????????????????????/??????}
     */
    private SubscribeDTO add(Subscribe subscribe) {
        log.info("SubscribeServiceImpl -> add -> Subscribe subscribe : {}", subscribe);
        SubscribeDTO dto = new SubscribeDTO();
        long memberId = subscribe.getMemberId();
        long subscribeId = subscribe.getSubscribeId();
        int subscribeType = subscribe.getSubscribeType();
        boolean memberIdEqualSubscribeId = memberId == subscribeId;
        log.info("SubscribeServiceImpl -> add -> boolean memberIdEqualSubscribeId : {}", memberIdEqualSubscribeId);
        if (memberIdEqualSubscribeId) {
            dto.setSuccess(false);
            dto.setCodeMessageEnum(SubscribeCodeMessageEnum.FAILED_LXAPI_000001);
            log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
            return dto;
        }
        int type = subscribe.getType();
        SubscribeExample subscribeExample = new SubscribeExample();
        SubscribeExample.Criteria subscribeExampleCriteria = subscribeExample.createCriteria();
        subscribeExampleCriteria.andMemberIdEqualTo(memberId)
                .andSubscribeIdEqualTo(subscribeId)
                .andSubscribeTypeEqualTo(subscribeType);
        Subscribe subscribeExisted = subscribeMapper.selectOneByExample(subscribeExample);
        boolean subscribeExistedIsNotNull = ObjectUtil.isNotNull(subscribeExisted);
        log.info("SubscribeServiceImpl -> add -> boolean subscribeExistedIsNotNull : {}", subscribeExistedIsNotNull);
        if (subscribeExistedIsNotNull) {
            Boolean updateSuccess;
            Integer existedType = subscribeExisted.getType();
            Byte existedDelFlag = subscribeExisted.getDelFlag();
            final int typeFollow = SubscribeEnum.TYPE_FOLLOW.getValue();
            final int deleteFlagNo = SubscribeEnum.DELETE_FLAG_NO.getValue();
            boolean addTypeEqualsFollow = type == typeFollow;
            boolean existedTypeEqualFollow = existedType == typeFollow;
            boolean existedNoBeenDeleted = existedDelFlag == deleteFlagNo;
            Subscribe updateCriteriaSubscribe;
            Subscribe updateSubscribe;
            log.info("SubscribeServiceImpl -> add -> boolean existedTypeEqualFollow : {}", existedTypeEqualFollow);
            if (existedTypeEqualFollow) {
                log.info("SubscribeServiceImpl -> add -> boolean addTypeEqualsFollow : {}", addTypeEqualsFollow);
                if (addTypeEqualsFollow) {
                    log.info("SubscribeServiceImpl -> add -> boolean existedNoBeenDeleted : {}", existedNoBeenDeleted);
                    if (existedNoBeenDeleted) {
                        dto.setSuccess(true);
                        log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
                        return dto;
                    }
                    updateCriteriaSubscribe = Subscribe.builder()
                            .memberId(memberId)
                            .subscribeId(subscribeId)
                            .subscribeType(subscribeType)
                            .build();
                    updateSubscribe = Subscribe.builder()
                            .delFlag((byte) deleteFlagNo)
                            .subscribeTime(LocalDateTime.now())
                            .updateBy(memberId)
                            .build();
                    updateSuccess = update(updateSubscribe, updateCriteriaSubscribe).getSuccess();
                    dto.setSuccess(updateSuccess);
                    log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
                    return dto;
                }
                updateCriteriaSubscribe = Subscribe.builder()
                        .memberId(memberId)
                        .subscribeId(subscribeId)
                        .subscribeType(subscribeType)
                        .build();
                updateSubscribe = Subscribe.builder()
                        .type(SubscribeEnum.TYPE_BLOCK.getValue())
                        .subscribeTime(LocalDateTime.now())
                        .updateBy(memberId)
                        .build();
                log.info("SubscribeServiceImpl -> add -> boolean existedNoBeenDeleted : {}", existedNoBeenDeleted);
                if (existedNoBeenDeleted) {
                    updateSuccess = update(updateSubscribe, updateCriteriaSubscribe).getSuccess();
                    dto.setSuccess(updateSuccess);
                    log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
                    return dto;
                }
                updateSubscribe.setDelFlag((byte) deleteFlagNo);
                updateSuccess = update(updateSubscribe, updateCriteriaSubscribe).getSuccess();
                dto.setSuccess(updateSuccess);
                log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
                return dto;
            }
            log.info("SubscribeServiceImpl -> add -> boolean addTypeEqualsFollow : {}", addTypeEqualsFollow);
            if (addTypeEqualsFollow) {
                log.info("SubscribeServiceImpl -> add -> boolean existedNoBeenDeleted : {}", existedNoBeenDeleted);
                if (existedNoBeenDeleted) {
                    dto.setSuccess(false);
                    dto.setCodeMessageEnum(SubscribeCodeMessageEnum.FAILED_LXAPI_000002);
                    log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
                    return dto;
                }
                updateCriteriaSubscribe = Subscribe.builder()
                        .memberId(memberId)
                        .subscribeId(subscribeId)
                        .subscribeType(subscribeType)
                        .build();
                updateSubscribe = Subscribe.builder()
                        .type(SubscribeEnum.TYPE_FOLLOW.getValue())
                        .delFlag((byte) deleteFlagNo)
                        .subscribeTime(LocalDateTime.now())
                        .updateBy(memberId)
                        .build();
                updateSuccess = update(updateSubscribe, updateCriteriaSubscribe).getSuccess();
                dto.setSuccess(updateSuccess);
                log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
                return dto;
            }
            log.info("SubscribeServiceImpl -> add -> boolean existedNoBeenDeleted : {}", existedNoBeenDeleted);
            if (existedNoBeenDeleted) {
                dto.setSuccess(true);
                log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
                return dto;
            }
            updateCriteriaSubscribe = Subscribe.builder()
                    .memberId(memberId)
                    .subscribeId(subscribeId)
                    .subscribeType(subscribeType)
                    .build();
            updateSubscribe = Subscribe.builder()
                    .delFlag((byte) deleteFlagNo)
                    .subscribeTime(LocalDateTime.now())
                    .updateBy(memberId)
                    .build();
            updateSuccess = update(updateSubscribe, updateCriteriaSubscribe).getSuccess();
            dto.setSuccess(updateSuccess);
            log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
            return dto;
        }
        if (subscribeType == SubscribeEnum.SUBSCRIBE_TYPE_MEMBER.getValue()) {
            MemberExample memberExample = new MemberExample();
            MemberExample.Criteria memberExampleCriteria = memberExample.createCriteria();
            memberExampleCriteria.andIdEqualTo(subscribeId)
                    .andDelFlagEqualTo((byte) SubscribeEnum.DELETE_FLAG_NO.getValue());
            MemberInfoRes member = memberService.getMemberByMemberId(subscribeId);
            boolean memberIsNotNull = ObjectUtil.isNotNull(member);
            log.info("SubscribeServiceImpl -> add -> boolean memberIsNotNull : {}", memberIsNotNull);
            if (memberIsNotNull) {
                Long snowflakeId = snowflakeIdWorker.nextId();
                Subscribe subscribeNew = Subscribe.builder()
                        .id(snowflakeId)
                        .createBy(memberId)
                        .memberId(memberId)
                        .subscribeId(subscribeId)
                        .subscribeTime(LocalDateTime.now())
                        .type(type)
                        .subscribeType(subscribeType)
                        .subscribeHeadImg(member.getHeadImage())
                        .subscribeName(member.getName())
                        .subscribePosition(member.getPosition())
                        .subscribeOrganizationName(member.getOrganizationName())
                        .subscribeBelong(member.getBelong())
                        .subscribeEducation(member.getEducation())
                        .build();
                int count = subscribeMapper.insertSelective(subscribeNew);
                dto.setSuccess(count > 0);
                boolean dtoNoSuccess = !dto.getSuccess();
                log.info("SubscribeServiceImpl -> add -> boolean dtoNoSuccess : {}", dtoNoSuccess);
                if (dtoNoSuccess) {
                    dto.setCodeMessageEnum(SubscribeCodeMessageEnum.EXCEPTION_HX000_000007);
                }
            }
            log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
            return dto;
        } else if (subscribeType == SubscribeEnum.SUBSCRIBE_TYPE_ORGANIZATION.getValue()) {
            OrganizationExample organizationExample = new OrganizationExample();
            OrganizationExample.Criteria organizationExampleCriteria = organizationExample.createCriteria();
            organizationExampleCriteria.andIdEqualTo(subscribeId)
                    .andDelFlagEqualTo(OrganizationEnum.DELETE_FLAG_NO.getValue().byteValue());
            Organization organization = organizationMapper.selectOneByExample(organizationExample);
            boolean organizationIsNotNull = ObjectUtil.isNotNull(organization);
            log.info("SubscribeServiceImpl -> add -> boolean organizationIsNotNull : {}", organizationIsNotNull);
            if (organizationIsNotNull) {
                Long snowflakeId = snowflakeIdWorker.nextId();
                Subscribe subscribeNew = Subscribe.builder()
                        .id(snowflakeId)
                        .createBy(memberId)
                        .memberId(memberId)
                        .subscribeId(subscribeId)
                        .subscribeTime(LocalDateTime.now())
                        .type(type)
                        .subscribeType(subscribeType)
                        .subscribeOrganizationName(organization.getName())
                        .subscribeBelong(organization.getBelong())
                        .build();
                int count = subscribeMapper.insertSelective(subscribeNew);
                dto.setSuccess(count > 0);
                boolean dtoNoSuccess = !dto.getSuccess();
                log.info("SubscribeServiceImpl -> add -> boolean dtoNoSuccess : {}", dtoNoSuccess);
                if (dtoNoSuccess) {
                    dto.setCodeMessageEnum(SubscribeCodeMessageEnum.EXCEPTION_HX000_000007);
                }
            }
            log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
            return dto;
        } else {
            dto.setSuccess(false);
            dto.setCodeMessageEnum(SubscribeCodeMessageEnum.FAILED_LXAPI_000004);
            log.info("SubscribeServiceImpl -> add -> Return -> SubscribeDTO : {}", dto);
            return dto;
        }
    }

    /**
     * @param updateSubscribe   ?????????????????? {
     *                          ??????Subscribe??????null??????
     *                          }
     * @param criteriaSubscribe ???????????? {
     *                          memberId ????????????id
     *                          subscribeId ?????????/???????????????id
     *                          subscribeType ?????????????????? 0-?????? 1-????????????(??????)
     *                          type ??????/??????(??????)
     *                          delFlag ??????????????????
     *                          ??????????????????????????????(???null?????????)
     *                          }
     * @return ??????update?????? {
     * ??????memberId???subscribeId???????????????null?????????
     * ??????????????????????????????
     * ps: add???????????????
     * }
     */
    private SubscribeDTO update(Subscribe updateSubscribe, Subscribe criteriaSubscribe) {
        log.info("SubscribeServiceImpl -> update -> Subscribe subscribe : {}, Subscribe criteriaSubscribe : {}", updateSubscribe, criteriaSubscribe);
        SubscribeDTO dto = updateAll(updateSubscribe, criteriaSubscribe);
        log.info("SubscribeServiceImpl -> update -> SubscribeDTO : {}", dto);
        return dto;
    }

    /**
     * <p>??????updateAll?????? {<br/>
     * (1) memberId?????????????????????????????????Subscribe??????(?????????????????????)<br/>
     * (2) memberId?????????????????????????????????????????????/????????????????????????????????????(??????????????????????????????????????????Controller????????????)<br/>
     * (3) type?????????????????????add???delete???????????????"??????????????????"?????????????????????<br/>
     * }
     * </p>
     *
     * @param updateSubscribe   ??????????????????{??????Subscribe??????null??????}
     * @param criteriaSubscribe ???????????? {memberId ????????????id(??????)????????????null?????????????????????;
     *                          subscribeId ?????????/???????????????id;
     *                          subscribeType ?????????????????? 0-?????? 1-????????????(??????),???????????? ??????/????????????????????????
     *                          type ??????/??????(??????)????????????????????? ??????/???????????????????????????;
     *                          delFlag ??????????????????(??????);
     *                          ??????????????????????????????(???null?????????);
     *                          }
     */
    private SubscribeDTO updateAll(Subscribe updateSubscribe, Subscribe criteriaSubscribe) {
        log.info("SubscribeServiceImpl -> updateAll -> Subscribe subscribe : {}, Subscribe criteriaSubscribe : {}", updateSubscribe, criteriaSubscribe);
        SubscribeExample subscribeExample = new SubscribeExample();
        SubscribeExample.Criteria criteria = subscribeExample.createCriteria();
        Long memberId = criteriaSubscribe.getMemberId();
        boolean memberIdIsNotNull = ObjectUtil.isNotNull(memberId);
        log.info("SubscribeServiceImpl -> updateAll -> boolean memberIdIsNotNull : {}", memberIdIsNotNull);
        if (memberIdIsNotNull) {
            criteria.andMemberIdEqualTo(memberId);
        }
        Long subscribeId = criteriaSubscribe.getSubscribeId();
        boolean subscribeIdIsNotNull = ObjectUtil.isNotNull(subscribeId);
        log.info("SubscribeServiceImpl -> updateAll -> boolean subscribeIdIsNotNull : {}", subscribeIdIsNotNull);
        if (subscribeIdIsNotNull) {
            // ??????????????????????????? ??????????????????
            MemberInfoRes member = memberService.getMemberByMemberId(subscribeId);
            updateSubscribe.setSubscribeHeadImg(member.getHeadImage());
            updateSubscribe.setSubscribeName(member.getName());
            updateSubscribe.setSubscribePosition(member.getPosition());
            updateSubscribe.setSubscribeOrganizationName(member.getOrganizationName());
            updateSubscribe.setSubscribeEducation(member.getEducation());
            updateSubscribe.setSubscribeBelong(member.getBelong());

            criteria.andSubscribeIdEqualTo(subscribeId);
        }
        Integer subscribeType = criteriaSubscribe.getSubscribeType();
        boolean subscribeTypeIsNotNull = ObjectUtil.isNotNull(subscribeType);
        log.info("SubscribeServiceImpl -> updateAll -> boolean subscribeTypeIsNotNull : {}", subscribeTypeIsNotNull);
        if (subscribeTypeIsNotNull) {
            criteria.andSubscribeTypeEqualTo(subscribeType);
        }
        Integer type = criteriaSubscribe.getType();
        boolean typeIsNotNull = ObjectUtil.isNotNull(type);
        log.info("SubscribeServiceImpl -> updateAll -> boolean typeIsNotNull : {}", typeIsNotNull);
        if (typeIsNotNull) {
            criteria.andTypeEqualTo(type);
        }
        Byte delFlag = criteriaSubscribe.getDelFlag();
        boolean delFlagIsNotNull = ObjectUtil.isNotNull(delFlag);
        log.info("SubscribeServiceImpl -> updateAll -> boolean delFlagIsNotNull : {}", delFlagIsNotNull);
        if (delFlagIsNotNull) {
            criteria.andDelFlagEqualTo(delFlag);
        }
        int count = subscribeMapper.updateByExampleSelective(updateSubscribe, subscribeExample);
        SubscribeDTO dto = SubscribeDTO.builder()
                .sqlCount(count)
                .success(count > 0)
                .build();
        log.info("SubscribeServiceImpl -> updateAll -> SubscribeDTO : {}", dto);
        return dto;
    }

    /**
     * <p>
     * ????????????delete?????? {<br/>
     * ???????????????deleteAll??????????????????????????????????????????(???????????????????????????subscribeId)<br/>
     * }
     * </p>
     *
     * @param subscribe ?????????????????? {
     *                  memberId ??????id;
     *                  subscribeId ?????????/???????????????id;
     *                  type ??????/??????;
     *                  subscribeType ??????/????????????????????????
     *                  }
     */
    private SubscribeDTO delete(Subscribe subscribe) {
        log.info("SubscribeServiceImpl -> delete -> Subscribe subscribe : {}", subscribe);
        SubscribeDTO dto = deleteAll(subscribe);
        log.info("SubscribeServiceImpl -> delete -> SubscribeDTO : {}", dto);
        return dto;
    }

    /**
     * <p>
     * ????????????deleteAll?????? {<br/>
     * ?????? "??????????????????" ????????????????????? => ?????????????????? "????????????"<br/>
     * }
     * </p>
     *
     * @param subscribe ?????????????????? {
     *                  memberId ??????id;
     *                  subscribeId ?????????/???????????????id (??????);
     *                  type ??????/??????;
     *                  subscribeType ??????/????????????????????????
     *                  }
     */
    private SubscribeDTO deleteAll(Subscribe subscribe) {
        log.info("SubscribeServiceImpl -> deleteAll -> Subscribe subscribe : {}", subscribe);
        Long memberId = subscribe.getMemberId();
        Subscribe criteriaSubscribe = Subscribe.builder()
                .memberId(memberId)
                .subscribeId(subscribe.getSubscribeId())
                .type(subscribe.getType())
                .subscribeType(subscribe.getSubscribeType())
                .delFlag((byte) SubscribeEnum.DELETE_FLAG_NO.getValue())
                .build();
        Subscribe updateSubscribe = Subscribe.builder()
                .build();
        updateSubscribe.setUpdateBy(memberId);
        updateSubscribe.setDelFlag((byte) SubscribeEnum.DELETE_FLAG_YES.getValue());
        SubscribeDTO dto = updateAll(updateSubscribe, criteriaSubscribe);
        boolean dtoGetSqlCountLg0 = 0 <= dto.getSqlCount();
        log.info("SubscribeServiceImpl -> deleteAll -> boolean dtoGetSqlCountLg0 : {}", dtoGetSqlCountLg0);
        if (dtoGetSqlCountLg0) {
            dto.setCodeMessageEnum(SubscribeCodeMessageEnum.FAILED_LXAPI_000003);
        }
        log.info("SubscribeServiceImpl -> deleteAll -> SubscribeDTO : {}", dto);
        return dto;
    }

    /**
     * ??????????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void attentionOrganization(AttentionOrganizationReq attentionOrganizationReq) {
        log.info("SubscribeServiceImpl -> attentionOrganization -> AttentionOrganizationReq : {}", attentionOrganizationReq);
        Long subscribeId = attentionOrganizationReq.getSubscribeId();
        Long memberId = attentionOrganizationReq.getMemberId();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andIdEqualTo(memberId);
        log.info("SubscribeServiceImpl -> attentionOrganization -> ?????????????????????");
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andIdEqualTo(subscribeId);
        Organization organization = organizationMapper.selectOneByExample(organizationExample);
        if (organization == null) {
            throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
        }
        SubscribeExample subscribeExample = new SubscribeExample();
        subscribeExample.createCriteria().andSubscribeIdEqualTo(subscribeId).andMemberIdEqualTo(memberId).andTypeEqualTo(SubscribeEnum.TYPE_FOLLOW.getValue());
        Subscribe subscribe = subscribeMapper.selectOneByExample(subscribeExample);
        log.info("SubscribeServiceImpl -> attentionOrganization -> ????????????????????????");
        if (subscribe == null) {
            Subscribe criteriaSubscribe = Subscribe.builder()
                    .memberId(attentionOrganizationReq.getMemberId()).subscribeTime(LocalDateTime.now())
                    .subscribeId(attentionOrganizationReq.getSubscribeId()).subscribeName(organization.getName())
                    .type(SubscribeEnum.TYPE_FOLLOW.getValue()).subscribeType(SubscribeEnum.TYPE_FOLLOW.getValue())
                    .delFlag((byte) SubscribeEnum.FLAG_FOLLOW.getValue())
                    .build();
            subscribeMapper.insertSelective(criteriaSubscribe);
        } else {
            log.info("SubscribeServiceImpl -> attentionOrganization -> ?????????????????????");
            subscribe.setDelFlag(attentionOrganizationReq.getDelFlag());
            subscribe.setMemberId(attentionOrganizationReq.getMemberId());
            subscribe.setSubscribeId(attentionOrganizationReq.getSubscribeId());
            subscribe.setSubscribeName(attentionOrganizationReq.getSubscribeName());
            subscribeMapper.updateByExampleSelective(subscribe, subscribeExample);
        }
    }


    /**
     * ???????????????????????????????????????
     */
    @Override
    public Boolean findMemberWhetherAttention(Long memberId, Integer type, Long subscribeId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(memberId).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
        }
        if (type == SubscribeEnum.SUBSCRIBE_TYPE_MEMBER.getValue()) {
            memberExample.clear();
            memberExample.createCriteria().andIdEqualTo(subscribeId).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            Member subscribe = memberMapper.selectOneByExample(memberExample);
            if (subscribe == null) {
                throw new BusinessException(MemberExceptionEnum.NOT_FOUND_PERSONAL);
            }
        }
        if (type == SubscribeEnum.SUBSCRIBE_TYPE_ORGANIZATION.getValue()) {
            OrganizationExample organizationExample = new OrganizationExample();
            organizationExample.createCriteria().andIdEqualTo(subscribeId).andDelFlagEqualTo(OrganizationEnum.DELETE_FLAG_NO.getValue().byteValue());
            Organization organization = organizationMapper.selectOneByExample(organizationExample);
            if (organization == null) {
                throw new BusinessException(OrganizaitonErrorEnum.NOFOUND_ORGANIZATION_ERROR);
            }
        }
        SubscribeExample subscribeExample = new SubscribeExample();
        subscribeExample.createCriteria().andSubscribeTypeEqualTo(type).andMemberIdEqualTo(memberId).andSubscribeIdEqualTo(subscribeId).andTypeEqualTo(SubscribeEnum.TYPE_FOLLOW.getValue());
        Subscribe subscribe = subscribeMapper.selectOneByExample(subscribeExample);
        if (subscribe == null) {
            return false;
        } else {
            Byte delFlag = subscribe.getDelFlag();
            if (delFlag.intValue() == SubscribeEnum.DELETE_FLAG_YES.getValue()) {
                return false;
            } else {
                return true;
            }
        }
    }
}
