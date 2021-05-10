package com.qidao.application.model.common.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * id 查询基类
 * @author Author
 */
@Data
@ApiModel
public class BaseIdQuery {
    @ApiModelProperty(value = "id",example = "1234567",required = true)
    @NotNull(message = "id不能为空")
    private Long id;
}
