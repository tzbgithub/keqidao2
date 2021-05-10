package com.qidao.application.model.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CanalReq {
    @ApiModelProperty(name = "version", value = "版本号", required = false,example = "1.1.1")
    private  String version;
    @NotNull(message = "渠道名不能为空")
    @ApiModelProperty(name = "canalName", value = "渠道名", required = true,example = "Android")
    private  String canalName;

}
