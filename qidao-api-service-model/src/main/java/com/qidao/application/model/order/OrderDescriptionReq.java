package com.qidao.application.model.order;

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
@ApiModel("订单详情请求对象")
public class OrderDescriptionReq {

    @NotNull(message = "订单流水号不能为空")
    @ApiModelProperty(value = "订单流水号" , required = true , example = "534534543546")
    private Long orderNo;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "123")
    private Long memberId;

}
