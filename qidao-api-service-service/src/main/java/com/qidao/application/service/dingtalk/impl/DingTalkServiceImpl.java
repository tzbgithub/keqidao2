package com.qidao.application.service.dingtalk.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.qidao.application.model.dingtalk.DingtalkAt;
import com.qidao.application.model.dingtalk.DingtalkMsgText;
import com.qidao.application.model.dingtalk.DingtalkText;
import com.qidao.application.service.dingtalk.IDingTalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Autuan.Yu
 */
@Service
@Slf4j
public class DingTalkServiceImpl implements IDingTalkService {
    @Value("${dingtalk.exception.url}")
    private String postAddress;
    @Value("${qidao.env}")
    private String env;

    @Override
    public void sendMsg(String content) {
        String prefix = StrUtil.format("业务异常报警 : \n 环境： {} \n ", env);
        // 只有 生产环境 atAll
        boolean isAtAll = "prod".equals(env);
        // 共 4 个环境 ： 开发 dev 测试 test 准线上 uat 线上 prod
        // 只有 uat 和 prod 发送钉钉预警
        boolean isSend = "prod".equals(env) || "uat".equals(env);
        if (isSend) {
            DingtalkMsgText msg = DingtalkMsgText.builder()
                    .text(new DingtalkText(prefix + content))
                    .at(DingtalkAt.builder().isAtAll(isAtAll).build())
                    .build();
            HttpUtil.post(postAddress, JSONUtil.toJsonStr(msg));
        }
    }
}
