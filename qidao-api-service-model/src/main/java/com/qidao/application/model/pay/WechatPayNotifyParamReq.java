package com.qidao.application.model.pay;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 微信支付 服务器通知对象
 */
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@ApiModel
public class WechatPayNotifyParamReq {
    /*

{"data":[{"serial_no":"5157F09EFDC096DE15EBE81A47057A7232F1B8E1","effective_time":"2018-03-26T11:39:50+08:00","expire_time":"2023-03-25T11:39:50+08:00","encrypt_certificate":{"algorithm":"AEAD_AES_256_GCM","nonce":"4de73afd28b6","associated_data":"certificate","ciphertext":"..."}}]}

    List<WechatPayNotifyParamItem> data;
     */


    /*

{
    "id":"EV-2018022511223320873",
    "create_time":"2015-05-20T13:29:35+08:00",
    "resource_type":"encrypt-resource",
    "event_type":"TRANSACTION.SUCCESS",
    "resource":{
        "algorithm":"AEAD_AES_256_GCM",
        "ciphertext":"...",
        "nonce":"...",
        "original_type":"transaction",
        "associated_data":""
    },
    "summary":"支付成功"
}
     */

    /**
     * 	通知的唯一ID
     * 	示例值：EV-2018022511223320873
     */
    private String id;
    /**
     * 通知创建的时间，遵循rfc3339标准格式，格式为 YYYY-MM-DDTHH:mm:ss+TIMEZONE
     */
    private String create_time;
    /**
     * 通知的类型，支付成功通知的类型为TRANSACTION.SUCCESS
     * 示例值：TRANSACTION.SUCCESS
     */
    private String event_type;
    /**
     * 通知的资源数据类型，支付成功通知为encrypt-resource
     * 示例值：encrypt-resource
     */
    private String resource_type;
    /**
     * 通知资源数据
     * json格式
     */
    private WechatPayNotifyParamItem resource;
    /**
     * 回调摘要
     * 示例值：支付成功
     */
    private String summary;


}
