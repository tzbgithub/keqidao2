package com.qidao.application.service.pay.impl;

import cn.hutool.core.util.StrUtil;
import com.alipay.easysdk.factory.Factory;
import com.qidao.application.entity.order.CustomOrder;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.pay.ALiInvokeParam;
import com.qidao.application.model.pay.AliPayConfig;
import com.qidao.application.model.pay.PayRes;
import com.qidao.application.model.pay.PolicyPayDTO;
import com.qidao.application.model.pay.enums.PayConstantsEnum;
import com.qidao.application.model.pay.enums.PayExceptionEnum;
import com.qidao.application.model.pay.enums.PayWayEnum;
import com.qidao.application.service.order.OrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("AliPayAppImpl")
@Slf4j
public class AliPayAppImpl extends AliPayBaseServiceImpl {
    public AliPayAppImpl(@Autowired OrderService orderService,
                         @Autowired AliPayConfig aliPayConfig) {
        super(orderService, aliPayConfig);
    }

    @Override
    public Integer getPayWay() {
        return PayWayEnum.ALIPAY.getType();
    }

    @SneakyThrows
    @Override
    public PayRes invoke(PolicyPayDTO req) {
        log.info("AliPayAppImpl -> pay -> req -> {}", req);
        // 订单信息查询
        CustomOrder order = orderService.selectByOrderNo(req.getOrderNo());
        if (null == order) {
            throw new BusinessException(PayExceptionEnum.ORDER_NULL);
        }
        if (order.getStatus() != 0) {
            throw new BusinessException(PayExceptionEnum.ORDER);
        }

        // 订单价格
        BigDecimal payPrice = order.getPayPrice();

        String productName = StrUtil.blankToDefault(req.getProductName(), "会员服务");
        String orderDescription = "开通" + productName;

        String subject = orderDescription;
        String outTradeNo = String.valueOf(req.getOrderNo());
        String totalAmount = payPrice.toString();

        com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse response = Factory.Payment.App().pay(subject, outTradeNo, totalAmount);

        log.info("alipay -> pay -> response -> {}", response.getBody());

        ALiInvokeParam aLiInvokeParam = ALiInvokeParam.appPayInvoke(appId, response.getBody());

        return PayRes.builder()
                .currency(PayConstantsEnum.CURRENCY.getStr())
                .payWay(getPayWay())
                .orderNo(order.getNo())
                .invokeParam(aLiInvokeParam)
                .totalAmount(payPrice)
                .build();
    }

}
