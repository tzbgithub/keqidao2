package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationBaseDataRep {
    @ApiModelProperty(name = "id", value = "会员主键")
    private  Long id;
    @ApiModelProperty(name = "name", value = "实验室名称")
    private  String name;
    @ApiModelProperty(name = "belong", value = "所属单位")
    private  String belong;
    @ApiModelProperty(name = "scaleId", value = "规模标识")
    private  Long scaleId;
    @ApiModelProperty(name = "val", value = "标签(该字段不需要展示)")
    private  String val;
}
