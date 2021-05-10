package com.qidao.application.service.pay.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.pay.AliPayConfig;
import com.qidao.application.model.pay.PayCallbackReq;
import com.qidao.application.model.pay.enums.PayExceptionEnum;
import com.qidao.application.service.order.OrderService;
import com.qidao.application.service.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class AliPayBaseServiceImpl implements PayService {
    protected final OrderService orderService;
    protected final String appId;

    public AliPayBaseServiceImpl(OrderService orderService, AliPayConfig aliPayConfig) {
        this.orderService = orderService;
        this.appId = aliPayConfig.getAppId();
        // 1. 设置参数（全局只需设置一次）
        Config config = new Config();
        config.protocol = aliPayConfig.getProtocol();
        config.gatewayHost = aliPayConfig.getGatewayHost();
        config.signType = aliPayConfig.getSignType();
        config.appId = aliPayConfig.getAppId();

        String aliPublicKey = "";
        String merchantPrivateKey = "";

        String aliPublicKeyPath = aliPayConfig.getAliPublicKeyPath();
        boolean havePublicKey = FileUtil.isFile(aliPublicKeyPath);
        log.info("AliPayAppImpl -> havePublicKey -> {}", havePublicKey);

        String merchantPrivateKeyPath = aliPayConfig.getMerchantPrivateKeyPath();
        boolean havePrivateKey = FileUtil.isFile(merchantPrivateKeyPath);
        log.info("AliPayAppImpl -> havePrivateKey -> {}", havePrivateKey);

        if (havePublicKey) {
            aliPublicKey = FileUtil.readString(aliPublicKeyPath, StandardCharsets.UTF_8);
        }
        if (havePrivateKey) {
            merchantPrivateKey = FileUtil.readString(merchantPrivateKeyPath, StandardCharsets.UTF_8);
        }

        config.merchantPrivateKey = merchantPrivateKey;
        config.alipayPublicKey = aliPublicKey;

        config.notifyUrl = aliPayConfig.getNotifyUrl();

        Factory.setOptions(config);
    }

    /**
     * 订单状态是否是已支付 <br>
     * WAIT_BUYER_PAY ：交易创建，等待买家付款 <br>
     * TRADE_CLOSED ：在指定时间段内未支付时关闭的交易或在交易完成全额退款成功时关闭的交易 <br>
     * TRADE_SUCCESS：商户签约的产品支持退款功能的前提下，买家付款成功 <br>
     * TRADE_FINISHED：商户签约的产品不支持退款功能的前提下，买家付款成功；或者，商户签约的产品支持退款功能 的前提下，交易已经成功并且已经超过可退款期限。 <br>
     *
     * @param tradeStatus
     * @return
     */
    protected boolean isPay(String tradeStatus) {
        return "TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callback(PayCallbackReq req) throws Exception {
        log.info("AliPayAppImpl -> callback -> start -> req -> {}", req);

        Long orderNo = Long.parseLong(String.valueOf(req.getResourceMap().get("orderNo")));
        log.info("AliPayAppImpl -> callback -> orderNo -> {}", orderNo);
        AlipayTradeQueryResponse queryResponse = Factory.Payment.Common().query(String.valueOf(orderNo));
        log.info("AliPayAppImpl -> callback -> queryResponse -> {}", JSONUtil.toJsonStr(queryResponse));

        /*
        WAIT_BUYER_PAY ：交易创建，等待买家付款
        TRADE_CLOSED ：在指定时间段内未支付时关闭的交易或在交易完成全额退款成功时关闭的交易
        TRADE_SUCCESS：商户签约的产品支持退款功能的前提下，买家付款成功
        TRADE_FINISHED：商户签约的产品不支持退款功能的前提下，买家付款成功；或者，商户签约的产品支持退款功能 的前提下，交易已经成功并且已经超过可退款期限。
         */
        if (isPay(queryResponse.getTradeStatus())) {
            log.info("AliPayAppImpl -> callback -> paid -> {}", orderNo);
            orderService.orderDone(orderNo, getPayWay());
            return;
        }
        throw new BusinessException(PayExceptionEnum.ORDER_CLOSE);
    }
}
