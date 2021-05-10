package com.qidao.application.service.redis.mq.impl;

import com.qidao.application.service.redis.mq.AbstractStreamListenerInterface;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;

import java.util.Map;

/**
 * redis 发送消息 stream 监听
 **/
@Slf4j
public class SendMsgStreamListener extends AbstractStreamListenerInterface<String, Long> {

    @Override
    public String getStreamName() {
        return "STREAM::ADMIN::SEND::MSG";
    }

    @Override
    public String getConsumerName() {
        return "consumer";
    }

    @Override
    public RStream<String, Long> getStream() {
        return redissonClient.getStream(getStreamName());
    }

    @Override
    protected void message(RStream<String, Long> stream, StreamMessageId msgId, Map<String, Long> messageMap) {
        log.info("SendMsgStreamListener -> message -> start messageMap:{}", messageMap);
        long ack = stream.ack(getGroupName(), msgId);
        log.info("SendMsgStreamListener -> message -> ack:{}", ack);
    }
}
