package com.qidao.application.controller.msg;

import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.msg.*;
import com.qidao.application.service.msg.MsgRecordService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "消息记录")
@RestController
@RequestMapping("/msg/msgRecord")
@Slf4j
public class MsgRecordController {

    @Autowired
    private MsgRecordService msgRecordService;

    /**
     * 消息记录查询
     *
     * @param req
     * @return
     */
    @ApiOperation("消息记录查询")
    @ApiImplicitParam(name = "req", value = "消息记录查询请求对象", dataType = "MsgRecordPageReq", dataTypeClass = MsgRecordPageReq.class, required = true)
    @PostMapping("/getMsgRecordPage")
    public ResponseResult<ServicePage<List<MsgRecordPageRes>>> getMsgRecordPage(@RequestBody @Validated MsgRecordPageReq req) {
        log.info("MsgRecordController -> getMsgRecordPage -> start -> param:{}", req);
        ServicePage<List<MsgRecordPageRes>> msgRecordPage = msgRecordService.getMsgRecordPage(req);
        log.info("MsgRecordController -> getMsgRecordPage -> end");
        return Result.ok(msgRecordPage);
    }

    /**
     * 修改消息记录
     *
     * @param req
     * @return
     */
    @ApiOperation("修改消息记录")
    @ApiImplicitParam(name = "req", value = "修改消息记录请求对象", dataType = "MsgRecordUpdateReq", dataTypeClass = MsgRecordUpdateReq.class, required = true)
    @PutMapping("/update")
    public ResponseResult<Object> update(@RequestBody @Validated MsgRecordUpdateReq req) {
        log.info("MsgRecordController -> update -> start -> param:{}", req);
        msgRecordService.update(req);
        log.info("MsgRecordController -> update -> end");
        return Result.ok();
    }

    /**
     * 新增消息记录
     *
     * @param req
     * @return
     */
    @ApiOperation("新增消息记录")
    @ApiImplicitParam(name = "req", value = "新增消息记录请求对象", dataType = "MsgRecordInsertReq", dataTypeClass = MsgRecordInsertReq.class, required = true)
    @PostMapping("/insert")
    public ResponseResult<Object> insert(@RequestBody @Validated MsgRecordInsertReq req) {
        log.info("MsgRecordController -> insert -> start -> param:{}", req);
        msgRecordService.insert(req);
        log.info("MsgRecordController -> insert -> end");
        return Result.ok();
    }

    /**
     * 删除消息记录
     *
     * @param req
     * @return
     */
    @ApiOperation("删除消息记录")
    @ApiImplicitParam(name = "req", value = "删除消息记录请求对象", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true)
    @DeleteMapping("/delete")
    public ResponseResult<Object> delete(@RequestBody @Validated BaseIdQuery req) {
        log.info("MsgRecordController -> delete -> start -> param:{}", req);
        msgRecordService.delete(req.getId());
        log.info("MsgRecordController -> delete -> end");
        return Result.ok();
    }

    /**
     * 删除当前会员已读的所有记录
     *
     * @param req
     * @return
     */
    @ApiOperation("删除当前会员已读的所有记录")
    @ApiImplicitParam(name = "req", value = "会员id", required = true)
    @PostMapping("/clearReadMsgRecordByMemberId")
    @QiDaoPermission
    public ResponseResult<Object> clearReadMsgRecordByMemberId(@RequestBody @Validated BaseIdQuery req) {
        log.info("MsgRecordController -> clearReadMsgRecordByMemberId -> start -> param:{}", req);
        msgRecordService.clearReadMsgRecordByMemberId(req.getId());
        log.info("MsgRecordController -> clearReadMsgRecordByMemberId -> end");
        return Result.ok();
    }

    /**
     * 删除消息记录
     *
     * @param req
     * @return
     */
    @ApiOperation("验证登录用户&删除消息记录请求对象")
    @PostMapping("/deleteByMemberId")
    @QiDaoPermission
    public ResponseResult<Object> deleteByMemberId(@RequestBody @Validated MsgRecordDeleteReq req) {
        log.info("MsgRecordController -> deleteByMemberId -> start -> param:{}", req);
        msgRecordService.delete(req.getMsgRecordId());
        log.info("MsgRecordController -> deleteByMemberId -> end");
        return Result.ok();
    }

    /**
     * 所有通知类型的消息查询
     *
     * @param req
     * @return
     */
    @ApiOperation("所有通知类型的消息查询")
    @PostMapping("/getAllNoticeMsgRecordPage")
    @QiDaoPermission
    public ResponseResult<ServicePage<List<NoticeMsgRecordPageResp>>> getAllNoticeMsgRecordPage(@RequestBody @Validated NoticeMsgRecordPageReq req) {
        log.info("MsgRecordController -> getAllNoticeMsgRecordPage -> start -> param:{}", req);
        //状态 0-（发送成功）未读 1-（发送成功）已读 2-未发送状态  3-发送成功 4-发送失败
        req.setStatusList(Arrays.asList(0, 1, 3));
        ServicePage<List<NoticeMsgRecordPageResp>> msgRecordPage = msgRecordService.getAllNoticeMsgRecordPage(req);
        log.info("MsgRecordController -> getAllNoticeMsgRecordPage -> end");
        return Result.ok(msgRecordPage);
    }


    /**
     * 查询未读消息数量
     *
     * @param req
     * @return
     */
    @ApiOperation("查询未读消息数量")
    @ApiImplicitParam(name = "req", value = "会员id", required = true)
    @PostMapping("/selectNotReadMsgCount")
    @QiDaoPermission
    public ResponseResult<Long> selectNotReadMsgCount(@RequestBody @Validated BaseIdQuery req) {
        log.info("MsgRecordController -> selectNotReadMsgCount -> start -> param:{}", req);
        long count = msgRecordService.selectNotReadMsgCount(req.getId());
        log.info("MsgRecordController -> selectNotReadMsgCount -> end");
        return Result.ok(count);
    }
}
