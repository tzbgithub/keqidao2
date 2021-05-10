package com.qidao.application.service.msg.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.utils.IOUtils;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgExceptionEnum;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;
import com.qidao.application.model.msg.listen.QidaoSchedulingConfigurer;
import com.qidao.application.service.msg.MsgSendService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 电子邮件发送
 *
 * @author Autuan.Yu
 */
@Service("EmailMsgSendImpl")
@Slf4j
public class EmailMsgSendImpl implements MsgSendService {
    @Value("${spring.mail.from}")
    private String sender;

    private Map<Integer, String> templateMap;
    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private QidaoSchedulingConfigurer qidaoSchedulingConfigurer;

    /**
     * 发送电子邮件
     *
     * @param param 消息内容
     */
    @Override
    public void send(MsgSendDTO param) {
        log.info("EmailMsgSendImpl -> send -> param -> {}", param);
        List<String> receivers = param.getReceivers();
        if (CollUtil.isEmpty(receivers)) {
            // todo 获取所有发送人 (Autuan.Yu)[21.3.15]
            param.setReceivers(Arrays.asList("autuan.yu@gmail.com"));
            // todo 如果无接收人：发送到全平台用户（Autuan.Yu)[21.3.15]
        }
        // todo 电子邮件模板 : （Autuan.Yu)[21.3.15]
        LocalDateTime sendTime = param.getSendTime();
        if (null == sendTime || sendTime.isBefore(LocalDateTime.now())) {
            execute(param);
        } else {
            qidaoSchedulingConfigurer.addTask(param.getId(), () -> {
                execute(param);
            }, sendTime);
        }
    }


    /**
     * 发送简单字符串邮件
     *
     * @param tos     邮件接收人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    private void sendSimpleMail(List<String> tos, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(ArrayUtil.toArray(tos, String.class));
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }


    /**
     * 发送HTML文本邮件
     *
     * @param tos     邮件接收人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    private void sendHtmlMail(List<String> tos, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(sender);
            //邮件接收人
            messageHelper.setTo(ArrayUtil.toArray(tos, String.class));
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
        } catch (MessagingException e) {
            log.error("sendHtml error", e);
        }
    }
    /**
     * 发送合同文案
     *
     * @param title              邮件标题
     * @param receiver           接收人(邮箱)
     * @param name               接受人姓名
     * @param attachment         合同PDF附件
     * @param attachmentFileName 附件名
     */
    public void sendContractCopy(String title, String receiver, String name, InputStream attachment, String attachmentFileName) {
        String content = "";
        String path = "/Users/ashiamd/mydocs/docs/project/QiDao/qidao_app/docs/邮件发送/合同文案.html";
        boolean isFile = FileUtil.isFile(path);
        if (isFile) {
            content += FileUtil.readString(path, StandardCharsets.UTF_8);
            content = CharSequenceUtil.format(content, name);
        } else {
            throw new BusinessException("未找到合同文案模板html");
        }
        this.send(MsgSendDTO.builder()
                .title(Collections.singletonList(title))
                .contentType(MsgSendTypeEnum.CONTRACT_COPY.getVal())
                .receivers(Collections.singletonList(receiver))
                .contents(Collections.singletonList(content))
                .attachment(attachment)
                .attachmentFileName(attachmentFileName)
                .build());
    }
    /**
     * 发送发票文案
     *
     * @param title                 邮件标题
     * @param receiver              接收人(邮箱)
     * @param invoiceAmount         开票金额
     * @param invoiceDateTime       开票时间
     * @param attachment            发票PDF附件
     * @param attachmentFileName    附件名
     */
    public void sendInvoiceCopy(String title,String receiver,Double invoiceAmount,LocalDateTime invoiceDateTime, InputStream attachment, String attachmentFileName){
        String content = "";
        String path = "/Users/ashiamd/mydocs/docs/project/QiDao/qidao_app/docs/邮件发送/发票文案.html";
        boolean isFile = FileUtil.isFile(path);
        if (isFile) {
            content += FileUtil.readString(path, StandardCharsets.UTF_8);
            content = CharSequenceUtil.format(content, invoiceAmount,invoiceDateTime);
        } else {
            throw new BusinessException("未找到发票文案模版html");
        }
        this.send(MsgSendDTO.builder()
                .title(Collections.singletonList(title))
                .contentType(MsgSendTypeEnum.INVOICE_COPY.getVal())
                .receivers(Collections.singletonList(receiver))
                .contents(Collections.singletonList(content))
                .attachment(attachment)
                .attachmentFileName(attachmentFileName)
                .build());
    }

