package com.qidao.application.mapper.contract;

import com.qidao.application.entity.contract.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 4:50 PM
 */
public interface CustomContractMapper {

    List<Contract> getContractItemList(@Param("memberId") Long memberId, @Param("delFlag") Byte delFlag);

    Contract getContract(@Param("contractId") Long contractId, @Param("memberId") Long memberId, @Param("delFlag") Byte delFlag);
}
