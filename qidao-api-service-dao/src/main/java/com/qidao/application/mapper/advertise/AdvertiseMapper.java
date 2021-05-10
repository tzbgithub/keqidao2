package com.qidao.application.mapper.advertise;

import com.qidao.application.entity.advertise.Advertise;
import com.qidao.application.entity.advertise.AdvertiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertiseMapper {
    long countByExample(AdvertiseExample example);

    int deleteByExample(AdvertiseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Advertise record);

    int insertSelective(@Param("record") Advertise record, @Param("selective") Advertise.Column ... selective);

    Advertise selectOneByExample(AdvertiseExample example);

    List<Advertise> selectByExample(AdvertiseExample example);

    Advertise selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Advertise record, @Param("example") AdvertiseExample example, @Param("selective") Advertise.Column ... selective);

    int updateByExample(@Param("record") Advertise record, @Param("example") AdvertiseExample example);

    int updateByPrimaryKeySelective(@Param("record") Advertise record, @Param("selective") Advertise.Column ... selective);

    int updateByPrimaryKey(Advertise record);

    int batchInsert(@Param("list") List<Advertise> list);

    int batchInsertSelective(@Param("list") List<Advertise> list, @Param("selective") Advertise.Column ... selective);
}