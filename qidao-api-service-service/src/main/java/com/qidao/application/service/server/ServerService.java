package com.qidao.application.service.server;

import com.github.pagehelper.PageInfo;
import com.qidao.application.model.dto.ServerPageDto;
import com.qidao.application.model.server.*;
import com.qidao.application.vo.InfoOrganizationServicePageRep;
import com.qidao.application.vo.InfoServerPageRep;
import com.qidao.application.vo.ServerIndustryRep;
import com.qidao.framework.service.ServicePage;

import java.util.List;

public interface ServerService {

     void  insertServer(ServerInsesrtReq serverInsesrtReq);

      PageInfo<InfoServerPageRep> infoServerPage(MemberServerReq req);

      void  underServer(UnderServerReq underServerReq);

    /**
     * 根据查询条件(可能有的)分页获取"需求"列表
     * Version 2
     */
    ServerDTO list(ServerListReq req);

    /**
     * 根据指定的serverId获取对应的详情页
     */
    ServerDTO detail(ServerDetailReq req);

    PageInfo<InfoOrganizationServicePageRep> infoOrganizationServerPage(ServerPageDto serverPageDto);

    PageInfo<InfoOrganizationServicePageRep> infoOrganizationServerContentPage(ServerContentReq req);

    List<ServerIndustryRep> selectHaveIndustryServer();

    /**
     * 关闭需求
     */
    void  deleteServer(Long id);

    /**
     * 查询需求中心数量
     * @return
     */
    int   findServerSize(Long memberId);

    ServicePage<List<ServerPageRes>> getServerPage(ServerListReq req);

    /**
     * 获取个人空间需求（展示企业下所有成员发布的）
     * @param req 请求对象{@link ServerMemberPageReq}
     * @return 分页集合 {@link ServerMemberPageRes}
     */
    ServicePage<List<ServerMemberPageRes>> listServerMember(ServerMemberPageReq req);


}
