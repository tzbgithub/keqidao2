package com.qidao.application.controller.order;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.order.*;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.service.order.OrderService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(tags = "订单相关接口")
@RequestMapping("/order")
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "OrderPageReq", dataTypeClass = OrderPageReq.class, required = true)
    @ApiOperation(value = "根据会员ID分页查询订单")
    @PostMapping("/getOrderByMemberId")
    @QiDaoPermission
    public ResponseResult<OrderPageRes> getOrderByMemberId(@RequestBody @Validated OrderPageReq req) {
        log.info("OrderController -> getOrderByMemberId -> start -> req:{}", req);
        OrderPageRes res = orderService.getOrderByMemberId(req);
        log.info("OrderController -> getOrderByMemberId -> end");
        return Result.ok(res);
    }


    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "OrderCreateOneProductReq", dataTypeClass = OrderCreateOneProductReq.class, required = true)
    @ApiOperation(value = "创建单产品订单")
    @PostMapping("/orderCreateOneProduct")
    @OperLog(title = "创建单产品订单", businessType = BusinessType.INSERT)
    @QiDaoPermission
    public ResponseResult orderCreateOneProduct(@RequestBody @Validated OrderCreateOneProductReq req) {
        log.info("OrderController -> getOrderDescription -> start -> req -> {}", req);
        OrderCreateOneProductRes res = orderService.orderCreateOneProduct(req);
        log.info("OrderController -> getOrderDescription -> end -> res -> {}", req);
        return Result.ok(res);
    }

    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "OrderDescriptionReq", dataTypeClass = OrderDescriptionReq.class, required = true)
    @ApiOperation("查询订单详情")
    @PostMapping("/getOrderDescription")
    public ResponseResult<OrderDescriptionRes> getOrderDescription(@RequestBody @Validated OrderDescriptionReq req) {
        log.info("OrderController -> getOrderDescription -> start -> param:{}", req);
        OrderDescriptionRes orderDescription = orderService.getOrderDescription(req);
        log.info("OrderController -> getOrderDescription -> end");
        return Result.ok(orderDescription);
    }

    @OperLog(title = "修改未支付订单为已支付", businessType = BusinessType.UPDATE)
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true)
    @ApiOperation("修改未支付订单为已支付")
    @PutMapping("/updateStatusPayment")
    public ResponseResult<Boolean> updateStatusPayment(@RequestBody @Validated BaseIdQuery req) {
        log.info("OrderController -> updateStatusPayment -> start -> param:{}", req);
        boolean b = orderService.updateStatusPayment(req.getId());
        log.info("OrderController -> updateStatusPayment -> end");
        return Result.ok(b);
    }

    @OperLog(title = "修改未支付订单为已关闭", businessType = BusinessType.UPDATE)
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true)
    @ApiOperation("修改未支付订单为已关闭")
    @PutMapping("/updateStatusClosed")
    public ResponseResult<Boolean> updateStatusClosed(@RequestBody @Validated BaseIdQuery req) {
        log.info("OrderController -> updateStatusClosed -> start -> param:{}", req);
        boolean b = orderService.updateStatusClosed(req.getId());
        log.info("OrderController -> updateStatusClosed -> end");
        return Result.ok(b);
    }


    @ApiOperation(value = "查询订单是否已支付并完成",notes = "")
    @PostMapping("/isOrderDone")
    public ResponseResult<IsOrderDoneRes> isOrderDone(@RequestBody @Validated OrderNoReq req){
        log.info("OrderController -> updateStatusClosed -> start -> param -> {}",req);
        boolean isDone = orderService.isOrderDone(req.getOrderNo());
        log.info("OrderController -> updateStatusClosed -> end -> isDone -> {}",isDone);
        return Result.ok(new IsOrderDoneRes(isDone));
    }

    @ApiOperation(value = "实验室0元订单确认完成")
    @PostMapping("/labOrderDone")
    @QiDaoPermission
    public ResponseResult<Objects> labOrderDone(@RequestBody @Validated LabOrderDoneReq req) {
        log.info("OrderController -> labOrderDone -> start -> param -> {}",req);
        orderService.labOrderDone(req);
        return Result.ok();
    }
}
