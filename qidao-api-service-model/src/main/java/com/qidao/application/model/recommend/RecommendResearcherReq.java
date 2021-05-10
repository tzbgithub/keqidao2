package com.qidao.application.model.recommend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class RecommendResearcherReq {
    @ApiModelProperty(value = "会员ID",example = "1234",required = true)
    @NotNull(message = "会员ID 不能为空")
    @Min(value = 1L,message = "会员ID格式不正确")
    private Long memberId;
}
