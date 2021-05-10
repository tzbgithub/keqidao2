package com.qidao.application.mapper.log;

import com.qidao.application.entity.log.LogError;
import com.qidao.application.entity.log.LogErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogErrorMapper {
    long countByExample(LogErrorExample example);

    int deleteByExample(LogErrorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogError record);

    int insertSelective(@Param("record") LogError record, @Param("selective") LogError.Column ... selective);

    LogError selectOneByExample(LogErrorExample example);

    LogError selectOneByExampleWithBLOBs(LogErrorExample example);

    List<LogError> selectByExampleWithBLOBs(LogErrorExample example);

    List<LogError> selectByExample(LogErrorExample example);

    LogError selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogError record, @Param("example") LogErrorExample example, @Param("selective") LogError.Column ... selective);

    int updateByExampleWithBLOBs(@Param("record") LogError record, @Param("example") LogErrorExample example);

    int updateByExample(@Param("record") LogError record, @Param("example") LogErrorExample example);

    int updateByPrimaryKeySelective(@Param("record") LogError record, @Param("selective") LogError.Column ... selective);

    int updateByPrimaryKeyWithBLOBs(LogError record);

    int updateByPrimaryKey(LogError record);

    int batchInsert(@Param("list") List<LogError> list);

    int batchInsertSelective(@Param("list") List<LogError> list, @Param("selective") LogError.Column ... selective);
}