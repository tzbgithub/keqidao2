package com.qidao.application.model.easemob;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class EasemobStatusReq {
    @ApiModelProperty(value = "环信用户名 （memberId)",example = "1234",required = true)
    @NotNull(message = "username 不能为空")
    private String username;
}
