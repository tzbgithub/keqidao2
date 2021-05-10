package com.qidao.application.controller.organization;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.dto.AddCompanyReq;
import com.qidao.application.model.dto.ModifyCompanyReq;
import com.qidao.application.model.dto.ReturnOrganizationDto;
import com.qidao.application.service.member.impl.MemberServiceImpl;
import com.qidao.application.service.organization.impl.OrganizationServiceImpl;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "公司企业接口")
public class EnterpriseController {
    @Autowired
    OrganizationServiceImpl organizationService;
    @Autowired
    MemberServiceImpl memberService;

    @OperLog(title = "公司更新信息", businessType = BusinessType.UPDATE,isAsyncSaveToDB = true)
    @PostMapping(value = "/organization/modifyCompany")
    @ApiOperation(value = "公司更新信息", notes = "公司更新信息")
    public ResponseResult modifyCompany(@RequestBody @Validated ModifyCompanyReq modifyCompanyReq) {
        organizationService.modifyCompany(modifyCompanyReq);
        return Result.ok();
    }

    @OperLog(title = "创建公司", businessType = BusinessType.INSERT, isAsyncSaveToDB = true)
    @PostMapping(value = "/enterprise/addCompany")
    @ApiOperation(value = "创建公司", notes = "创建公司")
    @QiDaoPermission
    public ResponseResult<Object> addCompany(@RequestBody @Validated AddCompanyReq req) {
        organizationService.addCompany(req);
        return Result.ok();
    }

    @ApiOperation(value="查询公司企业详情", notes="查询公司企业详情")
    @PostMapping(value = "/enterprise/findByOrganizationId")
    public ResponseResult<ReturnOrganizationDto> findByOrganizationId(@RequestBody  @Validated BaseIdQuery baseIdQuery){
        log.info("--- EnterpriseController ---> findByOrganizationId ---> : params={}",baseIdQuery);
        ReturnOrganizationDto result = organizationService.findByOrganizationId(baseIdQuery.getId());
        return Result.ok(result);
    }
}
