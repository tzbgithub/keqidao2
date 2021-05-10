package com.qidao.application.controller.recommend;

import com.qidao.application.model.common.Result;
import com.qidao.application.model.dynamic.DynamicPageRes;
import com.qidao.application.model.member.MemberSummaryRes;
import com.qidao.application.model.recommend.RecommendDynamicReq;
import com.qidao.application.model.recommend.RecommendResearcherReq;
import com.qidao.application.service.recommend.IRecommendService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommend")
@Api(tags ="千人千面推荐")
public class RecommendController {
    @Autowired
    private IRecommendService recommendService;

    @PostMapping("/dynamic")
    @ApiOperation("获取动态列表")
    ResponseResult<List<DynamicPageRes>> dynamic(@RequestBody @Validated RecommendDynamicReq req) throws Exception {
        List<DynamicPageRes> list = recommendService.getDynamicList(req);
        return Result.ok(list);
    }

    @PostMapping("/research")
    @ApiOperation("获取推荐人员列表")
    ResponseResult<List<MemberSummaryRes>> research(@RequestBody @Validated RecommendResearcherReq req) {
        List<MemberSummaryRes> scientificResearchList = recommendService.getScientificResearchList(req);
        return Result.ok(scientificResearchList);
    }
}
