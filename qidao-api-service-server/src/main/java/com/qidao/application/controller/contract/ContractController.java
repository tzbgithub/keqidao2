package com.qidao.application.controller.contract;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.contract.*;
import com.qidao.application.service.contract.ContractService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 2:29 PM
 */
@Api(tags = "合同项目")
@Slf4j
@RestController
@RequestMapping("/contract")
public class ContractController {


    @Autowired
    private ContractService contractService;

    @ApiOperation(value = "获取合同(我的项目)列表")
    @GetMapping("/list")
    @QiDaoPermission
    public ResponseResult<ServicePage<List<ContractListItemRes>>> list(
            @RequestParam @NotNull @ApiParam(name = "memberId", value = "当前会员的用户id", required = true, example = "130879657672706") Long memberId,
            @RequestParam @NotNull @ApiParam(name = "offset", value = "分页查询的页码(第n页),从1计数", required = true, example = "1") Integer offset,
            @RequestParam @NotNull @ApiParam(name = "limit", value = "一页的容量(页面共m条信息)", required = true, example = "1") Integer limit
    ) {
        log.info("ContractController -> list -> Long memberId : {} , Integer offset : {}, Integer limit : {}", memberId, offset, limit);
        ResponseResult<ServicePage<List<ContractListItemRes>>> resp;
        ContractDTO dto = contractService.list(
                ContractListReq.builder()
                        .memberId(memberId)
                        .offset(offset)
                        .limit(limit)
                        .build()
        );
        resp = Result.ok(dto.getContractList());
        log.info("ContractController -> list -> Return -> ResponseResult<ServicePage<List<ContractListItemRes>>> : {}", resp);
        return resp;
    }

    @OperLog(title = "乙方-签订合同", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "乙方-签订合同")
    @PutMapping("/sign")
    public ResponseResult<ContractRes> sign(@RequestBody ContractSignReq req) {
        log.info("ContractController -> sign -> ContractSignReq req : {}", req);
        ResponseResult<ContractRes> resp;
        ContractDTO dto = contractService.sign(req);
        boolean signSuccess = dto.getSuccess();
        log.info("ContractController -> sign -> boolean signSuccess : {}", signSuccess);
        if (signSuccess) {
            resp = Result.ok(null);
            log.info("ContractController -> sign -> Return -> ResponseResult<ContractRes> : {}", resp);
            return resp;
        }
        resp = Result.fail(dto.getCodeMessageEnum());
        log.info("ContractController -> sign -> Return -> ResponseResult<ProgressRes> : {}", resp);
        return resp;
    }

    @OperLog(title = "发送合同到邮箱", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "发送合同到邮箱", notes = "<h2 style='color:red;'>未实现</h2>发送合同的PDF版本到当前会员的注册邮箱中。<br> 未绑定邮箱的不能发送<br>")
    @PostMapping("/sendContractToMemberEmail")
    @QiDaoPermission
    ResponseResult sendContractToMemberEmail(@RequestBody SendContractToMemberEmailReq req) {
        log.info("ContractController -> sendContractToMemberEmail -> req -> {}", req);
        contractService.sendContractToMemberEmail(req);
        return Result.ok();
    }
}
