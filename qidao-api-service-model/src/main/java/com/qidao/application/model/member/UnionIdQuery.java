package com.qidao.application.model.member;

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
@ApiModel("unionId查询会员信息请求对象")
public class UnionIdQuery {

    @NotNull(message = "unionId不能为空")
    @ApiModelProperty(value = "微信唯一标识" , example = "asd123" , required = true)
    private String unionId;

}
