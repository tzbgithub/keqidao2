package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AuthOrganizationDto {
    @NotNull(message = "请传入操作人")
    @ApiModelProperty(name = "memberId", value = "管理员", required = true,example = "234234234234234")
    private Long memberId;
    @ApiModelProperty(name = "imgs", value = "认证图片", required = true,example = "[1,2,3]")
    @NotNull(message = "请上传图片")
    private List<String> imgs;

}
