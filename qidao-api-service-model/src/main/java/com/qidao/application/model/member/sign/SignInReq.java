package com.qidao.application.model.member.sign;

import com.google.inject.internal.asm.$ClassWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Autuan.Yu
 */
@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SignInReq {
    @ApiModelProperty(name = "type",value = "登录方式",notes = "0 手机号/账号 + 验证码登录 " +
            "1 微信第三方union id 登录 " +
            "2 手机号 + 机器码 登录  " +
            "3 手机号 + 密码登录" +
            "4 token登录 " +
            "5 账号(编号) + 密码登录 目前只有 0 3 ，5 实现",example = "1")
    @NotNull(message = "请选择一个登录途径")
    private Integer type;
    @ApiModelProperty(name = "account",value = "用户名",example = "1")
    private String account;
    @ApiModelProperty(name = "password",value = "密码",example = "1")
    private String password;
    @ApiModelProperty(name = "ip",value = "ip地址",example = "1")
    private String ip;
    @NotNull(message = "请传入机器识别码")
    @ApiModelProperty(name = "machineCode",value = "机器识别码",example = "1sd",required = true)
    private String machineCode;
    @NotNull(message = "请传入当前版本号")
    @Length(max = 16,message = "长度不可超过16位")
    @ApiModelProperty(name = "version",value = "版本号",example = "1",required = true)
    private String version;
    @Pattern(regexp = "(1[3456789]\\d{9}$)", message = "手机号码格式不正确,请核对后重新输入!")
    @ApiModelProperty(name = "phone",value = "手机号",example = "12342342423")
    private String phone;
    @NotNull(message = "请传入渠道服")
    @ApiModelProperty(name = "canal",value = "渠道服",example = "1",required = true)
    private Integer canal;
    @ApiModelProperty(name = "code",value = "验证码 验证码登录必须传入",example = "1",required = false)
    private String code;
}
