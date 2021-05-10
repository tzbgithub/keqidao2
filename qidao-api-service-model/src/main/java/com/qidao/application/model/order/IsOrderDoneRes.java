package com.qidao.application.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class IsOrderDoneRes {
    @ApiModelProperty(value = "是否已完成;只有<strong>已支付并完成回调</strong>的订单响应状态(data)是 true ,其他任何情况都是返回 false")
    private Boolean isDone;
}
