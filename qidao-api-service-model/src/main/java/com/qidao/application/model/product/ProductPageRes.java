package com.qidao.application.model.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("产品列表响应对象")
public class ProductPageRes {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "产品名")
    private String name;

    @ApiModelProperty(value = "产品简介")
    private String summary;

    @ApiModelProperty(value = "图片")
    private String imgs;

    @ApiModelProperty(value = "销售价")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "计费方式：0-时间；1-次数")
    private Integer type;

    @ApiModelProperty(value = "type=0 存放本产品的服务时间(单位：分)；type=1 本产品的最大服务次数")
    private Integer serverVal;

    @ApiModelProperty(value = "0-都可购买,1-只限实验室下属导师购买,2-只限企业机构购买")
    private Integer permission;

    @ApiModelProperty(value = "产品附加数据： hot-热门 sale-特卖优惠")
    private List<String> extra;

    @ApiModelProperty(value = "第三方产品ID（苹果）")
    private String thirdProductId;
}
