package com.qidao.application.controller.search;

import com.qidao.application.model.common.Result;
import com.qidao.application.model.search.HotSearchConstantEnum;
import com.qidao.application.model.search.HotSearchRes;
import com.qidao.application.model.search.SearchPageReq;
import com.qidao.application.service.search.SearchService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "搜索接口")
@Slf4j
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation("搜索")
    @PostMapping("/search")
//    @QiDaoPermission(accessLevel = 1)
    public ResponseResult<Object> searchResult(@RequestBody @Validated SearchPageReq req) {
        log.info("SearchController -> searchResult -> start -> param:{}", req);
        Object result = searchService.getSearchDynamic(req);
        log.info("SearchController -> searchResult -> end");
        return Result.ok(result);
    }

    /**
     * 热门搜索标签后台配置，最多显示6个，不足六个时有多少显示多少，
     * 多于6个时，只显示六个，后台没配置情况下，不显示热门搜索
     */
    @ApiOperation("获取热门搜索词汇(标签),最多显示6个")
    @PostMapping("/getHotSearch")
    public ResponseResult<HotSearchRes> getHotSearch() {
        HotSearchRes hotSearch = searchService.getHotSearch(HotSearchConstantEnum.HOT_DISPLAY_COUNT.getValue());
        log.info("SearchController -> getHotSearch -> Return -> ResponseResult<HotSearchRes> : {}", hotSearch);
        return Result.ok(hotSearch);
    }

}
