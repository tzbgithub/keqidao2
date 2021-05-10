package com.qidao.application.mapper.contract;

import com.qidao.application.entity.contract.Contract;
import com.qidao.application.entity.contract.ContractExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContractMapper {
    long countByExample(ContractExample example);

    int deleteByExample(ContractExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Contract record);

    int insertSelective(@Param("record") Contract record, @Param("selective") Contract.Column ... selective);

    Contract selectOneByExample(ContractExample example);

    List<Contract> selectByExample(ContractExample example);

    Contract selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Contract record, @Param("example") ContractExample example, @Param("selective") Contract.Column ... selective);

    int updateByExample(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByPrimaryKeySelective(@Param("record") Contract record, @Param("selective") Contract.Column ... selective);

    int updateByPrimaryKey(Contract record);

    int batchInsert(@Param("list") List<Contract> list);

    int batchInsertSelective(@Param("list") List<Contract> list, @Param("selective") Contract.Column ... selective);
}