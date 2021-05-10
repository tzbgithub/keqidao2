package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BaseSelectConfigReturnDto {
    @ApiModelProperty(name = "industryId", value = "所属行业id")
    private Long industryId;
    @ApiModelProperty(name = "scaleId", value = "规模id")
    private Long scaleId;
    @ApiModelProperty(name = "industryName", value = "所属行业")
    private String industryName;
    @ApiModelProperty(name = "scaleName", value = "规模")
    private String scaleName;
    @ApiModelProperty(name = "techScaleId", value = "技术规模id ")
    private Long techScaleId;
    @ApiModelProperty(name = "techScaleName", value = "技术规模")
    private String techScaleName;
}
