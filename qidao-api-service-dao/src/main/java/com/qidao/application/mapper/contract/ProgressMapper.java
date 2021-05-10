package com.qidao.application.mapper.contract;

import com.qidao.application.entity.contract.Progress;
import com.qidao.application.entity.contract.ProgressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProgressMapper {
    long countByExample(ProgressExample example);

    int deleteByExample(ProgressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Progress record);

    int insertSelective(@Param("record") Progress record, @Param("selective") Progress.Column ... selective);

    Progress selectOneByExample(ProgressExample example);

    List<Progress> selectByExample(ProgressExample example);

    Progress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Progress record, @Param("example") ProgressExample example, @Param("selective") Progress.Column ... selective);

    int updateByExample(@Param("record") Progress record, @Param("example") ProgressExample example);

    int updateByPrimaryKeySelective(@Param("record") Progress record, @Param("selective") Progress.Column ... selective);

    int updateByPrimaryKey(Progress record);

    int batchInsert(@Param("list") List<Progress> list);

    int batchInsertSelective(@Param("list") List<Progress> list, @Param("selective") Progress.Column ... selective);
}