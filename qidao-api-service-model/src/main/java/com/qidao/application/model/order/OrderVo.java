package com.qidao.application.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单对象")
public class OrderVo {

    @ApiModelProperty(value ="主键")
    private Long id;

    @ApiModelProperty(value ="创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value ="修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value ="创建者")
    private Long createBy;

    @ApiModelProperty(value ="修改者" )
    private Long updateBy;

    @ApiModelProperty(value ="逻辑删除 0-否 1-是")
    private Byte delFlag;

    @ApiModelProperty(value ="订单流水号")
    private Long no;

    @ApiModelProperty(value ="产品ID")
    private Long productSkuId;

    @ApiModelProperty(value ="用户ID")
    private Long memberId;

    @ApiModelProperty(value ="订单状态：0-未支付；1-已支付 2-已关闭 3-已退款 4-申诉 5-已完成 6-已发货")
    private Integer status;

    @ApiModelProperty(value ="会员名称" )
    private String memberName;

    @ApiModelProperty(value ="支付时间")
    private String payTime;

    @ApiModelProperty(value ="支付金额")
    private BigDecimal payPrice;

    @ApiModelProperty(value ="订单金额" )
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "购买产品数量")
    private Integer quantity;

    @ApiModelProperty(value = "支付方式：0-微信APP支付 1-微信小程序支付 2-微信H5支付 3-支付宝APP支付 4-支付宝H5支付 5-银联支付")
    private Integer payWay;

    @ApiModelProperty(value = "产品名称")
    private String productSkuName;

    @ApiModelProperty(value = "vip开始时间")
    private String vipStartTime;

    @ApiModelProperty(value = "vip结束时间")
    private String vipEndTime;

    @ApiModelProperty(value = "vip最终结束时间")
    private String finalVipEndTime;

    @ApiModelProperty(value = "实物订单 0-否 1-是")
    private Byte physicalFlag;

    @ApiModelProperty(value = "第三方订单号")
    private String thirdOrderNo;

    @ApiModelProperty(value = "物流编号")
    private String logisticsNo;

    @ApiModelProperty(value = "物流方式 0-顺丰")
    private Integer logisticsType;
}
