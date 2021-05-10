package com.qidao.application.service.msg.other.impl;

import com.qidao.application.model.msg.enums.MsgReceiveTypeEnum;
import com.qidao.application.service.msg.other.AbstractInsertMsgRecordInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 所有VIP用户
 **/
@Component
@Slf4j
public class AllVipMemberInsertMsgRecordInterface extends AbstractInsertMsgRecordInterface {

    @Override
    public MsgReceiveTypeEnum getSendType() {
        return MsgReceiveTypeEnum.ALL_VIP_MEMBER;
    }

    @Override
    public List<Long> getReceiveMsgMemberIds() {
        return customMemberMapper.getAllVipMemberIdAll(null, null);
    }

    @Override
    public List<Long> getBatchesReceiveMsgMemberIds(Long lastMemberId) {
        return customMemberMapper.getAllVipMemberIdAll(lastMemberId, batchesLimit());
    }

    @Override
    public long getReceiveMemberIdCount() {
        return customMemberMapper.getAllVipMemberIdAllCount();
    }
}
