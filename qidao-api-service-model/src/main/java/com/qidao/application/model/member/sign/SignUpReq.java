package com.qidao.application.model.member.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {
    @NotNull(message = "用户名不可以为空")
    @ApiModelProperty(name = "name",value = "用户名",example = "1",required = true)
    private String name;
    @ApiModelProperty(name = "password",value = "密码",example = "1")
    private String password;
    @ApiModelProperty(name = "ip",value = "ip地址",example = "1")
    private String ip;
    @ApiModelProperty(name = "code",value = "验证码",example = "123456")
    @NotNull(message = "请填入验证码")
    private String code;
    private String machineCode;
    @NotNull(message = "请传入当前版本号")
    @Length(max = 16,message = "长度不可超过16位")
    @ApiModelProperty(name = "version",value = "版本号",example = "1",required = true)
    private String version;
    @Pattern(regexp ="^1[3-9]\\d{9}$",message = "手机号格式不匹配")
    @NotNull(message = "手机号不能为空")
    @ApiModelProperty(name = "phone",value = "手机号",example = "12342342423",required = true)
    private String phone;
    @NotNull(message = "请传入渠道服")
    @ApiModelProperty(name = "canal",value = "渠道服",example = "1",required = true)
    private Integer canal;
}
