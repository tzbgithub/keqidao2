package com.qidao.application.service.msg;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 消息发送接口
 *
 * @author 晚成
 */
public interface MsgSendService {
    /**
     * 发送消息 重载方法
     *
     * @param content 内容
     * @return
     */
    default void send(String content) {
        this.send(null, null, Arrays.asList(content), null);
    }

    /**
     * 发送消息 重载方法
     *
     * @param contents 内容
     * @param type     类型
     * @return
     */
    default void send(List<String> contents, Integer type) {
        this.send(null, null, contents, type);
    }

    /**
     * 发送消息 重载方法
     *
     * @param sender   发送人: 如果为null: 默认系统用户
     * @param contents 内容
     * @param type     类型
     * @return
     */
    default void send(String sender, List<String> contents, Integer type) {
        this.send(null, sender, contents, type);
    }

    /**
     * 发送消息 重载方法
     *
     * @param receivers 接收人
     * @param sender    发送人: 如果为null: 默认系统用户
     * @param contents  内容
     * @param type      类型
     * @return
     */
    default void send(List<String> receivers, String sender, List<String> contents, Integer type) {
        this.send(MsgSendDTO.builder()
                .receivers(receivers)
                .sender(sender)
                .contents(contents)
                .contentType(type)
                .build());
    }
    /**
     * 发送消息 ： 只发送消息. 频繁发送的验证,消息超时
     *
     * @param param
     * @return
     */
    void send(MsgSendDTO param);

}
