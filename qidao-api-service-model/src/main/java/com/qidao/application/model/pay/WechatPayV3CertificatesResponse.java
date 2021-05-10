package com.qidao.application.model.pay;

import cn.hutool.core.date.DatePattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * 微信 获取公钥 响应结果 封装对象
 */
@Data
public class WechatPayV3CertificatesResponse implements Comparator<WechatPayV3CertificatesResponse> {
    private String serial_no;
    private String effective_time ;
    private String expire_time ;
    private String encrypt_certificate;

    @Data
    public class WechatPayV3CertificatesDetailResponse {
        private String algorithm;
        private String nonce;
        private String associated_data;
        private String ciphertext;
    }


    public LocalDate formatEffectiveTime(){
        return LocalDate.parse(this.effective_time, DatePattern.NORM_DATE_FORMATTER);
    }
    @Override
    public int compare(WechatPayV3CertificatesResponse a, WechatPayV3CertificatesResponse b) {
        return a.formatEffectiveTime().compareTo(b.formatEffectiveTime());
//        return a.name.compareToIgnoreCase(b.name);
//        return 1;
    }
}
