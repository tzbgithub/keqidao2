package com.qidao.application.model.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("组织机构详情返回对象")
public class OrganizationDetailRes {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "所属单位")
    private String belong;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "证书")
    private String license;

    @ApiModelProperty(value = "企业认证状态 0-未认证 1-认证中 2-已认证")
    private Integer status;

    @ApiModelProperty(value = "vip开始时间")
    private String vipStartTime;

    @ApiModelProperty(value = "vip结束时间")
    private String vipEndTime;

    @ApiModelProperty(value = "注册时间")
    private String signTime;

    @ApiModelProperty(value = "地址详情")
    private String addressDetail;

    @ApiModelProperty(value = "省")
    private String provinceName;

    @ApiModelProperty(value = "市")
    private String cityName;

    @ApiModelProperty(value = "区")
    private String areaName;

    @ApiModelProperty(value = "行业备注")
    private String industryRemark;

    @ApiModelProperty(value = "空间背景")
    private String backendImage;


    @ApiModelProperty(value = "规模ID")
    private Long scaleId;


    @ApiModelProperty(value = "规模")
    private String scaleName;

    @ApiModelProperty(value = "标签")
    private List<String> label;

    @ApiModelProperty(value = "行业")
    private List<String> industry;

}
