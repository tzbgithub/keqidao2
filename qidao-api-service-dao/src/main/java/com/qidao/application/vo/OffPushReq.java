package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffPushReq  {
    @ApiModelProperty(name = "status", value = "推送开关 0-关 1-开", required = true,example = "1")
    @NotNull(message = "状态不可为空")
    private  Integer status;
    @ApiModelProperty(value = "id",example = "1234567",required = true)
    @NotNull(message = "id不能为空")
    private Long id;
}
