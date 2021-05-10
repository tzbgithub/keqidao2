package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 浏览记录请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BehaveMemeberReq {
    @ApiModelProperty(name = "visitMemberId", value = "访问对象ID",required = true,example = "232423")
    @NotNull(message = "请传入访问对象")
    private Long visitMemberId;
    @ApiModelProperty(name = "memberId", value = "对象ID",required = true,example = "232423")
    @NotNull(message = "请传入目标对象")
    private Long memberId;
}
