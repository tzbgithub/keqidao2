package com.qidao.application.controller.config;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.common.req.BasePageQuery;
import com.qidao.application.model.config.*;
import com.qidao.application.service.config.CanalService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liu Le
 * @create 2020-12-29 14:15
 */
@Api(tags = "各包的分发渠道控制")
@RestController
@RequestMapping("/config/canal")
@Slf4j
public class CanalController {
    @Autowired
    private CanalService canalService;

    @ApiOperation(value = "添加分发渠道")
    @PostMapping("/create")
    @OperLog(title = "添加分发渠道", businessType = BusinessType.INSERT)
    public ResponseResult<Boolean> create(@RequestBody @Validated CanalCreateReq canalCreateReq) {
        log.info("CanalController -> create -> start -> canalCreateReq : {}", canalCreateReq);
        try {
            Boolean created = canalService.insert(canalCreateReq);
            log.info("CanalController -> create -> end");
            return Result.ok(created);
        } catch (Exception ex) {
            log.error("CanalController -> create -> end -> error : ", ex);
            return Result.fail(ex.getMessage());
        }
    }

    @ApiOperation(value = "更新分发渠道")
    @PutMapping("/update")
    @OperLog(title = "更新分发渠道", businessType = BusinessType.UPDATE)
    public ResponseResult<Boolean> update(@RequestBody @Validated CanalUpdateReq canalUpdateReq) {
        log.info("CanalController -> update -> start -> canalCreateReq : {}", canalUpdateReq);
        try {
            Boolean created = canalService.update(canalUpdateReq);
            log.info("CanalController -> create -> end");
            return Result.ok(created);
        } catch (Exception ex) {
            log.error("CanalController -> create -> end -> error : ", ex);
            return Result.fail(ex.getMessage());
        }
    }

    @ApiOperation(value = "删除分发渠道")
    @DeleteMapping("/deleteById")
    public ResponseResult<Boolean> deleteById(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("CanalController -> deleteById -> start -> baseIdQuery : {}", baseIdQuery);
        try {
            Boolean deleted = canalService.deleteById(baseIdQuery);
            log.info("CanalController -> deleteById -> end");
            return Result.ok(deleted);
        } catch (Exception ex) {
            log.error("CanalController -> deleteById -> end -> error : ", ex);
            return Result.fail(ex.getMessage());
        }
    }

    @ApiOperation(value = "分页查询所有包的分发渠道信息")
    @PostMapping("/getList")
    public ResponseResult<ServicePage<List<CanalRes>>> getList(@RequestBody @Validated BasePageQuery basePageQuery) {
        log.info("CanalController -> getList -> start -> basePageQuery : {}", basePageQuery);
        try {
            ServicePage<List<CanalRes>> servicePage = canalService.getList(basePageQuery);
            log.info("CanalController -> getList -> end");
            return Result.ok(servicePage);
        } catch (Exception ex) {
            log.error("CanalController -> getList -> end -> error : ", ex);
            return Result.fail(ex.getMessage());
        }
    }

    @ApiOperation(value = "查询是否是当前最新版本")
    @PostMapping("/verificationVersion")
    public ResponseResult<CanalRep> verificationVersion(@RequestBody @Validated CanalReq canalReq) {
        CanalRep canalRep = canalService.verificationVersion(canalReq);
        return Result.ok(canalRep);
    }
}
