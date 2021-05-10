package com.qidao.application.model.member.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 生成 refresh token 入参
 * @author Autuan.Yu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorRefreshTokenDTO {
    /**
     * 登录用户ID
     */
    private Long memberId;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
    /**
     * 版本
     */
    private String version;
    /**
     * 机器码
     */
    private String machineCode;
    /**
     * 渠道
     */
    private Integer canal;
    /**
     * IP
     */
    private String ip;
    /**
     * 最近使用时间
     * 非必填
     */
    private LocalDateTime lastUseTime;
    /**
     * 随机字符串
     * 非必填
     */
    private Integer randomInt;
}
