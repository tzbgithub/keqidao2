package com.qidao.application.model.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 技术成熟度枚举
 */
@AllArgsConstructor
@Getter
public enum TechnologyMaturityEnum {
    LAB(141352631336961L,"实验室",1F),
    FEW_TEST(141352872509441L,"小试",2F),
    MID_TEST(141352912355329L,"中试",3F),
    FEW_BATCH(141352952201217L,"小批量生产",4F),
    MID_BATCH(141352987852801L,"工业化生产",5F),
    ;
    /**
     * db id
     */
    private Long id;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 用于推荐系统的 分值
     */
    private Float score;

    public static Float calScore(Long id){
        return Arrays.stream(TechnologyMaturityEnum.values())
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(TechnologyMaturityEnum::getScore)
                .orElse(0F);
    }
}
