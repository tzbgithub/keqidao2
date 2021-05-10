package com.qidao.application.model.easemob;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.FileResource;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.easemob.chatfile.UploadRes;
import com.qidao.application.model.easemob.enums.EasemobExceptionEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 环信 IM 工具类
 */
@Slf4j
@Component
public class EasemobUtil {
    @Autowired
    private RedissonClient redissonClient;

    public String token() {
        return generatorUrl(tokenUrl);
    }

    public String uploadChatFiles() {
        return generatorUrl(fileUrl);
    }

    public String downloadChatFiles(String uuid) {
        return generatorUrl(downloadFileUrl);
    }


    public String register() {
        return generatorUrl(registerUrl);
    }

    public String getUser(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(getUserUrl, replaceMap);
    }

    public String getUserBatch(){
        return generatorUrl(getUserBatchUrl);
    }

    public String updateNickName(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(nickNameUrl, replaceMap);
    }

    public String notifyShowType(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(notifyShowTypeUrl, replaceMap);
    }

    public String setNotDisturb(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(setNotDisturbUrl, replaceMap);
    }

    public String deleteUser(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(deleteUserUrl, replaceMap);
    }

    public String updatePwd(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(updatePwdUrl, replaceMap);
    }

    public String addFriend(Long ownerUsername, Long friendUsername) {
        Map<String, String> replaceMap = new HashMap<>(2);
        replaceMap.put("{owner_username}", String.valueOf(ownerUsername));
        replaceMap.put("{friend_username}", String.valueOf(friendUsername));
        return generatorUrl(addFriendUrl, replaceMap);
    }

    public String removeFriend(Long ownerUsername, Long friendUsername) {
        Map<String, String> replaceMap = new HashMap<>(2);
        replaceMap.put("{owner_username}", String.valueOf(ownerUsername));
        replaceMap.put("{friend_username}", String.valueOf(friendUsername));
        return generatorUrl(removeFriendUrl, replaceMap);
    }

    public String getFriendList(Long memberId) {
        Map<String, String> replaceMap = new HashMap<>(1);
        replaceMap.put("{owner_username}", String.valueOf(memberId));
        return generatorUrl(friendUrl, replaceMap);
    }


    public String getStatusUrl(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(statusUrl, replaceMap);

    }

    public String disconnect(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(disconnectUrl, replaceMap);

    }

    public String statusBatchUrl() {
        return generatorUrl(statusBatchUrl);
    }

    public String getOffLineMsgCountUrl(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{owner_username}", username);
        return generatorUrl(offLineMsgCountUrl, replaceMap);
    }

    public String generatorOffLineMsgStatusUrl(String username, String msgId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        replaceMap.put("{msg_id}", msgId);
        return generatorUrl(offLineMsgCountUrl, replaceMap);
    }

    private String tokenUrl = "/{org_name}/{app_name}/token";

    private String fileUrl = "/{org_name}/{app_name}/chatfiles";
    private String downloadFileUrl = "/{org_name}/{app_name}/chatfiles/{uuid}";

    private String registerUrl = "/{org_name}/{app_name}/users";
    private String getUserUrl = "/{org_name}/{app_name}/users/{username}";
    private String getUserBatchUrl = "/{org_name}/{app_name}/users";
    private String deleteUserUrl = "/{org_name}/{app_name}/users/{username}";
    private String updatePwdUrl = "/{org_name}/{app_name}/users/{username}/password";
    private String nickNameUrl = "/{org_name}/{app_name}/users/{username}";
    private String notifyShowTypeUrl = "/{org_name}/{app_name}/users/{username}";
    private String setNotDisturbUrl = "/{org_name}/{app_name}/users/{username}";
    private String addFriendUrl = "/{org_name}/{app_name}/users/{owner_username}/contacts/users/{friend_username}";
    private String removeFriendUrl = "/{org_name}/{app_name}/users/{owner_username}/contacts/users/{friend_username}";
    private String friendUrl = "/{org_name}/{app_name}/users/{owner_username}/contacts/users";
    private String statusUrl = "/{org_name}/{app_name}/users/{username}/status";
    private String statusBatchUrl = "/{org_name}/{app_name}/users/batch/status";
    private String offLineMsgCountUrl = "/{org_name}/{app_name}/users/{owner_username}/offline_msg_count";
    private String offLineMsgStatusUrl = "/{org_name}/{app_name}/users/{username}/offline_msg_status/{msg_id}";
    private String disconnectUrl = "/{org_name}/{app_name}/users/{username}/disconnect";


