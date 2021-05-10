package com.qidao.application.model.msg;

import lombok.Data;

@Data
public class MsgSendRes {
    /**
     * 调用接口时 接收人数量
     */
    private Integer receiverSize;
    /**
     * 调用接口 成功数量
     */
    private Integer successSize;
    /**
     * 调用接口 失败数量
     */
    private Integer failSize;
    /**
     * 调用接口 描述
     */
    private String description;
}
