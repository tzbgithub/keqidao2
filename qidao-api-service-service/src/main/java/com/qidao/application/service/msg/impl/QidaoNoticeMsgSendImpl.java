package com.qidao.application.service.msg.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.common.collect.Lists;
import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.entity.msg.MsgRecordExample;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgConstantEnum;
import com.qidao.application.model.msg.enums.MsgExceptionEnum;
import com.qidao.application.model.msg.listen.QidaoSchedulingConfigurer;
import com.qidao.application.service.dingtalk.IDingTalkService;
import com.qidao.application.service.msg.MsgSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 企岛发送通知消息
 *
 * @author Autuan.Yu
 */
@Service("QidaoNoticeMsgSendImpl")
@Slf4j
public class QidaoNoticeMsgSendImpl implements MsgSendService {
    /**
     * 发送人
     * todo 通过第三方（极光）获取  （Autuan.Yu)[21.3.15]
     */
    private final String SENDER = "SYSTEM";
    @Resource
    private QidaoSchedulingConfigurer qidaoSchedulingConfigurer;
    @Resource
    private MsgRecordMapper msgRecordMapper;
    @Value("${knife4j.enable}")
    private boolean isTestDev;
    @Value("${msg.aurora.key}")
    private String auroraKey;
    @Value("${msg.aurora.secret}")
    private String auroraSecret;

    @Autowired
    private IDingTalkService dingTalkService;

    /**
     * 接收人
     *
     * @param param
     * @return
     */
    @Override
    public void send(MsgSendDTO param) {
        // 通知消息只有系统发送
        param.setSender(SENDER);
        List<String> receivers = param.getReceivers();
        if (CollUtil.isNotEmpty(receivers)) {
            log.info("QidaoNoticeMsgSendImpl --> send  发送人数{}", receivers.size());
            int size = receivers.size();
            if (size > 1000) {
                List<List<String>> partitions = Lists.partition(param.getReceivers(), 1000);// 使用guava
                LocalDateTime sendTime = param.getSendTime();
                partitions.stream().forEach(x -> {
                    param.setReceivers(x);
                    if (null == sendTime || sendTime.isBefore(LocalDateTime.now())) {
                        execute(param);
                    } else {
                        qidaoSchedulingConfigurer.addTask(param.getId(), () -> {
                            execute(param);
                        }, sendTime);
                    }
                });
                return;
            }
        }

        LocalDateTime sendTime = param.getSendTime();
        if (null == sendTime || sendTime.isBefore(LocalDateTime.now())) {
            log.info("QidaoNoticeMsgSendImpl -> send -> 立即推送 -> {}",sendTime);
            execute(param);
        } else {
            log.info("QidaoNoticeMsgSendImpl -> send -> 定时推送 -> {}",sendTime);
            qidaoSchedulingConfigurer.addTask(param.getId(), () -> {
                execute(param);
            }, sendTime);
        }

    }


    private void execute(MsgSendDTO param) {
        Map<String, String> extra = new HashMap<>(2);
        if(StrUtil.isNotBlank(param.getPathApp())) {
            extra.put("pathApp", param.getPathApp());
        }
        if(StrUtil.isNotBlank(param.getPathPc())) {
            extra.put("pathPc", param.getPathPc());
        }
        switch (param.getContentType()) {
            case 0: {
                sendMsg(param.getReceivers(), String.join("", param.getTitle()), String.join("", param.getContents()), param.getId(),extra);
                break;
            }
            default: {
                throw new BusinessException(MsgExceptionEnum.SUPPORT);
            }
        }
    }


    private String formatTitle(Integer titleType, List<String> contents) {
        return String.join("", contents);
    }

    private void sendMsg(List<String> receivers, String title, String content, Long msgRecordId,Map<String,String> extra) {
        // 修改 msg_record 为已发送 : 未读
        MsgRecordExample msgRecordExample = new MsgRecordExample();
        msgRecordExample.createCriteria()
                .andSendNumEqualTo(1)
                .andIdEqualTo(msgRecordId)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());

