package com.qidao.application.service.redis.mq;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.ObjectListener;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Slf4j
public abstract class AbstractStreamListenerInterface<K, V> implements StreamListenerInterface<K, V> {

    @Resource(name = "messageRedisson")
    protected RedissonClient redissonClient;

    /**
     * 注册 redis 主题监听
     */
    @PostConstruct
    public void init() {
        log.info("AbstractStreamListenerInterface -> init -> redis stream监听: {}", getStreamName());
    }


    /**
     * todo 没有触发条件  没有消息通知   只能手动调用
     **/
    @Override
    public void readMsg() {
        RStream<K, V> stream = getStream();
        Map<StreamMessageId, Map<K, V>> streamMessageIdMapMap = stream.readGroup(getGroupName(), getConsumerName(), 1);
        streamMessageIdMapMap.forEach((key, val) -> {
            message(stream, key, val);
        });
    }

    /**
     * 处理消息
     *
     * @param stream     redis stream对象
     * @param msgId      消息id
     * @param messageMap 消息内容map
     */
    protected abstract void message(RStream<K, V> stream, StreamMessageId msgId, Map<K, V> messageMap);

}
