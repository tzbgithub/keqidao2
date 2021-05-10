package com.qidao.application.vo;

import com.qidao.application.entity.member.OrganizationMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("实验室空间")
public class OrganizationSpaceRes {

    @ApiModelProperty(name = "name", value = "名称")
    private String name;

    @ApiModelProperty(name = "status", value = "0-未认证 1-认证中 2-已认证")
    private Integer status;

    @ApiModelProperty(name = "labels", value = "标签")
    private List<LinkLabelVo> labels;

    @ApiModelProperty(name = "tutors", value = "实验室成员基本信息")
    private List<TutorInfoReq> tutors;

    @ApiModelProperty(name = "serverSize", value = "承接项目数量")
    private Integer serverSize;

    @ApiModelProperty(name = "address", value = "地址")
    private String address;

    @ApiModelProperty(name = "techScale", value = "技术规模")
    private String techScale;

    @ApiModelProperty(name = "scale", value = "规模")
    private String scale;

    @ApiModelProperty(name = "funds", value = "研究经费")
    private String funds;

    @ApiModelProperty(value = "地址省")
    private String addressProvinceName;

    @ApiModelProperty(value = "市")
    private String addressCityName;

    @ApiModelProperty(value = "区")
    private String addressAreaName;

    @ApiModelProperty(value = "实验室助手/企业成员")
    private List<OrganizationMember> members;

}
