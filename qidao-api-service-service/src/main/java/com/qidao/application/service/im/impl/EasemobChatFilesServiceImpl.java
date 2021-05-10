package com.qidao.application.service.im.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.qidao.application.model.easemob.EasemobBaseDTO;
import com.qidao.application.model.easemob.EasemobUtil;
import com.qidao.application.model.easemob.chatfile.UploadRes;
import com.qidao.application.service.im.EasemobChatFilesService;
import com.qidao.application.service.upload.TxUploadService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EasemobChatFilesServiceImpl implements EasemobChatFilesService {
    @Autowired
    private EasemobUtil easemobUtil;
    @Autowired
    private TxUploadService uploadService;
    @Resource
    private RedissonClient redissonClient;

    @Override
    public UploadRes uploadChatFiles(MultipartFile file) {
        log.info("EasemobChatFilesServiceImpl -> uploadChatFiles -> start");
        UploadRes upload = easemobUtil.upload(file);
        log.info("EasemobChatFilesServiceImpl -> uploadChatFiles -> easemob -> ok -> {}",upload);
        // todo 需要建立文件之间的关联：在 log_chat_msg 表新加字段： backup_address 需要通过 uuid 进行关联 [v1.1]
        String uuid = upload.getUuid();
        String uploadStr = uploadService.uploadImage(file);
        log.info("EasemobChatFilesServiceImpl -> uploadChatFiles -> cos -> ok -> {}",uploadStr);
        // todo 修改 redisdb [v1.3]
        RBucket<String> bucket = redissonClient.getBucket("easemob::file::"+uuid);
        bucket.set(uploadStr,60, TimeUnit.DAYS);
        return upload;
    }

    @Override
    public Object downloadChatFiles() {
        Map<String, Object> param = new HashMap<>();
 /*
        Header:

        Content-Type	application/json
Authorization	Bearer ${token}
share-secret	share-secret
         */
        String respondStr = HttpRequest.delete(easemobUtil.downloadChatFiles(null))
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object downloadThumb() {
        Map<String, Object> param = new HashMap<>();
        /*
        Header:

        Content-Type	application/json
Authorization	Bearer ${token}
share-secret	share-secret
         */
        String respondStr = HttpRequest.get(easemobUtil.downloadChatFiles(null))
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }
}
