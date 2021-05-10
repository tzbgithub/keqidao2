package com.qidao.application.service.dingtalk;

/**
 * @author Autuan.Yu
 */
public interface IDingTalkService {
    /**
     * 钉钉群项目预警发送消息
     * @param content 发送内容
     */
    void sendMsg(String content);
}
