package com.qidao.application.model.address;

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
@ApiModel("区县对象")
public class Area {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "区县编码")
    private String areaCode;

    @ApiModelProperty(value = "区县名称")
    private String areaName;

    @ApiModelProperty(value = "所属城市编码")
    private String cityCode;

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "纬度")
    private String lat;
}
