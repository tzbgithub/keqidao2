package com.qidao.application.mapper.config;

import com.qidao.application.entity.config.Canal;
import com.qidao.application.entity.config.CanalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CanalMapper {
    long countByExample(CanalExample example);

    int deleteByExample(CanalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Canal record);

    int insertSelective(@Param("record") Canal record, @Param("selective") Canal.Column ... selective);

    Canal selectOneByExample(CanalExample example);

    List<Canal> selectByExample(CanalExample example);

    Canal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Canal record, @Param("example") CanalExample example, @Param("selective") Canal.Column ... selective);

    int updateByExample(@Param("record") Canal record, @Param("example") CanalExample example);

    int updateByPrimaryKeySelective(@Param("record") Canal record, @Param("selective") Canal.Column ... selective);

    int updateByPrimaryKey(Canal record);

    int batchInsert(@Param("list") List<Canal> list);

    int batchInsertSelective(@Param("list") List<Canal> list, @Param("selective") Canal.Column ... selective);
}