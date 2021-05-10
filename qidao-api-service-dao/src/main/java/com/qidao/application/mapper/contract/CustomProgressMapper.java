package com.qidao.application.mapper.contract;


import com.qidao.application.entity.contract.Progress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 4:50 PM
 */
public interface CustomProgressMapper {

    List<Progress> getProgressListByContractIds(@Param("contractIds") Long[] contractIds, @Param("delFlag") Byte delFlag);
}
