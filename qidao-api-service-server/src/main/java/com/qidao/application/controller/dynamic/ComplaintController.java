package com.qidao.application.controller.dynamic;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.dynamic.ComplaintAddReq;
import com.qidao.application.service.dynamic.complaint.ComplaintService;
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


/**
 * @Description:
 * @author: liu Le
 * @create: 2020-12-30 17:25
 */
@Api(tags = "动态投诉处理")
@RestController
@Slf4j
@RequestMapping("/dynamic/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @ApiOperation(value = "添加动态投诉")
    @PostMapping("/create")
    @OperLog(title = "动态投诉新增", businessType = BusinessType.INSERT)
    public ResponseResult<Boolean> create(@RequestBody @Validated ComplaintAddReq complaintAddReq) {
        log.info("ComplaintController -> create -> start -> complaintAddReq : {}", complaintAddReq);
        try {
            Boolean isCreated = complaintService.insert(complaintAddReq);
            log.info("ComplaintController -> create -> isCreated : {}", isCreated);
            return Result.ok(isCreated);
        } catch (Exception ex) {
            log.error("ComplaintController -> create -> error : ", ex);
            return Result.fail(ex.getMessage());
        }
    }
}
