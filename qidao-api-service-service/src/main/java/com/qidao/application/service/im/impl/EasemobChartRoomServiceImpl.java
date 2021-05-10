package com.qidao.application.service.im.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qidao.application.model.easemob.*;
import com.qidao.application.service.im.EasemobChartRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EasemobChartRoomServiceImpl implements EasemobChartRoomService {
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
    public EasemobGetAllChatRoomsRes getAllChatRooms() {
        log.info("EasemobChartRoomServiceImpl -> getAllChatRooms -> start");
        EasemobGetAllChatRoomsRes res = null;
        String url = easemobUrl.getAllChatRoomsUrl();
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetAllChatRoomsRes.ChatRoomProfile> chatRoomList = new ArrayList<>(response.size());
            for (String str : response) {
                chatRoomList.add(new Gson().fromJson(str, EasemobGetAllChatRoomsRes.ChatRoomProfile.class));
            }
            res = EasemobGetAllChatRoomsRes.builder()
                    .chatRoomList(chatRoomList)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> getAllChatRooms -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetJoinedChatRoomsByMemberNameRes getJoinedChatRoomsByMemberName(EasemobGetJoinedChatRoomsByMemberNameReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-10 ~ ]
        log.info("EasemobChartRoomServiceImpl -> getJoinedChatRoomsByMemberName -> start -> EasemobGetJoinedChatRoomsByMemberNameReq req : {}", req);
        EasemobGetJoinedChatRoomsByMemberNameRes res = null;
        String url = easemobUrl.getJoinedChatRoomsByMemberNameUrl(String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetJoinedChatRoomsByMemberNameRes.ChatRoomProfile> chatRoomList = new ArrayList<>(response.size());
            for (String str : response) {
                chatRoomList.add(new Gson().fromJson(str, EasemobGetJoinedChatRoomsByMemberNameRes.ChatRoomProfile.class));
            }
            res = EasemobGetJoinedChatRoomsByMemberNameRes.builder()
                    .chatRoomList(chatRoomList)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> getJoinedChatRoomsByMemberName -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetChatRoomDetailByIdRes getChatRoomDetailById(EasemobGetChatRoomDetailByIdReq req) {
        //TODO 实际还需要考虑 聊天室Id不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-08 ~ ]
        log.info("EasemobChartRoomServiceImpl -> getChatRoomDetailById -> start -> EasemobGetChatRoomDetailByIdReq req : {}", req);
        EasemobGetChatRoomDetailByIdRes res = null;
        String url = easemobUrl.getChatRoomDetailByIdUrl(req.getChatroomId());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobGetChatRoomDetailByIdRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> getChatRoomDetailById -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobCreateChatRoomRes createChatRoom(EasemobCreateChatRoomReq req) {
        //TODO 实际还需要考虑 用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-08 ~ ]
        log.info("EasemobChartRoomServiceImpl -> createChatRoom -> start -> EasemobCreateChatRoomReq req : {}", req);
        EasemobCreateChatRoomRes res = null;
        String url = easemobUrl.getCreateChatRoomUrl();
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobCreateChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> createChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobUpdateChatRoomRes updateChatRoom(EasemobUpdateChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-10 ~ ]
        log.info("EasemobChartRoomServiceImpl -> updateChatRoom -> start -> EasemobUpdateChatRoomReq req : {}", req);
        EasemobUpdateChatRoomRes res = null;
        String url = easemobUrl.getUpdateChatRoomUrl(req.getChatroomId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.putForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobUpdateChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> updateChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobDeleteChatRoomRes deleteChatRoom(EasemobDeleteChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-10 ~ ]
        log.info("EasemobChartRoomServiceImpl -> deleteChatRoom -> start -> EasemobDeleteChatRoomReq req : {}", req);
        EasemobDeleteChatRoomRes res = null;
        String url = easemobUrl.getDeleteChatRoomUrl(req.getChatroomId());
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobDeleteChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> deleteChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetChatRoomMembersRes getChatRoomMembers(EasemobGetChatRoomMembersReq req) {
        //TODO 实际还需要考虑 聊天室不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-10 ~ ]
        log.info("EasemobChartRoomServiceImpl -> getChatRoomMembers -> start -> EasemobGetChatRoomMembersReq req : {}", req);
        EasemobGetChatRoomMembersRes res = null;
        String url = easemobUrl.getChatRoomMembersUrl(req.getChatroomId(), req.getPageNum(), req.getPageSize());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetChatRoomMembersRes.Member> memberList = new ArrayList<>(response.size());
            for (String str : response) {
                memberList.add(new Gson().fromJson(str, EasemobGetChatRoomMembersRes.Member.class));
            }
            res = EasemobGetChatRoomMembersRes.builder()
                    .memberList(memberList)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> getChatRoomMembers -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddMemberToChatRoomRes addMemberToChatRoom(EasemobAddMemberToChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-10 ~ ]
        log.info("EasemobChartRoomServiceImpl -> addMemberToChatRoom -> start -> EasemobAddMemberToChatRoomReq req : {}", req);
        EasemobAddMemberToChatRoomRes res = null;
        String url = easemobUrl.getAddMemberToChatRoomUrl(req.getChatroomId(), String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.postForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddMemberToChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> addMemberToChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddMemberToChatRoomBatchRes addMemberToChatRoomBatch(EasemobAddMemberToChatRoomBatchReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-10 ~ ]
        log.info("EasemobChartRoomServiceImpl -> addMemberToChatRoomBatch -> start -> EasemobAddMemberToChatRoomBatchReq req : {}", req);
        EasemobAddMemberToChatRoomBatchRes res = null;
        String url = easemobUrl.getAddMemberToChatRoomBatchUrl(req.getChatroomId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddMemberToChatRoomBatchRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> addMemberToChatRoomBatch -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveMemberFromChatRoomRes removeMemberFromChatRoom(EasemobRemoveMemberFromChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> removeMemberFromChatRoom -> start -> EasemobRemoveMemberFromChatRoomReq req : {}", req);
        EasemobRemoveMemberFromChatRoomRes res = null;
        String url = easemobUrl.getRemoveMemberFromChatRoomUrl(req.getChatroomId(), String.valueOf(req.getUsername()));
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobRemoveMemberFromChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> removeMemberFromChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveMemberFromChatRoomBatchRes removeMemberFromChatRoomBatch(EasemobRemoveMemberFromChatRoomBatchReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> removeMemberFromChatRoomBatch -> start -> EasemobRemoveMemberFromChatRoomBatchReq req : {}", req);
        EasemobRemoveMemberFromChatRoomBatchRes res = null;
        String url = easemobUrl.getRemoveMemberFromChatRoomBatchUrl(req.getChatroomId(), req.getUsernamesStr());
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobRemoveMemberFromChatRoomBatchRes.Resp> respList = new ArrayList<>(response.size());
            for (String str : response) {
                respList.add(new Gson().fromJson(str, EasemobRemoveMemberFromChatRoomBatchRes.Resp.class));
            }
            res = EasemobRemoveMemberFromChatRoomBatchRes.builder()
                    .respList(respList)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> removeMemberFromChatRoomBatch -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetChatRoomAdminMembersRes getChatRoomAdminMembers(EasemobGetChatRoomAdminMembersReq req) {
        //TODO 实际还需要考虑 聊天室不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> getChatRoomAdminMembers -> start -> EasemobGetChatRoomAdminMembersReq req : {}", req);
        EasemobGetChatRoomAdminMembersRes res = null;
        String url = easemobUrl.getChatRoomAdminMembersUrl(req.getChatroomId());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = EasemobGetChatRoomAdminMembersRes.builder()
                    .usernames(response)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> getChatRoomAdminMembers -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddAdminMemberToChatRoomRes addAdminMemberToChatRoom(EasemobAddAdminMemberToChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> addAdminMemberToChatRoom -> start -> EasemobAddAdminMemberToChatRoomReq req : {}", req);
        EasemobAddAdminMemberToChatRoomRes res = null;
        String url = easemobUrl.getAddAdminMemberToChatRoomUrl(req.getChatroomId());
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddAdminMemberToChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> addAdminMemberToChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveAdminMemberFromChatRoomRes removeAdminMemberFromChatRoom(EasemobRemoveAdminMemberFromChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> removeAdminMemberFromChatRoom -> start -> EasemobRemoveAdminMemberFromChatRoomReq req : {}", req);
        EasemobRemoveAdminMemberFromChatRoomRes res = null;
        String url = easemobUrl.getRemoveAdminMemberFromChatRoomUrl(req.getChatroomId(), String.valueOf(req.getOldadmin()));
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobRemoveAdminMemberFromChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> removeAdminMemberFromChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetChatRoomMuteListRes getChatRoomMuteList(EasemobGetChatRoomMuteListReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> getChatRoomMuteList -> start -> EasemobGetChatRoomMuteListReq req : {}", req);
        EasemobGetChatRoomMuteListRes res = null;
        String url = easemobUrl.getChatRoomMuteList(req.getChatroomId());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobGetChatRoomMuteListRes.MutedUser> mutedUserList = new ArrayList<>(response.size());
            for (String str : response) {
                mutedUserList.add(new Gson().fromJson(str, EasemobGetChatRoomMuteListRes.MutedUser.class));
            }
            res = EasemobGetChatRoomMuteListRes.builder()
                    .mutedUserList(mutedUserList)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> getChatRoomMuteList -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobSetMemberMuteWithChatRoomRes setMemberMuteWithChatRoom(EasemobSetMemberMuteWithChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> setMemberMuteWithChatRoom -> start -> EasemobSetMemberMuteWithChatRoomReq req : {}", req);
        EasemobSetMemberMuteWithChatRoomRes res = null;
        String url = easemobUrl.getSetMemberMuteWithChatRoomUrl(req.getChatroomId());
        List<String> response = easemobUtil.postForList(url, req.getReqBodyJson(), String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobSetMemberMuteWithChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> setMemberMuteWithChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobCancelMemberMuteWithChatRoomRes cancelMemberMuteWithChatRoom(EasemobCancelMemberMuteWithChatRoomReq req) {
        //TODO 实际还需要考虑 聊天室/用户不存在等情况，这里暂时不考虑 (ashiamd)[2021-03-11 ~ ]
        log.info("EasemobChartRoomServiceImpl -> cancelMemberMuteWithChatRoom -> start -> EasemobCancelMemberMuteWithChatRoomReq req : {}", req);
        EasemobCancelMemberMuteWithChatRoomRes res = null;
        String url = easemobUrl.getCancelMemberMuteWithChatRoomUrl(req.getChatroomId(), req.getUsernamesStr());
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            List<EasemobCancelMemberMuteWithChatRoomRes.Members> userList = new ArrayList<>(response.size());
            for (String str : response) {
                userList.add(new Gson().fromJson(str, EasemobCancelMemberMuteWithChatRoomRes.Members.class));
            }
            res = EasemobCancelMemberMuteWithChatRoomRes.builder()
                    .userList(userList)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> cancelMemberMuteWithChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobGetChatRoomSuperAdminListRes getChatRoomSuperAdminList(EasemobGetChatRoomSuperAdminListReq req) {
        log.info("EasemobChartRoomServiceImpl -> getChatRoomSuperAdminList -> start -> EasemobGetChatRoomSuperAdminListReq req : {}", req);
        EasemobGetChatRoomSuperAdminListRes res = null;
        String url = easemobUrl.getChatRoomSuperAdminListUrl(req.getPageNum(), req.getPageSize());
        List<String> response = easemobUtil.getForList(url, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = EasemobGetChatRoomSuperAdminListRes.builder()
                    .usernames(response)
                    .build();
        }
        log.info("EasemobChartRoomServiceImpl -> getChatRoomSuperAdminList -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobAddSuperAdminToChatRoomRes addSuperAdminToChatRoom(EasemobAddSuperAdminToChatRoomReq req) {
        log.info("EasemobChartRoomServiceImpl -> addSuperAdminToChatRoom -> start -> EasemobAddSuperAdminToChatRoomReq req : {}", req);
        EasemobAddSuperAdminToChatRoomRes res = null;
        String url = easemobUrl.getAddSuperAdminToChatRoomUrl();
        String reqBodyJson = req.getReqBodyJson();
        List<String> response = easemobUtil.postForList(url, reqBodyJson, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobAddSuperAdminToChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> addSuperAdminToChatRoom -> result -> {}", res);
        return res;
    }

    @Override
    public EasemobRemoveSuperAdminFromChatRoomRes removeSuperAdminFromChatRoom(EasemobRemoveSuperAdminFromChatRoomReq req) {
        log.info("EasemobChartRoomServiceImpl -> removeSuperAdminFromChatRoom -> start -> EasemobRemoveSuperAdminFromChatRoomReq req : {}", req);
        EasemobRemoveSuperAdminFromChatRoomRes res = null;
        String url = easemobUrl.getRemoveSuperAdminFromChatRoomUrl(String.valueOf(req.getSuperAdmin()));
        List<String> response = easemobUtil.deleteForList(url, null, String.class);
        boolean responseNotEmpty = ObjectUtil.isNotEmpty(response);
        if (responseNotEmpty) {
            res = new Gson().fromJson(response.get(0), EasemobRemoveSuperAdminFromChatRoomRes.class);
        }
        log.info("EasemobChartRoomServiceImpl -> removeSuperAdminFromChatRoom -> result -> {}", res);
        return res;
    }
}
