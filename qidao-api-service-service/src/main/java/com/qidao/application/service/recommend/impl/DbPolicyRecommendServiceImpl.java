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
 * 数据库查询 : 其他均未命中的情况下,使用数据库层
 * @author Autuan.Yu
 */
@Slf4j
@Service
public class DbPolicyRecommendServiceImpl implements IPolicyRecommendService {
    @Override
    public Integer getType() {
        return RecommendConstantEnum.DB_MODEL.getInt();
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
