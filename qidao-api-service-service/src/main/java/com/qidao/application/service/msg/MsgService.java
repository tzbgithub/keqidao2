package com.qidao.application.service.msg;

import com.qidao.application.entity.msg.MemberVipMsg;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.config.SelectConfigVo;
import com.qidao.application.model.config.SelectGetByTypeReq;
import com.qidao.application.model.log.MsgNotifyReq;
import com.qidao.application.model.msg.*;
import com.qidao.framework.service.ServicePage;

import java.util.List;

public interface MsgService {

    /**
     * 新增消息
     * @param req
     */
    void insert(MsgInsertReq req);

    /**
     * 删除消息
     * @param id
     */
    void delete(Long id);

    /**
     * 修改消息
     * @param req
     */
    void update(MsgUpdateReq req);

    /**
     * 查询消息
     * @param req
     * @return
     */
    ServicePage<List<MsgPageRes>> getMsg(MsgPageReq req);

    /**
     * 根据type查询select_config
     * @param req
     * @return
     */
    ServicePage<List<SelectConfigVo>> getSelectByType(SelectGetByTypeReq req);

    /**
     * 用户发送短信
     * 1分钟之内只能发送1条
     * 1天只能发送10条
     * @param smsSendReq
     */
    void smsSend(SmsSendReq smsSendReq);

    /**
     * <p>检查是否可以向用户发送信息</p>
     * <p>返回 false 的情况：</p>
     * <ul>
     *     <li>会员已被删除</li>
     *     <li>会员已被冻结(VIP Level < 0 )</li>
     *     <li>会员已设置屏蔽推送</li>
     *     <li>其他需要返回 false 的业务情况</li>
     * </ul>
     * @param sendToMsgMemberId 消息接收者的会员ID
     * @return true-可惜发送信息 false-不可以发送信息
     */
    boolean ableSendMsg(Long sendToMsgMemberId);

    ServicePage<List<MemberVipMsgPageRes>> getMemberVipMsg(MemberVipMsgPageReq req);

    void emptyVipMsg(BaseIdQuery id);

    /**
     * 极光推送
     */
    void notifyPush(MsgNotifyReq req);
}
