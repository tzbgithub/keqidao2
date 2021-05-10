package com.qidao.application.entity.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("实验室老师助手/企业成员返回对象")
public class OrganizationMember {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "头像")
    private String headImage;

    @ApiModelProperty(value = "角色 0-普通用户；1-主管；2-管理员 3 行政人员 4-助手")
    private Integer role;

}
