package com.qidao.application.model.member;

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
@ApiModel("企业会员信息响应对象")
public class EnterpriseMemberRes {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "头像")
    private String headImage;

    @ApiModelProperty(value = "角色 0-普通用户；1-主管；2-管理员 3 行政人员 4-助手")
    private Integer role;

}
