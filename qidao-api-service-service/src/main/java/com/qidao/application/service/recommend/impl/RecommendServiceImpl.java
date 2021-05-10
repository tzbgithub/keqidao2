package com.qidao.application.service.recommend.impl;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dynamic.DynamicPageRes;
import com.qidao.application.model.member.MemberInfoRes;
import com.qidao.application.model.member.MemberLabelRes;
import com.qidao.application.model.member.MemberSummaryRes;
import com.qidao.application.model.recommend.RecommendDynamicReq;
import com.qidao.application.model.recommend.RecommendDynamicQueryPolicyReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;
import com.qidao.application.service.dynamic.dynamic.DynamicService;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.recommend.IPolicyRecommendService;
import com.qidao.application.service.recommend.IRecommendService;
import com.qidao.application.service.recommend.enums.RecommendConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Autuan.Yu
 */
@Service
@Slf4j
public class RecommendServiceImpl implements IRecommendService {

    private Map<Integer, IPolicyRecommendService> policyRecommendServiceMap;

    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private MemberService memberService;

    private final int policySize;

    @Autowired
    public RecommendServiceImpl(List<IPolicyRecommendService> polices) {
        policyRecommendServiceMap = new HashMap<>(polices.size());
        for (IPolicyRecommendService policy : polices) {
            policyRecommendServiceMap.put(policy.getType(), policy);
        }
        this.policySize = polices.size();
    }

    /**
     * 动态推荐 ： 通过推荐算法推荐 <br>
     * <p>
     * 为尽量避免推荐结果为空，采用下列推荐算法<br>
     * 动态协同过滤 -> 用户协同过滤 -> 热门标签 -> 普通动态<br>
     * <br>
     * <strong>初版只使用自定义分值系统进行推荐 </strong><br>
     * <p>
     * 5 分钟以内 获取的动态不会重复 <br>
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<DynamicPageRes> getDynamicList(RecommendDynamicReq req) {
        log.info("RecommendServiceImpl -> getDynamicList -> start -> param -> {}", req);

        MemberInfoRes memberInfo = memberService.getMemberByMemberId(req.getMemberId());
        if (null == memberInfo) {
            // todo error 枚举 (Autuan)[3.15]
            throw new BusinessException("用户信息不存在");
        }

        List<String> memberLabels = new ArrayList<>();
        if (CollUtil.isNotEmpty(memberInfo.getLabels())) {
            memberLabels = memberInfo.getLabels().stream()
                    .map(MemberLabelRes::getId)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        }

        Long industryId = memberInfo.getIndustryId();

        RecommendDynamicQueryPolicyReq listDynamicReq = RecommendDynamicQueryPolicyReq.builder()
                .industryId(industryId)
                .memberLabels(memberLabels)
                .memberId(req.getMemberId())
                .howMany(req.getHowMany())
                .build();
        List<? extends RecommendedItem> recommendedItems = policyRecommendServiceMap
                .get(RecommendConstantEnum.QIDAO_MODEL.getInt())
                .listDynamic(listDynamicReq);

        List<Long> results = recommendedItems.stream()
                .map(RecommendedItem::getItemID)
                .distinct()
                .collect(Collectors.toList());

        List<DynamicPageRes> dynamicList = dynamicService.getDynamicList(results, req.getMemberId());

        log.info("RecommendServiceImpl -> getDynamicList -> end");
        return dynamicList;
    }

    @Override
    public List<MemberSummaryRes> getScientificResearchList(RecommendResearcherReq req) {
        log.info("RecommendServiceImpl -> getScientificResearchList -> req -> {}", req);

        List<? extends RecommendedItem> recommendedItems = policyRecommendServiceMap.get(RecommendConstantEnum.QIDAO_MODEL.getInt()).listScientificResearch(req);

        // 查询会员基本信息
        List<Long> ids = recommendedItems.stream()
                .map(RecommendedItem::getItemID)
                .collect(Collectors.toList());

        List<MemberSummaryRes> members = memberService.listMemberSummaryByMemberId(ids);

        log.info("RecommendServiceImpl -> getScientificResearchList -> end");

        return members;
    }

}
