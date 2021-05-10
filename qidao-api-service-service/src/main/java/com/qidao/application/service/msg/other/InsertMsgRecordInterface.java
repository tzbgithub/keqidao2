package com.qidao.application.service.msg.other;

import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.model.msg.enums.MsgReceiveTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 添加消息记录 接口
 **/
public interface InsertMsgRecordInterface {

    /**
     * 获取发送类型
     *
     * @return
     */
    MsgReceiveTypeEnum getSendType();

    /**
     * 获取接收会员ids isBatches()false时使用
     *
     * @return
     */
    List<Long> getReceiveMsgMemberIds();

    /**
     * 分批 获取接收会员ids isBatches()true时使用
     *
     * @param lastMemberId 上一次分批的最后一个会员id
     * @return
     */
    List<Long> getBatchesReceiveMsgMemberIds(Long lastMemberId);

    /**
     * 根据接收会员id列表 创建消息记录列表
     *
     * @param receiveMsgMemberIds 获取接收会员ids
     * @param msgId               消息id
     * @param sendTime            发送时间
     * @return
     */
    List<MsgRecord> createMsgRecordList(List<Long> receiveMsgMemberIds, Long msgId, LocalDateTime sendTime);

    /**
     * 是否分配添加
     * 接收会员太多时分批处理数据
     * 例如： 10000个会员需要接收消息  batchesLimit()返回1000   添加msgRecord记录时，分为每批1000条记录添加  循环向上取整(10000/1000)次
     *
     * @return
     */
    boolean isBatches();

    /**
     * 是否分配添加
     * 接收会员太多时分批处理数据
     * 例如： 10000个会员需要接收消息  batchesLimit()返回1000   添加msgRecord记录时，分为每批1000条记录添加  循环向上取整(10000/1000)次
     * @param receiveMemberIdCount 获取 需要接收消息会员 的 数量
     * @return
     */
    boolean isBatches(Long receiveMemberIdCount);

    /**
     * 获取每批 限制数量
     * 接收会员太多时分批 每批最大数量
     *
     * @return
     */
    int batchesLimit();

    /**
     * 获取 需要接收消息会员 的 数量
     *
     * @return
     */
    long getReceiveMemberIdCount();
}
