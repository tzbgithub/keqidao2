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
 * 上面是提供给其他Controller/service调用的方法
 * 中面是提供给Controller的方法
 * 下面是内部使用的private方法
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

    //TODO (ashiamd)[2020-12-2X] @Cacheable不会用先空着=>其实主要不知道命名标准，之后补上
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

    //TODO (ashiamd)[2020-12-2X] @Cacheable不会用先空着=>其实主要不知道命名标准，之后补上
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
     *  通用分页查询List方法{
     *      根据类别获取 关注or屏蔽列表，只获取 "没被逻辑删除" 的条目
     *  }
     * </pre>
     * </p>
     *
     * @param subscribe 查询条件{用户/组织id ; 类别：关注/屏蔽 ; 对象：用户/组织}
     * @param offset    分页页码
     * @param limit     页面容量
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
     * <p>通用add方法 {<br/>
     * <pre>
     *  优先级：屏蔽 > 关注，屏蔽可以覆盖关注，反之不行
     *
     *  add的几种情况：
     *  1. 用户A  关注 用户B
     *  2. 用户A  关注 用户B（逻辑删除）
     *  3. 用户A  屏蔽 用户B
     *  4. 用户A  屏蔽 用户B（逻辑删除）
     *  5. 用户A 用户B 无关联
     *  其中2、4可通过update完成用户A关注用户B
     *  其中1、2、4可通过update完成用户A屏蔽用户B
     *  但是假如是情况3，update失败，也不代表用户A能关注用户B，所以这里还是不得不先查询后update
     *
     * (0) 需要先判断 用户A 关注/屏蔽的对象 是否是 用户A本身，若是，则添加失败
     * (1) 查询所有用户A 关注/屏蔽 用户B 的记录(先不判断类型);
     *    - 根据查询出来的subscribe的type分情况：(如果查询出来null，则直接往下走add流程)
     *   (1) 查询出的 type = 关注
     *      (1) 如果此时add添加的是关注
     *          (1) 存在，则直接返回success=true(但是表面上假装updateSuccess，其实不做操作)
     *          (2) 被逻辑删除，则取消逻辑删除，修改订阅时间，修改update_by
     *      (2) 如果此时add添加的是屏蔽，则将原本的关注改为屏蔽，取消逻辑删除，且修改update_by
     *          (1) 存在，type 关注=>屏蔽，修改订阅时间，修改update_by
     *          (2) 被逻辑删除，在(1)基础上取消逻辑删除
     *   (2) 查询出的 type = 屏蔽
     *      (1) 如果此时add添加的是关注，则相当于添加失败(个人理想情况是抛出业务异常，目前没统一标准，只能作罢)，直接返回
     *          (1) 存在，添加失败，返回success=false
     *          (2) 被逻辑删除，type 屏蔽=>关注，取消逻辑删除，修改update_by
     *      (2) 如果此时add添加的是屏蔽，则直接返回
     *          (1) 存在，则直接返回success=true(但是表面上假装updateSuccess，其实不做操作)
     *          (2) 被逻辑删除，则取消逻辑删除，修改update_by
     * (2) 如果找不到对应的 被关注/屏蔽用户member(没被逻辑删除)，则返回；
     * (3) 根据member找对应的职位select_config信息(激活、未被逻辑删除)，无论是否null，添加到属性里
     * (4) 根据member找对应的组织机构organization信息(未被逻辑删除)，无论是否null，添加到属性里
     *
     * subscribe的type只存在两种情况(不存在NULL值)；
     * subscribe的del_flag默认值为0(即未被逻辑删除)；
     * subscribe的add只会是FOLLOW或者BLOCK；
     * 基于以上三点，下面三句就足够if判断了
     * boolean addTypeEqualsFollow = existedType == typeFollow;
     * boolean existedTypeEqualFollow = existedType == typeFollow;
     * boolean existedNoBeenDeleted = existedDelFlag == DELETE_FLAG_NO;
     * </pre>
     * }</p>
     *
     * @param subscribe 新建的对象 {memeberId 对象Id; subscribeId 被关注/屏蔽的用户Id; type 行为类型：关注/屏蔽}
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
     * @param updateSubscribe   修改后的对象 {
     *                          任意Subscribe的非null属性
     *                          }
     * @param criteriaSubscribe 查询条件 {
     *                          memberId 当前用户id
     *                          subscribeId 被关注/屏蔽的用户id
     *                          subscribeType 关注对象身份 0-会员 1-组织机构(可选)
     *                          type 关注/屏蔽(可选)
     *                          delFlag 逻辑删除标识
     *                          其他需要被更新的字段(非null才更新)
     *                          }
     * @return 通用update方法 {
     * 根据memberId和subscribeId，只更新非null的字段
     * 不管删除与否，都更新
     * ps: add用到该方法
     * }
     */
    private SubscribeDTO update(Subscribe updateSubscribe, Subscribe criteriaSubscribe) {
        log.info("SubscribeServiceImpl -> update -> Subscribe subscribe : {}, Subscribe criteriaSubscribe : {}", updateSubscribe, criteriaSubscribe);
        SubscribeDTO dto = updateAll(updateSubscribe, criteriaSubscribe);
        log.info("SubscribeServiceImpl -> update -> SubscribeDTO : {}", dto);
        return dto;
    }

    /**
     * <p>通用updateAll方法 {<br/>
     * (1) memberId有值时，通常用于更新纯Subscribe信息(逻辑删除状态等)<br/>
     * (2) memberId无值时，通常用于更新所有被关注/屏蔽的用户的冗余用户信息(用户修改个人信息时触发，用户Controller跨类调用)<br/>
     * (3) type有值时，通常是add、delete调用时只对"未被逻辑删除"的条目进行更新<br/>
     * }
     * </p>
     *
     * @param updateSubscribe   修改后的对象{任意Subscribe的非null属性}
     * @param criteriaSubscribe 查询条件 {memberId 当前用户id(可选)，只有为null时才是更新所有;
     *                          subscribeId 被关注/屏蔽的用户id;
     *                          subscribeType 关注对象身份 0-会员 1-组织机构(可选),为了更新 关注/屏蔽的时候才用到
     *                          type 关注/屏蔽(可选)，只有为了更新 关注/屏蔽的时候才会用到;
     *                          delFlag 逻辑删除标识(可选);
     *                          其他需要被更新的字段(非null才更新);
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
            // 操作单独用户的时候 更新对象信息
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
     * 通用删除delete方法 {<br/>
     * 和删除多个deleteAll类似，不过是多了个指定的对象(被取消关注的用户的subscribeId)<br/>
     * }
     * </p>
     *
     * @param subscribe 删除单个条件 {
     *                  memberId 用户id;
     *                  subscribeId 被关注/屏蔽的对象id;
     *                  type 关注/屏蔽;
     *                  subscribeType 关注/屏蔽的对象的身份
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
     * 通用删除deleteAll方法 {<br/>
     * 只对 "未被逻辑删除" 的条目进行更新 => 标示成已经被 "逻辑删除"<br/>
     * }
     * </p>
     *
     * @param subscribe 删除所有条件 {
     *                  memberId 用户id;
     *                  subscribeId 被关注/屏蔽的对象id (可选);
     *                  type 关注/屏蔽;
     *                  subscribeType 关注/屏蔽的对象的身份
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
     * 关注组织机构
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void attentionOrganization(AttentionOrganizationReq attentionOrganizationReq) {
        log.info("SubscribeServiceImpl -> attentionOrganization -> AttentionOrganizationReq : {}", attentionOrganizationReq);
        Long subscribeId = attentionOrganizationReq.getSubscribeId();
        Long memberId = attentionOrganizationReq.getMemberId();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andIdEqualTo(memberId);
        log.info("SubscribeServiceImpl -> attentionOrganization -> 用户不为空判断");
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
        log.info("SubscribeServiceImpl -> attentionOrganization -> 从未有过关注记录");
        if (subscribe == null) {
            Subscribe criteriaSubscribe = Subscribe.builder()
                    .memberId(attentionOrganizationReq.getMemberId()).subscribeTime(LocalDateTime.now())
                    .subscribeId(attentionOrganizationReq.getSubscribeId()).subscribeName(organization.getName())
                    .type(SubscribeEnum.TYPE_FOLLOW.getValue()).subscribeType(SubscribeEnum.TYPE_FOLLOW.getValue())
                    .delFlag((byte) SubscribeEnum.FLAG_FOLLOW.getValue())
                    .build();
            subscribeMapper.insertSelective(criteriaSubscribe);
        } else {
            log.info("SubscribeServiceImpl -> attentionOrganization -> 已经有关注记录");
            subscribe.setDelFlag(attentionOrganizationReq.getDelFlag());
            subscribe.setMemberId(attentionOrganizationReq.getMemberId());
            subscribe.setSubscribeId(attentionOrganizationReq.getSubscribeId());
            subscribe.setSubscribeName(attentionOrganizationReq.getSubscribeName());
            subscribeMapper.updateByExampleSelective(subscribe, subscribeExample);
        }
    }


    /**
     * 查询用户是否关注组织或个人
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
