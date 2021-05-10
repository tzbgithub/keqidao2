package com.qidao.application.model.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("会员详细信息对象")
public class MemberDetailedRes {
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

    @ApiModelProperty(name = "educationId" , value = "学历ID")
    private Long educationId;

    @ApiModelProperty(name = "industry" , value = "技术可应用行业")
    private String industry;

    @ApiModelProperty(name = "industryNames" , value = "技术可应用行业")
    private List<String> industryNames;

    @ApiModelProperty(name = "organization" , value = "组织名称")
    private String organizationName;

    @ApiModelProperty(name = "industryId" , value = "技术可应用行业ID")
    private Long industryId;

    @ApiModelProperty(name = "industryIds" , value = "技术可应用行业ID")
    private List<Long> industryIds;

    @ApiModelProperty(name = "position" , value = "职称")
    private String position;

    @ApiModelProperty(name = "positionId" , value = "职称ID")
    private Long positionId;

    @ApiModelProperty(name = "belong" , value = "所属单位")
    private String belong;

    @ApiModelProperty(name = "email" , value = "邮箱")
    private String email;

    @ApiModelProperty(name = "direction" , value = "科研方向")
    private String direction;

    @ApiModelProperty(name = "labels" , value = "可提供的技术服务")
    private List<MemberLabelRes> labels;

    @ApiModelProperty(name = "organizationType" ,value = "角色信息 0-实验室 1-企业 null-没有加入组织机构")
    private Integer organizationType;

    @ApiModelProperty(name = "level" , value = "等级-1：冻结 0-普通会员 1-vip会员")
    private Integer level;

    @ApiModelProperty(name = "vipEndTime" , value = "vip到期时间")
    private LocalDateTime vipEndTime;

    @ApiModelProperty(name = "organizationVipEndTime" , value = "所属组织vip到期时间")
    private LocalDateTime organizationVipEndTime;

    @ApiModelProperty(name = "role" , value = "角色：0-普通用户；1-主管；2-管理员 3 行政人员")
    private Integer role;

    @ApiModelProperty(name = "phone" , value = "手机号")
    private String phone;

    @ApiModelProperty(name = "organizationId" , value = "组织机构ID")
    private String organizationId;

    @ApiModelProperty(name = "provinceCode" , value = "省编码")
    private String provinceCode;

    @ApiModelProperty(name = "provinceName" , value = "省名字")
    private String provinceName;

    @ApiModelProperty(name = "cityCode" , value = "市编码")
    private String cityCode;

    @ApiModelProperty(name = "cityName" , value = "市名字")
    private String cityName;

    @ApiModelProperty(name = "areaCode" , value = "区编码")
    private String areaCode;

    @ApiModelProperty(name = "areaName" , value = "区名字")
    private String areaName;

    @ApiModelProperty(name = "verifyStatus" , value = "入驻实验室审核状态： 0-待审核 1-审核失败 2-审核成功 3-审核中")
    private Integer verifyStatus;
    @ApiModelProperty(name = "imEasemobUsername" , value = "环信用户名")
    private String imEasemobUsername;
    @ApiModelProperty(name = "password" , value = "环信密码")
    private String password;

    @ApiModelProperty(value = "证书")
    private List<String> qualifications;

}
