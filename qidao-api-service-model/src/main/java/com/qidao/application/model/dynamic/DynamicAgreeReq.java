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
@ApiModel("动态点赞请求参数对象")
public class DynamicAgreeReq {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "134278105071618")
    private Long memberId;

    @NotNull(message = "动态ID不能为空")
    @ApiModelProperty(value = "动态ID" , required = true , example = "130894234976257")
    private Long dynamicId;

}
