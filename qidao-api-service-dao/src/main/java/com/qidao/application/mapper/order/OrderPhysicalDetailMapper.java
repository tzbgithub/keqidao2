package com.qidao.application.mapper.order;

import com.qidao.application.entity.order.OrderPhysicalDetail;
import com.qidao.application.entity.order.OrderPhysicalDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderPhysicalDetailMapper {
    long countByExample(OrderPhysicalDetailExample example);

    int deleteByExample(OrderPhysicalDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderPhysicalDetail record);

    int insertSelective(@Param("record") OrderPhysicalDetail record, @Param("selective") OrderPhysicalDetail.Column ... selective);

    OrderPhysicalDetail selectOneByExample(OrderPhysicalDetailExample example);

    List<OrderPhysicalDetail> selectByExample(OrderPhysicalDetailExample example);

    OrderPhysicalDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderPhysicalDetail record, @Param("example") OrderPhysicalDetailExample example, @Param("selective") OrderPhysicalDetail.Column ... selective);

    int updateByExample(@Param("record") OrderPhysicalDetail record, @Param("example") OrderPhysicalDetailExample example);

    int updateByPrimaryKeySelective(@Param("record") OrderPhysicalDetail record, @Param("selective") OrderPhysicalDetail.Column ... selective);

    int updateByPrimaryKey(OrderPhysicalDetail record);

    int batchInsert(@Param("list") List<OrderPhysicalDetail> list);

    int batchInsertSelective(@Param("list") List<OrderPhysicalDetail> list, @Param("selective") OrderPhysicalDetail.Column ... selective);
}