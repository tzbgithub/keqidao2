package com.qidao.application.service.dynamic.channel.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.qidao.application.entity.dynamic.Channel;
import com.qidao.application.entity.dynamic.ChannelExample;
import com.qidao.application.entity.relation.LinkDynamicChannel;
import com.qidao.application.entity.relation.LinkDynamicChannelExample;
import com.qidao.application.mapper.dynamic.ChannelMapper;
import com.qidao.application.mapper.relation.LinkDynamicChannelMapper;
import com.qidao.application.model.aadvertise.AdvertiseInfoRes;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.common.req.BasePageQuery;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dynamic.channel.*;
import com.qidao.application.service.advertise.AdvertiseService;
import com.qidao.application.service.dynamic.channel.ChannelService;
import com.qidao.application.vo.AdvertisePageRep;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xinfeng
 * @create 2021-01-29 13:41
 */
@Service("channelService")
@Slf4j
public class ChannelServiceImpl  implements ChannelService {

    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private LinkDynamicChannelMapper linkDynamicChannelMapper;
    @Autowired
    private AdvertiseService advertiseService;
    @Autowired
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Override
    public Boolean insert(ChannelAddReq channelAddReq) {
        log.info("ChannelServiceImpl -> insert -> start -> channelAddReq : {}", channelAddReq);
        Channel channel = Channel.builder()
                .id(snowflakeIdWorker53.nextId())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .title(channelAddReq.getTitle())
                .sequence(channelAddReq.getSequence())
                .delFlag(ConstantEnum.NOT_DEL.getByte())
                .build();
        log.info("ChannelServiceImpl -> insert -> end");
        return channelMapper.insert(channel) > 0;
    }

    @Override
    public Boolean update(ChannelUpdateReq channelUpdateReq) {
        log.info("ChannelServiceImpl -> update -> start -> channelUpdateReq : {}", channelUpdateReq);
        Channel channel = channelMapper.selectByPrimaryKey(channelUpdateReq.getId());
        if(ObjectUtil.isEmpty(channel)){
            log.error("ChannelServiceImpl -> update -> end -> error : {}", ChannelExceptionEnum.CHANNEL_NOT_EXIST.getMessage());
            throw new BusinessException(ChannelExceptionEnum.CHANNEL_NOT_EXIST);
        }

        if(ObjectUtil.isNotNull(channelUpdateReq.getSequence())){
            channel.setSequence(channelUpdateReq.getSequence());
        }
        channel.setTitle(channelUpdateReq.getTitle());
        channel.setUpdateTime(LocalDateTime.now());
        log.info("ChannelServiceImpl -> update -> end");
        return channelMapper.updateByPrimaryKey(channel) > 0;
    }

    @Override
    public ServicePage<List<ChannelRes>> getListByName(ChannelQuery channelQuery) {
        log.info("ChannelServiceImpl -> getListByName -> start -> channelQuery : {}", channelQuery);
        ChannelExample example = new ChannelExample();
        ChannelExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        criteria.andTitleEqualTo(channelQuery.getTitle());
        ServicePage<List<ChannelRes>> servicePage = new ServicePage<>();
        Page<Channel> page = PageUtil.start(channelQuery, () -> channelMapper.selectByExample(example));
        log.info("ChannelServiceImpl -> getListByName -> CollUtil.isNotEmpty(page) : {}", (CollUtil.isNotEmpty(page)));
        if(CollUtil.isNotEmpty(page)){
            servicePage.setEndRow(page.getEndRow());
            servicePage.setPageNum(page.getPageNum());
            servicePage.setPages(page.getPages());
            servicePage.setPageSize(page.getPageSize());
            servicePage.setTotal(page.getTotal());
            servicePage.setStartRow(page.getStartRow());
            List<Channel> list = page.getResult();
            List<ChannelRes> resList = new ArrayList<>();
            for(Channel channel : list){
                log.info("ChannelServiceImpl -> getListByName -> ObjectUtil.isNull(channel.getSequence()) : {}", (ObjectUtil.isNull(channel.getSequence())));
                if(ObjectUtil.isNull(channel.getSequence())){
                    ChannelRes channelRes = ChannelRes.builder()
                            .id(channel.getId())
                            .title(channel.getTitle())
                            .build();
                    resList.add(channelRes);
                }else{
                    ChannelRes channelRes = ChannelRes.builder()
                            .id(channel.getId())
                            .sequence(channel.getSequence())
                            .title(channel.getTitle())
                            .build();
                    resList.add(channelRes);
                }
            }
            servicePage.setResult(resList);
        }
        log.info("ChannelServiceImpl -> getListByName -> end");
        return servicePage;
    }

