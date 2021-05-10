package com.qidao.application.model.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class ListLabelReq {
    @ApiModelProperty(value = "组织ID",example = "133547409080322",required = false,hidden = true)
    private Long organizationId;
    @NotNull(message = "会员ID 不能为空")
    @ApiModelProperty(value = "会员ID",example = "1234",required = true)
    private Long memberId;
}
