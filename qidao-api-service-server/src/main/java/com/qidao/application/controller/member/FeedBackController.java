package com.qidao.application.controller.member;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.dynamic.ComplaintAddReq;
import com.qidao.application.model.member.feedback.FeedbackAddReq;
import com.qidao.application.model.member.feedback.MemberComplaintAddReq;
import com.qidao.application.service.dynamic.complaint.ComplaintService;
import com.qidao.application.service.member.FeedbackService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "会员反馈及投诉")
@RestController
@RequestMapping("/member")
@Slf4j
public class FeedBackController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private ComplaintService complaintService;

    @ApiImplicitParams({@ApiImplicitParam(name = "feedbackAddReq", value = "新增的反馈对象", required = true,dataType = "FeedbackAddReq",dataTypeClass = FeedbackAddReq.class)})
    @ApiOperation(value = "添加新的反馈记录")
    @OperLog(title = "反馈新增", businessType = BusinessType.INSERT,isAsyncSaveToDB = true)
    @PostMapping("/feedback/insert")
    @QiDaoPermission
    public ResponseResult<Boolean> insert(@RequestBody @Validated FeedbackAddReq feedbackAddReq) {
        log.info("FeedBackController -> insert -> start -> feedbackAddReq : {}", feedbackAddReq);
        log.info("FeedBackController -> insert -> end");
        return Result.ok(feedbackService.insert(feedbackAddReq));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "complaintAddReq", value = "待添加的动态投诉对象", required = true, dataType = "MemberComplaintAddReq", dataTypeClass = MemberComplaintAddReq.class)})
    @ApiOperation(value = "投诉会员")
    @PostMapping("/complaint/insert")
    @OperLog(title = "投诉会员", businessType = BusinessType.INSERT,isAsyncSaveToDB = true)
    @QiDaoPermission
    public ResponseResult<Boolean> complaintInsert(@RequestBody @Validated MemberComplaintAddReq complaintAddReq){
        log.info("FeedBackController -> complaintInsert -> start -> complaintAddReq : {}", complaintAddReq);
        Boolean isCreated = complaintService.memberComplaint(complaintAddReq);
        log.info("FeedBackController -> complaintInsert -> isCreated : {}", isCreated);
        return Result.ok(isCreated);
    }
}
