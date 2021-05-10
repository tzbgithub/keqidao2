package com.qidao.application.model.member.subscribe;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhetherAttention {
    @NotNull
    @ApiModelProperty(name = "memberId", value = "当前会员的用户标识",required = true,example = "130879657672706")
    private Long memberId;
    @NotNull
    @ApiModelProperty(name = "type", value = "关注对象身份 0-会员 1-组织机构",required = true,example = "0")
    private  Integer type;
    @NotNull
    @ApiModelProperty(name = "subscribeId", value = "被关注的组织机构标识",required = true,example = "130899165380610")
    private Long subscribeId;
}
