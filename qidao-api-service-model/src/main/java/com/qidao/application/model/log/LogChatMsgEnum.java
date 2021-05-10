package com.qidao.application.model.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/29 10:23 AM
 */
@Getter
@AllArgsConstructor
public enum LogChatMsgEnum {
    /**
     * MSG_TYPE 消息类型
     */

    /**
     * 文本类型消息
     */
    MSG_TYPE_TEXT("0"),
    /**
     * 图片类型消息
     */
    MSG_TYPE_IMG("1"),
    /**
     * 地址位置类型消息
     */
    MSG_TYPE_ADDRESS("2"),
    /**
     * 语音类型消息
     */
    MSG_TYPE_VOICE("3"),
    /**
     * 视频类型消息
     */
    MSG_TYPE_VIDEO("4"),
    /**
     * 文件类型消息
     */
    MSG_TYPE_FILE("5"),
    ;

    public static LogChatMsgEnum getByVal(String val) {
        for (LogChatMsgEnum item : LogChatMsgEnum.values()) {
            if(item.val.equals(val)){
                return item;
            }
        }
        return null;
    }

    private final String val;
}
