package com.qidao.application.controller.member;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.member.subscribe.*;
import com.qidao.application.service.member.SubscribeService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 依次是 关注、屏蔽
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/14 3:48 PM
 */
@Api(tags = "关注or屏蔽")
@Slf4j
@RestController
@RequestMapping("/member/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @ApiOperation(value = "分页获取关注列表")
    @PostMapping("/getFollowList")
    @QiDaoPermission
    public ResponseResult<ServicePage<List<SubscribeRes>>> getFollowList(@RequestBody @Validated SubscribeGetFollowListReq req) {
        log.info("SubscribeController -> getFollowList -> SubscribeGetFollowListReq req : {}", req);
        ResponseResult<ServicePage<List<SubscribeRes>>> resp;
        SubscribeDTO dto = subscribeService.getFollowList(req);
        resp = Result.ok(dto.getResList());
        log.info("SubscribeController -> getFollowList -> Return -> ResponseResult<ServicePage<List<SubscribeRes>>>: {}", resp);
        return resp;
    }

    @OperLog(title = "添加单个关注",businessType = BusinessType.INSERT,isAsyncSaveToDB = true)
    @ApiOperation(value = "添加单个关注")
    @PostMapping("/addFollow")
    @QiDaoPermission
    public ResponseResult addFollow(@RequestBody @Validated SubscribeAddFollowReq req) {
        log.info("SubscribeController -> addFollow -> SubscribeAddFollowReq req : {}", req);
        SubscribeDTO dto = subscribeService.addFollow(req);
        log.info("SubscribeController -> addFollow -> Return -> dto -> {}", dto);

        boolean addFollowSuccess = dto.getSuccess();
        return addFollowSuccess ? Result.ok() : Result.fail(dto.getCodeMessageEnum());
    }

    @OperLog(title = "删除单个关注",businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除单个关注")
    @DeleteMapping("/deleteFollow")
    @QiDaoPermission
    public ResponseResult<SubscribeRes> deleteFollow(@RequestBody @Validated SubscribeDeleteFollowReq req) {
        log.info("SubscribeController -> deleteFollow -> SubscribeDeleteFollowReq req : {}", req);
        ResponseResult<SubscribeRes> resp;
        SubscribeDTO dto = subscribeService.deleteFollow(req);
        boolean deleteFollowSuccess = dto.getSuccess();
        log.info("SubscribeController -> deleteFollow -> boolean deleteFollowSuccess : {}", deleteFollowSuccess);
        if (deleteFollowSuccess) {
            resp = Result.ok(null);
            log.info("SubscribeController -> deleteFollow -> Return -> ResponseResult<SubscribeRes> : {}", resp);
            return resp;
        }
        resp = Result.fail(dto.getCodeMessageEnum());
        log.info("SubscribeController -> deleteFollow -> Return -> ResponseResult<SubscribeRes> : {}", resp);
        return resp;
    }

    @OperLog(title = "删除所有关注",businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除所有关注")
    @DeleteMapping("/deleteAllFollow")
    @QiDaoPermission
    public ResponseResult<SubscribeRes> deleteAllFollow(@RequestBody @Validated SubscribeDeleteAllFollowReq req) {
        log.info("SubscribeController -> deleteAllFollow -> SubscribeDeleteAllFollowReq req : {}", req);
        ResponseResult<SubscribeRes> resp;
        SubscribeDTO dto = subscribeService.deleteAllFollow(req);
        boolean deleteAllSuccess = dto.getSuccess();
        log.info("SubscribeController -> deleteAllFollow -> boolean deleteAllSuccess : {}", deleteAllSuccess);
        if (deleteAllSuccess) {
            resp = Result.ok(null);
            log.info("SubscribeController -> deleteAllFollow -> Return -> ResponseResult<SubscribeRes> : {}", resp);
            return resp;
        }
        resp = Result.fail(dto.getCodeMessageEnum());
        log.info("SubscribeController -> deleteAllFollow -> Return -> ResponseResult<SubscribeRes> : {}", resp);
        return resp;
    }

    @ApiOperation(value = "分页获取屏蔽列表")
    @PostMapping("/getBlockList")
    @QiDaoPermission
    public ResponseResult<ServicePage<List<SubscribeRes>>> getBlockList(@RequestBody @Validated SubscribeGetBlockListReq req) {
        log.info("SubscribeController -> getBlockList -> SubscribeGetBlockListReq req : {}", req);
        ResponseResult<ServicePage<List<SubscribeRes>>> resp;
        SubscribeDTO dto = subscribeService.getBlockList(req);
        resp = Result.ok(dto.getResList());
        log.info("SubscribeController -> getBlockList -> Return -> ResponseResult<ServicePage<List<SubscribeRes>>>: {}", resp);
        return resp;
    }

    @OperLog(title = "添加单个屏蔽",businessType = BusinessType.INSERT)
    @ApiOperation(value = "添加单个屏蔽")
    @PostMapping("/addBlock")
    @QiDaoPermission
    public ResponseResult<SubscribeRes> addBlock(@RequestBody @Validated SubscribeAddBlockReq req) {
        log.info("SubscribeController -> addBlock -> SubscribeAddBlockReq req : {}", req);
        ResponseResult<SubscribeRes> resp;
        SubscribeDTO dto = subscribeService.addBlock(req);
        boolean addBlockSuccess = dto.getSuccess();
        log.info("SubscribeController -> addBlock -> boolean addBlockSuccess : {}", addBlockSuccess);
        return  addBlockSuccess ? Result.ok() : Result.fail(dto.getCodeMessageEnum());
    }

    @OperLog(title = "删除单个屏蔽",businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除单个屏蔽")
    @DeleteMapping("/deleteBlock")
    @QiDaoPermission
    public ResponseResult<SubscribeRes> deleteBlock(@RequestBody @Validated SubscribeDeleteBlockReq req) {
        log.info("SubscribeController -> deleteBlock -> SubscribeDeleteBlockReq req : {}", req);
        ResponseResult<SubscribeRes> resp;
        SubscribeDTO dto = subscribeService.deleteBlock(req);
        boolean deleteBlockSuccess = dto.getSuccess();
        log.info("SubscribeController -> deleteBlock -> boolean deleteBlockSuccess : {}", deleteBlockSuccess);
        if (deleteBlockSuccess) {
            resp = Result.ok(null);
            log.info("SubscribeController -> deleteBlock -> Return -> ResponseResult<SubscribeRes> : {}", resp);
            return resp;
        }
        resp = Result.fail(dto.getCodeMessageEnum());
        log.info("SubscribeController -> deleteBlock -> Return -> ResponseResult<SubscribeRes> : {}", resp);
        return resp;
    }

    @Deprecated
    @OperLog(title = "关注组织机构",businessType = BusinessType.INSERT)
    @ApiOperation(value = "关注组织机构")
    @PostMapping("/attentionOrganization")
    @QiDaoPermission(accessLevel = 1)
    public ResponseResult attentionOrganization(@RequestBody @Validated AttentionOrganizationReq attentionOrganizationReq) {
        subscribeService.attentionOrganization(attentionOrganizationReq);
        return  Result.ok();
    }

    /**
     * 查询用户是否关注组织或个人
     */
    @ApiOperation(value = "查询用户是否关注组织或个人")
    @PostMapping("/findMemberWhetherAttention")
    @QiDaoPermission
    public  ResponseResult<Boolean>  findMemberWhetherAttention(@RequestBody @Validated  WhetherAttention whetherAttention){
        Boolean flag = subscribeService.findMemberWhetherAttention(whetherAttention.getMemberId(), whetherAttention.getType(), whetherAttention.getSubscribeId());
        return  Result.ok(flag);
    }
}
