package com.qidao.application.mapper.log;

import com.qidao.application.entity.log.LogChatMsg;
import com.qidao.application.entity.log.LogChatMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogChatMsgMapper {
    long countByExample(LogChatMsgExample example);

    int deleteByExample(LogChatMsgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogChatMsg record);

    int insertSelective(@Param("record") LogChatMsg record, @Param("selective") LogChatMsg.Column ... selective);

    LogChatMsg selectOneByExample(LogChatMsgExample example);

    List<LogChatMsg> selectByExample(LogChatMsgExample example);

    LogChatMsg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogChatMsg record, @Param("example") LogChatMsgExample example, @Param("selective") LogChatMsg.Column ... selective);

    int updateByExample(@Param("record") LogChatMsg record, @Param("example") LogChatMsgExample example);

    int updateByPrimaryKeySelective(@Param("record") LogChatMsg record, @Param("selective") LogChatMsg.Column ... selective);

    int updateByPrimaryKey(LogChatMsg record);

    int batchInsert(@Param("list") List<LogChatMsg> list);

    int batchInsertSelective(@Param("list") List<LogChatMsg> list, @Param("selective") LogChatMsg.Column ... selective);
}