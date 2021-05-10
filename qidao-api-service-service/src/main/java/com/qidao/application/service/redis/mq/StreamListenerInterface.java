package com.qidao.application.service.redis.mq;

import org.redisson.api.RStream;

/**
 * redis stream 监听
 **/
public interface StreamListenerInterface<K, V> {

    /**
     * 获取 stream 名称
     *
     * @return
     */
    String getStreamName();

    /**
     * 获取消费者名字
     *
     * @return
     */
    String getConsumerName();

    /**
     * 获取分组名称
     **/
    default String getGroupName() {
        return "defaultGroup";
    }

    /**
     * 获取stream对象
     *
     * @return
     */
    RStream<K, V> getStream();

    /**
     * 读取消息
     */
    void readMsg();
}
