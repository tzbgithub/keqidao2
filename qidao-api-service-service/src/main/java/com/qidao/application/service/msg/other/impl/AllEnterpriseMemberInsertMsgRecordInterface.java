package com.qidao.application.service.msg.other.impl;

import com.qidao.application.model.msg.enums.MsgReceiveTypeEnum;
import com.qidao.application.service.msg.other.AbstractInsertMsgRecordInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 所有企业用户
 **/
@Component
@Slf4j
public class AllEnterpriseMemberInsertMsgRecordInterface extends AbstractInsertMsgRecordInterface {

    @Override
    public MsgReceiveTypeEnum getSendType() {
        return MsgReceiveTypeEnum.ALL_ENTERPRISE_MEMBER;
    }

    @Override
    public List<Long> getReceiveMsgMemberIds() {
        return customMemberMapper.getAllEnterpriseMemberIdAll(null, null);
    }

    @Override
    public List<Long> getBatchesReceiveMsgMemberIds(Long lastMemberId) {
        return customMemberMapper.getAllEnterpriseMemberIdAll(lastMemberId, batchesLimit());
    }

    @Override
    public long getReceiveMemberIdCount() {
        return customMemberMapper.getAllEnterpriseMemberIdAllCount();
    }
}
