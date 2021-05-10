package com.qidao.application.service.config.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qidao.application.entity.config.Canal;
import com.qidao.application.entity.config.CanalExample;
import com.qidao.application.mapper.config.CanalMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.common.req.BasePageQuery;
import com.qidao.application.model.config.CanalCreateReq;
import com.qidao.application.model.config.CanalRes;
import com.qidao.application.model.config.CanalUpdateReq;
import com.qidao.application.model.common.VerifyMatch;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.*;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.CanalExceptionEnum;
import com.qidao.application.model.server.ServerErrorEnum;
import com.qidao.application.service.config.CanalService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service(value = "CanalService")
@Slf4j
public class CanalServiceImpl implements CanalService {

    @Resource
    private CanalMapper canalMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Override
    public Boolean insert(CanalCreateReq canalCreateReq) {
        log.info("CanalServiceImpl -> insert -> start -> canalCreateReq : {}", canalCreateReq);
        log.info("CanalServiceImpl -> insert -> ObjectUtil.isNotEmpty(canalCreateReq) : {}", (ObjectUtil.isNotEmpty(canalCreateReq)));
        if(ObjectUtil.isNotEmpty(canalCreateReq)){
            Canal canal = new Canal();
            BeanUtils.copyProperties(canalCreateReq,canal);
            canal.setId(snowflakeIdWorker53.nextId());
            canal.setCreateTime(LocalDateTime.now());
            canal.setUpdateTime(LocalDateTime.now());
            canal.setDelFlag(ConstantEnum.NOT_DEL.getByte());
            log.info("CanalServiceImpl -> insert -> end");
            return canalMapper.insert(canal) > 0;
        }
        log.info("CanalServiceImpl -> insert -> end");
        return false;
    }

    @Override
    public Boolean update(CanalUpdateReq canalUpdateReq) {
        log.info("CanalServiceImpl -> update -> start -> canalUpdateReq : {}", canalUpdateReq);
        log.info("CanalServiceImpl -> update -> ObjectUtil.isNotEmpty(canalUpdateReq) : {}", (ObjectUtil.isNotEmpty(canalUpdateReq)));
        if(ObjectUtil.isNotEmpty(canalUpdateReq)){
            Canal canal = canalMapper.selectByPrimaryKey(canalUpdateReq.getId());
            log.info("CanalServiceImpl -> update -> canal.getDelFlag() != ConstantEnum.NOT_DEL.getByte() : {}", (canal.getDelFlag() != ConstantEnum.NOT_DEL.getByte()));
            if(canal.getDelFlag() != ConstantEnum.NOT_DEL.getByte()){
                Canal newCanal = new Canal();
                BeanUtils.copyProperties(canal, newCanal);
                newCanal.setUpdateTime(LocalDateTime.now());
                newCanal.setDownPath(canalUpdateReq.getDownPath());
                log.info("CanalServiceImpl -> update -> canalUpdateReq.getName() != null : {}", (canalUpdateReq.getName() != null));
                if(canalUpdateReq.getName() != null){
                    newCanal.setName(canalUpdateReq.getName());
                }
                newCanal.setVersion(canalUpdateReq.getVersion());
                newCanal.setUpdateBy(canalUpdateReq.getUpdateBy());
                log.info("CanalServiceImpl -> update -> end");
                return canalMapper.updateByPrimaryKey(newCanal) > 0;
            }
        }
        log.info("CanalServiceImpl -> update -> end");
        return false;
    }

