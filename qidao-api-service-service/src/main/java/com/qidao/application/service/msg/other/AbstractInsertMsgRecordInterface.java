package com.qidao.application.service.msg.other;

import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.msg.enums.MsgConstantEnum;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加消息记录
 **/
@Slf4j
public abstract class AbstractInsertMsgRecordInterface implements InsertMsgRecordInterface {

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Resource
    protected CustomMemberMapper customMemberMapper;

    @Resource
    protected MemberMapper memberMapper;

    @Override
    public List<MsgRecord> createMsgRecordList(List<Long> receiveMsgMemberIds, Long msgId, LocalDateTime sendTime) {
        List<MsgRecord> listMsgRecord = new ArrayList<>(receiveMsgMemberIds.size());
        receiveMsgMemberIds.forEach(memberId -> {
            MsgRecord msgRecord = new MsgRecord();
            msgRecord.setMsgId(msgId);
            msgRecord.setMemberId(memberId);
            msgRecord.setSendNum(1);
            msgRecord.setSendTime(sendTime);
            msgRecord.setId(snowflakeIdWorker53.nextId());
            msgRecord.setDelFlag(ConstantEnum.NOT_DEL.getByte());
            msgRecord.setStatus(MsgConstantEnum.STATUS_TRUE.getCode());
            listMsgRecord.add(msgRecord);
        });
        return listMsgRecord;
    }

    /**
     * 默认 按 每批 添加 1000条
     *
     * @return
     */
    @Override
    public int batchesLimit() {
        return 1000;
    }

    /**
     * 判断是 需要 分批添加
     *
     * @return
     */
    @Override
    public boolean isBatches() {
        return isBatches(getReceiveMemberIdCount());
    }

    @Override
    public boolean isBatches(Long receiveMemberIdCount) {
        int limit = batchesLimit();
        log.info("AbstractInsertMsgRecordInterface -> isBatches -> 接收消息的会员数量:{}  分批添加记录次数:{}", receiveMemberIdCount, Math.ceil(receiveMemberIdCount / (double) limit));
        return receiveMemberIdCount > limit;
    }
}
