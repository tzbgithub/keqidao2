package com.qidao.application.controller.organization;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.dto.*;
import com.qidao.application.model.organization.KickOutMemberReq;
import com.qidao.application.model.organization.SignOutEnterpriseReq;
import com.qidao.application.model.organization.OrganizationDetailRes;
import com.qidao.application.service.member.impl.MemberServiceImpl;
import com.qidao.application.service.organization.impl.OrganizationServiceImpl;
import com.qidao.application.model.member.EnterpriseMemberReq;
import com.qidao.application.model.member.EnterpriseMemberRes;
import com.qidao.application.model.dto.AuthOrganizationDto;
import com.qidao.application.vo.OrganizationBaseDataRep;
import com.qidao.application.vo.OrganizationSpaceRes;
import com.qidao.application.vo.TutorInfoReq;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organization")
@Slf4j
@Api(value = "App组织机构", tags = "App组织机构")
public class OrganizationController {

    @Autowired
    OrganizationServiceImpl organizationService;
    @Autowired
    MemberServiceImpl memberService;
    @ApiOperation(value = "实验室入驻",notes = "实验室入驻")
    @OperLog(title = "实验室入驻",businessType = BusinessType.INSERT)
    @PostMapping(value = "/save")
    public ResponseResult<String> save(@RequestBody @Validated OrganizationDto organizationDto, HttpServletRequest request) {
        log.info("--- OrganizationController ---> save ---> : params={}-----",organizationDto);
        String save = organizationService.save(request, organizationDto, 0);
        return Result.ok(save);
    }

    @ApiOperation(value = "组织机构入驻",notes = "组织机构入驻")
    @OperLog(title = "组织机构入驻",businessType = BusinessType.INSERT)
    @PostMapping(value = "/saveOrganization")
    public ResponseResult<String> saveOrganization(@RequestBody @Validated OrganizationDto organizationDto, HttpServletRequest request) {
        log.info("--- OrganizationController ---> saveOrganization ---> : params={}-----",organizationDto);
        String save = organizationService.save(request, organizationDto, 1);
        return Result.ok(save);
    }

