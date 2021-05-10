package com.qidao.application.controller.log;

import cn.hutool.core.util.ObjectUtil;
import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.log.LogErrorInsertBatchReq;
import com.qidao.application.service.log.LogErrorService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jodd.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 异常/崩溃信息记录
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 2:36 PM
 */
@Api(tags = "异常、崩溃信息")
@Slf4j
@RestController
@RequestMapping("/log/error")
public class LogErrorController {

    @Autowired
    private LogErrorService logErrorService;

    /**
     * 批量插入(异常、崩溃信息)
     *
     * @param req 需要插入的信息
     * @return 插入成功的条目数量
     */
    @OperLog(title = "批量插入(异常、崩溃信息)", businessType = BusinessType.INSERT)
    @ApiOperation("批量插入(异常、崩溃信息)")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "异常、崩溃信息(集合)", dataType = "LogErrorInsertBatchReq", dataTypeClass = LogErrorInsertBatchReq.class, required = true)})
    @PostMapping("/insertBatch")
    ResponseResult<Integer> insertBatch(@RequestBody @Validated LogErrorInsertBatchReq req) {
        log.info("LogErrorController -> insertBatch -> LogErrorInsertBatchReq req : {}", req);
        List<LogErrorInsertBatchReq.ErrorMessage> errorMessageList = req.getErrorMessageList();
        boolean isEmpty = ObjectUtil.isEmpty(errorMessageList);
        log.info("LogErrorController -> insertBatch -> boolean isEmpty : {}", isEmpty);
        if (isEmpty) {
            return Result.ok(0);
        }
        Integer res = logErrorService.insertBatch(errorMessageList);
        log.info("LogErrorController -> insertBatch -> Return -> ResponseResult<Integer> : {}", res);
        return Result.ok(res);
    }

}
