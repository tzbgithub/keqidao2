package com.qidao.application.model.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * 策略模式 支付 dto
 */
@ApiModel
@Data
public class PolicyPayDTO {
    @NotNull(message = "支付方式 不能为空")
    @ApiModelProperty(value = "支付方式;<br>0-微信APP支付<br> 1 ：微信小程序支付<br>2-微信H5支付<br>3-支付宝app支付<br>4-支付宝 H5<br>5-银联支付<br>6-支付宝网页支付<br>7-微信native支付",
            required = true,example = "0")
    private Integer type;

    @NotNull(message = "会员ID 不能为空")
    @ApiModelProperty(value = "会员ID",required = true,example = "1")
    private Long memberId;

    @NotNull(message = "订单号 不能为空")
    @Min(value = 1,message = "订单号 格式不正确")
    @ApiModelProperty(value = "订单号",required = true,example = "1")
    private Long orderNo;

    @ApiModelProperty(value = "产品名称",example = "季度高级会员")
    private String productName;

}
