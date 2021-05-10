package com.qidao.application.mapper.relation;

import com.qidao.application.entity.relation.LinkOrganizationSalesman;
import com.qidao.application.entity.relation.LinkOrganizationSalesmanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LinkOrganizationSalesmanMapper {
    long countByExample(LinkOrganizationSalesmanExample example);

    int deleteByExample(LinkOrganizationSalesmanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LinkOrganizationSalesman record);

    int insertSelective(@Param("record") LinkOrganizationSalesman record, @Param("selective") LinkOrganizationSalesman.Column ... selective);

    LinkOrganizationSalesman selectOneByExample(LinkOrganizationSalesmanExample example);

    List<LinkOrganizationSalesman> selectByExample(LinkOrganizationSalesmanExample example);

    LinkOrganizationSalesman selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LinkOrganizationSalesman record, @Param("example") LinkOrganizationSalesmanExample example, @Param("selective") LinkOrganizationSalesman.Column ... selective);

    int updateByExample(@Param("record") LinkOrganizationSalesman record, @Param("example") LinkOrganizationSalesmanExample example);

    int updateByPrimaryKeySelective(@Param("record") LinkOrganizationSalesman record, @Param("selective") LinkOrganizationSalesman.Column ... selective);

    int updateByPrimaryKey(LinkOrganizationSalesman record);

    int batchInsert(@Param("list") List<LinkOrganizationSalesman> list);

    int batchInsertSelective(@Param("list") List<LinkOrganizationSalesman> list, @Param("selective") LinkOrganizationSalesman.Column ... selective);
}