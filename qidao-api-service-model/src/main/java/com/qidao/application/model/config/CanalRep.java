package com.qidao.application.model.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CanalRep {
    @ApiModelProperty(name = "version", value = "版本号")
    private  String version;
    @ApiModelProperty(name = "canalName", value = "取到名字")
    private  String canalName;
    @ApiModelProperty(name = "flag", value = "ture 需要更新  false 不需要")
    private  Boolean flag;
    @ApiModelProperty(name = "path", value = "下载路径")
    private  String path;
}
