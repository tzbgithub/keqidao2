package com.qidao.application.mapper.dict;

import com.qidao.application.entity.dict.Dict;
import com.qidao.application.entity.dict.DictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictMapper {
    long countByExample(DictExample example);

    int deleteByExample(DictExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(@Param("record") Dict record, @Param("selective") Dict.Column ... selective);

    Dict selectOneByExample(DictExample example);

    List<Dict> selectByExample(DictExample example);

    Dict selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Dict record, @Param("example") DictExample example, @Param("selective") Dict.Column ... selective);

    int updateByExample(@Param("record") Dict record, @Param("example") DictExample example);

    int updateByPrimaryKeySelective(@Param("record") Dict record, @Param("selective") Dict.Column ... selective);

    int updateByPrimaryKey(Dict record);

    int batchInsert(@Param("list") List<Dict> list);

    int batchInsertSelective(@Param("list") List<Dict> list, @Param("selective") Dict.Column ... selective);
}