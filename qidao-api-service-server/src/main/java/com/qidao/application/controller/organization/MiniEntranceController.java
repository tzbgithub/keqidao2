package com.qidao.application.controller.organization;

import com.github.pagehelper.PageInfo;
import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.cos.UploadVideoRes;
import com.qidao.application.model.dto.*;
import com.qidao.application.model.organization.enums.OrganizaitonErrorEnum;
import com.qidao.application.service.member.impl.MemberServiceImpl;
import com.qidao.application.service.organization.impl.AchievementEquipmentServiceImpl;
import com.qidao.application.service.organization.impl.OrganizationServiceImpl;
import com.qidao.application.service.upload.impl.TxUploadServiceImpl;
import com.qidao.application.vo.AchievementListDto;
import com.qidao.application.vo.MemberDetailVo;
import com.qidao.application.vo.MemberVo;
import com.qidao.application.vo.SelectConfigResp;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/mp/miniEntrance")
@Api(value = "小程序后台入口", tags = "小程序后台入口")
public class MiniEntranceController {

    @Autowired
    OrganizationServiceImpl organizationService;
    @Autowired
    AchievementEquipmentServiceImpl achievementEquipmentService;

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    TxUploadServiceImpl txUploadService;

    @OperLog(title = "实验室入驻",businessType = BusinessType.INSERT)
    @ApiOperation(value="实验室入驻", notes="实验室入驻")
    @PostMapping(value = "/save")
    public ResponseResult<String> save(@RequestBody @Validated OrganizationDto organizationDto, HttpServletRequest request) {
        log.info("--- MiniEntranceController ---> save ---> : params={}-----",organizationDto);
        String save = organizationService.save(request, organizationDto, 0);
        return Result.ok(save);
    }


    @OperLog(title = "实验室入驻销售入口",businessType = BusinessType.INSERT)
    @ApiOperation(value="实验室入驻销售入口", notes="实验室入驻销售入口 (实验室入驻必须传入skillService,label参数)")
    @PostMapping(value = "/consoleSave")
    public ResponseResult<String> consoleSave(@RequestBody @Validated OrganizationDto organizationDto,HttpServletRequest request) {
        log.info("--- MiniEntranceController ---> consoleSave ---> : params={}-----",organizationDto);
        String save = organizationService.consoleSave(organizationDto, request, 0);
        return Result.ok(save);
    }

    @OperLog(title = "组织机构销毁",businessType = BusinessType.DELETE)
    @ApiOperation(value="组织机构销毁", notes="组织机构销毁")
    @DeleteMapping(value = "/delete")
    public ResponseResult<Organization> delete(@RequestBody  @Validated BaseIdQuery baseIdQuery){
        log.info("--- MiniEntranceController ---> delete ---> : params={}",baseIdQuery);
        int delete = organizationService.delete(baseIdQuery.getId());
        return delete>0 ? Result.ok() :  Result.fail(OrganizaitonErrorEnum.DELETE_ORGANIZATION_ERROR);

    }
    @ApiOperation(value="所属单位查询实验室", notes="所属单位查询实验室")
    @PostMapping(value = "/findByBelong")
    public ResponseResult findOrganizationByBelong(@RequestBody @Validated BeLongDto beLongDto){
        log.info("--- MiniEntranceController ---> findByBelong ---> : params={}",beLongDto);
        ArrayList<Map> result = organizationService.findOrganizationByBelong(beLongDto.getBelong(), 0);
        return Result.ok(result);
    }


    @ApiOperation(value="查询实验室详情", notes="查询实验室详情")
    @PostMapping(value = "/findByOrganizationId")
    public ResponseResult<ReturnOrganizationDto> findByOrganizationId(@RequestBody  @Validated BaseIdQuery baseIdQuery){
        log.info("--- OrganizationController ---> findByOrganizationId ---> : params={}",baseIdQuery);
        ReturnOrganizationDto result = organizationService.findByOrganizationId(baseIdQuery.getId());
        return Result.ok(result);
    }


    @OperLog(title = "设备/成果发布",businessType = BusinessType.INSERT)
    @ApiOperation(value="设备发布", notes="设备发布")@PostMapping(value = "/saveEquipment")
    @QiDaoPermission
    public ResponseResult saveEquipment(@RequestBody  @Validated AchievementEquipmentDto achievementEquipmentDto)
    {    log.info("--- MiniEntranceController ---> save ---> : params={}-----",achievementEquipmentDto);
        String result = achievementEquipmentService.save(achievementEquipmentDto);
        return Result.ok(result);
    }

