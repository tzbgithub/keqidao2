package com.qidao.application.model.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogChatMsgReq {
    @ApiModelProperty(name = "toMemberId", value = "接收人标识")
    private  Long toMemberId;
    @ApiModelProperty(name = "easemobMsgId", value = "未知")
    private  Long easemobMsgId;
    @ApiModelProperty(name = "fromMemberId", value = "发送人标识")
    private  Long fromMemberId;
    @ApiModelProperty(name = "fromEasemobMember", value = "(环信）发送人username")
    private  String fromEasemobMember;
    @ApiModelProperty(name = "toEasemob", value = "（环信）接收人的username或者接收group的ID")
    private  String toEasemob;
    @ApiModelProperty(name = "chatType", value = "聊天类型 0-单聊、1-群聊、2-聊天室")
    private  int chatType;
    @ApiModelProperty(name = "msgType", value = "消息类型  0-文本类型消息 1-图片类型消息 2-地址位置类型消息 3-语音类型消息 4-视频类型消息 5-文件类型消息")
    private  String msgType;
    @ApiModelProperty(name = "msgTime", value = "消息发送时间")
    private LocalDateTime  msgTime;
    @ApiModelProperty(name = "chatMsg", value = "消息内容")
    private  String chatMsg;
}
