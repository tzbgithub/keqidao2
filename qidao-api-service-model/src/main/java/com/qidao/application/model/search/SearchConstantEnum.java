package com.qidao.application.model.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 10:34 AM
 */
@Getter
@AllArgsConstructor
public enum SearchConstantEnum {
    /**
     * 搜索类型 0-动态
     */
    SEARCH_DYNAMIC(0),
    /**
     * 搜索类型 1-用户
     */
    SEARCH_MEMBER(1),
    /**
     * 搜索类型 2-成果文章
     */
    SEARCH_ACHIEVEMENT_EQUIPMENT(2)
    ;

    private final Integer value;
}
