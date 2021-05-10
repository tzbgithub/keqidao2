package com.qidao.application.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class OrderNoReq {
    @NotNull(message = "订单号不能为空")
    @Min(value = 1L,message = "订单号不合法")
    @ApiModelProperty(value = "查询订单号",required = true)
    private Long orderNo;
}
