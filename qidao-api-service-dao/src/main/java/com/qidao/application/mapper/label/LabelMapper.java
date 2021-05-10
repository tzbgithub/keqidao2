package com.qidao.application.mapper.label;

import com.qidao.application.entity.label.Label;
import com.qidao.application.entity.label.LabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LabelMapper {
    long countByExample(LabelExample example);

    int deleteByExample(LabelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Label record);

    int insertSelective(@Param("record") Label record, @Param("selective") Label.Column ... selective);

    Label selectOneByExample(LabelExample example);

    List<Label> selectByExample(LabelExample example);

    Label selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Label record, @Param("example") LabelExample example, @Param("selective") Label.Column ... selective);

    int updateByExample(@Param("record") Label record, @Param("example") LabelExample example);

    int updateByPrimaryKeySelective(@Param("record") Label record, @Param("selective") Label.Column ... selective);

    int updateByPrimaryKey(Label record);

    int batchInsert(@Param("list") List<Label> list);

    int batchInsertSelective(@Param("list") List<Label> list, @Param("selective") Label.Column ... selective);
}