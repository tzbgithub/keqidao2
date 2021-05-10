package com.qidao.application.service.im;

import com.qidao.application.model.easemob.*;

public interface EasemobChartRoomService {
    /**
     * 管理聊天室
     * 环信提供多个接口完成聊天室相关的集成，包括对聊天室的创建、获取、修改、删除等管理功能。
     *
     * 名称	请求	描述
     * 获取 APP 中所有的聊天室	/{org_name}/{app_name}/chatrooms	获取应用下全部的聊天室信息
     * 获取用户加入的聊天室	/{org_name}/{app_name}/users/{username}/joined_chatrooms	根据用户名称获取此用户加入的全部聊天室
     * 获取聊天室详情	/{org_name}/{app_name}/chatrooms/{chatroom_id}	根据聊天室ID获取此聊天室的详情
     * 创建一个聊天室	/{org_name}/{app_name}/chatrooms	创建一个新聊天室
     * 修改聊天室信息	/{org_name}/{app_name}/chatrooms/{chatroom_id}	修改聊天室的部分信息
     * 删除聊天室	/{org_name}/{app_name}/chatrooms/{chatroom_id}	删除一个聊天室
     */
    /**
     * 获取 APP 中所有的聊天室
     * @return
     */
    EasemobGetAllChatRoomsRes getAllChatRooms();

    /**
     * 获取用户加入的聊天室
     * @return
     */
    EasemobGetJoinedChatRoomsByMemberNameRes getJoinedChatRoomsByMemberName(EasemobGetJoinedChatRoomsByMemberNameReq req);

    /**
     * 获取聊天室详情
     * @return
     */
    EasemobGetChatRoomDetailByIdRes getChatRoomDetailById(EasemobGetChatRoomDetailByIdReq req);

    /**
     * 创建一个聊天室
     * @return
     */
    EasemobCreateChatRoomRes createChatRoom(EasemobCreateChatRoomReq req);

    /**
     * 修改聊天室信息
     * @return
     */
    EasemobUpdateChatRoomRes updateChatRoom(EasemobUpdateChatRoomReq req);

    /**
     * 删除聊天室
     * @return
     */
    EasemobDeleteChatRoomRes deleteChatRoom(EasemobDeleteChatRoomReq req);

    /**
     * 管理聊天室成员
     * 环信提供多个接口实现对聊天室成员的管理，包括添加、移除聊天室成员关系列表等
     *
     * 名称	请求	描述
     * 分页获取聊天室成员	/{org_name}/{app_name}/chatrooms/{chatroom_id}/users	分页获取一个聊天室的成员列表
     * 添加单个聊天室成员	/{org_name}/{app_name}/chatrooms/{chatroomid}/users/{username}	添加用户至聊天室成员列表
     * 批量添加聊天室成员	/{org_name}/{app_name}/chatrooms/{chatroomid}/users	批量添加用户至聊天室成员列表
     * 删除单个聊天室成员	/{org_name}/{app_name}/chatrooms/{chatroomid}/users/{username}	从聊天室成员列表中删除用户
     * 批量删除聊天室成员	/{org_name}/{app_name}/chatrooms/{chatroomid}/users/{usernames}	从聊天室成员列表中批量删除用户
     * 获取聊天室管理员列表	/{org_name}/{app_name}/chatrooms/{chatroom_id}/admin	获取聊天室管理员列表
     * 添加聊天室管理员	/{org_name}/{app_name}/chatrooms/{chatroom_id}/admin	添加用户至聊天室管理员列表
     * 移除聊天室管理员	/{org_name}/{app_name}/chatrooms/{chatroom_id}/admin/{oldadmin}	从聊天室管理员列表中移除用户
     */
    /**
     * 分页获取聊天室成员
     * @return
     */
    EasemobGetChatRoomMembersRes getChatRoomMembers(EasemobGetChatRoomMembersReq req);