        MsgRecord recordUpdateBean = MsgRecord.builder()
                .status(MsgConstantEnum.RECORD_UNREAD.getCode())
                .build();
        msgRecordMapper.updateByExampleSelective(recordUpdateBean, msgRecordExample);

        JPushClient jpushClient = new JPushClient(auroraSecret, auroraKey, null, ClientConfig.getInstance());
        Audience audience;
        if (CollUtil.isEmpty(receivers)) {
            audience = Audience.all();
        } else {
            // 极光推送 -> ios 不支持纯数字 -> 加上 "qidao" 后缀
            receivers = receivers.stream()
                    .map(str -> str = str + "qidao")
                    .collect(Collectors.toList());
            audience = Audience.alias(receivers);
        }

        Message message = Message.newBuilder()
                .setMsgContent(content)
                .setTitle(title)
                .addExtras(extra)
                .build();

        PushPayload payload = generatorPushPayload(audience, message, title, content);

        try {
            log.info("APIRequestException -> jpush -> payload -> {}", payload);
            PushResult result = jpushClient.sendPush(payload);
            log.info("QidaoNoticeMsgSendImpl -> send ->  result - " + result);
        } catch (APIRequestException e) {
            // 用户未找到 cannot find user by this audience or has been inactive for more than 255 days
            // todo 枚举值 (Autuan)[3.31]
            if (1011 == e.getErrorCode()) {
                log.warn("QidaoNoticeMsgSendImpl -> send ->  未找到用户设备 -> alias -> {} ", receivers);
            } else {
                log.error("QidaoNoticeMsgSendImpl -> sendMsg -> APIRequestException -> error ", e);
                dingTalkService.sendMsg("极光推送失败 APIRequestException ！" + e.getMessage());
            }
        } catch (APIConnectionException e) {
            log.error("QidaoNoticeMsgSendImpl -> sendMsg -> APIConnectionException -> error ", e);
            dingTalkService.sendMsg("极光推送失败 APIConnectionException ！" + e.getMessage());
        }
    }

    /**
     * 构建推送对象：平台是 Andorid 与 iOS， <br/>
     * 推送目标是 （"tag1" 与 "tag2" 的并集）交（"alias1" 与 "alias2" 的并集），<br/>
     * 推送内容是 - 内容为 MSG_CONTENT 的消息<br/>
     *
     * @param notificationContent 通知栏展示的内容
     * @param notificationTitle   因为IOS没有此字段，不传  ： 极光会固定为 企岛
     */

    public PushPayload generatorPushPayload(Audience audience, Message message, String notificationTitle, String notificationContent) {
//        IosNotification iosNotification = IosNotification.newBuilder()
//                .setAlert(notificationContent)
//                .addExtra("path_app", "index")
////                        .addCustom()
//                .build();
        Notification build = Notification.newBuilder()
//                .add()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(notificationContent)
                        .setTitle(notificationContent)
                        .addCustom("uri_action", "cn.jiguang.uniplugin_jpush.OpenClickActivity")
                        .addCustom("uri_activity", "cn.jiguang.uniplugin_jpush.OpenClickActivity")
//                        .addExtra("pathApp",)
                        .build()
                )
                .addPlatformNotification(IosNotification.newBuilder()
                        .setAlert(notificationContent)
//                        .addExtra("pathApp", (String) null)
//                        .addCustom()
                        .build())
                .build();
        return PushPayload.newBuilder()
                // 平台
                .setPlatform(Platform.all())
                // 听众（接收人）
                .setAudience(audience)
                // 消息内容
                .setMessage(message)
                // alert 模式
//                .setNotification(
//                        Notification.newBuilder()
//                        .setAlert(notificationContent)
////                        .setTitle("")
//
//                        .addCustom("uri_flag","HelloWorld")
//                        .build()
//                )
//                .setNotification(Notification.alert(notificationContent))
                .setNotification(build)

                .setOptions(Options.newBuilder()
                        .setApnsProduction(!isTestDev)
                        .build())
                .build();
    }
}
