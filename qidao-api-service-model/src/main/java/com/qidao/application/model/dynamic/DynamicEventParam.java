package com.qidao.application.model.dynamic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DynamicEventParam {
    /**
     * 动态ID
     */
    private Long dynamicId;
    /**
     * 消息发送
     */
    private String msgSendTo;
    /**
     * 发起这次事件的人员名称
     */
    private String eventMemberName;
    /**
     * 消息体
     */
    private String msgBody;
}
