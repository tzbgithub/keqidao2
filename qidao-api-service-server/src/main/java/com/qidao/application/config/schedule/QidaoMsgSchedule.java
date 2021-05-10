package com.qidao.application.config.schedule;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.entity.msg.Msg;
import com.qidao.application.entity.msg.MsgExample;
import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.entity.msg.MsgRecordExample;
import com.qidao.application.mapper.msg.MsgMapper;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgConstantEnum;
import com.qidao.application.model.msg.listen.QidaoSchedulingConfigurer;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 企岛定时消息
 *
 * @author Autuan.Yu
 */
@Slf4j
@Component
public class QidaoMsgSchedule {
    @Resource
    private QidaoSchedulingConfigurer qidaoSchedulingConfigurer;
    @Autowired
    @Qualifier("QidaoNoticeMsgSendImpl")
    private MsgSendService msgSendService;
    @Resource
    private MsgMapper msgMapper;
    @Resource
    private MsgRecordMapper msgRecordMapper;
    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;


    /**
     * 消息定时推送
     */
//    @Scheduled(fixedDelay = 1000 * 60 * 60 )
    @Deprecated
    void scheduledPush() {
        log.info("QidaoMsgSchedule -> scheduledPush -> start");
        LocalDateTime now = LocalDateTime.now();
        // 查库需要每日发送的消息
        MsgExample msgExample = new MsgExample();
        msgExample.createCriteria()
                .andStatusEqualTo(MsgConstantEnum.STATUS_TRUE.getCode())
                .andTypeEqualTo(MsgConstantEnum.TYPE_SCHEDULE.getCode())
                .andPushTimeGreaterThanOrEqualTo(now)
                .andExpireTimeGreaterThanOrEqualTo(now)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        // 根据消息查出所有需要发送的消息
        List<Msg> msgList = msgMapper.selectByExample(msgExample);
        if (CollUtil.isEmpty(msgList)) {
            log.info("QidaoMsgSchedule -> scheduledPush -> msgList is empty -> over");
            return;
        }
        MsgRecordExample msgRecordExample = new MsgRecordExample();
        msgRecordExample.createCriteria()
                .andSendNumEqualTo(1)
                .andStatusEqualTo(MsgConstantEnum.RECORD_NOT_SEND.getCode())
                .andMsgIdIn(msgList.stream().map(Msg::getId).collect(Collectors.toList()))
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<MsgRecord> msgRecords = msgRecordMapper.selectByExample(msgRecordExample);
        if (CollUtil.isEmpty(msgRecords)) {
            log.info("QidaoMsgSchedule -> scheduledPush -> msgRecords is empty");
            return;
        }
        // 发送数据

        for (Msg msg : msgList) {
            Long msgId = msg.getId();
            List<String> receivers = msgRecords.stream()
                    .filter(record -> msgId.equals(record.getMsgId()))
                    .map(MsgRecord::getMemberId).map(String::valueOf).collect(Collectors.toList());
            MsgSendDTO sendParam = MsgSendDTO.builder()
                    .id(msgId)
                    .receivers(receivers)
                    .title(Arrays.asList(msg.getTitle()))
                    .contents(Arrays.asList(msg.getContent()))
                    .sendTime(msg.getPushTime())
                    .build();

            msgSendService.send(sendParam);
        }

    }

    /**
     * 消息每日推送  每日凌晨0：00 执行一次
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    private void everydayPush() {
        log.info("QidaoMsgSchedule -> everydayPush -> start");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayBegin = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        // 查库需要每日发送的消息
        MsgExample msgExample = new MsgExample();
        msgExample.createCriteria()
                .andTypeEqualTo(MsgConstantEnum.TYPE_DAY.getCode())
                .andExpireTimeGreaterThanOrEqualTo(now)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        // 根据消息查出所有需要发送的消息
        List<Msg> msgList = msgMapper.selectByExample(msgExample);
        if (CollUtil.isEmpty(msgList)) {
            log.info("QidaoMsgSchedule -> everydayPush -> msgList is empty -> over");
            return;
        }
        MsgRecordExample msgRecordExample = new MsgRecordExample();
        // 每日推送不能直接通过 status 判断 ， 需要检查是否有今天发送信息数据
        List<Long> msgIds = msgList.stream().map(Msg::getId).collect(Collectors.toList());

        msgRecordExample.createCriteria()
                .andStatusNotEqualTo(MsgConstantEnum.RECORD_NOT_SEND.getCode())
                .andMsgIdIn(msgIds)
                .andCreateTimeGreaterThanOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<MsgRecord> todayRecords = msgRecordMapper.selectByExample(msgRecordExample);
        if (CollUtil.isNotEmpty(todayRecords)) {
            List<Long> sentTodayMsgId = todayRecords.stream().map(MsgRecord::getMsgId)
                    .distinct().collect(Collectors.toList());
            msgIds = msgIds.stream()
                    .filter(id -> !sentTodayMsgId.contains(id))
                    .collect(Collectors.toList());
            msgList = msgList.stream()
                    .filter(msg -> ! sentTodayMsgId.contains(msg.getId()))
                    .collect(Collectors.toList());
            if (CollUtil.isEmpty(msgIds)) {
                log.info("QidaoMsgSchedule -> everydayPush -> 每日消息每天只发送一次");
                return;
            }
        }

        msgRecordExample.clear();
        msgRecordExample.createCriteria()
                .andSendNumEqualTo(1)
                .andMsgIdIn(msgIds)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        List<MsgRecord> msgRecords = msgRecordMapper.selectByExample(msgRecordExample);

        // 生成今日份需要发送数据
        List<MsgRecord> generatorMsgRecordList = new ArrayList<>();
        for (MsgRecord record : msgRecords) {
            LocalDateTime createTime = record.getCreateTime();
            Duration between = Duration.between(createTime, now);

            if (createTime.isBefore(todayBegin)) {
                record.setId(snowflakeIdWorker53.nextId());

                record.setStatus(MsgConstantEnum.RECORD_UNREAD.getCode());
                record.setSendNum((int) (between.toDays() + 1));
                generatorMsgRecordList.add(record);
            }
        }

        if (CollUtil.isNotEmpty(generatorMsgRecordList)) {
            msgRecordMapper.batchInsert(generatorMsgRecordList);
        }
        // 发送数据

        for (Msg msg : msgList) {
            Long msgId = msg.getId();
            LocalDateTime configPushTime = msg.getPushTime();
            MsgSendDTO sendParam = MsgSendDTO.builder()
                    .id(msgId)
                    .receivers(msgRecords.stream()
                            .filter(record -> msgId.equals(record.getMsgId()))
                            .map(MsgRecord::getMemberId).map(String::valueOf).collect(Collectors.toList()))
                    .title(Arrays.asList(msg.getTitle()))
                    .sendTime(LocalDateTime.of(now.toLocalDate(), configPushTime.toLocalTime()))
                    .contents(Arrays.asList(msg.getContent()))
                    .build();
            log.info("QidaoMsgSchedule -> everydayPush -> before send -> msgId -> {} receiverSize -> {}",msgId,sendParam.getReceivers().size());
            msgSendService.send(sendParam);
        }

    }

    /**
     * 定时删除已完成任务  每2小时执行一次
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 2)
    public void removeDoneTask() {
        qidaoSchedulingConfigurer.clear();
    }
}
