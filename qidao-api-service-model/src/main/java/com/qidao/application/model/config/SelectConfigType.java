package com.qidao.application.model.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("选择下拉请求对象")
public class SelectConfigType {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型" , required = true , example = "6")
    private Integer type;

}
