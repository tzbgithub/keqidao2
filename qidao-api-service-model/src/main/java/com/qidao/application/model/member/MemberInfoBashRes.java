package com.qidao.application.model.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


/**
 * 用户基本信息 响应类
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoBashRes {
    @ApiModelProperty(name = "id" , value = "id")
    private Long id;
    @ApiModelProperty(name = "mobile" , value = "手机号")
    private String mobile;
    @ApiModelProperty(name = "headImage" , value = "头像")
    private String headImage;
    @ApiModelProperty(name = "name" , value = "昵称")
    @Length(max = 16,message = "长度不能超过16")
    private String name;
    @ApiModelProperty(name = "email" , value = "邮箱")
    private String email;

}
