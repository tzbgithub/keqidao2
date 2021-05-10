package com.qidao.application.mapper.inner;

import com.qidao.application.entity.inner.Salesman;
import com.qidao.application.entity.inner.SalesmanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesmanMapper {
    long countByExample(SalesmanExample example);

    int deleteByExample(SalesmanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Salesman record);

    int insertSelective(@Param("record") Salesman record, @Param("selective") Salesman.Column ... selective);

    Salesman selectOneByExample(SalesmanExample example);

    List<Salesman> selectByExample(SalesmanExample example);

    Salesman selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Salesman record, @Param("example") SalesmanExample example, @Param("selective") Salesman.Column ... selective);

    int updateByExample(@Param("record") Salesman record, @Param("example") SalesmanExample example);

    int updateByPrimaryKeySelective(@Param("record") Salesman record, @Param("selective") Salesman.Column ... selective);

    int updateByPrimaryKey(Salesman record);

    int batchInsert(@Param("list") List<Salesman> list);

    int batchInsertSelective(@Param("list") List<Salesman> list, @Param("selective") Salesman.Column ... selective);
}