    /**
     * 添加单个聊天室成员
     * @return
     */
    EasemobAddMemberToChatRoomRes addMemberToChatRoom(EasemobAddMemberToChatRoomReq req);

    /**
     * 批量添加聊天室成员
     * @return
     */
    EasemobAddMemberToChatRoomBatchRes addMemberToChatRoomBatch(EasemobAddMemberToChatRoomBatchReq req);

    /**
     * 删除单个聊天室成员
     * @return
     */
    EasemobRemoveMemberFromChatRoomRes removeMemberFromChatRoom(EasemobRemoveMemberFromChatRoomReq req);

    /**
     * 批量删除聊天室成员
     * @return
     */
    EasemobRemoveMemberFromChatRoomBatchRes removeMemberFromChatRoomBatch(EasemobRemoveMemberFromChatRoomBatchReq req);

    /**
     * 获取聊天室管理员列表
     * @return
     */
    EasemobGetChatRoomAdminMembersRes getChatRoomAdminMembers(EasemobGetChatRoomAdminMembersReq req);

    /**
     * 添加聊天室管理员
     * @return
     */
    EasemobAddAdminMemberToChatRoomRes addAdminMemberToChatRoom(EasemobAddAdminMemberToChatRoomReq req);

    /**
     * 移除聊天室管理员
     * @return
     */
    EasemobRemoveAdminMemberFromChatRoomRes removeAdminMemberFromChatRoom(EasemobRemoveAdminMemberFromChatRoomReq req);

    /**
     * 管理禁言
     * 环信提供多个管理聊天室禁言列表的接口，包括获取、将用户添加、移除禁言列表等
     *
     * 名称	请求	描述
     * 获取禁言列表	/{org_name}/{app_name}/chatrooms/{chatroom_id}/mute	获取聊天室的禁言列表
     * 添加禁言	/{org_name}/{app_name}/chatrooms/{chatroom_id}/mute	添加用户至聊天室的禁言列表
     * 移除禁言	/{org_name}/{app_name}/chatrooms/{chatroom_id}/mute/{member1}(,{member2},…)	从聊天室的禁言列表中移除用户
     */
    /**
     * 获取禁言列表
     * @return
     */
    EasemobGetChatRoomMuteListRes getChatRoomMuteList(EasemobGetChatRoomMuteListReq req);

    /**
     * 添加禁言
     * @return
     */
    EasemobSetMemberMuteWithChatRoomRes setMemberMuteWithChatRoom(EasemobSetMemberMuteWithChatRoomReq req);

    /**
     * 移除禁言
     * @return
     */
    EasemobCancelMemberMuteWithChatRoomRes cancelMemberMuteWithChatRoom(EasemobCancelMemberMuteWithChatRoomReq req);
    /**
     * 管理超级管理员
     * 环信提供多个管理聊天室超级管理员的接口，包括获取、添加、移除等。超级管理员身份给予了普通用户创建聊天室的权限，普通用户默认没有权限创建聊天室。
     *
     * 名称	请求	描述
     * 获取超级管理员列表	/{org_name}/{app_name}/chatrooms/super_admin	获取超级管理员列表
     * 添加超级管理员	/{org_name}/{app_name}/chatrooms/super_admin	添加用户至超级管理员列表
     * 移除超级管理员	/{org_name}/{app_name}/chatrooms/super_admin/{superAdmin}	从超级管理员列表列表中移除用户
     */
    /**
     * 获取超级管理员列表
     * @return
     */
    EasemobGetChatRoomSuperAdminListRes getChatRoomSuperAdminList(EasemobGetChatRoomSuperAdminListReq req);

    /**
     * 添加超级管理员
     * @return
     */
    EasemobAddSuperAdminToChatRoomRes addSuperAdminToChatRoom(EasemobAddSuperAdminToChatRoomReq req);

    /**
     * 移除超级管理员
     * @return
     */
    EasemobRemoveSuperAdminFromChatRoomRes removeSuperAdminFromChatRoom(EasemobRemoveSuperAdminFromChatRoomReq req);
}
