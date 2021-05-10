package com.qidao.application.model.recommend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDynamicQueryPolicyReq {
    private Long dynamicId;
    private Long memberId;
    private Integer howMany;

    private  Long industryId;
    private List<String> memberLabels;
}
