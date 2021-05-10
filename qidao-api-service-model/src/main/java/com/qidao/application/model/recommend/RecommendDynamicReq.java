package com.qidao.application.model.recommend;

import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Autuan.Yu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class RecommendDynamicReq {
    @ApiModelProperty(value = "查询条目数",example = "10",required = true)
    @NotNull(message = "查询条目数 不能为空")
    @Min(value = 1L,message = "查询条目数格式不正确")
    private Integer howMany;

    @ApiModelProperty(value = "会员ID",example = "123",required = true)
    @NotNull(message = "会员ID 不能为空")
    @Min(value = 1L,message = "会员ID格式不正确")
    private Long memberId;
}
