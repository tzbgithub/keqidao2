package com.qidao.application.mapper.relation;

import com.qidao.application.entity.relation.LinkLabel;
import com.qidao.application.entity.relation.LinkLabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LinkLabelMapper {
    long countByExample(LinkLabelExample example);

    int deleteByExample(LinkLabelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LinkLabel record);

    int insertSelective(@Param("record") LinkLabel record, @Param("selective") LinkLabel.Column ... selective);

    LinkLabel selectOneByExample(LinkLabelExample example);

    List<LinkLabel> selectByExample(LinkLabelExample example);

    LinkLabel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LinkLabel record, @Param("example") LinkLabelExample example, @Param("selective") LinkLabel.Column ... selective);

    int updateByExample(@Param("record") LinkLabel record, @Param("example") LinkLabelExample example);

    int updateByPrimaryKeySelective(@Param("record") LinkLabel record, @Param("selective") LinkLabel.Column ... selective);

    int updateByPrimaryKey(LinkLabel record);

    int batchInsert(@Param("list") List<LinkLabel> list);

    int batchInsertSelective(@Param("list") List<LinkLabel> list, @Param("selective") LinkLabel.Column ... selective);
}