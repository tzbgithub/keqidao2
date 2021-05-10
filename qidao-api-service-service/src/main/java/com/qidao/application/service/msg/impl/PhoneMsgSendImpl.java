package com.qidao.application.service.msg.impl;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.MemberInfoBashRes;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgExceptionEnum;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;
import com.qidao.application.model.msg.listen.QidaoSchedulingConfigurer;
import com.qidao.application.model.sms.SmsSendRequest;
import com.qidao.application.service.member.MemberService;
import com.qidao.application.service.msg.MsgSendService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 手机短信发送
 *
 * @author Autuan.Yu
 */
@Service("PhoneMsgSendImpl")
@Slf4j
public class PhoneMsgSendImpl implements MsgSendService {
    private final String serverUrl;
    private final String account;
    private final String password;
    private final QidaoSchedulingConfigurer qidaoSchedulingConfigurer;
    private final MemberService memberService;
    private final RedissonClient redissonClient;
    Map<Integer, String> templateMap;

    @SneakyThrows
    public PhoneMsgSendImpl(@Value("${chuanglan.account}") String account, @Value("${chuanglan.password}") String password,
                            @Value("${chuanglan.server}") String serverUrl,
                            @Autowired MemberService memberService, @Autowired QidaoSchedulingConfigurer qidaoSchedulingConfigurer,
                            @Autowired RedissonClient redissonClient) {
        this.account = account;
        this.serverUrl = serverUrl;
        this.password = password;
        this.memberService = memberService;
        this.redissonClient = redissonClient;
        this.qidaoSchedulingConfigurer = qidaoSchedulingConfigurer;
        this.templateMap = new HashMap<>(1);
        this.templateMap.put(MsgSendTypeEnum.CODE.getVal(), "【企岛科技】亲爱的用户，您的短信验证码为{},5分钟内有效，若非本人操作请忽略。");
    }

    @Override
    public void send(MsgSendDTO param) {
        log.info("PhoneMsgSendImpl -> send -> param -> {}", param);

        if (CollUtil.isEmpty(param.getReceivers())) {
            log.info("PhoneMsgSendImpl -> send -> 无接收人 -> 全平台发送 ");
            List<MemberInfoBashRes> members = memberService.listAllMemberPhone();
            List<String> mobiles = members.stream().map(MemberInfoBashRes::getMobile).collect(Collectors.toList());
            log.info("PhoneMsgSendImpl -> send -> 无接收人 -> 全平台发送 -> mobiles -> {}", mobiles);
            param.setReceivers(mobiles);
        }

        LocalDateTime sendTime = param.getSendTime();
        if (null == sendTime || sendTime.isBefore(LocalDateTime.now())) {
            execute(param);
        } else {
            qidaoSchedulingConfigurer.addTask(param.getId(), () -> {
                execute(param);
            }, sendTime);
        }
    }

    private void execute(MsgSendDTO param) {
        switch (param.getContentType()) {
            case 0: {
                sendMsg(param.getReceivers(), String.join("", param.getContents()));
                break;
            }
            case 1: {
                List<String> receivers = param.getReceivers();
                if (param.getReceivers().size() > 1) {
                    throw new BusinessException(MsgExceptionEnum.BATCH);
                }
                String template = templateMap.get(param.getContentType());
                RandomGenerator randomGenerator = new RandomGenerator("0123456789", 6);
                String code = randomGenerator.generate();
                String content = StrUtil.format(template, code);
                sendMsg(param.getReceivers(), content);

                RBucket<String> codeBucket = redissonClient.getBucket("sms::send::code::" + receivers.get(0));
                codeBucket.set(code, 5, TimeUnit.MINUTES);
                break;
            }
            default: {
                throw new BusinessException(MsgExceptionEnum.SUPPORT);
            }
        }
    }

    private void sendMsg(List<String> receivers, String content) {
        // 创蓝 最大每次只能发送1000 条短信
        int receiverSize = receivers.size();
        int pageSize = 1000;
        int totalPageNum = (receiverSize / pageSize) + 1;
        int currentPage = 0;
        do {
            int fromIndex = currentPage * pageSize;

            List<String> loopList = receivers.subList(fromIndex, Math.min(fromIndex + pageSize, receiverSize));

            SmsSendRequest sendRequest = SmsSendRequest.getInstance(account, password)
                    .setPhone(String.join(",", loopList))
                    .setMsg(content);
            String requestJson = JSON.toJSONString(sendRequest);

            log.info("ChangLanSmsSendServiceImpl -> smsSend -> requestJson -> {}", requestJson);
            String response = sendToShanYan(requestJson);
            JSONObject dataObj = JSONObject.parseObject(response);
            log.info("ChangLanSmsSendServiceImpl -> smsSend -> third res -> {}", dataObj);
            boolean isSend = null != dataObj && "0".equals(dataObj.getString("code"));
            if (!isSend) {
                log.error("ChangLanSmsSendServiceImpl -> smsSend -> 创蓝 -> 发送失败 -> response -> {}", dataObj);
                throw new BusinessException("ChangLanSmsSendServiceImpl -> smsSend -> 创蓝 -> 发送失败 -> response -> " + dataObj);
            }
            currentPage++;
        } while (currentPage < totalPageNum);
    }

    public String sendToShanYan(String postContent) {
            HttpResponse response = HttpUtil.createPost(serverUrl)
                    .body(postContent)
                    .contentType(ContentType.JSON.getValue())
                    .charset(StandardCharsets.UTF_8)
                    .execute();
            String body = response.body();

            log.info("ChangLanSmsSendServiceImpl -> sendToShanYan -> response -> {}",body);
            return body;
    }
}
