package com.qidao.application.service.order;

import com.qidao.application.entity.order.CustomOrder;
import com.qidao.application.model.order.*;

public interface OrderService {

    /**
     * 根据会员ID分页查询订单
     *
     * @param req
     * @return
     */
    OrderPageRes getOrderByMemberId(OrderPageReq req);

    /**
     * 单产品生成订单
     *
     * @param req 生成订单 请求实体
     * @return
     */
    OrderCreateOneProductRes orderCreateOneProduct(OrderCreateOneProductReq req);

    /**
     * 查询订单详情
     *
     * @param req 请求参数
     * @return
     */
    OrderDescriptionRes getOrderDescription(OrderDescriptionReq req);

    /**
     * 修改订单状态为已支付（只有未支付订单才可修改）
     *
     * @param id
     * @return
     */
    boolean updateStatusPayment(Long id);

    /**
     * 修改订单状态为已关闭（只有未支付订单才可修改）
     *
     * @param id
     * @return
     */
    boolean updateStatusClosed(Long id);

    /**
     * 订单完成回调执行<br>
     * 重载方法<br>
     * 只有<strong>未支付</strong>、<strong>已关闭</strong>订单可以完成
     *
     * @param orderNo 订单号
     * @param payWay  支付方式
     * @return true: 修改成功  false : 修改失败
     */
    void orderDone(Long orderNo, Integer payWay);

    /**
     * 订单完成回调执行<br>
     * 重载方法<br>
     * 只有<strong>未支付</strong>、<strong>已关闭</strong>订单可以完成
     *
     * @param orderNo    订单号
     * @param payWay     支付方式
     * @param isLabOrder 是否是实验室专属0元订单
     * @return true: 修改成功  false : 修改失败
     */
    void orderDone(Long orderNo, Integer payWay, Boolean isLabOrder);

    /**
     * 订单完成回调执行<br>
     * 只有<strong>未支付</strong>、<strong>已关闭</strong>订单可以完成
     *
     * @param orderNo      订单号
     * @param payWay       支付方式
     * @param isLabOrder   是否是实验室专属0元订单
     * @param thirdOrderNo 第三方订单号
     * @return true: 修改成功  false : 修改失败
     */
    void orderDone(Long orderNo, Integer payWay, Boolean isLabOrder, String thirdOrderNo);

    /**
     *
     * @param orderNo
     * @param payWay
     * @param isLabOrder
     * @param thirdOrderNo
     * @param isSandBox
     */
    void orderDone(Long orderNo, Integer payWay, Boolean isLabOrder, String thirdOrderNo,Boolean isSandBox);

    /**
     * 根据订单号查询订单 （ 被逻辑删除的也会查出 )
     *
     * @param orderNo 订单号
     * @return 订单信息封装对象
     */
    CustomOrder selectByOrderNo(Long orderNo);

    /**
     * 查询订单是否已支付并处理完成
     *
     * @param orderNo 订单NO
     * @return true - 已完成 false - 未完成
     */
    boolean isOrderDone(Long orderNo);

    /**
     * 实验室0元订单确认
     *
     * @param req
     */
    void labOrderDone(LabOrderDoneReq req);
}
