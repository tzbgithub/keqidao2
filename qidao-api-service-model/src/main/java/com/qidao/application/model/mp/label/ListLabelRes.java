package com.qidao.application.model.mp.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ListLabelRes {
    @ApiModelProperty("标签ID")
    private Long id;
    @ApiModelProperty("标签值(标签名)")
    private String val;
}
