package com.qidao.application.model.pay;

import io.swagger.annotations.ApiModel;
import lombok.Data;

    @Data
    @ApiModel
public class WechatPayNotifyParamItem {
//        String serial_no;
//        String effective_time;
//        String expire_time;
//
//        Object encrypt_certificate;

        /*
         "algorithm":"AEAD_AES_256_GCM",
        "ciphertext":"...",
        "nonce":"...",
        "original_type":"transaction",
        "associated_data":""
         */

        private String algorithm;
        private String ciphertext;
        private String nonce;
        private String original_type;
        private String associated_data;
}
