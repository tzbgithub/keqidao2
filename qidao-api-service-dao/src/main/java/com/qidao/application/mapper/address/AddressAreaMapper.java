package com.qidao.application.mapper.address;

import com.qidao.application.entity.address.AddressArea;
import com.qidao.application.entity.address.AddressAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AddressAreaMapper {
    long countByExample(AddressAreaExample example);

    int deleteByExample(AddressAreaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AddressArea record);

    int insertSelective(@Param("record") AddressArea record, @Param("selective") AddressArea.Column ... selective);

    AddressArea selectOneByExample(AddressAreaExample example);

    List<AddressArea> selectByExample(AddressAreaExample example);

    AddressArea selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AddressArea record, @Param("example") AddressAreaExample example, @Param("selective") AddressArea.Column ... selective);

    int updateByExample(@Param("record") AddressArea record, @Param("example") AddressAreaExample example);

    int updateByPrimaryKeySelective(@Param("record") AddressArea record, @Param("selective") AddressArea.Column ... selective);

    int updateByPrimaryKey(AddressArea record);

    int batchInsert(@Param("list") List<AddressArea> list);

    int batchInsertSelective(@Param("list") List<AddressArea> list, @Param("selective") AddressArea.Column ... selective);
}