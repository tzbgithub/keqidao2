package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeLongDto {

    @ApiModelProperty(name = "belong", value = "所属单位(通过所属单位查询实验室必传)",required = false)
    private String belong;
    @ApiModelProperty(name = "unionId", value = "微信唯一标识(unionId查询实验室必传)",required = false)
    private String unionId;

}
