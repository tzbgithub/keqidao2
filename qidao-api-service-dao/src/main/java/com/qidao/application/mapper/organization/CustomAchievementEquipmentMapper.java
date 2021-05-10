package com.qidao.application.mapper.organization;

import com.github.pagehelper.Page;
import com.qidao.application.common.Query;
import com.qidao.application.entity.organization.AchievementEquipment;
import com.qidao.application.vo.AchievementListDto;
import com.qidao.application.vo.AchievementListReq;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CustomAchievementEquipmentMapper {
    public Page<AchievementListReq> findAchievementPage(Query query);
}