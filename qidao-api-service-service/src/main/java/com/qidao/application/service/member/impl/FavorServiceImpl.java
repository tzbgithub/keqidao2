package com.qidao.application.service.member.impl;

import com.qidao.application.entity.dynamic.Dynamic;
import com.qidao.application.entity.dynamic.DynamicExample;
import com.qidao.application.entity.member.Favor;
import com.qidao.application.entity.member.FavorExample;
import com.qidao.application.entity.member.Subscribe;
import com.qidao.application.entity.member.SubscribeExample;
import com.qidao.application.mapper.dynamic.CustomDynamicMapper;
import com.qidao.application.mapper.dynamic.DynamicMapper;
import com.qidao.application.mapper.member.FavorMapper;
import com.qidao.application.mapper.member.SubscribeMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dynamic.DynamicExceptionEnum;
import com.qidao.application.model.member.favor.*;
import com.qidao.application.model.member.subscribe.SubscribeEnum;
import com.qidao.application.service.member.FavorService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service("favorService")
@Slf4j
public class FavorServiceImpl implements FavorService {

    @Resource
    private FavorMapper favorMapper;

    @Resource
    private SubscribeMapper subscribeMapper;

    @Resource
    private CustomDynamicMapper customDynamicMapper;

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    /**
     * 新增收藏
     * @param req
     * @return
     */
    @Override
    public Boolean create(FavorInsertReq req) {
        log.info("FavorService -> create -> start -> param:{}",req);
        DynamicExample dynamicExample = new DynamicExample();
        dynamicExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(req.getDynamicId());
        Dynamic dynamic = dynamicMapper.selectOneByExample(dynamicExample);
        log.info("FavorService -> create -> dynamic == null :{}",dynamic == null);
        if (dynamic == null){
            log.error("FavorService -> create -> error :{}",DynamicExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(DynamicExceptionEnum.DELETE_TRUE);
        }
        String dynamicLabels = customDynamicMapper.findDynamicLabels(req.getDynamicId());
        FavorExample favorExample = new FavorExample();
        favorExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andMemberIdEqualTo(req.getMemberId())
                .andDynamicIdEqualTo(req.getDynamicId());
        Favor favorQuery = favorMapper.selectOneByExample(favorExample);
        log.info("FavorService -> create -> favorQuery != null :{}",favorQuery != null);
        if (favorQuery != null){
            log.info("FavorService -> create -> error :{}",FavorExceptionEnum.FAVOR_REPEAT.getMessage());
            throw new BusinessException(FavorExceptionEnum.FAVOR_REPEAT);
        }
        SubscribeExample example = new SubscribeExample();
        example.createCriteria()
                .andSubscribeIdEqualTo(dynamic.getMemberId())
                .andTypeEqualTo(SubscribeEnum.TYPE_BLOCK.getValue())
                .andMemberIdEqualTo(req.getMemberId())
                .andDelFlagEqualTo((byte)SubscribeEnum.DELETE_FLAG_NO.getValue());
        Subscribe subscribe = subscribeMapper.selectOneByExample(example);
        log.info("FavorService -> create -> subscribe!= null :{}",subscribe!= null);
        if (subscribe != null){
            log.error("FavorService -> create -> error :{}",FavorExceptionEnum.MEMBER_BLOCK);
            throw new BusinessException(FavorExceptionEnum.MEMBER_BLOCK);
        }
        Favor favor = new Favor();
        BeanUtils.copyProperties(req , favor);
        favor.setCreateTime(LocalDateTime.now());
        favor.setUpdateTime(LocalDateTime.now());
        favor.setDynamicCommentNum(dynamic.getCommentNum());
        favor.setDynamicImg(dynamic.getImg());
        favor.setDynamicLabelStr(dynamicLabels);
        favor.setDynamicLikeNum(dynamic.getLikeNum());
        favor.setDynamicSummary(dynamic.getSummary());
        favor.setDynamicPushMemberId(dynamic.getMemberId());
        favor.setDynamicPushTime(dynamic.getPublishTime());
        favor.setDynamicTitle(dynamic.getTitle());
        favor.setId(snowflakeIdWorker53.nextId());
        int insert = favorMapper.insertSelective(favor);
        log.info("FavorService -> create -> end");
        return insert > 0 ;
    }

    /**
     * 根据dynamicId删除收藏
     * @param req
     */
    @Override
    public void deleteByDynamicId(FavorDeleteReq req) {
        log.info("FavorServiceImpl -> deleteByDynamicId -> start -> param:{}",req);
        FavorExample favorExample = new FavorExample();
        favorExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andDynamicIdEqualTo(req.getDynamicId())
                .andMemberIdEqualTo(req.getMemberId());
        Favor favor = favorMapper.selectOneByExample(favorExample);
        log.info("FavorServiceImpl -> deleteByDynamicId -> favor == null :{}",favor == null);
        if (favor == null){
            log.error("FavorServiceImpl -> deleteByDynamicId -> error :{}",FavorExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(FavorExceptionEnum.DELETE_TRUE);
        }
        favor.setDelFlag(ConstantEnum.DELETED.getByte());
        favorMapper.updateByPrimaryKeySelective(favor);
        log.info("FavorServiceImpl -> deleteByDynamicId -> end");
    }


    /**
     * 清空收藏
     * @param memberId
     */
    @Override
    public void empty(Long memberId) {
        log.info("FavorServiceImpl -> empty -> start -> param:{}",memberId);
        FavorExample favorExample = new FavorExample();
        favorExample.createCriteria()
                .andMemberIdEqualTo(memberId);
        Favor favor = new Favor();
        favor.setDelFlag(ConstantEnum.DELETED.getByte());
        favorMapper.updateByExampleSelective(favor , favorExample);
        log.info("FavorServiceImpl -> empty -> end");
    }
}
