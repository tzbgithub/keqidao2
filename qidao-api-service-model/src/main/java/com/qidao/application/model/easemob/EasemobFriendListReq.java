package com.qidao.application.model.easemob;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class EasemobFriendListReq {
    @ApiModelProperty(value = "会员ID",required = true,example = "1234")
    @NotNull(message = "会员ID 不能为空")
    @Min(value = 1L,message = "会员ID 不正确")
    private Long memberId;
}
