package com.qidao.application.service.msg;

import com.qidao.application.model.msg.MsgMenuInsertReq;
import com.qidao.application.model.msg.MsgMenuQueryReq;
import com.qidao.application.model.msg.MsgMenuQueryRes;
import com.qidao.application.model.msg.MsgMenuUpdateReq;

import java.util.List;

public interface MsgMenuService {

    /**
     * 查询消息菜单
     * @param req
     * @return
     */
    List<MsgMenuQueryRes> getMsgMenu(MsgMenuQueryReq req);

    /**
     * 删除消息菜单
     * @param msgMenuId
     */
    void delete(Long msgMenuId);

    /**
     * 新增消息菜单
     * @param req
     */
    void insert(MsgMenuInsertReq req);

    /**
     * 修改消息菜单
     * @param req
     */
    void update(MsgMenuUpdateReq req);

}
