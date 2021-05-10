package com.qidao.application.model.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Autuan.Yu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgSendDTO {
    /**
     * 消息ID
     */
    private Long id;

    /**
     * <p>接收人: 如果为null：则给所有用户发送</p>
     * <ul>
     *     <li>短信发送 ： 手机号</li>
     *     <li>邮件发送 ： 邮箱地址</li>
     *     <li>极光 & 系统推送 ： 用户ID</li>
     * </ul>
     */
    private List<String> receivers;

    /**
     * 发送人: 如果为null: 默认系统用户
     * 邮件、短信 ： 固定为系统发送
     */
    private String sender;

    /**
     * 内容
     */
    private List<String> contents;

    /**
     * <p>消息内容类型</p>
     * 0: 不使用消息模板，直接将<code>contents</code>拼接后发送到接收人<br>
     * 1: 发送验证码：会忽视 contents 下的所有内容，直接通过随机数生成器生成验证码并通过固定模板发送。 <br>
     *    发送成功后会以 sms::send::code::{mobile} 为 key 存入code值，过期时间5分钟<br>
     *    发送验证码时不能批量发送 <br>
     * 2: 附件发送 ： 只在 E-mail 时 启用
     * 3: 发送合同文案
     * 4: 发送发票文案
     */
    private Integer contentType;

    /**
     * <p>标题内容类型 : 目前仅在邮件发送需要</p>
     * 0: 不使用消息模板，直接将<code>title</code>拼接后作为标题
     */
    private Integer titleType;

    /**
     * 标题 <br>
     * 发送邮件时必填
     */
    private List<String> title;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 附件 <br>
     * 发送附件时必填
     */
    private InputStream attachment;

    /**
     * 附件文件名<br>
     * 发送附件时必填<br>
     * 例：  attachment.txt
     */
    private String attachmentFileName;

    private String pathApp;
    private String pathPc;
}
