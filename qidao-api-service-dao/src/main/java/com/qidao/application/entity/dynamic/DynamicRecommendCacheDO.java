package com.qidao.application.entity.dynamic;

import lombok.Data;

@Data
public class DynamicRecommendCacheDO {
    private Long dynamicId;
    private Long pushMemberId;
    private Integer dayDiff;
    private Long technologyMaturityId;
    private Long industryIdFirst;
    private Long industryIdSecond;
    private Long cityId;
    private Double pushTimeScore;
    private Double technologyMaturityScore;
    private String labels;
}
