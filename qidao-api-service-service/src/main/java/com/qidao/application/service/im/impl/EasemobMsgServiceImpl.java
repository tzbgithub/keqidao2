package com.qidao.application.service.im.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qidao.application.model.easemob.*;
import com.qidao.application.service.im.EasemobMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EasemobMsgServiceImpl implements EasemobMsgService {
    private String host = "host";

    @Autowired
    private EasemobUrl easemobUrl;

    @Autowired
    private EasemobUtil easemobUtil;

    @Override
    public EasemobSendTextMessageRes sendTextMessage(EasemobSendTextMessageReq req) {
        //TODO 暂时不考虑 收发消息的对象不存在的情况 (ashiamd)[2021-03-23 ~ ]
        log.info("EasemobMsgServiceImpl -> sendTextMessage -> start -> EasemobSendTextMessageReq req : {}", req);
        EasemobSendTextMessageRes res = null;
        String url = easemobUrl.getSendTextMessageUrl();
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url,reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            String rawJson = response.get(0);
            rawJson = rawJson.substring(1,rawJson.length()-1);
            String[] split = rawJson.split(",");
            List<EasemobSendTextMessageRes.Resp> respList = new ArrayList<>();
            for(String str : split){
                String[] objs = str.split(":");
                EasemobSendTextMessageRes.Resp resp = EasemobSendTextMessageRes.Resp.builder()
                        .to(objs[0].substring(1, objs[0].length() - 1))
                        .success("\"success\"".equals(objs[1]))
                        .build();
                respList.add(resp);
            }
            res = EasemobSendTextMessageRes.builder()
                    .respList(respList)
                    .build();
        }
        log.info("EasemobMsgServiceImpl -> sendTextMessage -> result -> {}", res);
        return res;
    }

    @Override
    public Object sendPictureMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object sendAudioMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object sendVideoMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object sendLocMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object sendCmdMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object sendCustomMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object sendExtendMessage() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }
}
