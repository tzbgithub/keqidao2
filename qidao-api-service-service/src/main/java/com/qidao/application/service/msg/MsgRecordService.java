package com.qidao.application.service.msg;

import com.qidao.application.model.msg.*;
import com.qidao.framework.service.ServicePage;

import java.util.List;

public interface MsgRecordService {

    /**
     * 查询消息记录
     *
     * @param req
     * @return
     */
    ServicePage<List<MsgRecordPageRes>> getMsgRecordPage(MsgRecordPageReq req);

    /**
     * 修改消息记录
     *
     * @param req
     */
    void update(MsgRecordUpdateReq req);

    /**
     * 新增消息记录
     *
     * @param req 入参
     * @return 生成的主键
     */
    Long insert(MsgRecordInsertReq req);

    /**
     * 删除消息记录
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据接收类型 添加消息记录
     *
     * @param msgId       消息id
     * @param receiveType 接收类型
     * @return 返回接收消息会员id列表
     */
    List<Long> insertMsgRecordByReceiveType(Long msgId, Integer receiveType);

    /**
     * 逻辑删除当前会员已读的所有记录
     *
     * @param memberId 会员id
     */
    void clearReadMsgRecordByMemberId(Long memberId);

    /**
     * 所有通知类型的消息查询
     * @param req
     * @return
     */
    ServicePage<List<NoticeMsgRecordPageResp>> getAllNoticeMsgRecordPage(NoticeMsgRecordPageReq req);

    /**
     * 查询未读消息数量
     * @param memberId
     * @return
     */
    long selectNotReadMsgCount(Long memberId);
}
