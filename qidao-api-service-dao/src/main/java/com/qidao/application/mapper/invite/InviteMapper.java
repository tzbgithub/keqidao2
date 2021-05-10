package com.qidao.application.mapper.invite;

import com.qidao.application.entity.invite.Invite;
import com.qidao.application.entity.invite.InviteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InviteMapper {
    long countByExample(InviteExample example);

    int deleteByExample(InviteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Invite record);

    int insertSelective(@Param("record") Invite record, @Param("selective") Invite.Column ... selective);

    Invite selectOneByExample(InviteExample example);

    List<Invite> selectByExample(InviteExample example);

    Invite selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Invite record, @Param("example") InviteExample example, @Param("selective") Invite.Column ... selective);

    int updateByExample(@Param("record") Invite record, @Param("example") InviteExample example);

    int updateByPrimaryKeySelective(@Param("record") Invite record, @Param("selective") Invite.Column ... selective);

    int updateByPrimaryKey(Invite record);

    int batchInsert(@Param("list") List<Invite> list);

    int batchInsertSelective(@Param("list") List<Invite> list, @Param("selective") Invite.Column ... selective);
}