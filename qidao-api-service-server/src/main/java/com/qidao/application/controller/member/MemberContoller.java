package com.qidao.application.controller.member;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.member.*;
import com.qidao.application.service.member.impl.MemberServiceImpl;
import com.qidao.application.vo.*;
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
@RequestMapping("/member")
@Api(tags = "App用户模块")
@Slf4j
public class MemberContoller {

    @Autowired
    MemberServiceImpl memberService;


    /**
     * 根据memberId查询会员基本信息
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据会员ID获取会员基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "会员ID", required = true, dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, example = "132331855740930")})
    @PostMapping("/getMemberInfoByID")
    public ResponseResult<MemberInfoRes> getMemberInfoByID(@RequestBody @Validated BaseIdQuery req) {
        log.info("MemberController -> getMemberInfoByID -> start -> param :{}", req);
        MemberInfoRes memberInfo = memberService.getMemberByMemberId(req.getId());
        log.info("MemberController -> getMemberInfoByID -> end");
        return Result.ok(memberInfo);
    }

    /**
     * 根据会员unionId查询会员基本信息
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据unionId获取会员基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "微信唯一标识", required = true, dataType = "UnionIdQuery", dataTypeClass = UnionIdQuery.class)})
    @PostMapping("/getMemberInfoByUnionId")
    public ResponseResult<MemberInfoRes> getMemberInfoByUnionId(@RequestBody @Validated UnionIdQuery req) {
        log.info("MemberController -> getMemberInfoByUnionId -> start -> unionId :{}", req);
        MemberInfoRes memberInfo = memberService.getMemberByUnionId(req.getUnionId());
        log.info("MemberController -> getMemberInfoByUnionId -> end");
        return Result.ok(memberInfo);
    }

    /**
     * 根据会员ID查询会员详细信息
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据会员ID获取会员详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "会员ID", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true, example = "132331855740930")})
    @PostMapping("/getDetailedByMemberId")
//    @QiDaoPermission
    public ResponseResult<MemberDetailedRes> getDetailedByMemberId(@RequestBody @Validated BaseIdQuery req) {
        log.info("MemberController -> getMemberInfoByID -> start -> param :{}", req);
        MemberDetailedRes detailed = memberService.getDetailedByMemberId(req.getId());
        log.info("MemberController -> getMemberInfoByID -> end");
        return Result.ok(detailed);
    }

    @ApiOperation(value = "根据unionID获取会员详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "微信唯一标识", dataType = "UnionIdQuery", dataTypeClass = UnionIdQuery.class, required = true)})
    @PostMapping("/getDetailedByUnionId")
    public ResponseResult<MemberDetailedRes> getDetailedByUnionId(@RequestBody @Validated UnionIdQuery req) {
        log.info("MemberController -> getDetailedByUnionId -> start -> param :{}", req);
        MemberDetailedRes detailed = memberService.getDetailedByUnionId(req.getUnionId());
        log.info("MemberController -> getDetailedByUnionId -> end");
        return Result.ok(detailed);
    }

    /**
     * 修改会员信息
     *
     * @param req
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "会员信息", dataType = "MemberInfoUpdateReq", dataTypeClass = MemberInfoUpdateReq.class, required = true)})
    @ApiOperation(value = "修改会员信息")
    @PutMapping("/update")
    @OperLog(title = "修改会员信息", businessType = BusinessType.UPDATE, isAsyncSaveToDB = true)
    public ResponseResult<Object> update(@RequestBody @Validated MemberInfoUpdateReq req) {
        log.info("MemberController -> update -> start -> req :{}", req);
        memberService.update(req);
        log.info("MemberController -> update -> end");
        return Result.ok(true);
    }


    /**
     * 修改用户个人信息
     */
    @PostMapping(value = "/updateMemberMessage")
    @ApiOperation(value = "修改用户个人信息")
    @OperLog(title = "修改用户个人信息", businessType = BusinessType.UPDATE, isAsyncSaveToDB = true)
    public ResponseResult<String> updateMemberMessage(@RequestBody @Validated MemberDetailVo memberDetailVo) {
        log.info("--- MemberController ---> updateMemberMessage ----> 修改用户个人信息 params:{} -----", memberDetailVo);
        memberService.updateMemberMessage(memberDetailVo);
        return Result.ok("更新成功");
    }

    /**
     * 查询用户名片
     */
    @PostMapping(value = "/findMemberMessage")
    @ApiOperation(value = "查询用户名片")
    public ResponseResult<MemberVo> findMemberMessage(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("--- MemberController ---> findMemberMessage ----> 查询用户名片 params:{} -----", baseIdQuery);
        MemberVo result = memberService.findMemberMessage(baseIdQuery.getId());
        return Result.ok(result);

    }

    /**
     * 查询用户详情
     */
    @PostMapping(value = "/findMemberMessageDetail")
    @ApiOperation(value = "查询用户详情")
    public ResponseResult<MemberDetailVo> findMemberMessageDetail(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("--- MemberController ---> findMemberMessageDetail ----> 查询用户详情 params:{} -----", baseIdQuery);
        MemberDetailVo result = memberService.findMemberMessageDetail(baseIdQuery.getId());
        return Result.ok(result);
    }

