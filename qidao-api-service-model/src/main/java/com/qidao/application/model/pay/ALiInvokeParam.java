package com.qidao.application.model.pay;

import cn.hutool.core.date.DatePattern;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支付宝支付唤醒  参数
 */
@Data
@Builder
@AllArgsConstructor
@ApiModel
public class ALiInvokeParam extends InvokeParam{
    @ApiModelProperty("应用ID")
    private String app_id;
    @ApiModelProperty("唤醒参数")
    private String biz_content;
    @ApiModelProperty(value = "字符集",example = "utf-8")
    private String charset;
    @ApiModelProperty(value = "方法",example = "alipay.trade.app.pay")
    private String method;
    @ApiModelProperty(value = "签名方式",example = "RSA2")
    private String sign_type;
    @ApiModelProperty(value = "时间",example = "2016-07-29 16:55:53")
    private String timestamp;
    @ApiModelProperty(value = "版本",example = "1.0")
    private String version;

    /**
     * app 支付参数生成
     * @return
     */
    public static ALiInvokeParam appPayInvoke(String appId,String bizContent){
        ALiInvokeParam result = new ALiInvokeParam();
        result.app_id = appId;
        result.biz_content = bizContent;
        return result;
    }

    public static ALiInvokeParam pagePayInvoke(String appId,String bizContent){
        ALiInvokeParam result = new ALiInvokeParam();
        result.app_id = appId;
        result.biz_content = bizContent;
        return result;
    }

    ALiInvokeParam(){
        this.version = "1.0";
        this.timestamp = LocalDateTime.now().format(DatePattern.NORM_DATETIME_FORMATTER);
        this.sign_type = "RSA2";
        this.charset = "utf-8";
        this.method = "alipay.trade.app.pay";
    }
}
