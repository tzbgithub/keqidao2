package com.qidao.application.service.search;

import com.qidao.application.model.search.HotSearchRes;
import com.qidao.application.model.search.SearchPageReq;

import java.util.List;


public interface SearchService {

    /**
     * 获取动态的搜索结果
     * @param req
     * @return
     */
    Object getSearchDynamic(SearchPageReq req);

    /**
     * 热门搜索标签后台配置，最多显示6个，不足六个时有多少显示多少，
     * 多于6个时，只显示六个，后台没配置情况下，不显示热门搜索
     * @param topN 返回最热门的topN个
     * @return
     */
    HotSearchRes getHotSearch(Integer topN);

}
