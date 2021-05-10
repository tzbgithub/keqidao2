package com.qidao.application.service.im.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.qidao.application.service.im.EasemobChatMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EasemobChatMessagesServiceImpl implements EasemobChatMessagesService {
    private String host = "host";
    @Override
    public Object getHistoryMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }
}
