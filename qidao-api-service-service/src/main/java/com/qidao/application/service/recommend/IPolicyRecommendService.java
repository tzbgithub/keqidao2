package com.qidao.application.service.recommend;

import com.qidao.application.model.recommend.RecommendDynamicQueryPolicyReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

/**
 * 推荐策略
 * !WARN! 此接口只提供给 recommend 使用,其他模块不要调用
 * @author Autuan.Yu
 */
public interface IPolicyRecommendService {
    /**
     * 获取 type
     * 0-item推荐
     * 1-user推荐
     * 2-hot推荐
     * 3-db推荐
     * @return
     */
    Integer getType();
    /**
     * 获取推荐动态列表
     * @return
     */
    List<? extends RecommendedItem> listDynamic(RecommendDynamicQueryPolicyReq req);

    /**
     * 获取推荐人员列表
     * @param memberId 会员ID
     * @param howMany 数量
     * @return 推荐结果集
     */
    List<? extends RecommendedItem> listScientificResearch(RecommendResearcherReq req);
}
