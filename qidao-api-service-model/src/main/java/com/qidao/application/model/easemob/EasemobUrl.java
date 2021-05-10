package com.qidao.application.model.easemob;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/8 3:52 PM
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 环信 IM URL
 */
@Slf4j
@Component
public class EasemobUrl {

    @Value("${im.easemob.host}")
    private String host;
    @Value("${im.easemob.orgName}")
    private String orgName;
    @Value("${im.easemob.appName}")
    private String appName;

    @Value("${im.easemob.clientId}")

    public String getSendTextMessageUrl() {
        return generatorUrl(sendTextMessageUrl);
    }

    public String getChatGroupsUrl() {
        return generatorUrl(getChatGroupsUrl);
    }

    public String getJoinedChatGroupsUrl(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(getJoinedChatGroupsUrl, replaceMap);
    }

    public String getGroupDetailById(String groupIds) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_ids}", groupIds);
        return generatorUrl(getGroupDetailByIdUrl, replaceMap);
    }

    public String getCreateGroupUrl() {
        return generatorUrl(createGroupUrl);
    }

    public String getUpdateGroupUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(updateGroupUrl, replaceMap);
    }

    public String getDeleteGroupUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(deleteGroupUrl, replaceMap);
    }

    public String getGroupAnnouncementUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(getGroupAnnouncementUrl, replaceMap);
    }

    public String getUpdateGroupAnnouncementUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(updateGroupAnnouncementUrl, replaceMap);
    }

    public String getGroupMembersUrl(String groupId, Integer pageNum, Integer pageSize) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{pagenum}", String.valueOf(pageNum));
        replaceMap.put("{pagesize}", String.valueOf(pageSize));
        return generatorUrl(getGroupMembersUrl, replaceMap);
    }

    public String getAddMemberToGroupUrl(String groupId, String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{username}", username);
        return generatorUrl(addMemberToGroupUrl, replaceMap);
    }

    public String getAddMemberToGroupBatchUrl(String chatgroupid) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatgroupid}", chatgroupid);
        return generatorUrl(addMemberToGroupBatchUrl, replaceMap);
    }

    public String getRemoveMemberFromGroupUrl(String groupId, String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{username}", username);
        return generatorUrl(removeMemberFromGroupUrl, replaceMap);
    }

    public String getRemoveMemberFromGroupBatchUrl(String groupId, String members) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{memebers}", members);
        return generatorUrl(removeMemberFromGroupBatchUrl, replaceMap);
    }

    public String getGroupAdminMembersUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(getGroupAdminMembersUrl, replaceMap);
    }

    public String getAddAdminMemberToGroupUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(addAdminMemberToGroupUrl, replaceMap);
    }

    public String getRemoveAdminMemberFromGroupUrl(String groupId, String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{oldadmin}", username);
        return generatorUrl(removeAdminMemberFromGroupUrl, replaceMap);
    }

    public String getTransferGroupUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{groupid}", groupId);
        return generatorUrl(transferGroupUrl, replaceMap);
    }

    public String getGroupBlockListUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(getGroupBlockListUrl, replaceMap);
    }

    public String getAddMemberToGroupBlockListUrl(String groupId, String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{username}", username);
        return generatorUrl(addMemberToGroupBlockListUrl, replaceMap);
    }

    public String getAddMemberToGroupBlockListBatchUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(addMemberToGroupBlockListBatchUrl, replaceMap);
    }

    public String getRemoveMemberFromGroupBlockListUrl(String groupId, String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{username}", username);
        return generatorUrl(removeMemberFromGroupBlockListUrl, replaceMap);
    }

    public String getRemoveMemberFromGroupBlockListBatchUrl(String groupId, String usernames) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{usernames}", usernames);
        return generatorUrl(removeMemberFromGroupBlockListBatchUrl, replaceMap);
    }

    public String getAddMemberToGroupMuteListUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(addMemberToGroupMuteListUrl, replaceMap);
    }

    public String getRemoveMemberFromGroupMuteListUrl(String groupId, String usernames) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        replaceMap.put("{usernames}", usernames);
        return generatorUrl(removeMemberFromGroupMuteListUrl, replaceMap);
    }

    public String getGroupMuteListUrl(String groupId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{group_id}", groupId);
        return generatorUrl(getGroupMuteListUrl, replaceMap);
    }

    public String getAllChatRoomsUrl() {
        return generatorUrl(getAllChatRoomsUrl);
    }

    public String getJoinedChatRoomsByMemberNameUrl(String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{username}", username);
        return generatorUrl(getJoinedChatRoomsByMemberNameUrl, replaceMap);
    }

    public String getChatRoomDetailByIdUrl(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        return generatorUrl(getChatRoomDetailByIdUrl, replaceMap);
    }

    public String getCreateChatRoomUrl() {
        return generatorUrl(createChatRoomUrl);
    }

    public String getUpdateChatRoomUrl(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        return generatorUrl(updateChatRoomUrl, replaceMap);
    }

    public String getDeleteChatRoomUrl(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        return generatorUrl(deleteChatRoomUrl, replaceMap);
    }

    public String getChatRoomMembersUrl(String chatroomId, Integer pageNum, Integer pageSize) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        replaceMap.put("{pagenum}", String.valueOf(pageNum));
        replaceMap.put("{pagesize}", String.valueOf(pageSize));
        return generatorUrl(getChatRoomMembersUrl, replaceMap);
    }

    public String getAddMemberToChatRoomUrl(String chatroomId, String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroomid}", chatroomId);
        replaceMap.put("{username}", username);
        return generatorUrl(addMemberToChatRoomUrl, replaceMap);
    }

    public String getAddMemberToChatRoomBatchUrl(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroomid}", chatroomId);
        return generatorUrl(addMemberToChatRoomBatchUrl, replaceMap);
    }

    public String getRemoveMemberFromChatRoomUrl(String chatroomId, String username) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroomid}", chatroomId);
        replaceMap.put("{username}", username);
        return generatorUrl(removeMemberFromChatRoomUrl, replaceMap);
    }

    public String getRemoveMemberFromChatRoomBatchUrl(String chatroomId, String usernames) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroomid}", chatroomId);
        replaceMap.put("{usernames}", usernames);
        return generatorUrl(removeMemberFromChatRoomBatchUrl, replaceMap);
    }

    public String getChatRoomAdminMembersUrl(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        return generatorUrl(getChatRoomAdminMembersUrl, replaceMap);
    }

    public String getAddAdminMemberToChatRoomUrl(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        return generatorUrl(addAdminMemberToChatRoomUrl, replaceMap);
    }

    public String getRemoveAdminMemberFromChatRoomUrl(String chatroomId, String oldAdmin) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        replaceMap.put("{oldadmin}", oldAdmin);
        return generatorUrl(removeAdminMemberFromChatRoomUrl, replaceMap);
    }

    public String getChatRoomMuteList(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        return generatorUrl(getChatRoomMuteListUrl, replaceMap);
    }

    public String getSetMemberMuteWithChatRoomUrl(String chatroomId) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        return generatorUrl(setMemberMuteWithChatRoomUrl, replaceMap);
    }

    public String getCancelMemberMuteWithChatRoomUrl(String chatroomId, String usernames) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{chatroom_id}", chatroomId);
        replaceMap.put("{usernames}", usernames);
        return generatorUrl(cancelMemberMuteWithChatRoomUrl, replaceMap);
    }

    public String getChatRoomSuperAdminListUrl(Integer pageNum, Integer pageSize) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{pagenum}", String.valueOf(pageNum));
        replaceMap.put("{pagesize}", String.valueOf(pageSize));
        return generatorUrl(getChatRoomSuperAdminListUrl, replaceMap);
    }

    public String getAddSuperAdminToChatRoomUrl() {
        return generatorUrl(addSuperAdminToChatRoomUrl);
    }

    public String getRemoveSuperAdminFromChatRoomUrl(String superAdmin) {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{superAdmin}", superAdmin);
        return generatorUrl(removeSuperAdminFromChatRoomUrl, replaceMap);
    }

    private final String sendTextMessageUrl = "/{org_name}/{app_name}/messages";

    private final String getChatGroupsUrl = "/{org_name}/{app_name}/chatgroups";
    private final String getJoinedChatGroupsUrl = "/{org_name}/{app_name}/users/{username}/joined_chatgroups";
    private final String getGroupDetailByIdUrl = "/{org_name}/{app_name}/chatgroups/{group_ids}";
    private final String createGroupUrl = "/{org_name}/{app_name}/chatgroups";
    private final String updateGroupUrl = "/{org_name}/{app_name}/chatgroups/{group_id}";
    private final String deleteGroupUrl = "/{org_name}/{app_name}/chatgroups/{group_id}";
    private final String getGroupAnnouncementUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/announcement";
    private final String updateGroupAnnouncementUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/announcement";
    private final String getGroupMembersUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/users?pagenum={pagenum}&pagesize={pagesize}";
    private final String addMemberToGroupUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/users/{username}";
    private final String addMemberToGroupBatchUrl = "/{org_name}/{app_name}/chatgroups/{chatgroupid}/users";
    private final String removeMemberFromGroupUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/users/{username}";
    private final String removeMemberFromGroupBatchUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/users/{memebers}";
    private final String getGroupAdminMembersUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/admin";
    private final String addAdminMemberToGroupUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/admin";
    private final String removeAdminMemberFromGroupUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/admin/{oldadmin}";
    private final String transferGroupUrl = "/{org_name}/{app_name}/chatgroups/{groupid}";
    private final String getGroupBlockListUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users";
    private final String addMemberToGroupBlockListUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users/{username}";
    private final String addMemberToGroupBlockListBatchUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users";
    private final String removeMemberFromGroupBlockListUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users/{username}";
    private final String removeMemberFromGroupBlockListBatchUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users/{usernames}";
    private final String addMemberToGroupMuteListUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/mute";
    private final String removeMemberFromGroupMuteListUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/mute/{usernames}";
    private final String getGroupMuteListUrl = "/{org_name}/{app_name}/chatgroups/{group_id}/mute";

    private final String getAllChatRoomsUrl = "/{org_name}/{app_name}/chatrooms";
    private final String getJoinedChatRoomsByMemberNameUrl = "/{org_name}/{app_name}/users/{username}/joined_chatrooms";
    private final String getChatRoomDetailByIdUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}";
    private final String createChatRoomUrl = "/{org_name}/{app_name}/chatrooms";
    private final String updateChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}";
    private final String deleteChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}";

    private final String getChatRoomMembersUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}/users?pagenum={pagenum}&pagesize={pagesize}";
    private final String addMemberToChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroomid}/users/{username}";
    private final String addMemberToChatRoomBatchUrl = "/{org_name}/{app_name}/chatrooms/{chatroomid}/users";
    private final String removeMemberFromChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroomid}/users/{username}";
    private final String removeMemberFromChatRoomBatchUrl = "/{org_name}/{app_name}/chatrooms/{chatroomid}/users/{usernames}";
    private final String getChatRoomAdminMembersUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}/admin";
    private final String addAdminMemberToChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}/admin";
    private final String removeAdminMemberFromChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}/admin/{oldadmin}";

    private final String getChatRoomMuteListUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}/mute";
    private final String setMemberMuteWithChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}/mute";
    private final String cancelMemberMuteWithChatRoomUrl = "/{org_name}/{app_name}/chatrooms/{chatroom_id}/mute/{usernames}";

    private final String getChatRoomSuperAdminListUrl = "/{org_name}/{app_name}/chatrooms/super_admin?pagenum={pagenum}&pagesize={pagesize}";
    private final String addSuperAdminToChatRoomUrl = "/{org_name}/{app_name}/chatrooms/super_admin";
    private final String removeSuperAdminFromChatRoomUrl = "/{org_name}/{app_name}/chatrooms/super_admin/{superAdmin}";

    private final String generatorUrl(String path) {
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

}
