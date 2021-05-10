package com.qidao.application.mapper.config;

import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SelectConfigMapper {
    long countByExample(SelectConfigExample example);

    int deleteByExample(SelectConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SelectConfig record);

    int insertSelective(@Param("record") SelectConfig record, @Param("selective") SelectConfig.Column ... selective);

    SelectConfig selectOneByExample(SelectConfigExample example);

    Long getConfigId(@Param("reasonId") Integer reasonId);

    List<SelectConfig> selectByExample(SelectConfigExample example);

    SelectConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SelectConfig record, @Param("example") SelectConfigExample example, @Param("selective") SelectConfig.Column ... selective);

    int updateByExample(@Param("record") SelectConfig record, @Param("example") SelectConfigExample example);

    int updateByPrimaryKeySelective(@Param("record") SelectConfig record, @Param("selective") SelectConfig.Column ... selective);

    int updateByPrimaryKey(SelectConfig record);

    int batchInsert(@Param("list") List<SelectConfig> list);

    int batchInsertSelective(@Param("list") List<SelectConfig> list, @Param("selective") SelectConfig.Column ... selective);
}