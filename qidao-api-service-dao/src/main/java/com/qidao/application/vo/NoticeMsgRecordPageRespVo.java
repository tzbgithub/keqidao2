package com.qidao.application.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMsgRecordPageRespVo {

    /**
     * 消息记录ID
     */
    private Long id;

    /**
     * 状态 0-（发送成功）未读 1-（发送成功）已读 2-未发送状态  3-发送成功 4-发送失败
     */
    private Integer status;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 消息ID
     */
    private Long msgId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 消息跳转路径-PC
     */
    private String pathPc;

    /**
     * 消息跳转路径-APP
     */
    private String pathApp;

    /**
     * APP 消息行为逻辑  0-不做任何处理 1-打开app内页面 2-打开h5页面
     */
    private Integer pathTypeApp;
}
