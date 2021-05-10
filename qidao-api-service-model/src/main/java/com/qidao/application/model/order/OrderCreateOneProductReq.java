package com.qidao.application.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 单产品订单生成
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OrderCreateOneProductReq {
    @ApiModelProperty(value = "会员ID",required = true,example = "1001")
    @NotNull(message = "会员ID 不能为空")
    private Long memberId;

    @ApiModelProperty(value = "订单产品",required = true,example = "3001")
    @NotNull(message = "购买产品不能为空")
    private Long productSkuId;

    @ApiModelProperty(value = "购买数量",required = true,example = "1")
    @NotNull(message = "数量不能为空")
    @Min(message = "数量最小为1",value = 1)
    private Integer quantity;

    @ApiModelProperty(value = "收货地址 实物订单必填")
    private OrderAddressDTO address;
}
