package com.qidao.application.service.log.impl;

import com.qidao.application.entity.log.LogChatMsg;
import com.qidao.application.mapper.log.LogChatMsgMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.log.LogChatMsgEnum;
import com.qidao.application.model.log.LogChatMsgReq;
import com.qidao.application.service.log.LogChatMsgService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class LogChatMsgServiceImpl implements LogChatMsgService {
    @Resource
    private LogChatMsgMapper logChatMsgMapper;
    @Autowired
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 保存环信数据记录
     *
     * @param logChatMsgReq
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(LogChatMsgReq logChatMsgReq) {
        log.info("LogChatMsgServiceImpl ->  insert ---params:{} ", logChatMsgReq);
        LogChatMsg logChatMsg = new LogChatMsg();
        logChatMsg.setCreateTime(LocalDateTime.now());
        logChatMsg.setChatMsg(logChatMsgReq.getChatMsg());
        logChatMsg.setChatType(ConstantEnum.NOT_DEL.getInt());
        logChatMsg.setDelFlag(ConstantEnum.NOT_DEL.getByte());
        logChatMsg.setEasemobMsgId(logChatMsgReq.getEasemobMsgId());
        logChatMsg.setFromEasemobMember(logChatMsgReq.getFromEasemobMember());
        logChatMsg.setFromMemberId(logChatMsgReq.getFromMemberId());
        logChatMsg.setMsgTime(logChatMsgReq.getMsgTime());
        logChatMsg.setToMemberId(logChatMsgReq.getToMemberId());
        logChatMsg.setId(snowflakeIdWorker53.nextId());
        logChatMsg.setToEasemob(logChatMsgReq.getToEasemob());
        logChatMsg.setMsgType(logChatMsgReq.getMsgType());
        switch (Objects.requireNonNull(LogChatMsgEnum.getByVal(logChatMsgReq.getMsgType()))) {
            case MSG_TYPE_IMG:
            case MSG_TYPE_VOICE:
            case MSG_TYPE_VIDEO:
            case MSG_TYPE_FILE:
                String uuid = logChatMsg.getChatMsg().split("/chatfiles/")[1];
                logChatMsg.setCosAddress(String.valueOf(redissonClient.getBucket("easemob::file::" + uuid).getAndDelete()));
                break;
            default:
                break;
        }
        logChatMsgMapper.insert(logChatMsg);
    }
}
