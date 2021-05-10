package com.qidao.application.service.recommend;


import com.qidao.application.model.dynamic.DynamicPageRes;
import com.qidao.application.model.member.MemberSummaryRes;
import com.qidao.application.model.recommend.RecommendDynamicReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;

import java.util.List;

public interface IRecommendService {
    /**
     * 获取动态列表
     * @param req {@link RecommendDynamicReq}
     * @return 集合 {@link DynamicPageRes}
     */
    List<DynamicPageRes> getDynamicList(RecommendDynamicReq req);

    /**
     * 获取推荐人员列表<br>
     * @param req {@link RecommendResearcherReq}
     * @return 集合 {@link MemberSummaryRes}
     */
    List<MemberSummaryRes> getScientificResearchList(RecommendResearcherReq req);
}
