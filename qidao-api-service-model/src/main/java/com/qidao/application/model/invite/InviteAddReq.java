package com.qidao.application.model.invite;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("新增邀请信息请求对象")
public class InviteAddReq {

    @NotNull(message = "邀请类型不能为空")
    @ApiModelProperty(value = "邀请类型  0-实验室老师邀请助手 1-实验室老师邀请老师 2-企业邀请成员 3-助手邀请老师")
    private Integer type;

    @NotNull(message = "邀请人ID不能为空")
    @ApiModelProperty(value = "邀请人ID" ,required = true , example = "123")
    private Long memberId;

    @NotNull(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号" ,required = true , example = "12345678998")
    @Pattern(regexp = "(1[3456789]\\d{9}$)", message = "手机号码格式不正确,请核对后重新输入!")
    private String invitePhone;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码" , required = true , example = "123")
    private String code;

    @ApiModelProperty(value = "用户昵称")
    @Size(max = 16 , message = "用户名称过长，请适当精简")
    private String inviteName;

    @Size(max = 16 , message = "工作岗位过长，请适当精简")
    @ApiModelProperty(value = "工作岗位")
    private String invitePosition;
}
