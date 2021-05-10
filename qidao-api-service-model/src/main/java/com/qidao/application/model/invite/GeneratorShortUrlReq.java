package com.qidao.application.model.invite;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("生成短链接请求参数")
public class GeneratorShortUrlReq {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "123")
    private Long memberId;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型  0-实验室老师邀请助手 1-实验室老师邀请老师 2-企业邀请成员 3-助手邀请老师" , required = true , example = "0")
    private Integer type;

}
