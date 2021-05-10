package com.qidao.application.model.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pay.ali")
@Data
public class AliPayConfig {
    private String protocol;
    private String gatewayHost;
    private String signType;
    private String appId;
    private String merchantPrivateKeyPath;
    private String aliPublicKeyPath;
    private String notifyUrl;
}
