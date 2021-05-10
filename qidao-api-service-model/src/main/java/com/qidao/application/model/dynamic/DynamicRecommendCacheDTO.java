package com.qidao.application.model.dynamic;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.model.config.enums.TechnologyMaturityEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * 用于推荐的缓存动态信息类
 */
@Data
public class DynamicRecommendCacheDTO implements Serializable, Comparable<DynamicRecommendCacheDTO> {
    /**
     * 动态ID
     */
    private Long dynamicId;
    /**
     * 动态发布人ID
     */
    private Long pushMemberId;
    /**
     * 差距天数 <br>
     * 0: 今天 <br>
     * 正： 未来天数 (n) <br>
     * 负： 过去天数 (n) <br>
     */
    private Integer dayDiff;

    private List<String> labelList;

    /**
     * 技术成熟度ID
     */
    private Long technologyMaturityId;

    /**
     * TODO 分值计算 实现 (Autuan)[3.16.start]
     * 一级行业
     */
    private Long industryIdFirst;

    /**
     * 二级行业ID
     */
    private Long industryIdSecond;

    /**
     * TODO 分值计算 实现 (Autuan)[3.16.start]
     * 城市ID
     */
    private Long cityId;

    private Float pushTimeScore;
    private Float technologyMaturityScore;
    private Float allScore ;

    /**
     * 实验室动态发布时间当日  1 <br>
     * 实验室动态发布时间1-3日  0.8 <br>
     * 实验室动态发布时间3-7日  0.5 <br>
     */
    public void calPushTimeScore() {
        Float score = 0F;
        if (null != this.dayDiff) {
            switch (this.dayDiff) {
                case 0:
                    score = 1F;
                    break;
                case -1:
                case -2:
                case -3:
                    score = 0.8F;
                    break;
                case -4:
                case -5:
                case -6:
                case -7:
                    score = 0.5F;
                    break;
                default:
                    break;
            }
        }
        this.pushTimeScore = score;
    }

    public void calTechnologyMaturityScore() {
        this.technologyMaturityScore = TechnologyMaturityEnum.calScore(this.technologyMaturityId);
    }

    public void calAllScore(Long industrySecond, List<String> memberLabels,List<Long> blockIdList) {
        // 被屏蔽的动态为负值
        if(CollUtil.isNotEmpty(blockIdList)
                && null != pushMemberId
                && blockIdList.contains(pushMemberId)
        ) {
            setAllScore(-1F);
            return;
        }
        this.calTechnologyMaturityScore();
        this.calPushTimeScore();

        Float industrySecondScore = industrySecond.equals(industryIdSecond) ? 3F : 0F;

        float labelScore = 0F;
        if (CollUtil.isNotEmpty(memberLabels) && CollUtil.isNotEmpty(labelList)) {
            long count = labelList.stream().filter(memberLabels::contains)
                    .count();
            labelScore = (float) count * 5;
        }

        Float allScore = industrySecondScore + this.pushTimeScore + this.technologyMaturityScore + labelScore;

        setAllScore(allScore);
    }


    @Override
    public int compareTo(DynamicRecommendCacheDTO o) {
        return allScore.compareTo(o.allScore);
    }

    public DynamicRecommendCacheDTO() {
        this.allScore = 0F;
        this.pushTimeScore = 0F;
        this.technologyMaturityScore = 0F;
    }
}
