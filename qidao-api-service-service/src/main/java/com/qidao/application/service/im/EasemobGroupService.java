package com.qidao.application.service.im;

import com.qidao.application.model.easemob.*;

public interface EasemobGroupService {
    /**
     * 管理群组
     * 获取App中所有的群组（可分页）	/{org_name}/{app_name}/chatgroups	获取应用下全部的群组信息
     * 获取一个用户参与的所有群组	/{app_name}/users/{username}/joined_chatgroups	根据用户名称获取此用户加入的全部群组
     * 获取群组详情	/{org_name}/{app_name}/chatgroups/{group_ids}	根据群组ID获取此群组的详情
     * 创建一个群组	/{org_name}/{app_name}/chatgroups	创建一个新群组
     * 修改群组信息	/{org_name}/{app_name}/chatgroups/{group_id}	修改群组的部分信息
     * 删除群组	/{org_name}/{app_name}/chatgroups/{group_id}	删除一个群组
     */
    /**
     * 获取应用下全部的群组信息
     * @return
     */
    EasemobGetChatGroupsRes getChatGroups(EasemobGetChatGroupsReq req);
    /**
     * 获取一个用户参与的所有群组
     */
    EasemobGetJoinedChatGroupsRes getJoinedChatGroups(EasemobGetJoinedChatGroupsReq req);

    /**
     * 根据群组ID获取此群组的详情
     * @return
     */
    EasemobGetGroupDetailByIdRes getGroupDetailById(EasemobGetGroupDetailByIdReq req);

    /**
     * 创建一个新群组
     * @return
     */
    EasemobCreateGroupRes createGroup(EasemobCreateGroupReq req);

    /**
     * 修改群组的部分信息
     * @return
     */
    EasemobUpdateGroupRes updateGroup(EasemobUpdateGroupReq req);

    /**
     * 删除一个群组
     * @return
     */
    EasemobDeleteGroupRes deleteGroup(EasemobDeleteGroupReq req);
    /**
     * 获取群组公告
     */
    EasemobGetGroupAnnouncementRes getGroupAnnouncement(EasemobGetGroupAnnouncementReq req);
    /**
     * 修改群组公告
     */
    EasemobUpdateGroupAnnouncementRes updateGroupAnnouncement(EasemobUpdateGroupAnnouncementReq req);
    /**
     * 获取群组共享文件
     */
    Object getGroupShareFiles();
    /**
     * 上传群组共享文件
     */
    Object uploadGroupShareFile();
    /**
     * 下载群组共享文件
     */
    Object downloadGroupShareFile();
    /**
     * 删除群组共享文件
     */
    Object deleteGroupShareFile();

    /**
     * 管理群组成员
     * 环信提供多个接口实现对群组成员的管理，包括添加、移除群组成员关系列表，转让群主等
     *
     * 名称	请求	描述
     * 分页获取群组成员	/{org_name}/{app_name}/chatgroups/{group_id}/users	分页获取一个群组的群成员列表
     * 添加单个群组成员	/{org_name}/{app_name}/chatgroups/{group_id}/users/{username}	添加用户至群组成员列表
     * 批量添加群组成员	/{org_name}/{app_name}/chatgroups/{chatgroupid}/users	批量添加用户至群组成员列表
     * 移除单个群组成员	/{org_name}/{app_name}/chatgroups/{group_id}/users/{username}	从群组成员列表中移除用户
     * 批量移除群组成员	/{org_name}/{app_name}/chatgroups/{group_id}/users/{usernames}	从群组成员列表中批量移除用户
     * 获取群管理员列表	/{org_name}/{app_name}/chatgroups/{group_id}/admin	获取群组管理员列表
     * 添加群管理员	/{org_name}/{app_name}/chatgroups/{group_id}/admin	添加用户至群组管理员列表
     * 移除群管理员	/{org_name}/{app_name}/chatgroups/{group_id}/admin/{oldadmin}	从群组管理员列表中移除用户
     * 转让群组	/{org_name}/{app_name}/chatgroups/{groupid}	转让群组owner
     */
    /**
     * 分页获取群组成员
     * @return
     */
    EasemobGetGroupMembersRes getGroupMembers(EasemobGetGroupMembersReq req);

    /**
     * 添加单个群组成员
     * @return
     */
    EasemobAddMemberToGroupRes addMemberToGroup(EasemobAddMemberToGroupReq req);

