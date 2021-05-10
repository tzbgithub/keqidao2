package com.qidao.application.controller.dynamic.channel;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.common.req.BasePageQuery;
import com.qidao.application.model.dynamic.channel.ChannelAddReq;
import com.qidao.application.model.dynamic.channel.ChannelQuery;
import com.qidao.application.model.dynamic.channel.ChannelRes;
import com.qidao.application.model.dynamic.channel.ChannelUpdateReq;
import com.qidao.application.service.dynamic.channel.ChannelService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xinfeng
 * @create 2021-01-29 13:38
 */
@Api(tags = "频道")
@Slf4j
@RestController
@RequestMapping("/dynamic/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @PostMapping("/insert")
    @ApiOperation(value = "频道新增")
    @OperLog(title = "频道新增", businessType = BusinessType.INSERT)
    public ResponseResult<Boolean> insert(@RequestBody @Validated ChannelAddReq channelAddReq) {
        log.info("ChannelController -> insert -> start -> channelAddReq : {}", channelAddReq);
        return Result.ok(channelService.insert(channelAddReq));
    }

    @PutMapping("/update")
    @ApiOperation(value = "频道更新")
    @OperLog(title = "频道更新", businessType = BusinessType.UPDATE)
    public ResponseResult<Boolean> update(@RequestBody @Validated ChannelUpdateReq channelUpdateReq) {
        log.info("ChannelController -> update -> start -> channelUpdateReq : {}", channelUpdateReq);
        return Result.ok(channelService.update(channelUpdateReq));
    }

    @PostMapping("/getList")
    @ApiOperation("查询所有频道列表")
    public ResponseResult<ServicePage<List<ChannelRes>>> getList(@RequestBody @Validated BasePageQuery basePageQuery) {
        log.info("ChannelController -> getList -> start -> channelQuery : {}", basePageQuery);
        return Result.ok(channelService.getList(basePageQuery));
    }

    @PostMapping("/getListByName")
    @ApiOperation("根据频道名查询频道列表")
    public ResponseResult<ServicePage<List<ChannelRes>>> getListByName(@RequestBody @Validated ChannelQuery channelQuery) {
        log.info("ChannelController -> getListByName -> start -> channelQuery : {}", channelQuery);
        return Result.ok(channelService.getListByName(channelQuery));
    }

    @DeleteMapping("deleteById")
    @ApiOperation(value = "根据ID删除频道")
    @QiDaoPermission(accessLevel = 5)
    @OperLog(title = "根据ID删除频道", businessType = BusinessType.DELETE)
    public ResponseResult<Boolean> deleteById(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("ChannelController -> deleteById -> start -> baseIdQuery : {}", baseIdQuery);
        return Result.ok(channelService.deleteById(baseIdQuery));
    }
}
