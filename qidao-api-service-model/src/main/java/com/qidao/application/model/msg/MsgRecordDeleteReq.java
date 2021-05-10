package com.qidao.application.model.msg;


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
@ApiModel("删除消息记录请求对象")
public class MsgRecordDeleteReq {

    @NotNull(message = "消息记录ID不能为空")
    @ApiModelProperty(value = "消息记录ID" , example = "134516773552129" , required = true)
    private Long msgRecordId;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "134134370467842")
    private Long memberId;
}
