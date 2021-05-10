package com.qidao.application.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class LabOrderDoneReq {
    @ApiModelProperty(value = "订单号",example = "1234",required = true)
    @NotNull(message = "订单号 不能为空")
    @Min(message = "订单号 不正确",value = 1L)
    private Long orderNo;

    @NotNull(message = "会员ID 不能为空")
    @Min(message = "会员ID 不正确",value = 1L)
    @ApiModelProperty(value = "会员ID",example = "1234",required = true)
    private Long memberId;
}
