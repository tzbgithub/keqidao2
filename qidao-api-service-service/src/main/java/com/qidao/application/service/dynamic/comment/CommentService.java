package com.qidao.application.service.dynamic.comment;

import com.qidao.application.model.dynamic.comment.CommentAgreeReq;
import com.qidao.application.model.dynamic.comment.CommentPageReq;
import com.qidao.application.model.dynamic.comment.CommentPushReq;
import com.qidao.application.model.dynamic.comment.CommentRes;
import com.qidao.framework.service.ServicePage;

import java.util.List;

public interface CommentService {

    /**
     * 发表评论
     * @param req
     * @return
     */
    Boolean pushComment(CommentPushReq req);

    /**
     * 删除评论
     * @param commentId
     */
    void deleteComment(Long commentId , Long memberId);

    /**
     * 根据动态id查询所属评论
     * @param req
     * @return
     */
    ServicePage<List<CommentRes>> getCommentByDynamicId(CommentPageReq req);

    /**
     * 点赞
     * @param req
     * @return
     */
    Boolean agree(CommentAgreeReq req);

    /**
     * 取消赞
     * @param req
     * @return
     */
    Boolean disagree(CommentAgreeReq req);


    /**
     * 验证当前用户是否为动态发布者或评论发布者
     * @param commentId
     * @param memberId
     * @return
     */
    int checkRole(Long commentId , Long memberId);

}
