package com.qidao.application.model.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PayRes {
    @ApiModelProperty(value = "支付方式<br>0-微信APP支付<br> 1-微信小程序支付<br>2-微信H5支付付<br>3-支付宝app支付<br>4-支付宝 H5<br>5-银联支付")
    private Integer payWay;

//    @ApiModelProperty("第三方订单号/流水号<br>微信支付：即为 ‘prepay_id’")
//    private String payWayOrderNo;

    @ApiModelProperty("订单号")
    private Long orderNo;

//    @ApiModelProperty("订单ID")
//    private Long orderId;

    @ApiModelProperty("币种;CNY-人民币")
    private String currency;

    @ApiModelProperty("订单总金额，单位：元")
    private BigDecimal totalAmount;

    @ApiModelProperty("唤醒第三方APP支付 参数")
    private InvokeParam invokeParam;

}
