package com.qidao.application.model.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pay.wechat")
@Data
public class WechatPayConfig {
    private String serial;
    private String apiV3Key;
    private String notifyUrl;
    private String testPrivateKey;
    private String privateKeyPath;
    private String mchId;
    private String appId;
}
