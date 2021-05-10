package com.qidao.application.mapper.dynamic;

import com.qidao.application.entity.dynamic.Dynamic;
import com.qidao.application.entity.dynamic.DynamicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DynamicMapper {
    long countByExample(DynamicExample example);

    int deleteByExample(DynamicExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Dynamic record);

    int insertSelective(@Param("record") Dynamic record, @Param("selective") Dynamic.Column ... selective);

    Dynamic selectOneByExample(DynamicExample example);

    Dynamic selectOneByExampleWithBLOBs(DynamicExample example);

    List<Dynamic> selectByExampleWithBLOBs(DynamicExample example);

    List<Dynamic> selectByExample(DynamicExample example);

    Dynamic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Dynamic record, @Param("example") DynamicExample example, @Param("selective") Dynamic.Column ... selective);

    int updateByExampleWithBLOBs(@Param("record") Dynamic record, @Param("example") DynamicExample example);

    int updateByExample(@Param("record") Dynamic record, @Param("example") DynamicExample example);

    int updateByPrimaryKeySelective(@Param("record") Dynamic record, @Param("selective") Dynamic.Column ... selective);

    int updateByPrimaryKeyWithBLOBs(Dynamic record);

    int updateByPrimaryKey(Dynamic record);

    int batchInsert(@Param("list") List<Dynamic> list);

    int batchInsertSelective(@Param("list") List<Dynamic> list, @Param("selective") Dynamic.Column ... selective);
}