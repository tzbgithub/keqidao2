package com.qidao.application.mapper.msg;

import com.qidao.application.entity.msg.MsgRecord;
import com.qidao.application.entity.msg.MsgRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgRecordMapper {
    long countByExample(MsgRecordExample example);

    int deleteByExample(MsgRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsgRecord record);

    int insertSelective(@Param("record") MsgRecord record, @Param("selective") MsgRecord.Column ... selective);

    MsgRecord selectOneByExample(MsgRecordExample example);

    List<MsgRecord> selectByExample(MsgRecordExample example);

    MsgRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MsgRecord record, @Param("example") MsgRecordExample example, @Param("selective") MsgRecord.Column ... selective);

    int updateByExample(@Param("record") MsgRecord record, @Param("example") MsgRecordExample example);

    int updateByPrimaryKeySelective(@Param("record") MsgRecord record, @Param("selective") MsgRecord.Column ... selective);

    int updateByPrimaryKey(MsgRecord record);

    int batchInsert(@Param("list") List<MsgRecord> list);

    int batchInsertSelective(@Param("list") List<MsgRecord> list, @Param("selective") MsgRecord.Column ... selective);
}