    /**
     * 批量添加群组成员
     * @return
     */
    EasemobAddMemberToGroupBatchRes addMemberToGroupBatch(EasemobAddMemberToGroupBatchReq req);

    /**
     * 移除单个群组成员
     * @return
     */
    EasemobRemoveMemberFromGroupRes removeMemberFromGroup(EasemobRemoveMemberFromGroupReq req);

    /**
     * 批量移除群组成员
     * @return
     */
    EasemobRemoveMemberFromGroupBatchRes removeMemberFromGroupBatch(EasemobRemoveMemberFromGroupBatchReq req);

    /**
     * 获取群管理员列表
     */
    EasemobGetGroupAdminMembersRes getGroupAdminMembers(EasemobGetGroupAdminMembersReq req);

    /**
     * 添加群管理员
     * @return
     */
    EasemobAddAdminMemberToGroupRes addAdminMemberToGroup(EasemobAddAdminMemberToGroupReq req);

    /**
     * 移除群管理员
     * @return
     */
    EasemobRemoveAdminMemberFromGroupRes removeAdminMemberFromGroup(EasemobRemoveAdminMemberFromGroupReq req);

    /**
     * 转让群组
     * @return
     */
    EasemobTransferGroupRes transferGroup(EasemobTransferGroupReq req);

    /**
     * 管理黑名单
     * 环信提供多个接口完成群组黑名单管理，包括查看、将用户添加、移除黑名单等。
     *
     * 查询群组黑名单	/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users	查看群组的黑名单列表
     * 添加单个用户至群组黑名单	/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users/{username}	将用户添加至群组的黑名单列表
     * 批量添加用户至群组黑名单	/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users	将用户批量添加至群组的黑名单列表
     * 从群组黑名单移除单个用户	/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users/{username}	将用户从黑名单列表中移除
     * 批量从群组黑名单移除用户	/{org_name}/{app_name}/chatgroups/{group_id}/blocks/users/{usernames}	批量将用户从黑名单列表中移除
     */
    /**
     * 查询群组黑名单
     * @return
     */
    EasemobGetGroupBlockListRes getGroupBlockList(EasemobGetGroupBlockListReq req);

    /**
     * 添加单个用户至群组黑名单
     * @return
     */
    EasemobAddMemberToGroupBlockListRes addMemberToGroupBlockList(EasemobAddMemberToGroupBlockListReq req);

    /**
     * 批量添加用户至群组黑名单
     * @return
     */
    EasemobAddMemberToGroupBlockListBatchRes addMemberToGroupBlockListBatch(EasemobAddMemberToGroupBlockListBatchReq req);

    /**
     * 从群组黑名单移除单个用户
     * @return
     */
    EasemobRemoveMemberFromGroupBlockListRes removeMemberFromGroupBlockList(EasemobRemoveMemberFromGroupBlockListReq req);

    /**
     * 批量从群组黑名单移除用户
     * @return
     */
    EasemobRemoveMemberFromGroupBlockListBatchRes removeMemberFromGroupBlockListBatch(EasemobRemoveMemberFromGroupBlockListBatchReq req);
    /**
     * 管理禁言
     * 环信提供多个接口群组禁言列表的管理，包括查看、将用户添加、移除禁言列表等。
     *
     * 名称	请求	描述
     * 添加禁言	/{org_name}/{app_name}/chatgroups/{group_id}/mute	添加用户至群组的禁言列表
     * 移除禁言	/{org_name}/{app_name}/chatgroups/{group_id}/mute/{member1}(,{member2},…)	从群组的禁言列表中移除用户
     * 获取禁言列表	/{org_name}/{app_name}/chatgroups/{group_id}/mute	获取群组的禁言列表
     */

    /**
     * 添加禁言
     * @return
     */
    EasemobAddMemberToGroupMuteListRes addMemberToGroupMuteList(EasemobAddMemberToGroupMuteListReq req);

    /**
     * 移除禁言
     * @return
     */
    EasemobRemoveMemberFromGroupMuteListRes removeMemberFromGroupMuteList(EasemobRemoveMemberFromGroupMuteListReq req);

    /**
     * 获取禁言列表
     * @return
     */
    EasemobGetGroupMuteListRes getGroupMuteList(EasemobGetGroupMuteListReq req);
}
