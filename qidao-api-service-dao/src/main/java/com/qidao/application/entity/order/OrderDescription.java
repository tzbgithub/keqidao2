package com.qidao.application.entity.order;

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
public class OrderDescription {

    private Long id;

    private Long no;

    private Long productSkuId;

    private String productName;

    private String productSummary;

    private BigDecimal salePrice;

    private BigDecimal marketPrice;

    private String productImg;

    private Long memberId;

    private String memberName;

    private Integer status;

    private LocalDateTime payTime;

    private BigDecimal payPrice;

    private BigDecimal orderPrice;

    private Integer quantity;

    private String phone;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private Integer payWay;

    private Integer physicalFlag;

    private String thirdOrderNo;

    private Integer logisticsType;

    private String logisticsNo;

}
