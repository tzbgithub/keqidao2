package com.qidao.application.mapper.server;

import com.github.pagehelper.Page;
import com.qidao.application.common.Query;
import com.qidao.application.entity.server.ServerList;
import com.qidao.application.entity.server.ServerPage;
import com.qidao.application.entity.server.ServerPageDo;
import com.qidao.application.vo.InfoOrganizationServicePageRep;
import com.qidao.application.vo.InfoServerPageRep;
import com.qidao.application.entity.server.Server;
import com.qidao.application.vo.ServerIndustryRep;
import com.qidao.framework.service.ServicePage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomServerMapper {

    /**
     * 个人空间展示需求（只展示个人发布）
     * @param query 查询条件
     * @return 分页集合 {@link InfoServerPageRep}
     */
    public Page<InfoServerPageRep> infoServerPage(Query query);

    /**
     * (1) 领域id不为空，根据领域id筛选
     * (2) 标题不为空，根据标题模糊查询
     * (3) 日期不为空，根据日期范围查询
     * (4) 根据指定的delFlag来获取结果
     *
     * @param delFlag        逻辑删除标示符
     * @param status         服务需求状态(目前只返回1-已发布状态的，但这里还是写成根据status判断)
     * @param specAreaId     需求领域id
     * @param keyword        查询关键字
     * @param queryTimeStart 发布起始时间
     * @param queryTimeEnd   发布截止时间
     * @return 服务需求-对象集合
     */
    List<ServerList> list(@Param("delFlag") Byte delFlag, @Param("status") Integer status, @Param("specAreaId") Long specAreaId, @Param("labelIds") Long[] labelIds, @Param("keyword") String keyword, @Param("queryTimeStart") LocalDateTime queryTimeStart, @Param("queryTimeEnd") LocalDateTime queryTimeEnd, @Param("industryId") List<Long> industryId);

    /**
     * @param serverIds 原本的serverId集合
     * @param labelIds  标签id集合
     * @return 筛选过滤后的serverId集合
     * @deprecated 根据标签id集合来过滤服务需求id集合。
     * 筛选带有任意一个标签labelIds的serverIds集合
     */
    List<Long> filterIdsWithLabelIds(@Param("serverIds") Long[] serverIds, @Param("labelIds") Long[] labelIds);


     Page<InfoOrganizationServicePageRep> infoOrganizationServerPage(Query query);
     Page<InfoOrganizationServicePageRep> infoOrganizationServerContentPage(Query query);
     List<ServerIndustryRep> selectHaveIndustryServer();
     int findServerSize(@Param("memberId") String memberId) ;

     List<ServerPage> getServerList(ServerPageDo serverPageDo);

    /**
     * 查询个人空间需求（展示组织下所有人发布的需求）
     * @param id 组织机构id
     * @return 分页集合 {@link InfoServerPageRep}
     */
     List<InfoServerPageRep> listServerMemberId(Long id);

}




