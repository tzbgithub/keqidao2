package com.qidao.application.mapper.order;

import com.qidao.application.entity.order.Order;
import com.qidao.application.entity.order.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(@Param("record") Order record, @Param("selective") Order.Column ... selective);

    Order selectOneByExample(OrderExample example);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example, @Param("selective") Order.Column ... selective);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(@Param("record") Order record, @Param("selective") Order.Column ... selective);

    int updateByPrimaryKey(Order record);

    int batchInsert(@Param("list") List<Order> list);

    int batchInsertSelective(@Param("list") List<Order> list, @Param("selective") Order.Column ... selective);
}