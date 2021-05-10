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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("随机动态请求对象")
public class RandomDynamicReq {

    @ApiModelProperty(value = "查询条数",example = "10",required = true)
    @NotNull(message = "查询条数不能为空")
    private Integer limit;

    @ApiModelProperty(value = "用户ID" , example = "123")
    private Long memberId;
}
