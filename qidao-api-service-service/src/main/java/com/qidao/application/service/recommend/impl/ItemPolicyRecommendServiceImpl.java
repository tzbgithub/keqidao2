package com.qidao.application.service.recommend.impl;

import com.qidao.application.model.recommend.RecommendDynamicQueryPolicyReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;
import com.qidao.application.service.recommend.IPolicyRecommendService;
import com.qidao.application.service.recommend.RecommendComponent;
import com.qidao.application.service.recommend.enums.RecommendConstantEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ItemPolicyRecommendServiceImpl implements IPolicyRecommendService {
    private Recommender itemRecommender;

    @SneakyThrows
    @Autowired
    public ItemPolicyRecommendServiceImpl(RecommendComponent recommendComponent) {
        DataModel dataModel = recommendComponent.getDataModel();
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        this.itemRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
    }

    @Override
    public Integer getType() {
        return RecommendConstantEnum.ITEM_MODEL.getInt();
    }

    @SneakyThrows
    @Override
    public List<RecommendedItem> listDynamic(RecommendDynamicQueryPolicyReq req) {
        List<RecommendedItem> recommendations = itemRecommender.recommend(req.getMemberId(), req.getHowMany());
        return recommendations;
    }

    @Override
    public List<? extends RecommendedItem> listScientificResearch(RecommendResearcherReq req) {
        return new ArrayList<>();
    }
}
