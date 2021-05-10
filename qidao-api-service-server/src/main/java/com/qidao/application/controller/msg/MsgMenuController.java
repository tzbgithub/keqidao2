package com.qidao.application.controller.msg;

import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.msg.MsgMenuInsertReq;
import com.qidao.application.model.msg.MsgMenuQueryReq;
import com.qidao.application.model.msg.MsgMenuQueryRes;
import com.qidao.application.model.msg.MsgMenuUpdateReq;
import com.qidao.application.service.msg.MsgMenuService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "消息菜单")
@RestController
@RequestMapping("/msg/msgMenu")
@Slf4j
public class MsgMenuController {

    @Autowired
    private MsgMenuService msgMenuService ;

    /**
     * 查询消息菜单列表
     *
     * @param req
     * @return
     */
    @ApiOperation("查询消息菜单列表")
    @ApiImplicitParam(name = "req", value = "查询消息菜单请求对象", dataType = "MsgMenuQueryReq", dataTypeClass = MsgMenuQueryReq.class, required = true)
    @PostMapping("/getMsgMenu")
    public ResponseResult<List<MsgMenuQueryRes>> getMsgMenu(@RequestBody @Validated MsgMenuQueryReq req) {
        log.info("MsgMenuController -> getMsgMenu -> start -> param:{}", req);
        List<MsgMenuQueryRes> msgMenu = msgMenuService.getMsgMenu(req);
        log.info("MsgMenuController -> getMsgMenu -> end");
        return Result.ok(msgMenu);
    }

    /**
     * 新增消息菜单
     *
     * @param req
     * @return
     */
    @ApiOperation("新增消息菜单")
    @ApiImplicitParam(name = "req", value = "新增消息菜单请求对象", dataType = "MsgMenuInsertReq", dataTypeClass = MsgMenuInsertReq.class, required = true)
    @PostMapping("/insert")
    public ResponseResult<Object> insert(@RequestBody @Validated MsgMenuInsertReq req) {
        log.info("MsgMenuController -> insert -> start -> param:{}", req);
        msgMenuService.insert(req);
        log.info("MsgMenuController -> insert -> end");
        return Result.ok();
    }

    /**
     * 修改消息菜单
     *
     * @param req
     * @return
     */
    @ApiOperation("修改消息菜单")
    @ApiImplicitParam(name = "req", value = "修改消息菜单请求对象", dataType = "MsgMenuUpdateReq", dataTypeClass = MsgMenuUpdateReq.class, required = true)
    @PutMapping("/update")
    public ResponseResult<Object> update(@RequestBody @Validated MsgMenuUpdateReq req) {
        log.info("MsgMenuController -> update -> start -> param:{}", req);
        msgMenuService.update(req);
        log.info("MsgMenuController -> update -> end");
        return Result.ok();
    }

    /**
     * 删除消息菜单
     *
     * @param req
     * @return
     */
    @ApiOperation("删除消息菜单")
    @ApiImplicitParam(name = "req", value = "删除消息菜单请求对象", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true)
    @DeleteMapping("/delete")
    public ResponseResult<Object> delete(@RequestBody @Validated BaseIdQuery req) {
        log.info("MsgMenuController -> delete -> start -> param:{}", req);
        msgMenuService.delete(req.getId());
        log.info("MsgMenuController -> delete -> end");
        return Result.ok();
    }

}
