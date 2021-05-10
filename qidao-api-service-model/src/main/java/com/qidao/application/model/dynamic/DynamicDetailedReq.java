package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("动态详情请求参数")
public class DynamicDetailedReq {

    @NotNull(message = "动态ID不能为空")
    @ApiModelProperty(value = "动态ID", required = true ,example = "132159557926913")
    private Long dynamicId;

    @ApiModelProperty(value = "用户ID"  , example = "134273413742594")
    private Long memberId;

}
