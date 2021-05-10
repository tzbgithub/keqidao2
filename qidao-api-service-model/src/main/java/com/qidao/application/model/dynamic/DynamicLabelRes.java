package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("动态标签响应对象")
public class DynamicLabelRes {

    @ApiModelProperty(value = "主键ID")
    private Long labelId;

    @ApiModelProperty(value = "值")
    private String val;
}
