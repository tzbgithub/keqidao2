package com.qidao.application.model.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("短信请求对象")
public class SmsSendReq {
   @ApiModelProperty(value = "手机号" , required = true , example = "18721204790")
   @Pattern(regexp = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))",message = "手机号码格式不正确,请核对后重新输入!")
   @NotNull(message = "手机号不能为空")
   private String phone;

//   @NotNull(message = "用户ID 不能为空")
//   @ApiModelProperty(value = "用户ID",required = true,example = "1234")
//   @Min(value = 1,message = "用户ID 格式不正确")
//   private Long memberId;
   //^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\d{8}$
}
