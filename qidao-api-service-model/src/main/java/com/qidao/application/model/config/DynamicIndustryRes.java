package com.qidao.application.model.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("动态筛选行业")
public class DynamicIndustryRes {

    @ApiModelProperty(value = "行业")
    private List<ScreenIndustryRes> industry;

    @ApiModelProperty(value = "热门行业")
    private List<ChildIndustryRes> hotIndustry;

}
