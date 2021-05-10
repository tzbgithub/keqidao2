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
@ApiModel(value = "MemberLabelRes" , description = "[响应]根据会员ID查询会员基本信息标签")
public class MemberLabelRes {

    @ApiModelProperty(name = "id" , value = "主键ID")
    private Long id;

    @ApiModelProperty(name = "val" , value = "展示值")
    private String val;
}
