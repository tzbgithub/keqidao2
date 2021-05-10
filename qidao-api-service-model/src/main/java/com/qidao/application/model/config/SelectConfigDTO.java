package com.qidao.application.model.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "配置对象")
public class SelectConfigDTO {
    @ApiModelProperty(name = "id",value ="主键" ,required = true , example = "1")
    private Long id;

    @ApiModelProperty(name = "val",value ="展示值" ,required = true , example = "test")
    private String val;
}