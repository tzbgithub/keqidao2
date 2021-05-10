package com.qidao.application.controller.dynamic.comment;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.dynamic.comment.*;
import com.qidao.application.service.dynamic.comment.CommentService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "评论接口")
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 发表评论
     *
     * @param req
     * @return
     */
    @OperLog(title = "发表评论", businessType = BusinessType.INSERT)
    @ApiOperation("发表评论")
    @PostMapping("/pushComment")
    public ResponseResult<Boolean> pushComment(@RequestBody @Validated CommentPushReq req) {
        log.info("CommentController -> pushComment -> start -> params:{}", req);
        Boolean pushComment = commentService.pushComment(req);
        log.info("CommentController -> pushComment -> end");
        return Result.ok(pushComment);

    }

    /**
     * 删除评论
     *
     * @param req
     * @return
     */
    @OperLog(title = "删除评论", businessType = BusinessType.DELETE)
    @ApiOperation("删除评论")
    @DeleteMapping("/deleteCommentById")
    public ResponseResult<Object> deleteCommentById(@RequestBody @Validated CommentDeleteReq req) {
        log.info("CommentController -> deleteCommentById -> start -> param:{} ", req);
        commentService.deleteComment(req.getCommentId(), req.getMemberId());
        log.info("CommentController -> deleteCommentById -> end");
        return Result.ok();
    }

    /**
     * 根据动态ID分页查询评论
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据动态ID查询评论")
    @PostMapping("/getCommentByDynamicId")
    public ResponseResult<ServicePage<List<CommentRes>>> getCommentByDynamicId(@RequestBody @Validated CommentPageReq req) {
        log.info("CommentController -> getCommentByDynamicId -> start -> param:{}", req);
        ServicePage<List<CommentRes>> comments = commentService.getCommentByDynamicId(req);
        log.info("CommentController -> getCommentByDynamicId -> end");
        return Result.ok(comments);
    }

    /**
     * 点赞
     *
     * @param req
     * @return
     */
    @OperLog(title = "点赞", businessType = BusinessType.UPDATE)
    @ApiOperation("点赞")
    @PostMapping("/agree")
    public ResponseResult<Boolean> agree(@RequestBody @Validated CommentAgreeReq req) {
        log.info("CommentController -> agree -> start -> param:{}", req);
        Boolean agree = commentService.agree(req);
        log.info("CommentController -> agree -> end");
        return Result.ok(agree);
    }

    /**
     * 取消点赞
     *
     * @param req
     * @return
     */
    @OperLog(title = "取消点赞", businessType = BusinessType.UPDATE)
    @ApiOperation("取消点赞")
    @PostMapping("/disagree")
    public ResponseResult<Boolean> disagree(@RequestBody @Validated CommentAgreeReq req) {
        log.info("CommentController -> disagree -> start -> param:{}", req);
        Boolean disagree = commentService.disagree(req);
        log.info("CommentController -> disagree -> end");
        return Result.ok(disagree);
    }

    /**
     * 验证用户是否有删除评论的权限
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "验证用户是否有删除评论的权限", notes = "只有动态发布者或评论发布者可以删除评论<br>返回参数：0-是   1-否")
    @PostMapping("/checkRole")
    public ResponseResult<Integer> checkRole(@RequestBody @Validated CommentCheckRoleReq req) {
        log.info("CommentController -> checkRole -> start -> param:{}", req);
        int status = commentService.checkRole(req.getCommentId(), req.getMemberId());
        log.info("CommentController -> checkRole -> end");
        return Result.ok(status);
    }

}
