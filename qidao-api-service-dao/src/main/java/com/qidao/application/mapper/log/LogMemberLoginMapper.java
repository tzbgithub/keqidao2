package com.qidao.application.mapper.log;

import com.qidao.application.entity.log.LogMemberLogin;
import com.qidao.application.entity.log.LogMemberLoginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogMemberLoginMapper {
    long countByExample(LogMemberLoginExample example);

    int deleteByExample(LogMemberLoginExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogMemberLogin record);

    int insertSelective(@Param("record") LogMemberLogin record, @Param("selective") LogMemberLogin.Column ... selective);

    LogMemberLogin selectOneByExample(LogMemberLoginExample example);

    List<LogMemberLogin> selectByExample(LogMemberLoginExample example);

    LogMemberLogin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogMemberLogin record, @Param("example") LogMemberLoginExample example, @Param("selective") LogMemberLogin.Column ... selective);

    int updateByExample(@Param("record") LogMemberLogin record, @Param("example") LogMemberLoginExample example);

    int updateByPrimaryKeySelective(@Param("record") LogMemberLogin record, @Param("selective") LogMemberLogin.Column ... selective);

    int updateByPrimaryKey(LogMemberLogin record);

    int batchInsert(@Param("list") List<LogMemberLogin> list);

    int batchInsertSelective(@Param("list") List<LogMemberLogin> list, @Param("selective") LogMemberLogin.Column ... selective);
}