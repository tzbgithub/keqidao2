package com.qidao.application.model.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class LabelDTO implements Serializable {
    @ApiModelProperty(name = "id", value = "标签ID", example = "1231231")
    private Long id;
    @ApiModelProperty(name = "val", value = "标签值", example = "测试")
    private String val;
}
