package com.qidao.application.service.msg.other;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.config.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加消息记录 组件
 **/
@Component
@Slf4j
public class InsertMsgRecordInterfaceComponent {

    @Autowired
    private List<InsertMsgRecordInterface> insertMsgRecordInterfaceList;

    @Autowired
    private MsgRecordMapper msgRecordMapper;

    private Map<Integer, InsertMsgRecordInterface> insertMsgRecordInterfaceMap;


    @PostConstruct
    public void init() {
        insertMsgRecordInterfaceMap = new HashMap<>(insertMsgRecordInterfaceList.size());
        insertMsgRecordInterfaceList.forEach(item -> {
            insertMsgRecordInterfaceMap.put(item.getSendType().getVal(), item);
        });
        log.info("InsertMsgRecordInterfaceComponent -> init -> insertMsgRecordInterfaceMap size:{}", insertMsgRecordInterfaceMap.size());
    }


    /**
     * 根据 发送类型 添加消息记录
     *
     * @param msgId       消息id
     * @param receiveType 消息接收类型
     * @param sendTime    消息发送时间
     * @return 返回接收消息的会员id列表
     **/
    public List<Long> insertMsgRecordByReceiveType(Long msgId, Integer receiveType, LocalDateTime sendTime) {
        log.info("InsertMsgRecordInterfaceComponent -> insertMsgRecordByReceiveType -> start  msgId:{} receiveType:{} sendTime:{}", msgId, receiveType, sendTime);
        List<Long> result = null;
        InsertMsgRecordInterface msgRecordInterface = insertMsgRecordInterfaceMap.get(receiveType);
        if (msgRecordInterface == null) {
            log.info("InsertMsgRecordInterfaceComponent -> insertMsgRecordByReceiveType -> 消息接收类型不正确，没有获取到相应处理类型 receiveType:{}", receiveType);
            throw new BusinessException("消息接收类型不正确");
        }
        //获取接收消息会员的数量
        long receiveMemberIdCount = msgRecordInterface.getReceiveMemberIdCount();
        if (msgRecordInterface.isBatches(receiveMemberIdCount)) {
            log.info("InsertMsgRecordInterfaceComponent -> insertMsgRecordByReceiveType -> 分批添加消息记录");
            Long lastMemberId = null;
            int limitInt = msgRecordInterface.batchesLimit();
            //初始化返回值
            result = new ArrayList<>(Long.valueOf(receiveMemberIdCount).intValue());
            do {
                //分批获取接收会员ids
                List<Long> batchesReceiveMsgMemberIds = msgRecordInterface.getBatchesReceiveMsgMemberIds(lastMemberId);
                if (CollUtil.isNotEmpty(batchesReceiveMsgMemberIds)) {
                    List<MsgRecord> msgRecordList = msgRecordInterface.createMsgRecordList(batchesReceiveMsgMemberIds, msgId, sendTime);
                    //保存记录到db
                    if (!insertMsgRecordToDB(msgRecordList)) {
                        throw new BusinessException("分批添加消息记录失败");
                    }
                    //每批记录数量是否是限制数量  是:证明可能有下一页  不是:没有下一页
                    if (batchesReceiveMsgMemberIds.size() == limitInt) {
                        lastMemberId = batchesReceiveMsgMemberIds.get(batchesReceiveMsgMemberIds.size() - 1);
                    } else {
                        lastMemberId = null;
                    }
                } else {
                    lastMemberId = null;
                }
                //添加会员id 到返回值
                result.addAll(batchesReceiveMsgMemberIds);
            } while (lastMemberId != null);

        } else {
            log.info("InsertMsgRecordInterfaceComponent -> insertMsgRecordByReceiveType -> 添加消息记录");
            //查询需要接收消息的会员ids
            List<Long> receiveMsgMemberIds = msgRecordInterface.getReceiveMsgMemberIds();
            if (CollUtil.isNotEmpty(receiveMsgMemberIds)) {
                List<MsgRecord> msgRecordList = msgRecordInterface.createMsgRecordList(receiveMsgMemberIds, msgId, sendTime);
                if (!insertMsgRecordToDB(msgRecordList)) {
                    throw new BusinessException("批量添加消息记录失败");
                }
            }
            result = receiveMsgMemberIds;
        }
        log.info("InsertMsgRecordInterfaceComponent -> insertMsgRecordByReceiveType -> end");

        return result;
    }

    /**
     * 批量添加 消息记录
     *
     * @param msgRecordList
     */
    private boolean insertMsgRecordToDB(List<MsgRecord> msgRecordList) {
        int i = msgRecordMapper.batchInsertSelective(
                msgRecordList,
                MsgRecord.Column.msgId,
                MsgRecord.Column.memberId,
                MsgRecord.Column.sendNum,
                MsgRecord.Column.sendTime,
                MsgRecord.Column.id,
                MsgRecord.Column.delFlag,
                MsgRecord.Column.status
        );
        log.info("InsertMsgRecordInterfaceComponent -> insertMsgRecordToDB -> 需要添加MsgRecord数量:{}  添加MsgRecord成功数量:{}", msgRecordList.size(), i);
        return msgRecordList.size() == i;
    }

}
