package com.qidao.application.model.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class WeChatAppInvokeParam extends InvokeParam{
    @ApiModelProperty("应用id")
    private String appid;

    @ApiModelProperty("商户号")
    private String partnerid;

    @ApiModelProperty("预支付交易会话ID")
    private String prepayid;

    @ApiModelProperty("随机字符串")
    private String noncestr;

    @ApiModelProperty("时间戳")
    private String timestamp;

    @ApiModelProperty("签名")
    private String sign;
}
