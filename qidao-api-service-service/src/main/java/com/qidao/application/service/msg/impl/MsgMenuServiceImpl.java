package com.qidao.application.service.msg.impl;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.entity.msg.MsgMenu;
import com.qidao.application.entity.msg.MsgMenuExample;
import com.qidao.application.mapper.msg.MsgMenuMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.msg.*;
import com.qidao.application.model.msg.enums.MsgConstantEnum;
import com.qidao.application.model.msg.enums.MsgExceptionEnum;
import com.qidao.application.service.msg.MsgMenuService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("msgMenuService")
@Slf4j
public class MsgMenuServiceImpl implements MsgMenuService {

    @Resource
    private MsgMenuMapper msgMenuMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    /**
     * 查询消息菜单列表
     * @param req
     * @return
     */
    @Override
    public List<MsgMenuQueryRes> getMsgMenu(MsgMenuQueryReq req) {
        log.info("MsgMenuServiceImpl -> getMsgMenu -> start -> param:{}",req);
        MsgMenuExample msgMenuExample = new MsgMenuExample();
        MsgMenuExample.Criteria criteria = msgMenuExample.createCriteria();
        Boolean nameIsEmpty = req.getKeyword()!=null && !req.getKeyword().equals("");
        log.info("MsgMenuServiceImpl -> getMsgMenu -> nameIsEmpty :{}",nameIsEmpty);
        if (nameIsEmpty){
            criteria.andNameLike("%"+req.getKeyword()+"%");
        }
        log.info("MsgMenuServiceImpl -> getMsgMenu -> req.getStatus() != null :{}",req.getStatus() != null);
        if (req.getStatus() != null){
            criteria.andStatusEqualTo(req.getStatus());
        }
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<MsgMenu> msgMenus = msgMenuMapper.selectByExample(msgMenuExample);
        List<MsgMenuQueryRes> res = new ArrayList<>();
        log.info("MsgMenuServiceImpl -> getMsgMenu -> msgMenus != null :{}",CollUtil.isNotEmpty(msgMenus));
        if (CollUtil.isNotEmpty(msgMenus)){
            msgMenus.forEach(msgMenu -> {
                MsgMenuQueryRes menuQueryRes = new MsgMenuQueryRes();
                BeanUtils.copyProperties(msgMenu , menuQueryRes);
                res.add(menuQueryRes);
            });
        }
        log.info("MsgMenuServiceImpl -> getMsgMenu -> end -> return:{}",res);
        return res;
    }

    /**
     * 删除消息菜单
     * @param msgMenuId
     */
    @Override
    public void delete(Long msgMenuId) {
        log.info("MsgMenuServiceImpl -> delete -> start -> param:{}",msgMenuId);
        MsgMenuExample msgMenuExample = new MsgMenuExample();
        msgMenuExample.createCriteria()
                .andIdEqualTo(msgMenuId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        MsgMenu msgMenu = msgMenuMapper.selectOneByExample(msgMenuExample);
        log.info("MsgMenuServiceImpl -> delete -> msgMenu == null :{}",msgMenu == null);
        if (msgMenu == null){
            log.error("MsgMenuServiceImpl -> delete -> error :{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        msgMenu.setDelFlag(ConstantEnum.DELETED.getByte());
        msgMenuMapper.updateByPrimaryKeySelective(msgMenu);
        log.info("MsgMenuServiceImpl -> delete -> end");
    }

    /**
     * 新增消息菜单
     * @param req
     */
    @Override
    public void insert(MsgMenuInsertReq req) {
        log.info("MsgMenuServiceImpl -> insert -> start -> param:{}",req);
        MsgMenu msgMenu = new MsgMenu();
        BeanUtils.copyProperties(req , msgMenu);
        msgMenu.setId(snowflakeIdWorker53.nextId());
        msgMenu.setDelFlag(ConstantEnum.NOT_DEL.getByte());
        msgMenu.setStatus(MsgConstantEnum.STATUS_TRUE.getCode());
        msgMenuMapper.insertSelective(msgMenu);
        log.info("MsgMenuServiceImpl -> insert -> end");
    }

    /**
     * 修改消息菜单
     * @param req
     */
    @Override
    public void update(MsgMenuUpdateReq req) {
        log.info("MsgMenuServiceImpl -> update -> start -> param:{}",req);
        MsgMenuExample msgMenuExample = new MsgMenuExample();
        msgMenuExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdEqualTo(req.getId());
        MsgMenu msgMenu = msgMenuMapper.selectOneByExample(msgMenuExample);
        log.info("MsgMenuServiceImpl -> update -> msgMenu == null :{}",msgMenu == null);
        if (msgMenu == null){
            log.error("MsgMenuServiceImpl -> update -> error :{}",MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        BeanUtils.copyProperties(req , msgMenu);
        msgMenuMapper.updateByPrimaryKeySelective(msgMenu);
        log.info("MsgMenuServiceImpl -> update -> end");
    }
}
