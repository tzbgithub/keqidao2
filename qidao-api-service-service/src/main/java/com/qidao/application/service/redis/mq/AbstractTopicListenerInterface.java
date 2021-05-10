package com.qidao.application.service.redis.mq;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;

@Slf4j
public abstract class AbstractTopicListenerInterface<T> implements TopicListenerInterface<T> {

    @Resource(name = "messageRedisson")
    private RedissonClient redissonClient;

    /**
     * 注册 redis 主题监听
     */
    @PostConstruct
    public void init() {
        String topicName = getTopicName();
        log.info("AbstractTopicListenerInterface -> init -> redis 主题监听: {}", topicName);
        redissonClient.getTopic(topicName)
                .addListener(getType(), this);
    }

    @Override
    public void onMessage(CharSequence channel, T msg) {
        RLock lock = getLock(channel, msg, redissonClient);
        if (lock != null) {
            if (tryLock(lock)) {
                try {
                    onMessage(msg);
                } finally {
                    lock.unlock();
                }
            } else {
                notLock(channel, msg);
            }
        } else {
            onMessage(msg);
        }
    }

    /**
     * 消息处理
     * 消息处理太快里面要有消息是否消费判断，不然 加锁 解锁 太快 可能重复加锁成功
     *
     * @param msg
     */
    protected abstract void onMessage(T msg);

    /**
     * 没有加锁成功  处理
     **/
    protected abstract void notLock(CharSequence channel, T msg);

    /**
     * 尝试加锁
     *
     * @param lock
     * @return
     */
    protected abstract boolean tryLock(RLock lock);

    /**
     * 获取锁， 避免 集群部署 topic 重复消费问题
     *
     * @return
     */
    protected abstract RLock getLock(CharSequence channel, T msg, RedissonClient redissonClient);


    /**
     * 获取泛型 类型
     *
     * @return
     */
    private Class getType() {
        Class genericInterface = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return genericInterface;
    }
}
