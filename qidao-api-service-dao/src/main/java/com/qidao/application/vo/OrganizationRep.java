package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
/**
 * 根据会员ID查询组织机构信息
 */
public class OrganizationRep {
    @ApiModelProperty(name = "flag", value = "推送开关 0-关 1-开")
    private  boolean flag;
    @ApiModelProperty(name = "type", value = "组织机构类型")
    private  Integer type;
    @ApiModelProperty(name = "roleId", value = "该会员在此组织机构的身份")
    private  Integer roleId;
}
