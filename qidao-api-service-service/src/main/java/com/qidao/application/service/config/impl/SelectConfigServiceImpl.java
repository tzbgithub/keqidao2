package com.qidao.application.service.config.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import com.qidao.application.entity.dict.Dict;
import com.qidao.application.entity.dynamic.Dynamic;
import com.qidao.application.entity.dynamic.DynamicExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.relation.LinkDynamicChannel;
import com.qidao.application.entity.relation.LinkDynamicChannelExample;
import com.qidao.application.mapper.config.CustomSelectConfigMapper;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.dict.DictMapper;
import com.qidao.application.mapper.dynamic.DynamicMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.config.*;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.dynamic.DynamicConstantEnum;
import com.qidao.application.service.config.SelectConfigService;
import com.qidao.framework.service.ServicePage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("SelectService")
@Slf4j
public class SelectConfigServiceImpl implements SelectConfigService {

    @Resource
    private SelectConfigMapper selectMapper;

    @Resource
    private DictMapper dictMapper;

    @Resource
    private CustomSelectConfigMapper customSelectConfigMapper;


    /**
     * 根据类型分页查询
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<SelectConfigVo>> getSelectByType(SelectGetByTypeReq req) {
        log.info("SelectServiceImpl -> getSelectByType -> start -> param:{}", req);
        Dict dict = dictMapper.selectByPrimaryKey(Long.valueOf(req.getType()));
        log.info("SelectServiceImpl -> getSelectByType -> dict == null :{}",dict == null);
        if (dict == null){
            log.error("SelectServiceImpl -> getSelectByType -> error :{}", SelectConfigExceptionEnum.TYPE_WRONGFUL.getMessage());
            throw new BusinessException(SelectConfigExceptionEnum.TYPE_WRONGFUL);
        }
        SelectConfigExample example = new SelectConfigExample();
        example.setOrderByClause("sequence desc");
        example.createCriteria()
                .andTypeEqualTo(req.getType())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andHotEqualTo(req.getHot()!= null ? req.getHot().byteValue() : ConstantEnum.NOT_DEL.getByte())
                .andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue());
        Page<SelectConfig> page = PageUtil.start(req ,() -> selectMapper.selectByExample(example));
        ServicePage<List<SelectConfigVo>> selectPage = new ServicePage<>();
        BeanUtils.copyProperties(page , selectPage);
        List<SelectConfig> selectConfigs = page.getResult();
        boolean notEmpty = CollUtil.isNotEmpty(selectConfigs);
        log.info("SelectServiceImpl -> getSelectByType -> judge selectConfigs -> isNotEmpty -> {}", notEmpty);
        if (notEmpty) {
            List<SelectConfigVo> selectVos = new ArrayList<>();
            for (SelectConfig selectConfig : selectConfigs) {
                SelectConfigVo selectVo = new SelectConfigVo();
                BeanUtils.copyProperties(selectConfig, selectVo);
                selectVos.add(selectVo);
            }
            selectPage.setResult(selectVos);
        }
        log.info("SelectServiceImpl -> getSelectByType -> end ");
        return selectPage;
    }

    @Override
    public List<SelectConfigVo> getSelectByPid(Long pid) {
        log.info("SelectServiceImpl -> getSelectByPid -> start -> pid:{}", pid);
        SelectConfigExample example = new SelectConfigExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue());
        example.setOrderByClause("sequence desc");
        List<SelectConfig> selectConfigs = selectMapper.selectByExample(example);
        log.info("SelectServiceImpl -> getSelectByPid -> 判断查询的值是否为空selectConfigs：{}",selectConfigs);
        if (CollUtil.isNotEmpty(selectConfigs)) {
            List<SelectConfigVo> selectConfigVos = new ArrayList<>();
            for (SelectConfig selectConfig : selectConfigs) {
                SelectConfigVo selectConfigVo = new SelectConfigVo();
                BeanUtils.copyProperties(selectConfig, selectConfigVo);
                selectConfigVos.add(selectConfigVo);
            }
            log.info("SelectServiceImpl -> getSelectByPid -> end");
            return selectConfigVos;
        }
        log.info("SelectServiceImpl -> getSelectByPid -> end");
        return null;
    }

    @Override
    public List<SelectConfigVo> getSelectFatherSonByType(Integer type) {
        log.info("SelectServiceImpl -> getSelectFatherSonByType -> start -> type:{}", type);
        List<SelectConfigVo> finalResult = new ArrayList<>();
        SelectConfigExample fatherExample = new SelectConfigExample();
        fatherExample.createCriteria()
                .andTypeEqualTo(type)
                .andPidEqualTo(SelectConfigEnum.NOT_PID.getLong())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue());
        fatherExample.setOrderByClause("sequence desc");
        List<SelectConfig> fatherSelectConfigs = selectMapper.selectByExample(fatherExample);
        SelectConfigExample sonExample = new SelectConfigExample();
        sonExample.createCriteria()
                .andTypeEqualTo(type)
                .andPidNotEqualTo(SelectConfigEnum.NOT_PID.getLong())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue());
        sonExample.setOrderByClause("sequence desc");
        List<SelectConfig> sonSelectConfigs = selectMapper.selectByExample(sonExample);
        log.info("SelectServiceImpl -> getSelectFatherSonByType -> 判断fatherSelectConfigs是否为空 fatherSelectConfigs：{}", fatherSelectConfigs);
        if (CollUtil.isNotEmpty(fatherSelectConfigs)) {
            for (SelectConfig fatherSelectConfig : fatherSelectConfigs) {
                SelectConfigVo selectConfigVo = new SelectConfigVo();
                BeanUtils.copyProperties(fatherSelectConfig, selectConfigVo);
                List<SelectSonVo> selectSonVos = new ArrayList<>();
                for (SelectConfig sonSelectConfig : sonSelectConfigs) {
                    if (fatherSelectConfig.getId().equals(sonSelectConfig.getPid())) {
                        SelectSonVo selectSonVo = new SelectSonVo();
                        BeanUtils.copyProperties(sonSelectConfig, selectSonVo);
                        selectSonVos.add(selectSonVo);
                    }
                }
                selectConfigVo.setChild(selectSonVos);
                finalResult.add(selectConfigVo);
            }
        }
        log.info("SelectServiceImpl -> getSelectFatherSonByType -> end");
        return finalResult;
    }

    @Override
    public DynamicIndustryRes getDynamicIndustry() {
        log.info("SelectServiceImpl -> getDynamicIndustry -> start");
        DynamicIndustryRes res = new DynamicIndustryRes();
        List<Long> industryIds = customSelectConfigMapper.getIndustryId();
        log.info("SelectServiceImpl -> getDynamicIndustry -> industryIds != null -> {}",CollUtil.isEmpty(industryIds));
        if (CollUtil.isEmpty(industryIds)){
            log.info("SelectServiceImpl -> getDynamicIndustry -> return -> null");
            return null;
        }
        SelectConfigExample industryExample = new SelectConfigExample();
        industryExample.createCriteria()
                .andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andIdIn(industryIds);
        List<SelectConfig> industryList = selectMapper.selectByExample(industryExample);
        List<SelectConfig> selectConfigs = industryList.stream().filter(s -> s.getHot() == 1).collect(Collectors.toList());
        List<ChildIndustryRes> hotIndustryList = new ArrayList<>();
        selectConfigs.forEach(s -> {
            ChildIndustryRes hotIndustry = new ChildIndustryRes();
            BeanUtils.copyProperties(s , hotIndustry);
            hotIndustryList.add(hotIndustry);
        });
        res.setHotIndustry(hotIndustryList);
        SelectConfigExample fatherExample = new SelectConfigExample();
        fatherExample.createCriteria()
                .andStatusEqualTo(SelectConfigEnum.STATUS_ACTIVE.getValue())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andTypeEqualTo(DictConstantEnum.INDUSTRY.getId())
                .andPidEqualTo(SelectConfigEnum.NOT_PID.getLong());
        List<SelectConfig> fatherConfigs = selectMapper.selectByExample(fatherExample);
        List<ScreenIndustryRes> screenIndustryList = new ArrayList<>();
        fatherConfigs.forEach(f -> {
            ScreenIndustryRes screenIndustry = new ScreenIndustryRes();
            BeanUtils.copyProperties(f, screenIndustry);
            List<ChildIndustryRes> children = new ArrayList<>();
            industryList.forEach(s -> {
                if (s.getPid().equals(f.getId())){
                    ChildIndustryRes child = new ChildIndustryRes();
                    BeanUtils.copyProperties(s , child);
                    children.add(child);
                }
            });
            screenIndustry.setChild(children);
            screenIndustryList.add(screenIndustry);
        });
        res.setIndustry(screenIndustryList.stream().filter(s -> CollUtil.isNotEmpty(s.getChild())).collect(Collectors.toList()));
        log.info("SelectServiceImpl -> getDynamicIndustry -> end");
        return res;
    }
}