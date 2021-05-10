package com.qidao.application.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSkuReq {
    @NotNull(message = "产品名称不能为空")
    @ApiModelProperty(name = "name", value = "产品名称",example="白银",required = true)
    private String name;
    @NotNull(message = "产品简介不能为空")
    @ApiModelProperty(name = "summary", value = "产品简介",example="白银至尊会员",required = true)
    private String summary;
    @NotNull(message = "产品图片不能为空")
    @ApiModelProperty(name = "imgs", value = "产品图片",example="['xxx.jpg']",required = true)
    private List<String> imgs;
    @NotNull(message = "销售价不能为空")
    @DecimalMin(value = "1")
    @ApiModelProperty(name = "salePrice", value = "销售价",example="999.00",required = true)
    private BigDecimal salePrice;
    @DecimalMin(value = "1")
    @NotNull(message = "市场价不能为空")
    @ApiModelProperty(name = "marketPrice", value = "市场价",example="999.00",required = true)
    private BigDecimal marketPrice;
    @NotNull(message = "请选择计费方式")
    @ApiModelProperty(name = "type", value = "计费方式：0-时间；1-次数",example="0",required = true)
    private Integer type;
    @ApiModelProperty(name = "serverVal", value = "type=0 存放本产品的服务时间(单位：分)；type=1 本产品的最大服务次数 ",example="999",required = false)
    private Integer serverVal;
    @ApiModelProperty(name = "permission", value = "0-都可购买 1-只限实验室下属导师购买 2-只限企业机构购买 默认传0",example="0",required = false)
    private Integer permission;
    @NotNull(message = "请输入库存容量")
    @ApiModelProperty(name = "stock", value = "库存",example="0",required = true)
    private Integer  stock;

}
