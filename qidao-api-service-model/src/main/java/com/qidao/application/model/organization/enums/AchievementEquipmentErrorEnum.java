package com.qidao.application.model.organization.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AchievementEquipmentErrorEnum implements ResultEnumInterface {

    INSERT_ACHIEVEMENT_EQUIPMENT_ERROR("LxAPI-000601", "实验室设备添加失败"),
    BATCH_INSERT_LINK_LABEL_ERROR("LxAPI-000602", "标签中间记录批量添加失败"),
    BATCH_INSERT_LINK_SELECT_ERROR("LxAPI-000603", "下拉选择中间记录批量添加失败"),
    ;
    private final String code;
    private final String message;
}
