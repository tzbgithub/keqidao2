package com.qidao.application.model.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 缓存 信息实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgCacheDO implements Serializable {
    private Long id;
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private String sendTime;

}
