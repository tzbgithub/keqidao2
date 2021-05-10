package com.qidao.application.service.recommend.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.qidao.application.entity.dynamic.Dynamic;
import com.qidao.application.model.dynamic.DynamicRecommendCacheDTO;
import com.qidao.application.model.member.MemberInfoRes;
import com.qidao.application.model.member.subscribe.SubscribeDTO;
import com.qidao.application.model.member.subscribe.SubscribeGetBlockListReq;
import com.qidao.application.model.member.subscribe.SubscribeRes;
import com.qidao.application.model.recommend.RecommendDynamicQueryPolicyReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.member.SubscribeService;
import com.qidao.application.service.recommend.IPolicyRecommendService;
import com.qidao.application.service.recommend.enums.RecommendConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.hadoop.MutableRecommendedItem;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QiDaoPolicyRecommendServiceImpl implements IPolicyRecommendService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Integer getType() {
        return RecommendConstantEnum.QIDAO_MODEL.getInt();
    }

    @Override
    public List<? extends RecommendedItem> listDynamic(RecommendDynamicQueryPolicyReq req) {
        LocalDate now = LocalDate.now();
//        RList<DynamicRecommendCacheDTO> redissonSourceRList = redissonClient.getList("recommend::dynamic::source::" + now.format(DatePattern.NORM_DATE_FORMATTER));
        RList<String> redissonSourceRList = redissonClient.getList("recommend::dynamic::source::" + now.format(DatePattern.NORM_DATE_FORMATTER));

        RList<Long> usedList = redissonClient.getList("recommend::dynamic::used::" + req.getMemberId());

        boolean firstSettingUsedList = CollUtil.isEmpty(usedList);
        List<DynamicRecommendCacheDTO> redissonList = redissonSourceRList.stream()
                .map(item -> JSONUtil.toBean(item, DynamicRecommendCacheDTO.class))
                .collect(Collectors.toList());

//        List<DynamicRecommendCacheDTO> redissonList = new ArrayList<>(redissonSourceRList);
        if (!firstSettingUsedList) {
            redissonList = redissonList.stream()
                    .filter(item -> !usedList.contains(item.getDynamicId()))
                    .collect(Collectors.toList());
        }

        // 排除屏蔽
        SubscribeGetBlockListReq blockQueryReq = SubscribeGetBlockListReq.builder()
                .memberId(req.getMemberId())
                .offset(1)
                .limit(10000)
                .build();
        SubscribeDTO blockList = subscribeService.getBlockList(blockQueryReq);

        List<Long> blockIdList = new ArrayList<>();
        if(null != blockList.getResList() && CollUtil.isNotEmpty(blockList.getResList().getResult())) {
            blockIdList = blockList.getResList().getResult().stream()
                .map(SubscribeRes::getSubscribeId)
                .collect(Collectors.toList());

        }
        for (DynamicRecommendCacheDTO bean : redissonList) {
            bean.calAllScore(req.getIndustryId(), req.getMemberLabels(), blockIdList);
        }

        List<DynamicRecommendCacheDTO> data = redissonList.stream()
                .filter(item -> item.getAllScore() > 0F)
                .sorted(Comparator.comparing(DynamicRecommendCacheDTO::getAllScore).reversed())
                .collect(Collectors.toList());

        if (data.size() > req.getHowMany()) {
            data = data.subList(0, req.getHowMany());
        }

        List<MutableRecommendedItem> recommendedItemList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MutableRecommendedItem bean = new MutableRecommendedItem(data.get(i).getDynamicId(), data.get(i).getAllScore());
            recommendedItemList.add(bean);

            usedList.add(data.get(i).getDynamicId());
        }

        if (firstSettingUsedList) {
            usedList.expire(7L, TimeUnit.DAYS);
        }
        return recommendedItemList;
    }

    @Override
    public List<? extends RecommendedItem> listScientificResearch(RecommendResearcherReq req) {
        /*
        1.0版本：
        职称+入驻时间，
        按照职称从高到低顺序排列，
        教授、研究员、高级实验师、副教授，副研究员，讲师、助理研究员、实验师、助教
        职称相同情况下，按照入驻时间先后排列，先入驻的排在前面。

        2.0逻辑排列：
        行业基础上，科研人员职称*X15 +
        认证状态，已认证*X10、等待认证*X5、未认证的不展示在推荐中）+
        实验室空间内容每条*X1.5 +
        合作状态已签署合同每个*X2.5 +
        学历（博士3、硕士2、本科1、大专1）+
        地理位置同省*X1+地理位置同市区*X3 +
        （已有身份用户公司或实验室科研方向及研究方向关键词数量匹配每个*3.5）
         =  排序规则，如果科研人员分数相同，按照入驻时间先后排序，先入驻的在上

        1.0内容少的状态下或2.0推荐方式算法中，实验室科研人员进入APP时，首页推荐科研人员的三个中，很有可能发现自己，注意科研人员进入使用时，不能自己关注自己，列表中的自己不显示关注按钮。（故意为之，结合了市场开发时候说的推广以及抬高科研人员心理预期）

         */

        List<Long> longs = memberService.listMemberWithoutSubscribe(req.getMemberId());

        RList<Long> usedRList = redissonClient.getList("recommend::research::used::" + req.getMemberId());
        boolean firstSet = CollUtil.isEmpty(usedRList);

        longs = longs.stream()
                .filter(id -> !usedRList.contains(id))
                .limit(3)
                .collect(Collectors.toList());

        usedRList.addAll(longs);

        if (firstSet) {
            usedRList.expire(1, TimeUnit.DAYS);
        }

        List<MutableRecommendedItem> list = new ArrayList<>();
        for (Long id : longs) {
            MutableRecommendedItem bean = new MutableRecommendedItem();
            bean.setItemID(id);
            list.add(bean);
        }

        return list;
    }
}
