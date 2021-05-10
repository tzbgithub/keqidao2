package com.qidao.application.controller.easemob;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.easemob.*;
import com.qidao.application.service.im.EasemobChartRoomService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/8 3:01 PM
 */
@Api(tags = "环信IM-聊天室管理")
@Slf4j
@RestController
@RequestMapping("/easemob/api/chatroom")
public class EasemobAPIChatRoomController {

    @Autowired
    private EasemobChartRoomService easemobChartRoomService;

    @PostMapping("/getAllChatRooms")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取APP中所有的聊天室", hidden = true)
    public ResponseResult<EasemobGetAllChatRoomsRes> getAllChatRooms() {
        log.info("EasemobAPIChatRoomController -> getAllChatRooms -> start");
        EasemobGetAllChatRoomsRes result = easemobChartRoomService.getAllChatRooms();
        log.info("EasemobAPIChatRoomController -> getAllChatRooms -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getJoinedChatRoomsByMemberName")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取用户加入的聊天室", hidden = true)
    public ResponseResult<EasemobGetJoinedChatRoomsByMemberNameRes> getJoinedChatRoomsByMemberName(@RequestBody @Validated EasemobGetJoinedChatRoomsByMemberNameReq req) {
        log.info("EasemobAPIChatRoomController -> getJoinedChatRoomsByMemberName -> start -> req -> {}", req);
        EasemobGetJoinedChatRoomsByMemberNameRes result = easemobChartRoomService.getJoinedChatRoomsByMemberName(req);
        log.info("EasemobAPIChatRoomController -> getJoinedChatRoomsByMemberName -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getChatRoomDetailById")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取聊天室详情", hidden = true)
    public ResponseResult<EasemobGetChatRoomDetailByIdRes> getChatRoomDetailById(@RequestBody @Validated EasemobGetChatRoomDetailByIdReq req) {
        log.info("EasemobAPIChatRoomController -> getChatRoomDetailById -> start -> req -> {}", req);
        EasemobGetChatRoomDetailByIdRes result = easemobChartRoomService.getChatRoomDetailById(req);
        log.info("EasemobAPIChatRoomController -> getChatRoomDetailById -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/createChatRoom")
    @OperLog(title = "创建一个聊天室", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "创建一个聊天室", hidden = true)
    public ResponseResult<EasemobCreateChatRoomRes> createChatRoom(@RequestBody @Validated EasemobCreateChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> createChatRoom -> start -> req -> {}", req);
        EasemobCreateChatRoomRes result = easemobChartRoomService.createChatRoom(req);
        log.info("EasemobAPIChatRoomController -> createChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PutMapping("/updateChatRoom")
    @OperLog(title = "修改聊天室信息", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "修改聊天室信息", hidden = true)
    public ResponseResult<EasemobUpdateChatRoomRes> updateChatRoom(@RequestBody @Validated EasemobUpdateChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> updateChatRoom -> start -> req -> {}", req);
        EasemobUpdateChatRoomRes result = easemobChartRoomService.updateChatRoom(req);
        log.info("EasemobAPIChatRoomController -> updateChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/deleteChatRoom")
    @OperLog(title = "删除聊天室", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "删除聊天室", hidden = true)
    public ResponseResult<EasemobDeleteChatRoomRes> deleteChatRoom(@RequestBody @Validated EasemobDeleteChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> deleteChatRoom -> start -> req -> {}", req);
        EasemobDeleteChatRoomRes result = easemobChartRoomService.deleteChatRoom(req);
        log.info("EasemobAPIChatRoomController -> deleteChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getChatRoomMembers")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "分页获取聊天室成员", hidden = true)
    public ResponseResult<EasemobGetChatRoomMembersRes> getChatRoomMembers(@RequestBody @Validated EasemobGetChatRoomMembersReq req) {
        log.info("EasemobAPIChatRoomController -> getChatRoomMembers -> start -> req -> {}", req);
        EasemobGetChatRoomMembersRes result = easemobChartRoomService.getChatRoomMembers(req);
        log.info("EasemobAPIChatRoomController -> getChatRoomMembers -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addMemberToChatRoom")
    @OperLog(title = "添加单个聊天室成员", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加单个聊天室成员", hidden = true)
    public ResponseResult<EasemobAddMemberToChatRoomRes> addMemberToChatRoom(@RequestBody @Validated EasemobAddMemberToChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> addMemberToChatRoom -> start -> req -> {}", req);
        EasemobAddMemberToChatRoomRes result = easemobChartRoomService.addMemberToChatRoom(req);
        log.info("EasemobAPIChatRoomController -> addMemberToChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addMemberToChatRoomBatch")
    @OperLog(title = "批量添加聊天室成员", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量添加聊天室成员", hidden = true)
    public ResponseResult<EasemobAddMemberToChatRoomBatchRes> addMemberToChatRoomBatch(@RequestBody @Validated EasemobAddMemberToChatRoomBatchReq req) {
        log.info("EasemobAPIChatRoomController -> addMemberToChatRoomBatch -> start -> req -> {}", req);
        EasemobAddMemberToChatRoomBatchRes result = easemobChartRoomService.addMemberToChatRoomBatch(req);
        log.info("EasemobAPIChatRoomController -> addMemberToChatRoomBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeMemberFromChatRoom")
    @OperLog(title = "删除单个聊天室成员", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "删除单个聊天室成员", hidden = true)
    public ResponseResult<EasemobRemoveMemberFromChatRoomRes> removeMemberFromChatRoom(@RequestBody @Validated EasemobRemoveMemberFromChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> removeMemberFromChatRoom -> start -> req -> {}", req);
        EasemobRemoveMemberFromChatRoomRes result = easemobChartRoomService.removeMemberFromChatRoom(req);
        log.info("EasemobAPIChatRoomController -> removeMemberFromChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeMemberFromChatRoomBatch")
    @OperLog(title = "批量删除聊天室成员", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量删除聊天室成员", hidden = true)
    public ResponseResult<EasemobRemoveMemberFromChatRoomBatchRes> removeMemberFromChatRoomBatch(@RequestBody @Validated EasemobRemoveMemberFromChatRoomBatchReq req) {
        log.info("EasemobAPIChatRoomController -> removeMemberFromChatRoomBatch -> start -> req -> {}", req);
        EasemobRemoveMemberFromChatRoomBatchRes result = easemobChartRoomService.removeMemberFromChatRoomBatch(req);
        log.info("EasemobAPIChatRoomController -> removeMemberFromChatRoomBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getChatRoomAdminMembers")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取聊天室管理员列表", hidden = true)
    public ResponseResult<EasemobGetChatRoomAdminMembersRes> getChatRoomAdminMembers(@RequestBody @Validated EasemobGetChatRoomAdminMembersReq req) {
        log.info("EasemobAPIChatRoomController -> getChatRoomAdminMembers -> start -> req -> {}", req);
        EasemobGetChatRoomAdminMembersRes result = easemobChartRoomService.getChatRoomAdminMembers(req);
        log.info("EasemobAPIChatRoomController -> getChatRoomAdminMembers -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addAdminMemberToChatRoom")
    @OperLog(title = "添加聊天室管理员", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加聊天室管理员", hidden = true)
    public ResponseResult<EasemobAddAdminMemberToChatRoomRes> addAdminMemberToChatRoom(@RequestBody @Validated EasemobAddAdminMemberToChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> addAdminMemberToChatRoom -> start -> req -> {}", req);
        EasemobAddAdminMemberToChatRoomRes result = easemobChartRoomService.addAdminMemberToChatRoom(req);
        log.info("EasemobAPIChatRoomController -> addAdminMemberToChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeAdminMemberFromChatRoom")
    @OperLog(title = "移除聊天室管理员", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "移除聊天室管理员", hidden = true)
    public ResponseResult<EasemobRemoveAdminMemberFromChatRoomRes> removeAdminMemberFromChatRoom(@RequestBody @Validated EasemobRemoveAdminMemberFromChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> removeAdminMemberFromChatRoom -> start -> req -> {}", req);
        EasemobRemoveAdminMemberFromChatRoomRes result = easemobChartRoomService.removeAdminMemberFromChatRoom(req);
        log.info("EasemobAPIChatRoomController -> removeAdminMemberFromChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getChatRoomMuteList")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取禁言列表", hidden = true)
    public ResponseResult<EasemobGetChatRoomMuteListRes> getChatRoomMuteList(@RequestBody @Validated EasemobGetChatRoomMuteListReq req) {
        log.info("EasemobAPIChatRoomController -> getChatRoomMuteList -> start -> req -> {}", req);
        EasemobGetChatRoomMuteListRes result = easemobChartRoomService.getChatRoomMuteList(req);
        log.info("EasemobAPIChatRoomController -> getChatRoomMuteList -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/setMemberMuteWithChatRoom")
    @OperLog(title = "添加禁言", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加禁言", hidden = true)
    public ResponseResult<EasemobSetMemberMuteWithChatRoomRes> setMemberMuteWithChatRoom(@RequestBody @Validated EasemobSetMemberMuteWithChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> setMemberMuteWithChatRoom -> start -> req -> {}", req);
        EasemobSetMemberMuteWithChatRoomRes result = easemobChartRoomService.setMemberMuteWithChatRoom(req);
        log.info("EasemobAPIChatRoomController -> setMemberMuteWithChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/cancelMemberMuteWithChatRoom")
    @OperLog(title = "移除禁言", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "移除禁言", hidden = true)
    public ResponseResult<EasemobCancelMemberMuteWithChatRoomRes> cancelMemberMuteWithChatRoom(@RequestBody @Validated EasemobCancelMemberMuteWithChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> cancelMemberMuteWithChatRoom -> start -> req -> {}", req);
        EasemobCancelMemberMuteWithChatRoomRes result = easemobChartRoomService.cancelMemberMuteWithChatRoom(req);
        log.info("EasemobAPIChatRoomController -> cancelMemberMuteWithChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getChatRoomSuperAdminList")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取超级管理员列表", hidden = true)
    public ResponseResult<EasemobGetChatRoomSuperAdminListRes> getChatRoomSuperAdminList(@RequestBody @Validated EasemobGetChatRoomSuperAdminListReq req) {
        log.info("EasemobAPIChatRoomController -> getChatRoomSuperAdminList -> start -> req -> {}", req);
        EasemobGetChatRoomSuperAdminListRes result = easemobChartRoomService.getChatRoomSuperAdminList(req);
        log.info("EasemobAPIChatRoomController -> getChatRoomSuperAdminList -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addSuperAdminToChatRoom")
    @OperLog(title = "添加超级管理员", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加超级管理员", hidden = true)
    public ResponseResult<EasemobAddSuperAdminToChatRoomRes> addSuperAdminToChatRoom(@RequestBody @Validated EasemobAddSuperAdminToChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> addSuperAdminToChatRoom -> start -> req -> {}", req);
        EasemobAddSuperAdminToChatRoomRes result = easemobChartRoomService.addSuperAdminToChatRoom(req);
        log.info("EasemobAPIChatRoomController -> addSuperAdminToChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeSuperAdminFromChatRoom")
    @OperLog(title = "移除超级管理员", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "移除超级管理员", hidden = true)
    public ResponseResult<EasemobRemoveSuperAdminFromChatRoomRes> removeSuperAdminFromChatRoom(@RequestBody @Validated EasemobRemoveSuperAdminFromChatRoomReq req) {
        log.info("EasemobAPIChatRoomController -> removeSuperAdminFromChatRoom -> start -> req -> {}", req);
        EasemobRemoveSuperAdminFromChatRoomRes result = easemobChartRoomService.removeSuperAdminFromChatRoom(req);
        log.info("EasemobAPIChatRoomController -> removeSuperAdminFromChatRoom -> end -> result -> {}", result);
        return Result.ok(result);
    }

}
