package com.qidao.application.mapper.address;

import com.qidao.application.entity.address.AddressCity;
import com.qidao.application.entity.address.AddressCityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AddressCityMapper {
    long countByExample(AddressCityExample example);

    int deleteByExample(AddressCityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AddressCity record);

    int insertSelective(@Param("record") AddressCity record, @Param("selective") AddressCity.Column ... selective);

    AddressCity selectOneByExample(AddressCityExample example);

    List<AddressCity> selectByExample(AddressCityExample example);

    AddressCity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AddressCity record, @Param("example") AddressCityExample example, @Param("selective") AddressCity.Column ... selective);

    int updateByExample(@Param("record") AddressCity record, @Param("example") AddressCityExample example);

    int updateByPrimaryKeySelective(@Param("record") AddressCity record, @Param("selective") AddressCity.Column ... selective);

    int updateByPrimaryKey(AddressCity record);

    int batchInsert(@Param("list") List<AddressCity> list);

    int batchInsertSelective(@Param("list") List<AddressCity> list, @Param("selective") AddressCity.Column ... selective);
}