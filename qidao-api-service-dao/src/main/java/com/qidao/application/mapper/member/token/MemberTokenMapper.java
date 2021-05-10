package com.qidao.application.mapper.member.token;

import com.qidao.application.entity.member.token.MemberToken;
import com.qidao.application.entity.member.token.MemberTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberTokenMapper {
    long countByExample(MemberTokenExample example);

    int deleteByExample(MemberTokenExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemberToken record);

    int insertSelective(@Param("record") MemberToken record, @Param("selective") MemberToken.Column ... selective);

    MemberToken selectOneByExample(MemberTokenExample example);

    List<MemberToken> selectByExample(MemberTokenExample example);

    MemberToken selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MemberToken record, @Param("example") MemberTokenExample example, @Param("selective") MemberToken.Column ... selective);

    int updateByExample(@Param("record") MemberToken record, @Param("example") MemberTokenExample example);

    int updateByPrimaryKeySelective(@Param("record") MemberToken record, @Param("selective") MemberToken.Column ... selective);

    int updateByPrimaryKey(MemberToken record);

    int batchInsert(@Param("list") List<MemberToken> list);

    int batchInsertSelective(@Param("list") List<MemberToken> list, @Param("selective") MemberToken.Column ... selective);
}