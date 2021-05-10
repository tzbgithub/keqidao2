package com.qidao.application.service.event.dynamic;

import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.dynamic.DynamicEventParam;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.service.msg.MsgRecordService;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.application.service.msg.MsgService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态事件监听
 */
@Component
@Slf4j
public class DynamicEventListen implements SmartApplicationListener {
    @Autowired
    @Qualifier("QidaoNoticeMsgSendImpl")
    private MsgSendService msgSendService;

    @Autowired
    private MsgRecordMapper msgRecordMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Autowired
    private MsgService msgService;
    /**
     * 消息  id
     */
    @Value("${msg.dynamic.agree.id}")
    private Long msgId;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return DynamicEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("DynamicEventListen -> SmartApplicationListener -> 事件监听 -> 捕获到 动态模块事件");
        DynamicEventParam source = (DynamicEventParam) event.getSource();

        String memberId = source.getMsgSendTo();
        if (! msgService.ableSendMsg(Long.parseLong(memberId))) {
            log.info("DynamicEventListen -> SmartApplicationListener -> 不发送");
            return;
        }

        // 动态点赞事件
        if (event instanceof DynamicAgreeEvent) {
            // 生成 MsgRecord
            LocalDateTime now = LocalDateTime.now();
            long recordId = snowflakeIdWorker53.nextId();

            MsgRecord msgRecord = MsgRecord.builder()
                    .id(recordId)
                    .msgId(msgId)
                    .memberId(Long.valueOf(memberId))
                    .sendTime(now)
                    .build();
            msgRecordMapper.insertSelective(msgRecord);

            MsgSendDTO sendParam = MsgSendDTO.builder()
                    .id(recordId)
                    .contentType(0)
                    .receivers(Arrays.asList(memberId))
                    .title(Arrays.asList((source.getMsgBody())))
                    .contents(Arrays.asList(source.getMsgBody()))
                    .sendTime(now)
                    .build();

            msgSendService.send(sendParam);
        }
    }
}
