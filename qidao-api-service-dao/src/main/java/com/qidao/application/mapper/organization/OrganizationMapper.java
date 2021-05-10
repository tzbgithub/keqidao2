package com.qidao.application.mapper.organization;

import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.organization.OrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrganizationMapper {
    long countByExample(OrganizationExample example);

    int deleteByExample(OrganizationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Organization record);

    int insertSelective(@Param("record") Organization record, @Param("selective") Organization.Column ... selective);

    Organization selectOneByExample(OrganizationExample example);

    List<Organization> selectByExample(OrganizationExample example);

    Organization selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Organization record, @Param("example") OrganizationExample example, @Param("selective") Organization.Column ... selective);

    int updateByExample(@Param("record") Organization record, @Param("example") OrganizationExample example);

    int updateByPrimaryKeySelective(@Param("record") Organization record, @Param("selective") Organization.Column ... selective);

    int updateByPrimaryKey(Organization record);

    int batchInsert(@Param("list") List<Organization> list);

    int batchInsertSelective(@Param("list") List<Organization> list, @Param("selective") Organization.Column ... selective);

    /**
     * 通过用户ID查询组织机构表的审核状态
     * @param memberId 入参：用户ID
     * @return verify_status -- 0-未审核 1-审核失败 2-审核成功    入驻审核
     */
    Long getOrganStaus(@Param("memberId")Long memberId);
}