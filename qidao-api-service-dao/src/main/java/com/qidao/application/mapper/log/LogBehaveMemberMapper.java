package com.qidao.application.mapper.log;

import com.qidao.application.entity.log.LogBehaveMember;
import com.qidao.application.entity.log.LogBehaveMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogBehaveMemberMapper {
    long countByExample(LogBehaveMemberExample example);

    int deleteByExample(LogBehaveMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogBehaveMember record);

    int insertSelective(@Param("record") LogBehaveMember record, @Param("selective") LogBehaveMember.Column ... selective);

    LogBehaveMember selectOneByExample(LogBehaveMemberExample example);

    List<LogBehaveMember> selectByExample(LogBehaveMemberExample example);

    LogBehaveMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogBehaveMember record, @Param("example") LogBehaveMemberExample example, @Param("selective") LogBehaveMember.Column ... selective);

    int updateByExample(@Param("record") LogBehaveMember record, @Param("example") LogBehaveMemberExample example);

    int updateByPrimaryKeySelective(@Param("record") LogBehaveMember record, @Param("selective") LogBehaveMember.Column ... selective);

    int updateByPrimaryKey(LogBehaveMember record);

    int batchInsert(@Param("list") List<LogBehaveMember> list);

    int batchInsertSelective(@Param("list") List<LogBehaveMember> list, @Param("selective") LogBehaveMember.Column ... selective);
}