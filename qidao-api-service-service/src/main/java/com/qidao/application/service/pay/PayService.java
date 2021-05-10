package com.qidao.application.service.pay;

import com.qidao.application.model.pay.PayCallbackReq;
import com.qidao.application.model.pay.PayRes;
import com.qidao.application.model.pay.PolicyPayDTO;

public interface PayService {
    /**
     * 获取支付方式:用于策略模式
     *
     * @return 支付方式，定义见  {@link com.qidao.application.model.pay.enums.PayWayEnum}
     */
    Integer getPayWay();

    /**
     * 支付唤醒 <br>
     * 使用策略模式 <br>
     *
     * @param req {@linkplain PolicyPayDTO}  支付唤醒请求参数
     * @return {@link PayRes} 各支付方式唤醒参数 ； 抽象类
     */
    PayRes invoke(PolicyPayDTO req);

    /**
     * 支付回调：确认订单已支付并赠送会员到当前会员<br>
     * 目前实现的方式有:<br>
     * <ul>
     *     <li>支付宝：  通过支付宝服务器发送请求进行回调</li>
     *     <li>微信： 通过微信服务器发送请求进行回调</li>
     *     <li>苹果支付： 通过<strong><em>客户端</em></strong>发送请求</li>
     * </ul>
     *
     * @param req {@link PayCallbackReq} 回调执行参数
     * @throws Exception 可能会抛出异常
     */
    void callback(PayCallbackReq req) throws Exception;

}
