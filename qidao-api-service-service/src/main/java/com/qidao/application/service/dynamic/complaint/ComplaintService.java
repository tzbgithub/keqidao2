package com.qidao.application.service.dynamic.complaint;

import com.qidao.application.model.dynamic.ComplaintAddReq;
import com.qidao.application.model.member.feedback.MemberComplaintAddReq;

/**
 * @author liu Le
 * @create 2020-12-30 17:22
 */
public interface ComplaintService {

    /**
     * 添加动态投诉 <br>
     * @param complaintAddReq {@link ComplaintAddReq} - 待添加的动态投诉对象
     * @return true-添加成功 false-添加失败
     */
    Boolean insert(ComplaintAddReq complaintAddReq);

    /**
     * 添加会员投诉
     * @param complaintAddReq
     * @return
     */
    Boolean memberComplaint(MemberComplaintAddReq complaintAddReq);
}
