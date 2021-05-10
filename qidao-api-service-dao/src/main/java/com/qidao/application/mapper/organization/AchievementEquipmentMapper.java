package com.qidao.application.mapper.organization;

import com.qidao.application.entity.organization.AchievementEquipment;
import com.qidao.application.entity.organization.AchievementEquipmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AchievementEquipmentMapper {
    long countByExample(AchievementEquipmentExample example);

    int deleteByExample(AchievementEquipmentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AchievementEquipment record);

    int insertSelective(@Param("record") AchievementEquipment record, @Param("selective") AchievementEquipment.Column ... selective);

    AchievementEquipment selectOneByExample(AchievementEquipmentExample example);

    AchievementEquipment selectOneByExampleWithBLOBs(AchievementEquipmentExample example);

    List<AchievementEquipment> selectByExampleWithBLOBs(AchievementEquipmentExample example);

    List<AchievementEquipment> selectByExample(AchievementEquipmentExample example);

    AchievementEquipment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AchievementEquipment record, @Param("example") AchievementEquipmentExample example, @Param("selective") AchievementEquipment.Column ... selective);

    int updateByExampleWithBLOBs(@Param("record") AchievementEquipment record, @Param("example") AchievementEquipmentExample example);

    int updateByExample(@Param("record") AchievementEquipment record, @Param("example") AchievementEquipmentExample example);

    int updateByPrimaryKeySelective(@Param("record") AchievementEquipment record, @Param("selective") AchievementEquipment.Column ... selective);

    int updateByPrimaryKeyWithBLOBs(AchievementEquipment record);

    int updateByPrimaryKey(AchievementEquipment record);

    int batchInsert(@Param("list") List<AchievementEquipment> list);

    int batchInsertSelective(@Param("list") List<AchievementEquipment> list, @Param("selective") AchievementEquipment.Column ... selective);
}