    @OperLog(title = "组织机构销毁", businessType = BusinessType.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = false, dataType = "int", dataTypeClass = int.class, example = "391235115570827264")
    })
    @ApiOperation(value = "组织机构销毁", notes = "组织机构销毁")
    @DeleteMapping(value = "/delete")
    public ResponseResult<Organization> delete(@RequestParam("id") Long id) {
        log.info("--- OrganizationController ---> delete ---> : id={}", id);
        int delete = organizationService.delete(id);
        if (delete > 0) {
            return new ResponseResult<Organization>("Sx200", null, "注销成功", LocalDateTime.now());
        } else {
            return new ResponseResult<Organization>("LxAPI-CCCCCC", null, "注销失败", LocalDateTime.now());
        }
    }
    @OperLog(title = "所属单位查询实验室",businessType = BusinessType.OTHER)
    @PostMapping(value = "/findByBelong")
    @ApiOperation(value = "所属单位查询实验室",notes = "所属单位查询实验室")
    public ResponseResult<ArrayList<Map>> findOrganizationByBelong(@RequestBody @Validated  BeLongDto beLongDto){
        log.info("--- OrganizationController ---> findByBelong ---> : params={}",beLongDto);
        ArrayList<Map> organizationByBelong = organizationService.findOrganizationByBelong(beLongDto.getBelong(), 0);
        return Result.ok(organizationByBelong);
    }


    @OperLog(title = "实验室标识查询实验室详情",businessType = BusinessType.OTHER)
    @PostMapping(value = "/findByOrganizationId")
    @ApiOperation(value = "实验室标识查询实验室/组织机构详情",notes = "实验室标识查询实验室/组织机构详情")
    public ResponseResult<OrganizationDetailRes> findByOrganizationId(@RequestBody @Validated BaseIdQuery baseIdQuery){
        log.info("--- OrganizationController ---> findByOrganizationId ---> : params={}",baseIdQuery);
        OrganizationDetailRes byOrganizationId = organizationService.findOrganizationDetail(baseIdQuery.getId());
        return Result.ok(byOrganizationId);
    }


    /**
     * 修改实验室信息
     */
    @OperLog(title = "修改实验室信息",businessType = BusinessType.OTHER)
    @PostMapping(value = "/updateOrganization")
    @ApiOperation(value = "修改实验室信息")
    public  ResponseResult<String>  updateOrganization(@RequestBody  @Validated UpdateOriganizationDto updateOriganizationDto){
        log.info("--- OrganizationController ---> updateOrganization ----> 修改实验室信息 params:{} -----",updateOriganizationDto);
        organizationService.update(updateOriganizationDto);
        return Result.ok("更新成功");
    }

    @OperLog(title = "实验室认证",businessType = BusinessType.OTHER)
    @PostMapping(value = "/authOrganization")
    @ApiOperation(value = "实验室认证",notes = "上传营业执照")
    @QiDaoPermission
    public  ResponseResult authOrganization(@RequestBody @Validated AuthOrganizationDto authOrganizationDto) {
        log.info("--- OrganizationController ---> authOrganization ---->  params:{} -----",authOrganizationDto);
        organizationService.authOrganization(authOrganizationDto);;
        return Result.ok("上传成功,等待处理");
    }

    @PostMapping(value = "/findOrganizationSpace")
    @ApiOperation(value = "实验室空间",notes = "实验室空间 展示所属下面的前五个用户 根据职位以及入驻时间倒叙 ")
    public ResponseResult<OrganizationSpaceRes>  findOrganizationSpace(@RequestBody @Validated BaseIdQuery baseIdQuery){
        OrganizationSpaceRes organizationSpace = organizationService.findOrganizationSpace(baseIdQuery.getId());
        return Result.ok(organizationSpace);
    }

    @PostMapping(value = "/findOrganizationMembers")
    @ApiOperation(value = "展示所有组织成员",notes = "实验室空间下的默认展示5个成员,点击下拉展示所有的用户专用")
    public ResponseResult<List<TutorInfoReq>> findOrganizationMembers(@RequestBody @Validated BaseIdQuery baseIdQuery){
        log.info("--- OrganizationController ---> findOrganizationMembers ---->  params:{} -----",baseIdQuery);
        List<TutorInfoReq> result = organizationService.findOrganizationMembers(baseIdQuery.getId());
        return Result.ok(result);
    }

    /**
     * 实验室资料
     */
    @PostMapping(value = "/findOrganizationData")
    @ApiOperation(value = "实验室资料",notes = "实验室资料")
    public ResponseResult<OrganizationBaseDataRep> findOrganizationData(@RequestBody @Validated BaseIdQuery baseIdQuery){
        log.info("--- OrganizationController ---> findOrganizationData ---->  params:{} -----",baseIdQuery);
        OrganizationBaseDataRep organizationData = organizationService.findOrganizationData(baseIdQuery.getId());
        return Result.ok(organizationData);
    }

    @ApiOperation(value = "查询企业下所有成员 --yqj")
    @PostMapping("/findEnterpriseMember")
    public ResponseResult<ServicePage<List<EnterpriseMemberRes>>> findEnterpriseMember(@RequestBody @Validated EnterpriseMemberReq req) {
        log.info("OrganizationController -> findEnterpriseMember -> start -> params -> {}", req);
        ServicePage<List<EnterpriseMemberRes>> enterpriseMember = organizationService.findEnterpriseMember(req);
        log.info("OrganizationController -> findEnterpriseMember -> end");
        return Result.ok(enterpriseMember);
    }

    @OperLog(title = "退出企业", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "退出企业 --yqj")
    @ApiImplicitParam(name = "req", value = "推出企业请求对象", dataType = "SignOutEnterpriseReq", dataTypeClass = SignOutEnterpriseReq.class, required = true)
    @PostMapping("/signOutEnterprise")
    @QiDaoPermission
    public ResponseResult<Object> signOutEnterprise(@RequestBody @Validated SignOutEnterpriseReq req){
        log.info("OrganizationController -> signOutEnterprise -> start -> params -> {}",req);
        organizationService.signOutEnterprise(req);
        log.info("OrganizationController -> signOutEnterprise -> end");
        return Result.ok();
    }

    @ApiOperation(value="查询公司企业详情", notes="查询公司企业详情")
    @PostMapping(value = "/enterprise/findByOrganizationId")
    public ResponseResult<ReturnOrganizationDto> findByOrganizationIdInfo(@RequestBody  @Validated BaseIdQuery baseIdQuery){
        log.info("--- EnterpriseController ---> findByOrganizationIdInfo ---> : params={}",baseIdQuery);
        ReturnOrganizationDto result = organizationService.findByOrganizationId(baseIdQuery.getId());
        return Result.ok(result);
    }

    @OperLog(title = "企业管理员删除员工",businessType = BusinessType.UPDATE)
    @PostMapping(value = "/kickOutMember")
    @ApiOperation(value = "企业管理员删除员工")
    public  ResponseResult<String>  kickOutMember(@RequestBody  @Validated KickOutMemberReq req){
        log.info("--- OrganizationController ---> kickOutMember ----> params:{} -----",req);
        organizationService.kickOutMember(req);
        return Result.ok("更新成功");
    }


}
