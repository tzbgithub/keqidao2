package com.qidao.application.vo.offical;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxChatUnionReq {
    @ApiModelProperty(name = "accessToken", value = "调用接口凭证",required = true,example = "232423")
    @NotNull(message = "请传入调用接口凭证")
    private String accessToken;
    @ApiModelProperty(name = "openId", value = "普通用户的标识，对当前公众号唯一",required = true,example = "232423")
    @NotNull(message = "请传入公众号唯一的标识")
    private String openId;
}
