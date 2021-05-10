package com.qidao.application.model.invite;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("邀请用户信息")
public class InviteMemberInfo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "组织机构ID")
    private Long organizationId;

    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "生成时间")
    private LocalDateTime generatorTime;

    @ApiModelProperty(value = "组织机构类型 0-实验室老师邀请助手 1-实验室老师邀请老师 2-企业邀请成员 3-助手邀请老师")
    private Integer type;

    @ApiModelProperty(value = "短链接")
    private String shortUrl;

}
