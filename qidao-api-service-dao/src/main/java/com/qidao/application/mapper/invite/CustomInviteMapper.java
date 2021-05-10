package com.qidao.application.mapper.invite;

import com.qidao.application.entity.invite.InvitedMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomInviteMapper {

    List<InvitedMember> findInvitedMember(Long id);

    List<InvitedMember> invitedAssistantTeacher(Long id);

    List<InvitedMember> listAssistantInvitedTeacher(Long assistantId);

    /**
     * 根据 invitePhone 修改 邀请状态 为 1-已登录APP绑定邀请人
     * <p>
     * 修改条件    and expire_time > now()
     * and del_flag = 0
     * and status = 0
     *
     * @param invitePhone
     * @return
     */
    int updateInviteStatusByInvitePhone(String invitePhone);

}