    @Value("${im.easemob.host}")
    private String host;
    @Value("${im.easemob.orgName}")
    private String orgName;
    @Value("${im.easemob.appName}")
    private String appName;
    @Value("${im.easemob.clientId}")
    private String clientId;
    @Value("${im.easemob.clientSecret}")
    private String clientSecret;

    private String generatorUrl(String path) {
        String replacePath = path.replace("{org_name}", orgName)
                .replace("{app_name}", appName);
        return host + replacePath;
    }

    private String generatorUrl(String path, Map<String, String> replaceMap) {
        String replacePath = path.replace("{org_name}", orgName)
                .replace("{app_name}", appName);

        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            replacePath = replacePath.replace(key, val);
        }
        return host + replacePath;
    }

    private String refreshToken() {
        Map<String, Object> param = new HashMap<>();
        param.put("grant_type", "client_credentials");
        param.put("client_id", clientId);
        param.put("client_secret", clientSecret);
        String url = this.token();
        String reqBody = new Gson().toJson(param);
        log.info("EasemobUserServiceImpl -> refreshToken -> http -> url -> {} reqBody -> {}", url, reqBody);
        HttpResponse response = HttpRequest.post(url)
                .body(reqBody)
                .execute();
        int status = response.getStatus();
        if (200 != status) {
            throw new RuntimeException("IM Token 获取失败");
        }

        /*
            {
              "application": "f8859a44-179b-487d-a838-f2a22e84efc3",
              "access_token": "YWMtbjAuGHsSEeufWt1uF78cuQAAAAAAAAAAAAAAAAAAAAH4hZpEF5tIfag48qIuhO_DAgMAAAF38UNyqQBPGgDy901fEmalATxPEzNut78P2tLlPHEIV1lDPLFvUIZ54Q",
              "expires_in": 5184000
            }
         */
        String respondStr = response.body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> status -> {} respondStr -> {}", status, respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);

        String redisKey = "easemob::token";

        RBucket<String> bucket = redissonClient.getBucket(redisKey);

        String token = respondObj.getString("access_token");
        Long expireTime = respondObj.getLong("expires_in");
        bucket.set(token, expireTime, TimeUnit.SECONDS);

        log.info("EasemobUserServiceImpl -> refreshToken -> redis 保存 -> {}", token);
        return token;
    }

    private String getToken() {
        log.info("EasemobUserServiceImpl -> getToken -> start");
        String redisKey = "easemob::token";

        RBucket<String> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists()) {
            log.info("EasemobUserServiceImpl -> getToken ->  redis 存在 -> {}", bucket.get());
            return bucket.get();
        }
        return refreshToken();
    }

    public UploadRes upload(MultipartFile file) {
        List<UploadRes> result = executeHttpSend("POST", uploadChatFiles(), "", UploadRes.class, true, file);
        return CollUtil.isEmpty(result) ? null : result.get(0);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO get(String url) {
        return get(url, EasemobBaseDTO.class);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO get(String url, Class<? extends EasemobBaseDTO> clazz) {
        return get(url, null, clazz);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO get(String url, String requestJsonBody, Class<? extends EasemobBaseDTO> clazz) {
        List<? extends EasemobBaseDTO> responseData = executeHttpSend("GET", url, requestJsonBody, clazz);
        return CollUtil.isEmpty(responseData) ? null : responseData.get(0);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO post(String url) {
        return post(url, EasemobBaseDTO.class);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO post(String url, Class<? extends EasemobBaseDTO> clazz) {
        return post(url, null, clazz);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO post(String url, String requestJsonBody, Class<? extends EasemobBaseDTO> clazz) {
        List<? extends EasemobBaseDTO> responseData = executeHttpSend("POST", url, requestJsonBody, clazz);
        return CollUtil.isEmpty(responseData) ? null : responseData.get(0);
    }

    public <T> EasemobBaseDTO delete(String url) {
        return delete(url, EasemobBaseDTO.class);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO delete(String url, Class<? extends EasemobBaseDTO> clazz) {
        return delete(url, null, clazz);
    }

    public <T extends EasemobBaseDTO> EasemobBaseDTO delete(String url, String requestJsonBody, Class<? extends EasemobBaseDTO> clazz) {
        List<? extends EasemobBaseDTO> responseData = executeHttpSend("DELETE", url, requestJsonBody, clazz);
        return CollUtil.isEmpty(responseData) ? null : responseData.get(0);
    }

    public <T> T put(String url, String requestJsonBody, Class<T> clazz) {
        List<T> ts = putForList(url, requestJsonBody, clazz);
        return CollUtil.isEmpty(ts) ? null : ts.get(0);
    }

    public <T> List<T> putForList(String url, String requestJsonBody) {
        return executeHttpSend("PUT", url, requestJsonBody, null);
    }

    public <T> List<T> putForList(String url, String requestJsonBody, Class<T> clazz) {
        return executeHttpSend("PUT", url, requestJsonBody, clazz);
    }

    public <T> List<T> postForList(String url, String requestJsonBody, Class<T> clazz) {
        return executeHttpSend("POST", url, requestJsonBody, clazz);
    }

    public <T> List<T> deleteForList(String url, String requestJsonBody, Class<T> clazz) {
        return executeHttpSend("DELETE", url, requestJsonBody, clazz);
    }

    public <T> List<T> getForList(String url, Class<T> clazz) {
        return executeHttpSend("GET", url, null, clazz);
    }

    /**
     * 执行 HTTP 方法
     *
     * @return
     */
    public <T> List<T> executeHttpSend(String type, String url,
                                       String reqJsonBody,
                                       Class<T> clazz) {
        return executeHttpSend(type, url, reqJsonBody, clazz, false);
    }

    public <T> List<T> executeHttpSend(String type, String url,
                                       String reqJsonBody,
                                       Class<T> clazz, boolean isRepeat) {
        return executeHttpSend(type, url, reqJsonBody, clazz, isRepeat, null);
    }

    @SneakyThrows
    public <T> List<T> executeHttpSend(String type, String url,
                                       String reqJsonBody,
                                       Class<T> clazz, boolean isRepeat, MultipartFile file) {
        HttpRequest httpRequest;
        switch (type) {
            case "GET":
                httpRequest = HttpRequest.get(url);
                break;
            case "POST":
                httpRequest = HttpRequest.post(url);
                break;
            case "DELETE":
                httpRequest = HttpRequest.delete(url);
                break;
            case "PATCH":
                httpRequest = HttpRequest.patch(url);
                break;
            case "PUT":
                httpRequest = HttpRequest.put(url);
                break;
            default:
                throw new RuntimeException("未定义的type类型");
        }

        if (null != file && !file.isEmpty()) {
            InputStreamResource fileResource = new InputStreamResource(file.getInputStream(), file.getOriginalFilename());

            httpRequest.contentType(ContentType.MULTIPART.getValue())
                    .header("restrict-access", "true")
                    .form("file", fileResource);
        } else {
            httpRequest.body(reqJsonBody);
        }
        log.info("easemob -> send -> type -> {} url -> {} reqJsonBody -> {} isRepeat -> {}", type, url, reqJsonBody, isRepeat);

        httpRequest.header("Authorization", "Bearer " + getToken());

        HttpResponse response = httpRequest.execute();

        int status = response.getStatus();
        String respondStr = response.body();
        log.warn("easemob -> send -> respond -> status -> {} respondStr -> {}", status, respondStr);
        if (401 == status && (!isRepeat)) {
            refreshToken();
            return executeHttpSend(type, url, reqJsonBody, clazz, true);
        }

        if (200 != status) {
            JSONObject errorObject = JSONObject.parseObject(respondStr);
            // 重复注册
            if ("DuplicateUniquePropertyExistsException".equals(errorObject.getString("exception"))) {
                // 视为成功
                throw new BusinessException(EasemobExceptionEnum.REPEAT);
            }
            // 用户不存在
            if (404 == status || "UserNotFoundException".equals(errorObject.getString("exception"))) {
                throw new BusinessException(EasemobExceptionEnum.USER);
            }
            throw new BusinessException("环信执行失败");
        }

        EasemobResponse easemobResponse = JSONObject.parseObject(respondStr, EasemobResponse.class);
        return easemobResponse.getResponseData(clazz);
    }

}
