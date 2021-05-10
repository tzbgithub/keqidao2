package com.qidao.application.model.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件提取对象
 * @author Autuan.Yu
 */
@ConfigurationProperties(prefix = "token")
@Data
@Component
public class TokenConfigDTO {
    private byte[] key;
}
