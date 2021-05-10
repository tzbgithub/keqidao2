package com.qidao.application.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单产品订单生成 响应
 */
@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateOneProductRes {
    @ApiModelProperty("订单ID")
    private Long orderId;
    @ApiModelProperty("订单no")
    private Long orderNo;
}