    @Override
    public Boolean deleteById(BaseIdQuery baseIdQuery) {
        log.info("CanalServiceImpl -> deleteById -> start -> baseIdQuery : {}", baseIdQuery);
        Canal canal = canalMapper.selectByPrimaryKey(baseIdQuery.getId());
        log.info("CanalServiceImpl -> deleteById -> canal != null && canal.getDelFlag() == ConstantEnum.NOT_DEL.getByte() : {}", (canal != null && canal.getDelFlag() == ConstantEnum.NOT_DEL.getByte()));
        boolean checkCanal =canal!= null && canal.getDelFlag() == ConstantEnum.NOT_DEL.getByte();
        if(checkCanal){
            Canal newCanal = new Canal();
            BeanUtils.copyProperties(canal, newCanal);
            newCanal.setDelFlag(ConstantEnum.DELETED.getByte());
            log.info("CanalServiceImpl -> deleteById -> end");
            return canalMapper.updateByPrimaryKey(newCanal) > 0;
        }
        log.info("CanalServiceImpl -> deleteById -> end");
        return false;
    }

    @Override
    public ServicePage<List<CanalRes>> getList(BasePageQuery basePageQuery) {
        log.info("CanalServiceImpl -> getList -> start -> basePageQuery : {}", basePageQuery);
        CanalExample example = new CanalExample();
        CanalExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Page<Canal>page = PageUtil.start(basePageQuery, () -> canalMapper.selectByExample(example));
        log.info("CanalServiceImpl -> getList -> CollUtil.isNotEmpty(page) : {}", (CollUtil.isNotEmpty(page)));
        if(CollUtil.isNotEmpty(page)){
            ServicePage<List<CanalRes>> servicePage = new ServicePage<>();
            servicePage.setStartRow(page.getStartRow());
            servicePage.setTotal(page.getTotal());
            servicePage.setPageSize(page.getPageSize());
            servicePage.setPages(page.getPages());
            servicePage.setPageNum(page.getPageNum());
            servicePage.setEndRow(page.getEndRow());
            List<Canal> canalList = page.getResult();
            List<CanalRes> list = new ArrayList<>();
            for(Canal canal : canalList){
                CanalRes canalRes = CanalRes.builder()
                        .id(canal.getId())
                        .createBy(canal.getCreateBy())
                        .createTime(canal.getCreateTime())
                        .downPath(canal.getDownPath())
                        .name(canal.getName())
                        .updateBy(canal.getUpdateBy())
                        .updateTime(canal.getUpdateTime())
                        .version(canal.getVersion())
                        .build();
                list.add(canalRes);
            }
            servicePage.setResult(list);
            log.info("CanalServiceImpl -> getList -> end");
            return servicePage;
        }
        log.info("CanalServiceImpl -> getList -> end");
        return null;
    }

    /**
     * 验证最新版本号
     * @param canalReq
     */
    @Override
    public CanalRep verificationVersion(CanalReq canalReq) {
        // 0423 prod 紧急适配
        if ("ios".equals(canalReq.getCanalName()) ) {
            if("1.0.4".equals(canalReq.getVersion()) || "1.0.5".equals(canalReq.getVersion())) {
                canalReq.setVersion("1.0.3");
            }
        }
        log.info("CanalServiceImpl -> verificationVersion : params {}", canalReq);
        CanalExample canalExample = new CanalExample();
        canalExample.createCriteria()
                .andNameEqualTo(canalReq.getCanalName())
                //0-未发布 1-预发布 2-已发布
                .andStatusEqualTo(2)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        canalExample.setOrderByClause("create_time desc");
        List<Canal> canals = canalMapper.selectByExample(canalExample);
        if(CollUtil.isEmpty(canals)){
            throw new BusinessException(CanalExceptionEnum.VERSION_NOT_FOUND);
        }
        Canal canal = canals.get(0);

        if(StrUtil.isNotBlank(canalReq.getVersion())){
            int endSize = VerifyMatch.compareVersion(canalReq.getVersion(), canal.getVersion()); //1 代表左邊大
            if(endSize == 1){
                throw new BusinessException(CanalExceptionEnum.VERSION_NOT_FOUND);
            }else if(endSize ==0){
                return null;
            }
        }

        CanalRep canalRep = new CanalRep();
        canalRep.setFlag(true).setCanalName(canal.getName()).setPath(canal.getDownPath()).setVersion(canal.getVersion());
        return  canalRep;

    }
}
