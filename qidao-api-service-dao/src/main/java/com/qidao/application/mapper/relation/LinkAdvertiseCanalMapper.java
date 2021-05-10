package com.qidao.application.mapper.relation;

import com.qidao.application.entity.relation.LinkAdvertiseCanal;
import com.qidao.application.entity.relation.LinkAdvertiseCanalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LinkAdvertiseCanalMapper {
    long countByExample(LinkAdvertiseCanalExample example);

    int deleteByExample(LinkAdvertiseCanalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LinkAdvertiseCanal record);

    int insertSelective(@Param("record") LinkAdvertiseCanal record, @Param("selective") LinkAdvertiseCanal.Column ... selective);

    LinkAdvertiseCanal selectOneByExample(LinkAdvertiseCanalExample example);

    List<LinkAdvertiseCanal> selectByExample(LinkAdvertiseCanalExample example);

    LinkAdvertiseCanal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LinkAdvertiseCanal record, @Param("example") LinkAdvertiseCanalExample example, @Param("selective") LinkAdvertiseCanal.Column ... selective);

    int updateByExample(@Param("record") LinkAdvertiseCanal record, @Param("example") LinkAdvertiseCanalExample example);

    int updateByPrimaryKeySelective(@Param("record") LinkAdvertiseCanal record, @Param("selective") LinkAdvertiseCanal.Column ... selective);

    int updateByPrimaryKey(LinkAdvertiseCanal record);

    int batchInsert(@Param("list") List<LinkAdvertiseCanal> list);

    int batchInsertSelective(@Param("list") List<LinkAdvertiseCanal> list, @Param("selective") LinkAdvertiseCanal.Column ... selective);
}