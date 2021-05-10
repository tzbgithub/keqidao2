package com.qidao.application.service.member;

import com.qidao.application.model.member.feedback.FeedbackAddReq;


/**
 * @Author xieyiming
 * @CreateTime 2020-12-19
 */
public interface FeedbackService {


    /**
     * 用于插入新的反馈记录
     *
     * @param feedbackAddReq 待添加的反馈对象
     * @return 插入是否成功
     *
     */
    Boolean insert(FeedbackAddReq feedbackAddReq);

}
