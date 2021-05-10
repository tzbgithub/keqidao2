package com.qidao.application.controller.contract;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.contract.progress.*;
import com.qidao.application.service.contract.ProgressService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 11:13 AM
 */
@Api(tags = "合同项目进度")
@Slf4j
@RestController
@RequestMapping("/contract/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @OperLog(title = "乙方-完成项目", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "乙方-完成项目")
    @PutMapping("/complete")
    public ResponseResult<ProgressRes> complete(@RequestBody ProgressCompleteReq req) {
        log.info("ProgressController -> complete -> ProgressCompleteReq req : {}", req);
        ResponseResult<ProgressRes> resp;
        ProgressDTO dto = progressService.complete(req);
        boolean completeSuccess = dto.getSuccess();
        log.info("ProgressController -> complete -> boolean completeSuccess : {}", completeSuccess);
        if (completeSuccess) {
            resp = Result.ok(null);
            log.info("ProgressController -> complete -> Return -> ResponseResult<ProgressRes> : {}", resp);
            return resp;
        }
        resp = Result.fail(dto.getCodeMessageEnum());
        log.info("ProgressController -> complete -> Return -> ResponseResult<ProgressRes> : {}", resp);
        return resp;
    }

    @OperLog(title = "甲方-验收项目", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "甲方-验收项目")
    @PutMapping("/accept")
    @QiDaoPermission
    public ResponseResult<ProgressRes> accept(@RequestBody ProgressAcceptReq req) {
        log.info("ProgressController -> accept -> ProgressCompleteReq req : {}", req);
        ResponseResult<ProgressRes> resp;
        ProgressDTO dto = progressService.accept(req);
        boolean acceptSuccess = dto.getSuccess();
        log.info("ProgressController -> accept -> boolean acceptSuccess : {}", acceptSuccess);
        if (acceptSuccess) {
            resp = Result.ok(null);
            log.info("ProgressController -> accept -> Return -> ResponseResult<ProgressRes> : {}", resp);
            return resp;
        }
        resp = Result.fail(dto.getCodeMessageEnum());
        log.info("ProgressController -> accept -> Return -> ResponseResult<ProgressRes> : {}", resp);
        return resp;
    }

    @OperLog(title = "乙方-确认项目", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "乙方-确认项目")
    @PutMapping("/confirm")
    public ResponseResult<ProgressRes> confirm(@RequestBody ProgressComfirmReq req) {
        log.info("ProgressController -> confirm -> ProgressComfirmReq req : {}", req);
        ResponseResult<ProgressRes> resp;
        ProgressDTO dto = progressService.confirm(req);
        boolean confirmSuccess = dto.getSuccess();
        log.info("ProgressController -> confirm -> boolean confirmSuccess : {}", confirmSuccess);
        if (confirmSuccess) {
            resp = Result.ok(null);
            log.info("ProgressController -> confirm -> Return -> ResponseResult<ProgressRes> : {}", resp);
            return resp;
        }
        resp = Result.fail(dto.getCodeMessageEnum());
        log.info("ProgressController -> confirm -> Return -> ResponseResult<ProgressRes> : {}", resp);
        return resp;
    }

    @ApiOperation(value = "获取项目进度详情页")
    @GetMapping("/detail")
    public ResponseResult<ProgressDetailRes> detail(
            @RequestParam @NotNull @ApiParam(name = "memberId", value = "当前会员的用户id", required = true, example = "130879657672706") Long memberId,
            @RequestParam @NotNull @ApiParam(name = "progressId", value = "项目进度任务id", required = true, example = "1") Long progressId
    ) {
        log.info("ProgressController -> detail -> Long memberId : {} , Long progressId : {}", memberId, progressId);
        ResponseResult<ProgressDetailRes> resp;
        ProgressDTO dto = progressService.detail(
                ProgressDetailReq.builder()
                        .memberId(memberId)
                        .progressId(progressId)
                        .build()
        );
        resp = Result.ok(dto.getDetail());
        log.info("ProgressController -> detail -> Return -> ResponseResult<ProgressDetailRes> : {}", resp);
        return resp;
    }

    @ApiOperation(value = "获取项目周期目标确认列表")
    @GetMapping("/listSteps")
    public ResponseResult<List<ProgressStepRes>> list(
            @RequestParam @NotNull @ApiParam(name = "memberId", value = "当前会员的用户id", required = true, example = "130879657672706") Long memberId,
            @RequestParam @NotNull @ApiParam(name = "contractId", value = "合同(项目)id", required = true, example = "1") Long contractId
    ) {
        log.info("ProgressController -> listUnconfirmed -> Long memberId : {} , Long contractId : {}", memberId, contractId);
        ResponseResult<List<ProgressStepRes>> resp;
        ProgressDTO dto = progressService.listSteps(
                ProgressListStepsReq.builder()
                        .memberId(memberId)
                        .contractId(contractId)
                        .build()
        );
        resp = Result.ok(dto.getStepList());
        log.info("ProgressController -> listUnconfirmed -> Return -> ResponseResult<List<ProgressStepRes>> : {}", resp);
        return resp;
    }
}
