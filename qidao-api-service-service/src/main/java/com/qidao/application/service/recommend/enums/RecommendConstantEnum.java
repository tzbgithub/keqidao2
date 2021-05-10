package com.qidao.application.service.recommend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RecommendConstantEnum {
    /**
     * 企岛推荐模式
     */
    QIDAO_MODEL("0"),
    /**
     * item 推荐模块
     */
    ITEM_MODEL("1"),
    /**
     * user 推荐模式
     */
    USER_MODEL("2"),
    /**
     * hot 推荐模式
     */
    HOT_MODEL("3"),
    /**
     * db 推荐模式
     */
    DB_MODEL("4"),
    ;
    private String val;
    public int getInt(){
        return Integer.parseInt(val);
    }
}
