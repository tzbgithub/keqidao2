package com.qidao.application.controller.dynamic;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.dynamic.*;
import com.qidao.application.model.member.favor.FavorPageReq;
import com.qidao.application.model.member.favor.FavorPageRes;
import com.qidao.application.service.dynamic.dynamic.DynamicService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "动态")
@RestController
@RequestMapping("dynamic")
@Slf4j
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;


    /**
     * 发布动态
     *
     * @param req
     * @return
     */
    @OperLog(title = "发布动态", businessType = BusinessType.INSERT)
    @ApiOperation(value = "发布动态", notes = "只有实验室可以发布动态<br>" +
            "内容可以添加为纯文字、图片+文字、文字+链接、图片+链接、文字+图片+链接、文字+视频；<br>" +
            "视频，图片及视频只能选择一项添加，纯文字内容不添加视频封面，带有图片或视频的必须添加封面")
    @PostMapping("/pushDynamic")
    public ResponseResult<Boolean> pushDynamic(@RequestBody @Validated DynamicPushReq req) {
        log.info("DynamicController -> pushDynamic -> start -> param : {}", req);
        dynamicService.pushDynamic(req);
        log.info("DynamicController -> pushDynamic -> end");
        return Result.ok();
    }

    /**
     * 分页查询动态列表
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "分页查询动态列表")
    @PostMapping("/getDynamic")
    public ResponseResult<ServicePage<List<DynamicPageRes>>> getDynamic(@RequestBody @Validated DynamicPageReq req) {
        log.info("DynamicController -> getDynamic -> start -> param:{}", req);
        ServicePage<List<DynamicPageRes>> dynamicPage = dynamicService.getDynamic(req);
        log.info("DynamicController -> getDynamic -> end");
        return Result.ok(dynamicPage);
    }

    /**
     * 根据动态ID删除动态及评论
     *
     * @param req
     * @return
     */
    @OperLog(title = "根据动态id删除动态及评论", businessType = BusinessType.DELETE)
    @ApiOperation("根据动态id删除动态及评论")
    @DeleteMapping("/deleteDynamicById")
    public ResponseResult<Object> deleteDynamicById(@RequestBody @Validated DynamicDeleteReq req) {
        log.info("DynamicController -> deleteDynamicById -> start -> param:{}", req);
        dynamicService.deleteDynamicById(req.getDynamic(), req.getMemberId());
        log.info("DynamicController -> deleteDynamicById -> end");
        return Result.ok();
    }

    /**
     * 获取动态详情
     *
     * @param req
     * @return
     */
    @ApiOperation("获取动态详情")
    @PostMapping("/getDynamicDetailed")
    public ResponseResult<DynamicDetailedRes> getDynamicDetailed(@RequestBody @Validated DynamicDetailedReq req) {
        log.info("DynamicController -> getDynamicDetailed -> start -> param:{}", req);
        DynamicDetailedRes dynamicDetailed = dynamicService.getDynamicDetailed(req.getDynamicId(), req.getMemberId());
        log.info("DynamicController -> getDynamicDetailed -> end");
        return Result.ok(dynamicDetailed);
    }

    /**
     * 动态点赞
     *
     * @param req
     * @return
     */
    @OperLog(title = "点赞", businessType = BusinessType.UPDATE)
    @ApiOperation("点赞")
    @PostMapping("/agreeDynamic")
    @QiDaoPermission
    public ResponseResult<Boolean> agreeDynamic(@RequestBody @Validated DynamicAgreeReq req) {
        log.info("DynamicController -> agreeDynamic -> start -> param:{}", req);
        dynamicService.agreeDynamic(req);
        log.info("DynamicController -> agreeDynamic -> end");
        return Result.ok();
    }

    /**
     * 动态取消点赞
     *
     * @param req
     * @return
     */
    @OperLog(title = "取消点赞", businessType = BusinessType.UPDATE)
    @ApiOperation("取消点赞")
    @PostMapping("/disagreeDynamic")
    public ResponseResult<Boolean> disagreeDynamic(@RequestBody @Validated DynamicDisagreeReq req) {
        log.info("DynamicController -> disagreeDynamic -> start -> param:{}", req);
        dynamicService.disagreeDynamic(req);
        log.info("DynamicController -> disagreeDynamic -> end");
        return Result.ok();
    }

    @ApiOperation("获取动态关注列表")
    @PostMapping("/getDynamicFollowRes")
    public ResponseResult<ServicePage<List<DynamicFollowRes>>> getDynamicFollowRes(@RequestBody @Validated DynamicFollowReq req) {
        log.info("DynamicController -> getDynamicFollowRes -> start -> param:{}", req);
        ServicePage<List<DynamicFollowRes>> dynamicFollow = dynamicService.getDynamicFollow(req);
        log.info("DynamicController -> getDynamicFollowRes -> end");
        return Result.ok(dynamicFollow);
    }

    /**
     * 获取我的评论分页
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取我的评论")
    @PostMapping("/getMyComment")
    public ResponseResult<ServicePage<List<MyCommentRes>>> getMyComment(@RequestBody @Validated MyCommentReq req) {
        log.info("DynamicController -> getMyComment -> start -> param:{}", req);
        ServicePage<List<MyCommentRes>> myComments = dynamicService.getMyComment(req);
        log.info("DynamicController -> getMyComment -> end ");
        return Result.ok(myComments);
    }

    /**
     * 获取我的点赞
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取我的点赞")
    @PostMapping("/getMyAgree")
    public ResponseResult<ServicePage<List<MyAgreeRes>>> getMyAgree(@RequestBody @Validated MyAgreeReq req) {
        log.info("DynamicController -> getMyAgree -> start -> param:{}", req);
        ServicePage<List<MyAgreeRes>> myAgree = dynamicService.getMyAgree(req);
        log.info("DynamicController -> getMyAgree -> end ");
        return Result.ok(myAgree);
    }

    /**
     * 获取我的收藏
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取我的收藏")
    @PostMapping("/getMyFavor")
    public ResponseResult<ServicePage<List<FavorPageRes>>> getMyFavor(@RequestBody @Validated FavorPageReq req) {
        log.info("DynamicController -> getMyComment -> start -> param:{}", req);
        ServicePage<List<FavorPageRes>> servicePage = dynamicService.getByMemberId(req);
        log.info("DynamicController -> getMyComment -> end ");
        return Result.ok(servicePage);
    }

    /**
     * 获取热门动态
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取热门动态")
    @PostMapping("/getHotDynamic")
    public ResponseResult<ServicePage<List<DynamicHotRes>>> getHotDynamic(@RequestBody @Validated DynamicHotReq req) {
        log.info("DynamicController -> getHotDynamic -> start -> param:{}", req);
        ServicePage<List<DynamicHotRes>> hotDynamic = dynamicService.getHotDynamic(req);
        log.info("DynamicController -> getHotDynamic -> end ");
        return Result.ok(hotDynamic);
    }

    /**
     * 获取我发布的动态
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取我发布的动态")
    @PostMapping("/getMyDynamic")
    public ResponseResult<ServicePage<List<MyDynamicRes>>> getMyDynamic(@RequestBody @Validated MyDynamicReq req) {
        log.info("DynamicController -> getMyDynamic -> start -> param:{}", req);
        ServicePage<List<MyDynamicRes>> dynamics = dynamicService.getMyDynamic(req);
        log.info("DynamicController -> getMyDynamic -> end ");
        return Result.ok(dynamics);
    }

    /**
     * 根据发布类型获取我发布的动态
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据发布类型获取我发布的动态")
    @PostMapping("/getMyDynamicArticle")
    public ResponseResult<ServicePage<List<MyDynamicArticlePageRes>>> getMyDynamicArticle(@RequestBody @Validated MyDynamicArticleReq req) {
        log.info("DynamicController -> getMyDynamicArticle -> start -> param:{}", req);
        ServicePage<List<MyDynamicArticlePageRes>> dynamics = dynamicService.getMyDynamicArticle(req);
        log.info("DynamicController -> getMyDynamicArticle -> end ");
        return Result.ok(dynamics);
    }

    /**
     * 根据发布类型获取实验室的动态
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据发布类型获取实验室的动态")
    @PostMapping("/getOrganizationDynamicArticle")
    public ResponseResult<ServicePage<List<OrganizationDynamicArticleRes>>> getOrganizationDynamicArticle(@RequestBody @Validated OrganizationDynamicArticleReq req) {
        log.info("DynamicController -> getOrganizationDynamicArticle -> start -> param:{}", req);
        ServicePage<List<OrganizationDynamicArticleRes>> dynamics = dynamicService.getOrganizationDynamicArticle(req);
        log.info("DynamicController -> getOrganizationDynamicArticle -> end ");
        return Result.ok(dynamics);
    }

    /**
     * 查询随机动态列表
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "随机动态列表")
    @PostMapping("/getRandomDynamic")
    public ResponseResult<List<RandomDynamicRes>> getRandomDynamic(@RequestBody @Validated RandomDynamicReq req) {
        log.info("DynamicController -> getRandomDynamic -> start -> param:{}", req);
        List<RandomDynamicRes> dynamicPage = dynamicService.getRandomDynamic(req);
        log.info("DynamicController -> getRandomDynamic -> end");
        return Result.ok(dynamicPage);
    }

    @ApiOperation(value = "老师或助手自己查询发布的动态")
    @PostMapping("/myselfDynamicArticle")
    public ResponseResult<ServicePage<List<MyDynamicArticlePageRes>>> myselfDynamicArticle(@RequestBody @Validated MyselfDynamicArticleReq req) {
        log.info("DynamicController -> myselfDynamicArticle -> start -> param:{}", req);
        ServicePage<List<MyDynamicArticlePageRes>> dynamics = dynamicService.myselfMyDynamicArticle(req);
        log.info("DynamicController -> myselfDynamicArticle -> end ");
        return Result.ok(dynamics);
    }
}
