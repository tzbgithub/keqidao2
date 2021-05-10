package com.qidao.application.model.member.assistant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class RemoveAssistantReq {
    @ApiModelProperty(value = "实验室导师会员ID",required = true,example = "1234")
    @NotNull(message = "实验室导师不能为空")
    @Min(value = 1L,message = "实验室导师格式不合法")
    private Long teacherId;

    @ApiModelProperty(value = "助手会员ID",required = true,example = "1234")
    @NotNull(message = "助手不能为空")
    @Min(value = 1L,message = "助手格式不合法")
    private Long assistantId;
}
