package com.qidao.application.mapper.organization;

import com.qidao.application.entity.organization.MemberOrganizationTypeDO;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.organization.OrganizationDetail;
import com.qidao.application.vo.OrganizationBaseDataRep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface CustomOrganizationMapper {

    int insert(Organization record);

    Organization findByName(String name,Integer type,String belong);

    List<Organization> listByIds(@Param("organizationIds") Long[] organizationIds,@Param("delFlag")Byte delFlag);

    /**
     * 根据memberId查询实验室类型
     * @param memberId
     * @return 0-实验室 1-企业
     */
    Integer getOrganizationType (Long memberId);

    List<OrganizationBaseDataRep> findOrganizationData(Long organizationId);

    /**
     * 查询用记所属组织机构类型
     * @param memberIds
     * @return
     */
    List<MemberOrganizationTypeDO> queryOrganizationType(@Param("memberIds") List<Long> memberIds);

    OrganizationDetail findOrganizationDetail(Long id);
}