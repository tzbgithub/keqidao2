package com.qidao.application.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bytecode.assign.primitive.VoidAwareAssigner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("订单详情响应对象")
public class OrderDescriptionRes {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private Long no;

    @ApiModelProperty(value = "产品ID")
    private Long productSkuId;

    @ApiModelProperty(value = "产品名字")
    private String productName;

    @ApiModelProperty(value = "产品简介")
    private String productSummary;

    @ApiModelProperty(value = "产品销售价")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "产品市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "产品图片")
    private String productImg;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "用户名字")
    private String memberName;

    @ApiModelProperty(value = "订单状态：0-未支付；1-已支付；2-已关闭；3-已退款；4-申诉")
    private Integer status;

    @ApiModelProperty(value = "支付时间")
    private String payTime;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payPrice;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "购买产品数量")
    private Integer quantity;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "vip开始时间")
    private String vipStartTime;

    @ApiModelProperty(value = "vip结束时间")
    private String vipEndTime;

    @ApiModelProperty(reference = "com.qidao.application.model.pay.enums.PayWayEnum")
    private Integer payWay;

    @ApiModelProperty(value = "实物订单 0-否 1-是")
    private Integer physicalFlag;

    @ApiModelProperty(value = "第三方订单号")
    private String thirdOrderNo;

    @ApiModelProperty(value = "物流方式 0-顺丰")
    private Integer logisticsType;

    @ApiModelProperty(value = "物流编号")
    private String logisticsNo;

}
