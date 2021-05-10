package com.qidao.application.service.label.impl;

import cn.hutool.core.bean.BeanUtil;
import com.qidao.application.entity.label.Label;
import com.qidao.application.entity.label.LabelExample;
import com.qidao.application.mapper.label.CustomLabelMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.label.LabelDTO;
import com.qidao.application.model.label.ListLabelReq;
import com.qidao.application.model.mp.label.ListLabelRes;
import com.qidao.application.service.label.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Autuan.Yu
 */
@Service
@Slf4j
public class LabelServiceImpl implements LabelService {
    @Resource
    private CustomLabelMapper customLabelMapper;

    @Override
    public List<ListLabelRes> listAchievementLabel(ListLabelReq req) {
        log.info("LabelServiceImpl -> listAchievementLabel  -> req -> {}",req);
        List<Label> labels = customLabelMapper.listAchievementLabel(req.getMemberId());
        List<ListLabelRes> list = new ArrayList<>(labels.size());
        for (Label label : labels) {
            ListLabelRes dto = new ListLabelRes();
            BeanUtil.copyProperties(label, dto);
            list.add(dto);
        }
        log.info("LabelServiceImpl -> listAchievementLabel  -> list size -> {}",list.size());
        return list;
    }
}
