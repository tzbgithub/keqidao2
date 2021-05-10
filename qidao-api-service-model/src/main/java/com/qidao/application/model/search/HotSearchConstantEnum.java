package com.qidao.application.model.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 10:34 AM
 */
@Getter
@AllArgsConstructor
public enum HotSearchConstantEnum {
    /**
     * 热门搜索词汇/标签的展示个数
     */
    HOT_DISPLAY_COUNT(6);

    private final Integer value;
}