    @PostMapping(value = "/offPush")
    @ApiOperation(value = "推送开关")
    @OperLog(title = "推送开关", businessType = BusinessType.UPDATE, isAsyncSaveToDB = true)
    public ResponseResult offPush(@RequestBody @Validated OffPushReq offPushReq) {
        memberService.offPush(offPushReq.getStatus(), offPushReq.getId());
        return Result.ok();
    }

    /**
     * 根据会员ID查询组织机构信息
     *
     * @return
     */
    @OperLog(title = "根据会员ID查询组织机构信息", businessType = BusinessType.OTHER)
    @PostMapping(value = "/findOrganizationByMemberId")
    @ApiOperation(value = "根据会员ID查询组织机构信息")
    public ResponseResult<OrganizationRep> findOrganizationByMemberId(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        OrganizationRep organizationRep = memberService.findOrganizationByMemberId(baseIdQuery.getId());
        return Result.ok(organizationRep);
    }

    /**
     * 根据用户表示绑定行业
     *
     * @return
     */
    @OperLog(title = "根据用户表示绑定行业", businessType = BusinessType.OTHER)
    @PostMapping(value = "/memberBindingIndustry")
    @ApiOperation(value = "根据用户表示绑定行业")
    public ResponseResult memberBindingIndustry(@RequestBody @Validated BindIndustryReq bindIndustryReq) {
        memberService.memberBindingIndustry(bindIndustryReq);
        return Result.ok();
    }

    @OperLog(title = "根据用户表示绑定邮箱", businessType = BusinessType.OTHER)
    @PostMapping(value = "/memberBindingEmail")
    @ApiOperation(value = "根据用户表示绑定邮箱")
    public ResponseResult memberBindingEmail(@RequestBody @Validated BindEmailReq bindEmailReq) {
        memberService.memberBindingEmail(bindEmailReq.getMemberId(), bindEmailReq.getEmail());
        return Result.ok();
    }

    @OperLog(title = "简单修改用户基本信息", businessType = BusinessType.OTHER)
    @PostMapping(value = "/modifyMemberBasic")
    @ApiOperation(value = "简单修改用户基本信息")
    public ResponseResult modifyMemberBasic(@RequestBody @Validated MemberBasicReq memberBasicReq) {
        memberService.modifyMemberBasic(memberBasicReq);
        return Result.ok();
    }

    @PostMapping(value = "/findOrganizationStatus")
    @ApiOperation(value = "通过用户ID查询组织机构表的审核状态", notes = "0 未审核 1 审核失败 2 审核成功")
    public ResponseResult<Long> findOrganizationStatus(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("--- MemberController ---> findOrganizationStatus ----> 通过用户ID查询组织机构表的审核状态 BaseIdQuery:{} -----", baseIdQuery);
        Long verifyStatus = memberService.findMemberStatus(baseIdQuery.getId());
        return Result.ok(verifyStatus);

    }


    /**
     * 实验室上传验证证件接口
     */
    @OperLog(title = "实验室上传验证证件接口", businessType = BusinessType.OTHER)
    @PostMapping(value = "/uploadOrganizationAuthentication")
    @ApiOperation(value = "实验室上传验证证件接口")
    public ResponseResult uploadOrganizationAuthentication(@RequestBody @Validated MemberAuthenticationReq memberAuthenticationReq) {
        log.info("--- MemberController ---> uploadOrganizationAuthentication ----> 实验室上传验证证件接口 params:{} -----", memberAuthenticationReq);
        memberService.memberAuthentication(memberAuthenticationReq);
        return Result.ok();
    }

    /**
     * 查询用户是否完善公司信息
     *
     * @return
     */
    @PostMapping(value = "/findMemberIsPrefect")
    @ApiOperation(value = "查询用户是否完善公司信息")
    public ResponseResult<Boolean> findMemberIsPrefect(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("--- MemberController ---> findMemberIsPrefect ----> 查询用户是否完善公司信息 BaseIdQuery:{} -----", baseIdQuery);
        boolean memberIsPrefect = memberService.findMemberIsPrefect(baseIdQuery.getId());
        return Result.ok(memberIsPrefect);

    }

    /**
     * 查询用户行业
     *
     * @return
     */
    @PostMapping(value = "/findMemberIndustry")
    @ApiOperation(value = "查询用户行业")
    public ResponseResult<List<MemberIndustryRes>> findMemberIndustry(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        log.info("--- MemberController ---> findMemberIndustry ----> start -> params:{} -----", baseIdQuery);
        List<MemberIndustryRes> industry = memberService.findMemberIndustry(baseIdQuery.getId());
        log.info("--- MemberController ---> findMemberIndustry ----> end");
        return Result.ok(industry);

    }

    /**
     * 注销会员信息
     *
     * @return
     */
    @PostMapping(value = "/cancellationMember")
    @ApiOperation(value = "注销会员信息")
    @OperLog(title = "注销会员信息", businessType = BusinessType.UPDATE, isAsyncSaveToDB = true)
    @QiDaoPermission
    public ResponseResult<Boolean> cancellationMember(@RequestBody @Validated MemberCancellationReq req) {
        log.info("--- MemberController ---> cancellationMember ----> start -> params:{} -----", req);
        Boolean result = memberService.cancellationMember(req);
        log.info("--- MemberController ---> cancellationMember ----> end");
        return Result.ok(result);
    }

}
