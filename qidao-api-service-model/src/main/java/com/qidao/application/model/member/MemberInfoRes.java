package com.qidao.application.model.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "MemberInfoRes" , description = "[响应]根据会员ID查询会员基本信息")
public class MemberInfoRes {

    @ApiModelProperty(name = "id" , value = "主键ID")
    private Long id;

    @ApiModelProperty(name = "unionId" , value = "unionId")
    private String unionId;

    @ApiModelProperty(name = "headImage" , value = "头像")
    private String headImage;

    @ApiModelProperty(name = "backendImage" , value = "个人空间背景图")
    private String backendImage;

    @ApiModelProperty(name = "name" , value = "昵称")
    private String name;

    @ApiModelProperty(name = "education" , value = "学历")
    private String education;

    @ApiModelProperty(name = "industry" , value = "行业")
    private String industry;

    @ApiModelProperty(name = "industryNames" , value = "行业")
    private List<String> industryNames;

    @ApiModelProperty(name = "industryIds" , value = "行业")
    private List<Long> industryIds;

    @ApiModelProperty(name = "industryId" , value = "行业ID")
    private Long industryId;

    @ApiModelProperty(name = "organization" , value = "组织名称")
    private String organizationName;

    @ApiModelProperty(name = "CityName" , value = "地址市名称")
    private String CityName;

    @ApiModelProperty(name = "areaName" , value = "地址区名称")
    private String areaName;

    @ApiModelProperty(name = "organizationId" , value = "组织id")
    private Long organizationId;

    @ApiModelProperty(name = "position" , value = "职位")
    private String position;

    @ApiModelProperty(name = "belong" , value = "所属单位")
    private String belong;

    @ApiModelProperty(name = "labels" , value = "标签")
    private List<MemberLabelRes> labels;

    @ApiModelProperty(name = "organizationType" ,value = "角色信息 0-实验室 1-企业 null-普通用户")
    private Integer organizationType;

    @ApiModelProperty(name = "level" , value = "等级-1：冻结 0-普通会员 1-vip会员")
    private Integer level;

    @ApiModelProperty(name = "vipEndTime" , value = "会员到期时间")
    private LocalDateTime vipEndTime;

    @ApiModelProperty(name = "role" , value = "角色：0-普通用户；1-主管；2-管理员 3 行政人员")
    private Integer role;

    @ApiModelProperty(name = "phone" , value = "手机号")
    private String phone;

    @ApiModelProperty(value = "实验室审核状态")
    private Integer verifyStatus;

    @ApiModelProperty(value = "证书")
    private List<String> qualifications;
}
