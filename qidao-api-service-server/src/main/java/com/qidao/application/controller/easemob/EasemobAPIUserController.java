package com.qidao.application.controller.easemob;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.easemob.*;
import com.qidao.application.model.easemob.enums.EasemobExceptionEnum;
import com.qidao.application.service.im.EasemobUserService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@Api(tags = "环信IM-用户体系集成")
@Slf4j
@RestController
@RequestMapping("/easemob/api/user")
public class EasemobAPIUserController {
    @Autowired
    private EasemobUserService easemobUserService;

    @PostMapping("/addFriend")
    @ApiOperation(value = "环信添加好友")
    @OperLog(title = "环信添加好友", businessType = BusinessType.INSERT)
    public ResponseResult<EasemobFriendRes> addFriend(@RequestBody @Validated EasemobAddFriendReq req) {
        log.info("EasemobAPIUserController -> addFriend -> {}", req);
        // 判断 ： 不能对自己进行操作
        if (req.getFriendMemberId().equals(req.getMemberId())) {
            return Result.fail(EasemobExceptionEnum.SELF);
        }
        EasemobFriendRes user = easemobUserService.registerAndAddFriend(req);
        return Result.ok(user);
    }

    /**
     * 移除好友
     * 暂时不考虑异常情况(重复删除、删除不存在的用户等)
     *
     * @param req
     * @return
     */
    @DeleteMapping("/removeFriend")
    @OperLog(title = "移除好友", businessType = BusinessType.DELETE)
    @ApiOperation(value = "移除好友")
    public ResponseResult<EasemobBaseDTO> removeFriend(@RequestBody @Validated EasemobRemoveFriendReq req) {
        log.info("EasemobAPIUserController -> removeFriend -> start -> req -> {}", req);
        EasemobBaseDTO result = easemobUserService.removeFriend(req);
        log.info("EasemobAPIUserController -> removeFriend -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getFriendList")
    @ApiOperation(value = "环信获取好友列表", notes = "响应结果是 List<String> 例： ['1234','123']")
    public ResponseResult<List<EasemobFriendRes>> getFriendList(@RequestBody @Validated EasemobFriendListReq req) {
        List<EasemobFriendRes> list = easemobUserService.getFriendList(req.getMemberId());
        return Result.ok(list);
    }

    /**
     * 注册单个用户，不可重复注册
     * 暂时不考虑异常情况(重复注册等)
     *
     * @param req
     * @return
     */
    @PostMapping("/register")
    @OperLog(title = "注册单个用户(授权)", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "注册单个用户(授权)", hidden = true)
    public ResponseResult<EasemobBaseDTO> register(@RequestBody @Validated EasemobRegisterReq req) {
        log.info("EasemobAPIUserController -> register -> start -> req -> {}", req);
        EasemobBaseDTO result = easemobUserService.register(req);
        log.info("EasemobAPIUserController -> register -> end -> result -> {}", result);
        return Result.ok(result);
    }

    /**
     * 批量注册用户，不可重复注册
     * 暂时不考虑异常情况(重复注册等)
     *
     * @param req
     * @return
     */
    @PostMapping("/registerBatch")
    @OperLog(title = "批量注册用户(授权)", businessType = BusinessType.INSERT)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量注册用户(授权)", hidden = true)
    public ResponseResult<List<EasemobBaseDTO>> registerBatch(@RequestBody @Validated EasemobRegisterBatchReq req) {
        log.info("EasemobAPIUserController -> registerBatch -> start -> req -> {}", req);
        List<EasemobBaseDTO> result = easemobUserService.registerBatch(req);
        log.info("EasemobAPIUserController -> registerBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }


    /**
     * 批量获取用户
     *
     * @param req
     * @return
     */
    @PostMapping("/getUserBatch")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量获取用户", hidden = true)
    public ResponseResult<List<EasemobBaseDTO>> getUserBatch(@RequestBody @Validated EasemobGetUserBatchReq req) {
        log.info("EasemobAPIUserController -> getUserBatch -> start -> req -> {}", req);
        List<EasemobBaseDTO> result = easemobUserService.getUserBatch(req);
        log.info("EasemobAPIUserController -> getUserBatch -> end -> result -> {}", result);
        return Result.ok(result);
    }

    /**
     * 删除单个用户，不可重复删除
     * 暂时不考虑异常情况(重复删除、删除不存在的用户等)
     *
     * @param req
     * @return
     */
    @DeleteMapping("/deleteUser")
    @OperLog(title = "删除单个用户", businessType = BusinessType.DELETE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "删除单个用户", hidden = true)
    public ResponseResult<EasemobBaseDTO> deleteUser(@RequestBody @Validated EasemobDeleteUserReq req) {
        log.info("EasemobAPIUserController -> deleteUser -> start -> req -> {}", req);
        EasemobBaseDTO result = easemobUserService.deleteUser(String.valueOf(req.getUsername()));
        log.info("EasemobAPIUserController -> deleteUser -> end -> result -> {}", result);
        return Result.ok(result);
    }

    /**
     * 修改用户密码
     * 暂时不考虑异常情况(修改不存在的用户的密码等)
     *
     * @param req
     * @return
     */
    @PutMapping("/updatePwd")
    @OperLog(title = "修改用户密码", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "修改用户密码", hidden = true)
    public ResponseResult<Boolean> updatePwd(@RequestBody @Validated EasemobUpdatePwdReq req) {
        log.info("EasemobAPIUserController -> updatePwd -> start -> req -> {}", req);
        EasemobUpdatePwdDTO dto = easemobUserService.updatePwd(req);
        boolean updateSuccess = dto.getUpdateSuccess();
        log.info("EasemobAPIUserController -> updatePwd -> end -> updateSuccess -> {}", updateSuccess);
        return Result.ok(updateSuccess);
    }

    /**
     * 设置推送昵称
     * 暂时不考虑异常情况(设置不存在的用户的昵称等)
     *
     * @param req
     * @return
     */
    @PutMapping("/updateNickname")
    @OperLog(title = "设置推送昵称", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "设置推送昵称", hidden = true)
    public ResponseResult<EasemobBaseDTO> updateNickname(@RequestBody @Validated EasemobUpdateNicknameReq req) {
        log.info("EasemobAPIUserController -> updateNickname -> start -> req -> {}", req);
        EasemobBaseDTO result = easemobUserService.updateNickName(req);
        log.info("EasemobAPIUserController -> updateNickname -> end -> result -> {}", result);
        return Result.ok(result);
    }

    /**
     * 设置推送消息展示方式
     * 暂时不考虑异常情况(用户不存在等情况)
     *
     * @param req
     * @return
     */
    @PutMapping("/notifyShowType")
    @OperLog(title = "设置推送消息展示方式", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "设置推送消息展示方式", hidden = true)
    public ResponseResult<EasemobBaseDTO> notifyShowType(@RequestBody @Validated EasemobNotifyShowTypeReq req) {
        log.info("EasemobAPIUserController -> notifyShowType -> start -> req -> {}", req);
        EasemobBaseDTO result = easemobUserService.notifyShowType(req);
        log.info("EasemobAPIUserController -> notifyShowType -> end -> result -> {}", result);
        return Result.ok(result);
    }

    /**
     * 设置免打扰
     * 暂时不考虑异常情况(用户不存在等情况)
     *
     * @param req
     * @return
     */
    @PutMapping("/setNotDisturb")
    @OperLog(title = "设置免打扰", businessType = BusinessType.UPDATE)
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "设置免打扰", hidden = true)
    public ResponseResult<EasemobBaseDTO> setNotDisturb(@RequestBody @Validated EasemobSetNotDisturbReq req) {
        log.info("EasemobAPIUserController -> setNotDisturb -> start -> req -> {}", req);
        EasemobBaseDTO result = easemobUserService.setNotDisturb(req);
        log.info("EasemobAPIUserController -> setNotDisturb -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/statusOne")
    @ApiOperation(value = "查看用户是否在线")
    public ResponseResult<EasemobStatusRes> statusOne(@RequestBody @Validated EasemobStatusReq req) {
        log.info("EasemobAPIUserController -> updatePwd -> start -> req -> {}", req);
        EasemobStatusRes result = easemobUserService.statusOne(req.getUsername());
        log.info("EasemobAPIUserController -> updatePwd -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/statusBatch")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "批量获取用户在线状态", hidden = true)
    public ResponseResult<List<EasemobStatusRes>> statusBatch(@RequestBody @Validated EasemobStatusBatchReq req) {
        log.info("EasemobAPIUserController -> updatePwd -> start -> req -> {}", req);
        List<EasemobStatusRes> result = easemobUserService.statusBatch(req);
        log.info("EasemobAPIUserController -> updatePwd -> end -> result -> {}", result);
        return Result.ok(result);
    }

    @PostMapping("/getOfflineMsgCount")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取离线消息数", hidden = true)
    public ResponseResult<List<EasemobOfflineCountRes>> getOfflineMsgCount(@RequestBody @Validated EasemobStatusReq req) {
        log.info("EasemobAPIUserController -> updatePwd -> start -> req -> {}", req);
        List<EasemobOfflineCountRes> result = easemobUserService.getOfflineMsgCount(req.getUsername());
        log.info("EasemobAPIUserController -> updatePwd -> end -> result -> {}", result);
        return Result.ok(result);
    }


    @PostMapping("/getOfflineMsgStatus")
    @QiDaoPermission(accessLevel = 3)
    @ApiOperation(value = "获取离线消息的状态", hidden = true)
    public ResponseResult<Object> getOfflineMsgStatus(@RequestBody @Validated EasemobUserOffLineMsgStatusReq req) {
        log.info("EasemobAPIUserController -> updatePwd -> start -> req -> {}", req);
        Object result = easemobUserService.getOfflineMsgStatus(req);
        log.info("EasemobAPIUserController -> updatePwd -> end -> result -> {}", result);
        return Result.ok(result);
    }
}
