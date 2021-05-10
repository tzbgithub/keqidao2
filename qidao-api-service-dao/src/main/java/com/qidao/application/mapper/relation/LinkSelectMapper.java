package com.qidao.application.mapper.relation;

import com.qidao.application.entity.relation.LinkSelect;
import com.qidao.application.entity.relation.LinkSelectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LinkSelectMapper {
    long countByExample(LinkSelectExample example);

    int deleteByExample(LinkSelectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LinkSelect record);

    int insertSelective(@Param("record") LinkSelect record, @Param("selective") LinkSelect.Column ... selective);

    LinkSelect selectOneByExample(LinkSelectExample example);

    List<LinkSelect> selectByExample(LinkSelectExample example);

    LinkSelect selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LinkSelect record, @Param("example") LinkSelectExample example, @Param("selective") LinkSelect.Column ... selective);

    int updateByExample(@Param("record") LinkSelect record, @Param("example") LinkSelectExample example);

    int updateByPrimaryKeySelective(@Param("record") LinkSelect record, @Param("selective") LinkSelect.Column ... selective);

    int updateByPrimaryKey(LinkSelect record);

    int batchInsert(@Param("list") List<LinkSelect> list);

    int batchInsertSelective(@Param("list") List<LinkSelect> list, @Param("selective") LinkSelect.Column ... selective);
}