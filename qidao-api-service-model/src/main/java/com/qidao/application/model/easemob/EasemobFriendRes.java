package com.qidao.application.model.easemob;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class EasemobFriendRes {
    @ApiModelProperty(value = "会员ID",example = "1234")
    private Long memberId;
    @ApiModelProperty(value = "会员头像",example = "2021/02/23/72251614045544723_.jpg")
    private String headImage;
    @ApiModelProperty(value = "会员昵称",example = "张三")
    private String name;
    @ApiModelProperty(value = "环信用户昵称",example = "1234")
    private String imEasemobUsername;
    @ApiModelProperty(value = "类别：0-实验室；1-公司",example = "0")
    private Integer type;
    @ApiModelProperty(value = "组织机构ID")
    private Long organizationId;
    @ApiModelProperty(value = "是否是第一次添加好友",example = "false")
    private Boolean firstAdd;
}
