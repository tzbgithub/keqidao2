package com.qidao.application.controller.organization;

import com.github.pagehelper.PageInfo;
import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.dto.AchievementEquipmentDto;
import com.qidao.application.service.member.impl.MemberServiceImpl;
import com.qidao.application.vo.AchievementListDto;
import com.qidao.application.model.dto.AchievementPageDto;
import com.qidao.application.service.organization.impl.AchievementEquipmentServiceImpl;
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

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/achievementEquipment")
@Api(value = "App成果模块", tags = "App成果模块")
public class AchievementEquipmentContorller {
    @Autowired
    AchievementEquipmentServiceImpl achievementEquipmentService;
    @Autowired
    MemberServiceImpl memberService;

    @OperLog(title = "设备/成果发布", businessType = BusinessType.INSERT)
    @ApiOperation(value = "设备发布", notes = "设备发布")
    @PostMapping(value = "/saveEquipment")
    @QiDaoPermission
    public ResponseResult saveEquipment(@RequestBody @Validated AchievementEquipmentDto achievementEquipmentDto) {
        log.info("--- AchievementEquipmentContorller ---> save ---> : params={}-----", achievementEquipmentDto);
        String result = achievementEquipmentService.save(achievementEquipmentDto);
        return Result.ok(result);
    }

    @OperLog(title = "逻辑删除成果/设备", businessType = BusinessType.INSERT)
    @ApiOperation(value = "逻辑删除成果/设备", notes = "逻辑删除成果/设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = false, dataType = "Long", dataTypeClass = Long.class, example = "391235115570827264")
    })
    @DeleteMapping(value = "/updateFlag")
    public ResponseResult<String> updateFlag(@RequestParam(value = "id") Long id) {
        log.info("--- AchievementEquipmentContorller ---> updateFlag ---> : id={}-----", id);
        String result = achievementEquipmentService.updateFlag(id);
        return Result.ok(result);
    }

    @ApiOperation(value = "实验室成果展示", notes = "实验室成果展示")
    @PostMapping(value = "/findAchievementPage")
    public ResponseResult<PageInfo<AchievementListDto>> findAchievementPage(@RequestBody @Validated AchievementPageDto achievementPageDto) {
        log.info("--- AchievementEquipmentContorller ---> findAchievementPage ---> params{}: ", achievementPageDto);
        PageInfo<AchievementListDto> achievementPage = achievementEquipmentService.findAchievementPage(achievementPageDto.getOffset(),
                achievementPageDto.getLimit(), achievementPageDto.getOrganizationId(), achievementPageDto.getType());
        return Result.ok(achievementPage);
    }


    /**
     * 查询设备/成果详情
     */
    @ApiOperation(value = "查询设备/成果详情", notes = "查询设备/成果详情")
    @PostMapping(value = "/findAchievementDetail")
    public ResponseResult findAchievementDetail(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("--- AchievementEquipmentContorller ---> findAchievementDetail ---> : params={}-----", baseIdQuery);
        AchievementListDto result = achievementEquipmentService.findAchievementDetail(baseIdQuery.getId());
        return Result.ok(result);
    }

    /**
     * 查询成果发布标题栏
     */
    @PostMapping(value = "/findSelectConfigByOrganization")
    @ApiOperation(value = "根据实验室标识查询成果发布标题栏")
    public ResponseResult<List<SelectConfigResp>> findSelectConfigByOrganization(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("--- AchievementEquipmentContorller ---> findSelectConfigByOrganization ---> : params={}-----", baseIdQuery);
        List<SelectConfigResp> result = memberService.findSelectConfigByOrganization(baseIdQuery.getId());
        return Result.ok(result);
    }


}
