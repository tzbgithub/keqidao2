package com.qidao.application.controller.server;

import com.github.pagehelper.PageInfo;
import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.dto.ServerPageDto;
import com.qidao.application.model.server.*;
import com.qidao.application.service.server.ServerService;
import com.qidao.application.vo.InfoOrganizationServicePageRep;
import com.qidao.application.vo.InfoServerPageRep;
import com.qidao.application.vo.ServerIndustryRep;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 2:29 PM
 */
@Api(value = "需求Server", tags = "需求Server")
@Slf4j
@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerService serverService;


    @ApiOperation(value = "获取需求列表")
    @PostMapping("/list")
    @QiDaoPermission
    public ResponseResult<ServicePage<List<ServerPageRes>>> list(@RequestBody @Validated ServerListReq req) {
        log.info("ServerController -> list -> ServerListReq req : {}", req);
        ServicePage<List<ServerPageRes>> serverPage = serverService.getServerPage(req);
        return Result.ok(serverPage);
        /*boolean keywordIsEmpty = StringUtil.isEmpty(req.getKeyword());
        log.info("ServerController -> list -> boolean keywordIsEmpty : {}", keywordIsEmpty);
        if (keywordIsEmpty) {
            req.setKeyword(null);
        }
        ResponseResult<ServicePage<List<ServerListItemRes>>> resp = new ResponseResult<>();
        ServerDTO dto = serverService.list(req);
        if (dto != null && dto.getServerList() != null){
            List<ServerListItemRes> result = dto.getServerList().getResult();
            if (CollUtil.isEmpty(result)){
                result = new ArrayList<>();
            }else {
                result.sort((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()));
            }
            dto.getServerList().setResult(result);
            resp = Result.ok(dto.getServerList());
        }
        log.info("ServerController -> list -> Return -> ResponseResult<ServicePage<List<ServerListItemRes>>> : {}", resp);*/
    }

    @ApiOperation(value = "获取需求详情页")
    @GetMapping("/detail")
    public ResponseResult<ServerDetailRes> detail(
            @RequestParam @NotNull @ApiParam(name = "serverId", value = "服务需求id", required = true, example = "1") Long serverId
    ) {
        log.info("ServerController -> detail -> Long serverId : {}", serverId);
        ResponseResult<ServerDetailRes> resp;
        ServerDTO dto = serverService.detail(
                ServerDetailReq.builder()
                        .serverId(serverId)
                        .build()
        );
        resp = Result.ok(dto.getServerDetailRes());
        log.info("ServerController -> detail -> Return -> ResponseResult<ServerDetailRes> : {}", resp);
        return resp;
    }


    @OperLog(title = "发布需求", businessType = BusinessType.INSERT)
    @ApiOperation(value = "发布需求", notes = "用户发布一个需求 非会员不可发布")
    @PostMapping(value = "/insertServer")
    public ResponseResult insertServer(@RequestBody @Validated ServerInsesrtReq serverInsesrtReq) {
        int type = serverInsesrtReq.getType();
        serverService.insertServer(serverInsesrtReq);
        if (type == 0) {
            return Result.ok("恭喜您发布需求成功,我们将为您匹配优质科研人员");
        }
        return Result.ok("恭喜您发布需求成功,我们将会把您的需求优先匹配给本行业科研人员");
    }

    @OperLog(title = "承接服务", businessType = BusinessType.INSERT)
    @ApiOperation(value = "承接服务", notes = "个人承接对应企业发布的server需求服务")
    @PostMapping(value = "/underServer")
    @QiDaoPermission
    public ResponseResult underServer(@RequestBody @Validated UnderServerReq underServerReq) {
        serverService.underServer(underServerReq);
        return Result.ok();
    }

    @ApiOperation(value = "个人发布服务列表展示", notes = "个人信息中展示的承接服务列表  不可点击查看详情")
    @PostMapping(value = "/infoServerPage")
    @QiDaoPermission
    public ResponseResult<PageInfo<InfoServerPageRep>> infoServerPage(@RequestBody @Validated MemberServerReq req) {
        PageInfo<InfoServerPageRep> infoServerPageRepPageInfo = serverService.infoServerPage(req);
        return Result.ok(infoServerPageRepPageInfo);
    }

    @ApiOperation(value = "企业空间需求展示", notes = "企业空间里面对应的需求展示")
    @PostMapping(value = "/infoOrganizationServerPage")
    @QiDaoPermission
    public ResponseResult<PageInfo<InfoOrganizationServicePageRep>> infoOrganizationServerPage(@RequestBody @Validated ServerPageDto serverPageDto) {
        PageInfo<InfoOrganizationServicePageRep> infoOrganizationServicePageRepPageInfo = serverService.infoOrganizationServerPage(serverPageDto);
        return Result.ok(infoOrganizationServicePageRepPageInfo);
    }

    @ApiOperation(value = "需求中心", notes = "只有实验室有,企业是没有的")
    @PostMapping(value = "/infoOrganizationServerContentPage")
    @QiDaoPermission
    public ResponseResult<PageInfo<InfoOrganizationServicePageRep>> infoOrganizationServerContentPage(@RequestBody @Validated ServerContentReq req) {
        PageInfo<InfoOrganizationServicePageRep> infoOrganizationServicePageRepPageInfo = serverService.infoOrganizationServerContentPage(req);
        return Result.ok(infoOrganizationServicePageRepPageInfo);

    }

    @ApiOperation(value = "筛选列表", notes = "筛选列表")
    @PostMapping(value = "/selectHaveIndustryServer")
    public ResponseResult<List<ServerIndustryRep>> selectHaveIndustryServer() {
        List<ServerIndustryRep> serverIndustryReps = serverService.selectHaveIndustryServer();
        return Result.ok(serverIndustryReps);
    }

    @OperLog(title = "关闭需求", businessType = BusinessType.DELETE)
    @ApiOperation(value = "关闭需求", notes = "关闭需求")
    @PostMapping(value = "/deleteServer")
    public ResponseResult deleteServer(@RequestBody @Validated BaseIdQuery baseIdQuery) {
        serverService.deleteServer(baseIdQuery.getId());
        return Result.ok();
    }


    @ApiOperation(value = "需求中心消息数", notes = "需求中心消息数")
    @PostMapping(value = "/findServerSize")
    public ResponseResult<Integer> findServerSize(@RequestBody @Validated ServerSizeReq req) {
        int serverSize = serverService.findServerSize(req.getMemberId());
        return Result.ok(serverSize);
    }

    @ApiOperation(value = "个人空间展示需求（组织下所有成员发布的需求）")
    @ApiImplicitParam(name = "req" , value = "请求对象" , required = true)
    @PostMapping("/listServerMember")
    public ResponseResult<ServicePage<List<ServerMemberPageRes>>> listServerMember(@RequestBody @Validated ServerMemberPageReq req){
        ServicePage<List<ServerMemberPageRes>> listServicePage = serverService.listServerMember(req);
        return Result.ok(listServicePage);
    }
}
