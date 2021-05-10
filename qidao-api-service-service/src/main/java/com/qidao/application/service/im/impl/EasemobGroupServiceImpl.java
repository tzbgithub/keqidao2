package com.qidao.application.service.im.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qidao.application.model.easemob.*;
import com.qidao.application.service.im.EasemobGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EasemobGroupServiceImpl implements EasemobGroupService {
    /**
     * todo implements (Autuan)[start.3.16]
     */
    /**
     * todo apollo set val (Autuan)[start.3.16]
     */
    private String host = "host";

    @Autowired
    private EasemobUrl easemobUrl;

    @Autowired
    private EasemobUtil easemobUtil;

    @Override
    public EasemobGetChatGroupsRes getChatGroups(EasemobGetChatGroupsReq req) {
        //TODO 这里暂时没使用到 cursor和limit参数 (ashiamd)[2021-03-23 ~ ]
        log.info("EasemobGroupServiceImpl -> getChatGroups -> start -> EasemobGetChatGroupsReq req : {}", req);
        EasemobGetChatGroupsRes res = null;
        String url = easemobUrl.getChatGroupsUrl();
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetChatGroupsRes.Group> groupList = new ArrayList<>(response.size());
            for (String str : response) {
                groupList.add(new Gson().fromJson(str, EasemobGetChatGroupsRes.Group.class));
            }
            res = EasemobGetChatGroupsRes.builder()
                    .groupList(groupList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> getChatGroups -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetJoinedChatGroupsRes getJoinedChatGroups(EasemobGetJoinedChatGroupsReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-15 ~ ]
        log.info("EasemobGroupServiceImpl -> getJoinedChatGroups -> start -> EasemobGetJoinedChatGroupsReq req : {}", req);
        EasemobGetJoinedChatGroupsRes res = null;
        String url = easemobUrl.getJoinedChatGroupsUrl(String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetJoinedChatGroupsRes.Group> groupList = new ArrayList<>(response.size());
            for (String str : response) {
                groupList.add(new Gson().fromJson(str, EasemobGetJoinedChatGroupsRes.Group.class));
            }
            res = EasemobGetJoinedChatGroupsRes.builder()
                    .groupList(groupList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> getJoinedChatGroups -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetGroupDetailByIdRes getGroupDetailById(EasemobGetGroupDetailByIdReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-15 ~ ]
        log.info("EasemobGroupServiceImpl -> getGroupDetailById -> start -> EasemobGetGroupDetailByIdReq req : {}", req);
        EasemobGetGroupDetailByIdRes res = null;
        String url = easemobUrl.getGroupDetailById(req.getGroupIds());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetGroupDetailByIdRes.GroupDetail> groupDetailList = new ArrayList<>(response.size());
            for (String str : response) {
                groupDetailList.add(new Gson().fromJson(str, EasemobGetGroupDetailByIdRes.GroupDetail.class));
            }
            res = EasemobGetGroupDetailByIdRes.builder()
                    .groupDetailList(groupDetailList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> getGroupDetailById -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobCreateGroupRes createGroup(EasemobCreateGroupReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-15 ~ ]
        log.info("EasemobGroupServiceImpl -> createGroup -> start -> EasemobCreateGroupReq req : {}", req);
        EasemobCreateGroupRes res = null;
        String url = easemobUrl.getCreateGroupUrl();
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobCreateGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> createGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobUpdateGroupRes updateGroup(EasemobUpdateGroupReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-15 ~ ]
        log.info("EasemobGroupServiceImpl -> updateGroup -> start -> EasemobUpdateGroupReq req : {}", req);
        EasemobUpdateGroupRes res = null;
        String url = easemobUrl.getUpdateGroupUrl(req.getGroupId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.putForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobUpdateGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> updateGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobDeleteGroupRes deleteGroup(EasemobDeleteGroupReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> deleteGroup -> start -> EasemobDeleteGroupReq req : {}", req);
        EasemobDeleteGroupRes res = null;
        String url = easemobUrl.getDeleteGroupUrl(req.getGroupId());
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobDeleteGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> deleteGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetGroupAnnouncementRes getGroupAnnouncement(EasemobGetGroupAnnouncementReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> getGroupAnnouncement -> start -> EasemobGetGroupAnnouncementReq req : {}", req);
        EasemobGetGroupAnnouncementRes res = null;
        String url = easemobUrl.getGroupAnnouncementUrl(req.getGroupId());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobGetGroupAnnouncementRes.class);
        }
        log.info("EasemobGroupServiceImpl -> getGroupAnnouncement -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobUpdateGroupAnnouncementRes updateGroupAnnouncement(EasemobUpdateGroupAnnouncementReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> updateGroupAnnouncement -> start -> EasemobUpdateGroupAnnouncementReq req : {}", req);
        EasemobUpdateGroupAnnouncementRes res = null;
        String url = easemobUrl.getUpdateGroupAnnouncementUrl(req.getGroupId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobUpdateGroupAnnouncementRes.class);
        }
        log.info("EasemobGroupServiceImpl -> updateGroupAnnouncement -> result -> {}", res);
        return res;
    }

    @Override
    public Object getGroupShareFiles() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object uploadGroupShareFile() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object downloadGroupShareFile() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public Object deleteGroupShareFile() {
        Map<String, Object> param = new HashMap<>();
        String respondStr = HttpRequest.get(host + "/{org_name}/{app_name}/chatfiles/{file_uuid}\n")
                .form(param).execute().body();
        log.info("EasemobUserServiceImpl -> getToken -> http -> respond -> {}", respondStr);
        JSONObject respondObj = JSONObject.parseObject(respondStr);
        return respondObj;
    }

    @Override
    public EasemobGetGroupMembersRes getGroupMembers(EasemobGetGroupMembersReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> getGroupMembers -> start -> EasemobGetGroupMembersReq req : {}", req);
        EasemobGetGroupMembersRes res = null;
        String url = easemobUrl.getGroupMembersUrl(req.getGroupId(), req.getPageNum(), req.getPageSize());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetGroupMembersRes.Member> memberList = new ArrayList<>(response.size());
            for (String str : response) {
                memberList.add(new Gson().fromJson(str, EasemobGetGroupMembersRes.Member.class));
            }
            res = EasemobGetGroupMembersRes.builder()
                    .memberList(memberList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> getGroupMembers -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddMemberToGroupRes addMemberToGroup(EasemobAddMemberToGroupReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> addMemberToGroup -> start -> EasemobAddMemberToGroupReq req : {}", req);
        EasemobAddMemberToGroupRes res = null;
        String url = easemobUrl.getAddMemberToGroupUrl(req.getGroupId(), String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.postForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddMemberToGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> addMemberToGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddMemberToGroupBatchRes addMemberToGroupBatch(EasemobAddMemberToGroupBatchReq req) {
        //TODO 实际还需要考虑 群组/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> addMemberToGroupBatch -> start -> EasemobAddMemberToGroupBatchReq req : {}", req);
        EasemobAddMemberToGroupBatchRes res = null;
        String url = easemobUrl.getAddMemberToGroupBatchUrl(req.getGroupId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddMemberToGroupBatchRes.class);
        }
        log.info("EasemobGroupServiceImpl -> addMemberToGroupBatch -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveMemberFromGroupRes removeMemberFromGroup(EasemobRemoveMemberFromGroupReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroup -> start -> EasemobRemoveMemberFromGroupReq req : {}", req);
        EasemobRemoveMemberFromGroupRes res = null;
        String url = easemobUrl.getRemoveMemberFromGroupUrl(req.getGroupId(), String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobRemoveMemberFromGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetGroupAdminMembersRes getGroupAdminMembers(EasemobGetGroupAdminMembersReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> getGroupAdminMembers -> start -> EasemobGetGroupAdminMembersReq req : {}", req);
        EasemobGetGroupAdminMembersRes res = null;
        String url = easemobUrl.getGroupAdminMembersUrl(req.getGroupId());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = EasemobGetGroupAdminMembersRes.builder()
                    .members(response)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> getGroupAdminMembers -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveMemberFromGroupBatchRes removeMemberFromGroupBatch(EasemobRemoveMemberFromGroupBatchReq req) {
        //TODO 实际还需要考虑 群组/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupBatch -> start -> EasemobRemoveMemberFromGroupBatchReq req : {}", req);
        EasemobRemoveMemberFromGroupBatchRes res = null;
        String url = easemobUrl.getRemoveMemberFromGroupBatchUrl(req.getGroupId(), req.getMembers());
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobRemoveMemberFromGroupBatchRes.Resp> respList = new ArrayList<>(response.size());
            for (String str : response) {
                respList.add(new Gson().fromJson(str, EasemobRemoveMemberFromGroupBatchRes.Resp.class));
            }
            res = EasemobRemoveMemberFromGroupBatchRes.builder()
                    .respList(respList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupBatch -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddAdminMemberToGroupRes addAdminMemberToGroup(EasemobAddAdminMemberToGroupReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> addAdminMemberToGroup -> start -> EasemobAddAdminMemberToGroupReq req : {}", req);
        EasemobAddAdminMemberToGroupRes res = null;
        String url = easemobUrl.getAddAdminMemberToGroupUrl(req.getGroupId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddAdminMemberToGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> addAdminMemberToGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveAdminMemberFromGroupRes removeAdminMemberFromGroup(EasemobRemoveAdminMemberFromGroupReq req) {
        //TODO 实际还需要考虑 群组/用户 不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-22 ~ ]
        log.info("EasemobGroupServiceImpl -> removeAdminMemberFromGroup -> start -> EasemobRemoveAdminMemberFromGroupReq req : {}", req);
        EasemobRemoveAdminMemberFromGroupRes res = null;
        String url = easemobUrl.getRemoveAdminMemberFromGroupUrl(req.getGroupId(),String.valueOf(req.getOldadmin()));
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobRemoveAdminMemberFromGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> removeAdminMemberFromGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobTransferGroupRes transferGroup(EasemobTransferGroupReq req) {
        //TODO 实际还需要考虑 群组/用户 不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-22 ~ ]
        log.info("EasemobGroupServiceImpl -> transferGroup -> start -> EasemobTransferGroupReq req : {}", req);
        EasemobTransferGroupRes res = null;
        String url = easemobUrl.getTransferGroupUrl(req.getGroupId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.putForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobTransferGroupRes.class);
        }
        log.info("EasemobGroupServiceImpl -> transferGroup -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetGroupBlockListRes getGroupBlockList(EasemobGetGroupBlockListReq req) {
        //TODO 实际还需要考虑 群组不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-22 ~ ]
        log.info("EasemobGroupServiceImpl -> getGroupBlockList -> start -> EasemobGetGroupBlockListReq req : {}", req);
        EasemobGetGroupBlockListRes res = null;
        String url = easemobUrl.getGroupBlockListUrl(req.getGroupId());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = EasemobGetGroupBlockListRes.builder()
                    .members(response)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> getGroupBlockList -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddMemberToGroupBlockListRes addMemberToGroupBlockList(EasemobAddMemberToGroupBlockListReq req) {
        //TODO 实际还需要考虑 群组/用户不存在、不能添加群管理员到黑名单 等情况，这里暂时不考虑 (ashiamd)[2021-03-22 ~ ]
        log.info("EasemobGroupServiceImpl -> addMemberToGroupBlockList -> start -> EasemobAddMemberToGroupBlockListReq req : {}", req);
        EasemobAddMemberToGroupBlockListRes res = null;
        String url = easemobUrl.getAddMemberToGroupBlockListUrl(req.getGroupId(),String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.postForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddMemberToGroupBlockListRes.class);
        }
        log.info("EasemobGroupServiceImpl -> addMemberToGroupBlockList -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddMemberToGroupBlockListBatchRes addMemberToGroupBlockListBatch(EasemobAddMemberToGroupBlockListBatchReq req) {
        //TODO 实际还需要考虑 群组/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-19 ~ ]
        log.info("EasemobGroupServiceImpl -> addMemberToGroupBlockListBatch -> start -> EasemobAddMemberToGroupBlockListBatchReq req : {}", req);
        EasemobAddMemberToGroupBlockListBatchRes res = null;
        String url = easemobUrl.getAddMemberToGroupBlockListBatchUrl(req.getGroupId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobAddMemberToGroupBlockListBatchRes.Resp> respList = new ArrayList<>(response.size());
            for (String str : response) {
                respList.add(new Gson().fromJson(str, EasemobAddMemberToGroupBlockListBatchRes.Resp.class));
            }
            res = EasemobAddMemberToGroupBlockListBatchRes.builder()
                    .respList(respList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> addMemberToGroupBlockListBatch -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveMemberFromGroupBlockListRes removeMemberFromGroupBlockList(EasemobRemoveMemberFromGroupBlockListReq req) {
        //TODO 实际还需要考虑 群组/用户不存在 等情况，这里暂时不考虑 (ashiamd)[2021-03-22 ~ ]
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupBlockList -> start -> EasemobRemoveMemberFromGroupBlockListReq req : {}", req);
        EasemobRemoveMemberFromGroupBlockListRes res = null;
        String url = easemobUrl.getRemoveMemberFromGroupBlockListUrl(req.getGroupId(),String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobRemoveMemberFromGroupBlockListRes.class);
        }
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupBlockList -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveMemberFromGroupBlockListBatchRes removeMemberFromGroupBlockListBatch(EasemobRemoveMemberFromGroupBlockListBatchReq req) {
        //TODO 实际还需要考虑 群组/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-23 ~ ]
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupBlockListBatch -> start -> EasemobRemoveMemberFromGroupBlockListBatchReq req : {}", req);
        EasemobRemoveMemberFromGroupBlockListBatchRes res = null;
        String url = easemobUrl.getRemoveMemberFromGroupBlockListBatchUrl(req.getGroupId(),req.getUsernames());
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobRemoveMemberFromGroupBlockListBatchRes.Resp> respList = new ArrayList<>(response.size());
            for (String str : response) {
                respList.add(new Gson().fromJson(str, EasemobRemoveMemberFromGroupBlockListBatchRes.Resp.class));
            }
            res = EasemobRemoveMemberFromGroupBlockListBatchRes.builder()
                    .respList(respList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupBlockListBatch -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddMemberToGroupMuteListRes addMemberToGroupMuteList(EasemobAddMemberToGroupMuteListReq req){
        //TODO 实际还需要考虑 群组/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-23 ~ ]
        log.info("EasemobGroupServiceImpl -> addMemberToGroupMuteList -> start -> EasemobAddMemberToGroupMuteListReq req : {}", req);
        EasemobAddMemberToGroupMuteListRes res = null;
        String url = easemobUrl.getAddMemberToGroupMuteListUrl(req.getGroupId());
        List<String> response = easemobUtil.postForList(url, req.getReqBodyJson(), String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddMemberToGroupMuteListRes.class);
        }
        log.info("EasemobGroupServiceImpl -> addMemberToGroupMuteList -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveMemberFromGroupMuteListRes removeMemberFromGroupMuteList(EasemobRemoveMemberFromGroupMuteListReq req) {
        //TODO 实际还需要考虑 群组/用户 不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-23 ~ ]
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupMuteList -> start -> EasemobRemoveMemberFromGroupMuteListReq req : {}", req);
        EasemobRemoveMemberFromGroupMuteListRes res = null;
        String url = easemobUrl.getRemoveMemberFromGroupMuteListUrl(req.getGroupId(), req.getUsernamesStr());
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobRemoveMemberFromGroupMuteListRes.Members> userList = new ArrayList<>(response.size());
            for (String str : response) {
                userList.add(new Gson().fromJson(str, EasemobRemoveMemberFromGroupMuteListRes.Members.class));
            }
            res = EasemobRemoveMemberFromGroupMuteListRes.builder()
                    .userList(userList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> removeMemberFromGroupMuteList -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetGroupMuteListRes getGroupMuteList(EasemobGetGroupMuteListReq req) {
        //TODO 实际还需要考虑 群组/用户 不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-23 ~ ]
        log.info("EasemobGroupServiceImpl -> getGroupMuteList -> start -> EasemobGetGroupMuteListReq req : {}", req);
        EasemobGetGroupMuteListRes res = null;
        String url = easemobUrl.getGroupMuteListUrl(req.getGroupId());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetGroupMuteListRes.MutedUser> mutedUserList = new ArrayList<>(response.size());
            for (String str : response) {
                mutedUserList.add(new Gson().fromJson(str, EasemobGetGroupMuteListRes.MutedUser.class));
            }
            res = EasemobGetGroupMuteListRes.builder()
                    .mutedUserList(mutedUserList)
                    .build();
        }
        log.info("EasemobGroupServiceImpl -> getGroupMuteList -> result -> {}", res);
        return res;
    }
}