    /**
     * 发送符件邮件
     *
     * @param tos     邮件接收人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    @SneakyThrows
    private void sendAttachmentsMail(List<String> tos, String subject, String content, InputStream attachment, String attachmentFileName) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(ArrayUtil.toArray(tos, String.class));
        helper.setSubject(subject);
        helper.setText(content, true);

        helper.addAttachment(attachmentFileName, new ByteArrayResource(IOUtils.toByteArray(attachment)));
        mailSender.send(message);
        // todo 日志信息 (Autuan)[2.23]
    }

    /**
     * 格式化需要发送的内容
     *
     * @param param 发送消息请求入口
     * @return 要发送的消息
     */
    private void execute(MsgSendDTO param) {
        List<String> contents = param.getContents();
        if (CollUtil.isEmpty(contents)) {
            throw new BusinessException(MsgExceptionEnum.CONTENT);
        }
        List<String> titles = param.getTitle();
        if (CollUtil.isEmpty(titles)) {
            throw new BusinessException(MsgExceptionEnum.TITLE);
        }
        MsgSendTypeEnum sendType = MsgSendTypeEnum.switchEnum(param.getContentType());

        String title = "";
        if (titles.size() > 1) {
            String[] titleParam = titles.subList(1, titles.size()).toArray(new String[0]);
            title = StrUtil.format(titles.get(0), titleParam);
        } else {
            title = String.join("", titles);
        }
        String content = "";
        if (contents.size() > 1) {
            String[] contentParam = contents.subList(1, contents.size()).toArray(new String[0]);
            content = StrUtil.format(contents.get(0), contentParam);
        } else {
            content = String.join("", contents);
        }
        switch (sendType) {
            case CODE:
            case NO_TEMPLATE: {
                sendHtmlMail(param.getReceivers(), title, content);
                break;
            }
            case CONTRACT_PDF: {
                InputStream attachment = param.getAttachment();
                String attachmentFileName = param.getAttachmentFileName();
                if (StrUtil.isBlank(attachmentFileName) || null == attachment) {
                    throw new BusinessException(MsgExceptionEnum.CONTENT);
                }
                sendAttachmentsMail(param.getReceivers(), title, content, attachment, attachmentFileName);
                break;
            }
            case CONTRACT_COPY: {
                InputStream attachment = param.getAttachment();
                String attachmentFileName = param.getAttachmentFileName();
                if (StrUtil.isBlank(attachmentFileName) || null == attachment) {
                    throw new BusinessException(MsgExceptionEnum.CONTENT);
                }
                sendAttachmentsMail(param.getReceivers(), title, content, attachment, attachmentFileName);
                break;
            }
            case INVOICE_COPY: {
                InputStream attachment = param.getAttachment();
                String attachmentFileName = param.getAttachmentFileName();
                if (StrUtil.isBlank(attachmentFileName) || null == attachment) {
                    throw new BusinessException(MsgExceptionEnum.CONTENT);
                }
                sendAttachmentsMail(param.getReceivers(), title, content, attachment, attachmentFileName);
                break;
            }
            default: {
                throw new BusinessException(MsgExceptionEnum.SUPPORT);
            }
        }
    }
}
