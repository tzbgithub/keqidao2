package com.qidao.application.service.msg.other.impl;

import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.msg.enums.MsgReceiveTypeEnum;
import com.qidao.application.service.msg.other.AbstractInsertMsgRecordInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全平台发送
 **/
@Component
@Slf4j
public class AllMemberInsertMsgRecordInterface extends AbstractInsertMsgRecordInterface {

    @Override
    public MsgReceiveTypeEnum getSendType() {
        return MsgReceiveTypeEnum.ALL_MEMBER;
    }

    @Override
    public List<Long> getReceiveMsgMemberIds() {
        return customMemberMapper.getMemberIdAll(null, null);
    }

    @Override
    public List<Long> getBatchesReceiveMsgMemberIds(Long lastMemberId) {
        return customMemberMapper.getMemberIdAll(lastMemberId, batchesLimit());
    }

    @Override
    public long getReceiveMemberIdCount() {
        //查询全部会员id 数量
        MemberExample example = new MemberExample();
        example.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        return memberMapper.countByExample(example);
    }
}
