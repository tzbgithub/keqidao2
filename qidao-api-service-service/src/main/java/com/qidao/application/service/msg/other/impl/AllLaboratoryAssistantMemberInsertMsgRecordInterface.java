package com.qidao.application.service.msg.other.impl;

import com.qidao.application.model.msg.enums.MsgReceiveTypeEnum;
import com.qidao.application.service.msg.other.AbstractInsertMsgRecordInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 所有实验室用户及其助手（审核中和审核通过）
 **/
@Component
@Slf4j
public class AllLaboratoryAssistantMemberInsertMsgRecordInterface extends AbstractInsertMsgRecordInterface {

    @Override
    public MsgReceiveTypeEnum getSendType() {
        return MsgReceiveTypeEnum.ALL_LABORATORY_ASSISTANT_MEMBER;
    }

    @Override
    public List<Long> getReceiveMsgMemberIds() {
        return customMemberMapper.getAllLaboratoryAssistantMemberIdAll(null, null);
    }

    @Override
    public List<Long> getBatchesReceiveMsgMemberIds(Long lastMemberId) {
        return customMemberMapper.getAllLaboratoryAssistantMemberIdAll(lastMemberId, batchesLimit());
    }

    @Override
    public long getReceiveMemberIdCount() {
        return customMemberMapper.getAllLaboratoryAssistantMemberIdAllCount();
    }
}
