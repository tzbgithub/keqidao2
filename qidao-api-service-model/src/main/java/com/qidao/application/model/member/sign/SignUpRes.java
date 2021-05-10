package com.qidao.application.model.member.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRes {
    private Long memberId;
    @ApiModelProperty(name = "backendImage", value = "用户名", required = false,example = "张三")
    private String name;
    @ApiModelProperty(name = "phone", value = "手机号", required = false,example = "19999999999")
    private String phone;
}
