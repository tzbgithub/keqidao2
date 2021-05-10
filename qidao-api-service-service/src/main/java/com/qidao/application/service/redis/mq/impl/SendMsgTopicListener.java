package com.qidao.application.service.redis.mq.impl;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.entity.msg.Msg;
import com.qidao.application.mapper.msg.CustomMsgRecordMapper;
import com.qidao.application.mapper.msg.MsgMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgReceiveTypeEnum;
import com.qidao.application.service.msg.MsgRecordService;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.application.service.redis.mq.AbstractTopicListenerInterface;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis 发送消息 主题 监听
 **/
@Component
@Slf4j
public class SendMsgTopicListener extends AbstractTopicListenerInterface<Long> {

    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private CustomMsgRecordMapper customMsgRecordMapper;
    @Autowired
    @Qualifier("QidaoNoticeMsgSendImpl")
    private MsgSendService msgSendService;
    @Autowired
    private MsgRecordService msgRecordService;


    @Override
    public String getTopicName() {
        return "TOPIC::ADMIN::SEND::MSG";
    }

    @Override
    protected void notLock(CharSequence channel, Long msg) {
        log.info("SendMsgTopicListener -> notLock -> 没有加锁成功 channel:{} msg:{}", channel, msg);
    }

    @Override
    protected boolean tryLock(RLock lock) {
        try {
            //onMessage()处理时间 超过10秒 修改自动解锁时间
            return lock.tryLock(0L, 10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("SendMsgTopicListener -> tryLock -> 加锁异常 error:{}", e);
            return false;
        }
    }

    @Override
    protected RLock getLock(CharSequence channel, Long msg, RedissonClient redissonClient) {
        return redissonClient.getLock(channel + "::" + msg);
    }

    @Override
    protected void onMessage(Long msgId) {
        log.info("SendMsgTopicListener -> onMessage -> start msgId:{}", msgId);
        //msgId是数据库msg表id 处理发送消息逻辑
        /* `type` '消息类型
         0-不推送
         1-立即推送
         2-每日推送
         3-定时发送
         4-触发类型：用户注册
         5-滚动消息：达成合作
         6-用户获取短信验证码
         7：触发类型：购买VIP成功
        */
        Msg msg = msgMapper.selectByPrimaryKey(msgId);
        //titleType 标题类型 0-原文发送 1-验证码
        if (msg != null && !msg.getTitleType().equals(1)) {

            //接收类型 0-全平台发送 1-指定用户发送 2-所有VIP用户 3. 所有实验室用户及其助手（审核中和审核通过）
            // 4. 所有实验室用户及其助手 （审核通过） 5. 所有企业用户 6. 所有vip企业用户 7.当前触发用户 8-未修改名称&头像用户
            Integer receiveType = msg.getReceiveType();

            //接收消息会员id 列表 //添加消息记录   添加消息记录
            List<Long> memberIdList = msgRecordService.insertMsgRecordByReceiveType(msg.getId(), receiveType);

            MsgSendDTO param = new MsgSendDTO();
            param.setId(msg.getId());
            //接收类型 0-全平台发送
            if (!MsgReceiveTypeEnum.ALL_MEMBER.getVal().equals(receiveType)) {
                List<String> memberIdsByMsgId = memberIdList.stream()
                        .map(memberId -> memberId.toString()).collect(Collectors.toList());

                if (CollUtil.isNotEmpty(memberIdsByMsgId)) {
                    //设置会员id
                    param.setReceivers(memberIdsByMsgId);
                    log.info("SendMsgTopicListener -> onMessage -> 需要发送人数:{}", memberIdsByMsgId.size());
                } else {
                    log.error("SendMsgTopicListener -> onMessage -> 消息接收会员为空,不做消息处理 msg:{}", msg);
                    return;
                }
            }

            param.setContents(Arrays.asList(msg.getContent()));
            param.setContentType(0);
            param.setTitle(Arrays.asList(msg.getTitle()));
            msgSendService.send(param);
        } else {
            log.info("SendMsgTopicListener -> onMessage -> msg不做处理  msg:{}", msg);
        }

        log.info("SendMsgTopicListener -> onMessage -> end");
    }
}
