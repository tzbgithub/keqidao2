package com.qidao.application.service.event.member;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.entity.msg.Msg;
import com.qidao.application.entity.msg.MsgExample;
import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.mapper.msg.MsgMapper;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.dynamic.comment.RegisterEventParam;
import com.qidao.application.model.member.BecomeVipEventParam;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgConstantEnum;
import com.qidao.application.service.msg.MsgService;
import com.qidao.application.service.msg.impl.QidaoNoticeMsgSendImpl;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class RegisterEventListen implements SmartApplicationListener {
    @Autowired
    private MsgRecordMapper msgRecordMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Autowired
    private QidaoNoticeMsgSendImpl qidaoNoticeMsgSend;
    @Autowired
    private MsgService msgService;
    @Autowired
    MsgMapper msgMapper;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return MemberEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("RegisterEventListen -> SmartApplicationListener -> 事件监听 -> onApplicationEvent");
        List<Msg> messageList = null;
        Long memberId = null;

        // 点赞事件
        if (event instanceof MemberAgreeEvent) {
            log.info("RegisterEventListen -> SmartApplicationListener -> 事件监听 -> MemberAgreeEvent");
            RegisterEventParam source = (RegisterEventParam) event.getSource();
            memberId = source.getMemberId();
            if (!msgService.ableSendMsg(memberId)) {
                log.info("RegisterEventListen -> SmartApplicationListener -> 不发送");
                return;
            }
            MsgExample msgExample = new MsgExample();
            msgExample.createCriteria()
                    .andDelFlagEqualTo(MsgConstantEnum.STATUS_TRUE.getCode().byteValue())
                    .andTypeEqualTo(MsgConstantEnum.TYPE_EVENT_SIGN_UP.getCode());
            List<Msg> msgs = msgMapper.selectByExample(msgExample);
            log.info("RegisterEventListen -> SmartApplicationListener -> 查询到对应的模板以后存入发送信息并发送 ");

            if (CollUtil.isNotEmpty(msgs)) {
                for(Msg x : msgs) {
                    // 生成 MsgRecord
                    LocalDateTime now = LocalDateTime.now();
                    long recordId = snowflakeIdWorker53.nextId();

                    MsgRecord msgRecord = MsgRecord.builder()
                            .id(recordId)
                            .msgId(x.getId())
                            .memberId(memberId)
                            .sendTime(now)
                            .build();
                    msgRecordMapper.insertSelective(msgRecord);
                    log.info("RegisterEventListen -> SmartApplicationListener -> 开始推送给{}用户信息 ", memberId);
                    MsgSendDTO sendParam = MsgSendDTO.builder()
                            .id(recordId)
                            .contentType(0)
                            .receivers(Collections.singletonList(String.valueOf(memberId)))
                            .title(Collections.singletonList((x.getTitle())))
                            .contents(Collections.singletonList(x.getContent()))
                            .sendTime(now)
                            .build();
                    qidaoNoticeMsgSend.send(sendParam);
                }
            }

        }
        // 成为 vip 事件
        if (event instanceof BecomeVipEvent) {
            log.info("RegisterEventListen -> SmartApplicationListener -> 事件监听 -> BecomeVipEvent");
            // 发送触发类型消息
            BecomeVipEventParam param = (BecomeVipEventParam) event.getSource();
            memberId = param.getMemberId();
            MsgExample msgExample = new MsgExample();
            msgExample.createCriteria()
                    .andDelFlagEqualTo(MsgConstantEnum.STATUS_TRUE.getCode().byteValue())
                    .andTypeEqualTo(MsgConstantEnum.TYPE_BECOME_VIP.getCode());
            messageList = msgMapper.selectByExample(msgExample);

        }

        // 消息记录
        if (CollUtil.isNotEmpty(messageList) && null != memberId) {
            // 生成 MsgRecord
            LocalDateTime now = LocalDateTime.now();
            for (Msg message : messageList) {
                long recordId = snowflakeIdWorker53.nextId();
                MsgRecord msgRecord = MsgRecord.builder()
                        .id(recordId)
                        .msgId(message.getId())
                        .memberId(memberId)
                        .sendTime(now)
                        .build();
                msgRecordMapper.insertSelective(msgRecord);
                log.info("RegisterEventListen -> SmartApplicationListener -> 开始推送给{}用户信息 ", memberId);
                MsgSendDTO sendParam = MsgSendDTO.builder()
                        .id(recordId)
                        .contentType(0)
                        .receivers(Collections.singletonList(String.valueOf(memberId)))
                        .title(Collections.singletonList((message.getTitle())))
                        .contents(Collections.singletonList(message.getContent()))
                        .sendTime(now)
                        .build();
                qidaoNoticeMsgSend.send(sendParam);

            }
        }

    }
}
