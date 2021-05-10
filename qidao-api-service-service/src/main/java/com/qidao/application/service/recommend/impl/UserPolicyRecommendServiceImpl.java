package com.qidao.application.service.recommend.impl;

import com.qidao.application.model.recommend.RecommendDynamicQueryPolicyReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;
import com.qidao.application.service.recommend.IPolicyRecommendService;
import com.qidao.application.service.recommend.RecommendComponent;
import com.qidao.application.service.recommend.enums.RecommendConstantEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserPolicyRecommendServiceImpl implements IPolicyRecommendService {
    private Recommender userRecommend;

    @SneakyThrows
    @Autowired
    public UserPolicyRecommendServiceImpl(RecommendComponent recommendComponent) {
        DataModel dataModel = recommendComponent.getDataModel();
        // 皮埃森算法
//        UserSimilarity similarity=new PearsonCorrelationSimilarity(dataModel);
        UserSimilarity similarity=new SpearmanCorrelationSimilarity(dataModel);
//         todo 常量枚举 (Autuan)[21.1.20]
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(10,similarity,dataModel);
        this.userRecommend = new GenericUserBasedRecommender(dataModel,neighborhood,similarity);
    }
    @Override
    public Integer getType() {
        return RecommendConstantEnum.USER_MODEL.getInt();
    }

    @SneakyThrows
    @Override
    public List<RecommendedItem> listDynamic(RecommendDynamicQueryPolicyReq req) {
        List<RecommendedItem> userRecommends=userRecommend.recommend(req.getMemberId(), req.getHowMany());
        return userRecommends;
    }

    @Override
    public List<? extends RecommendedItem> listScientificResearch(RecommendResearcherReq req) {
        return new ArrayList<>();
    }
}
