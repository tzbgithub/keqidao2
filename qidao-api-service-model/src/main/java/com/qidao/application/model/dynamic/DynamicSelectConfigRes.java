package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("动态发布类型返回对象")
public class DynamicSelectConfigRes {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "值")
    private String val;
}
