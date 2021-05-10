package com.qidao.application.service.msg.other.impl;

import com.qidao.application.model.msg.enums.MsgReceiveTypeEnum;
import com.qidao.application.service.msg.other.AbstractInsertMsgRecordInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 未修改名称&头像用户
 **/
@Component
@Slf4j
public class NotUpdateNameHeadImgMemberInsertMsgRecordInterface extends AbstractInsertMsgRecordInterface {

    @Override
    public MsgReceiveTypeEnum getSendType() {
        return MsgReceiveTypeEnum.NOT_UPDATE_NAME_HEAD_IMG_MEMBER;
    }

    @Override
    public List<Long> getReceiveMsgMemberIds() {
        return customMemberMapper.getNotUpdateNameAndHeadImg(null, null);
    }

    @Override
    public List<Long> getBatchesReceiveMsgMemberIds(Long lastMemberId) {
        return customMemberMapper.getNotUpdateNameAndHeadImg(lastMemberId, batchesLimit());
    }

    @Override
    public long getReceiveMemberIdCount() {
        return customMemberMapper.getNotUpdateNameAndHeadImgCount();
    }
}
