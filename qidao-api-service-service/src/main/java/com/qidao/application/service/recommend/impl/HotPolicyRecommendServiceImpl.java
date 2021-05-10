package com.qidao.application.service.recommend.impl;

import com.qidao.application.model.recommend.RecommendDynamicQueryPolicyReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;
import com.qidao.application.service.recommend.IPolicyRecommendService;
import com.qidao.application.service.recommend.enums.RecommendConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门标签 : 通常由后台管理进行配置
 * @author Autuan.Yu
 */
@Service
@Slf4j
public class HotPolicyRecommendServiceImpl implements IPolicyRecommendService {
    @Override
    public Integer getType() {
        return RecommendConstantEnum.HOT_MODEL.getInt();
    }

    @Override
    public List<RecommendedItem> listDynamic(RecommendDynamicQueryPolicyReq req) {
        return new ArrayList<>();
    }

    @Override
    public List<? extends RecommendedItem> listScientificResearch(RecommendResearcherReq req) {
        return new ArrayList<>();
    }
}
