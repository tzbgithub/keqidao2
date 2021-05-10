package com.qidao.application.service.im;

import com.qidao.application.model.easemob.*;

import java.util.List;

/**
 * 环境：用户
 */
public interface EasemobUserService {

    /**
     * 注册单个用户(开放)
     * 与(授权)不同的是，不需移除好友要携带IM的token
     * post
     * /{org_name}/{app_name}/users
     * @return
     */
    EasemobBaseDTO registerNoToken(EasemobRegisterNoTokenReq req);

    /**
     * 注册单个用户(授权)
     * 批量注册用户
     * post
     * /{org_name}/{app_name}/users
     * @return
     */
    EasemobBaseDTO register(EasemobRegisterReq req);


    /**
     * 批量注册用户
     * @return
     */
    List<EasemobBaseDTO> registerBatch(EasemobRegisterBatchReq req);

    /**
     * 获取单个用户
     * get
     * 	/{org_name}/{app_name}/users/{username}
     * @return
     */
    EasemobBaseDTO getUser(String username);

    /**
     * 批量获取用户
     * get
     * /{org_name}/{app_name}/users
     * @return
     */
    List<EasemobBaseDTO> getUserBatch(EasemobGetUserBatchReq req);

    /**
     * 删除单个用户
     * delete
     * /{org_name}/{app_name}/users/{username}
     * @return
     */
    EasemobBaseDTO deleteUser(String username);

    /**
     * 批量删除用户
     * delete
     * /{org_name}/{app_name}/users
     * @return
     */
    Object deleteUserBatch();

    /**
     * 修改用户密码
     * put
     * /{org_name}/{app_name}/users/{username}/password
     * @return
     */
    EasemobUpdatePwdDTO updatePwd(EasemobUpdatePwdReq req);

    /**
     * 设置推送昵称
     * 	/{org_name}/{app_name}/users/{username}
     * @return
     */
    EasemobBaseDTO updateNickName(EasemobUpdateNicknameReq req);

    /**
     * 设置推送消息展示方式
     * /{org_name}/{app_name}/users/{username}
     * @return
     */
    EasemobBaseDTO notifyShowType(EasemobNotifyShowTypeReq req);

    /**
     * 设置免打扰
     * @return
     */
    EasemobBaseDTO setNotDisturb(EasemobSetNotDisturbReq req);

    /**
     * 添加好友	/{org_name}/{app_name}/users/{owner_username}/contacts/users/{friend_username}	添加为好友
     * @return
     */
    EasemobFriendRes addFriend(EasemobAddFriendReq req);

    /**
     * 添加好友<br>
     * 如果好友在科企岛系统中<strong>不存在</strong>，会返回错误
     * 如果好友在科企岛系统中<strong>存在</strong>，在环信不存在，会注册后添加好友
     *
     * @return 好友基本信息 {@link EasemobFriendRes}
     */
    EasemobFriendRes registerAndAddFriend(EasemobAddFriendReq req);

    /**
     * 移除好友	/{org_name}/{app_name}/users/{owner_username}/contacts/users/{friend_username}	移除好友列表中的用户
     * @return
     */
    EasemobBaseDTO removeFriend(EasemobRemoveFriendReq req);

    /**
     * 获取好友列表	/{org_name}/{app_name}/users/{owner_username}/contacts/users	获取好友列表以及信息
     */
    List<EasemobFriendRes> getFriendList(Long memberId);
    /**
     * 获取黑名单	/{org_name}/{app_name}/users/{owner_username}/blocks/users	获取黑名单以及信息
     */
    /**
     * 添加黑名单	/{org_name}/{app_name}/users/{owner_username}/blocks/users	拉黑用户，添加至黑名单
     */
    /**
     * 移除黑名单	/{org_name}/{app_name}/users/{owner_username}/blocks/users/{blocked_username}	除黑名单中的用户
     */
    /**
     * 获取用户在线状态<br>
     * @param username 环信ID
     * @return {@link EasemobStatusRes}
     */
    EasemobStatusRes statusOne(String username);

    /**
     * 批量获取用户在线状态	/{org_name}/{app_name}/users/batch/status	批量查看用户的在线状态
     */
    List<EasemobStatusRes> statusBatch(EasemobStatusBatchReq req);

    /**
     * 获取离线消息数	/{org_name}/{app_name}/users/{owner_username}/offline_msg_count	获取一个 IM 用户的离线消息数
     */
    List<EasemobOfflineCountRes> getOfflineMsgCount(String username);

    /**
     * 获取离线消息的状态	/{org_name}/{app_name}/users/{username}/offline_msg_status/{msg_id}	通过离线消息的 ID 查看用户的该条离线消息状态
     */
    Object getOfflineMsgStatus(EasemobUserOffLineMsgStatusReq req);
    /**
     * 用户账号禁用	/{org_name}/{app_name}/users/{username}/deactivate	禁用用户的登录账号，必须通过解禁才能恢复
     */
    /**
     * 用户账号解禁	/{org_name}/{app_name}/users/{username}/activate	解禁后用户恢复正常使用
     */
    /**
     * 强制下线
     * 强制 IM 用户状态改为离线，用户需要重新登录才能正常使用。
     *
     * HTTP Request
     * 	/{org_name}/{app_name}/users/{username}/disconnect
     */
    Object disconnect(String username);
}
