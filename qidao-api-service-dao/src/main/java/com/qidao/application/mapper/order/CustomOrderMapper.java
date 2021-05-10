package com.qidao.application.mapper.order;

import com.qidao.application.entity.order.CustomOrder;
import com.qidao.application.entity.order.Order;
import com.qidao.application.entity.order.OrderDescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CustomOrderMapper {

    List<CustomOrder> getOrderByMemberId(Long memberId, Integer physicalFlag);

    CustomOrder getOrderById(Long id);

    /**
     * 新增订单 ： null 不存
     *
     * @param record    要插入的实体对象
     * @param selective 列
     * @return 受影响行数
     */
    int insertSelective(@Param("record") Order record, @Param("selective") Order.Column... selective);

    /**
     * 获取订单描述
     *
     * @param orderNo  订单号
     * @param memberId 会员ID
     * @return 订单信息
     */
    OrderDescription getOrderDescription(Long orderNo, Long memberId);

    int updateStatusPayment(Long id);

    Order queryOrderStatus(Long id);

    int updateStatusClosed(Long id);

    /**
     * 订单完成后 修改订单状态为已完成 <br>
     * 只有<strong>虚拟订单</strong>可以修改成功
     *
     * @param id             订单ID
     * @param payType        支付方式
     * @param productSkuName sku名称
     * @param vipStartTime   vip 开始时间
     * @param vipEndTime     vip 结束时间
     * @return 受影响行数
     */
    Integer updateStatusDone(@Param("id") Long id, @Param("payType") Integer payType,
                             @Param("vipStartTime") LocalDateTime vipStartTime, @Param("vipEndTime") LocalDateTime vipEndTime,
                             @Param("productSkuName") String productSkuName,
                             @Param("thirdOrderNo") String thirdOrderNo,
                             @Param("isSandbox") Boolean isSandbox);

    /**
     * 订单完成后 修改订单状态为已支付 <br>
     * 只有<strong>实物订单</strong>可以修改成功
     *
     * @param id             订单ID
     * @param payType        支付方式
     * @param productSkuName sku名称
     * @param vipStartTime   vip 开始时间
     * @param vipEndTime     vip 结束时间
     * @return 受影响行数
     */
    Integer updateStatusPaidPhysical(@Param("id") Long id, @Param("payType") Integer payType,
                                     @Param("vipStartTime") LocalDateTime vipStartTime,
                                     @Param("vipEndTime") LocalDateTime vipEndTime,
                                     @Param("productSkuName") String productSkuName,
                                     @Param("thirdOrderNo") String thirdOrderNo,
                                     @Param("isSandbox") Boolean isSandbox
    );

    /**
     * 根据订单号查询订单 （ 被逻辑删除的也会查出 )
     *
     * @param orderNo 订单号
     * @return 订单信息封装对象
     */
    CustomOrder selectByOrderNo(Long orderNo);

    /**
     * 获取订单状态
     *
     * @param orderNo 订单号
     * @return 订单状态  , 定义值见 : <br>
     * @see com.qidao.application.model.order.enums.OrderConstantEnum
     */
    Integer getOrderStatusByOrderNo(Long orderNo);

    /**
     * 统计第三方订单出现次数 <br>
     * 逻辑删除的订单也纳入统计范围
     * @param thirdOrderNo 第三方订单号
     * @return 统计数
     */
    Integer countThirdOrderNo(String thirdOrderNo);
}
