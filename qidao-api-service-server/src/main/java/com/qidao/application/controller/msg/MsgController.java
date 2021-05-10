package com.qidao.application.controller.msg;

import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.config.SelectConfigVo;
import com.qidao.application.model.config.SelectGetByTypeReq;
import com.qidao.application.model.log.MsgNotifyReq;
import com.qidao.application.model.msg.*;
import com.qidao.application.model.msg.listen.QidaoSchedulingConfigurer;
import com.qidao.application.service.msg.MsgService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "消息相关接口")
@RestController
@RequestMapping("/msg/msg")
@Slf4j
public class MsgController {

    @Autowired
    private MsgService msgService;

    @Autowired
    private QidaoSchedulingConfigurer qidaoSchedulingConfigurer;

    /**
     * 新增消息
     *
     * @param req
     * @return
     */
    @ApiOperation("新增消息")
    @ApiImplicitParam(name = "req", value = "新增消息对象", dataType = "MsgInsertReq", dataTypeClass = MsgInsertReq.class, required = true)
    @PostMapping("/insert")
    public ResponseResult<Boolean> insert(@RequestBody @Validated MsgInsertReq req) {
        log.info("MsgController -> insert -> start -> param:{}", req);
        msgService.insert(req);
        log.info("MsgController -> insert -> end");
        return Result.ok();
    }

    /**
     * 分页查询消息列表
     * @param req
     * @return
     */
    @ApiOperation("分页查询消息列表")
    @ApiImplicitParam(name = "req", required = true, dataType = "MsgPageReq", dataTypeClass = MsgPageReq.class, value = "查询消息请求对象")
    @PostMapping("/getPageMsg")
    public ResponseResult<ServicePage<List<MsgPageRes>>> getPageMsg(@RequestBody @Validated MsgPageReq req) {
        log.info("MsgController -> getPageMsg -> start -> param:{}", req);
        ServicePage<List<MsgPageRes>> msg = msgService.getMsg(req);
        log.info("MsgController -> getPageMsg -> end");
        return Result.ok(msg);
    }

    /**
     * 修改消息
     * @param req
     * @return
     */
    @ApiOperation("修改消息")
    @ApiImplicitParam(name = "req", value = "修改消息对象", dataType = "MsgUpdateReq", dataTypeClass = MsgUpdateReq.class, required = true)
    @PutMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody @Validated MsgUpdateReq req) {
        log.info("MsgController -> update -> start -> param:{}", req);
        msgService.update(req);
        log.info("MsgController -> update -> end");
        return Result.ok();
    }

    /**
     * 删除消息
     * @param req
     * @return
     */
    @ApiOperation("删除消息")
    @ApiImplicitParam(name = "req", value = "删除消息对象", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true)
    @DeleteMapping("/delete")
    public ResponseResult<Boolean> delete(@RequestBody @Validated BaseIdQuery req) {
        log.info("MsgController -> delete -> start -> param:{}", req);
        msgService.delete(req.getId());
        log.info("MsgController -> delete -> end");
        return Result.ok();
    }

    /**
     * 根据类型查询select_config
     * @param req
     * @return
     */
    @ApiOperation("根据类型查询select_config")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "SelectGetByTypeReq", dataTypeClass = SelectGetByTypeReq.class, required = true)
    @PostMapping("/getSelectConfigByType")
    public ResponseResult<ServicePage<List<SelectConfigVo>>> getSelectConfigByType(@RequestBody @Validated SelectGetByTypeReq req) {
        log.info("MsgController -> getSelectConfigByType -> start -> param:{}", req);
        ServicePage<List<SelectConfigVo>> res = msgService.getSelectByType(req);
        log.info("MsgController -> getSelectConfigByType -> end");
        return Result.ok(res);
    }

    @PostMapping("/timerSize")
    @QiDaoPermission(ableMember = "1101")
    @ApiOperation(value = "查询定时器内未执行任务数量",notes = "只允许会员ID： 1101 可以查询")
    public ResponseResult<Integer> timerSize(){
        Integer size = qidaoSchedulingConfigurer.size();
        return Result.ok(size);
    }

    /**
     * 查询用户VIP消息列表
     * @param req
     * @return
     */
    @ApiOperation("查询用户VIP消息列表")
    @ApiImplicitParam(name = "req", required = true, value = "查询用户VIP消息请求对象", dataType = "MemberVipMsgPageReq", dataTypeClass = MemberVipMsgPageReq.class)
    @PostMapping("/getMemberVipPageMsg")
    public ResponseResult<ServicePage<List<MemberVipMsgPageRes>>> getMemberVipPageMsg(@RequestBody @Validated MemberVipMsgPageReq req) {
        log.info("MsgController -> getMemberVipPageMsg -> start -> param:{}", req);
        ServicePage<List<MemberVipMsgPageRes>> msg = msgService.getMemberVipMsg(req);
        log.info("MsgController -> getMemberVipPageMsg -> end");
        return Result.ok(msg);
    }

    @ApiOperation("清空vip消息")
    @ApiImplicitParam(name = "req", required = true, value = "会员ID", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class)
    @PostMapping("/emptyVipMsg")
    public ResponseResult<Object> emptyVipMsg(@RequestBody @Validated BaseIdQuery req) {
        msgService.emptyVipMsg(req);
        return Result.ok();
    }


    @ApiOperation(value = "测试：发送通知", notes = "测试临时使用，极光推送到指定用户")
    @PostMapping(value = "/notify")
    @Deprecated
    ResponseResult notify(@RequestBody @Validated MsgNotifyReq req) {
        msgService.notifyPush(req);
        return Result.ok();
    }
}
