package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindEmailReq {
    @NotNull(message = "用户不能为空")
    @ApiModelProperty(name = "memberId", value = "用户表示", required = true, example = "234234234234234")
    private Long memberId;

    @NotNull(message = "邮箱不能为空")
    @Size(max = 32, message = "邮箱最大32字符")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "234234234234234")
    private String email;

}
