package com.qidao.application.model.member.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class AutoSignReq {
    @ApiModelProperty(value = "登录方式",notes = " 1:微信第三方union id;",example = "1")
    private Integer type;

    private String ip;

    private String machineCode;

    private String version;
    @Pattern(regexp ="^1[3-9]\\d{9}$",message = "手机号格式不匹配")
    @NotNull(message = "手机号不能为空")
    private String phone;
    private Integer canal;
}
