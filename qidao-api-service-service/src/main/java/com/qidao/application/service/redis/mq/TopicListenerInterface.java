package com.qidao.application.service.redis.mq;

import org.redisson.api.listener.MessageListener;

/**
 * redis主题监听
 **/
public interface TopicListenerInterface<T> extends MessageListener<T> {

    /**
     * 获取 主题 名称
     *
     * @return
     */
    String getTopicName();

}
