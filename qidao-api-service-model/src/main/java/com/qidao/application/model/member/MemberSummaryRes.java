package com.qidao.application.model.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel
public class MemberSummaryRes {
    @ApiModelProperty(name = "id" , value = "主键ID")
    private Long id;

    @ApiModelProperty(name = "headImage" , value = "头像")
    private String headImage;

    @ApiModelProperty(name = "name" , value = "昵称")
    @Length(max = 16,message = "长度不能超过16")
    private String name;
}
