package com.qidao.application.model.server;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UnderServerReq {
    @ApiModelProperty(name = "memberIdPartyA", value = "需求目标标识",required = true,example = "232423")
    @NotNull(message = "目标不能为空")
    private Long serverId;
    @ApiModelProperty(name = "memberIdPartyA", value = "用户标识",required = true,example = "232423")
    @NotNull(message = "用户不能为空")
    private Long memberId;
}
