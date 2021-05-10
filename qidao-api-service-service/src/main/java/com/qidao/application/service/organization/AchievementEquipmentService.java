package com.qidao.application.service.organization;


import com.github.pagehelper.PageInfo;
import com.qidao.application.model.dto.AchievementEquipmentDto;
import com.qidao.application.vo.AchievementListDto;
import com.qidao.framework.web.ResponseResult;

public interface AchievementEquipmentService {
    /**
     * 添加实验室设备 / 成果
     * @param achievementEquipment
     * @return
     */
    String save(AchievementEquipmentDto achievementEquipment);


    /**
     * 逻辑删除实验室设备 / 成果
     */
    String updateFlag(Long id);

    /**
     * 查询组织机构设备成果列表
     * @param pageNum
     * @param pageSize
     * @param organizatioId
     * @return
     */
     PageInfo<AchievementListDto> findAchievementPage(Integer pageNum, Integer pageSize, Long organizatioId,Long type);


    /**
     * 查询设备/成果详情
     */
    AchievementListDto  findAchievementDetail(Long id);
}
