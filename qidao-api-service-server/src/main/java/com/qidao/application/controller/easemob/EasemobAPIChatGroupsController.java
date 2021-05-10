package com.qidao.application.controller.easemob;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.easemob.*;
import com.qidao.application.service.im.EasemobGroupService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/15 11:23 AM
 */
@Api(tags = "环信IM-群组管理")
@Slf4j
@RestController
@RequestMapping("/easemob/api/chatgroups")
public class EasemobAPIChatGroupsController {

    @Autowired
    private EasemobGroupService easemobGroupService;

    @PostMapping("/getChatGroups")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取应用下全部的群组信息", hidden = true)
    public ResponseResult<EasemobGetChatGroupsRes> getChatGroups(@RequestBody @Validated EasemobGetChatGroupsReq req) {
        log.info("EasemobAPIChatGroupsController -> getChatGroups -> start -> req -> {}", req);
        EasemobGetChatGroupsRes result = easemobGroupService.getChatGroups(req);
        log.info("EasemobAPIChatGroupsController -> getChatGroups -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getJoinedChatGroups")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取一个用户参与的所有群组", hidden = true)
    public ResponseResult<EasemobGetJoinedChatGroupsRes> getJoinedChatGroups(@RequestBody @Validated EasemobGetJoinedChatGroupsReq req) {
        log.info("EasemobAPIChatGroupsController -> getJoinedChatGroups -> start -> req -> {}", req);
        EasemobGetJoinedChatGroupsRes result = easemobGroupService.getJoinedChatGroups(req);
        log.info("EasemobAPIChatGroupsController -> getJoinedChatGroups -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getGroupDetailById")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "根据群组ID获取此群组的详情", hidden = true)
    public ResponseResult<EasemobGetGroupDetailByIdRes> getGroupDetailById(@RequestBody @Validated EasemobGetGroupDetailByIdReq req) {
        log.info("EasemobAPIChatGroupsController -> getGroupDetailById -> start -> req -> {}", req);
        EasemobGetGroupDetailByIdRes result = easemobGroupService.getGroupDetailById(req);
        log.info("EasemobAPIChatGroupsController -> getGroupDetailById -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/createGroup")
    @OperLog(title = "创建一个新群组", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "创建一个新群组", hidden = true)
    public ResponseResult<EasemobCreateGroupRes> createGroup(@RequestBody @Validated EasemobCreateGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> createGroup -> start -> req -> {}", req);
        EasemobCreateGroupRes result = easemobGroupService.createGroup(req);
        log.info("EasemobAPIChatGroupsController -> createGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PatchMapping("/updateGroup")
    @OperLog(title = "修改群组的部分信息", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "修改群组的部分信息", hidden = true)
    public ResponseResult<EasemobUpdateGroupRes> updateGroup(@RequestBody @Validated EasemobUpdateGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> updateGroup -> start -> req -> {}", req);
        EasemobUpdateGroupRes result = easemobGroupService.updateGroup(req);
        log.info("EasemobAPIChatGroupsController -> updateGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/deleteGroup")
    @OperLog(title = "删除一个群组", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "删除一个群组", hidden = true)
    public ResponseResult<EasemobDeleteGroupRes> deleteGroup(@RequestBody @Validated EasemobDeleteGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> deleteGroup -> start -> req -> {}", req);
        EasemobDeleteGroupRes result = easemobGroupService.deleteGroup(req);
        log.info("EasemobAPIChatGroupsController -> deleteGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getGroupAnnouncement")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取群组公告", hidden = true)
    public ResponseResult<EasemobGetGroupAnnouncementRes> getGroupAnnouncement(@RequestBody @Validated EasemobGetGroupAnnouncementReq req) {
        log.info("EasemobAPIChatGroupsController -> getGroupAnnouncement -> start -> req -> {}", req);
        EasemobGetGroupAnnouncementRes result = easemobGroupService.getGroupAnnouncement(req);
        log.info("EasemobAPIChatGroupsController -> getGroupAnnouncement -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/updateGroupAnnouncement")
    @OperLog(title = "删除一个群组", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "修改群组公告", hidden = true)
    public ResponseResult<EasemobUpdateGroupAnnouncementRes> updateGroupAnnouncement(@RequestBody @Validated EasemobUpdateGroupAnnouncementReq req) {
        log.info("EasemobAPIChatGroupsController -> updateGroupAnnouncement -> start -> req -> {}", req);
        EasemobUpdateGroupAnnouncementRes result = easemobGroupService.updateGroupAnnouncement(req);
        log.info("EasemobAPIChatGroupsController -> updateGroupAnnouncement -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getGroupMembers")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "分页获取群组成员", hidden = true)
    public ResponseResult<EasemobGetGroupMembersRes> getGroupMembers(@RequestBody @Validated EasemobGetGroupMembersReq req) {
        log.info("EasemobAPIChatGroupsController -> getGroupMembers -> start -> req -> {}", req);
        EasemobGetGroupMembersRes result = easemobGroupService.getGroupMembers(req);
        log.info("EasemobAPIChatGroupsController -> getGroupMembers -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addMemberToGroup")
    @OperLog(title = "添加单个群组成员", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加单个群组成员", hidden = true)
    public ResponseResult<EasemobAddMemberToGroupRes> addMemberToGroup(@RequestBody @Validated EasemobAddMemberToGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> addMemberToGroup -> start -> req -> {}", req);
        EasemobAddMemberToGroupRes result = easemobGroupService.addMemberToGroup(req);
        log.info("EasemobAPIChatGroupsController -> addMemberToGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addMemberToGroupBatch")
    @OperLog(title = "批量添加群组成员", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量添加群组成员", hidden = true)
    public ResponseResult<EasemobAddMemberToGroupBatchRes> addMemberToGroupBatch(@RequestBody @Validated EasemobAddMemberToGroupBatchReq req) {
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupBatch -> start -> req -> {}", req);
        EasemobAddMemberToGroupBatchRes result = easemobGroupService.addMemberToGroupBatch(req);
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeMemberFromGroup")
    @OperLog(title = "移除单个群组成员", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "移除单个群组成员", hidden = true)
    public ResponseResult<EasemobRemoveMemberFromGroupRes> removeMemberFromGroup(@RequestBody @Validated EasemobRemoveMemberFromGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroup -> start -> req -> {}", req);
        EasemobRemoveMemberFromGroupRes result = easemobGroupService.removeMemberFromGroup(req);
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeMemberFromGroupBatch")
    @OperLog(title = "批量移除群组成员", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量移除群组成员", hidden = true)
    public ResponseResult<EasemobRemoveMemberFromGroupBatchRes> removeMemberFromGroupBatch(@RequestBody @Validated EasemobRemoveMemberFromGroupBatchReq req) {
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupBatch -> start -> req -> {}", req);
        EasemobRemoveMemberFromGroupBatchRes result = easemobGroupService.removeMemberFromGroupBatch(req);
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getGroupAdminMembers")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取群管理员列表", hidden = true)
    public ResponseResult<EasemobGetGroupAdminMembersRes> getGroupAdminMembers(@RequestBody @Validated EasemobGetGroupAdminMembersReq req) {
        log.info("EasemobAPIChatGroupsController -> getGroupAdminMembers -> start -> req -> {}", req);
        EasemobGetGroupAdminMembersRes result = easemobGroupService.getGroupAdminMembers(req);
        log.info("EasemobAPIChatGroupsController -> getGroupAdminMembers -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addAdminMemberToGroup")
    @OperLog(title = "添加群管理员", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加群管理员", hidden = true)
    public ResponseResult<EasemobAddAdminMemberToGroupRes> addAdminMemberToGroup(@RequestBody @Validated EasemobAddAdminMemberToGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> addAdminMemberToGroup -> start -> req -> {}", req);
        EasemobAddAdminMemberToGroupRes result = easemobGroupService.addAdminMemberToGroup(req);
        log.info("EasemobAPIChatGroupsController -> addAdminMemberToGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeAdminMemberFromGroup")
    @OperLog(title = "移除群管理员", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "移除群管理员", hidden = true)
    public ResponseResult<EasemobRemoveAdminMemberFromGroupRes> removeAdminMemberFromGroup(@RequestBody @Validated EasemobRemoveAdminMemberFromGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> removeAdminMemberFromGroup -> start -> req -> {}", req);
        EasemobRemoveAdminMemberFromGroupRes result = easemobGroupService.removeAdminMemberFromGroup(req);
        log.info("EasemobAPIChatGroupsController -> removeAdminMemberFromGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PutMapping("/transferGroup")
    @OperLog(title = "转让群组", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "转让群组", hidden = true)
    public ResponseResult<EasemobTransferGroupRes> transferGroup(@RequestBody @Validated EasemobTransferGroupReq req) {
        log.info("EasemobAPIChatGroupsController -> transferGroup -> start -> req -> {}", req);
        EasemobTransferGroupRes result = easemobGroupService.transferGroup(req);
        log.info("EasemobAPIChatGroupsController -> transferGroup -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getGroupBlockList")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "查询群组黑名单", hidden = true)
    public ResponseResult<EasemobGetGroupBlockListRes> getGroupBlockList(@RequestBody @Validated EasemobGetGroupBlockListReq req) {
        log.info("EasemobAPIChatGroupsController -> getGroupBlockList -> start -> req -> {}", req);
        EasemobGetGroupBlockListRes result = easemobGroupService.getGroupBlockList(req);
        log.info("EasemobAPIChatGroupsController -> getGroupBlockList -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addMemberToGroupBlockList")
    @OperLog(title = "添加单个用户至群组黑名单", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加单个用户至群组黑名单", hidden = true)
    public ResponseResult<EasemobAddMemberToGroupBlockListRes> addMemberToGroupBlockList(@RequestBody @Validated EasemobAddMemberToGroupBlockListReq req) {
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupBlockList -> start -> req -> {}", req);
        EasemobAddMemberToGroupBlockListRes result = easemobGroupService.addMemberToGroupBlockList(req);
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupBlockList -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addMemberToGroupBlockListBatch")
    @OperLog(title = "批量添加用户至群组黑名单", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量添加用户至群组黑名单", hidden = true)
    public ResponseResult<EasemobAddMemberToGroupBlockListBatchRes> addMemberToGroupBlockListBatch(@RequestBody @Validated EasemobAddMemberToGroupBlockListBatchReq req) {
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupBlockListBatch -> start -> req -> {}", req);
        EasemobAddMemberToGroupBlockListBatchRes result = easemobGroupService.addMemberToGroupBlockListBatch(req);
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupBlockListBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeMemberFromGroupBlockList")
    @OperLog(title = "从群组黑名单移除单个用户", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "从群组黑名单移除单个用户", hidden = true)
    public ResponseResult<EasemobRemoveMemberFromGroupBlockListRes> removeMemberFromGroupBlockList(@RequestBody @Validated EasemobRemoveMemberFromGroupBlockListReq req) {
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupBlockList -> start -> req -> {}", req);
        EasemobRemoveMemberFromGroupBlockListRes result = easemobGroupService.removeMemberFromGroupBlockList(req);
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupBlockList -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeMemberFromGroupBlockListBatch")
    @OperLog(title = "批量从群组黑名单移除用户", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量从群组黑名单移除用户", hidden = true)
    public ResponseResult<EasemobRemoveMemberFromGroupBlockListBatchRes> removeMemberFromGroupBlockListBatch(@RequestBody @Validated EasemobRemoveMemberFromGroupBlockListBatchReq req) {
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupBlockListBatch -> start -> req -> {}", req);
        EasemobRemoveMemberFromGroupBlockListBatchRes result = easemobGroupService.removeMemberFromGroupBlockListBatch(req);
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupBlockListBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/addMemberToGroupMuteList")
    @OperLog(title = "添加用户至群组的禁言列表", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "添加用户至群组的禁言列表", hidden = true)
    public ResponseResult<EasemobAddMemberToGroupMuteListRes> addMemberToGroupMuteList(@RequestBody @Validated EasemobAddMemberToGroupMuteListReq req) {
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupMuteList -> start -> req -> {}", req);
        EasemobAddMemberToGroupMuteListRes result = easemobGroupService.addMemberToGroupMuteList(req);
        log.info("EasemobAPIChatGroupsController -> addMemberToGroupMuteList -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @DeleteMapping("/removeMemberFromGroupMuteList")
    @OperLog(title = "从群组的禁言列表中移除用户", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "从群组的禁言列表中移除用户", hidden = true)
    public ResponseResult<EasemobRemoveMemberFromGroupMuteListRes> removeMemberFromGroupMuteList(@RequestBody @Validated EasemobRemoveMemberFromGroupMuteListReq req) {
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupMuteList -> start -> req -> {}", req);
        EasemobRemoveMemberFromGroupMuteListRes result = easemobGroupService.removeMemberFromGroupMuteList(req);
        log.info("EasemobAPIChatGroupsController -> removeMemberFromGroupMuteList -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getGroupMuteList")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取禁言列表", hidden = true)
    public ResponseResult<EasemobGetGroupMuteListRes> getGroupMuteList(@RequestBody @Validated EasemobGetGroupMuteListReq req) {
        log.info("EasemobAPIChatGroupsController -> getGroupMuteList -> start -> req -> {}", req);
        EasemobGetGroupMuteListRes result = easemobGroupService.getGroupMuteList(req);
        log.info("EasemobAPIChatGroupsController -> getGroupMuteList -> end -> result -> {}", result);
        return Result.ok(result);
    }
}
