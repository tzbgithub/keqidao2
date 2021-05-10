package com.qidao.application.entity.order;

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
public class CustomOrder {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte delFlag;

    private Long no;

    private Long productSkuId;

    private Long memberId;

    private Integer status;

    private String memberName;

    private LocalDateTime payTime;

    private BigDecimal payPrice;

    private BigDecimal orderPrice;

    private Integer quantity;

    private Integer payWay;

    private String productSkuName;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private Byte physicalFlag;

    private String thirdOrderNo;

    private LocalDateTime finalVipEndTime;

    private Integer logisticsType;

    private String logisticsNo;
}
