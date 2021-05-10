package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicPageVo {
    //动态ID集合  非必填
    private  List<Long> dynamicIds;
    //动态-用户ID集合 非必填
    private  List<Long> subscribeIds;
    //动态-行业ID
    private  Long industryId;
    //渠道-ID 必填
    private  Long channelId;
}