    @OperLog(title = "逻辑删除成果/设备", businessType = BusinessType.INSERT)
    @ApiOperation(value = "逻辑删除成果/设备", notes = "逻辑删除成果/设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = false, dataType = "int", dataTypeClass = int.class, example = "391235115570827264")
    })
    @DeleteMapping(value = "/updateFlag")
    public ResponseResult<String> updateFlag(@RequestParam(value = "id") Long id) {
        log.info("--- MiniEntranceController ---> updateFlag ---> : id={}-----", id);
        String result = achievementEquipmentService.updateFlag(id);
        return Result.ok(result);
    }

    @ApiOperation(value="实验室成果展示", notes="实验室成果展示")
    @PostMapping(value = "/findAchievementPage")
    public PageInfo<AchievementListDto> findAchievementPage(@RequestBody  @Validated AchievementPageDto achievementPageDto) {
        log.info("--- MiniEntranceController ---> findAchievementPage ---> params{}: ",achievementPageDto);
        return achievementEquipmentService.findAchievementPage(achievementPageDto.getOffset(),
                achievementPageDto.getLimit(),achievementPageDto.getOrganizationId(),achievementPageDto.getType());
    }


    /**
     * 查询设备/成果详情
     */
    @ApiOperation(value="查询设备/成果详情", notes="查询设备/成果详情")
    @PostMapping(value = "/findAchievementDetail")
    public  ResponseResult  findAchievementDetail(@RequestBody @Validated BaseIdQuery baseIdQuery){
        log.info("--- AchievementEquipmentContorller ---> findAchievementDetail ---> : params={}-----",baseIdQuery);
        AchievementListDto achievementDetail = achievementEquipmentService.findAchievementDetail(baseIdQuery.getId());
        return Result.ok(achievementDetail);
    }


    @ApiOperation(value = "腾讯云上传图片", notes = "腾讯云上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "images", value = "图片", required = true, allowMultiple = true, paramType = "query", dataType = "file", dataTypeClass = MultipartFile[].class)
    })
    @PostMapping(value = "/sendTxImage")
    public ResponseResult<String> uploadPicture(@RequestParam(value = "images") MultipartFile[] multipartFiles) throws IOException {

        log.info("--- MiniEntranceController ---> uploadPicture ---> : multipartFile={}", multipartFiles);
        if (multipartFiles.length <= 0) {
            return Result.fail("请上传图片");
        }
        String result = txUploadService.uploadImage(multipartFiles[0]);
        return Result.ok(result);
    }

    @ApiOperation(value = "腾讯云上传视频", notes = "腾讯云上传视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "video", value = "视频文件", required = true, allowMultiple = true, paramType = "query", dataType = "file", dataTypeClass = MultipartFile[].class)
    })
    @PostMapping(value = "/uploadVideo")

    public ResponseResult<UploadVideoRes> uploadVedio(@RequestParam(value = "video") MultipartFile[] video) throws IOException {
        if (video.length <= 0) {
            return Result.fail("请上传视频");
        }
        log.info("--- MiniEntranceController ---> uploadVedio ---> : multipartFile={}", video);
        UploadVideoRes uploadVideoRes = txUploadService.uploadVideo(video[0]);
        return Result.ok(uploadVideoRes);

    }

    /**
     * 根据union查询实验室信息  如果有返回没有返回空串
     */

    @ApiOperation(value = "unionId查询实验室和会员")
    @PostMapping("/getOrganizationByUnionId")
    public ResponseResult<MemberVo> getOrganizationByUnionId(@RequestBody @Validated BeLongDto beLongDto){
        log.info("--- MiniEntranceController ---> getOrganizationByUnionId ----> params:{} -----",beLongDto);
        Member memberInfo = memberService.getOrganizationByUnionId(beLongDto.getUnionId());
        if(memberInfo==null){
            return  Result.fail("未查询到对应用户");
        }
        MemberVo memberMessage = memberService.findMemberMessage(memberInfo.getId());
        return Result.ok(memberMessage);
    }

    /**
     * 查询用户名片
     */
    @PostMapping(value = "/findMemberMessage")
    @ApiOperation(value = "查询用户名片")
    public  ResponseResult<MemberVo>  findMemberMessage(@RequestBody   @Validated BaseIdQuery baseIdQuery){
        log.info("--- MiniEntranceController ---> findMemberMessage ----> 查询用户名片 params:{} -----",baseIdQuery);
        MemberVo memberMessage = memberService.findMemberMessage(baseIdQuery.getId());
        return Result.ok(memberMessage);
    }

    /**
     * 查询用户详情
     */
    @PostMapping(value = "/findMemberMessageDetail")
    @ApiOperation(value = "查询用户详情")
    public  ResponseResult<MemberDetailVo>  findMemberMessageDetail(@RequestBody @Validated BaseIdQuery baseIdQuery){
        log.info("--- MiniEntranceController ---> findMemberMessageDetail ----> 查询用户详情 params:{} -----",baseIdQuery);
        MemberDetailVo result = memberService.findMemberMessageDetail(baseIdQuery.getId());
        return Result.ok(result);
    }

    /**
     * 修改实验室信息
     */
    @PostMapping(value = "/updateOrganization")
    @ApiOperation(value = "修改实验室信息")
    public  ResponseResult<String>  updateOrganization(@RequestBody  @Validated UpdateOriganizationDto updateOriganizationDto){
        log.info("--- MiniEntranceController ---> updateOrganization ----> 修改实验室信息 params:{} -----",updateOriganizationDto);
        organizationService.update(updateOriganizationDto);
        return Result.ok("更新成功");
    }


    /**
     * 修改用户个人信息
     */
    @PostMapping(value = "/updateMemberMessage")
    @ApiOperation(value = "修改用户个人信息")
    public  ResponseResult<String>  updateMemberMessage(@RequestBody @Validated MemberDetailVo memberDetailVo){
        log.info("--- MiniEntranceController ---> updateMemberMessage ----> 修改用户个人信息 params:{} -----",memberDetailVo);
        memberService.updateMemberMessage(memberDetailVo);
        return Result.ok("更新成功");
    }

    /**
     * 查询成果发布标题栏
     */
    @PostMapping(value = "/findSelectConfigByOrganization")
    @ApiOperation(value = "根据实验室标识查询成果发布标题栏")
    public  ResponseResult<List<SelectConfigResp>>  findSelectConfigByOrganization(@RequestBody @Validated BaseIdQuery baseIdQuery){
        List<SelectConfigResp> result = memberService.findSelectConfigByOrganization(baseIdQuery.getId());
        return Result.ok(result);
    }
}
