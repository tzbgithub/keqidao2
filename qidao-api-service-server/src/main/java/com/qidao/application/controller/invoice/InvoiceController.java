package com.qidao.application.controller.invoice;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.invoice.InvoiceAddReq;
import com.qidao.application.model.invoice.InvoiceQuery;
import com.qidao.application.model.invoice.InvoiceRes;
import com.qidao.application.model.invoice.InvoiceUpdateReq;
import com.qidao.application.service.invoice.InvoiceService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 发票信息相关
 */
@RestController
@Slf4j
@RequestMapping("/invoice/")
@Api(tags = "发票信息相关",hidden = true)
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @ApiImplicitParams({@ApiImplicitParam(name = "invoiceAddReq", value = "待添加的发票对象", dataType = "InvoiceAddReq", dataTypeClass = InvoiceAddReq.class, required = true)})
    @ApiOperation(value = "添加发票", hidden = true)
    @PostMapping("createInvoice")
    @OperLog(title = "发票新增", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    public ResponseResult<Boolean> createInvoice(@RequestBody @Validated InvoiceAddReq invoiceAddReq) {
        log.info("InvoiceController -> createInvoice -> start -> invoiceAddReq ：{}", invoiceAddReq);
        try {
            Boolean isCreated = invoiceService.insert(invoiceAddReq);
            log.info("InvoiceController -> createInvoice -> end");
            return Result.ok(isCreated);
        } catch (Exception ex) {
            log.error("InvoiceController -> createInvoice -> end -> error : ", ex);
            return Result.fail("添加发票失败 -> " + ex.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "invoiceUpdateReq", value = "待更新的发票信息对象", dataType = "InvoiceUpdateReq", dataTypeClass = InvoiceUpdateReq.class, required = true)
    })
    @ApiOperation(value = "修改发票信息", hidden = true)
    @PutMapping("updateInvoice")
    @OperLog(title = "发票修改", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    public ResponseResult<Boolean> updateInvoice(@RequestBody @Validated InvoiceUpdateReq invoiceUpdateReq) {
        log.info("InvoiceController -> updateInvoice -> start -> invoiceUpdateReq : {}", invoiceUpdateReq);
        try {
            return Result.ok(invoiceService.update(invoiceUpdateReq));
        } catch (Exception ex) {
            log.error("InvoiceController -> updateInvoice -> end -> error : ", ex);
            return Result.fail("修改发票失败");
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "invoiceQuery", value = "分页查询对象 --> 分页查询用户申请且已被处理的所有发票", dataType = "InvoiceQuery", dataTypeClass = InvoiceQuery.class, required = true)
    })
    @ApiOperation(value = "分页查询用户申请且已被处理的所有发票", hidden = true)
    @PostMapping("getListDo")
    @QiDaoPermission(accessLevel = 3)
    public ResponseResult<ServicePage<List<InvoiceRes>>> getListDo(@RequestBody @Validated InvoiceQuery invoiceQuery) {
        log.info("InvoiceController -> getListDo -> start -> InvoiceQuery : {}", invoiceQuery);
        return Result.ok(invoiceService.getListDo(invoiceQuery));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "invoiceQuery", value = "分页查询对象 --> 分页查询用户申请且未被处理的所有发票", dataType = "InvoiceQuery", dataTypeClass = InvoiceQuery.class, required = true)
    })
    @ApiOperation(value = "分页查询用户申请且未被处理的所有发票", hidden = true)
    @PostMapping("getListUndo")
    @QiDaoPermission(accessLevel = 3)
    public ResponseResult<ServicePage<List<InvoiceRes>>> getListUndo(@RequestBody @Validated InvoiceQuery invoiceQuery) {
        log.info("InvoiceController -> getListUndo -> start -> invoiceQuery : {}", invoiceQuery);
        return Result.ok(invoiceService.getListUndo(invoiceQuery));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "baseIdQuery", value = "带删除的发票ID--主键", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true)
    })
    @ApiOperation(value = "根据发票ID删除该发票信息", hidden = true)
    @DeleteMapping("deleteById")
    @QiDaoPermission(accessLevel = 3)
    public ResponseResult<Boolean> deleteById(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("InvoiceController -> deleteById -> start -> baseIdQuery : {}", baseIdQuery);
        Boolean isDeleted = invoiceService.deleteById(baseIdQuery);
        log.info("InvoiceController -> deleteById -> isDeleted : {}", isDeleted);
        if (isDeleted) {
            log.info("InvoiceController -> deleteById -> end");
            return Result.ok();
        } else {
            log.info("InvoiceController -> deleteById -> end");
            return Result.fail("请求失败");
        }
    }
}