    @Override
    public Boolean deleteById(BaseIdQuery baseIdQuery) {
        log.info("ChannelServiceImpl -> deleteById -> start -> baseIdQuery : {}", baseIdQuery);
        Channel channel = channelMapper.selectByPrimaryKey(baseIdQuery.getId());
        log.info("ChannelServiceImpl -> deleteById -> ObjectUtil.isEmpty(channel) || channel.getDelFlag() == ConstantEnum.DELETED.getByte() : {}", (ObjectUtil.isEmpty(channel) || channel.getDelFlag() == ConstantEnum.DELETED.getByte()));
        if(ObjectUtil.isEmpty(channel) || channel.getDelFlag() == ConstantEnum.DELETED.getByte()){
            log.error("ChannelServiceImpl -> deleteById -> end -> error : {}", ChannelExceptionEnum.CHANNEL_NOT_EXIST.getMessage());
            throw new BusinessException(ChannelExceptionEnum.CHANNEL_NOT_EXIST);
        }
        channel.setDelFlag(ConstantEnum.DELETED.getByte());
        return channelMapper.updateByPrimaryKey(channel) > 0;
    }

    @Override
    public ServicePage<List<ChannelRes>> getList(BasePageQuery basePageQuery) {
        log.info("ChannelServiceImpl -> getList -> start -> channelQuery : {}", basePageQuery);
        ChannelExample example = new ChannelExample();
        example.setOrderByClause(" sequence desc ");
        ChannelExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        ServicePage<List<ChannelRes>> servicePage = new ServicePage<>();
        Page<Channel> page = PageUtil.start(basePageQuery, () -> channelMapper.selectByExample(example));
        log.info("ChannelServiceImpl -> getList -> CollUtil.isNotEmpty(page) : {}", (CollUtil.isNotEmpty(page)));

        if(CollUtil.isNotEmpty(page)){
            BeanUtil.copyProperties(page,servicePage);
            List<Channel> list = page.getResult();
            List<ChannelRes> resList = new ArrayList<>();
            List<AdvertiseInfoRes> adList = new ArrayList<>();
            if(CollUtil.isNotEmpty(list)) {
                LinkDynamicChannelExample linkExample = new LinkDynamicChannelExample();
                linkExample.createCriteria()
                        // todo 魔法值 [5.1]
                        .andTypeEqualTo(1)
                        .andChannelIdIn(list.stream().map(Channel::getId).collect(Collectors.toList()));
                // todo 魔法值 [5.1]
                adList = advertiseService.getList(142127887613953L, list.stream().map(Channel::getId).collect(Collectors.toList()));
            }
            for(Channel channel : list){
                log.info("ChannelServiceImpl -> getList -> ObjectUtil.isNull(channel.getSequence()) : {}", (ObjectUtil.isNull(channel.getSequence())));
                    ChannelRes channelRes = ChannelRes.builder()
                            .sequence(channel.getSequence())
                            .title(channel.getTitle())
                            .id(channel.getId())
                            .adList(adList.stream().filter(ad -> ad.getChannelId().equals(channel.getId())).collect(Collectors.toList()))
                            .build();
                    resList.add(channelRes);
            }
            servicePage.setResult(resList);
        }

        return servicePage;
    }
}
